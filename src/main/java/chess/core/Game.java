package chess.core;

import java.util.*;

public class Game {
    private class Move {
        private final Coordinate from;
        private final Coordinate to;
        Move(Coordinate from, Coordinate to) {
            this.from = from;
            this.to = to;
        }

        public Coordinate getFrom() {
            return from;
        }

        public Coordinate getTo() {
            return to;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Move move)) {
                return false;
            }
            return this.from.equals(move.getFrom()) &&
                    this.to.equals(move.getTo());
        }
    }
    private final Board board;
    private Coordinate enPassantCoordinate;
    private boolean pawnPromoted;
    private final Set<Coordinate> whiteThreatens;
    private final Set<Coordinate> blackThreatens;
    private final ArrayList<Move> whiteLegalMoves;
    private final ArrayList<Move> blackLegalMoves;

    public Game() {
        this.board = new Board();
        this.whiteThreatens = new HashSet<>();
        this.blackThreatens = new HashSet<>();
        this.whiteLegalMoves = new ArrayList<>();
        this.blackLegalMoves = new ArrayList<>();

        setPieces();
    }

    private Game(Board board) {
        this.board = board;
        this.whiteThreatens = new HashSet<>();
        this.blackThreatens = new HashSet<>();
        this.whiteLegalMoves = new ArrayList<>();
        this.blackLegalMoves = new ArrayList<>();
    }

    /**
     * Get the king position for the given team.
     * @param team
     *      The team whose king is in question
     * @return
     *      The coordinates of @team's king
     */
    private Coordinate getKingCoordinate(TeamColor team) {
        for (Coordinate coordinate : Coordinate.all()) {
            Square square = board.getSquare(coordinate);
            if (square.occupied() && square.getPiece().getType().equals(PieceType.KING) && square.getPiece().getColor().equals(team)) {
                return coordinate;
            }
        }

        return null;
    }

    private void setPieces() {
        final PieceType[] backRow = new PieceType[]{PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK};

        for (Coordinate.File file : Coordinate.File.values()) {
            // Place pieces
            for (Coordinate.Rank rank : new Coordinate.Rank[]{Coordinate.Rank.ONE, Coordinate.Rank.EIGHT}) {
                TeamColor team = rank == Coordinate.Rank.ONE ? TeamColor.WHITE : TeamColor.BLACK;
                this.board.getSquare(new Coordinate(file, rank)).setPiece(new Piece(backRow[file.toIndex()], team));
            }

            // Place pawns
            for (Coordinate.Rank rank : new Coordinate.Rank[]{Coordinate.Rank.TWO, Coordinate.Rank.SEVEN}) {
                TeamColor team = rank == Coordinate.Rank.TWO ? TeamColor.WHITE : TeamColor.BLACK;
                this.board.getSquare(new Coordinate(file, rank)).setPiece(new Piece(PieceType.PAWN, team));
            }
        }
    }

    /**
     * Determine if coordinate is threatened.
     * @param coordinate
     *      chess.core.Square of interest
     * @param teamColor
     *      Team that is [not] threatening the target square
     * @return
     *      true iff @teamColor is threatening the square at coordinate @coordinate
     */
    boolean isNotAttacked(Coordinate coordinate, TeamColor teamColor) {
        Set<Coordinate> squares = teamColor == TeamColor.WHITE ? whiteThreatens : blackThreatens;

        return !squares.contains(coordinate);
    }

    /**
     * Determine if @team is in check.
     * @param team
     *      The team in question
     * @return
     *      true iff @team is in check
     */
    private boolean inCheck(TeamColor team) {
        Set<Coordinate> threatens = team.equals(TeamColor.WHITE) ? this.blackThreatens : this.whiteThreatens;
        Coordinate kingCoordinate = this.getKingCoordinate(team);

        return threatens.contains(kingCoordinate);
    }

    public boolean validIndices(int x, int y) {
        return x >= 0 && x < Board.GRID_SIZE && y >= 0 && y < Board.GRID_SIZE;
    }

    void updateThreatsMoves(Coordinate coordinate) {
        Set<Coordinate> threatens = new HashSet<>();
        ArrayList<Move> legalMoves = new ArrayList<>();

        int[] startIndices = new int[]{coordinate.getFile().toIndex(), coordinate.getRank().toIndex()};

        Piece piece = this.board.getSquare(coordinate).getPiece();

        switch (piece.getType()) {
            case KING -> {
                int[] directions = new int[]{-1, 0, 1};

                for (int i : directions) {
                    for (int j : directions) {
                        if (i == 0 && j == 0 || !validIndices(startIndices[0] + i, startIndices[1] + j)) {
                            continue;
                        }
                        int newX = startIndices[0] + i;
                        int newY = startIndices[1] + j;
                        Coordinate newCoordinate = new Coordinate(Coordinate.File.from(newX), Coordinate.Rank.from(newY));
                        Square square = this.board.getSquare(newCoordinate);

                        threatens.add(newCoordinate);
                        if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                            legalMoves.add(new Move(coordinate, newCoordinate));
                        }
                    }
                }

                /* Check for castling */
                TeamColor team = piece.getColor().equals(TeamColor.WHITE) ? TeamColor.WHITE : TeamColor.BLACK;
                if (piece.hasNotMoved() && !inCheck(team)) {
                    TeamColor oppositeTeam = piece.getColor().equals(TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;

                    if (team.equals(TeamColor.WHITE)) {
                        /* White kingside castles */
                        if (!this.board.getSquare(new Coordinate(Coordinate.File.F, Coordinate.Rank.ONE)).occupied() &&
                                isNotAttacked(new Coordinate(Coordinate.File.F, Coordinate.Rank.ONE), oppositeTeam) &&
                                !this.board.getSquare(new Coordinate(Coordinate.File.G, Coordinate.Rank.ONE)).occupied() &&
                                isNotAttacked(new Coordinate(Coordinate.File.G, Coordinate.Rank.ONE), oppositeTeam) &&
                                this.board.getSquare(new Coordinate(Coordinate.File.H, Coordinate.Rank.ONE)).occupied() &&
                                this.board.getSquare(new Coordinate(Coordinate.File.H, Coordinate.Rank.ONE)).getPiece().hasNotMoved()) {
                            legalMoves.add(new Move(coordinate, new Coordinate(Coordinate.File.G, Coordinate.Rank.ONE)));
                        }

                        /* White queenside castles */
                        if (!this.board.getSquare(new Coordinate(Coordinate.File.D, Coordinate.Rank.ONE)).occupied() &&
                                isNotAttacked(new Coordinate(Coordinate.File.D, Coordinate.Rank.ONE), oppositeTeam) &&
                                !this.board.getSquare(new Coordinate(Coordinate.File.C, Coordinate.Rank.ONE)).occupied() &&
                                isNotAttacked(new Coordinate(Coordinate.File.C, Coordinate.Rank.ONE), oppositeTeam) &&
                                !this.board.getSquare(new Coordinate(Coordinate.File.B, Coordinate.Rank.ONE)).occupied() &&
                                this.board.getSquare(new Coordinate(Coordinate.File.A, Coordinate.Rank.ONE)).occupied() &&
                                this.board.getSquare(new Coordinate(Coordinate.File.A, Coordinate.Rank.ONE)).getPiece().hasNotMoved()) {
                            legalMoves.add(new Move(coordinate, new Coordinate(Coordinate.File.C, Coordinate.Rank.ONE)));
                        }
                    } else {
                        /* Black kingside castles */
                        if (!this.board.getSquare(new Coordinate(Coordinate.File.F, Coordinate.Rank.EIGHT)).occupied() &&
                                isNotAttacked(new Coordinate(Coordinate.File.F, Coordinate.Rank.EIGHT), oppositeTeam) &&
                                !this.board.getSquare(new Coordinate(Coordinate.File.G, Coordinate.Rank.EIGHT)).occupied() &&
                                isNotAttacked(new Coordinate(Coordinate.File.G, Coordinate.Rank.EIGHT), oppositeTeam) &&
                                this.board.getSquare(new Coordinate(Coordinate.File.H, Coordinate.Rank.EIGHT)).occupied() &&
                                this.board.getSquare(new Coordinate(Coordinate.File.H, Coordinate.Rank.EIGHT)).getPiece().hasNotMoved()) {
                            legalMoves.add(new Move(coordinate, new Coordinate(Coordinate.File.G, Coordinate.Rank.EIGHT)));
                        }

                        /* White queenside castles */
                        if (!this.board.getSquare(new Coordinate(Coordinate.File.D, Coordinate.Rank.EIGHT)).occupied() &&
                                isNotAttacked(new Coordinate(Coordinate.File.D, Coordinate.Rank.EIGHT), oppositeTeam) &&
                                !this.board.getSquare(new Coordinate(Coordinate.File.C, Coordinate.Rank.EIGHT)).occupied() &&
                                isNotAttacked(new Coordinate(Coordinate.File.C, Coordinate.Rank.EIGHT), oppositeTeam) &&
                                !this.board.getSquare(new Coordinate(Coordinate.File.B, Coordinate.Rank.EIGHT)).occupied() &&
                                this.board.getSquare(new Coordinate(Coordinate.File.A, Coordinate.Rank.EIGHT)).occupied() &&
                                this.board.getSquare(new Coordinate(Coordinate.File.A, Coordinate.Rank.EIGHT)).getPiece().hasNotMoved()) {
                            legalMoves.add(new Move(coordinate, new Coordinate(Coordinate.File.C, Coordinate.Rank.EIGHT)));
                        }
                    }

                }
            }
            case PAWN -> {
                int direction = piece.getColor() == TeamColor.WHITE ? 1 : -1;

                /*
                 * Forward moves
                 */
                if (validIndices(startIndices[0], startIndices[1] + direction)) {
                    Coordinate oneStepCoordinate = new Coordinate(Coordinate.File.from(startIndices[0]), Coordinate.Rank.from(startIndices[1] + direction));
                    Square oneStep = this.board.getSquare(oneStepCoordinate);
                    if (!oneStep.occupied()) {
                        legalMoves.add(new Move(coordinate, oneStepCoordinate));

                        /*
                         * Two steps
                         */
                        if (piece.hasNotMoved() && validIndices(startIndices[0], startIndices[1] + direction * 2)) {
                            Coordinate twoStepCoordinate = new Coordinate(Coordinate.File.from(startIndices[0]), Coordinate.Rank.from(startIndices[1] + direction * 2));
                            Square twoStep = this.board.getSquare(twoStepCoordinate);
                            if (!twoStep.occupied()) {
                                legalMoves.add(new Move(coordinate, twoStepCoordinate));
                            }
                        }
                    }
                }

                /*
                 * Capturing moves
                 */
                for (int side : new int[]{-1, 1}) {
                    int newX = startIndices[0] + side;
                    int newY = startIndices[1] + direction;
                    if (validIndices(newX, newY)) {
                        Coordinate newCoordinate = new Coordinate(Coordinate.File.from(newX), Coordinate.Rank.from(newY));
                        Square target = this.board.getSquare(newCoordinate);

                        threatens.add(newCoordinate);
                        boolean legalEnPassant = newCoordinate.equals(this.enPassantCoordinate);
                        boolean legalNormalCapture = target.occupied() && target.getPiece().getColor() != piece.getColor();
                        if (legalNormalCapture || legalEnPassant) {
                            legalMoves.add(new Move(coordinate, newCoordinate));
                        }
                    }
                }
            }
            case ROOK -> {
                int[] directions = new int[]{-1, 1};

                /*
                 * Vertical moves
                 */
                int newX = startIndices[0];
                for (int direction : directions) {
                    int multiplier = 1;
                    int newY = startIndices[1] + direction * multiplier;

                    while (validIndices(newX, newY)) {
                        Coordinate newCoordinate = new Coordinate(Coordinate.File.from(newX), Coordinate.Rank.from(newY));
                        Square square = this.board.getSquare(newCoordinate);
                        threatens.add(newCoordinate);

                        if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                            legalMoves.add(new Move(coordinate, newCoordinate));
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
                        Coordinate newCoordinate = new Coordinate(Coordinate.File.from(newX), Coordinate.Rank.from(newY));
                        Square square = this.board.getSquare(newCoordinate);
                        threatens.add(newCoordinate);

                        if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                            legalMoves.add(new Move(coordinate, newCoordinate));
                        }

                        if (square.occupied()) {
                            break;
                        }
                        multiplier++;
                        newX = startIndices[0] + direction * multiplier;
                    }
                }
            }
            case QUEEN -> {
                int[] directions = new int[]{-1, 0, 1};

                for (int i : directions) {
                    for (int j : directions) {
                        if (i == 0 && j == 0 || !validIndices(startIndices[0] + i, startIndices[1] + j)) {
                            continue;
                        }
                        int multiplier = 1;
                        int newX = startIndices[0] + i;
                        int newY = startIndices[1] + j;
                        while (validIndices(newX, newY)) {
                            Coordinate newCoordinate = new Coordinate(Coordinate.File.from(newX), Coordinate.Rank.from(newY));
                            Square square = this.board.getSquare(newCoordinate);

                            threatens.add(newCoordinate);
                            if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                                legalMoves.add(new Move(coordinate, newCoordinate));
                            }
                            /*
                             * Stop when you hit a piece
                             */
                            if (square.occupied()) {
                                break;
                            }
                            multiplier++;
                            newX = startIndices[0] + i * multiplier;
                            newY = startIndices[1] + j * multiplier;
                        }

                    }
                }
            }
            case BISHOP -> {
                int[] directions = new int[]{-1, 1};

                for (int i : directions) {
                    for (int j : directions) {
                        int multiplier = 1;
                        int newX = startIndices[0] + i * multiplier;
                        int newY = startIndices[1] + j * multiplier;

                        while (validIndices(newX, newY)) {
                            Coordinate newCoordinate = new Coordinate(Coordinate.File.from(newX), Coordinate.Rank.from(newY));
                            Square square = this.board.getSquare(newCoordinate);

                            threatens.add(newCoordinate);

                            if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                                legalMoves.add(new Move(coordinate, newCoordinate));
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
            }
            case KNIGHT -> {
                int[] oneStep = new int[]{-1, 1};
                int[] twoStep = new int[]{-2, 2};

                for (int i : oneStep) {
                    for (int j : twoStep) {
                        int newX = startIndices[0] + i;
                        int newY = startIndices[1] + j;
                        if (validIndices(newX, newY)) {
                            Coordinate newCoordinate = new Coordinate(Coordinate.File.from(newX), Coordinate.Rank.from(newY));
                            Square square = this.board.getSquare(newCoordinate);
                            threatens.add(newCoordinate);

                            if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                                legalMoves.add(new Move(coordinate, newCoordinate));
                            }
                        }

                        newX = startIndices[0] + j;
                        newY = startIndices[1] + i;
                        if (validIndices(newX, newY)) {
                            Coordinate newCoordinate = new Coordinate(Coordinate.File.from(newX), Coordinate.Rank.from(newY));
                            Square square = this.board.getSquare(newCoordinate);
                            threatens.add(newCoordinate);

                            if (!square.occupied() || square.getPiece().getColor() != piece.getColor()) {
                                legalMoves.add(new Move(coordinate, newCoordinate));
                            }
                        }

                    }
                }
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
        for (Coordinate coordinate : Coordinate.all()) {
            square = board.getSquare(coordinate);
            if (square.occupied()) {
                updateThreatsMoves(coordinate);
            }
        }
    }

    /**
     * Check if any moves legal by piece movement are legal on board.
     * @param team
     *      Team that may be able to move
     * @return
     *      true iff @team has any legal moves
     */
    private boolean anyLegalMoves(TeamColor team) {
        ArrayList<Move> legalMoves = team.equals(TeamColor.WHITE) ? whiteLegalMoves : blackLegalMoves;

        ArrayList<Move> illegalMoves = new ArrayList<>();
        for (Move move : legalMoves) {
            /* Create copy of the model */
            Game copy = new Game(this.board.copy());

            /* Test move on copy */
            copy.makeMove(move.getFrom(), move.getTo());

            copy.updateMoves();

            /* If move leaves the team in check, it is not legal */
            if(copy.inCheck(team)) {
                illegalMoves.add(move);
            }
        }

        return legalMoves.size() == illegalMoves.size();
    }

    private Coordinate findPromotingPawn() {
        Coordinate.Rank[] backRanks = new Coordinate.Rank[]{Coordinate.Rank.ONE, Coordinate.Rank.EIGHT};
        for (Coordinate.Rank backRank : backRanks) {
            for (Coordinate.File file : Coordinate.File.values()) {
                Coordinate coordinate = new Coordinate(file, backRank);
                Square target = this.board.getSquare(coordinate);
                if (target.occupied() && target.getPiece().getType().equals(PieceType.PAWN)) {
                    return coordinate;
                }
            }
        }
        return null;
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
        /* Update threatened squares and moves legal by piece logic */
        updateMoves();

        TeamColor team = this.board.getSquare(start).getPiece().getColor();

        ArrayList<Move> legalMoves = team.equals(TeamColor.WHITE) ? this.whiteLegalMoves : this.blackLegalMoves;

        boolean legalByPieceLogic = legalMoves.stream().anyMatch(a -> a.equals(new Move(start, end)));
        System.out.printf("King is in check? %s\n", inCheck(team));
        /* Return early if not legal by piece logic */
        if (!legalByPieceLogic) {
            return false;
        }
        /* Check if move would put king in check */
        /* Create copy of the model */
        Game copy = new Game(this.board.copy());

        /* Test move on copy */
        copy.makeMove(start, end);

        copy.updateMoves();

        /* If move leaves the team in check, it is not legal */
        if(copy.inCheck(team)) {
            System.out.printf("%s to %s leaves king in check\n", start, end);
            return false;
        }

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

        Piece piece = startSquare.getPiece();

        endSquare.setPiece(piece);
        startSquare.removePiece();
        piece.setMoved(true);

        /* Check if move was castling */
        if (piece.getType().equals(PieceType.KING) &&
                Math.abs(start.getFile().toIndex() - end.getFile().toIndex()) == 2) {
            if (end.equals(new Coordinate(Coordinate.File.G, Coordinate.Rank.ONE))) {
                /* White kingside castles */
                makeMove(new Coordinate(Coordinate.File.H, Coordinate.Rank.ONE), new Coordinate(Coordinate.File.F, Coordinate.Rank.ONE));
            } else if (end.equals(new Coordinate(Coordinate.File.C, Coordinate.Rank.ONE))) {
                /* White queenside castles */
                makeMove(new Coordinate(Coordinate.File.A, Coordinate.Rank.ONE), new Coordinate(Coordinate.File.D, Coordinate.Rank.ONE));
            } else if (end.equals(new Coordinate(Coordinate.File.G, Coordinate.Rank.EIGHT))) {
                /* Black kingside castles */
                makeMove(new Coordinate(Coordinate.File.H, Coordinate.Rank.EIGHT), new Coordinate(Coordinate.File.F, Coordinate.Rank.EIGHT));
            } else if (end.equals(new Coordinate(Coordinate.File.C, Coordinate.Rank.EIGHT))) {
                /* Black queenside castles */
                makeMove(new Coordinate(Coordinate.File.A, Coordinate.Rank.EIGHT), new Coordinate(Coordinate.File.D, Coordinate.Rank.EIGHT));
            }
        }

        /* Check if pawn promoted */
        Coordinate.Rank endRank = end.getRank();
        this.pawnPromoted = piece.getType().equals(PieceType.PAWN) && (endRank == Coordinate.Rank.ONE || endRank == Coordinate.Rank.EIGHT);

        /* Check if move was en passant */
        if(piece.getType().equals(PieceType.PAWN) && end.equals(this.enPassantCoordinate)) {
            /* Remove opponent pawn */
            this.board.getSquare(new Coordinate(end.getFile(), start.getRank())).removePiece();
        }
        /* Set en passant square */
        if (piece.getType().equals(PieceType.PAWN) && Math.abs(end.getRank().toIndex() - start.getRank().toIndex()) == 2) {
            this.enPassantCoordinate = new Coordinate(start.getFile(), Coordinate.Rank.from((start.getRank().toIndex() + end.getRank().toIndex())/2));
        } else {
            this.enPassantCoordinate = null;
        }
    }

    public boolean pawnPromoted() {
        return this.pawnPromoted;
    }

    public void promotePawn(PieceType pieceType) {
        Coordinate promotingPawn = findPromotingPawn();

        Piece piece = new Piece(pieceType, getBoard().getSquare(promotingPawn).getPiece().getColor());
        getBoard().getSquare(promotingPawn).setPiece(piece);
    }

    /**
     * Determine if checkmate has been delivered.
     * @param teamColor
     *      Team which has [not] delivered checkmate
     * @return true iff @teamColor has delivered checkmate
     */
    public boolean checkmate(TeamColor teamColor) {
        updateMoves();
        return anyLegalMoves(teamColor) && inCheck(teamColor);
    }

    /**
     * Determine if stalemate has been forced.
     * @param teamColor
     *      Team which has [not] forced stalemate
     * @return true iff @teamColor has forced stalemate
     */
    public boolean stalemate(TeamColor teamColor) {
        updateMoves();
        return anyLegalMoves(teamColor) && !inCheck(teamColor);
    }

}
