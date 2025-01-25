package upei.project;


import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the RandomStrategy class.
 */
public class RandomStrategyTest {

    @Test
    void testRandomStrategyNoMovesAvailable() {
        Board board = new Board();
        RandomStrategy strategy = new RandomStrategy();
        Player blackPlayer = new Player("Black", strategy);

        // No pieces for Black on the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.removePieceAt(row, col); // Remove all pieces
            }
        }

        Move chosenMove = strategy.decideMove(board, blackPlayer);

        assertNull(chosenMove, "RandomStrategy should return null when no moves are available.");
    }
    @Test
    void testRandomStrategyMultipleValidMoves() {
        Board board = new Board();
        RandomStrategy strategy = new RandomStrategy();
        Player blackPlayer = new Player("Black", strategy);

        // Setup multiple valid moves for Black player
        board.placePieceAt(3, 3, new RegularPiece("Black"));
        board.placePieceAt(2, 2, new RegularPiece("White"));
        board.placePieceAt(4, 4, new RegularPiece("White"));

        // Get valid moves for the Black player
        List<Move> validMoves = board.getValidMoves(blackPlayer);
        Move chosenMove = strategy.decideMove(board, blackPlayer);

        // Ensure the chosen move is one of the valid moves
        assertNotNull(chosenMove, "RandomStrategy should choose a valid move.");
        assertTrue(validMoves.contains(chosenMove), "The chosen move should be in the list of valid moves.");

        // Print the moves for debugging
        System.out.println("Valid Moves: ");
        for (Move move : validMoves) {
            System.out.println(move);
        }
        System.out.println("Chosen Move: " + chosenMove);
    }



    @Test
    public void testRandomStrategyDoesNotModifyBoard() {
        Board board = new Board();

        // Clear the board and place a piece
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.placePieceAt(row, col, null);
            }
        }
        board.placePieceAt(2, 3, new RegularPiece("Black"));
        board.placePieceAt(3, 4, null);

        Player player = new Player("Black", new RandomStrategy());

        // Clone the board state before the move
        String boardBeforeMove = board.toString();

        // Act
        player.decideMove(board);

        // Assert
        assertEquals(boardBeforeMove, board.toString(), "RandomStrategy should not modify the board state.");
    }









}
