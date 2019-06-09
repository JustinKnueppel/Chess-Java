package GUI;

import Game.Board;
import Game.Coordinates;
import Game.Pieces.Move;
import Game.Pieces.Piece;
import Game.Pieces.PieceType;
import Game.Square;
import Game.TeamColor;

import java.util.ArrayList;
import java.util.Stack;

public class Model {
    private Board board;
    private Stack<Move> history;

    public Model() {
        this.history = new Stack<>();
        this.board = new Board();
        this.board.initializePieces();
    }

    /*
     * Helper methods
     */

    private boolean legalByPieceLogic(Piece piece, Coordinates oldCoordinates, Coordinates newCoordinates) {
        //TODO: Move piece logic
        return true;
    }

    private boolean viewObstructed(Coordinates oldCoordinates, Coordinates newCoordinates) {
        //TODO: Check if view is obstructed
        return true;
    }

    private boolean inCheck(TeamColor team) {
        //TODO: Check if team is in check
        return true;
    }

    private boolean enPassantLegal(Coordinates captureCoordinates) {
        //TODO: Check for en passant to particular coordinates
        return true;
    }

    private boolean castleLegal(TeamColor team, Coordinates newKingCoordinates) {
        //TODO: Check for castling legal
        return true;
    }

    /*
     * ===============================
     * Public methods for controller
     * ===============================
     */

    /**
     * Get all pieces currently in play.
     * @return all pieces placed on the board
     */
    public ArrayList<Piece> getPieces() {
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.addAll(this.board.getBlackPieces());
        pieces.addAll(this.board.getWhitePieces());
        return pieces;
    }

    /**
     * Determine if a given move is legal.
     * @param oldCoords
     *          Starting coordinates
     * @param newCoords
     *          New coordinates
     * @return true iff a move from @oldCoords to @newCoords is a legal chess move
     */
    public boolean legalMove(Coordinates oldCoords, Coordinates newCoords) {
        //TODO: Determine if move is legal
        return true;
    }

    /**
     * Move the piece at @oldCoords to @newCoords.
     * @param oldCoords
     *          Starting coordinates of target piece
     * @param newCoords
     *          New coordinates of target piece
     */
    public void makeMove(Coordinates oldCoords, Coordinates newCoords) {
        //TODO: Make move
    }

    /**
     * Retrieve all legal moves for the given team.
     * @return all legal moves for @team
     */
    public ArrayList<Coordinates[]> getMoves(TeamColor team) {
        //TODO: Get list of leagl moves
        return new ArrayList<>();
    }

    /**
     * Go back one move.
     * @return true if reverting the move was successful else false
     */
    public boolean revertMove() {
        //TODO: Revert move
        return true;
    }
}
