package maze.model;

/**
 * Type of cell.
 *
 * @author Nikolai Ogorodnik
 */
public enum CellType {

    /**
     * Wall.
     */
    WALL("██"),

    /**
     * Empty space.
     */
    EMPTY("  "),

    /**
     * Starting point of maze.
     */
    START("  "),

    /**
     * Exit point of maze.
     */
    EXIT("  "),

    /**
     * Empty space marked as a part of found path.
     */
    PATH("//");

    private final String cellImage;

    CellType(String cellImage) {
        this.cellImage = cellImage;
    }

    @Override
    public String toString() {
        return cellImage;
    }

}