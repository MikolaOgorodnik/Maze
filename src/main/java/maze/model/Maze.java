package maze.model;

/**
 * Maze interface describes base functionality of a Maze Generator @ Solver
 *
 * @author Nikolai Ogorodnik
 */
public interface Maze {

    /**
     * Checks if current Maze object contains Maze of proper size.
     *
     * @return {@code True} if current Maze object contains Maze of proper size or {@code False} if not.
     */
    boolean isMazeExists();

    /**
     *
     *
     * @return Character representation of a Maze.
     */
    String showSolution();

    String toString();

}
