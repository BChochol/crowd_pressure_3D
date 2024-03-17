package agh.projects.crowd_pressure.repository;

import agh.projects.crowd_pressure.engine.simulation.Simulation;
import agh.projects.crowd_pressure.engine.simulation.computation.SingleThreadComputingEngine;
import agh.projects.crowd_pressure.engine.simulation.heuristic.DirectionHeuristic;
import agh.projects.crowd_pressure.engine.simulation.heuristic.DistanceHeuristic;
import agh.projects.crowd_pressure.engine.simulation.initializer.agent.DefaultAgentsInitializer;
import agh.projects.crowd_pressure.engine.simulation.initializer.board.DefaultBoardInitializer;
import agh.projects.crowd_pressure.engine.simulation.physics.SocialForcePhysicalModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CrowdPressureRepositoryImpl implements CrowdPressureRepository {

    private final ConcurrentHashMap<String, Simulation> simulations;

    public CrowdPressureRepositoryImpl() {
        this.simulations = new ConcurrentHashMap<>();
        this.simulations.put(
                "default",
                new Simulation(
                        500,
                        500,
                        new SocialForcePhysicalModel(
                                1,
                                1,
                                1
                        ),
                        List.of(
                                new DirectionHeuristic(),
                                new DistanceHeuristic()
                        ),
                        new SingleThreadComputingEngine(),
                        new DefaultBoardInitializer(),
                        new DefaultAgentsInitializer(20)
                )
        );
    }

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
