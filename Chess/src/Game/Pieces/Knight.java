package Game.Pieces;


import Game.Coordinates;
import Game.TeamColor;

public class Knight extends Piece {

    public Knight(Coordinates coordinates, TeamColor team) {
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

}
