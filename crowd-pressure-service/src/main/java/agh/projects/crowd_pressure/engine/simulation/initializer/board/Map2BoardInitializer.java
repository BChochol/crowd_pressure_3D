package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Board;

public class Map2BoardInitializer implements BoardInitializer {

    @Override
    public Board initialize(int width, int height) throws Exception {
        return new Map1BoardInitializer().initialize(width, height);
    }
}
