public class Board {
    /*
    Declare useful constants and private variables
     */
    public final int GRID_SIZE = 8;
    public final String LETTERS = "ABCDEFGH";
    public final String NUMBERS = "12345678";
    private Square[][] grid;
    private String[] backRowOrder;
    public enum TeamColor {WHITE, BLACK};
    public enum PieceType {PAWN, KING, KNIGHT, BISHOP, ROOK, QUEEN};

    /**
     * Create GRID_SIZE x GRID_SIZE sized board and place pieces on it.
     */
    public Board() {
        backRowOrder = new String[]{"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"};
        initializeGrid();
        initializePieces();
    }

    /**
     * Initializes this.grid with ID'd squares in the style of A1, B2 etc.
     */
    private void initializeGrid() {
        this.grid = new Square[GRID_SIZE][GRID_SIZE];
        StringBuilder id = new StringBuilder();
        for (int row = 0; row < LETTERS.length(); row++) {
            for (int column = 0; column < NUMBERS.length(); column++) {
                id.append(LETTERS.charAt(row));
                id.append(NUMBERS.charAt(column));
                grid[row][column] = new Square(id.toString());
                id.delete(0,id.length());
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
            this.grid[whitePawnRow][column].putPiece(getPieceByName("Pawn", TeamColor.WHITE));
            /*
            Place black team
             */
            this.grid[blackBackRow][column].putPiece(getPieceByName(this.backRowOrder[blackOffset - column], TeamColor.BLACK));
            this.grid[blackPawnRow][column].putPiece(getPieceByName("Pawn", TeamColor.BLACK));
        }

    }

    /**
     * Based on a String representation of the type of piece, return an initialized object of the correct implementation.
     * @param name
     *      The pragmatic name of the piece
     * @param team
     *      The team to which the piece belongs for initialization
     * @return a specific implementation of Piece based on name
     */
    private Piece getPieceByName(String name, TeamColor team) {
        Piece piece;
        switch (name) {
            case "Rook":
                piece = new Rook(this, team);
                break;
            case "Knight":
                piece = new Knight(this, team);
                break;
            case "Bishop":
                piece = new Bishop(this, team);
                break;
            case "Queen":
                piece = new  Queen(this, team);
                break;
            case "King":
                piece = new King(this, team);
                break;
            case "Pawn":
                piece = new Pawn(this, team);
                break;
            default:
                System.out.println("Piece could not be initialized by String");
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
    Square getSquare(String id) {
        return grid[LETTERS.indexOf(id.charAt(0))][NUMBERS.indexOf(id.charAt(1))];
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
     * Takes two indices to LETTERS and NUMBERS and returns the String ID.
     * @param x
     *      Index for LETTERS
     * @param y
     *      Index for NUMBERS
     * @return String ID based on the two indices
     */
    public String indexToID(int x, int y) {
        StringBuilder idBuilder = new StringBuilder();
        idBuilder.append(LETTERS.charAt(x));
        idBuilder.append(NUMBERS.charAt(y));
        return idBuilder.toString();
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