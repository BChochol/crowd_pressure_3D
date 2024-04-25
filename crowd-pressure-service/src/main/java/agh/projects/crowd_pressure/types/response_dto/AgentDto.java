package agh.projects.crowd_pressure.types.response_dto;

import agh.projects.crowd_pressure.types.common.PointDto;

public record AgentDto(
        String agentId,
        PointDto position,
        PointDto agentDesiredPosition,
        double agentMass,
        double agentRadius,
        double agentVisionAngle,
        double agentMaxVisionDistance,
        boolean isStopped
) {
}
