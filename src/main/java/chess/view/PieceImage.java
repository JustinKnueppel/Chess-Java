package chess.view;

import chess.core.PieceType;
import javafx.scene.image.Image;

public class PieceImage extends Image {
    private final PieceType pieceType;

    public PieceImage(String url, PieceType pieceType) {
        super(url);
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
