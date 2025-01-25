package upei.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testBoardInitialization() {
        Board board = new Board();

        // Check that Black pieces are in their starting positions
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    assertNotNull(board.getPieceAt(row, col));
                    assertEquals('B', board.getPieceAt(row, col).getSymbol());
                } else {
                    assertNull(board.getPieceAt(row, col));
                }
            }
        }

        // Check that White pieces are in their starting positions
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    assertNotNull(board.getPieceAt(row, col));
                    assertEquals('W', board.getPieceAt(row, col).getSymbol());
                } else {
                    assertNull(board.getPieceAt(row, col));
                }
            }
        }

        // Ensure middle rows are empty
        for (int row = 3; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                assertNull(board.getPieceAt(row, col));
            }
        }
    }

    @Test
    void testPlacePieceAt() {
        Board board = new Board();
        Piece customPiece = new RegularPiece("Black");
        board.placePieceAt(4, 4, customPiece);

        assertNotNull(board.getPieceAt(4, 4));
        assertEquals(customPiece, board.getPieceAt(4, 4));
    }

    @Test
    void testRemovePieceAt() {
        Board board = new Board();
        Piece removedPiece = board.removePieceAt(0, 1);

        assertNotNull(removedPiece); // Ensure a piece was removed
        assertNull(board.getPieceAt(0, 1)); // Cell should now be empty
    }

    @Test
    void testOutOfBounds() {
        Board board = new Board();

        assertThrows(IllegalArgumentException.class, () -> board.getPieceAt(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> board.placePieceAt(8, 0, new RegularPiece("White")));
    }
}
