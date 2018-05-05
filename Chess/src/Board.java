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
        int[] teamRows = {0, 7};
        for(int column = 0; column < this.NUMBERS.length(); column++) {

        }

    }
    private String[] rowOrder() {
        return new String[]{"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"};
    }
}