package Game;

import java.util.ArrayList;

public class Knight extends Piece {

    Knight(Board board, Board.TeamColor team) {
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;
        this.type = Board.PieceType.KNIGHT;

    }

    @Override
    protected void updatePossibleMoves() {
        moves.clear();
        Coordinates curID = this.square.getID();
        final int curRank = curID.getRank();
        final int curFile = curID.getFile();
        final int[] rankAdjustments = {2, 1, -1, -2};
        final int[] fileAdjustments = {1, 2, 2, 1};
        final int[] directions = {-1, 1};
        for (int i = 0; i < rankAdjustments.length; i++) {
            for (int direction : directions) {
                addMoveIfLegal(curRank + rankAdjustments[i], curFile + fileAdjustments[i] * direction);
            }
        }
    }
    private void addMoveIfLegal(int rank, int file) {
        if (this.board.inBounds(rank) && this.board.inBounds(file)) {
            Coordinates idToCheck = new Coordinates(rank, file);
            Square square = board.getSquare(idToCheck);
            if (!square.isOccupied() || (square.getPiece().getTeam() != this.team)) {
                moves.add(idToCheck);
            }
        }
    }

}
