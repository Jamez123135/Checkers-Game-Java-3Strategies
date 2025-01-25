package upei.project;

import java.util.List;
import java.util.Random;

/**
 * RandomStrategy selects a random valid move from the player's available moves.
 */
public class RandomStrategy implements Strategy {

    private final Random random = new Random();

    /**
     * Decides a random move for the player.
     *
     * @param board  The current state of the board.
     * @param player The current player.
     * @return A random valid move for the player or null if no valid moves exist.
     */
    @Override
    public Move decideMove(Board board, Player player) {
        List<Move> validMoves = board.getValidMoves(player);

        // Print valid moves for debugging
        System.out.println("Valid Moves: ");
        for (Move move : validMoves) {
            System.out.println(move);
        }

        if (validMoves == null || validMoves.isEmpty()) {
            return null;
        }

        // Randomly select a valid move
        int randomIndex = random.nextInt(validMoves.size());
        Move chosenMove = validMoves.get(randomIndex);

        System.out.println("Chosen Move: " + chosenMove);

        return chosenMove; // Randomly select a valid move
    }
}
