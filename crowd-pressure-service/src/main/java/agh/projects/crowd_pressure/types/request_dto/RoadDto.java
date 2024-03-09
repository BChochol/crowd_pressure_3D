package agh.projects.crowd_pressure.types.request_dto;

public record RoadDto(
        PointDto start,
        PointDto end,
        Double width,
        CrossingDto crossing
) {
}
