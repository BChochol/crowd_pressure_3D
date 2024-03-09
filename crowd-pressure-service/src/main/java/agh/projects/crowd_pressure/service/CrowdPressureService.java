package agh.projects.crowd_pressure.service;

import agh.projects.crowd_pressure.types.domain.Simulation;

import java.util.Optional;

public interface CrowdPressureService {

   Optional<Simulation> getSimulationById(String simulationId);

}
