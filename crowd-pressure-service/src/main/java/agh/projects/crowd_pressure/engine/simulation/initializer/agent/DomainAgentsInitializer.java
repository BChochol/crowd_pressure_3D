package agh.projects.crowd_pressure.engine.simulation.initializer.agent;

import agh.projects.crowd_pressure.engine.simulation.model.Agent;
import agh.projects.crowd_pressure.engine.simulation.model.AgentGroup;
import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.model.Point;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static agh.projects.crowd_pressure.engine.utils.MathUtil.randomizePointInCircle;

public class DomainAgentsInitializer implements AgentsInitializer {

    private final Random random = new Random();
    private final List<AgentGroup> agentGroups;

    public DomainAgentsInitializer(List<AgentGroup> agentGroups) {
        this.agentGroups = agentGroups;
    }

    @Override
    public List<Agent> initialize(Board board) throws Exception {
        return agentGroups.stream().flatMap(group -> {
            assertCorrectGroup(group, board);
            return agentsFromGroup(group);
        }).toList();
    }

    private Stream<Agent> agentsFromGroup(AgentGroup group) {
        return IntStream.range(0, group.getCount()).mapToObj(i -> {
            double agentMass = random.nextDouble(50, 100);

            return new Agent(
                    randomizePointInCircle(group.getStartCenter(), group.getStartRadius(), random),
                    agentMass,
                    agentMass / 50.0,
                    random.nextDouble(1.5, 2),
                    random.nextDouble(1.2, 1.3),
                    random.nextDouble(100, 105),
                    random.nextDouble(0.45, 0.55),
                    randomizePointInCircle(group.getDestinationCenter(), group.getDestinationRadius(), random)
            );
        });
    }

    private static void assertCorrectGroup(AgentGroup group, Board board) {
        assert group.getCount() > 0;
        assert group.getStartRadius() > 0;
        assert group.getDestinationRadius() > 0;

        assertCorrectPoint(group.getStartCenter(), board);
        assertCorrectPoint(group.getDestinationCenter(), board);
    }

    private static void assertCorrectPoint(Point point, Board board) {
        assert point.getX() >= 0 && point.getX() < board.getWidth();
        assert point.getY() >= 0 && point.getY() < board.getHeight();
    }
}
