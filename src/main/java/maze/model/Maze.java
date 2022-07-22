package maze.model;

import maze.graph.Dijkstra;
import maze.graph.Edge;
import maze.graph.Graph;
import maze.graph.Prim;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Maze.
 *
 * @author Nikolai Ogorodnik
 */
@Component
public class Maze implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int NEIGHBOUR_FACTOR = 9;

    public static final int DEFAULT_HEIGHT = 15;

    public static final int DEFAULT_WIDTH = 15;

    /**
     * Height of a Maze.
     */
    private int height;

    /**
     * Width of a Maze.
     */
    private int width;

    /**
     * Entry of a maze.
     */
    private Cell startingPoint;

    /**
     * Exit of a Maze.
     */
    private Cell finishPoint;

    /**
     * Array representation of a Maze for exposition.
     */
    private Cell[][] grid;

    /**
     * {@link Graph} associated with a maze.
     */
    private Graph<Cell> mazeGraph;

    /**
     * A chain of {@link Cell}s with the shortest path from Entry point to Exit.
     */
    private List<Cell> solution;

    /**
     * Randomizer for Maze generation.
     */
    private final Random rnd;

    public Maze() {
        grid = new Cell[0][0];
        this.mazeGraph = new Graph<>();
        this.rnd = new Random();
    }

    /**
     * Constructs new Maze of given size
     *
     * @param height Vertical Maze size.
     * @param width  Horizontal Maze size
     * @throws IllegalArgumentException when {@code height} or {@code width} less than 3 {@link Cell}s
     *                                  with message <b>"Too small maze size"</b>
     */
    public Maze(int height, int width) throws IllegalArgumentException {
        this();
        if (height < 3 || width < 3) throw new IllegalArgumentException("Too small maze size");
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        initStartingFinishingCells();
        generateMaze();
    }

    /**
     * Checks if current Maze object contains Maze of proper size.
     *
     * @return {@code True} if current Maze object contains Maze of proper size or {@code False} if not.
     */
    public boolean isMazeExists() {
        return height > 2 && width > 2;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Uses Dijkstra's shortest path algorithm to solve the Maze.
     */
    public void solveMaze() {
        if (Optional.ofNullable(solution).isEmpty()) {
            Dijkstra<Cell> dijkstra = new Dijkstra<>();

            solution = dijkstra.getShortestPath(mazeGraph, startingPoint, finishPoint);

            solution
                    .stream()
                    .filter(x -> x.getCellType() != CellType.EXIT && x.getCellType() != CellType.START)
                    .forEach(x -> grid[x.getY()][x.getX()].setCellType(CellType.PATH));

            setBorderClassToVertices();
        }
    }

    private void initStartingFinishingCells() {
        int startingX = 0;
        int finishingX = width - 1;
        int startingY = 0;
        int finishingY = height - 1;

        if (rnd.nextBoolean()) {
            // X axis selected
            startingX = rnd.nextInt(1, width / 2);
        } else {
            // Y axis
            startingY = rnd.nextInt(1, height / 2);
        }

        if (rnd.nextBoolean()) {
            // X axis selected

            finishingX = rnd.nextInt(width - 1);
        } else {
            // Y axis
            finishingY = rnd.nextInt(height - 1);
        }

        startingPoint = new Cell(startingY, startingX, CellType.START);
        finishPoint = new Cell(finishingY, finishingX, CellType.EXIT);
    }

    private CellType getCorrespondingCellType(int i, int j) {
        CellType result = CellType.PASSAGE;
        if (startingPoint.getY() == i && startingPoint.getX() == j) {
            result = CellType.START;
        } else if (finishPoint.getY() == i && finishPoint.getX() == j) {
            result = CellType.EXIT;
        }

        return result;
    }

    private void initGraph() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell currentCell = new Cell(i, j, getCorrespondingCellType(i, j));
                List<Cell> neighbourCells = new ArrayList<>();

                int above = i - 1;
                int below = i + 1;
                int left = j - 1;
                int right = j + 1;

                if (above >= 0) neighbourCells.add(new Cell(above, j, getCorrespondingCellType(above, j)));
                if (below < height) neighbourCells.add(new Cell(below, j, getCorrespondingCellType(below, j)));
                if (left >= 0) neighbourCells.add(new Cell(i, left, getCorrespondingCellType(i, left)));
                if (right < width) neighbourCells.add(new Cell(i, right, getCorrespondingCellType(i, right)));

                for (Cell cell : neighbourCells) {
                    mazeGraph.addUndirectedWeightedEdge(
                            currentCell,
                            cell,
                            (int) (Math.abs(rnd.nextGaussian()) * NEIGHBOUR_FACTOR)
                    );
                }
            }
        }
    }

    private void generateMaze() {
        initGraph();

        var prim = new Prim<>(mazeGraph);

        mazeGraph = prim.run(startingPoint);

        setBorderClassToVertices();

        graphToMaze();
    }

    private void setBorderClassToVertices() {
        for (Cell vertex : mazeGraph.getVerticesSet()) {

            StringJoiner borderClass = new StringJoiner(" ");
            borderClass.add("cell");

            String[] sides = new String[]{"left", "top", "right", "bottom"};

            int currentX = vertex.getX();
            int currentY = vertex.getY();

            for (Edge<Cell> edge : mazeGraph.getAdjVertices(vertex)) {
                Cell adjVertex = edge.getVertexTo();
                if (currentY - adjVertex.getY() < 0) {
                    sides[3] = "nobottom";
                } else if (currentY - adjVertex.getY() > 0) {
                    sides[1] = "notop";
                }

                if (currentX - adjVertex.getX() < 0) {
                    sides[2] = "noright";
                } else if (currentX - adjVertex.getX() > 0) {
                    sides[0] = "noleft";
                }
            }

            // Check start & finish cells
            if (vertex.getCellType() == CellType.START
                    || vertex.getCellType() == CellType.EXIT
            ) {
                borderClass.add("edge");

                if (vertex.getY() == 0) {
                    sides[1] = "notop";
                } else {
                    sides[3] = "nobottom";
                }

                if (vertex.getX() == 0) {
                    sides[2] = "noleft";
                } else {
                    sides[0] = "noright";
                }
            }

            if (vertex.getCellType() == CellType.PATH) {
                borderClass.add("solution");
            }

            for (String side : sides) {
                borderClass.add(side);
            }

            vertex.setBorderClass(borderClass.toString());
        }
    }

    /**
     * Converts associated {@link Graph} to Maze.
     */
    private void graphToMaze() {
        for (Cell cell : mazeGraph.getVerticesSet()) {
            grid[cell.getY()][cell.getX()] = cell;
        }
    }

    @Override
    public String toString() {
        graphToMaze();
        StringBuilder thisMaze = new StringBuilder("\n");
        for (Cell[] cellsRow : grid) {
            for (int j = 0; j < width; j++) {
                thisMaze.append(cellsRow[j]);
            }
            thisMaze.append('\n');
        }

        return thisMaze.toString();
    }
}
