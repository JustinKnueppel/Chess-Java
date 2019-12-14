public class Piece {
    private PieceType type;
    private TeamColor color;
    private boolean moved;

    public Piece(PieceType type, TeamColor color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public TeamColor getColor() {
        return color;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
