package chess.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Coordinate2 {
    public enum File {
        A, B, C, D, E, F, G, H;

        /**
         * Retrieve the file based on its name.
         * @param s the name of the file
         * @return the file represented by @s
         */
        public static File from(String s) {
            return switch(s.toLowerCase()) {
                case "A" -> File.A;
                case "B" -> File.B;
                case "C" -> File.C;
                case "D" -> File.D;
                case "E" -> File.E;
                case "F" -> File.F;
                case "G" -> File.G;
                case "H" -> File.H;
            };
        }

        /**
         * Get the index representation of the file.
         * @return 0-7 depending on file
         */
        public int toIndex() {
            return switch(this) {
                case A -> 0;
                case B -> 1;
                case C -> 2;
                case D -> 3;
                case E -> 4;
                case F -> 5;
                case G -> 6;
                case H -> 7;
            };
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
    public enum Rank {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT;

        /**
         * Retrieve the rank based on its name.
         * @param s the name of the rank
         * @return the rank represented by @s
         */
        public static Rank from(String s) {
            return switch(s.toLowerCase()) {
                case "1" -> Rank.ONE;
                case "2" -> Rank.TWO;
                case "3" -> Rank.THREE;
                case "4" -> Rank.FOUR;
                case "5" -> Rank.FIVE;
                case "6" -> Rank.SIX;
                case "7" -> Rank.SEVEN;
                case "8" -> Rank.EIGHT;
            };
        }

        /**
         * Get the index representation of the rank.
         * @return 0-7 depending on rank
         */
        public int toIndex() {
            return switch(this) {
                case ONE -> 0;
                case TWO -> 1;
                case THREE -> 2;
                case FOUR -> 3;
                case FIVE -> 4;
                case SIX -> 5;
                case SEVEN -> 6;
                case EIGHT -> 7;
            };
        }

        @Override
        public String toString() {
            return switch (this) {
                case ONE -> "1";
                case TWO -> "2";
                case THREE -> "3";
                case FOUR -> "4";
                case FIVE -> "5";
                case SIX -> "6";
                case SEVEN -> "7";
                case EIGHT -> "8";
            };
        }
    }

    private final File file;
    private final Rank rank;

    public Coordinate2(File file, Rank rank) {
        this.file =  file;
        this.rank = rank;
    }

    /**
     * Temporarily convert coordinates while switching
     * @param coordinate old style coordinate
     * @return new style coordinate
     */
    public static Coordinate2 from(Coordinate coordinate) {
        String coordinateString = coordinate.toString();
        return new Coordinate2(
                File.from(String.valueOf(coordinateString.charAt(0))),
                Rank.from(String.valueOf(coordinateString.charAt(1)))
        );
    }

    /**
     * Convert new coordinate to old coordinate.
     * @return old coordinate representation of new coordinate
     */
    public Coordinate toCoordinate() {
        return Coordinate.fromIndices(file.toIndex(), rank.toIndex());
    }

    @Override
    public String toString() {
        return this.file.toString() + this.rank.toString();
    }

    /**
     * Retrieve a collection of all coordinates.
     * @return an exhaustive list of all coordinates
     */
    public static Iterator<Coordinate2> all() {
        List<Coordinate2> coordinates = new ArrayList<>();
        for (File file : File.values()) {
            for (Rank rank : Rank.values()) {
                coordinates.add(new Coordinate2(file, rank));
            }
        }
        return (Iterator<Coordinate2>) coordinates;
    }
}
