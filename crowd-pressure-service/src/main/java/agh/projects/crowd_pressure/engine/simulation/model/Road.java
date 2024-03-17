package agh.projects.crowd_pressure.engine.simulation.model;

import agh.projects.crowd_pressure.types.request_dto.RoadDto;
import lombok.Getter;

@Getter
public class Road {

    private final Point start;
    private final Point end;
    private final double width;
    private final Crossing crossing;

    public Road(Point start, Point end, double width, Crossing crossing) {
        this.start = start;
        this.end = end;
        this.width = width;
        this.crossing = crossing;
    }

    public Road(RoadDto dto) {
        this(new Point(dto.start()), new Point(dto.end()), dto.width(), new Crossing(dto.crossing()));
    }
}
