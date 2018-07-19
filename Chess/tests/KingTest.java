import Game.Board;
import Game.Coordinates;
import Game.MoveType;
import Game.Pieces.Piece;
import Game.Pieces.PieceType;
import Game.TeamColor;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.function.Executable;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {
    private Board board;
    private Piece piece;
    private Coordinates startCoords;
    @BeforeEach
    void initBoard() {
        /*
        Initialize custom board
         */
        board = new Board();
        board.initializePieces();
        startCoords = new Coordinates(4,4 );
        piece = Piece.getPieceByName(PieceType.KING, startCoords, TeamColor.WHITE);
        board.getGrid()[startCoords.getX()][startCoords.getY()].putPiece(piece);
    }

    @TestFactory
    public Collection<DynamicTest> testMoves() {

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int[] move : piece.getPossibleMoves()) {
            Coordinates newCoords = new Coordinates(piece.getCoordinates().getX() + move[0], piece.getCoordinates().getY() + move[1]);
            MoveType moveType = board.isLegalMove(piece, newCoords);
            board.move(piece, newCoords, moveType);

            Executable exec = () -> assertEquals(moveType, MoveType.NORMAL);

            String testName = "Testing move to X:" + newCoords.getX() + " Y:" + newCoords.getY();

            DynamicTest dynamicTest = DynamicTest.dynamicTest(testName, exec);

            dynamicTests.add(dynamicTest);
            board.revertMove();

        }

        return dynamicTests;
    }

}