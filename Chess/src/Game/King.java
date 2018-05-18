package Game;


import Game.Pieces.MoveType;

public class King extends Piece {

    King(Coordinates coordinates, TeamColor team) {
        super (coordinates, team);
    }

    @Override
    void initMoveDirections() {
        this.moveDirections = new MoveType[][]{
                {MoveType.KILL, MoveType.KILL, MoveType.KILL},
                {MoveType.KILL, MoveType.NONE, MoveType.KILL},
                {MoveType.KILL, MoveType.KILL, MoveType.KILL}};
    }

    @Override
    void initPieceType() {
        this.type = PieceType.KING;

    }
    /*@Override
    protected void updatePossibleMoves() {
        Coordinates id = this.square.getID();
        final int curRank = id.getRank();
        final int curFile = id.getFile();
        final int[] adjust = {-1, 0, 1};
        for (int rankAdjust : adjust) {
            for (int fileAdjust : adjust) {
                addMoveIfLegal(curRank + rankAdjust, curFile + fileAdjust);
            }
        }
    }
    private void addMoveIfLegal(int rank, int file) {
        if (this.board.inBounds(rank) && this.board.inBounds(file)) {
            Coordinates idToCheck = new Coordinates(rank, file);
            Square squareToCheck = board.getSquare(idToCheck);
            if (!board.inCheck(squareToCheck, this.team) &&
                    (!squareToCheck.isOccupied() || squareToCheck.getPiece().getTeam() != this.team)) {
                this.moves.add(idToCheck);
            }
        }
    }*/

}
