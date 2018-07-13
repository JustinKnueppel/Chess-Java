import Game.Board;
import Game.Coordinates;
import Game.MoveType;
import Game.Pieces.Piece;
import Game.Pieces.PieceType;
import Game.TeamColor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Application;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {
    @BeforeEach
    void init() {
        Application app = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                primaryStage.setScene(new Scene(new Pane()));
                primaryStage.show();
            }
        };
    }
    @Test
    public void testUp() {
        /*
        Initialize custom board
         */
        Board board = new Board();
        Coordinates startCoords = new Coordinates(4, 4);
        Piece king = board.getPieceByName(PieceType.KING, startCoords, TeamColor.WHITE);
        board.getGrid()[startCoords.getX()][startCoords.getY()].putPiece(king);
        /*
        Move Piece
         */
        Coordinates newCoords = new Coordinates(4, 5);
        board.move(king, newCoords, MoveType.NORMAL);

        assertEquals(king, board.getGrid()[newCoords.getX()][newCoords.getY()].getPiece());
    }


}