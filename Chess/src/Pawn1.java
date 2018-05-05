import java.util.ArrayList;

public class Pawn1 implements Pawn{
    private boolean hasMoved;
    private Square id;
    private boolean team;

    public Pawn1(Square id, boolean team) {
        this.id = id;
        this.team = team;
        this.hasMoved = false;

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
    }



    @Override
    public void updatePossibleMoves() {

    }

    @Override
    public ArrayList<String> getPossibleMoves() {
        return new String[0];

    }


}
