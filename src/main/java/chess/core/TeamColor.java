package chess.core;

public enum TeamColor {
    WHITE, BLACK;

    @Override
    public String toString() {
        return switch(this) {
            case BLACK -> "Black";
            case WHITE -> "White";
        };
    }
}
