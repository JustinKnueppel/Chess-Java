public class Board {
    /*
    Declare useful constants and private variables
     */
    public final int GRID_SIZE = 8;

    private Square[][] grid;
    public enum TeamColor {WHITE, BLACK};
    public enum PieceType {PAWN, KING, KNIGHT, BISHOP, ROOK, QUEEN};
    private PieceType[] backRowOrder;

    /**
     * Create GRID_SIZE rank GRID_SIZE sized board and place pieces on it.
     */
    public Board() {
        backRowOrder = new PieceType[]{PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
                PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK};
        initializeGrid();
        initializePieces();
    }

    /**
     * Initializes this.grid with ID'd squares in the style of A1, B2 etc.
     */
    private void initializeGrid() {
        this.grid = new Square[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                grid[row][column] = new Square(new Coordinates(row, column));
            }
        }
    }

    /**
     * Place the correct pieces on the chess board according to this.backRowOrder and a second rank of pawns.
     */
    private void initializePieces() {
        final int blackOffset = 7;
        final int whiteBackRow = 0;
        final int whitePawnRow = 1;
        final int blackBackRow = 7;
        final int blackPawnRow = 6;

        for(int column = 0; column < this.GRID_SIZE; column++) {
            /*
            Place white pieces
             */
            this.grid[whiteBackRow][column].putPiece(getPieceByName(this.backRowOrder[column], TeamColor.WHITE));
            this.grid[whitePawnRow][column].putPiece(getPieceByName(PieceType.PAWN, TeamColor.WHITE));
            /*
            Place black team
             */
            this.grid[blackBackRow][column].putPiece(getPieceByName(this.backRowOrder[blackOffset - column], TeamColor.BLACK));
            this.grid[blackPawnRow][column].putPiece(getPieceByName(PieceType.PAWN, TeamColor.BLACK));
        }

    }

    /**
     * Based on a PieceType, return an initialized object of the correct implementation.
     * @param type
     *      The type of the piece
     * @param team
     *      The team to which the piece belongs for initialization
     * @return a specific implementation of Piece based on name
     */
    private Piece getPieceByName(PieceType type, TeamColor team) {
        Piece piece;
        switch (type) {
            case ROOK:
                piece = new Rook(this, team);
                break;
            case KNIGHT:
                piece = new Knight(this, team);
                break;
            case BISHOP:
                piece = new Bishop(this, team);
                break;
            case QUEEN:
                piece = new  Queen(this, team);
                break;
            case KING:
                piece = new King(this, team);
                break;
            case PAWN:
                piece = new Pawn(this, team);
                break;
            default:
                System.out.println("Piece could not be initialized");
                piece = null;
                break;
        }
        return piece;
    }


    /**
     * Given a String ID, return the corresponding Square.
     * @param id
     *      The ID in grid
     * @return the Square with the given ID
     */
    Square getSquare(Coordinates id) {
        return grid[id.getRank()][id.getFile()];
    }

    /**
     * Determines whether or not the given position falls in bounds of the grid.
     * @param pos
     *      position to check
     * @return true iff 0 <= pos < this.GRID_SIZE
     */
    public boolean inBounds(int pos) {
        return pos >= 0 && pos < this.GRID_SIZE;
    }



    /**
     * Determines whether the given square is threatened by the opposing team.
     * @param square
     *      Target square to check
     * @param team
     *      Team attempting to move to the given square
     * @return true iff !team is threatening square
     */
    public boolean inCheck(Square square, TeamColor team) {
        //TODO: figure out efficient way to check each person's possible moves.
        return false;
    }
}