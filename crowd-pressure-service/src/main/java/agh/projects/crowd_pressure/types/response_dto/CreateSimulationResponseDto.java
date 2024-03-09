package agh.projects.crowd_pressure.types.response_dto;

import agh.projects.crowd_pressure.types.domain.Map;

public record CreateSimulationResponseDto(String simulationId, Map map) {
}
