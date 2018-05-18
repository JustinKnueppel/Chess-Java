package Game;

import GUI.View;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Piece extends StackPane {
    TeamColor team;
    boolean hasMoved;
    Square square;
    ArrayList<Coordinates> moves;
    PieceType type;
    Board board;
    String URL;
    public static String PRE_IMAGE = "file:\\\\C:\\Users\\justi\\IdeaProjects\\Chess-Java\\Chess\\Images\\";
    /**
     * Get the team of this.
     * @return
     *      TeamColor of this
     */
    public TeamColor getTeam() {
        return this.team;
    }
    void initImage() {
        //Issue initializing square inside of piece consistently
        int file = getSquare().getID().getFile();
        int rank = getSquare().getID().getRank();
        relocate(file * View.TILE_SIZE, rank * View.TILE_SIZE);
        Image img = new Image(URL);
        ImageView iView = new ImageView();
        iView.setImage(img);
        this.getChildren().add(iView);
    }

    /**
     * Moves the given piece to a new square.
     * @param newSquare
     *      The target square for this
     */
    public void move(Square newSquare) {
        this.square.setVacant();
        newSquare.putPiece(this);
        this.hasMoved = true;
    }

    public Square getSquare() {
        return square;
    }

    /**
     * Initial placement of this.
     * @param square
     *      The initial square for this
     */
    public void setSquare(Square square) {
        this.square = square;
    }
    /**
     * Updates the possible moves based on the current state of board, and the piece logic.
     */
    protected abstract void updatePossibleMoves();

    /**
     * Updates this.moves with possible moves, then returns them.
     * @return a list of possible moves.
     */
    ArrayList<Coordinates> getPossibleMoves() {
        updatePossibleMoves();
        return this.moves;
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