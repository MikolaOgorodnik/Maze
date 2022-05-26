package maze;

import maze.graph.Graph;
import maze.graph.Prim;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class PrimTest {

    @Test
        //@Disabled
    void runTest() {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.log(Level.INFO, "Start testing Prims algorithm");

        Graph<String> givenGraph = new Graph<>();
        givenGraph.addVertex("0");
        givenGraph.addVertex("1");
        givenGraph.addVertex("2");
        givenGraph.addVertex("3");
        givenGraph.addVertex("4");
        givenGraph.addVertex("5");
        givenGraph.addVertex("6");
        givenGraph.addVertex("7");

        givenGraph.addUndirectedWeightedEdge("0", "1", 1);
        givenGraph.addUndirectedWeightedEdge("0", "2", 1);
        givenGraph.addUndirectedWeightedEdge("0", "3", 2);
        givenGraph.addUndirectedWeightedEdge("1", "3", 2);
        givenGraph.addUndirectedWeightedEdge("2", "3", 4);
        givenGraph.addUndirectedWeightedEdge("2", "4", 4);
        givenGraph.addUndirectedWeightedEdge("3", "4", 3);
        givenGraph.addUndirectedWeightedEdge("3", "5", 2);
        givenGraph.addUndirectedWeightedEdge("4", "5", 3);
        givenGraph.addUndirectedWeightedEdge("4", "6", 5);
        givenGraph.addUndirectedWeightedEdge("4", "7", 1);
        givenGraph.addUndirectedWeightedEdge("5", "7", 3);
        givenGraph.addUndirectedWeightedEdge("6", "7", 2);

        Graph<String> ethalonMST = new Graph<>();
        ethalonMST.addVertex("0");
        ethalonMST.addVertex("1");
        ethalonMST.addVertex("2");
        ethalonMST.addVertex("3");
        ethalonMST.addVertex("4");
        ethalonMST.addVertex("5");
        ethalonMST.addVertex("6");
        ethalonMST.addVertex("7");

        ethalonMST.addUndirectedWeightedEdge("0", "1", 1);
        ethalonMST.addUndirectedWeightedEdge("0", "2", 1);
        ethalonMST.addUndirectedWeightedEdge("0", "3", 2);
        ethalonMST.addUndirectedWeightedEdge("3", "4", 3);
        ethalonMST.addUndirectedWeightedEdge("3", "5", 2);
        ethalonMST.addUndirectedWeightedEdge("4", "7", 1);
        ethalonMST.addUndirectedWeightedEdge("6", "7", 2);

        var prim = new Prim<>(givenGraph);

        var mst = prim.run("0");

        assertEquals(ethalonMST, mst);

        logger.log(Level.INFO, "Finish testing Prims algorithm");
    }
}