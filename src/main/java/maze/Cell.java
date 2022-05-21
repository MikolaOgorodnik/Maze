package maze;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>Represents cell of a maze.</pre>
 * <pre>
 * Contains:
 *   <b>X</b> coordinate of a cell.
 *   <b>Y</b> coordinate of a cell.
 *   <b>Cell Type</b> see: {@link CellType CellType}.
 * </pre>
 * @author Nikolai Ogorodnik
 */
public class Cell implements Comparable<Cell>, Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    /**
     * <b>X</b> coordinate of cell/
     */
    private final int x;

    /**
     * <b>Y</b> coordinate of cell/
     */
    private final int y;

    /**
     * Type of cell.
     * <br>
     * see: {@link CellType CellType}.
     */
    private CellType cellType;

    /**
     * Constructs new Cell of a maze.
     * <br><br>
     * @param x X coordinate of Cell.
     * @param y Y coordinate of Cell.
     * @param cellType type of Cell.
     */
    public Cell(int x, int y, CellType cellType) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
    }

    /**
     * Returns type of Cell.
     * <br><br>
     * @return Cell Type see: {@link CellType CellType}.
     */
    public CellType getCellType() {
        return cellType;
    }

    /**
     * Sets type of Cell.
     * <br><br>
     * @param cellType Cell Type see: {@link CellType CellType}
     */
    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    /**
     * Returns X coordinate of Cell.
     * <br><br>
     * @return X coordinate of Cell.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns Y coordinate of Cell.
     * <br><br>
     * @return Y coordinate of Cell.
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y && cellType == cell.cellType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, cellType);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", cellType=" + cellType +
                '}';
    }

    @Override
    public int compareTo(Cell o) {
        if (this == o) return 0;

        if (this.getX() != o.getX()) {
            return Integer.compare(this.getX(), o.getX());
        } else if (this.getY() != o.getY()) {
            return Integer.compare(this.getY(), o.getY());
        } else {
            return 0;
        }
    }
}
