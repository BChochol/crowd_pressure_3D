package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Board;
import agh.projects.crowd_pressure.engine.simulation.model.Point;
import agh.projects.crowd_pressure.engine.simulation.model.Road;
import agh.projects.crowd_pressure.engine.simulation.model.Wall;
import agh.projects.crowd_pressure.engine.utils.Line;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
            Line mainLine = Line.fromPoints(road.getStart(), road.getEnd());
            Pair<Line, Line> roadLines = Line.extendLine(mainLine, road.getWidth());

            Pair<Point, Point> points1 = findBorderCrossingPoints(roadLines.getLeft(), upper, lower, left, right, width, height);
            Pair<Point, Point> points2 = findBorderCrossingPoints(roadLines.getRight(), upper, lower, left, right, width, height);

            Point point1 = points1.getLeft();
            Point point2 = points1.getRight();
            Point point3 = points2.getLeft();
            Point point4 = points2.getRight();

            Wall wall1 = new Wall(point1, point2);
            Wall wall2 = new Wall(point3, point4);

            walls.add(wall1);
            walls.add(wall2);
        });

        return new Board(width, height, walls);
    }

    private Pair<Point, Point> findBorderCrossingPoints(Line line, Line upper, Line lower, Line left, Line right, int width, int height) {
        List<Point> points = Stream.of(
                Line.getCrossingPointsBoundary(line, upper, 0, width, false),
                Line.getCrossingPointsBoundary(line, right, 0, height, true),
                Line.getCrossingPointsBoundary(line, lower, 0, width, false),
                Line.getCrossingPointsBoundary(line, left, 0, height, true)
        ).filter(Optional::isPresent).map(Optional::get).toList();

        if (points.size() != 2) throw new IllegalStateException("Cannot establish border crossing points");
        return Pair.of(points.get(0), points.get(1));
    }
}
