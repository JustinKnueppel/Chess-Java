package chess.core;

public class Board {
    public static final int GRID_SIZE = 8;
    private final Square[][] board;

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
     * Get deep copy of this.
     * @return
     *      Copy of @this
     */
    public Board copy() {
        Board copy = new Board();

        for (Coordinate coordinate : Coordinate.all()) {
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
     *      chess.core.Coordinate2 of the target square
     * @return the target square
     */
    public Square getSquare(Coordinate coordinate) {
        return this.board[coordinate.getFile().toIndex()][coordinate.getRank().toIndex()];
    }
}
