package Game;

import GUI.View;
import Game.Pieces.MoveType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {
    TeamColor team;
    boolean hasMoved;
    PieceType type;
    Coordinates coordinates;
    MoveType[][] moveDirections;
    int[][] directions;
    public static String PRE_IMAGE = "file:\\\\C:\\Users\\justi\\IdeaProjects\\Chess-Java\\Chess\\Images\\";

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
        //this should go in board with move logic
        this.directions = new int[][]{{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {1, 0}, {-1, -1}, {0, -1}, {1, -1}};
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
     * Determines the type of move that is possible for each direction.
     */
    abstract void initMoveDirections();

    /**
     * Sets the initial PieceType.
     */
    abstract void initPieceType();
    void initImage() {
        //Issue initializing square inside of piece consistently
        String URL = PRE_IMAGE + this.team.name() + this.type.name() + ".png";
        int file = this.coordinates.getFile();
        int rank = this.coordinates.getRank();
        relocate(file * View.TILE_SIZE - (View.TILE_SIZE / 2), rank * View.TILE_SIZE - (View.TILE_SIZE / 2));
        Image img = new Image(URL);
        setImage(img);
        setFitWidth(View.TILE_SIZE);
        setFitHeight(View.TILE_SIZE);
        setPreserveRatio(true);

    }

    /**
     * Moves the given piece to new Coordinates.
     * @param newCoordinates
     *      The target square for this
     */
    public void move(Coordinates newCoordinates) {
        this.coordinates = newCoordinates;
        this.hasMoved = true;
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
     * @return this.hasMoved
     */
    public boolean hasMoved() {
        return hasMoved;
    }
}