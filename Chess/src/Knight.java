import java.util.ArrayList;

public class Knight implements Piece {
    private boolean hasMoved;
    private Square square;
    private Board.TeamColor team;
    private ArrayList<String> moves;
    private Board board;
    private Board.PieceType type;

    Knight(Board board, Board.TeamColor team) {
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;
        this.type = Board.PieceType.KNIGHT;

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
        moves.clear();
        String curID = this.square.getID();
        final int curRank = this.board.LETTERS.indexOf(curID.charAt(0));
        final int curFile = this.board.NUMBERS.indexOf(curID.charAt(1));
        final int[] rankAdjustments = {2, 1, -1, -2};
        final int[] fileAdjustments = {1, 2, 2, 1};
        final int[] directions = {-1, 1};
        for (int i = 0; i < rankAdjustments.length; i++) {
            for (int direction : directions) {
                addMoveIfLegal(curRank + rankAdjustments[i], curFile + fileAdjustments[i] * direction);
            }
        }
    }
    private void addMoveIfLegal(int rank, int file) {
        if (this.board.inBounds(rank) && this.board.inBounds(file)) {
            String idToCheck = board.indexToID(rank, file);
            Square square = board.getSquare(idToCheck);
            if (!square.isOccupied() || (square.getPiece().getTeam() != this.team)) {
                moves.add(idToCheck);
            }
        }
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
