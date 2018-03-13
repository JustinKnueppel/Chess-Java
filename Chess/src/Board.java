public class Board {
    public Board() {
        Square[][] grid = InitializeBoard();
        //InitializePieces(grid);
    }
    private Square[][] InitializeBoard() {
        final String LETTERS = "ABCDEFGH";
        final String NUMBERS = "12345678";
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
}