package maze.graph;

import java.io.Serial;
import java.io.Serializable;

/**
 * Edge of Graph
 * @author Nikolai Ogorodnik
 * @param <T> Vertex type. Any reference or primitive type.
 */
public class Edge<T extends Comparable<T> & Serializable>
        implements Comparable<Edge<T>>, Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private static final int DEFAULT_WEIGHT = 1;

    /**
     * First connected Vertex or initial Vertex for directed graphs.
     */
    private final T vertexFrom;

    /**
     * Second connected Vertex or destination Vertex for directed graphs.
     */
    private final T vertexTo;

    /**
     * Weight of an Edge.
     */
    private final double weight;

    /**
     * Constructs Edge for undirected weighted graphs.
     *
     * @param vertexFrom Initial Vertex.
     * @param vertexTo Destination Vertex.
     * @param weight Weight of an Edge.
     */
    public Edge(T vertexFrom, T vertexTo, double weight) {
        this.vertexFrom = vertexFrom;
        this.vertexTo = vertexTo;
        this.weight = weight;
    }

    /**
     * Constructs Edge for directed weighted graphs.
     *
     * @param vertexTo Destination Vertex.
     * @param weight Weight of an Edge.
     */
    public Edge(T vertexTo, double weight) {
        this.vertexFrom = null;
        this.vertexTo = vertexTo;
        this.weight = weight;
    }

    /**
     * Constructs Edge for directed unweighted graphs.
     *
     * @param vertexTo Destination Vertex.
     */
    public Edge(T vertexTo) {
        this.vertexFrom = null;
        this.vertexTo = vertexTo;
        this.weight = DEFAULT_WEIGHT;
    }

    /**
     * @return first connected Vertex or initial Vertex for directed graphs.
     */
    public T getVertexFrom() {
        return vertexFrom;
    }

    /**
     * @return second connected Vertex or destination Vertex for directed graphs.
     */
    public T getVertexTo() {
        return vertexTo;
    }

    /**
     * @return weight of an Edge.
     */
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge<?> edge = (Edge<?>) o;

        if (!getVertexFrom().equals(edge.getVertexFrom())) return false;
        return getVertexTo().equals(edge.getVertexTo());
    }

    @Override
    public int hashCode() {
        int result = getVertexFrom().hashCode();
        result = 31 * result + getVertexTo().hashCode();
        return result;
    }

    @Override
    public int compareTo(Edge<T> o) {
        int result = Double.compare(this.weight, o.getWeight());

        if (result == 0) {
            if (this.getVertexTo().compareTo(o.getVertexTo()) == 0) {
                result = this.getVertexFrom().compareTo(o.getVertexFrom());
            } else {
                result = this.getVertexTo().compareTo(o.getVertexTo());
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "vertexFrom=" + vertexFrom +
                ", vertexTo=" + vertexTo +
                ", weight=" + weight +
                '}';
    }
}

