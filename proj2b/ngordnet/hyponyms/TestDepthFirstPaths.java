package ngordnet.hyponyms;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

public class TestDepthFirstPaths {

    public DepthFirstPaths createTestPath() {
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

        return new DepthFirstPaths(graph, 0);
    }


    @Test
    public void TestPaths() {
        // create a DepthFirstPaths object
        DepthFirstPaths path = createTestPath();

        // test the hasPathTo and pathTo methods
        assertTrue(path.hasPathTo(1));
        assertEquals(Arrays.asList(2, 1, 0), path.pathTo(2));
        assertTrue(path.hasPathTo(0));

    }

    @Test
    public void TestConnectedComponent() {
        DepthFirstPaths path = createTestPath();
        // test connectedComponent()
        assertEquals(Set.of(8, 10), path.connectedComponent(8));
    }


}
