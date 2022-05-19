package maze;

import maze.graph.Edge;
import maze.graph.Graph;

import java.io.Serializable;
import java.util.*;

public class Maze implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int height;

    private final int width;

    private Cell startingPoint;

    private Cell finishPoint;

    private final CellType[][] grid;

    private Graph<Cell> maze_graph;

    private List<Cell> solution;

    public Maze(int size) throws IllegalArgumentException {
        if (size < 3) throw new IllegalArgumentException("Too small maze size");
        this.height = size;
        this.width = size;
        this.grid = new CellType[size][size];
        this.maze_graph = new Graph<>();
    }

    public Maze(int height, int width) throws IllegalArgumentException {
        if (height < 3 || width < 3) throw new IllegalArgumentException("Too small maze size");
        this.height = height;
        this.width = width;
        this.grid = new CellType[height][width];
        this.maze_graph = new Graph<>();
    }

    public boolean isMazeExists() {
        return height > 2 && width > 2;
    }

    public Graph<Cell> getMaze_graph() {
        return maze_graph;
    }

    public Optional<Cell> findStartingPoint() {
        Cell found = null;

        for (Cell cell : maze_graph.getVerticesSet()) {
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

        for (Cell cell : maze_graph.getVerticesSet()) {
            grid[cell.getX()][cell.getY()] = cell.getCellType();
        }
    }

    public void fillGrid() {
        final int neighbourFactor = 9;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (!(i == height - 1
                        || j == width - 1
                        || i % 2 == 0
                        || j % 2 == 0)) {
                    maze_graph.addVertex(new Cell(i, j, CellType.EMPTY));
                }
            }
        }

        var vertexes = maze_graph.getEntireGraph();

        Cell[] neighbourCells = new Cell[4];

        for (Map.Entry<Cell, Set<Edge<Cell>>> entry : vertexes.entrySet()) {

            Cell current = entry.getKey();
            neighbourCells[0] = new Cell(current.getX() - 2, current.getY(), CellType.EMPTY);
            neighbourCells[1] = new Cell(current.getX() + 2, current.getY(), CellType.EMPTY);
            neighbourCells[2] = new Cell(current.getX(), current.getY() - 2, CellType.EMPTY);
            neighbourCells[3] = new Cell(current.getX(), current.getY() + 2, CellType.EMPTY);

            for (Cell neighbour : neighbourCells) {
                if (vertexes.containsKey(neighbour)) {
                    maze_graph.addUndirectedWeightedEdge(current, neighbour, (int) (Math.random() * neighbourFactor));
                }
            }
        }

        Cell startCell = new Cell(1, 0, CellType.START);
        startingPoint = startCell;
        maze_graph.addVertex(startCell);
        maze_graph.addUndirectedWeightedEdge(
                startCell,
                new Cell(1, 1, CellType.EMPTY),
                (int) (Math.random() * neighbourFactor)
        );

        boolean exitFound = false;
        int i = height - 1;

        while (i > 0 && !exitFound) {
            int j = width - 1;

            while (j > 0 && !exitFound) {
                Cell currentCell = new Cell(i, j, CellType.EMPTY);

                exitFound = maze_graph.getVerticesSet().contains(currentCell);

                if (exitFound) {

                    Cell exitCell = new Cell(i, width - 1, CellType.EXIT);
                    finishPoint = exitCell;

                    maze_graph.addVertex(exitCell);
                    maze_graph.addUndirectedWeightedEdge(
                            currentCell,
                            exitCell,
                            (int) (Math.random() * neighbourFactor)
                    );
                }
                j--;
            }
            i--;
        }

        var prim = new Prim<>(maze_graph);

        maze_graph = prim.run(startCell);

        Set<Edge<Cell>> edgeSet = new HashSet<>();

        for (Set<Edge<Cell>> edges : maze_graph.getEntireGraph().values()) {
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
                maze_graph.deleteUndirectedEdge(edge.getVertexFrom(), edge.getVertexTo());
                maze_graph.addVertex(newCell.get());
                maze_graph.addUndirectedWeightedEdge(edge.getVertexFrom(), newCell.get(), 1);
                maze_graph.addUndirectedWeightedEdge(newCell.get(), edge.getVertexTo(), 1);
            }
        }

    }

    private void solveMaze() {
        Dijkstra<Cell> dijkstra = new Dijkstra<>();

        solution = dijkstra.getShortestPath(maze_graph, startingPoint, finishPoint);
    }

    public String showSolution() {
        solveMaze();
        for (CellType[] cellRow : grid) {
            Arrays.fill(cellRow, CellType.WALL);
        }

        for (Cell cell : maze_graph.getVerticesSet()) {
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
