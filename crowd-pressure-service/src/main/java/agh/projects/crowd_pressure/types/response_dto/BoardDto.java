package agh.projects.crowd_pressure.types.response_dto;

import java.util.List;

public record BoardDto(
        int width,
        int height,
        List<WallDto> walls
) {
}
