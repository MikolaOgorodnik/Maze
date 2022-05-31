package maze.graph;

import maze.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Prim's algorithm.
 * @author Nikolai Ogorodnik
 * @param <T> Vertex type. Any reference or primitive type.
 */
public class Prim<T extends Comparable<T> & Serializable> {

    private final Logger logger = LoggerFactory.getLogger(Prim.class);

    private final Graph<T> graph;

    /**
     * Constructs Prim's algorithm.
     * @param graph Processed {@link Graph}.
     */
    public Prim(Graph<T> graph) {
        this.graph = graph;
    }

    /**
     * Runs Prim's algorithm.
     * @param startingPoint Starting vertex.
     * @return Minimum spanning tree of type {@link Graph}.
     */
    public Graph<T> run(T startingPoint) {
        logger.info("MST calculation started.");

        Graph<T> mst = new Graph<>();
        mst.addVertex(startingPoint);

        var verticesSet = new HashSet<>(graph.getVerticesSet());
        var edges = new TreeSet<>(graph.getAdjVertices(startingPoint));

        verticesSet.remove(startingPoint);

        while (!verticesSet.isEmpty()) {
            var currentMinEdge = edges.first();

            mst.addVertex(currentMinEdge.getVertexTo());

            mst.addUndirectedWeightedEdge(
                    currentMinEdge.getVertexFrom(),
                    currentMinEdge.getVertexTo(),
                    currentMinEdge.getWeight()
            );

            edges.remove(currentMinEdge);

            verticesSet.remove(currentMinEdge.getVertexTo());

            edges.addAll(graph.getAdjVertices(currentMinEdge.getVertexTo()));

            edges = edges
                    .stream()
                    .filter(x -> verticesSet.contains(x.getVertexTo()))
                    .collect(Collectors.toCollection(TreeSet::new));
        }

        logger.info("MST calculated.");
        logger.debug(mst.toString());
        return mst;
    }
}
