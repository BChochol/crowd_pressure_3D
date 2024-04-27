package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.model.Point;
import agh.projects.crowd_pressure.engine.simulation.model.Road;
import agh.projects.crowd_pressure.engine.simulation.model.Wall;
import agh.projects.crowd_pressure.engine.utils.Line;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class DomainBoardInitializer implements BoardInitializer {

    private final List<Road> roads;

    public DomainBoardInitializer(List<Road> roads) {
        this.roads = roads;
    }

    @Override
    public Board initialize(int width, int height) throws Exception {
        Line upper = Line.fromPoints(Point.of(0, height), Point.of(width, height));
        Line lower = Line.fromPoints(Point.of(0, 0), Point.of(width, 0));
        Line left = Line.fromPoints(Point.of(0, 0), Point.of(0, height));
        Line right = Line.fromPoints(Point.of(width, 0), Point.of(width, height));

        List<Wall> walls = new ArrayList<>();
        roads.forEach(road -> {
            // todo: implement the change
            Line mainLine = Line.fromPoints(road.getStart(), road.getEnd());
            Pair<Line, Line> roadLines = Line.extendLine(mainLine, road.getWidth());
            System.out.println(roadLines);


        });

        return new Board(width, height, walls);
    }
}
