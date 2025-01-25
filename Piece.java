package upei.project;

/**
 * Abstract class representing a generic piece in the game.
 * Subclasses will implement specific behavior for regular and king pieces.
 */
import java.util.List;

public abstract class Piece {
    protected String playerColor; // Color of the piece ("Black" or "White")

    public Piece(String playerColor) {
        this.playerColor = playerColor;
    }

    // Common method to get the player's color
    public String getPlayerColor() {
        return this.playerColor;
    }

    // Abstract method for getting valid moves
    public abstract List<Move> getValidMoves(Board board, int row, int col);

    // Abstract method for creating a copy of the piece
    public abstract Piece copy();
    public abstract char getSymbol();
}
