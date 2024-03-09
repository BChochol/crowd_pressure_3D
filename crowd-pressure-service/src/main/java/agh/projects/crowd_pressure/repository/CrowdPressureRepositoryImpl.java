package agh.projects.crowd_pressure.repository;

import agh.projects.crowd_pressure.engine.simulation.Simulation;
import agh.projects.crowd_pressure.types.response_dto.SimulationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class CrowdPressureRepositoryImpl implements CrowdPressureRepository {

    private final ConcurrentHashMap<String, Simulation> simulations = new ConcurrentHashMap<>();

    @Override
    public Optional<Simulation> getSimulation(String simulationId) {
        return Optional.ofNullable(simulations.get(simulationId));
    }

    @Override
    public void saveSimulation(Simulation simulation) {
        String id = simulation.getSimulationId();
        if (simulations.containsKey(id))
            throw new IllegalStateException(String.format("Simulation with id [%s] already exist", id));

        simulations.put(id, simulation);
    }

    @Override
    public Optional<Simulation> deleteSimulation(String simulationId) {
        return Optional.ofNullable(simulations.remove(simulationId));
    }
}
