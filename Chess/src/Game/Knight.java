package Game;


import Game.Pieces.MoveType;

public class Knight extends Piece {

    Knight(Coordinates coordinates, TeamColor team) {
        super(coordinates, team);
    }

    @Override
    void initMoveDirections() {
        this.possibleMoves = new int[][]{{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
    }

    @Override
    void initPieceType() {
        this.type = PieceType.KNIGHT;

    }
/*@Override
    protected void updatePossibleMoves() {
        moves.clear();
        Coordinates curID = this.square.getID();
        final int curRank = curID.getX();
        final int curFile = curID.getY();
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
    }*/

}
