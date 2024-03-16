package agh.projects.crowd_pressure.engine.simulation.model;

import agh.projects.crowd_pressure.types.response_dto.BoardDto;

import java.util.List;

public class Board {

    private final List<Wall> walls;
    private final int width;
    private final int height;

    public Board(int width, int height, List<Wall> walls) {
        this.width = width;
        this.height = height;
        this.walls = walls;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BoardDto toDto() {
        return new BoardDto(
                getWidth(),
                getHeight(),
                getWalls().stream().map(Wall::toDto).toList()
        );
    }
}
