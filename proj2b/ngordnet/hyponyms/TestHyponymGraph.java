package ngordnet.hyponyms;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestHyponymGraph {
    @Test
    public void TestHyponymGraph() {
        HyponymGraph graph = new HyponymGraph(11);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(8, 10);
        graph.addEdge(9, 10);

        assertFalse(graph.hasEdge(10, 9));
        assertTrue(graph.hasEdge(0,1));
        assertTrue(graph.hasEdge(1,2));


        List<Integer> expected = new ArrayList<>();
        expected.add(6);
        expected.add(7);
        assertEquals(expected, graph.adj(5));

    }

}
