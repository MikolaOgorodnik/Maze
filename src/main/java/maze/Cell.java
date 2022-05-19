package maze;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Cell implements Comparable<Cell>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int x;

    private final int y;

    private CellType cellType;

    public Cell(int x, int y, CellType cellType) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public int getX() {
        return x;
    }

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
