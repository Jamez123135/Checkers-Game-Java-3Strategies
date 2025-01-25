package upei.project;

import java.util.ArrayList;
import java.util.List;

/**
 * The Board class represents the 8x8 grid used in the game of Checkers.
 * It handles the initialization of the board, placement of pieces, and
 * provides utility methods to interact with the board.
 */
public class Board {
    // The 8x8 grid where each cell holds a Piece or is null if empty.
    private final Piece[][] grid;

    /**
     * Constructor for the Board class.
     * Initializes an empty 8x8 grid and sets up the initial positions of the pieces.
     */
    public Board() {
        grid = new Piece[8][8]; // Create an 8x8 grid
        initializePieces();     // Place pieces in their starting positions
    }

    /**
     * Initializes the board with pieces in their starting positions.
     * Players' pieces are placed on the first three and last three rows, respectively.
     */
    private void initializePieces() {
        // Loop through the first three rows for Player 1 (Black pieces)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                // Pieces only occupy dark squares (odd indices in even rows, even indices in odd rows)
                if ((row + col) % 2 == 1) {
                    grid[row][col] = new RegularPiece("Black");
                }
            }
        }

        // Loop through the last three rows for Player 2 (White pieces)
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    grid[row][col] = new RegularPiece("White");
                }
            }
        }
    }

    /**
     * Gets the piece at the specified position on the board.
     *
     * @param x the row index (0-7).
     * @param y the column index (0-7).
     * @return the Piece at the specified position, or null if the cell is empty.
     */
    public Piece getPieceAt(int x, int y) {
        // Ensure the coordinates are within the board's bounds
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Coordinates out of bounds.");
        }
        return grid[x][y];
    }

    /**
     * Places a piece at the specified position on the board.
     *
     * @param x the row index (0-7).
     * @param y the column index (0-7).
     * @param piece the Piece to place at the specified position.
     */
    public void placePieceAt(int x, int y, Piece piece) {
        // Ensure the coordinates are within the board's bounds
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Coordinates out of bounds.");
        }
        grid[x][y] = piece;
    }

    /**
     * Removes a piece from the specified position on the board.
     *
     * @param x the row index (0-7).
     * @param y the column index (0-7).
     * @return the Piece that was removed, or null if the cell was already empty.
     */
    public Piece removePieceAt(int x, int y) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Coordinates out of bounds.");
        }
        Piece removedPiece = grid[x][y];
        grid[x][y] = null; // Clear the cell
        return removedPiece;
    }

    /**
     * Prints the current state of the board for debugging purposes.
     */
    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (grid[row][col] == null) {
                    System.out.print(". "); // Empty cell
                } else {
                    System.out.print(grid[row][col].getSymbol() + " ");
                }
            }
            System.out.println(); // Move to the next row
        }
    }
    /**
     * Computes all valid moves for the given player.
     * If any capturing moves are available, only these moves will be returned.
     *
     * @param player the player whose moves are being calculated.
     * @return a list of valid moves for the player.
     */
    public List<Move> getValidMoves(Player player) {
        List<Move> validMoves = new ArrayList<>();
        List<Move> capturingMoves = new ArrayList<>();
        String color = player.getColor();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPieceAt(row, col);
                if (piece != null && piece.getPlayerColor().equals(color)) {
                    // Add all valid moves for this piece
                    for (Move move : getMovesForPiece(row, col)) {
                        if (move.isCapture()) {
                            capturingMoves.add(move);
                        } else {
                            validMoves.add(move);
                        }
                    }
                }
            }
        }
        return !capturingMoves.isEmpty() ? capturingMoves : validMoves; // Prioritize captures
    }




    /**
     * Computes all valid moves for a specific piece on the board.
     *
     * @param startX the row index of the piece.
     * @param startY the column index of the piece.
     * @return a list of valid moves for the piece.
     */
    private List<Move> getMovesForPiece(int startX, int startY) {
        List<Move> moves = new ArrayList<>();
        Piece piece = getPieceAt(startX, startY);

        if (piece == null) {
            return moves; // No piece to move
        }

        if (piece instanceof KingPiece) {
            // King pieces can move in all four diagonal directions
            int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
            for (int[] dir : directions) {
                // Single-step move
                addMoveIfValid(moves, startX, startY, startX + dir[0], startY + dir[1], false);
                // Capture move (jump over an opponent)
                addMoveIfValid(moves, startX, startY, startX + 2 * dir[0], startY + 2 * dir[1], true);
            }
        } else {
            // Regular pieces move in one direction based on their color
            int direction = piece.getPlayerColor().equals("Black") ? 1 : -1; // Black moves down, White moves up

            // Check basic diagonal moves
            addMoveIfValid(moves, startX, startY, startX + direction, startY - 1, false);
            addMoveIfValid(moves, startX, startY, startX + direction, startY + 1, false);

            // Check capturing moves (jump over an opponent)
            addMoveIfValid(moves, startX, startY, startX + 2 * direction, startY - 2, true);
            addMoveIfValid(moves, startX, startY, startX + 2 * direction, startY + 2, true);
        }

        return moves;
    }

    /**
     * Adds a move to the list if it is valid.
     *
     * @param moves the list of valid moves.
     * @param startX the row index of the starting position.
     * @param startY the column index of the starting position.
     * @param endX the row index of the ending position.
     * @param endY the column index of the ending position.
     * @param isCapture whether the move is a capturing move.
     */
    private void addMoveIfValid(List<Move> moves, int startX, int startY, int endX, int endY, boolean isCapture) {
        // Ensure the destination is within bounds
        if (endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            return; // Out of bounds
        }

        // Destination must be empty
        if (getPieceAt(endX, endY) != null) {
            return; // Destination is occupied
        }

        // Check if the move is a capture
        if (isCapture) {
            int midX = (startX + endX) / 2;
            int midY = (startY + endY) / 2;
            Piece midPiece = getPieceAt(midX, midY);

            // Ensure there's an opponent piece to jump over
            Piece startPiece = getPieceAt(startX, startY);
            if (midPiece == null || startPiece == null || midPiece.getPlayerColor().equals(startPiece.getPlayerColor())) {
                return; // No opponent piece to capture
            }
        }

        // Add the move to the list
        moves.add(new Move(startX, startY, endX, endY, isCapture));
    }



    public void executeMove(Move move) {
        // Get the piece being moved
        Piece piece = getPieceAt(move.getStartX(), move.getStartY());

        // Clear the starting position
        grid[move.getStartX()][move.getStartY()] = null;

        // If the move is a capture, remove the captured piece
        if (move.isCapture()) {
            int midX = (move.getStartX() + move.getEndX()) / 2;
            int midY = (move.getStartY() + move.getEndY()) / 2;
            grid[midX][midY] = null; // Remove the captured piece
        }

        // Place the piece at the destination
        grid[move.getEndX()][move.getEndY()] = piece;
    }
    public Board copy() {
        Board newBoard = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = this.getPieceAt(row, col);
                if (piece != null) {
                    newBoard.placePieceAt(row, col, piece.copy());
                } else {
                    newBoard.placePieceAt(row, col, null);
                }
            }
        }
        return newBoard;
    }

    public void applyMove(Move move) {
        Piece piece = this.getPieceAt(move.getStartX(), move.getStartY());
        if (piece != null) {
            this.placePieceAt(move.getEndX(), move.getEndY(), piece);
            this.placePieceAt(move.getStartX(), move.getStartY(), null);

            // Handle capturing
            if (move.isCapture()) {
                int capturedRow = (move.getStartX() + move.getEndX()) / 2;
                int capturedCol = (move.getStartY() + move.getEndY()) / 2;
                this.placePieceAt(capturedRow, capturedCol, null);
            }

            // Promote to King if it reaches the opponent's side
            if (move.getEndX() == 0 && piece.getPlayerColor().equalsIgnoreCase("Black")) {
                this.placePieceAt(move.getEndX(), move.getEndY(), new KingPiece(piece.getPlayerColor()));
            } else if (move.getEndX() == 8 - 1 && piece.getPlayerColor().equalsIgnoreCase("White")) {
                this.placePieceAt(move.getEndX(), move.getEndY(), new KingPiece(piece.getPlayerColor()));
            }
        }
    }
    public int getSize() {
        return 8; // Assuming the board size is stored in a field `size`
    }
    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }







}

