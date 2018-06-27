package Game;

import GUI.View;
import Game.Pieces.*;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Board extends GridPane {
    /*
    Declare useful constants and private variables
     */
    private final int GRID_SIZE = 8;
    private Square[][] grid;
    private PieceType[] backRowOrder;
    private Map<TeamColor, Piece> kings;
    private Stack<Move> previousMoves;
    /*
    Used to determine which team can move
     */
    private int moveCounter;

    /**
     * Create GRID_SIZE rank GRID_SIZE sized board and place pieces on it.
     */
    public Board() {
        kings = new HashMap<>();
        previousMoves = new Stack<>();
        backRowOrder = new PieceType[]{PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
                PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK};
        initializeGrid();
        initializePieces();
        moveCounter = 0;
    }

    /**
     * Getter for a [][] or squares representing the board.
     * @return this.grid
     */
    public Square[][] getGrid() {
        return grid;
    }

    /**
     * Determines the given team's king piece.
     * @param team
     *      The team of the king to return
     * @return the team's king
     */
    private Piece getKing(TeamColor team) {
        return kings.get(team);
    }

    /**
     * Set a king to a given team.
     * @param king
     *      The king being set
     */
    private void setKing(Piece king) {
        kings.put(king.getTeam(), king);
    }

    /**
     * Initializes this.grid with ID'd squares in the style of A1, B2 etc.
     */
    private void initializeGrid() {
        this.grid = new Square[GRID_SIZE][GRID_SIZE];
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                grid[x][y] = new Square((x + y) % 2 != 0, new Coordinates(x, y));
                add(grid[x][y], x, convertY(y));
            }
        }
    }

    /**
     * Convert Y values between Board and View.
     * @param y
     *      The y value being converted
     * @return View.Y_OFFSET - y
     */
    private int convertY(int y) {
        return View.Y_OFFSET - y;
    }

    /**
     * Place the correct pieces on the chess board according to this.backRowOrder and a second rank of pawns.
     */
    private void initializePieces() {
        final int whiteBackRow = 0;
        final int whitePawnRow = 1;
        final int blackBackRow = 7;
        final int blackPawnRow = 6;


        for(int x = 0; x < this.GRID_SIZE; x++) {
            /*
            Place white pieces
             */
            this.grid[x][whiteBackRow]
                    .putPiece(getPieceByName(this.backRowOrder[x], new Coordinates(x, whiteBackRow), TeamColor.WHITE));
            add(this.grid[x][whiteBackRow].getPiece(), x, convertY(whiteBackRow));
            this.grid[x][whitePawnRow]
                    .putPiece(getPieceByName(PieceType.PAWN, new Coordinates(x, whitePawnRow), TeamColor.WHITE));
            add(this.grid[x][whitePawnRow].getPiece(), x, convertY(whitePawnRow));
            /*
            Place black pieces
             */
            this.grid[x][blackBackRow]
                    .putPiece(getPieceByName(this.backRowOrder[x], new Coordinates(x, blackBackRow), TeamColor.BLACK));
            add(this.grid[x][blackBackRow].getPiece(), x, convertY(blackBackRow));
            this.grid[x][blackPawnRow]
                    .putPiece(getPieceByName(PieceType.PAWN, new Coordinates(x, blackPawnRow), TeamColor.BLACK));
            add(this.grid[x][blackPawnRow].getPiece(), x, convertY(blackPawnRow));
        }

    }

    /**
     * Based on a PieceType, return an initialized object of the correct implementation.
     * @param type
     *      The type of the piece
     * @param team
     *      The team to which the piece belongs for initialization
     * @return a specific implementation of Game.Pieces.Piece based on name
     */
    private Piece getPieceByName(PieceType type, Coordinates coordinates, TeamColor team) {
        Piece piece;
        switch (type) {
            case ROOK:
                piece = new Rook(coordinates, team);
                break;
            case KNIGHT:
                piece = new Knight(coordinates, team);
                break;
            case BISHOP:
                piece = new Bishop(coordinates, team);
                break;
            case QUEEN:
                piece = new  Queen(coordinates, team);
                break;
            case KING:
                piece = new King(coordinates, team);
                setKing(piece);
                break;
            case PAWN:
                piece = new Pawn(coordinates, team);
                break;
            default:
                System.out.println("Game.Pieces.Piece could not be initialized");
                piece = null;
                break;
        }
        piece.setOnMouseReleased(e -> {
            System.out.println("x scene: " + e.getSceneX() + " y scene: " + e.getSceneY());
            int x = toBoard(e.getSceneX());
            int y = convertY(toBoard(e.getSceneY()));
            System.out.println("x after release: " + x + " y after release: " + y);
            //determine if legal move, have way to abort move, have way to do move
            if ((piece.getTeam() == TeamColor.WHITE ? 0 : 1) == (moveCounter % 2)) {
                MoveType moveType = isLegalMove(piece, new Coordinates(x, y));
                System.out.println(moveType);
                if (moveType != MoveType.NONE) {
                    move(piece, new Coordinates(x, y), moveType);
                } else {
                    //TODO: force revert the attempted move
                    piece.move(piece.getCoordinates());
                }
            }
        });
        return piece;
    }


    /**
     * Given a String ID, return the corresponding Game.Square.
     * @param id
     *      The ID in grid
     * @return the Game.Square with the given ID
     */
    private Square getSquare(Coordinates id) {
        assert inBounds(id.getX()) : "X out of bounds for getSquare with x=" + id.getX();
        assert inBounds(id.getY()) : "Y out of bounds for getSquare with y=" + id.getY();
        return grid[id.getX()][id.getY()];
    }

    /**
     * Determines whether or not the given position falls in bounds of the grid.
     * @param pos
     *      position to check
     * @return true iff 0 <= pos < this.GRID_SIZE
     */
    private boolean inBounds(int pos) {
        return pos >= 0 && pos < this.GRID_SIZE;
    }

    /**
     * Determines the type of move attempted by the given piece.
     * @param piece
     *      The piece attempting to move
     * @param newCoords
     *      The potential new coordinates for piece
     * @return a MoveType corresponding to the legality of the move
     */
    private MoveType isLegalMove(Piece piece, Coordinates newCoords) {
        MoveType moveType = MoveType.NONE;
        //Get starting coordinates
        int oldX = piece.getCoordinates().getX();
        int oldY = piece.getCoordinates().getY();

        Square newSquare = getSquare(newCoords);
        assert newSquare.getID().equals(newCoords) : "Retrieved square does not match given coordinates";

        PieceType pieceType = piece.getType();
        int[] moveDifference = {newCoords.getX() - oldX, newCoords.getY() - oldY};
        System.out.println("moveDifference: x=" + moveDifference[0] + " y=" + moveDifference[1]);
        switch (pieceType) {
            case PAWN:
                int[][] moveSet = piece.getPossibleMoves();
                if (Arrays.equals(moveDifference, moveSet[0]) || Arrays.equals(moveDifference, moveSet[3])) {
                    if (newSquare.isOccupied() && newSquare.getPiece().getTeam() != piece.getTeam()) {
                        moveType = MoveType.NORMAL;
                    } else if (enPassantLegal(piece, moveDifference[0])){
                        moveType = MoveType.EN_PASSANT;
                    }
                } else if (Arrays.equals(moveDifference, moveSet[1]) || Arrays.equals(moveDifference, moveSet[2])) {
                    Square oneStep = Arrays.equals(moveDifference, moveSet[1]) ? newSquare : getSquare(new Coordinates(oldX, (oldY + newCoords.getY()) / 2));
                    if (!oneStep.isOccupied()) {
                        if (Arrays.equals(moveDifference, moveSet[2])) {
                            if (!newSquare.isOccupied()) {
                                moveType = MoveType.NORMAL;
                            }
                        } else {
                            moveType = MoveType.NORMAL;
                        }
                    }
                }
                break;
            case KING:
                if (Math.abs(moveDifference[0]) == 2 && castleLegal(piece, newCoords.getX())) {
                    moveType = MoveType.CASTLE;
                    break;
                }
            case KNIGHT:
                moveType = legalByPieceLogic(moveDifference, piece, false);
                break;
                //Bishop, Rook, and Queen all use multiple move logic - could split to diagonal and straightaways
            case BISHOP:
            case ROOK:
            case QUEEN:
                moveType = legalByPieceLogic(moveDifference, piece, true);
                break;
        }
        moveType = legalByBoardLogic(piece, newCoords, moveType);
        return moveType;
    }

    /**
     * Checks if the new square is unoccupied or occupied by the other team, and that the king will not be in check after moving.
     * @param newCoords
     *      New coordinates to test for the piece
     * @param piece
     *      The piece to move
     * @return true iff new square is legal for piece to move to
     */
    private MoveType legalByBoardLogic(Piece piece,Coordinates newCoords, MoveType moveType) {
        if (moveType != MoveType.NONE) {
            Square newSquare = getSquare(newCoords);
            //Square must either be open or able to be killed
            if (!newSquare.isOccupied() || newSquare.getPiece().getTeam() != piece.getTeam()) {
                //After moving, the team's king must not be in check
                move(piece, newCoords, moveType);
                if (inCheck(getSquare(getKing(piece.getTeam()).getCoordinates()), piece.getTeam())) {
                    System.out.println("Your king is in check");
                    moveType = MoveType.NONE;
                }
                revertMove();
            } else {
                System.out.println("Square occupied by same team");
                moveType = MoveType.NONE;
            }
        } else {
            System.out.println("legalByBoardLogic was passed MoveType.NONE as arg");
        }
        if (moveType == MoveType.NONE) {
            System.out.println("Move failed board logic");
        }
        return  moveType;
    }

    /**
     * Moves the given  piece to new Coordinates and updates this
     * @param piece
     *      Piece to be moved
     * @param newCoords
     *      New coordinates for piece
     */
    private void move(Piece piece, Coordinates newCoords, MoveType moveType) {
        assert moveType != MoveType.NONE : "MoveType.NONE passed to move method";
        Square oldSquare = getSquare(piece.getCoordinates());
        Square newSquare = getSquare(newCoords);
        /*
        Helper moves are normal moves, but do not change other attributes of board logic
         */
        if (moveType != MoveType.HELPER) {
            this.previousMoves.push(new Move(piece, newSquare, moveType));
            this.moveCounter++;
            piece.setHasMoved(true);
        }
        switch (moveType) {
            case HELPER:
                //Regular move except it does not affect board logic
            case NORMAL:
                oldSquare.setVacant();
                newSquare.putPiece(piece);
                break;
            case EN_PASSANT:
                this.previousMoves.peek().setEnPassantPawn(enPassantMove(piece, newCoords));
                break;
            case CASTLE:
                this.previousMoves.peek().setCastleRook(castleMove(piece, newCoords.getX()));
                break;
        }
    }

    /**
     * If possible, undo the last made move
     */
    private void revertMove() {
        if (!this.previousMoves.empty()) {
            Move lastMove = this.previousMoves.pop();
            move(lastMove.getNewPiece(), lastMove.getOldCoordinates(), MoveType.NORMAL);
            lastMove.getNewPiece().setHasMoved(lastMove.newPieceHadMoved());
            switch (lastMove.getMoveType()) {
                case NONE:
                    break;
                case NORMAL:
                    if (lastMove.wasCapture()) {
                        getSquare(lastMove.getNewCoordinates()).putPiece(lastMove.getOldPiece());
                    }
                    break;
                case EN_PASSANT:
                    getSquare(lastMove.getEnPassantPawn().getCoordinates()).putPiece(lastMove.getEnPassantPawn());
                    break;
                case CASTLE:
                    getSquare(lastMove.getCastleRook().getCoordinates()).putPiece(lastMove.getCastleRook());
                    break;
            }


        }
    }

    /**
     * Checks if the move follows piece logic.
     * @param prospectiveMove
     *      The difference in coordinates from new square to old square
     * @param piece
     *      The piece attempting to move
     * @return true iff the move is legal based on the type of piece
     */
    private MoveType legalByPieceLogic(int[] prospectiveMove, Piece piece, boolean moveMultiple) {
        MoveType moveType = MoveType.NONE;
        int newX = prospectiveMove[0];
        int newY = prospectiveMove[1];
        for (int[] move : piece.getPossibleMoves()) {
            //If multiple moves are legal, reduce the prospective move as much as possible
            if (moveMultiple) {
                //Multiplier must be the same for x and y
                int xMultiplier = -1;
                int yMultiplier;
                //Don't adjust if a good move is 0
                if (move[0] != 0) {
                    //If newX is evenly divisible by move[0], get the multiplier, otherwise move will not work
                    if (newX != 0 && evenDivis(newX, move[0])) {
                        xMultiplier = newX / move[0];
                        newX /= xMultiplier;
                    } else {
                        continue;
                    }
                    //If a good move is 0, the prospective move must be 0 in the direction
                } else if (newX != 0) {
                    continue;
                }
                //Don't adjust if a good move is 0
                if (move[1] != 0) {
                    //If newY is evenly divisible by move[1], check the multiplier with the previous one
                    if (newY != 0 && evenDivis(newY, move[1])) {
                        yMultiplier = newY / move[1];
                        if (xMultiplier != -1 && xMultiplier != yMultiplier) {
                            continue;
                        }
                    }
                    //If a good move is 0, the prospective move must be 0 in that direction
                } else if (newY != 0) {
                    continue;
                }
                //Check if view is obstructed
                if ((prospectiveMove[0] != 0 || prospectiveMove[1] != 0) && obstructedView(piece, prospectiveMove)) {
                    continue;
                }
            }
            if (Arrays.equals(new int[]{newX, newY}, move)) {
                moveType = MoveType.NORMAL;
                break;
            }

        }
        if (moveType == MoveType.NONE) {
            System.out.println("Illegal by piece logic");
        }
        return moveType;
    }

    /**
     * Determines if piece as any pieces in between it and its new square.
     * @param piece
     *      The piece attempting to move
     * @param attemptedMove
     *      The addition to the piece's [x, y] coordinates
     * @return true iff there are no pieces between piece and its new square
     */
    private boolean obstructedView(Piece piece, int[] attemptedMove) {
        assert !(attemptedMove[0] == 0 && attemptedMove[1] == 0) : "Cannot move to same square";
        int max = attemptedMove[0] != 0 ? attemptedMove[0] : attemptedMove[1];
        int direction = max / Math.abs(max);
        for (int step = direction; Math.abs(step) < Math.abs(max); step+= direction) {
            if (getSquare(new Coordinates(piece.getCoordinates().getX() + step, piece.getCoordinates().getY() + step)).isOccupied()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if en passant is legal for the given pawn.
     * @param pawn
     *      The pawn attempting en Passant
     * @param xChange
     *      The x direction in which the pawn is attempting to capture
     * @return true iff en Passant is legal for the given pawn and direction
     */
    private boolean enPassantLegal(Piece pawn, int xChange) {
        boolean isLegal = false;
        if (!previousMoves.empty()) {
            Move lastMove = previousMoves.peek();
            Coordinates enemyPawnLocation = lastMove.getNewCoordinates();
            Coordinates currentLocation = pawn.getCoordinates();
            if (lastMove.getNewPiece().getType() == PieceType.PAWN
                    && Math.abs(enemyPawnLocation.getY() - lastMove.getOldCoordinates().getY()) == 2) {
                if (currentLocation.getY() == enemyPawnLocation.getY() && enemyPawnLocation.getX() - currentLocation.getX() == xChange) {
                    isLegal = true;
                }
            }
        }
        return isLegal;
    }

    /**
     * Checks if the given king is able to castle to the new x position.
     * @param king
     *      The king piece attempting to castle
     * @param newX
     *      The new x coordinate for the king
     * @return true iff the king is able to castle to the new square given chess rules
     */
    private boolean castleLegal(Piece king, int newX) {
        boolean isLegal = false;
        int kingX = king.getCoordinates().getX();
        int kingY = king.getCoordinates().getY();
        if (!king.getHasMoved() && Math.abs(newX - kingX) == 2) {
            Square rookSquare = getCastleRookSquare(king, newX);
            if (rookSquare.isOccupied() && !rookSquare.getPiece().getHasMoved() && rookSquare.getPiece().getType() == PieceType.ROOK) {
                //Check danger squares for king, and occupation of squares in between
                if(!obstructedView(king, new int[]{rookSquare.getID().getX(), rookSquare.getID().getY()})) {
                    if (!inCheck(getSquare(new Coordinates((newX + kingX)/2, kingY)), king.getTeam())
                            && !inCheck(getSquare(new Coordinates(newX, kingY)), king.getTeam())) {
                        isLegal = true;
                    }
                }
            }
        }
        return isLegal;
    }

    /**
     * Retrieve the square of the rook with which the given king would castle.
     * @param king
     *      The king that is trying to castle
     * @param newX
     *      The new x coordinate for the king upon castling
     * @return either the first or final file on the back rank of king.team depending on the king's new square
     */
    private Square getCastleRookSquare(Piece king, int newX) {
        int rookFile = newX > king.getCoordinates().getX() ? GRID_SIZE - 1 : 0;
        return getSquare(new Coordinates(rookFile, king.getCoordinates().getY()));
    }

    /**
     * Special move for the king that involves both the king and a rook.
     * @param king
     *      The king to be moved
     * @param newX
     *      The new x coordinate for the king
     * @return the rook which was involved in castling
     */
    private Piece castleMove(Piece king, int newX) {
        Piece rook = getCastleRookSquare(king, newX).getPiece();
        move(king, new Coordinates(newX, king.getCoordinates().getY()), MoveType.HELPER);
        move(rook, new Coordinates((newX + king.getCoordinates().getX())/2, king.getCoordinates().getY()), MoveType.HELPER);
        return rook;
    }

    /**
     * Special move for a pawn that involves a capture of another pawn.
     * @param pawn
     *      The pawn moving via en Passant
     * @param newCoords
     *      The new coordinates for the moving pawn
     * @return the pawn that is captured via en Passant
     */
    private Piece enPassantMove(Piece pawn, Coordinates newCoords){
        Square enemyPawnSquare = getSquare(previousMoves.peek().getNewCoordinates());
        Piece enemyPawn = enemyPawnSquare.getPiece();
        enemyPawnSquare.setVacant();
        move(pawn, newCoords, MoveType.HELPER);
        return enemyPawn;

    }

    /**
     * Check if x is evenly divisible by mod.
     * @param num
     *      The dividend
     * @param mod
     *      The divisor
     * @return true iff x % mod == 0
     */
    private boolean evenDivis(int num, int mod) {
        return num % mod == 0;
    }

    /**
     * Transform pixel coordinates from view into a board integer index.
     * @param pixel
     *      The pixel on the screen
     * @return an integer conversion of the pixel
     */
    private int toBoard(double pixel) {
        return (int)(pixel) / View.TILE_SIZE;
    }


    /**
     * Determines whether the given square is threatened by the opposing team.
     * @param square
     *      Target square to check
     * @param team
     *      Team attempting to move to the given square
     * @return true iff !team is threatening square
     */
    private boolean inCheck(Square square, TeamColor team) {
        Coordinates id = square.getID();
        return (threatenedByDiagonal(id, team) || threatenedByStraightaway(id, team) || threatenedByKnight(id, team));
    }

    /**
     * Determined if !team can attack curID using diagonals.
     * @param curID
     *      The ID to check
     * @param team
     *      The team who would be threatened
     * @return true iff !team can attack curID
     */
    private boolean threatenedByDiagonal(Coordinates curID, TeamColor team) {
        int curRank = curID.getY();
        int curFile = curID.getX();
        int pawnAdjust = team == TeamColor.WHITE ? 1 : -1;
        int pawnThreatRank = curRank + pawnAdjust;
        int[] directions = {-1, 1};
        for (int rankAdjust : directions) {
            for (int fileAdjust : directions) {
                int multiplier = 1;
                int nextRank = curRank + rankAdjust;
                int nextFile = curFile + fileAdjust;
                while (inBounds(nextRank) && inBounds(nextFile)) {
                    Square toCheck = getSquare(new Coordinates(nextRank, nextFile));
                    if (toCheck.isOccupied()) {
                        Piece piece = toCheck.getPiece();
                        if (piece.getTeam() != team) {
                            PieceType type = piece.getType();
                            if (type == PieceType.QUEEN || type == PieceType.BISHOP || type == PieceType.KING ||
                                    (nextRank == pawnThreatRank && type == PieceType.PAWN)) {
                                return true;
                            }
                        }
                        break;
                    }
                    multiplier++;
                    nextRank = curRank + rankAdjust * multiplier;
                    nextFile = curFile + fileAdjust * multiplier;
                }
            }
        }
        return false;
    }

    /**
     * Determined if !team can attack curID using ranks or files.
     * @param curID
     *      The ID to check
     * @param team
     *      The team who would be threatened
     * @return true iff !team can attack curID
     */
    private boolean threatenedByStraightaway (Coordinates curID, TeamColor team) {
        int curRank = curID.getY();
        int curFile = curID.getX();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] adjustments : directions) {
            int multiplier = 1;
            int newRank = curRank + adjustments[0];
            int newFile = curFile + adjustments[1];
            while (inBounds(newRank) && inBounds(newFile)) {
                Square toCheck = getSquare(new Coordinates(newRank, newFile));
                if (toCheck.isOccupied()) {
                    Piece piece = toCheck.getPiece();
                    if (piece.getTeam() != team) {
                        PieceType type = piece.getType();
                        if (type == PieceType.QUEEN || type == PieceType.ROOK || type == PieceType.KING) {
                            return true;
                        }
                    }
                    break;
                }
                multiplier++;
                newRank = curRank + adjustments[0] * multiplier;
                newFile = curFile + adjustments[1] * multiplier;
            }

        }
        return false;

    }

    /**
     * Determined if !team can attack curID using knights.
     * @param curID
     *      The ID to check
     * @param team
     *      The team who would be threatened
     * @return true iff !team can attack curID
     */
    private boolean threatenedByKnight (Coordinates curID, TeamColor team) {
        int curX = curID.getX();
        int curY = curID.getY();
        int[][] directions = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}};
        int[] sides = {-1, 1};
        for (int[] direction : directions) {
            for (int side : sides) {
                /*
                Check if knight square is in bounds
                 */
                int knightX = curX + direction[0];
                int knightY = curY + direction[1] * side;
                if (!inBounds(knightX) || !inBounds(knightY)) {
                    continue;
                }
                /*
                See if an enemy knight occupies the given square
                 */
                Square toCheck = getSquare(new Coordinates(knightX, knightY));
                if (toCheck.isOccupied()) {
                    Piece piece = toCheck.getPiece();
                    if (piece.getTeam() != team) {
                        if (piece.getType() == PieceType.KNIGHT) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}