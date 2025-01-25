package upei.project;

import java.util.ArrayList;
import java.util.List;

public class KingPiece extends Piece {

    public KingPiece(String playerColor) {
        super(playerColor);
    }

    @Override
    public List<Move> getValidMoves(Board board, int row, int col) {
        List<Move> validMoves = new ArrayList<>();

        // King pieces can move in all diagonal directions
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            // Regular move (empty destination)
            if (board.isWithinBounds(newRow, newCol) && board.getPieceAt(newRow, newCol) == null) {
                validMoves.add(new Move(row, col, newRow, newCol, false));
            }

            // Capture move
            int captureRow = row + 2 * direction[0];
            int captureCol = col + 2 * direction[1];
            int middleRow = row + direction[0];
            int middleCol = col + direction[1];
            if (board.isWithinBounds(captureRow, captureCol)
                    && board.getPieceAt(middleRow, middleCol) != null
                    && !board.getPieceAt(middleRow, middleCol).getPlayerColor().equals(this.playerColor)
                    && board.getPieceAt(captureRow, captureCol) == null) {
                validMoves.add(new Move(row, col, captureRow, captureCol, true));
            }
        }

        return validMoves;
    }

    @Override
    public Piece copy() {
        return new KingPiece(this.playerColor);
    }
    @Override
    public char getSymbol() {
        return playerColor.equalsIgnoreCase("Black") ? 'K' : 'Q';
    }
}
