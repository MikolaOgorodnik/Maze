package maze;

public enum CellType {
    WALL("██"),
    EMPTY("  "),
    START("  "),
    EXIT("  "),
    PATH("//");

    private final String cellImage;

    CellType(String cellImage) {
        this.cellImage = cellImage;
    }

    public String getCellImage() {
        return cellImage;
    }

    @Override
    public String toString() {
        return cellImage;
    }

}