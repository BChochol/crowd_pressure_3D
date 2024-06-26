package agh.projects.crowd_pressure.engine.utils;

import agh.projects.crowd_pressure.engine.simulation.model.*;

import java.util.List;
import java.util.Random;

public class MathUtil {

    private MathUtil() {
    }

    public static double calculateLineCoefficient(Point point1, Point point2) {
        return (point1.getY() - point2.getY()) / (point1.getX() - point2.getX());
    }

    public static double calculateMutualAngle(Point point1, Point point2) {
        double angle = Math.atan2(point2.getY() - point1.getY(), point2.getX() - point1.getX());
        if (angle < 0) {
            angle += 2 * Math.PI;
        }
        return angle;
    }

    public static double calculateDistanceBetweenPoints(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
    }

    public static Point getCrossingPoint(Point sourcePoint, double sourceAngle, Point straightStart, Point straightEnd) {
        double x1 = sourcePoint.getX(), y1 = sourcePoint.getY();
        double x2 = -1, y2 = Math.tan(sourceAngle) * (-1) + (y1 - Math.tan(sourceAngle) * x1);
        double x3 = straightStart.getX(), y3 = straightStart.getY();
        double x4 = straightEnd.getX(), y4 = straightEnd.getY();

        double tmp = ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
        double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / tmp;
        double y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / tmp;

        return new Point(x, y);
    }

    public static Point getCrossingPointInShortestPath(Point sourcePoint, Point straightStart, Point straightEnd) {
        double lineCoefficient = calculateLineCoefficient(straightStart, straightEnd);
        return getCrossingPoint(sourcePoint, Math.atan(-1 / lineCoefficient), straightStart, straightEnd);
    }

    public static boolean isInsideCircle(Point pointToCheck, Point center, double radius) {
        return calculateDistanceBetweenPoints(pointToCheck, center) < radius;
    }

    public static boolean isAngleBetween(double angleToVerify, double angle1, double angle2) {
        double a = Math.min(angle1, angle2);
        double b = Math.max(angle1, angle2);
        if ((b - a) > Math.PI) return ((angleToVerify > b) || (angleToVerify < a));
        return ((angleToVerify > a) && (angleToVerify < b));
    }

    public static double calculateDistanceToCollision(double angleToCheck, Agent agent, List<Agent> agents, Board board) {
        // check obstacles
        double distanceToCollision = agent.getAgentMaxVisionDistance();
        for (Agent obstacle : agents) {
            double mutualAngle = MathUtil.calculateMutualAngle(agent.getPosition(), obstacle.getPosition());
            double distance = MathUtil.calculateDistanceBetweenPoints(agent.getPosition(), obstacle.getPosition()) + obstacle.getAgentRadius();
            double relaxationAngle = Math.atan(obstacle.getAgentRadius() / distance);

            if (isAngleBetween(angleToCheck, mutualAngle - relaxationAngle, mutualAngle + relaxationAngle)) {
                distanceToCollision = Math.min(distance, distanceToCollision);
            }
        }

        // check walls
        for (Wall wall : board.getWalls()) {
            double mutualAngleStart = MathUtil.calculateMutualAngle(agent.getPosition(), wall.getStartPoint());
            double mutualAngleEnd = MathUtil.calculateMutualAngle(agent.getPosition(), wall.getEndPoint());

            if (isAngleBetween(angleToCheck, mutualAngleStart, mutualAngleEnd)) {
                Point crossingPoint = MathUtil.getCrossingPoint(agent.getPosition(), angleToCheck, wall.getStartPoint(), wall.getEndPoint());
                double distance = MathUtil.calculateDistanceBetweenPoints(agent.getPosition(), crossingPoint);
                distanceToCollision = Math.min(distance, distanceToCollision);
            }
        }

        return distanceToCollision;
    }

    public static Point randomizePointInCircle(Point center, double radius, Random random) {
        double angle = random.nextDouble() * 2 * Math.PI;
        double distance = Math.sqrt(random.nextDouble()) * radius;

        double x = center.getX() + (distance * Math.cos(angle));
        double y = center.getY() + (distance * Math.sin(angle));

        return new Point(x, y);
    }
}