public class Controller {
    private View view;
    private Game model;

    public Controller(View view) {
        this.view = view;
        this.model = new Game();
        updateBoard();
    }

    private void updateBoard() {
        for (int i = 0; i < Board.GRID_SIZE; i++) {
            for (int j = 0; j < Board.GRID_SIZE; j++) {
                Square square = this.model.getBoard().getSquare(Coordinate.fromIndices(i, j));
                if (square.occupied()) {
                    this.view.placePiece(i, j, square.getPiece());
                }
            }
        }
    }

    /*
     * ======================
     * == Public interface ==
     * ======================
     */

    public void processMove(double startX, double startY, double endX, double endY) {
        //TODO: convert to coordinates
        //TODO: Check for move legality
        //TODO: Make move
    }
}
