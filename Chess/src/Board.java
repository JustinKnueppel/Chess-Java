public class Board {
    public Board() {
        String[][] grid = InitializeBoard();
        //InitializePieces(grid);
    }
    private String[][] InitializeBoard() {
        final String LETTERS = "ABCDEFGH";
        final String NUMBERS = "12345678";
        String[][] board = new String[8][8];
        for (int i = 0; i < LETTERS.length(); i++) {
            for (int k = 0; k < NUMBERS.length(); k++) {
                String temp = "";
                temp += LETTERS.charAt(i) + NUMBERS.charAt(k);
                board[i][k] = temp;
            }
        }
        return board;
    }
}