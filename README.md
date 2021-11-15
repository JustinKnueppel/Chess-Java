# Chess

This repository recreates the historic game of [chess](https://en.wikipedia.org/wiki/Chess) using Java with the JavaFX GUI library.

## Structure

```
.
├── Chess
│   ├── Chess.iml
│   ├── Images
│   │   ├── BlackBishop.png
│   │   ├── BlackKing.png
│   │   ├── BlackKnight.png
│   │   ├── BlackPawn.png
│   │   ├── BlackQueen.png
│   │   ├── BlackRook.png
│   │   ├── WhiteBishop.png
│   │   ├── WhiteKing.png
│   │   ├── WhiteKnight.png
│   │   ├── WhitePawn.png
│   │   ├── WhiteQueen.png
│   │   └── WhiteRook.png
│   └── src
│       ├── chess.core.Board.java
│       ├── chess.view.Controller.java
│       ├── chess.core.Coordinate.java
│       ├── chess.core.Game.java
│       ├── chess.core.Piece.java
│       ├── chess.core.PieceType.java
│       ├── chess.core.Square.java
│       ├── chess.core.TeamColor.java
│       └── chess.view.View.java
├── out
└── README.md

```

This game is created using the Model-chess.view.View-chess.view.Controller (MVC) design. The entry point to run is the chess.view.View class which will load the GUI.

## TODO

### Features

- Undo button
- Export/load PGN
- Export/load FEN
