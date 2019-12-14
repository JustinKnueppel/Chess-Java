import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Game {
    private Board board;
    private Set<Coordinate> whiteThreatens;
    private Set<Coordinate> blackThreatens;

    public Game() {
        this.board = new Board();
        this.whiteThreatens = new HashSet<>();
        this.blackThreatens = new HashSet<>();
    }

    /**
     * Determine if coordinate is threatened.
     * @param coordinate
     *      Square of interest
     * @param teamColor
     *      Team that is [not] threatening the target square
     * @return
     *      true iff @teamColor is threatening the square at coordinate @coordinate
     */
    boolean isAttacked(Coordinate coordinate, TeamColor teamColor) {
        Set<Coordinate> squares = teamColor == TeamColor.WHITE ? whiteThreatens : blackThreatens;

        return squares.contains(coordinate);
    }

    void addThreats(Square square) {
        Set<Coordinate> threatens = new HashSet<>();

        //TODO: Add move logic to find threatened squares
        switch (square.getPiece().getType()) {
            case KING: {
                break;
            }
            case PAWN: {
                break;
            }
            case ROOK: {
                break;
            }
            case QUEEN: {
                break;
            }
            case BISHOP: {
                break;
            }
            case KNIGHT: {
                break;
            }


        }
        if (square.getPiece().getColor() == TeamColor.WHITE) {
            whiteThreatens.addAll(threatens);
        } else {
            blackThreatens.addAll(threatens);
        }
    }

    /**
     * Update all squares that each team threatens.
     */
    void updateThreatenedSquares() {
        whiteThreatens.clear();
        blackThreatens.clear();

        Square square;
        for (Coordinate coordinate : EnumSet.allOf(Coordinate.class)) {
            square = board.getSquare(coordinate);
            if (square.occupied()) {
                addThreats(square);
            }
        }
    }
}
