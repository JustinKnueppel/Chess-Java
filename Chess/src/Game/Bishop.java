package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Bishop extends Piece {

    Bishop(Board board, Board.TeamColor team) {
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;
        this.type = Board.PieceType.BISHOP;
        this.images.put(Board.TeamColor.WHITE, new ImageView(PRE_IMAGE + "whiteBishop.png"));
        this.images.put(Board.TeamColor.BLACK, new ImageView(PRE_IMAGE + "blackBishop.png"));
    }
    @Override
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
    }

}
