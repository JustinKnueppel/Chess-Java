package chess.view;

import chess.core.*;

public class Controller {
    private final View view;
    private final Game model;
    private int moveCounter;

    public Controller(Game model, View view) {
        this.view = view;
        this.model = model;
        this.moveCounter = 0;
        updateBoard();
    }

    private void checkIfGameOver() {
        TeamColor team = this.moveCounter % 2 == 0 ? TeamColor.WHITE : TeamColor.BLACK;

        if (this.model.checkmate(team)) {
            TeamColor winner = this.moveCounter % 2 == 0 ? TeamColor.BLACK : TeamColor.WHITE;

            System.out.printf("GAME OVER: %s WON!\n", winner);
        } else if (this.model.stalemate(team)) {
            System.out.println("GAME OVER: STALEMATE");
        }
    }

    private void updateBoard() {
        for (int file = 0; file < Board.GRID_SIZE; file++) {
            for (int rank = 0; rank < Board.GRID_SIZE; rank++) {
                Square square = this.model.getBoard().getSquare(Coordinate.fromIndices(file, rank));
                if (square.occupied()) {
                    this.view.placePiece(file, Board.GRID_SIZE - rank - 1, square.getPiece());
                }
            }
        }
    }

    /*
     * ======================
     * == Public interface ==
     * ======================
     */

    public void processMove(int startX, int viewStartY, int endX, int viewEndY) {
        int startY = Board.GRID_SIZE - 1 - viewStartY;
        int endY = Board.GRID_SIZE - 1 - viewEndY;
        System.out.printf("Start x: %d, start y: %d%n", startX, startY);
        System.out.printf("End x: %d, end y: %d%n", endX, endY);

        if (!this.model.validIndices(endX, endY)) {
            this.updateBoard();
            return;
        }

        /* Convert to coordinates */
        Coordinate start = Coordinate.fromIndices(startX, startY);
        Coordinate end = Coordinate.fromIndices(endX, endY);

        TeamColor whoseMove = moveCounter % 2 == 0 ? TeamColor.WHITE : TeamColor.BLACK;
        TeamColor pieceColor = this.model.getBoard().getSquare(start).getPiece().getColor();
        /* Check for move legality */
        if (pieceColor == whoseMove && !start.equals(end) && this.model.isLegalMove(start, end)) {
            System.out.println("Move is legal");
            /* Make move */
            this.model.makeMove(Coordinate.fromIndices(startX, startY), Coordinate.fromIndices(endX, endY));
            System.out.println("Pawn promoted? " + this.model.pawnPromoted());
            if (this.model.pawnPromoted()) {
                this.view.displayPromotionOptions(whoseMove);
            }
            moveCounter++;

        } else {
            /* Reset illegal move */
            System.out.println("Move is not legal");

        }

        if (!this.model.pawnPromoted()) {
            this.view.clearBoard();
            this.updateBoard();

            this.checkIfGameOver();
        }

    }

    public void processPromotion(PieceType pieceType) {
        this.model.promotePawn(pieceType);

        this.view.disablePromotionOptions();

        this.view.clearBoard();
        this.updateBoard();

        this.checkIfGameOver();
    }
}
