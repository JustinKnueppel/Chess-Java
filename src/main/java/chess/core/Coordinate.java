package chess.core;

import java.util.ArrayList;
import java.util.List;

public record Coordinate(chess.core.Coordinate.File file, chess.core.Coordinate.Rank rank) {
    public enum File {
        A, B, C, D, E, F, G, H;

        public static File from(int index) {
            return switch (index) {
                case 0 -> File.A;
                case 1 -> File.B;
                case 2 -> File.C;
                case 3 -> File.D;
                case 4 -> File.E;
                case 5 -> File.F;
                case 6 -> File.G;
                case 7 -> File.H;
                default -> File.H;
            };
        }

        /**
         * Get the index representation of the file.
         *
         * @return 0-7 depending on file
         */
        public int toIndex() {
            return switch (this) {
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

        public static Rank from(int index) {
            return switch (index) {
                case 0 -> Rank.ONE;
                case 1 -> Rank.TWO;
                case 2 -> Rank.THREE;
                case 3 -> Rank.FOUR;
                case 4 -> Rank.FIVE;
                case 5 -> Rank.SIX;
                case 6 -> Rank.SEVEN;
                case 7 -> Rank.EIGHT;
                default -> Rank.EIGHT;
            };
        }

        /**
         * Get the index representation of the rank.
         *
         * @return 0-7 depending on rank
         */
        public int toIndex() {
            return switch (this) {
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

    public File getFile() {
        return this.file;
    }

    public Rank getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return this.file.toString() + this.rank.toString();
    }

    /**
     * Retrieve a collection of all coordinates.
     *
     * @return an exhaustive list of all coordinates
     */
    public static Iterable<Coordinate> all() {
        List<Coordinate> coordinates = new ArrayList<>();
        for (File file : File.values()) {
            for (Rank rank : Rank.values()) {
                coordinates.add(new Coordinate(file, rank));
            }
        }
        return coordinates;
    }
}
