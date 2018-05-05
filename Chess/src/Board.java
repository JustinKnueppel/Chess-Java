public class Board {
    private final String LETTERS = "ABCDEFGH";
    private final String NUMBERS = "12345678";
    private Square[][] grid;
    private String[] backRowOrder;

    public Board() {
        backRowOrder = rowOrder();
        this.grid = InitializeBoard();
        //InitializePieces(grid);
    }
    private Square[][] InitializeBoard() {

        Square[][] board = new Square[8][8];
        for (int i = 0; i < LETTERS.length(); i++) {
            for (int k = 0; k < NUMBERS.length(); k++) {
                String temp = "";
                temp += LETTERS.charAt(i) + NUMBERS.charAt(k);
                board[i][k] = new Square(temp);
            }
        }
        return board;
    }
    private void initializePieces() {
        int whiteRow = 0;
        int blackRow = 7;
        for(int column = 0; column < this.NUMBERS.length(); column++) {

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
                System.out.println("Piece can not be initialized by String");
                break;
        }
    }
}