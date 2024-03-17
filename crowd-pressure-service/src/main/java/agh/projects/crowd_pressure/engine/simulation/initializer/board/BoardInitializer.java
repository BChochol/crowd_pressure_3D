package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Board;

public interface BoardInitializer {

    /**
     * The method is responsible to initialize the board
     *
     * @return the board that will be initialized
     * @throws Exception the exception is thrown whenever any error occurs
     */
    Board initialize(int width, int height) throws Exception;

}
