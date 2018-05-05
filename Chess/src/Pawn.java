import java.util.ArrayList;

public class Pawn implements Piece {
    private boolean hasMoved;
    private Square id;
    private boolean team;
    private ArrayList<String> moves;
    private Board board;

    public Pawn(Board board, Square id, boolean team) {
        this.id = id;
        this.team = team;
        this.hasMoved = false;
        this.moves = new ArrayList<>();
        this.board = board;

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
    public void updatePossibleMoves() {

    }

    @Override
    public ArrayList<String> getPossibleMoves() {
        return this.moves;

    }


}
