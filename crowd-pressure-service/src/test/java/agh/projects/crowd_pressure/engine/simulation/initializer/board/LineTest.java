package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.Point;
import agh.projects.crowd_pressure.engine.utils.Line;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void fromPointsShouldCalculateProperLine() {
        Point p1 = Point.of(0, 0);
        Point p2 = Point.of(1, 1);

        Line line1 = Line.fromPoints(p1, p2);
        Line line2 = Line.fromPoints(p2, p1);

        Line expected = new Line(Optional.of(1.0), 0.0);

        assertEquals(expected, line1);
        assertEquals(expected, line2);
    }

    @Test
    void fromPointsShouldCalculateProperVerticalLine() {
        Point p1 = Point.of(1, 0);
        Point p2 = Point.of(1, 1);

        Line line1 = Line.fromPoints(p1, p2);
        Line line2 = Line.fromPoints(p2, p1);

        Line expected = new Line(Optional.empty(), 1.0);

        assertEquals(expected, line1);
        assertEquals(expected, line2);
    }

    @Test
    void extendLine() {
        Line line = new Line(Optional.of(1.0), 0.0);

        Pair<Line, Line> lines = Line.extendLine(line, Math.sqrt(2));
        Pair<Line, Line> expected = Pair.of(
                new Line(Optional.of(1.0), -1.0),
                new Line(Optional.of(1.0), 1.0)
        );

        assertEquals(expected, lines);
    }

    @Test
    void extendVerticalLine() {
        Line line = new Line(Optional.empty(), 5.0);

        Pair<Line, Line> lines = Line.extendLine(line, 2);
        Pair<Line, Line> expected = Pair.of(
                new Line(Optional.empty(), 4.0),
                new Line(Optional.empty(), 6.0)
        );

        assertEquals(expected, lines);
    }

    @Test
    void crossingPointWithinBoundary() {
        Line line1 = new Line(Optional.of(1.0), 0.0);
        Line line2 = new Line(Optional.of(-1.0), 2.0);

        Optional<Point> crossingPoint = Line.getCrossingPointBoundary(line1, line2, 0.0, 10.0, true);
        Optional<Point> expected = Optional.of(Point.of(1, 1));

        assertEquals(expected, crossingPoint);
    }

    @Test
    void crossingPointOutsideBoundary() {
        Line line1 = new Line(Optional.of(1.0), 0.0);
        Line line2 = new Line(Optional.of(-1.0), 2.0);

        Optional<Point> crossingPoint = Line.getCrossingPointBoundary(line1, line2, 2.0, 10.0, true);
        Optional<Point> expected = Optional.empty();

        assertEquals(expected, crossingPoint);
    }

}