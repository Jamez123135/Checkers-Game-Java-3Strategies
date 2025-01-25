package upei.project;

import java.util.HashSet;
import java.util.Set;

/**
 * The Game class manages the overall flow of the Checkers game.
 * It handles the board, players, and turn logic.
 */
public class Game {
    private int movesWithoutCapture = 0; // Tracks consecutive non-capture moves
    private final int maxMovesWithoutCapture = 50; // Example threshold
    private final Set<String> boardHistory = new HashSet<>();


    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer; // Tracks whose turn it is.

    /**
     * Constructor for the Game class.
     *
     * @param player1 the first player.
     * @param player2 the second player.
     */
    public Game(Player player1, Player player2) {
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1; // Black starts first in Checkers.
    }

    /**
     * Switches the turn to the next player.
     */
    private void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Plays a single turn of the game.
     */
    public void playTurn() {
        System.out.println(currentPlayer.getColor() + "'s turn!");

        // Decide the move using the current player's strategy
        Move move = currentPlayer.decideMove(board);

        if (move == null) {
            System.out.println("No valid moves available. Turn skipped.");
            return;
        }

        // Execute the move on the board
        board.executeMove(move);

        // Update the counter for moves without capture
        if (move.isCapture()) {
            movesWithoutCapture = 0; // Reset counter
        } else {
            movesWithoutCapture++;
        }

        // Print the board state after the move
        board.printBoard();

        // Check for draw conditions
        if (isDraw()) {
            System.out.println("The game ends in a draw.");
            System.exit(0); // End the game immediately
        }

        // Switch to the next player
        switchTurn();
    }


    /**
     * Starts the game and manages the turn sequence.
     */
    public void startGame() {
        System.out.println("Starting the game...");
        board.printBoard();

        while (!isGameOver()) { // Continue until the game is over
            playTurn();
        }

        // Determine the winner
        determineWinner();
    }
    /**
     * Determines and announces the winner.
     */
    /**
     * Determines the winner of the game.
     *
     * @return "Player 1", "Player 2", or "Draw" based on the game's outcome.
     */
    public String determineWinner() {
        int player1Pieces = countPieces(player1);
        int player2Pieces = countPieces(player2);

        if (player1Pieces > player2Pieces) {
            return "Player 1";
        } else if (player2Pieces > player1Pieces) {
            return "Player 2";
        } else {
            return "Draw";
        }
    }


    /**
     * Counts the number of pieces a player has on the board.
     *
     * @param player the player whose pieces are being counted.
     * @return the number of pieces the player has on the board.
     */
    private int countPieces(Player player) {
        int count = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null && piece.getPlayerColor().equals(player.getColor())) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        // Check if either player has no valid moves or no pieces left
        boolean player1HasMoves = !board.getValidMoves(player1).isEmpty();
        boolean player2HasMoves = !board.getValidMoves(player2).isEmpty();

        if (!player1HasMoves || !player2HasMoves) {
            return true; // Game over if either player cannot make a move
        }

        return false; // Game is not over
    }
    /**
     * Checks if the game is a draw.
     *
     * @return true if the game is a draw, false otherwise.
     */
    private boolean isDraw() {
        // Check if the moves without capture exceed the maximum allowed
        if (movesWithoutCapture >= maxMovesWithoutCapture) {
            System.out.println("Draw due to no captures in " + maxMovesWithoutCapture + " moves.");
            return true;
        }

        // Check for repeated board states
        String currentState = boardStateAsString();
        if (boardHistory.contains(currentState)) {
            System.out.println("Draw due to repeated board state.");
            return true;
        }

        // Add current state to history
        boardHistory.add(currentState);

        return false;
    }
    /**
     * Converts the current board state to a string for comparison.
     *
     * @return a string representing the board state.
     */
    private String boardStateAsString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(row, col);
                sb.append(piece == null ? "." : piece.getSymbol());
            }
        }
        return sb.toString();
    }



}
