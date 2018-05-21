package Game;

import GUI.View;
import Game.Pieces.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Board {
    /*
    Declare useful constants and private variables
     */
    private final int GRID_SIZE = 8;
    private Square[][] grid;
    private PieceType[] backRowOrder;
    private Map<TeamColor, Piece> kings;
    private Stack<MoveType> previousMoves;

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
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                grid[row][column] = new Square((row + column) % 2 == 0, new Coordinates(row, column));
            }
        }
    }

    /**
     * Place the correct pieces on the chess board according to this.backRowOrder and a second rank of pawns.
     */
    private void initializePieces() {
        final int blackOffset = 7;
        final int whiteBackRow = 0;
        final int whitePawnRow = 1;
        final int blackBackRow = 7;
        final int blackPawnRow = 6;

        for(int column = 0; column < this.GRID_SIZE; column++) {
            /*
            Place white pieces
             */
            this.grid[whiteBackRow][column]
                    .putPiece(getPieceByName(this.backRowOrder[column], new Coordinates(whiteBackRow, column), TeamColor.WHITE));
            this.grid[whitePawnRow][column]
                    .putPiece(getPieceByName(PieceType.PAWN, new Coordinates(whitePawnRow, column), TeamColor.WHITE));
            /*
            Place black pieces
             */
            this.grid[blackBackRow][column]
                    .putPiece(getPieceByName(this.backRowOrder[blackOffset - column], new Coordinates(blackBackRow, column), TeamColor.BLACK));
            this.grid[blackPawnRow][column]
                    .putPiece(getPieceByName(PieceType.PAWN, new Coordinates(blackPawnRow, column), TeamColor.BLACK));
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
            int x = toBoard(piece.getLayoutX());
            int y = toBoard(piece.getLayoutY());
            //determine if legal move, have way to abort move, have way to do move
            if (isLegalMove(piece, new Coordinates(x, y))) {

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
    public Square getSquare(Coordinates id) {
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
    public boolean isLegalMove(Piece piece, Coordinates newCoords) {
        boolean isLegal = false;
        //Get starting coordinates
        int oldX = piece.getCoordinates().getX();
        int oldY = piece.getCoordinates().getY();

        Square newSquare = getSquare(newCoords);

        PieceType pieceType = piece.getType();
        int[] moveDifference = {newCoords.getX() - oldX, newCoords.getY() - oldY};
        /*
        Pawns have distinct move logic, then pieces that can only move one square, then multiple squares
         */
        if (pieceType == PieceType.PAWN) {
            int[][] moveSet = piece.getPossibleMoves();
            if ((moveDifference == moveSet[0] || moveDifference == moveSet[3]) && newSquare.isOccupied() && newSquare.getPiece().getTeam() != piece.getTeam()) {
                isLegal = legalByBoardLogic(newCoords, piece);
            } else if (moveDifference == moveSet[1] || moveDifference == moveSet[2]) {
                Square oneStep;
                if (moveDifference == moveSet[1]) {
                    oneStep = getSquare(newCoords);
                    if (!oneStep.isOccupied()) {
                        isLegal = legalByBoardLogic(newCoords, piece);
                    }
                } else {
                    oneStep = getSquare(new Coordinates(oldX, (newCoords.getY() + oldY) / 2));
                    if (!oneStep.isOccupied() && !newSquare.isOccupied()) {
                        isLegal = legalByBoardLogic(newCoords, piece);
                    }
                }

            }

        } else if (pieceType == PieceType.KING || pieceType == PieceType.KNIGHT){
            isLegal = legalByPieceLogic(moveDifference, piece, false) && legalByBoardLogic(newCoords, piece);
        } else {
            //TODO: find a way to make sure there are no pieces in the way
            isLegal = legalByPieceLogic(moveDifference, piece, true) && legalByBoardLogic(newCoords, piece);

        }
        return isLegal;
    }

    /**
     * Checks if the new square is unoccupied or occupied by the other team, and that the king will not be in check after moving.
     * @param newCoords
     *      New coordinates to test for the piece
     * @param piece
     *      The piece to move
     * @return true iff new square is legal for piece to move to
     */
    private boolean legalByBoardLogic(Coordinates newCoords, Piece piece) {
        boolean isLegal = false;
        Square newSquare = getSquare(newCoords);
        //Square must either be open or able to be killed
        if (!newSquare.isOccupied() || newSquare.getPiece().getTeam() != piece.getTeam()) {
            //After moving, the team's king must not be in check
            move(piece, newCoords);
            if (!inCheck(getSquare(getKing(piece.getTeam()).getCoordinates()), piece.getTeam())) {
                isLegal = true;
            }
            revertMove();
        }
        return  isLegal;
    }

    /**
     * Moves the given  piece to new Coordinates and updates this
     * @param piece
     *      Piece to be moved
     * @param newCoords
     *      New coordinates for piece
     */
    private void move(Piece piece, Coordinates newCoords) {
        Square oldSquare = getSquare(piece.getCoordinates());
        Square newSquare = getSquare(newCoords);
        this.previousMoves.push(new MoveType(piece, newSquare));
        oldSquare.setVacant();
        newSquare.putPiece(piece);
        piece.move(newCoords);
    }
    public void revertMove() {
        if (!this.previousMoves.empty()) {
            MoveType lastMove = this.previousMoves.pop();
            move(lastMove.getNewPiece(), lastMove.getOldCoordinates());
            getSquare(lastMove.getNewCoordinates()).putPiece(lastMove.getOldPiece());
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
    private boolean legalByPieceLogic(int[] prospectiveMove, Piece piece, boolean moveMultiple) {
        boolean isLegal = false;
        int newX = prospectiveMove[0];
        int newY = prospectiveMove[1];
        for (int[] move : piece.getPossibleMoves()) {
            //If multiple moves are legal, reduce the prospective move as much as possible
            if (moveMultiple) {
                //Multiplier must be the same for x and y
                int multiplier = 1;
                //Don't adjust if a good move is 0
                if (move[0] != 0) {
                    //If newX is evenly divisible by move[0], get the multiplier, otherwise move will not work
                    if (evenDivis(newX, move[0])) {
                        multiplier = newX / move[0];
                        newX /= multiplier;
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
                    if (evenDivis(newY, move[1])) {
                        if (newY / move[1] == multiplier) {
                            newY /= multiplier;
                        } else {
                            continue;
                        }
                    }
                    //If a good move is 0, the prospective move must be 0 in that direction
                } else if (newY != 0) {
                    continue;
                }
            }
            if (new int[]{newX, newY} == move) {
                isLegal = true;
                break;
            }
        }
        return isLegal;
    }
    private boolean enPassantLegal(Piece pawn) {
        boolean isLegal = false;

        return isLegal;
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
    private int toBoard(double pixel) {
        return (int)(pixel + View.TILE_SIZE / 2) / View.TILE_SIZE;
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
        int curRank = curID.getX();
        int curFile = curID.getY();
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
        int curRank = curID.getX();
        int curFile = curID.getY();
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
        int curRank = curID.getX();
        int curFile = curID.getY();
        int[][] directions = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}};
        int[] sides = {-1, 1};
        for (int[] direction : directions) {
            for (int side : sides) {
                Square toCheck = getSquare(new Coordinates(curRank + direction[0], curFile + direction[1] * side));
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