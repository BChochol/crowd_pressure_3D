package agh.projects.crowd_pressure.types.request_dto;

public record AgentGroupDto(
        PointDto startCenter,
        double startRadius,
        PointDto destination,
        double destinationRadius,
        int groupSize

) {
}
