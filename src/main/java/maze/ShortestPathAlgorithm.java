package maze;

import maze.graph.Graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction for shortest path algorithm.
 *
 * @param <T> Vertex type. Any reference or primitive type.
 */
public abstract class ShortestPathAlgorithm<T extends Comparable<T> & Serializable> {
    private ArrayList<T> shortestPath = new ArrayList<>();

    /**
     *Returns the shortest path.
     *
     * @param src Starting vertex
     * @param destination Finishing vertex
     * @param path List of possible paths.
     * @return List of possible paths.
     */
    protected ArrayList<T> processPath(
            T src,
            T destination,
            ArrayList<T> path) {

        int index = path.indexOf(destination);
        T source = path.get(index + 1);

        shortestPath.add(0, destination);

        if (source.equals(src)) {
            shortestPath.add(0, src);
            return shortestPath;
        } else {
            return processPath(src, source, path);
        }
    }

    /**
     * Abstract method for implementing algorithm that searches the shortest
     * path between given vertices on given graph.<br>
     * Returns {@code List} of vertices representing the shortest path.
     *
     * @param graph Processed {@link Graph}.
     * @param source Starting vertex.
     * @param destination Finishing vertex.
     * @return {@code List} of vertices representing the shortest path.
     */
    public abstract List<T> getShortestPath(Graph<T> graph, T source, T destination);
}
