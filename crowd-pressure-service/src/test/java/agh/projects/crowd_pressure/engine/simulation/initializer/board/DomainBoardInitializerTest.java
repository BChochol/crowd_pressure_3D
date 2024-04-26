package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomainBoardInitializerTest {

    private final int width = 500;
    private final int height = 500;

    @Test
    void domainBoardShouldInitializeEmptyState() {
        domainBoardTest(
                List.of(),
                Board.empty(width, height)
        );
    }

    @Test
    void domainBoardShouldInitializeBoardWithSingleRoad() {
        domainBoardTest(
                List.of(new Road(
                        Point.of(0, height / 2.0),
                        Point.of(width, height / 2.0),
                        10,
                        new Crossing(
                                Point.of(width / 2.0, 0),
                                Point.of(width / 2.0, height),
                                10
                        )
                )),
                new Board(
                        width,
                        height,
                        List.of(
                                // left block
                                new Wall(Point.of(0, height / 2.0 - 5), Point.of(width / 2.0 - 5, height / 2.0 - 5)),
                                new Wall(Point.of(width / 2.0 - 5, height / 2.0 - 5), Point.of(width / 2.0 - 5, height / 2.0 + 5)),
                                new Wall(Point.of(0, height / 2.0 + 5), Point.of(width / 2.0 - 5, height / 2.0 + 5)),

                                // right block
                                new Wall(Point.of(width / 2.0 + 5, height / 2.0 - 5), Point.of(width, height / 2.0 - 5)),
                                new Wall(Point.of(width / 2.0 + 5, height / 2.0 - 5), Point.of(width / 2.0 + 5, height / 2.0 + 5)),
                                new Wall(Point.of(width / 2.0 + 5, height / 2.0 + 5), Point.of(width, height / 2.0 + 5))
                        )
                )
        );
    }

    @Test
    void domainBoardShouldInitializeBoardWithSingleAskewRoad() {
        domainBoardTest(
                List.of(new Road(
                        Point.of(width, 0),
                        Point.of(0, height),
                        Math.sqrt(2),
                        null
                )),
                new Board(
                        width,
                        height,
                        List.of(
                                new Wall(Point.of(width - 1, 0), Point.of(width, 1)),
                                new Wall(Point.of(width, 1), Point.of(1, height)),
                                new Wall(Point.of(1, height), Point.of(0, height - 1)),
                                new Wall(Point.of(0, height - 1), Point.of(width - 1, 0))
                        )
                )
        );
    }

    @Test
    void domainBoardShouldInitializeBoardWithTwoRoads() {
        domainBoardTest(
                List.of(new Road(
                                Point.of(0, height / 2.0),
                                Point.of(width, height / 2.0),
                                10,
                                new Crossing(
                                        Point.of(width / 2.0, 0),
                                        Point.of(width / 2.0, height),
                                        10
                                )
                        ),
                        new Road(
                                Point.of(width / 4.0, 0),
                                Point.of(width / 4.0, height),
                                10,
                                new Crossing(
                                        Point.of(0, height / 4.0),
                                        Point.of(width, height / 4.0),
                                        10
                                )
                        )),
                new Board(
                        width,
                        height,
                        List.of(
                                // horizontal wall
                                // left block
                                new Wall(Point.of(0, height / 2.0 - 5), Point.of(width / 2.0 - 5, height / 2.0 - 5)),
                                new Wall(Point.of(width / 2.0 - 5, height / 2.0 - 5), Point.of(width / 2.0 - 5, height / 2.0 + 5)),
                                new Wall(Point.of(0, height / 2.0 + 5), Point.of(width / 2.0 - 5, height / 2.0 + 5)),

                                // right block
                                new Wall(Point.of(width / 2.0 + 5, height / 2.0 - 5), Point.of(width, height / 2.0 - 5)),
                                new Wall(Point.of(width / 2.0 + 5, height / 2.0 - 5), Point.of(width / 2.0 + 5, height / 2.0 + 5)),
                                new Wall(Point.of(width / 2.0 + 5, height / 2.0 + 5), Point.of(width, height / 2.0 + 5)),

                                // vertical wall
                                // upper block
                                new Wall(Point.of(width / 4.0 + 5, 0), Point.of(width / 4.0 + 5, height / 4.0 - 5)),
                                new Wall(Point.of(width / 4.0 + 5, height / 4.0 - 5), Point.of(width / 4.0 - 5, height / 4.0 - 5)),
                                new Wall(Point.of(width / 4.0 - 5, 0), Point.of(width / 4.0 - 5, height / 4.0 - 5)),

                                // lower block
                                new Wall(Point.of(width / 4.0 + 5, height / 4.0 + 5), Point.of(width / 4.0 + 5, height)),
                                new Wall(Point.of(width / 4.0 - 5, height / 4.0 + 5), Point.of(width / 4.0 + 5, height / 4.0 + 5)),
                                new Wall(Point.of(width / 4.0 - 5, height / 4.0 + 5), Point.of(width / 4.0 - 5, height))
                        )
                )
        );
    }

    private void domainBoardTest(
            List<Road> roads,
            Board expectedBoard
    ) {
        Board obtainedBoard = null;
        BoardInitializer initializer = new DomainBoardInitializer(roads);
        try {
            obtainedBoard = initializer.initialize(width, height);
        } catch (Exception ignore) {
        }
        assertEquals(expectedBoard, obtainedBoard);
    }

}
