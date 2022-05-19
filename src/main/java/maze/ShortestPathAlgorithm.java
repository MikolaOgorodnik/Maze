package maze;

import maze.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public abstract class ShortestPathAlgorithm<T extends Comparable<T>> {
    private ArrayList<T> shortestPath = new ArrayList<>();

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

    public abstract List<T> getShortestPath(Graph<T> graph, T source, T destination);
}
