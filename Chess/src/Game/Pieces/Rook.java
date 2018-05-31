package Game.Pieces;

import Game.Coordinates;
import Game.TeamColor;

public class Rook extends Piece {

    public Rook(Coordinates coordinates, TeamColor team) {
        super(coordinates, team);
    }

    @Override
    void initMoveDirections() {
        this.possibleMoves = new int[][]{{0, 1}, {-1, 0}, {1, 0}, {0, -1}};
    }

    @Override
    void initPieceType() {
        this.type = PieceType.ROOK;
    }

}
