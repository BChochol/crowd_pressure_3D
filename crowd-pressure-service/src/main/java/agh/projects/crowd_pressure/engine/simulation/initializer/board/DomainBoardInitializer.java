package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.model.Road;

import java.util.List;

public class DomainBoardInitializer implements BoardInitializer {

    private final List<Road> roads;


    public DomainBoardInitializer(List<Road> roads) {
        this.roads = roads;
    }

    @Override
    public Board initialize(int width, int height) throws Exception {
        // todo: write board initializer
        return new DefaultBoardInitializer().initialize(width, height);
    }
}
