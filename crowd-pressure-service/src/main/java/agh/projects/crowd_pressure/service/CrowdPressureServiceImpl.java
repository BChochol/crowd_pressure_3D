package agh.projects.crowd_pressure.service;

import agh.projects.crowd_pressure.engine.simulation.Simulation;
import agh.projects.crowd_pressure.engine.simulation.computation.MultiThreadComputingEngine;
import agh.projects.crowd_pressure.engine.simulation.heuristic.DirectionHeuristic;
import agh.projects.crowd_pressure.engine.simulation.heuristic.DistanceHeuristic;
import agh.projects.crowd_pressure.engine.simulation.physics.SocialForcePhysicalModel;
import agh.projects.crowd_pressure.repository.CrowdPressureRepository;
import agh.projects.crowd_pressure.types.response_dto.SimulationDto;
import agh.projects.crowd_pressure.types.request_dto.CreateSimulationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CrowdPressureServiceImpl implements CrowdPressureService {

    private static final int THREAD_COUNT = 10;
    private final CrowdPressureRepository repository;

    @Override
    public Optional<SimulationDto> getSimulationById(String simulationId) {
        return repository.getSimulation(simulationId).map(Simulation::toDto);
    }

    @Override
    public Optional<SimulationDto> deleteSimulationById(String simulationId) {
        return repository.deleteSimulation(simulationId).map(Simulation::toDto);
    }

    @Override
    public SimulationDto createSimulation(CreateSimulationRequestDto createSimulationRequestDto) {
        checkCreateRequest(createSimulationRequestDto);

        Simulation simulation = new Simulation(
                new SocialForcePhysicalModel(
                        createSimulationRequestDto.scaleCoefficient(),
                        createSimulationRequestDto.destinationRadius(),
                        createSimulationRequestDto.timeQuantum()
                ),
                List.of( // todo: think about shared heuristics
                        new DirectionHeuristic(),
                        new DistanceHeuristic()
                ),
                new MultiThreadComputingEngine(THREAD_COUNT), // todo: think about shared engine
                null, // todo: implement new board initializer
                null // todo: implement new agent initializer
        );

        repository.saveSimulation(simulation);

        return simulation.toDto();
    }

    @Override
    public Optional<SimulationDto> stepSimulation(String simulationId, int steps) {
        return executeOnSimulation(simulationId, sim -> {
            try {
                for (int i = 0; i < steps; ++i) {
                    sim.step();
                }
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }

            return sim;
        });
    }

    @Override
    public Optional<SimulationDto> resetSimulation(String simulationId) {
        return executeOnSimulation(simulationId, sim -> {
                    sim.restoreInitState();
                    return sim;
                }
        );
    }

    private Optional<SimulationDto> executeOnSimulation(String simulationId, Function<Simulation, Simulation> fun) {
        return repository.getSimulation(simulationId).map(fun).map(Simulation::toDto);
    }

    private void checkCreateRequest(CreateSimulationRequestDto createSimulationRequestDto) {
        assert createSimulationRequestDto.simulationHeight() > 0;
        assert createSimulationRequestDto.simulationWidth() > 0;
        assert createSimulationRequestDto.destinationRadius() > 0;
        assert createSimulationRequestDto.scaleCoefficient() > 0;
        assert createSimulationRequestDto.timeQuantum() > 0;
        assert !createSimulationRequestDto.roads().isEmpty();
        assert !createSimulationRequestDto.agentGroups().isEmpty();
    }

}
