package maze.graph;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GraphTest {

    private static Graph<String> testGraph;
    private static String testVertexKate;
    private static String testVertexMikola;
    private static String testVertexMira;
    private static String testVertexTony;

    @BeforeEach
    void setUp() {
        testGraph = new Graph<>();
        testVertexKate   = "Kate";
        testVertexMikola = "Mikola";
        testVertexMira   = "Mira";
        testVertexTony   = "Tony";

        testGraph.addVertex(testVertexKate);
        testGraph.addVertex(testVertexMikola);
    }

    @Test
    @Order(1)
    void addVertexTest() {
        assertNotNull(testGraph.getAdjVertices(testVertexKate));
        assertNotNull(testGraph.getAdjVertices(testVertexMikola));
        assertEquals(2, testGraph.getEntireGraph().size());
    }

    @Test
    @Order(2)
    @Disabled
    void addUndirectedUnweightedEdgeTest() {
        testGraph.addVertex(testVertexMira);
        testGraph.addUndirectedUnweightedEdge(testVertexKate, testVertexMira);
        assertTrue(testGraph.getAdjVertices(testVertexKate).contains(new Edge<>(testVertexMira)));
    }

    @Test
    @Order(3)
    @Disabled
    void addUndirectedWeightedEdgeTest() {
        double weight = 4.4d;
        testGraph.addVertex(testVertexTony);
        testGraph.addUndirectedWeightedEdge(testVertexKate, testVertexTony, weight);
        var adjVertices = testGraph.getAdjVertices(testVertexKate);

        assertTrue(adjVertices.contains(new Edge<>(testVertexTony, weight)));
    }

    @Test
    @Order(4)
    @Disabled
    void addDirectedUnweightedEdge() {
        testGraph.addVertex(testVertexMira);
        testGraph.addDirectedUnweightedEdge(testVertexKate, testVertexMira);

        assertTrue(
                testGraph.getAdjVertices(testVertexKate).contains(new Edge<>(testVertexMira)) &&
                        !testGraph.getAdjVertices(testVertexMira).contains(new Edge<>(testVertexKate))
        );
    }

    @Test
    @Order(5)
    @Disabled
    void addDirectedWeightedEdge() {
        testGraph.addVertex(testVertexTony);
        testGraph.addDirectedWeightedEdge(testVertexMikola, testVertexTony, 3);

        assertNotNull(testGraph.getAdjVertices(testVertexTony));
    }
}