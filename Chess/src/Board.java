public class Board {
    private final int GRID_SIZE = 8;
    private final String LETTERS = "ABCDEFGH";
    private final String NUMBERS = "12345678";
    private Square[][] grid;
    private String[] backRowOrder;

    public Board() {
        backRowOrder = rowOrder();
        initializeGrid();
        initializePieces();
    }
    private void initializeGrid() {

        this.grid = new Square[8][8];
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
    private void initializePieces() {
        final int blackOffset = 7;
        final int whiteBackRow = 0;
        final int whitePawnRow = 1;
        final int blackBackRow = 7;
        final int blackPawnRow = 6;

        for(int column = 0; column < this.GRID_SIZE; column++) {
            this.grid[whiteBackRow][column].putPiece(getPieceByName(this.backRowOrder[column], true));
            this.grid[whitePawnRow][column].putPiece(getPieceByName("Pawn", true));
            this.grid[blackBackRow][column].putPiece(getPieceByName(this.backRowOrder[blackOffset - column], false));
            this.grid[blackPawnRow][column].putPiece(getPieceByName("Pawn", false));
        }


    }
    private String[] rowOrder() {
        return new String[]{"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"};
    }
    private Piece getPieceByName(String name, boolean team) {
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
    public boolean isOccupied(String id) {
        return grid[LETTERS.indexOf(id.charAt(0))][NUMBERS.indexOf(id.charAt(1))] != null;
    }
}