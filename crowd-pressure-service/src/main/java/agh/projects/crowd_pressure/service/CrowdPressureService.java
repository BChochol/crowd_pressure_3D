package agh.projects.crowd_pressure.service;

import agh.projects.crowd_pressure.types.domain.Simulation;
import agh.projects.crowd_pressure.types.request_dto.CreateSimulationRequestDto;
import agh.projects.crowd_pressure.types.response_dto.CreateSimulationResponseDto;

import java.util.Optional;

public interface CrowdPressureService {

    Optional<Simulation> getSimulationById(String simulationId);

    Optional<Simulation> deleteSimulationById(String simulationId);

    CreateSimulationResponseDto createSimulation(CreateSimulationRequestDto createSimulationRequestDto);
}
