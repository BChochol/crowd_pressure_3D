package agh.projects.crowd_pressure.repository;

import agh.projects.crowd_pressure.engine.simulation.Simulation;

import java.util.Optional;

public interface CrowdPressureRepository {

    Optional<Simulation> getSimulation(String simulationId);

    void saveSimulation(Simulation simulation);

    Optional<Simulation> deleteSimulation(String simulationId);

}
