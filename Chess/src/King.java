import java.util.ArrayList;

public class King implements Piece {
    private boolean hasMoved;
    private Square square;
    private boolean team;
    private ArrayList<String> moves;
    private Board board;
    private String type;

    King(Board board, boolean team) {
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;
        this.type = "King";

    }

    @Override
    public boolean getTeam() {
        return this.team;
    }

    @Override
    public void move(Square newSquare) {
        this.square = newSquare;
        this.hasMoved = true;
    }
    @Override
    public void setSquare(Square square) {
        this.square = square;
    }


    private void updatePossibleMoves() {
        String id = this.square.getID();
        final int curRank = this.board.LETTERS.indexOf(id.charAt(0));
        final int curFile = this.board.NUMBERS.indexOf(id.charAt(1));
        final int[] adjust = {-1, 0, 1};
        for (int rankAdjust: adjust) {
            for(int fileAdjust: adjust) {
                addMoveIfLegal(curRank + rankAdjust, curFile + fileAdjust);
            }
        }
    }
    private void addMoveIfLegal(int rank, int file) {
        if (this.board.inBounds(rank) && this.board.inBounds(file)) {
            String idToCheck = board.indexToID(rank, file);
            Square squareToCheck = board.getSquare(idToCheck);
            if (!board.inCheck(squareToCheck, this.team) &&
                    (!squareToCheck.isOccupied() || squareToCheck.getPiece().getTeam() != this.team)) {
                this.moves.add(idToCheck);
            }
        }
    }

    @Override
    public ArrayList<String> getPossibleMoves() {
        updatePossibleMoves();
        return this.moves;

    }
    @Override
    public String getType(){
        return this.type;
    }
}
