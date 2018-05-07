import java.util.ArrayList;

public class Bishop implements Piece {
    private boolean hasMoved;
    private Square square;
    private boolean team;
    private ArrayList<String> moves;
    private Board board;
    private String type;

    Bishop(Board board, boolean team) {
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;
        this.type = "Bishop";

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
