package ngordnet.hyponyms;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestBreadthFirstSearch {
    public BreadthFirstSearch createTestPath() {
        HyponymGraph graph = new HyponymGraph();

        for (int i = 0; i < 11; i++) {
            graph.addVertex(i);
        }

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(8, 10);
        graph.addEdge(9, 10);

        return new BreadthFirstSearch(graph, 0);
    }

    @Test
    public void TestPaths() {
        // create a BreadthFirstPath object
        BreadthFirstSearch path = createTestPath();

        // test connectedComponent()
        assertEquals(Set.of(0, 1, 2, 3, 4, 5, 6, 7), path.connectedComponent(0));

        // test connectedDComponent()
        assertEquals(Set.of(0, 1, 2, 3, 4, 5), path.connectedDComponent(0,4));

    }

}
