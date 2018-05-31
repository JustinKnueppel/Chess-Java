package Game.Pieces;


import Game.Coordinates;
import Game.TeamColor;

public class Bishop extends Piece {

    public Bishop(Coordinates coordinates, TeamColor team) {
       super (coordinates, team);

    }

    @Override
    void initMoveDirections() {
        this.possibleMoves = new int[][]{{-1, 1}, {1, 1}, {-1, -1}, {-1, 1}};
    }

    @Override
    void initPieceType() {
        this.type = PieceType.BISHOP;
    }

}
