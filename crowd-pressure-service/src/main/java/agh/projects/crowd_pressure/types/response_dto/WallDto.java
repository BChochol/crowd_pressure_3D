package agh.projects.crowd_pressure.types.response_dto;

import agh.projects.crowd_pressure.types.common.PointDto;

public record WallDto(
        PointDto start,
        PointDto end
) {
}
