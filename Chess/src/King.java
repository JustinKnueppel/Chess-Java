import java.util.ArrayList;

public class King extends Piece {

    King(Board board, Board.TeamColor team) {
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;
        this.type = Board.PieceType.KING;

    }
    @Override
    protected void updatePossibleMoves() {
        String id = this.square.getID();
        final int curRank = this.board.LETTERS.indexOf(id.charAt(0));
        final int curFile = this.board.NUMBERS.indexOf(id.charAt(1));
        final int[] adjust = {-1, 0, 1};
        for (int rankAdjust : adjust) {
            for (int fileAdjust : adjust) {
                addMoveIfLegal(curRank + rankAdjust, curFile + fileAdjust);
            }
        }
    }
    protected void addMoveIfLegal(int rank, int file) {
        if (this.board.inBounds(rank) && this.board.inBounds(file)) {
            String idToCheck = board.indexToID(rank, file);
            Square squareToCheck = board.getSquare(idToCheck);
            if (!board.inCheck(squareToCheck, this.team) &&
                    (!squareToCheck.isOccupied() || squareToCheck.getPiece().getTeam() != this.team)) {
                this.moves.add(idToCheck);
            }
        }
    }

}
