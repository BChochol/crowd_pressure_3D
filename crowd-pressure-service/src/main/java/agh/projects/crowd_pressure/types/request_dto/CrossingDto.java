package agh.projects.crowd_pressure.types.request_dto;

public record CrossingDto(
        PointDto start,
        PointDto end,
        Double width
) {
}
