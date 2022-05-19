package maze;

import maze.graph.Graph;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Prim<T extends Comparable<T> & Serializable> {
    private final Graph<T> graph;

    public Prim(Graph<T> graph) {
        this.graph = graph;
    }

    public Graph<T> run(T startingPoint) {

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

        return mst;
    }

}
