package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class AggressiveStrategyTest {

    @Test
    void testAggressiveStrategyPrioritizesCapture() {
        Board board = new Board();
        AggressiveStrategy strategy = new AggressiveStrategy();
        Player blackPlayer = new Player("Black", strategy);

        // Set up a scenario with a capture opportunity
        board.placePieceAt(3, 3, new RegularPiece("Black"));
        board.placePieceAt(4, 4, new RegularPiece("White")); // Opponent piece to be captured

        // Valid moves for the Black piece
        Move chosenMove = strategy.decideMove(board, blackPlayer);

        // Expected move is a capture to (5, 5)
        assertNotNull(chosenMove, "AggressiveStrategy should choose a move.");
        assertTrue(chosenMove.isCapture(), "AggressiveStrategy should prioritize capture moves.");
        assertEquals(5, chosenMove.getEndX(), "The end row should indicate a capture move.");
        assertEquals(5, chosenMove.getEndY(), "The end column should indicate a capture move.");
    }



    @Test
    void testAggressiveStrategyChoosesAdvancingMoveWhenNoCapture() {
        Board board = new Board();
        AggressiveStrategy strategy = new AggressiveStrategy();
        Player blackPlayer = new Player("Black", strategy);

        // Set up a scenario with no capture opportunities
        board.placePieceAt(3, 3, new RegularPiece("Black"));

        // Valid advancing move is (4, 4)
        Move chosenMove = strategy.decideMove(board, blackPlayer);

        assertNotNull(chosenMove, "AggressiveStrategy should choose a move.");
        assertFalse(chosenMove.isCapture(), "AggressiveStrategy should not choose a capture move if none are available.");
        assertEquals(4, chosenMove.getEndX(), "The move should advance the piece.");
        assertEquals(4, chosenMove.getEndY(), "The move should advance the piece diagonally.");
    }
    @Test
    void testAggressiveStrategyAdvancesTowardsPromotion() {
        Board board = new Board();
        AggressiveStrategy strategy = new AggressiveStrategy();
        Player blackPlayer = new Player("Black", strategy);

        // Setup: Black has a piece at (3, 3) and can advance towards promotion
        board.placePieceAt(3, 3, new RegularPiece("Black"));

        // Execute: Get the move chosen by AggressiveStrategy
        Move chosenMove = strategy.decideMove(board, blackPlayer);

        // Validation: Ensure the piece moves towards promotion (higher row for Black)
        assertNotNull(chosenMove, "AggressiveStrategy should choose a move.");
        assertEquals(4, chosenMove.getEndX(), "The move should advance the piece.");
        assertEquals(4, chosenMove.getEndY(), "The move should advance the piece diagonally.");
    }




}
