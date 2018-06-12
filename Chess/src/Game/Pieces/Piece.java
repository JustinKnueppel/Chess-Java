package Game.Pieces;

import GUI.View;
import Game.Coordinates;
import Game.TeamColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public abstract class Piece extends Pane {
    //Class variables
    TeamColor team;
    boolean hasMoved;
    PieceType type;
    Coordinates coordinates;
    int[][] possibleMoves;

    private double mouseX, mouseY;
    //Necessary due to indexing in Javafx starting in top left, and a chess board using bottom left
    final static int Y_DISPLAY_OFFSET = 7;
    //Used for the image URLs
    static String PRE_IMAGE = "file:\\C:\\Users\\justi\\IdeaProjects\\Chess-Java\\Chess\\Images\\";

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
        initImage();

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            System.out.println("init x: " + this.coordinates.getX() + " init y: " +  this.coordinates.getY());
        });
        setOnMouseDragged(e -> {
            double offsetX = e.getSceneX() - mouseX;
            double offsetY = e.getSceneY() - mouseY;

            Piece piece = (Piece)e.getSource();
            piece.setTranslateX(piece.getTranslateX() + offsetX);
            piece.setTranslateY(piece.getTranslateY() + offsetY);

            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
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
     * Initializes the image representation of the piece.
     */
    private void initImage() {
        //Set the image of the correct piece
        String imageURL = getImageURL();
        Image img = new Image(imageURL);
        ImageView iv = new ImageView();
        iv.setImage(img);
        iv.setFitWidth(View.TILE_SIZE);
        iv.setFitHeight(View.TILE_SIZE);
        iv.setPreserveRatio(true);

        //Place the image in the correct Position

        move(this.coordinates);
        getChildren().add(iv);
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
        GridPane.setColumnIndex(this, coordinates.getX());
        GridPane.setRowIndex(this, View.Y_OFFSET - coordinates.getY());
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