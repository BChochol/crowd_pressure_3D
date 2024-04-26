package agh.projects.crowd_pressure.engine.simulation.model;

import agh.projects.crowd_pressure.types.response_dto.WallDto;

import java.util.Objects;

public class Wall {

    private Point startPoint;
    private Point endPoint;

    public Wall(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public WallDto toDto() {
        return new WallDto(startPoint.toDto(), endPoint.toDto());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return Objects.equals(startPoint, wall.startPoint) && Objects.equals(endPoint, wall.endPoint) ||
                Objects.equals(startPoint, wall.endPoint) && Objects.equals(endPoint, wall.startPoint);
    }

}
