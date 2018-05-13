public class Coordinates {
    /*
    Name for indices for rows and columns on board
     */
    private final String LETTERS = "ABCDEFGH";
    private final String NUMBERS = "12345678";
    /*
    Data stored in object
     */
    private int rank;
    private int file;

    /**
     * Constructor based on integer values for the coordinates.
     * @param rank
     *      Row on the board
     * @param file
     *      Column on the board
     */
    public Coordinates(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    /**
     * Constructor based on String representation of coordinates of a chess board.
     * @param id
     *      [A-H][1-8] representation of coordinates on the board
     */
    public Coordinates(String id) {
        this.rank = LETTERS.indexOf(id.charAt(0));
        this.file = NUMBERS.indexOf(id.charAt(1));
    }

    /**
     * Gives index of alpha component to board ID.
     * @return this.rank
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Gives index of numerical component to board ID.
     * @return this.file
     */
    public int getFile() {
        return this.file;
    }

    /**
     * Gives String representation of numerical coordinates on a chess board.
     * @return [A-H][1-8] representation of given tile on a chess board
     */
    public String getID() {
        return indexToID(rank, file);
    }
    /**
     * Takes two indices to LETTERS and NUMBERS and returns the String ID.
     * @param rank
     *      Index for LETTERS
     * @param file
     *      Index for NUMBERS
     * @return String ID based on the two indices
     */
    private String indexToID(int rank, int file) {
        StringBuilder idBuilder = new StringBuilder();
        idBuilder.append(LETTERS.charAt(rank));
        idBuilder.append(NUMBERS.charAt(file));
        return idBuilder.toString();
    }
}