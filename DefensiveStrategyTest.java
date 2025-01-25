package upei.project;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefensiveStrategyTest {

    @Test
    void testDefensiveStrategy() {
        // Initialize the board
        Board board = new Board();

        // Initialize players with the defensive strategy
        Player player1 = new Player("Black", new DefensiveStrategy());

        // Clear the board for custom setup
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.removePieceAt(row, col);
            }
        }

        // Setup a test scenario
        board.placePieceAt(3, 3, new RegularPiece("Black")); // Player 1's piece
        board.placePieceAt(2, 2, new RegularPiece("White")); // Player 2's piece
        board.placePieceAt(2, 4, new RegularPiece("White")); // Player 2's piece

        // Player 1 uses DefensiveStrategy
        DefensiveStrategy defensiveStrategy = new DefensiveStrategy();
        Move move = defensiveStrategy.decideMove(board, player1);

        // Print calculated risks for each move
        System.out.println("Chosen Move: Start(" + move.getStartX() + ", " + move.getStartY() +
                ") -> End(" + move.getEndX() + ", " + move.getEndY() + ")");

        // Validate the move
        assertNotNull(move, "A move should be selected.");
        assertEquals(3, move.getStartX(), "The start row should be 3.");
        assertEquals(3, move.getStartY(), "The start column should be 3.");

        // Print risks for debugging
        System.out.println("Expected safer position: End(4, 4). Actual safer position: End(" +
                move.getEndX() + ", " + move.getEndY() + ").");
    }



    @Test
    void testDefensiveStrategyWithNoValidMoves() {
        // Initialize the board
        Board board = new Board();

        // Initialize players
        Player player1 = new Player("Black", new DefensiveStrategy());
        Player player2 = new Player("White", new DefensiveStrategy());

        // Clear the board for custom setup
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.removePieceAt(row, col);
            }
        }

        // Setup a test scenario: Player 1 has no pieces on the board
        DefensiveStrategy defensiveStrategy = new DefensiveStrategy();
        Move move = defensiveStrategy.decideMove(board, player1);

        // Validate that no move is chosen
        assertEquals(null, move, "No move should be chosen when there are no valid moves.");
    }
    @Test
    void testDefensiveStrategySingleMove() {
        // Initialize the board
        Board board = new Board();

        // Initialize a player with the defensive strategy
        Player player1 = new Player("Black", new DefensiveStrategy());

        // Clear the board for custom setup
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.removePieceAt(row, col);
            }
        }

        // Place a single piece for Player 1
        board.placePieceAt(3, 3, new RegularPiece("Black"));

        // Call the defensive strategy
        Move move = player1.decideMove(board);

        // Assert the piece can move to one of its valid positions
        assertEquals(3, move.getStartX(), "The starting row should be 3.");
        assertEquals(3, move.getStartY(), "The starting column should be 3.");
        assertEquals(4, move.getEndX(), "The ending row should be 4.");
        assertEquals(2, move.getEndY(), "The ending column should be 2.");
    }

    @Test
    void testDefensiveStrategyNoMoves() {
        // Initialize the board
        Board board = new Board();

        // Initialize a player with the defensive strategy
        Player player1 = new Player("Black", new DefensiveStrategy());

        // Clear the board for custom setup
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.removePieceAt(row, col);
            }
        }

        // Player 1 has no pieces, so no valid moves
        Move move = player1.decideMove(board);

        // Assert no move is chosen
        assertNull(move, "No move should be chosen when there are no pieces.");
    }
    @Test
    void testDefensiveStrategyMovesToThreatFreePosition() {
        // Initialize the board
        Board board = new Board();

        // Initialize players with the defensive strategy
        Player player1 = new Player("Black", new DefensiveStrategy());
        Player player2 = new Player("White", new DefensiveStrategy());

        // Clear the board for custom setup
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.removePieceAt(row, col);
            }
        }

        // Setup a test scenario
        board.placePieceAt(3, 3, new RegularPiece("Black")); // Player 1's piece
        board.placePieceAt(2, 2, new RegularPiece("White")); // Threat from top-left
        board.placePieceAt(2, 4, new RegularPiece("White")); // Threat from top-right

        // Player 1 uses DefensiveStrategy
        DefensiveStrategy defensiveStrategy = new DefensiveStrategy();
        Move move = defensiveStrategy.decideMove(board, player1);

        // Print the chosen move for debugging
        System.out.println("Chosen Move: Start(" + move.getStartX() + ", " + move.getStartY() +
                ") -> End(" + move.getEndX() + ", " + move.getEndY() + ")");

        // Validate the chosen move
        // In this setup, moving to (4, 2) or (4, 4) would avoid all threats.
        // Assuming the DefensiveStrategy calculates the risk correctly:
        assertEquals(3, move.getStartX(), "The start row should be 3.");
        assertEquals(3, move.getStartY(), "The start column should be 3.");
        assertTrue(
                (move.getEndX() == 4 && move.getEndY() == 2) || (move.getEndX() == 4 && move.getEndY() == 4),
                "The move should result in a position with a threat of 0."
        );
    }



}
