package chess.core;

import java.util.EnumSet;

public class Board {
    public static final int GRID_SIZE = 8;
    private Square[][] board;

    public Board() {
        this.board = createBoard();
    }

    /**
     * Create empty board.
     * @return empty board
     */
    private Square[][] createBoard() {
        Square[][] board = new Square[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                board[i][j] = new Square();
            }
        }
        return board;
    }

    /**
     * Get the square in this.board corresponding to the given coordinate.
     * @param coordinate
     *      chess.core.Coordinate of the target square
     * @return a 2-tuple with the file and rank indices of the target square
     */
    private int[] convertCoordinate(Coordinate coordinate) {
        final String fileOrder = "ABCDEFGH";
        final String rankOrder = "12345678";

        int file = fileOrder.indexOf(coordinate.toString().charAt(0));
        int rank = rankOrder.indexOf(coordinate.toString().charAt(1));

        return new int[]{file, rank};
    }

    /**
     * Get deep copy of this.
     * @return
     *      Copy of @this
     */
    public Board copy() {
        Board copy = new Board();

        for (Coordinate coordinate : EnumSet.allOf(Coordinate.class)) {
            Square oldSquare = this.getSquare(coordinate);
            if (oldSquare.occupied()) {
                copy.getSquare(coordinate).setPiece(oldSquare.getPiece().copy());
            }
        }

        return copy;
    }

    /**
     * Retrieve the square at the given coordinate.
     * @param coordinate
     *      chess.core.Coordinate of the target square
     * @return the target square
     */
    public Square getSquare(Coordinate coordinate) {
        int[] indices = convertCoordinate(coordinate);
        return this.board[indices[0]][indices[1]];
    }
}
