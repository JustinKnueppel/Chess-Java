package Game.Pieces;

import Game.Coordinates;
import Game.TeamColor;


public abstract class Piece {
    //Class variables
    TeamColor team;
    boolean hasMoved;
    PieceType type;
    Coordinates coordinates;
    int[][] possibleMoves;
    /**
     * Based on a PieceType, return an initialized object of the correct implementation.
     * @param type
     *      The type of the piece
     * @param coordinates
     *      The starting coordinates for the piece
     * @param team
     *      The team to which the piece belongs for initialization
     * @return a specific implementation of Game.Pieces.Piece based on name
     */
    public static Piece getPieceByName(PieceType type, Coordinates coordinates, TeamColor team) {
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
                piece = new Queen(coordinates, team);
                break;
            case KING:
                piece = new King(coordinates, team);
                break;
            case PAWN:
                piece = new Pawn(coordinates, team);
                break;
            default:
                System.out.println("Game.Pieces.Piece could not be initialized");
                piece = null;
                break;
        }
        return piece;
    }

    /**
     * Constructor that is universal for each piece.
     * @param coordinates
     *      Rank and file of piece
     * @param team
     *      TeamColor of piece
     */
    public Piece(Coordinates coordinates, TeamColor team) {
        this.coordinates = coordinates;
        this.team = team;
        this.hasMoved = false;
        initMoveDirections();
        initPieceType();
    }

    /**
     * Get the team of this.
     * @return
     *      TeamColor of this
     */
    public TeamColor getTeam() {
        return this.team;
    }

    /**
     * Get the possible moves based on piece logic.
     * @return an int[][] with coordinate changes possible by type
     */
    public int[][] getPossibleMoves() {
        return possibleMoves;
    }

    /**
     * Get the current coordinates of this piece.
     * @return Coordinates to housing square of this
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Determines the type of move that is possible for each direction.
     */
    abstract void initMoveDirections();

    /**
     * Sets the initial PieceType.
     */
    abstract void initPieceType();

    /**
     * Moves the given piece to new Coordinates.
     * @param newCoordinates
     *      The target square for this
     */
    public void move(Coordinates newCoordinates) {
        this.coordinates = newCoordinates;
    }



    /**
     * Get the type of piece.
     * @return piece type
     */
    public PieceType getType() {
        return this.type;
    }

    /**
     * Determine if the piece has moved.
     * @return this.getHasMoved
     */
    public boolean hasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {this.hasMoved = hasMoved;}


}