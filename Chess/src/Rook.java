import java.util.ArrayList;

public class Rook implements Piece {
    private boolean hasMoved;
    private Square id;
    private boolean team;
    private ArrayList<String> moves;
    private Board board;
    private String type;

    public Rook(Board board, boolean team) {
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;
        this.type = "Rook";

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
        this.id = newSquare;
        this.hasMoved = true;
    }
    @Override
    public void setSquare(Square square) {
        this.id = square;
    }



    @Override
    public void updatePossibleMoves() {

    }

    @Override
    public ArrayList<String> getPossibleMoves() {
        return this.moves;

    }
    @Override
    public String getType(){
        return this.type;
    }
}
