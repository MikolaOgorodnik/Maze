package maze.graph;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Graph class represented with adjacency list.
 *<br>
 * @author Nikolai Ogorodnik
 * @version 1.5
 */
public class Graph<T extends Comparable<T> & Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final double DEFAULT_WEIGHT = 1D;

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
     *<br>
     * @param vertex a vertex to be added.
     */
    public void addVertex(T vertex) {
        this.adjVertices.putIfAbsent(vertex, new HashSet<>());
    }

    /**
     * Returns adjacent vertices.
     *<br>
     * @param vertex a vertex whose edges to be returned.
     * @return {@code Set} of adjacent edges.
     */
    public Set<Edge<T>> getAdjVertices(T vertex) {
        return adjVertices.get(vertex);
    }

    /**
     * Returns {@code Map} contains whole graph collection.
     *<br>
     * @return {@code Map} contains whole graph collection.
     */
    public Map<T, Set<Edge<T>>> getEntireGraph() {
        return adjVertices;
    }

    /**
     * A complete {@code Set} of edges.
     *<br>
     * @return a complete {@code Set} of edges.
     */
    public Set<T> getVerticesSet() {
        return adjVertices.keySet();
    }

    private void addMissingVertices(T label1, T label2){
        if (!adjVertices.containsKey(label1)) addVertex(label1);
        if (!adjVertices.containsKey(label2)) addVertex(label2);
    }

    /**
     * This method adds an <b>undirected unweighted</b> edge to graph.
     *<br>
     * @param label1 vertex from
     * @param label2 vertex to
     */
    public void addUndirectedUnweightedEdge(T label1, T label2) {
        addUndirectedWeightedEdge(label1, label2, DEFAULT_WEIGHT);
    }

    /**
     * This method adds an <b>undirected weighted</b> edge to graph.
     *<br>
     * @param label1 vertex from
     * @param label2 vertex to
     * @param weight weight of an edge
     */
    public void addUndirectedWeightedEdge(T label1, T label2, double weight) {
        addMissingVertices(label1, label2);

        this.adjVertices.get(label1).add(new Edge<>(label1, label2, weight));
        this.adjVertices.get(label2).add(new Edge<>(label2, label1, weight));
    }

    /**
     *This method adds an <b>directed unweighted</b> edge to graph.
     *<br>
     * @param label1 vertex from
     * @param label2 vertex to
     */
    public void addDirectedUnweightedEdge(T label1, T label2) {
        addDirectedWeightedEdge(label1, label2, DEFAULT_WEIGHT);
    }

    /**
     * This method adds an <b>directed weighted</b> edge to graph.
     *<br>
     * @param label1 vertex from
     * @param label2 vertex to
     * @param weight weight of an edge
     */
    public void addDirectedWeightedEdge(T label1, T label2, double weight) {
        addMissingVertices(label1, label2);

        this.adjVertices.get(label1).add(new Edge<>(label1, label2, weight));
    }

    /**
     *This method delete an <b>undirected</b> edge to graph.
     *<br>
     * @param label1 vertex from
     * @param label2 vertex to
     */
    public void deleteUndirectedEdge(T label1, T label2) {
        adjVertices.get(label1).remove(new Edge<T>(label1, label2, 0));
        adjVertices.get(label2).remove(new Edge<T>(label2, label1, 0));
    }

    /**
     * Compares <b>adjacent vertices</b> of a graph with the other graph.
     * Returns {@code true} if the compared graphs are the same, {@code false} otherwise.
     *<br>
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
