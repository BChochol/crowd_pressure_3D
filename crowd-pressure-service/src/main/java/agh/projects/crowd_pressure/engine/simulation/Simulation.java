package agh.projects.crowd_pressure.engine.simulation;

import agh.projects.crowd_pressure.engine.simulation.computation.ComputingEngine;
import agh.projects.crowd_pressure.engine.simulation.heuristic.Heuristic;
import agh.projects.crowd_pressure.engine.simulation.initializer.agent.AgentsInitializer;
import agh.projects.crowd_pressure.engine.simulation.initializer.board.BoardInitializer;
import agh.projects.crowd_pressure.engine.simulation.initializer.board.EmptyBoardInitializer;
import agh.projects.crowd_pressure.engine.simulation.initializer.agent.Map1AgentsInitializer;
import agh.projects.crowd_pressure.engine.simulation.model.Agent;
import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.physics.PhysicalModel;
import agh.projects.crowd_pressure.types.response_dto.SimulationDto;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Simulation implements Closeable {

    private final String simulationId;
    private final List<Agent> initAgents;
    private List<Agent> agents;
    private Board board;
    private PhysicalModel physicalModel;
    private List<Heuristic> heuristics;
    private ComputingEngine engine;

    public Simulation(int width, int height, int agentCount, PhysicalModel physicalModel, List<Heuristic> heuristics, ComputingEngine engine, BoardInitializer boardInitializer, AgentsInitializer agentInitializer) {
        this.simulationId = UUID.randomUUID().toString();
        this.physicalModel = physicalModel;
        this.heuristics = heuristics;
        this.engine = engine;

        try {
            this.board = boardInitializer.initialize(width, height);
        } catch (Exception exception) {
            System.out.println("Exception during board initialization. Empty board was created. Details: " + exception.getMessage());
            try {
                this.board = new EmptyBoardInitializer().initialize(width, height);
            } catch (Exception ignore) {
            }
        }

        try {
            this.agents = agentInitializer.initialize(agentCount, this.board);
        } catch (Exception exception) {
            System.out.println("Exception during agents initialization. Empty agent list was created. Details: " + exception.getMessage());
            try {
                this.agents = new Map1AgentsInitializer().initialize(agentCount, this.board);
            } catch (Exception ignore) {
            }
        }
        this.initAgents = new ArrayList<>();
        for (Agent agent : agents) initAgents.add(new Agent(agent));
    }

    public void setPhysicalModel(PhysicalModel physicalModel) {
        this.physicalModel = physicalModel;
    }

    public void setHeuristics(List<Heuristic> heuristics) {
        this.heuristics = heuristics;
    }

    public void setEngine(ComputingEngine engine) {
        try {
            this.engine.close();
            this.engine = engine;
        } catch (Exception exception) {
            System.out.println("Could not change the computing engine");
        }
    }

    public String getSimulationId() {
        return simulationId;
    }

    public SimulationDto toDto() {
        return new SimulationDto(simulationId);
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public Board getBoard() {
        return board;
    }

    public boolean step() throws Exception {
        engine.compute(agents, board, physicalModel, heuristics);
        return agents.stream().allMatch(Agent::isStopped);
    }

    public void restoreInitState() {
        agents.clear();
        for (Agent agent : initAgents) agents.add(new Agent(agent));
    }

    @Override
    public void close() throws IOException {
        engine.close();
    }
}
