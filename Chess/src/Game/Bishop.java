package Game;


import Game.Pieces.MoveType;

public class Bishop extends Piece {

    Bishop(Coordinates coordinates, TeamColor team) {
       super (coordinates, team);

    }

    @Override
    void initMoveDirections() {
        this.moveDirections = new MoveType[][]{
                {MoveType.KILL, MoveType.NONE, MoveType.KILL},
                {MoveType.NONE, MoveType.NONE, MoveType.NONE},
                {MoveType.KILL, MoveType.NONE, MoveType.KILL}};
    }

    @Override
    void initPieceType() {
        this.type = PieceType.BISHOP;
    }
    /*@Override
    protected void updatePossibleMoves() {
        this.moves.clear();
        Coordinates curID = this.square.getID();
        final int curRank = curID.getRank();
        final int curFile = curID.getFile();
        final int[] movements = {-1, 1};
        for (int rankAdjustment:movements) {
            for (int fileAdjustment:movements) {
                boolean checkSquare = true;
                int multiplier = 1;
                while (checkSquare) {
                    checkSquare = addMoveIfLegal(curRank + rankAdjustment * multiplier, curFile + fileAdjustment * multiplier);
                    multiplier++;
                }
            }
        }
    }
    private boolean addMoveIfLegal(int rank, int file) {
        boolean allowNext = false;
        if (this.board.inBounds(rank) && this.board.inBounds(file)){
            Coordinates idToCheck = new Coordinates(rank, file);
            Square square = this.board.getSquare(idToCheck);
            allowNext = square.isOccupied();
            if (!square.isOccupied() || square.getPiece().getTeam() != this.team) {
                this.moves.add(idToCheck);
            }
        }
        return allowNext;
    }*/

}
