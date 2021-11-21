package chess.view;

import chess.core.Piece;
import chess.core.TeamColor;
import javafx.scene.Scene;

public interface View {
    public void setController(Controller controller);
    public Scene getScene();
    public void clearBoard();
    public void placePiece(int x, int y, Piece piece);
    public void displayPromotionOptions(TeamColor teamColor);
    public void disablePromotionOptions();
}
