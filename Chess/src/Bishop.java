import java.util.ArrayList;

public class Bishop implements Piece {
    private boolean hasMoved;
    private Square square;
    private Board.TeamColor team;
    private ArrayList<String> moves;
    private Board board;
    private Board.PieceType type;

    Bishop(Board board, Board.TeamColor team) {
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;
        this.type = Board.PieceType.BISHOP;

    }
    @Override
    public Board.TeamColor getTeam() {
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
        this.moves.clear();
        String curID = this.square.getID();
        final int curRank = this.board.LETTERS.indexOf(curID.charAt(0));
        final int curFile = this.board.NUMBERS.indexOf(curID.charAt(1));
        final int[] movements = {-1, 1};
        for (int rankAdjustment:movements) {
            for (int fileAdjustment:movements) {
                boolean checkSquare = true;
                int multiplier = 1;
                while (checkSquare) {
                    checkSquare = addMoveIfLegal(curRank + rankAdjustment * multiplier, curFile + fileAdjustment * multiplier);
                    multiplier++;
                }
            }
        }
    }
    private boolean addMoveIfLegal(int rank, int file) {
        boolean allowNext = false;
        if (this.board.inBounds(rank) && this.board.inBounds(file)){
            String idToCheck = this.board.indexToID(rank, file);
            Square square = this.board.getSquare(idToCheck);
            allowNext = square.isOccupied();
            if (!square.isOccupied() || square.getPiece().getTeam() != this.team) {
                this.moves.add(idToCheck);
            }
        }
        return allowNext;
    }

    @Override
    public ArrayList<String> getPossibleMoves() {
        updatePossibleMoves();
        return this.moves;

    }
    @Override
    public Board.PieceType getType(){
        return this.type;
    }
}
