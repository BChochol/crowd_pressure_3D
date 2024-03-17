package agh.projects.crowd_pressure.engine.simulation.model;

import agh.projects.crowd_pressure.types.request_dto.CrossingDto;
import lombok.Getter;

@Getter
public class Crossing {

    private final Point start;
    private final Point end;
    private final double width;

    public Crossing(Point start, Point end, double width) {
        this.start = start;
        this.end = end;
        this.width = width;
    }

    public Crossing(CrossingDto dto) {
        this(new Point(dto.start()), new Point(dto.end()), dto.width());
    }
}
