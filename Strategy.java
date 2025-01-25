package upei.project;

/**
 * The Strategy interface defines the behavior of a player's decision-making.
 * Each strategy implements this interface to decide on a move during the player's turn.
 */
public interface Strategy {
    /**
     * Decides a move for the player based on the current state of the board.
     *
     * @param board the current state of the game board.
     * @param player the player making the move.
     * @return a Move object representing the chosen move.
     */
    Move decideMove(Board board, Player player);
}
