package upei.project;
import java.util.ArrayList;
import java.util.List;
/**
 * The Player class represents a player in the Checkers game.
 * It manages the player's color and strategy.
 */
public class Player {
    private final String color; // The color of the player's pieces ("Black" or "White").
    private final Strategy strategy; // The strategy used by the player.

    /**
     * Constructor for the Player class.
     *
     * @param color the color of the player's pieces ("Black" or "White").
     * @param strategy the strategy used by the player.
     */
    public Player(String color, Strategy strategy) {
        if (!color.equals("Black") && !color.equals("White")) {
            throw new IllegalArgumentException("Invalid color. Must be 'Black' or 'White'.");
        }
        this.color = color;
        this.strategy = strategy;
    }

    /**
     * Gets the color of the player's pieces.
     *
     * @return the color of the player's pieces.
     */
    public String getColor() {
        return color;
    }

    /**
     * Decides a move based on the player's strategy.
     *
     * @param board the current state of the board.
     * @return a Move object representing the chosen move.
     */
    public Move decideMove(Board board) {
        return strategy.decideMove(board, this);
    }
    public List<Move> getAllValidMoves(Board board) {
        List<Move> validMoves = new ArrayList<>();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null && piece.getPlayerColor().equalsIgnoreCase(this.color)) {
                    validMoves.addAll(piece.getValidMoves(board, row, col));
                }
            }
        }
        return validMoves;
    }
    public boolean isBlack() {
        return this.color.equalsIgnoreCase("Black");
    }

}
