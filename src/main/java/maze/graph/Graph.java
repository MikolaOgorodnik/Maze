package maze.graph;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Graph class represented with adjacency list.
 *<br><br>
 * @author Nikolai Ogorodnik
 * @version 1.5
 */
public class Graph<T extends Comparable<T> & Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * A Map of Vertices as a keys with Set of adjacent Edges.
     *
     */
    private final Map<T, Set<Edge<T>>> adjVertices;

    /**
     * Constructs an empty graph.
     */
    public Graph() {
        this.adjVertices = new HashMap<>();
    }

    /**
     * Adds new Vertex to a graph.
     *<br><br>
     * @param vertex a vertex to be added.
     */
    public void addVertex(T vertex) {
        this.adjVertices.putIfAbsent(vertex, new HashSet<>());
    }

    /**
     * Returns adjacent vertices.
     *<br><br>
     * @param vertex a vertex whose edges to be returned.
     * @return {@code Set} of adjacent edges.
     */
    public Set<Edge<T>> getAdjVertices(T vertex) {
        return adjVertices.get(vertex);
    }

    /**
     * Returns {@code Map} contains whole graph collection.
     *<br><br>
     * @return {@code Map} contains whole graph collection.
     */
    public Map<T, Set<Edge<T>>> getEntireGraph() {
        return adjVertices;
    }

    /**
     * A complete {@code Set} of edges.
     *<br><br>
     * @return a complete {@code Set} of edges.
     */
    public Set<T> getVerticesSet() {
        return adjVertices.keySet();
    }

    /**
     * This method adds an <b>undirected unweighted</b> edge to graph.
     *<br><br>
     * @param vertex1 vertex from
     * @param vertex2 vertex to
     */
    public void addUndirectedUnweightedEdge(T vertex1, T vertex2) {
        this.adjVertices.get(vertex1).add(new Edge<>(vertex2));
        this.adjVertices.get(vertex2).add(new Edge<>(vertex1));
    }

    /**
     * This method adds an <b>undirected weighted</b> edge to graph.
     *<br><br>
     * @param vertex1 vertex from
     * @param vertex2 vertex to
     * @param weight weight of an edge
     */
    public void addUndirectedWeightedEdge(T vertex1, T vertex2, double weight) {
        this.adjVertices.get(vertex1).add(new Edge<>(vertex1, vertex2, weight));
        this.adjVertices.get(vertex2).add(new Edge<>(vertex2, vertex1, weight));
    }

    /**
     *This method adds an <b>directed unweighted</b> edge to graph.
     *<br><br>
     * @param vertex1 vertex from
     * @param vertex2 vertex to
     */
    public void addDirectedUnweightedEdge(T vertex1, T vertex2) {
        this.adjVertices.get(vertex1).add(new Edge<>(vertex2));
    }

    /**
     * This method adds an <b>directed weighted</b> edge to graph.
     *<br><br>
     * @param vertex1 vertex from
     * @param vertex2 vertex to
     * @param weight weight of an edge
     */
    public void addDirectedWeightedEdge(T vertex1, T vertex2, long weight) {
        this.adjVertices.get(vertex1).add(new Edge<>(vertex2, weight));
    }

    /**
     *This method delete an <b>undirected</b> edge to graph.
     *<br><br>
     * @param vertex1 vertex from
     * @param vertex2 vertex to
     */
    public void deleteUndirectedEdge(T vertex1, T vertex2) {
        adjVertices.get(vertex1).remove(new Edge<T>(vertex1, vertex2, 0));
        adjVertices.get(vertex2).remove(new Edge<T>(vertex2, vertex1, 0));
    }

    /**
     * Compares <b>adjacent vertices</b> of a graph with the other graph.
     * Returns {@code true} if the compared graphs are the same, {@code false} otherwise.
     *<br><br>
     * @param o compared graph
     * @return {@code true} if the compared graphs are the same, {@code false} otherwise.
     */
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
