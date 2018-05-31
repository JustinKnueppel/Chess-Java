package Game.Pieces;


import Game.Coordinates;
import Game.TeamColor;

public class King extends Piece {

    public King(Coordinates coordinates, TeamColor team) {
        super (coordinates, team);
    }

    @Override
    void initMoveDirections() {
        this.possibleMoves = new int[][]{{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {1, 0}, {-1, -1}, {0, -1}, {-1, 1}};
    }

    @Override
    void initPieceType() {
        this.type = PieceType.KING;

    }

}
