import java.lang.reflect.Array;
import java.util.*;

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
        this.whiteLegalMoves = new ArrayList<>();
        this.blackLegalMoves = new ArrayList<>();

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

        Piece piece = this.board.getSquare(coordinate).getPiece();

        switch (piece.getType()) {
            case KING: {
                int[] directions = new int[]{-1, 0, 1};

                for (int i : directions) {
                    for (int j : directions) {
                        if (i == 0 && j == 0 || !validIndices(startIndices[0] + i, startIndices[1] + j)) {
                            continue;
                        }
                        int newX = startIndices[0] + i;
                        int newY = startIndices[1] + j;
                        Coordinate newCoordinate = Coordinate.fromIndices(newX, newY);
                        Square square = this.board.getSquare(newCoordinate);

                        threatens.add(newCoordinate);
                        if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                            legalMoves.add(new Coordinate[]{coordinate, newCoordinate});
                        }
                    }
                }
                break;
            }
            case PAWN: {
                int direction = piece.getColor() == TeamColor.WHITE ? 1 : -1;

                /*
                 * Forward moves
                 */
                if (validIndices(startIndices[0], startIndices[1] + direction)) {
                    Coordinate oneStepCoordinate = Coordinate.fromIndices(startIndices[0], startIndices[1] + direction);
                    Square oneStep = this.board.getSquare(oneStepCoordinate);
                    if (!oneStep.occupied()) {
                        legalMoves.add(new Coordinate[]{coordinate, oneStepCoordinate});

                        /*
                         * Two steps
                         */
                        if (!piece.hasMoved() && validIndices(startIndices[0], startIndices[1] + direction * 2)) {
                            Coordinate twoStepCoordinate = Coordinate.fromIndices(startIndices[0], startIndices[1] + direction * 2);
                            Square twoStep = this.board.getSquare(twoStepCoordinate);
                            if (!twoStep.occupied()) {
                                legalMoves.add(new Coordinate[]{coordinate, twoStepCoordinate});
                            }
                        }
                    }
                }

                /*
                 * Capturing moves
                 */
                for (int side : new int[]{-1,1}) {
                    int newX = startIndices[0] + side;
                    int newY = startIndices[1] + direction;
                    if (validIndices(newX, newY)) {
                        Coordinate newCoordinate = Coordinate.fromIndices(newX, newY);
                        Square target = this.board.getSquare(newCoordinate);

                        threatens.add(newCoordinate);
                        if (target.occupied() && target.getPiece().getColor() != piece.getColor()) {
                            legalMoves.add(new Coordinate[]{coordinate, newCoordinate});
                        }
                    }
                }
                break;
            }
            case ROOK: {
                int[]  directions = new int[]{-1, 1};

                /*
                 * Vertical moves
                 */
                int newX = startIndices[0];
                for (int direction : directions) {
                    int multiplier = 1;
                    int newY = startIndices[1] + direction * multiplier;

                    while (validIndices(newX, newY)) {
                        Coordinate newCoordinate = Coordinate.fromIndices(newX, newY);
                        Square square = this.board.getSquare(newCoordinate);
                        threatens.add(newCoordinate);

                        if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                            legalMoves.add(new Coordinate[]{coordinate, newCoordinate});
                        }

                        if (square.occupied()) {
                            break;
                        }
                        multiplier++;
                        newY = startIndices[1] + direction * multiplier;
                    }
                }

                /*
                 * Horizontal moves
                 */
                int newY = startIndices[1];
                for (int direction : directions) {
                    int multiplier = 1;
                    newX = startIndices[0] + direction * multiplier;

                    while (validIndices(newX, newY)) {
                        Coordinate newCoordinate = Coordinate.fromIndices(newX, newY);
                        Square square = this.board.getSquare(newCoordinate);
                        threatens.add(newCoordinate);

                        if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                            legalMoves.add(new Coordinate[]{coordinate, newCoordinate});
                        }

                        if (square.occupied()) {
                            break;
                        }
                        multiplier++;
                        newX = startIndices[0] + direction * multiplier;
                    }
                }
                break;
            }
            case QUEEN: {
                int[] directions = new int[]{-1, 0, 1};

                for (int i : directions) {
                    for (int j : directions) {
                        if (i == 0 && j == 0 || !validIndices(startIndices[0] + i, startIndices[1] + j)) {
                            continue;
                        }
                        int multpilier = 1;
                        int newX = startIndices[0] + i;
                        int newY = startIndices[1] + j;
                        while (validIndices(newX, newY)) {
                            Coordinate newCoordinate = Coordinate.fromIndices(newX, newY);
                            Square square = this.board.getSquare(newCoordinate);

                            threatens.add(newCoordinate);
                            if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                                legalMoves.add(new Coordinate[]{coordinate, newCoordinate});
                            }
                            /*
                             * Stop when you hit a piece
                             */
                            if (square.occupied()) {
                                break;
                            }
                            multpilier++;
                            newX = startIndices[0] + i * multpilier;
                            newY = startIndices[1] + j * multpilier;
                        }

                    }
                }
                break;
            }
            case BISHOP: {
                int[] directions = new int[]{-1,1};

                for (int i : directions) {
                    for (int j : directions) {
                        int multiplier = 1;
                        int newX = startIndices[0] + i * multiplier;
                        int newY = startIndices[1] + j * multiplier;

                        while (validIndices(newX, newY)) {
                            Coordinate newCoordinate = Coordinate.fromIndices(newX, newY);
                            Square square = this.board.getSquare(newCoordinate);

                            threatens.add(newCoordinate);

                            if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                                legalMoves.add(new Coordinate[]{coordinate, newCoordinate});
                            }

                            if (square.occupied()) {
                                break;
                            }

                            multiplier++;
                            newX = startIndices[0] + i * multiplier;
                            newY = startIndices[1] + j * multiplier;

                        }
                    }
                }
                break;
            }
            case KNIGHT: {
                int[] oneStep = new int[]{-1, 1};
                int[] twoStep = new int[]{-2, 2};

                for (int i : oneStep) {
                    for (int j: twoStep) {
                        int newX = startIndices[0] + i;
                        int newY = startIndices[1] + j;
                        if (validIndices(newX, newY)) {
                            Coordinate newCoordinate = Coordinate.fromIndices(newX, newY);
                            Square square = this.board.getSquare(newCoordinate);
                            threatens.add(newCoordinate);

                            if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                                legalMoves.add(new Coordinate[]{coordinate, newCoordinate});
                            }
                        }

                        newX = startIndices[0] + j;
                        newY = startIndices[1] + i;
                        if (validIndices(newX, newY)) {
                            Coordinate newCoordinate = Coordinate.fromIndices(newX, newY);
                            Square square = this.board.getSquare(newCoordinate);
                            threatens.add(newCoordinate);

                            if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                                legalMoves.add(new Coordinate[]{coordinate, newCoordinate});
                            }
                        }

                    }
                }
                break;
            }


        }
        if (piece.getColor() == TeamColor.WHITE) {
            this.whiteThreatens.addAll(threatens);
            this.whiteLegalMoves.addAll(legalMoves);
        } else {
            this.blackThreatens.addAll(threatens);
            this.blackLegalMoves.addAll(legalMoves);
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
        updateMoves();
        TeamColor team = this.board.getSquare(start).getPiece().getColor();
        ArrayList<Coordinate[]> legalMoves = team.equals(TeamColor.WHITE) ? this.whiteLegalMoves : this.blackLegalMoves;
        Set<Coordinate> threatens = team.equals(TeamColor.WHITE) ? this.whiteThreatens : this.blackThreatens;

        return legalMoves.stream().anyMatch(a -> Arrays.equals(a, new Coordinate[] {start, end}));
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
