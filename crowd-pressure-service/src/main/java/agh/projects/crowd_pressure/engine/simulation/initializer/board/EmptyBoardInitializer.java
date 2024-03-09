package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.model.Point;
import agh.projects.crowd_pressure.engine.simulation.model.Wall;

import java.util.ArrayList;
import java.util.List;

public class EmptyBoardInitializer implements BoardInitializer {

    @Override
    public Board initialize(int width, int height) throws Exception {
        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(new Point(0, 0), new Point(0, height)));
        walls.add(new Wall(new Point(0, height), new Point(width, height)));
        walls.add(new Wall(new Point(0, 0), new Point(width, 0)));
        walls.add(new Wall(new Point(width, 0), new Point(width, height)));
        return new Board(width, height, walls);
    }
}
