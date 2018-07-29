package Chess.tests;

import Game.Board;
import Game.Coordinates;
import Game.MoveType;
import Game.Pieces.Pawn;
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
    public Collection<DynamicTest> testLegalMoves() {

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int[] move : piece.getPossibleMoves()) {
            Coordinates newCoords = new Coordinates(piece.getCoordinates().getX() + move[0], piece.getCoordinates().getY() + move[1]);
            MoveType moveType = board.isLegalMove(piece, newCoords);
            board.move(piece, newCoords, moveType);

            Executable exec = () -> assertEquals(MoveType.NORMAL, moveType);

            String testName = "Testing MoveType of move to X:" + newCoords.getX() + " Y:" + newCoords.getY();

            DynamicTest dynamicTest = DynamicTest.dynamicTest(testName, exec);

            dynamicTests.add(dynamicTest);
            board.revertMove();

        }

        return dynamicTests;
    }

    @TestFactory
    public Collection<DynamicTest> testLegalCaptures() {

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int[] move : piece.getPossibleMoves()) {
            Coordinates newCoords = new Coordinates(piece.getCoordinates().getX() + move[0], piece.getCoordinates().getY() + move[1]);
            Piece enemyPiece = new Pawn(newCoords, TeamColor.BLACK);
            board.getGrid()[newCoords.getX()][newCoords.getY()].putPiece(enemyPiece);
            MoveType moveType = board.isLegalMove(piece, newCoords);
            board.move(piece, newCoords, moveType);

            Executable exec = () -> assertEquals(MoveType.NORMAL, moveType);

            String testName = "Testing MoveType of capture of pawn on X:" + newCoords.getX() + " Y:" + newCoords.getY();

            DynamicTest dynamicTest = DynamicTest.dynamicTest(testName, exec);

            dynamicTests.add(dynamicTest);
            board.revertMove();

        }

        return dynamicTests;
    }
    @TestFactory
    public Collection<DynamicTest> testMovePlacement() {
        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int[] move : piece.getPossibleMoves()) {
            Coordinates newCoords = new Coordinates(piece.getCoordinates().getX() + move[0], piece.getCoordinates().getY() + move[1]);
            MoveType moveType = board.isLegalMove(piece, newCoords);
            board.move(piece, newCoords, moveType);

            Executable exec = () -> assertEquals(piece, board.getGrid()[newCoords.getX()][newCoords.getY()].getPiece());

            String testName = "Testing move of piece to X: " + newCoords.getX() + " Y: " + newCoords.getY();

            DynamicTest dynamicTest = DynamicTest.dynamicTest(testName, exec);

            dynamicTests.add(dynamicTest);
            board.revertMove();
        }
        return dynamicTests;
    }
    @TestFactory
    public Collection<DynamicTest> testCaptureMovePlacement() {
        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int[] move : piece.getPossibleMoves()) {
            Coordinates newCoords = new Coordinates(piece.getCoordinates().getX() + move[0], piece.getCoordinates().getY() + move[1]);
            Piece enemyPiece = new Pawn(newCoords, TeamColor.BLACK);
            board.getGrid()[newCoords.getX()][newCoords.getY()].putPiece(enemyPiece);
            MoveType moveType = board.isLegalMove(piece, newCoords);
            board.move(piece, newCoords, moveType);

            Executable exec = () -> assertEquals(piece, board.getGrid()[newCoords.getX()][newCoords.getY()].getPiece());

            String testName = "Testing capture of enemy pawn by piece to X: " + newCoords.getX() + " Y: " + newCoords.getY();

            DynamicTest dynamicTest = DynamicTest.dynamicTest(testName, exec);

            dynamicTests.add(dynamicTest);
            board.revertMove();
        }
        return dynamicTests;
    }
    @Test
    public void testOneMove() {
        Coordinates newCoords = new Coordinates(4, 5);
        Piece enemyPiece = new Pawn(newCoords, TeamColor.BLACK);
        board.getGrid()[newCoords.getX()][newCoords.getY()].putPiece(enemyPiece);

        MoveType moveType = board.isLegalMove(piece, newCoords);
        board.move(piece, newCoords, moveType);

        assertEquals(MoveType.NORMAL, moveType);
        assertEquals(piece, board.getGrid()[newCoords.getX()][newCoords.getY()].getPiece());
    }

}