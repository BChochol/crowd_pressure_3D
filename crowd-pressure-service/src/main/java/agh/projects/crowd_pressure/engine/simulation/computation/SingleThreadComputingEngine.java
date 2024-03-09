package agh.projects.crowd_pressure.engine.simulation.computation;

import agh.projects.crowd_pressure.engine.simulation.computation.task.SimulationTask;
import agh.projects.crowd_pressure.engine.simulation.computation.task.Task;
import agh.projects.crowd_pressure.engine.simulation.heuristic.Heuristic;
import agh.projects.crowd_pressure.engine.simulation.model.Agent;
import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.physics.PhysicalModel;

import java.io.IOException;
import java.util.List;

public class SingleThreadComputingEngine implements ComputingEngine {

    @Override
    public void compute(List<Agent> agents, Board board, PhysicalModel physicalModel, List<Heuristic> heuristics) throws Exception {
        Task task = new SimulationTask(physicalModel, heuristics, agents, agents, board);
        task.execute();
        task.cleanUp();
    }

    @Override
    public void close() throws IOException {
    }
}
