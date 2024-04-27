package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.*;
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

            Line roadLine1 = roadLines.getLeft();
            Line roadLine2 = roadLines.getRight();

            Pair<Point, Point> points1 = findBorderCrossingPoints(roadLine1, upper, lower, left, right, width, height);
            Pair<Point, Point> points2 = findBorderCrossingPoints(roadLine2, upper, lower, left, right, width, height);

            Point wall1Start = points1.getLeft();
            Point wall1End = points1.getRight();
            Point wall2Start = points2.getLeft();
            Point wall2End = points2.getRight();

            Crossing crossing = road.getCrossing();
            if (crossing == null) {
                Wall wall1 = new Wall(wall1Start, wall1End);
                Wall wall2 = new Wall(wall2Start, wall2End);

                walls.add(wall1);
                walls.add(wall2);
            } else {
                Line crossingMainLine = Line.fromPoints(crossing.getStart(), crossing.getEnd());
                Pair<Line, Line> crossingLines = Line.extendLine(crossingMainLine, crossing.getWidth());

                Line crossingLine1 = crossingLines.getLeft(); // always below line 2 -> points from crossing 1 should be lower
                Line crossingLine2 = crossingLines.getRight();

                Optional<Point> crossingLine1RoadLine1 = Line.getCrossingPoint(crossingLine1, roadLine1);
                Optional<Point> crossingLine1RoadLine2 = Line.getCrossingPoint(crossingLine1, roadLine2);
                Optional<Point> crossingLine2RoadLine1 = Line.getCrossingPoint(crossingLine2, roadLine1);
                Optional<Point> crossingLine2RoadLine2 = Line.getCrossingPoint(crossingLine2, roadLine2);

                if (crossingLine1RoadLine1.isPresent() && crossingLine1RoadLine2.isPresent() && crossingLine2RoadLine1.isPresent() && crossingLine2RoadLine2.isPresent()) {
                    walls.add(new Wall(wall1Start, crossingLine2RoadLine1.get()));
                    walls.add(new Wall(wall2Start, crossingLine2RoadLine2.get()));
                    walls.add(new Wall(crossingLine2RoadLine1.get(), crossingLine2RoadLine2.get()));

                    walls.add(new Wall(wall2End, crossingLine1RoadLine1.get()));
                    walls.add(new Wall(wall2End, crossingLine1RoadLine2.get()));
                    walls.add(new Wall(crossingLine1RoadLine1.get(), crossingLine1RoadLine2.get()));
                } else {
                    throw new IllegalStateException("Cannot establish crossing points of the crossing with the road");
                }
            }
        });

        return new Board(width, height, walls);
    }

    private Pair<Point, Point> findBorderCrossingPoints(Line line, Line upper, Line lower, Line left, Line right, int width, int height) {
        List<Point> points = Stream.of(
                Line.getCrossingPointBoundary(line, upper, 0, width, false),
                Line.getCrossingPointBoundary(line, right, 0, height, true),
                Line.getCrossingPointBoundary(line, lower, 0, width, false),
                Line.getCrossingPointBoundary(line, left, 0, height, true)
        ).filter(Optional::isPresent).map(Optional::get).toList();

        if (points.size() != 2) throw new IllegalStateException("Cannot establish border crossing points");
        return Pair.of(points.get(0), points.get(1));
    }
}
