package agh.projects.crowd_pressure.types.response_dto;

import java.util.List;

public record SimulationDto(
        String simulationId,
        BoardDto board,
        List<AgentDto> agents
) {
}
