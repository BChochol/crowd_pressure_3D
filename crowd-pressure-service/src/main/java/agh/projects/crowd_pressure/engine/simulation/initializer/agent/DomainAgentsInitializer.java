package agh.projects.crowd_pressure.engine.simulation.initializer.agent;

import agh.projects.crowd_pressure.engine.simulation.model.Agent;
import agh.projects.crowd_pressure.engine.simulation.model.AgentGroup;
import agh.projects.crowd_pressure.engine.simulation.model.Board;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class DomainAgentsInitializer implements AgentsInitializer{

    private final List<AgentGroup> agentGroups;

    public DomainAgentsInitializer(List<AgentGroup> agentGroups) {
        this.agentGroups = agentGroups;
    }

    @Override
    public List<Agent> initialize(Board board) throws Exception {
        // todo: write agent initializer
        return null;
    }
}
