package Game.Pieces;

import Game.Coordinates;
import Game.TeamColor;

public class Pawn extends Piece {

    public Pawn(Coordinates coordinates, TeamColor team) {
        super(coordinates, team);
    }

    @Override
    void initMoveDirections() {
        int direction = team == TeamColor.WHITE ? 1 : -1;
        this.possibleMoves = new int[][]{{-1, direction}, {0, direction}, {0, direction * 2}, {1, direction}};
    }

    @Override
    void initPieceType() {
        this.type = PieceType.PAWN;
    }


}
