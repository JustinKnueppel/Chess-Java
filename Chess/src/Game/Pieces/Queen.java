package Game.Pieces;


import Game.Coordinates;
import Game.TeamColor;

public class Queen extends Piece {

    public Queen(Coordinates coordinates, TeamColor team) {
        super(coordinates, team);
    }

    @Override
    void initMoveDirections() {
        this.possibleMoves = new int[][]{{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {1, 0}, {-1, -1}, {0, -1}, {-1, 1}};
    }

    @Override
    void initPieceType() {
        this.type = PieceType.QUEEN;
    }
/*@Override
    protected void updatePossibleMoves() {
        this.moves.clear();
        Coordinates curID = this.square.getID();
        final int curRank = curID.getX();
        final int curFile = curID.getY();
        int[][] directions = {{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0, 1}, {-1, -1}, {-1, 0}, {-1, 1}};
        for(int[] adjustments : directions) {
            boolean checkSquare = true;
            int multiplier = 1;
            while (checkSquare) {
                checkSquare = addMoveIfLegal(curRank + adjustments[0] * multiplier, curFile + adjustments[1] * multiplier);
                multiplier++;
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
