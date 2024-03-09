package agh.projects.crowd_pressure.repository;

import agh.projects.crowd_pressure.types.domain.Simulation;

import java.util.Optional;

public interface CrowdPressureRepository {

    Optional<Simulation> getSimulation(String simulationId);

    Optional<Simulation> deleteSimulation(String simulationId);

}
