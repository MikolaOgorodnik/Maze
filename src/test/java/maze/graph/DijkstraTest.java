package maze.graph;


import maze.graph.Dijkstra;
import maze.graph.Graph;
import maze.model.ShortestPathAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {

    private static Graph<String> testGraph;

    @BeforeEach
    void setUp() {
        testGraph        = new Graph<>();
        String testVertexKate   = "Kate";
        String testVertexMikola = "Mikola";
        String testVertexMira   = "Mira";
        String testVertexTony   = "Tony";
        String testVertexGalina = "Galina";
        String testVertexIgor   = "Igor";
        String testVertexAnna   = "Anna";
        String testVertexPetro  = "Petro";

        testGraph.addVertex(testVertexKate);
        testGraph.addVertex(testVertexMikola);
        testGraph.addVertex(testVertexMira);
        testGraph.addVertex(testVertexTony);
        testGraph.addVertex(testVertexGalina);
        testGraph.addVertex(testVertexIgor);
        testGraph.addVertex(testVertexAnna);
        testGraph.addVertex(testVertexPetro);

        testGraph.addUndirectedWeightedEdge(testVertexAnna, testVertexMikola, 5d);
        testGraph.addUndirectedWeightedEdge(testVertexPetro, testVertexMikola, 7d);
        testGraph.addUndirectedWeightedEdge(testVertexGalina, testVertexKate, 3d);
        testGraph.addUndirectedWeightedEdge(testVertexIgor, testVertexKate, 4d);
        testGraph.addUndirectedWeightedEdge(testVertexGalina, testVertexIgor, 4d);
        testGraph.addUndirectedWeightedEdge(testVertexMikola, testVertexKate, 9d);
        testGraph.addUndirectedWeightedEdge(testVertexMikola, testVertexMira, 3d);
        testGraph.addUndirectedWeightedEdge(testVertexMikola, testVertexTony, 1d);
        testGraph.addUndirectedWeightedEdge(testVertexKate, testVertexMira, 2d);
        testGraph.addUndirectedWeightedEdge(testVertexKate, testVertexTony, 3d);
    }

    @Test
    void getShortestPath() {
        Logger logger = LoggerFactory.getLogger(DijkstraTest.class);
        logger.info("Test Dijkstra Started!");

        String testVertexKate   = "Kate";
        String testVertexMikola = "Mikola";
        String testVertexTony   = "Tony";
        String testVertexIgor   = "Igor";
        String testVertexPetro  = "Petro";

        ShortestPathAlgorithm<String> dijkstra = new Dijkstra<>();

        List<String> result = dijkstra.getShortestPath(testGraph, testVertexPetro, testVertexIgor);

        ArrayList<String> testData = new ArrayList<>();
        testData.add(testVertexPetro);
        testData.add(testVertexMikola);
        testData.add(testVertexTony);
        testData.add(testVertexKate);
        testData.add(testVertexIgor);


        logger.info("The Result is: " + result.toString());

        assertEquals(testData, result);

        logger.info("Test Dijkstra Finished!");
    }
}