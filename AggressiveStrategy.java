package upei.project;

import java.util.List;

/**
 * AggressiveStrategy focuses on capturing opponent pieces and advancing towards promotion.
 * It prioritizes moves that result in captures and targets the highest-value pieces.
 */
public class AggressiveStrategy implements Strategy {

    /**
     * Decides the most aggressive move for the player.
     *
     * @param board  The current state of the board.
     * @param player The current player.
     * @return The move that maximizes aggression, or null if no valid moves exist.
     */
    @Override
    public Move decideMove(Board board, Player player) {
        List<Move> validMoves = board.getValidMoves(player);
        if (validMoves.isEmpty()) {
            return null; // No valid moves
        }

        Move bestMove = null;
        int highestValue = 0;

        for (Move move : validMoves) {
            // Simulate the board state after the move
            Board simulatedBoard = board.copy();
            simulatedBoard.executeMove(move);

            // Calculate the value of this move
            int moveValue = calculateMoveValue(simulatedBoard, move, player);

            // Debugging: Log move evaluation
            System.out.println("Move: " + move + " Value: " + moveValue);

            if (moveValue > highestValue) {
                highestValue = moveValue;
                bestMove = move;
            } else if (moveValue == highestValue) {
                // Tie-breaker: Prefer advancing moves
                if (bestMove == null || isAdvancing(move, player)) {
                    bestMove = move;
                }
            }
        }

        // Debugging: Log the chosen move
        System.out.println("Chosen Move: " + bestMove + " with Value: " + highestValue);

        return bestMove;
    }




    /**
     * Calculates the value of a move based on its impact.
     * Captures add value, with Kings being worth more than regular pieces.
     *
     * @param board  The simulated board state.
     * @param move   The move being evaluated.
     * @param player The player making the move.
     * @return The value of the move.
     */
    private int calculateMoveValue(Board board, Move move, Player player) {
        int value = 0;

        // If the move is a capture
        if (move.isCapture()) {
            int midX = (move.getStartX() + move.getEndX()) / 2;
            int midY = (move.getStartY() + move.getEndY()) / 2;

            // Get the piece being captured
            Piece capturedPiece = board.getPieceAt(midX, midY);

            if (capturedPiece != null) {
                // Debugging: Log the captured piece
                System.out.println("Captured Piece: " + capturedPiece.getClass().getSimpleName());

                // Assign value based on the type of captured piece
                // King pieces are worth 3 points, Regular pieces are worth 1 point
                value += (capturedPiece instanceof KingPiece) ? 3 : 1;
            }
        }

        return value;
    }




    /**
     * Determines if a move advances a piece closer to promotion.
     *
     * @param move   The move being evaluated.
     * @param player The player making the move.
     * @return true if the move advances the piece, false otherwise.
     */
    private boolean isAdvancing(Move move, Player player) {
        // Black pieces advance towards higher row indices; White towards lower row indices
        if (player.getColor().equals("Black")) {
            return move.getEndX() > move.getStartX();
        } else {
            return move.getEndX() < move.getStartX();
        }
    }
}
