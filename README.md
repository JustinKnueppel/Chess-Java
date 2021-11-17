# Chess

This repository recreates the game of [chess](https://en.wikipedia.org/wiki/Chess) using Java with the JavaFX GUI library.

## Structure

```
.
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── README.md
└── src
    └── main
        ├── java
        │   ├── chess
        │   │   ├── core
        │   │   │   ├── Board.java
        │   │   │   ├── Coordinate.java
        │   │   │   ├── Game.java
        │   │   │   ├── Piece.java
        │   │   │   ├── PieceType.java
        │   │   │   ├── Square.java
        │   │   │   └── TeamColor.java
        │   │   └── view
        │   │       ├── Controller.java
        │   │       ├── LocatedImage.java
        │   │       └── View.java
        │   └── module-info.java
        └── resources
            └── images
                ├── Black-Bishop.png
                ├── Black-King.png
                ├── Black-Knight.png
                ├── Black-Pawn.png
                ├── Black-Queen.png
                ├── Black-Rook.png
                ├── White-Bishop.png
                ├── White-King.png
                ├── White-Knight.png
                ├── White-Pawn.png
                ├── White-Queen.png
                └── White-Rook.png
```

This game is created using the Model-View-Controller (MVC) architecture. The entry point to run is the chess.view.View class which will load the GUI.

## TODO

### Features

- Undo button
- Export/load PGN
- Export/load FEN

