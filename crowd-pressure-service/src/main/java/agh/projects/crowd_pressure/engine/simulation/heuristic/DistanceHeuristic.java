package agh.projects.crowd_pressure.engine.simulation.heuristic;

import agh.projects.crowd_pressure.engine.simulation.model.Agent;
import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.utils.MathUtil;

import java.util.List;

public class DistanceHeuristic implements Heuristic {

    @Override
    public void apply(Agent agent, List<Agent> allAgents, Board board) throws Exception {
        agent.getDesiredVelocity().setValue(
                Math.min(
                        agent.getAgentComfortableSpeed(),
                        MathUtil.calculateDistanceToCollision(agent.getVelocity().getAngle(), agent, allAgents, board) / agent.getAgentRelaxationTime()
                )
        );
    }
}
