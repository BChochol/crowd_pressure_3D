package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.model.Point;
import agh.projects.crowd_pressure.engine.simulation.model.Wall;

import java.util.ArrayList;

public class DefaultBoardInitializer implements BoardInitializer {
    @Override
    public Board initialize(int width, int height) throws Exception {
        Board result;
        try {
            result = new EmptyBoardInitializer().initialize(width, height);
        } catch (Exception exception) {
            result = new Board(width, height, new ArrayList<>());
        }

        result.getWalls().add(new Wall(
                new Point(width / 2.0, height - 1),
                new Point(width / 2.0, (height - 1) / 2.0 + 3))
        );

        result.getWalls().add(new Wall(
                new Point(width / 2.0, 0),
                new Point(width / 2.0, (height - 1) / 2.0 - 3))
        );

        return result;
    }
}
