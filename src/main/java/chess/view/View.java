package chess.view;

import chess.core.Piece;
import chess.core.TeamColor;
import javafx.scene.Scene;

public interface View {
    /**
     * Associate a controller for the view to use.
     * @param controller takes actions based on view inputs
     */
    void setController(Controller controller);

    /**
     * Retrieve the scene to display to the user.
     * @return the chess game scene
     */
    Scene getScene();

    /**
     * Remove all pieces from the board
     */
    void clearBoard();

    /**
     * Place a piece on the board.
     * @param x represents the x coordinate on the board
     * @param y represents the y coordinate on the board
     * @param piece is the piece to be placed at the desired coordinates
     */
    void placePiece(int x, int y, Piece piece);

    /**
     * Display promotion options to the user.
     * @param teamColor the team who will be promoting
     */
    void displayPromotionOptions(TeamColor teamColor);

    /**
     * Hide the promotion options from the user.
     */
    void disablePromotionOptions();
}
