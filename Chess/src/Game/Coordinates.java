package Game;

public class Coordinates {
    /*
    Name for indices for rows and columns on board
     */
    private final String LETTERS = "ABCDEFGH";
    private final String NUMBERS = "12345678";
    /*
    Data stored in object
     */
    private int x;
    private int y;

    /**
     * Constructor based on integer values for the coordinates.
     * @param x
     *      Row on the board
     * @param y
     *      Column on the board
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor based on String representation of coordinates of a chess board.
     * @param id
     *      [A-H][1-8] representation of coordinates on the board
     */
    public Coordinates(String id) {
        this.x = LETTERS.indexOf(id.charAt(0));
        this.y = NUMBERS.indexOf(id.charAt(1));
    }

    /**
     * Gives index of alpha component to board ID.
     * @return this.x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gives index of numerical component to board ID.
     * @return this.y
     */
    public int getY() {
        return this.y;
    }

    /**
     * Gives String representation of numerical coordinates on a chess board.
     * @return [A-H][1-8] representation of given tile on a chess board
     */
    public String getID() {
        return new String(new char[]{LETTERS.charAt(x), NUMBERS.charAt(y)});

    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Coordinates)) {
            return false;
        }
        Coordinates c = (Coordinates)o;
        return c.getX() == this.getX() && c.getY() == this.getY();
    }

}