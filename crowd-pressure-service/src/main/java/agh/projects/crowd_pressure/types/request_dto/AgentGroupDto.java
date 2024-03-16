package agh.projects.crowd_pressure.types.request_dto;

import agh.projects.crowd_pressure.types.common.PointDto;

public record AgentGroupDto(
        PointDto startCenter,
        double startRadius,
        PointDto destination,
        double destinationRadius,
        int groupSize

) {
}
