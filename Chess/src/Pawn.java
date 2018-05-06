import java.util.ArrayList;

public class Pawn implements Piece {
    private boolean hasMoved;
    private Square square;
    private boolean team;
    private ArrayList<String> moves;
    private Board board;
    private String type;

    Pawn(Board board, boolean team) {
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;
        this.type = "Pawn";

    }

    @Override
    public boolean hasMoved() {
        return this.hasMoved;
    }

    @Override
    public boolean getTeam() {
        return this.team;
    }

    @Override
    public void move(Square newSquare) {
        this.square.setVacant();
        this.square = newSquare;
        this.hasMoved = true;
    }
    @Override
    public void setSquare(Square square) {
        this.square = square;
    }



    @Override
    public void updatePossibleMoves() {
        final int direction = (this.team) ? 1 : -1;
        this.moves.clear();
        String id = this.square.getId();
        int nextRank = board.LETTERS.indexOf(id.charAt(0)) + direction;
        if (board.inBounds(nextRank)){
            StringBuilder idBuilder = new StringBuilder();
            /*
            Check directly in front of the piece
             */
            idBuilder.append(board.LETTERS.charAt(nextRank));
            idBuilder.append(id.charAt(1));
            String idToCheck = idBuilder.toString();
            if(!board.getSquare(idToCheck).isOccupied()) {
                this.moves.add(idToCheck);
                /*
                Check if moving twice is possible
                 */
                if(!this.hasMoved && board.inBounds(nextRank + direction)) {
                    idBuilder.replace(0, 1, Character.toString(board.LETTERS.charAt(nextRank + direction)));
                    idToCheck = idBuilder.toString();
                    if (!board.getSquare(idToCheck).isOccupied()) {
                        this.moves.add(idToCheck);
                    }
                }
            }
            /*
            Check front left
             */
            int leftFile = board.NUMBERS.indexOf(id.charAt(1)) - direction;
            if (board.inBounds(leftFile)) {
                idBuilder.replace(0, 1, Character.toString(board.LETTERS.charAt(nextRank)));
                idBuilder.replace(1, 2, Character.toString(board.NUMBERS.charAt(leftFile)));
                idToCheck = idBuilder.toString();
                if (!board.getSquare(idToCheck).isOccupied()) {
                    moves.add(idToCheck);
                }
            }
            /*
            Check front right
             */
            int rightFile = board.NUMBERS.indexOf((id.charAt(1)) + direction);
            if (board.inBounds(rightFile)){
                idBuilder.replace(0, 1, Character.toString(board.LETTERS.charAt(nextRank)));
                idBuilder.replace(1, 2, Character.toString(board.NUMBERS.charAt(rightFile)));
                idToCheck = idBuilder.toString();
                if (!board.getSquare(idToCheck).isOccupied()) {
                    moves.add(idToCheck);
                }
            }

        }
    }

    @Override
    public ArrayList<String> getPossibleMoves() {
        return this.moves;

    }
    @Override
    public String getType() {
        return this.type;
    }


}
