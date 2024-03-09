package agh.projects.crowd_pressure.types.request_dto;

public record RoadDto(
        PointDto start,
        PointDto end,
        double width,
        CrossingDto crossing
) {
}
