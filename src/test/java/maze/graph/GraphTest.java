package maze.graph;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GraphTest {

    private static Graph<String> testGraph;
    private static String testLabelKate;
    private static String testLabelMikola;
    private static String testLabelMira;
    private static String testLabelTony;

    @BeforeEach
    void setUp() {
        testGraph = new Graph<>();
        testLabelKate = "Kate";
        testLabelMikola = "Mikola";
        testLabelMira = "Mira";
        testLabelTony = "Tony";

        testGraph.addVertex(testLabelKate);
        testGraph.addVertex(testLabelMikola);
    }

    @Test
    @Order(1)
    void addVertexTest() {
        assertNotNull(testGraph.getAdjVertices(testLabelKate));
        assertNotNull(testGraph.getAdjVertices(testLabelMikola));
        assertEquals(2, testGraph.getEntireGraph().size());
    }

    @Test
    @Order(2)
    //@Disabled
    void addUndirectedUnweightedEdgeTest() {
        testGraph.addUndirectedUnweightedEdge(testLabelKate, testLabelMikola);

        var kateEdges = testGraph.getAdjVertices(testLabelKate);
        var mikolaEdges = testGraph.getAdjVertices(testLabelMikola);

        assertNotNull(kateEdges);
        assertNotNull(mikolaEdges);

        assertTrue(kateEdges.contains(new Edge<>(testLabelKate, testLabelMikola, 1D)));
        assertTrue(mikolaEdges.contains(new Edge<>(testLabelMikola, testLabelKate, 1D)));
    }

    @Test
    @Order(3)
    //@Disabled
    void addUndirectedWeightedEdgeTest() {
        double weight = 4.4d;

        testGraph.addUndirectedWeightedEdge(testLabelKate, testLabelTony, weight);

        var kateEdges = testGraph.getAdjVertices(testLabelKate);
        var tonyEdges = testGraph.getAdjVertices(testLabelTony);

        assertNotNull(kateEdges);
        assertNotNull(tonyEdges);

        assertTrue(kateEdges.contains(new Edge<>(testLabelKate, testLabelTony, weight)));
        assertTrue(tonyEdges.contains(new Edge<>(testLabelTony, testLabelKate, weight)));
    }

    @Test
    @Order(4)
    //@Disabled
    void addDirectedUnweightedEdge() {
        testGraph.addDirectedUnweightedEdge(testLabelKate, testLabelMira);

        var kateEdges = testGraph.getAdjVertices(testLabelKate);
        var miraEdges = testGraph.getAdjVertices(testLabelMira);

        assertNotNull(kateEdges);
        assertNotNull(miraEdges);

        assertTrue(kateEdges.contains(new Edge<>(testLabelKate, testLabelMira, 1D)));
        assertFalse(miraEdges.contains(new Edge<>(testLabelMira, testLabelKate, 1D)));
    }

    @Test
    @Order(5)
    //@Disabled
    void addDirectedWeightedEdge() {
        double weight = 3.7D;

        testGraph.addDirectedWeightedEdge(testLabelMikola, testLabelTony, weight);

        var mikolaEdges = testGraph.getAdjVertices(testLabelMikola);
        var tonyEdges = testGraph.getAdjVertices(testLabelTony);

        assertNotNull(mikolaEdges);
        assertNotNull(tonyEdges);

        assertTrue(mikolaEdges.contains(new Edge<>(testLabelMikola, testLabelTony, weight)));
        assertFalse(tonyEdges.contains(new Edge<>(testLabelTony, testLabelMikola, weight)));
    }
}