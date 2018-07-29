package Game.Pieces;

import Game.Coordinates;
import Game.TeamColor;
import javafx.scene.image.Image;


public abstract class Piece {
    //Class variables
    TeamColor team;
    boolean hasMoved;
    PieceType type;
    Coordinates coordinates;
    private Image image;
    int[][] possibleMoves;
    //Used for the image URLs
    static String PRE_IMAGE = "file:\\C:\\Users\\justi\\IdeaProjects\\Chess-Java\\Chess\\Images\\";
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
/*
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            System.out.println("init x: " + this.coordinates.getX() + " init y: " +  this.coordinates.getY());
            System.out.println("init mX " + mouseX + " init mY " + mouseY);
        });
        setOnMouseDragged(e -> {
            double offsetX = e.getSceneX() - mouseX;
            double offsetY = e.getSceneY() - mouseY;

            Piece piece = (Piece)e.getSource();
            piece.setTranslateX(piece.getTranslateX() + offsetX);
            piece.setTranslateY(piece.getTranslateY() + offsetY);

            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });*/
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
     * Retrieve image representation of this.
     */
    public Image getImage() {
        if (this.image == null) {
            //Set the image of the correct piece
            String imageURL = getImageURL();
            image = new Image(imageURL);
        }
        return image;

    }

    /**
     * Retrieve the correct image URL based on the piece.
     * @return a PNG url
     */
    private String getImageURL() {
        return PRE_IMAGE + this.team.name() + this.type.name() + ".png";
    }

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
     * Determine if the piece has moves.
     * @return this.getHasMoved
     */
    public boolean getHasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {this.hasMoved = hasMoved;}


}