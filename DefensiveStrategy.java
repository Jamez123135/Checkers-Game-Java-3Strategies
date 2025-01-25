package upei.project;

import java.util.List;

/**
 * DefensiveStrategy focuses on minimizing risks for the player's pieces.
 * It prioritizes capturing moves and moves that keep the player's pieces safe from being captured.
 */
public class DefensiveStrategy implements Strategy {

    /**
     * Decides the best defensive move for the player.
     *
     * @param board  The current state of the board.
     * @param player The current player.
     * @return The move that minimizes the risk for the player or null if no valid moves exist.
     */
    @Override
    public Move decideMove(Board board, Player player) {
        // Step 1: Get all valid moves for the player
        List<Move> validMoves = board.getValidMoves(player);

        if (validMoves.isEmpty()) {
            return null; // No valid moves available
        }

        // Step 2: Check for capturing moves
        Move bestCaptureMove = getBestCaptureMove(validMoves, board, player);
        if (bestCaptureMove != null) {
            return bestCaptureMove; // Prioritize captures
        }

        // Step 3: Evaluate risk for non-capturing moves
        return getSafestMove(validMoves, board, player);
    }

    /**
     * Finds the best capture move among the valid moves.
     * A "best" capture is one that minimizes the risk after the move.
     *
     * @param validMoves List of valid moves for the player.
     * @param board The current board state.
     * @param player The current player.
     * @return The best capture move, or null if no captures are available.
     */
    private Move getBestCaptureMove(List<Move> validMoves, Board board, Player player) {
        Move bestCaptureMove = null;
        int minimumRisk = Integer.MAX_VALUE;

        for (Move move : validMoves) {
            if (move.isCapture()) {
                // Simulate the board state after the capture
                Board simulatedBoard = board.copy();
                simulatedBoard.executeMove(move);

                // Calculate the risk of the resulting board state
                int risk = calculateRisk(simulatedBoard, player);
                if (risk < minimumRisk) {
                    minimumRisk = risk;
                    bestCaptureMove = move;
                }
            }
        }
        return bestCaptureMove;
    }

    /**
     * Finds the safest non-capturing move by minimizing risk.
     *
     * @param validMoves List of valid moves for the player.
     * @param board The current board state.
     * @param player The current player.
     * @return The safest move, or null if no valid moves exist.
     */
    private Move getSafestMove(List<Move> validMoves, Board board, Player player) {
        Move safestMove = null;
        int minimumRisk = Integer.MAX_VALUE;

        for (Move move : validMoves) {
            // Simulate the move on a copy of the board
            Board simulatedBoard = board.copy();
            simulatedBoard.executeMove(move);

            // Calculate the risk for the simulated board state
            int risk = calculateRisk(simulatedBoard, player);

            // Keep track of the move with the least risk
            if (risk < minimumRisk) {
                minimumRisk = risk;
                safestMove = move;
            }
        }
        return safestMove;
    }

    /**
     * Calculates the risk of the board state for the given player.
     *
     * @param board  The simulated board after a move.
     * @param player The player making the move.
     * @return An integer representing the risk level (lower is better).
     */
    private int calculateRisk(Board board, Player player) {
        int risk = 0;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null && piece.getPlayerColor().equals(player.getColor())) {
                    // Add risk based on the number of opponent threats
                    int threats = countOpponentThreats(board, row, col, player);
                    risk += threats;
                }
            }
        }

        return risk;
    }

    /**
     * Counts the number of opponent pieces that threaten a given position.
     *
     * @param board  The board state.
     * @param row    The row of the position being evaluated.
     * @param col    The column of the position being evaluated.
     * @param player The player owning the piece at the given position.
     * @return The number of opponent pieces that can potentially capture the piece.
     */
    private int countOpponentThreats(Board board, int row, int col, Player player) {
        int threats = 0;

        String opponentColor = player.getColor().equals("Black") ? "White" : "Black";
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] dir : directions) {
            int opponentRow = row + dir[0];
            int opponentCol = col + dir[1];

            if (board.isWithinBounds(opponentRow, opponentCol)) {
                Piece opponentPiece = board.getPieceAt(opponentRow, opponentCol);
                if (opponentPiece != null && opponentPiece.getPlayerColor().equals(opponentColor)) {
                    int jumpRow = opponentRow + dir[0];
                    int jumpCol = opponentCol + dir[1];
                    if (board.isWithinBounds(jumpRow, jumpCol) && board.getPieceAt(jumpRow, jumpCol) == null) {
                        threats++;
                    }
                }
            }
        }

        return threats;
    }
}
