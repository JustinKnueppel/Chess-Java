public class Piece {
    private PieceType type;
    private TeamColor color;

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
}
