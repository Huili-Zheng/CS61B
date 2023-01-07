package ngordnet.hyponyms;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestHyponymGraph {

    public HyponymGraph createTestGraph() {
        HyponymGraph graph = new HyponymGraph();

        for (int i = 0; i < 11; i++) {
            graph.addVertex(i);
        }

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(8, 10);
        graph.addEdge(9, 10);

        return graph;
    }
    @Test
    public void TestEdge() {
        HyponymGraph graph = createTestGraph();

        assertFalse(graph.hasEdge(10, 9));
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 2));

    }

    @Test
    public void TestAdj() {
        HyponymGraph graph = createTestGraph();

        assertEquals(Arrays.asList(6,7), graph.getAdj(5));

    }

}
