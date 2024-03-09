package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.model.Point;
import agh.projects.crowd_pressure.engine.simulation.model.Wall;

public class Map6BoardInitializer implements BoardInitializer{

    private static final int WALL_DISTANCE = 6;
    private static final int CROSS_WIDTH = 4;

    @Override
    public Board initialize(int width, int height) throws Exception {
        Board result = new Map2BoardInitializer().initialize(width, height);

        result.getWalls().add(new Wall(
                new Point(width / 2.0 - WALL_DISTANCE, height / 2.0 - CROSS_WIDTH),
                new Point(width / 2.0 - WALL_DISTANCE, height / 2.0 + CROSS_WIDTH)
        ));

        return result;
    }
}
