package agh.projects.crowd_pressure.engine.simulation.initializer.board;

import agh.projects.crowd_pressure.engine.simulation.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class DomainBoardInitializerTest {

    private final Logger logger = Logger.getLogger(getClass().getName());

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
                                new Wall(Point.of(width, 1), Point.of(1, height)),
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
        } catch (Exception exception) {
            logger.warning(String.format("Exception on assertion: [%s]", exception.getMessage()));
        }
        assertEquals(expectedBoard, obtainedBoard);
    }

}

// expected
// Board[width=500, height=500, walls=[
// 1e. Wall[startPoint=Point(0.0, 255.0), endPoint=Point(245.0, 255.0)], 3o
// 2e. Wall[startPoint=Point(255.0, 255.0), endPoint=Point(500.0, 255.0)], 1o
// 3e. Wall[startPoint=Point(255.0, 245.0), endPoint=Point(500.0, 245.0)], 6o
// 4e. Wall[startPoint=Point(255.0, 245.0), endPoint=Point(255.0, 255.0)], 5o
// 5e. Wall[startPoint=Point(245.0, 245.0), endPoint=Point(245.0, 255.0)], 2o
// 6e. Wall[startPoint=Point(0.0, 245.0), endPoint=Point(245.0, 245.0)]]
// ]

// obtained
// Board[width=500, height=500, walls=[
// 1o. Wall[startPoint=Point(500.0, 255.0), endPoint=Point(255.0, 255.0)],
// 2o. Wall[startPoint=Point(245.0, 245.0), endPoint=Point(245.0, 255.0)],
// 3o. Wall[startPoint=Point(0.0, 255.0), endPoint=Point(245.0, 255.0)],
// 4o. Wall[startPoint=Point(0.0, 255.0), endPoint=Point(245.0, 245.0)], wrong one
// 5o. Wall[startPoint=Point(255.0, 245.0), endPoint=Point(255.0, 255.0)],
// 6o. Wall[startPoint=Point(500.0, 245.0), endPoint=Point(255.0, 245.0)]]
// ]
