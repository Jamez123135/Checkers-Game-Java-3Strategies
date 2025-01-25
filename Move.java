package upei.project;

import java.util.Objects;

/**
 * The Move class represents a single action a player takes during their turn.
 * It includes the start and end positions, and whether the move is a capture.
 */
public class Move {
    private final int startX; // Row index of the starting position
    private final int startY; // Column index of the starting position
    private final int endX;   // Row index of the ending position
    private final int endY;   // Column index of the ending position
    private final boolean isCapture; // Whether this move involves capturing an opponent's piece

    /**
     * Constructor for the Move class.
     *
     * @param startX the row index of the starting position.
     * @param startY the column index of the starting position.
     * @param endX the row index of the ending position.
     * @param endY the column index of the ending position.
     * @param isCapture whether the move is a capture.
     */
    public Move(int startX, int startY, int endX, int endY, boolean isCapture) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.isCapture = isCapture;
    }

    /**
     * Gets the row index of the starting position.
     *
     * @return the row index of the starting position.
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Gets the column index of the starting position.
     *
     * @return the column index of the starting position.
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Gets the row index of the ending position.
     *
     * @return the row index of the ending position.
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Gets the column index of the ending position.
     *
     * @return the column index of the ending position.
     */
    public int getEndY() {
        return endY;
    }

    /**
     * Checks whether the move is a capture.
     *
     * @return true if the move is a capture, false otherwise.
     */
    public boolean isCapture() {
        return isCapture;
    }

    /**
     * Checks whether the move is risky (simple placeholder for demonstration).
     * A move could be considered risky if it leaves the piece vulnerable.
     *
     * @return true if the move is risky, false otherwise.
     */
    public boolean isRisky() {
        // Placeholder logic: actual risk assessment will depend on the board state.
        return false;
    }

    @Override
    public String toString() {
        return String.format("Move[%d,%d -> %d,%d, capture=%b]",
                startX, startY, endX, endY, isCapture);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return startX == move.startX &&
                startY == move.startY &&
                endX == move.endX &&
                endY == move.endY &&
                isCapture == move.isCapture;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startX, startY, endX, endY, isCapture);
    }



}
