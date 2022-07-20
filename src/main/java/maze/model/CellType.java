package maze.model;

/**
 * Type of cell.
 *
 * @author Nikolai Ogorodnik
 */
public enum CellType {

    /**
     * Empty space.
     */
    PASSAGE("  "),

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