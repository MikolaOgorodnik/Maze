package maze.graph;

import java.io.Serial;
import java.io.Serializable;

/**
 * Edge of Graph
 *
 * @param <T> Vertex type. Any reference or primitive type.
 * @author Nikolai Ogorodnik
 */
public record Edge<T extends Comparable<T> & Serializable>(
        T vertexFrom,
        T vertexTo,
        double weight
) implements Comparable<Edge<T>>, Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

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
        if (!(o instanceof Edge<?> edge)) return false;

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

