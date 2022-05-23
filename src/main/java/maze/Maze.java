package maze;

import maze.graph.Edge;
import maze.graph.Graph;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Maze implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int NEIGHBOUR_FACTOR = 9;

    private final int height;

    private final int width;

    private Cell startingPoint;

    private Cell finishPoint;

    private final CellType[][] grid;

    private Graph<Cell> mazeGraph;

    private List<Cell> solution;

    private final Random rnd;

    public Maze(int height, int width) throws IllegalArgumentException {
        if (height < 3 || width < 3) throw new IllegalArgumentException("Too small maze size");
        this.height = height;
        this.width = width;
        this.grid = new CellType[height][width];
        this.mazeGraph = new Graph<>();
        this.rnd = new Random();
    }

    public boolean isMazeExists() {
        return height > 2 && width > 2;
    }

    public Optional<Cell> findStartingPoint() {
        Cell found = null;

        for (Cell cell : mazeGraph.getVerticesSet()) {
            if (cell.getCellType().equals(CellType.START)) {
                found = cell;
            }
        }

        return Optional.ofNullable(found);
    }

    private void graphToMaze() {
        for (CellType[] cellRow : grid) {
            Arrays.fill(cellRow, CellType.WALL);
        }

        for (Cell cell : mazeGraph.getVerticesSet()) {
            grid[cell.getX()][cell.getY()] = cell.getCellType();
        }
    }

    private void fillMazeByEmptyCells() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (!(i == height - 1
                        || j == width - 1
                        || i % 2 == 0
                        || j % 2 == 0)) {
                    mazeGraph.addVertex(new Cell(i, j, CellType.EMPTY));
                }
            }
        }

        var vertexes = mazeGraph.getEntireGraph();

        Cell[] neighbourCells = new Cell[4];

        for (Map.Entry<Cell, Set<Edge<Cell>>> entry : vertexes.entrySet()) {

            Cell current = entry.getKey();
            neighbourCells[0] = new Cell(current.getX() - 2, current.getY(), CellType.EMPTY);
            neighbourCells[1] = new Cell(current.getX() + 2, current.getY(), CellType.EMPTY);
            neighbourCells[2] = new Cell(current.getX(), current.getY() - 2, CellType.EMPTY);
            neighbourCells[3] = new Cell(current.getX(), current.getY() + 2, CellType.EMPTY);

            for (Cell neighbour : neighbourCells) {
                if (vertexes.containsKey(neighbour)) {
                    mazeGraph.addUndirectedWeightedEdge(current, neighbour, (int) (Math.abs(rnd.nextGaussian()) * NEIGHBOUR_FACTOR));
                }
            }
        }
    }

    private void findExit(Cell startCell) {
        startingPoint = startCell;
        mazeGraph.addVertex(startCell);
        mazeGraph.addUndirectedWeightedEdge(
                startCell,
                new Cell(1, 1, CellType.EMPTY),
                (int) (Math.abs(rnd.nextGaussian()) * NEIGHBOUR_FACTOR)
        );

        boolean exitFound = false;
        int i = height - 1;

        while (i > 0 && !exitFound) {
            int j = width - 1;

            while (j > 0 && !exitFound) {
                Cell currentCell = new Cell(i, j, CellType.EMPTY);

                exitFound = mazeGraph.getVerticesSet().contains(currentCell);

                if (exitFound) {

                    Cell exitCell = new Cell(i, width - 1, CellType.EXIT);
                    finishPoint = exitCell;

                    mazeGraph.addVertex(exitCell);
                    mazeGraph.addUndirectedWeightedEdge(
                            currentCell,
                            exitCell,
                            (int) (Math.abs(rnd.nextGaussian()) * NEIGHBOUR_FACTOR)
                    );
                }
                j--;
            }
            i--;
        }
    }

    public void fillGrid() {
        fillMazeByEmptyCells();

        Cell startCell = new Cell(1, 0, CellType.START);

        findExit(startCell);

        var prim = new Prim<>(mazeGraph);

        mazeGraph = prim.run(startCell);

        Set<Edge<Cell>> edgeSet = new HashSet<>();

        for (Set<Edge<Cell>> edges : mazeGraph.getEntireGraph().values()) {
            edgeSet.addAll(edges);
        }

        for (Edge<Cell> edge : edgeSet) {

            int xDiff = edge.getVertexTo().getX() - edge.getVertexFrom().getX();
            int yDiff = edge.getVertexTo().getY() - edge.getVertexFrom().getY();

            Optional<Cell> newCell = Optional.empty();

            if (Math.abs(xDiff) == 2) {
                newCell = Optional.of(new Cell(
                        edge.getVertexTo().getX() + (int) (xDiff > 0 ? -1 * Math.pow(xDiff, 0) : Math.pow(xDiff, 0)),
                        edge.getVertexFrom().getY(),
                        CellType.EMPTY
                ));
            } else if (Math.abs(yDiff) == 2) {
                newCell = Optional.of(new Cell(
                        edge.getVertexFrom().getX(),
                        edge.getVertexTo().getY() + (int) (yDiff > 0 ? -1 * Math.pow(yDiff, 0) : Math.pow(yDiff, 0)),
                        CellType.EMPTY
                ));
            }

            if (newCell.isPresent()) {
                mazeGraph.deleteUndirectedEdge(edge.getVertexFrom(), edge.getVertexTo());
                mazeGraph.addVertex(newCell.get());
                mazeGraph.addUndirectedWeightedEdge(edge.getVertexFrom(), newCell.get(), 1);
                mazeGraph.addUndirectedWeightedEdge(newCell.get(), edge.getVertexTo(), 1);
            }
        }

    }

    private void solveMaze() {
        Dijkstra<Cell> dijkstra = new Dijkstra<>();

        solution = dijkstra.getShortestPath(mazeGraph, startingPoint, finishPoint);
    }

    public String showSolution() {
        solveMaze();
        for (CellType[] cellRow : grid) {
            Arrays.fill(cellRow, CellType.WALL);
        }

        for (Cell cell : mazeGraph.getVerticesSet()) {
            grid[cell.getX()][cell.getY()] = solution.contains(cell) ? CellType.PATH : cell.getCellType();
        }

        StringBuilder solvedMaze = new StringBuilder("\n");
        for (CellType[] cellsRow : grid) {
            for (int j = 0; j < width; j++) {
                solvedMaze.append(cellsRow[j]);
            }
            solvedMaze.append('\n');
        }

        return solvedMaze.toString();
    }

    @Override
    public String toString() {
        graphToMaze();
        StringBuilder thisMaze = new StringBuilder("\n");
        for (CellType[] cellsRow : grid) {
            for (int j = 0; j < width; j++) {
                thisMaze.append(cellsRow[j]);
            }
            thisMaze.append('\n');
        }

        return thisMaze.toString();
    }
}
