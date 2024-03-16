package agh.projects.crowd_pressure.types.request_dto;

import agh.projects.crowd_pressure.types.common.PointDto;

public record RoadDto(
        PointDto start,
        PointDto end,
        double width,
        CrossingDto crossing
) {
}
