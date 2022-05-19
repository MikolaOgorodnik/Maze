package maze.graph;

import java.io.Serializable;
import java.util.*;


/**
 * Graph class represented with adjacency list
 *
 * @author Nikolai Ogorodnik
 * @version 1.5
 */
public class Graph<T extends Comparable<T> & Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A Map of Vertices as a keys with List of adjacent Edges
     */
    private final Map<T, Set<Edge<T>>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<>();
    }

    /**
     * Add new Vertex to graph
     * @param vertex
     */
    public void addVertex(T vertex) {
        this.adjVertices.putIfAbsent(vertex, new HashSet<>());
    }

    /**
     * Returns adjacent vertices
     *
     * @param vertex
     * @return
     */
    public Set<Edge<T>> getAdjVertices(T vertex) {
        return adjVertices.get(vertex);
    }

    public Map<T, Set<Edge<T>>> getEntireGraph() {
        return adjVertices;
    }

    public Set<T> getVerticesSet() {
        return adjVertices.keySet();
    }

    public void addUndirectedUnweightedEdge(T vertex1, T vertex2) {
        this.adjVertices.get(vertex1).add(new Edge<>(vertex2));
        this.adjVertices.get(vertex2).add(new Edge<>(vertex1));
    }

    public void addUndirectedWeightedEdge(T vertex1, double weight1, T vertex2, double weight2) {
        this.adjVertices.get(vertex1).add(new Edge<>(vertex1, vertex2, weight2));
        this.adjVertices.get(vertex2).add(new Edge<>(vertex2, vertex1, weight1));
    }

    public void addUndirectedWeightedEdge(T vertex1, T vertex2, double weight) {
        this.adjVertices.get(vertex1).add(new Edge<>(vertex1, vertex2, weight));
        this.adjVertices.get(vertex2).add(new Edge<>(vertex2, vertex1, weight));
    }

    public void addDirectedUnweightedEdge(T vertex1, T vertex2) {
        this.adjVertices.get(vertex1).add(new Edge<>(vertex2));
    }

    public void addDirectedWeightedEdge(T vertex1, T vertex2, long weight) {
        this.adjVertices.get(vertex1).add(new Edge<>(vertex2, weight));
    }

    public void deleteUndirectedEdge(T vertex1, T vertex2) {
        adjVertices.get(vertex1).remove(new Edge<T>(vertex1, vertex2, 0));
        adjVertices.get(vertex2).remove(new Edge<T>(vertex2, vertex1, 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Graph<?> graph)) return false;
        return adjVertices.equals(graph.adjVertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjVertices);
    }
}
