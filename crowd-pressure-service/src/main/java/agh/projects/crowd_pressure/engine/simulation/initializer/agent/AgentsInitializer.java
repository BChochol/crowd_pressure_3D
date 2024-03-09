package agh.projects.crowd_pressure.engine.simulation.initializer.agent;

import agh.projects.crowd_pressure.engine.simulation.model.Agent;
import agh.projects.crowd_pressure.engine.simulation.model.Board;

import java.util.List;

public interface AgentsInitializer {

    /**
     * The method is responsible to initialize the agent
     *
     * @param agentCount the number of agents that will be created
     * @param board      the board that the agents belong to
     * @return the list of initialized agents
     * @throws Exception the exception is thrown whenever any error occurs
     */
    List<Agent> initialize(int agentCount, Board board) throws Exception;
}
