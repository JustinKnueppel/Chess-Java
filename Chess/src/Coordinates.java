public class Coordinates {
    private final String LETTERS = "ABCDEFGH";
    private final String NUMBERS = "12345678";
    private int rank;
    private int file;
    public Coordinates(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }
    public int getRank() {
        return this.rank;
    }
    public int getFile() {
        return this.file;
    }
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