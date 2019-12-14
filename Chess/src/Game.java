import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Game {
    private Board board;
    private Set<Coordinate> whiteThreatens;
    private Set<Coordinate> blackThreatens;
    private ArrayList<Coordinate[]> whiteLegalMoves;
    private ArrayList<Coordinate[]> blackLegalMoves;

    public Game() {
        this.board = new Board();
        this.whiteThreatens = new HashSet<>();
        this.blackThreatens = new HashSet<>();

        setPieces();
    }

    private void setPieces() {
        final PieceType[] backRow = new PieceType[]{PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK};

        for (int file = 0; file < Board.GRID_SIZE; file++) {
            for (int rank : new int[]{0, 7}) {
                TeamColor team = rank == 0 ? TeamColor.WHITE : TeamColor.BLACK;
                Coordinate coordinate = Coordinate.fromIndices(file, rank);
                this.board.getSquare(coordinate).setPiece(new Piece(backRow[file], team));
            }
        }

        for (int file = 0; file < Board.GRID_SIZE; file++) {
            for (int rank : new int[]{1, 6}) {
                TeamColor team = rank == 1 ? TeamColor.WHITE : TeamColor.BLACK;
                Coordinate coordinate = Coordinate.fromIndices(file, rank);
                this.board.getSquare(coordinate).setPiece(new Piece(PieceType.PAWN, team));
            }
        }
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

    private boolean validIndices(int x, int y) {
        return x >= 0 && x < Board.GRID_SIZE && y >= 0 && y < Board.GRID_SIZE;
    }

    void updateThreatsMoves(Coordinate coordinate) {
        Set<Coordinate> threatens = new HashSet<>();
        ArrayList<Coordinate[]> legalMoves = new ArrayList<>();

        int[] startIndices = coordinate.toIndices();
        int[] directions = new int[]{-1, 0, 1};

        Piece piece = this.board.getSquare(coordinate).getPiece();

        //TODO: Add move logic to find threatened squares
        switch (piece.getType()) {
            case KING: {
                for (int i : directions) {
                    for (int j : directions) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        int newX = startIndices[0] + i;
                        int newY = startIndices[1] + j;
                        Square square = this.board.getSquare(Coordinate.fromIndices(newX, newY));

                        threatens.add(Coordinate.fromIndices(newX, newY));
                        if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                            legalMoves.add(new Coordinate[]{coordinate, Coordinate.fromIndices(newX, newY)});
                        }
                    }
                }
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
        if (piece.getColor() == TeamColor.WHITE) {
            whiteThreatens.addAll(threatens);
        } else {
            blackThreatens.addAll(threatens);
        }
    }

    /**
     * Update all squares that each team threatens and all legal moves.
     */
    void updateMoves() {
        whiteThreatens.clear();
        blackThreatens.clear();
        whiteLegalMoves.clear();
        blackLegalMoves.clear();

        Square square;
        for (Coordinate coordinate : EnumSet.allOf(Coordinate.class)) {
            square = board.getSquare(coordinate);
            if (square.occupied()) {
                updateThreatsMoves(coordinate);
            }
        }
    }

    /*
     * ==================
     * = Public methods =
     * ==================
     */

    public Board getBoard() {
        return this.board;
    }

    /**
     * Determine if move is legal.
     * @param start
     *      Starting coordinate of the moving piece
     * @param end
     *      Ending coordinate of the moving piece
     * @return true iff the move is legal
     */
    public boolean isLegalMove(Coordinate start, Coordinate end) {
        return true;
    }

    /**
     * Make the desired move in the model.
     * @param start
     *      Starting coordinate of the moving piece
     * @param end
     *      Ending coordinate of the moving piece
     */
    public void makeMove(Coordinate start, Coordinate end) {
        Square startSquare = this.board.getSquare(start);
        Square endSquare = this.board.getSquare(end);

        endSquare.setPiece(startSquare.getPiece());
        startSquare.removePiece();
        endSquare.getPiece().setMoved(true);
    }

    /**
     * Determine if checkmate has been delivered.
     * @param teamColor
     *      Team which has [not] delivered checkmate
     * @return true iff @teamColor has delivered checkmate
     */
    public boolean checkmate(TeamColor teamColor) {
        return false;
    }

    /**
     * Determine if stalemate has been forced.
     * @param teamColor
     *      Team which has [not] forced stalemate
     * @return true iff @teamColor has forced stalemate
     */
    public boolean stalemate(TeamColor teamColor) {
        return false;
    }

}
