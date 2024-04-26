package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.model.Point;
import agh.projects.crowd_pressure.engine.simulation.model.Road;
import agh.projects.crowd_pressure.engine.simulation.model.Wall;

import java.util.ArrayList;
import java.util.List;

public class DomainBoardInitializer implements BoardInitializer {

    private final List<Road> roads;

    public DomainBoardInitializer(List<Road> roads) {
        this.roads = roads;
    }

    @Override
    public Board initialize(int width, int height) throws Exception {
        List<Wall> walls = new ArrayList<>();
        roads.forEach(road -> {
            // todo: implement the change
        });

        return new Board(width, height, walls);
    }
}
