package agh.projects.crowd_pressure.engine.simulation.initializer.agent;

import agh.projects.crowd_pressure.engine.simulation.model.Agent;
import agh.projects.crowd_pressure.engine.simulation.model.Board;

import java.util.List;

public class Map5AgentsInitializer implements AgentsInitializer{

    @Override
    public List<Agent> initialize(int agentCount, Board board) throws Exception {
        return new Map4AgentsInitializer().initialize(agentCount, board);
    }
}
