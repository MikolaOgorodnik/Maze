package maze.graph;

import maze.model.ShortestPathAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Nikolai Ogorodnik
 * @param <T> Vertex type. Any reference or primitive type.
 */
public class Dijkstra<T extends Comparable<T> & Serializable> extends ShortestPathAlgorithm<T> {

    private final Logger logger = LoggerFactory.getLogger(Dijkstra.class);

    @Override
    public List<T> getShortestPath(Graph<T> graph, T source, T destination) {

        logger.info("Shortest path calculation started.");

        class Vertex implements Comparable<Vertex>  {
            private T id;
            private Double distance;

            public Vertex(T id, Double distance) {
                this.id = id;
                this.distance = distance;
            }

            public T getId() {
                return id;
            }

            public Double getDistance() {
                return distance;
            }

            public void setDistance(Double distance) {
                this.distance = distance;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Vertex vertex = (Vertex) o;
                return id.equals(vertex.id) && distance.equals(vertex.distance);
            }

            @Override
            public int hashCode() {
                return Objects.hash(id, distance);
            }

            @Override
            public int compareTo(Vertex o) {
                if (this.distance < o.distance)
                    return -1;
                else if (this.distance > o.distance)
                    return 1;
                else
                    return this.getId().compareTo(o.getId());
            }
        }

        final Map<T, Double> distances = new HashMap<>();
        final Map<T, Vertex> previous = new HashMap<>();
        PriorityQueue<Vertex> nodes = new PriorityQueue<>();

        // Setting up initial values
        for (T vertex : graph.getEntireGraph().keySet()) {
            if (vertex.equals(source)) {
                distances.put(vertex, 0d);
                nodes.add(new Vertex(vertex, 0d));
            } else {
                distances.put(vertex, Double.MAX_VALUE);
                nodes.add(new Vertex(vertex, Double.MAX_VALUE));
            }
            previous.put(vertex, null);
        }

        while (!nodes.isEmpty()) {
            Vertex smallest = nodes.poll();
            if (smallest.getId().equals(destination)) {
                final var path = new ArrayList<T>();
                while (previous.get(smallest.getId()) != null) {
                    path.add(smallest.getId());
                    smallest = previous.get(smallest.getId());
                }
                path.add(smallest.getId());
                Collections.reverse(path);
                return path;
            }

            if (distances.get(smallest.getId()) == Double.MAX_VALUE) {
                break;
            }

            for (Edge<T> neighbor : graph.getEntireGraph().get(smallest.getId())) {
                Double alt = distances.get(smallest.getId()) + neighbor.getWeight();
                if (alt < distances.get(neighbor.getVertexTo())) {
                    distances.put(neighbor.getVertexTo(), alt);
                    previous.put(neighbor.getVertexTo(), smallest);

                    for (Vertex n : nodes) {
                        if (n.getId().equals(neighbor.getVertexTo())) {
                            nodes.remove(n);
                            n.setDistance(alt);
                            nodes.add(n);
                            break;
                        }
                    }
                }
            }
        }

        logger.info("Shortest path calculated.");
        return new ArrayList<>(distances.keySet());
    }
}
