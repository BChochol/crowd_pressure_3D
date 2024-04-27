package agh.projects.crowd_pressure.engine.utils;

import agh.projects.crowd_pressure.engine.simulation.model.Point;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public record Line(Optional<Double> a, Double b) {

    public static Line fromPoints(Point p1, Point p2) {
        Optional<Double> a;
        double maybeA, b;

        maybeA = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        if (Double.isFinite(maybeA)) {
            a = Optional.of(maybeA);
            b = p1.getY() - maybeA * p1.getX();
        } else {
            a = Optional.empty();
            b = p1.getX();
        }

        return new Line(a, b);
    }

    public static Pair<Line, Line> extendLine(Line line, double width) {
        if (line.a.isPresent()) {
            double raw = (width / 2.0) / Math.sqrt(1 / (1 + Math.pow(line.a.get(), 2)));
            double shift = Math.round(raw * 1000) / 1000.0;
            return Pair.of(
                    new Line(line.a, line.b - shift),
                    new Line(line.a, line.b + shift)
            );
        } else {
            return Pair.of(
                    new Line(Optional.empty(), line.b - width / 2.0),
                    new Line(Optional.empty(), line.b + width / 2.0)
            );
        }
    }

    public static Optional<Point> getCrossingPointsBoundary(Line line1, Line line2, double lower, double upper, boolean vertical) {
        Optional<Point> point;
        if (line1.a.equals(line2.a)) {
            point = Optional.empty();
        }
        if (line1.a.isEmpty() && line2.a.isPresent()) {
            double x = line1.b;
            double y = line2.a.get() * x + line2.b;
            point = Optional.of(Point.of(x, y));
        } else if (line2.a.isEmpty() && line1.a.isPresent()) {
            double x = line2.b;
            double y = line1.a.get() * x + line1.b;
            point = Optional.of(Point.of(x, y));
        } else {
            double x = (line2.b - line1.b) / (line1.a.get() - line2.a.get());
            double y = line1.a.get() * x + line1.b;
            point = Optional.of(Point.of(x, y));
        }

        return point.flatMap(p -> {
                    if (vertical) {
                        if (p.getY() >= lower && p.getY() <= upper) return Optional.of(p);
                        else return Optional.empty();
                    } else {
                        if (p.getX() >= lower && p.getX() <= upper) return Optional.of(p);
                        else return Optional.empty();
                    }
                }
        );
    }

}
