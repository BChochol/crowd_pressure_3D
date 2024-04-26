package agh.projects.crowd_pressure.engine.simulation.model;

import agh.projects.crowd_pressure.types.response_dto.BoardDto;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    public static Board empty(int width, int height) {
        return new Board(width, height, List.of());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return width == board.width && height == board.height && Objects.equals(Set.copyOf(walls), Set.copyOf(board.walls));
    }
}
