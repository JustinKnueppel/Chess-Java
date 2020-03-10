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
│       ├── Board.java
│       ├── Controller.java
│       ├── Coordinate.java
│       ├── Game.java
│       ├── Piece.java
│       ├── PieceType.java
│       ├── Square.java
│       ├── TeamColor.java
│       └── View.java
├── out
└── README.md

```

This game is created using the Model-View-Controller (MVC) design. The entry point to run is the View class which will load the GUI.

## TODO

### Core

- En Passant
- Castling
- Pawn promotion

### Features

- Undo button
- Export/load PGN
- Export/load FEN

