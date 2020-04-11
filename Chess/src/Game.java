import java.util.*;

public class Game {
    private Board board;
    private Coordinate enPassantCoordinate;
    private boolean pawnPromoted;
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
        for (Coordinate coordinate : EnumSet.allOf(Coordinate.class)) {
            Square square = board.getSquare(coordinate);
            if (square.occupied() && square.getPiece().getType().equals(PieceType.KING) && square.getPiece().getColor().equals(team)) {
                return coordinate;
            }
        }
        return null;
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

                /* Check for castling */
                TeamColor team = piece.getColor().equals(TeamColor.WHITE) ? TeamColor.WHITE : TeamColor.BLACK;
                if (!piece.hasMoved() && !inCheck(team)) {
                    TeamColor oppositeTeam = piece.getColor().equals(TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;

                    if (team.equals(TeamColor.WHITE)) {
                        /* White kingside castles */
                        if (!this.board.getSquare(Coordinate.F1).occupied() &&
                                !isAttacked(Coordinate.F1, oppositeTeam) &&
                                !this.board.getSquare(Coordinate.G1).occupied() &&
                                !isAttacked(Coordinate.G1, oppositeTeam) &&
                                this.board.getSquare(Coordinate.H1).occupied() &&
                                !this.board.getSquare(Coordinate.H1).getPiece().hasMoved()) {
                            legalMoves.add(new Coordinate[] {coordinate, Coordinate.G1});
                        }

                        /* White queenside castles */
                        if (!this.board.getSquare(Coordinate.D1).occupied() &&
                                !isAttacked(Coordinate.D1, oppositeTeam) &&
                                !this.board.getSquare(Coordinate.C1).occupied() &&
                                !isAttacked(Coordinate.C1, oppositeTeam) &&
                                !this.board.getSquare(Coordinate.B1).occupied() &&
                                this.board.getSquare(Coordinate.A1).occupied() &&
                                !this.board.getSquare(Coordinate.A1).getPiece().hasMoved()) {
                            legalMoves.add(new Coordinate[] {coordinate, Coordinate.C1});
                        }
                    } else {
                        /* Black kingside castles */
                        if (!this.board.getSquare(Coordinate.F8).occupied() &&
                                !isAttacked(Coordinate.F8, oppositeTeam) &&
                                !this.board.getSquare(Coordinate.G8).occupied() &&
                                !isAttacked(Coordinate.G8, oppositeTeam) &&
                                this.board.getSquare(Coordinate.H8).occupied() &&
                                !this.board.getSquare(Coordinate.H8).getPiece().hasMoved()) {
                            legalMoves.add(new Coordinate[] {coordinate, Coordinate.G8});
                        }

                        /* White queenside castles */
                        if (!this.board.getSquare(Coordinate.D8).occupied() &&
                                !isAttacked(Coordinate.D8, oppositeTeam) &&
                                !this.board.getSquare(Coordinate.C8).occupied() &&
                                !isAttacked(Coordinate.C8, oppositeTeam) &&
                                !this.board.getSquare(Coordinate.B8).occupied() &&
                                this.board.getSquare(Coordinate.A8).occupied() &&
                                !this.board.getSquare(Coordinate.A8).getPiece().hasMoved()) {
                            legalMoves.add(new Coordinate[] {coordinate, Coordinate.C8});
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
                        boolean legalEnPassant = this.enPassantCoordinate != null && newCoordinate.equals(this.enPassantCoordinate);
                        boolean legalNormalCapture = target.occupied() && target.getPiece().getColor() != piece.getColor();
                        if (legalNormalCapture || legalEnPassant) {
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

    /**
     * Check if any moves legal by piece movement are legal on board.
     * @param team
     *      Team that may be able to move
     * @return
     *      true iff @team has any legal moves
     */
    private boolean anyLegalMoves(TeamColor team) {
        ArrayList<Coordinate[]> legalMoves = team.equals(TeamColor.WHITE) ? whiteLegalMoves : blackLegalMoves;

        ArrayList<Coordinate[]> illegalMoves = new ArrayList<>();
        for (Coordinate[] move : legalMoves) {
            /* Create copy of the model */
            Game copy = new Game(this.board.copy());

            /* Test move on copy */
            copy.makeMove(move[0], move[1]);

            copy.updateMoves();

            /* If move leaves the team in check, it is not legal */
            if(copy.inCheck(team)) {
                illegalMoves.add(move);
            }
        }

        return legalMoves.size() == illegalMoves.size();
    }

    private Coordinate findPromotingPawn() {
        int[] backRanks = new int[] {0, 7};
        for (int backRank : backRanks) {
            for (int file = 0; file < Board.GRID_SIZE; file++) {
                Coordinate coordinate = Coordinate.fromIndices(file, backRank);
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

        ArrayList<Coordinate[]> legalMoves = team.equals(TeamColor.WHITE) ? this.whiteLegalMoves : this.blackLegalMoves;

        boolean legalByPieceLogic = legalMoves.stream().anyMatch(a -> Arrays.equals(a, new Coordinate[] {start, end}));
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

        Piece piece = startSquare.getPiece();

        endSquare.setPiece(piece);
        startSquare.removePiece();
        piece.setMoved(true);

        /* Check if move was castling */
        if (piece.getType().equals(PieceType.KING) &&
                Math.abs(start.toIndices()[0] - end.toIndices()[0]) == 2) {
            switch(end) {
                case G1: { /* White kingside castles */
                    makeMove(Coordinate.H1, Coordinate.F1);
                    break;
                }
                case C1: { /* White queenside castles */
                    makeMove(Coordinate.A1, Coordinate.D1);
                    break;
                }
                case G8: { /* Black kingside castles */
                    makeMove(Coordinate.H8, Coordinate.F8);
                    break;
                }
                case A8: { /* Black queenside castles */
                    makeMove(Coordinate.A8, Coordinate.D8);
                    break;
                }
                default: {
                    break;
                }
            }
        }

        /* Check if pawn promoted */
        int endRank = end.toIndices()[1];
        this.pawnPromoted = piece.getType().equals(PieceType.PAWN) && (endRank == 0 || endRank == 7);

        /* Check if move was en passant */
        if(piece.getType().equals(PieceType.PAWN) && end.equals(this.enPassantCoordinate)) {
            /* Remove opponent pawn */
            int x = end.toIndices()[0];
            int y = start.toIndices()[1];

            this.board.getSquare(Coordinate.fromIndices(x, y)).removePiece();
        }
        /* Set en passant square */
        if (piece.getType().equals(PieceType.PAWN) && Math.abs(end.toIndices()[1] - start.toIndices()[1]) == 2) {
            int x = start.toIndices()[0];
            int y = (start.toIndices()[1] + end.toIndices()[1])/2;
            this.enPassantCoordinate = Coordinate.fromIndices(x, y);

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
