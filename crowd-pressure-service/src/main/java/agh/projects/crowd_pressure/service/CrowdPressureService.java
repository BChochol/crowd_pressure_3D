package agh.projects.crowd_pressure.service;

import agh.projects.crowd_pressure.types.response_dto.SimulationDto;
import agh.projects.crowd_pressure.types.request_dto.CreateSimulationRequestDto;

import java.util.Optional;

public interface CrowdPressureService {

    Optional<SimulationDto> getSimulationById(String simulationId);

    Optional<SimulationDto> deleteSimulationById(String simulationId);

    SimulationDto createSimulation(CreateSimulationRequestDto createSimulationRequestDto);
}
