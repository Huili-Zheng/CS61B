package ngordnet.hyponyms;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

public class TestDepthFirstPaths {
    @Test
    public void TestDepthFirstPaths() {
        // create a graph and add some nodes and edges
        HyponymGraph graph = new HyponymGraph(11);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(8, 10);
        graph.addEdge(9, 10);

        // create a DepthFirstPaths object and test the hasPathTo and pathTo methods
        DepthFirstPaths paths = new DepthFirstPaths(graph,0);
        assertTrue(paths.hasPathTo(1));
        assertEquals(Arrays.asList(2,1,0), paths.pathTo(2));
        assertTrue(paths.hasPathTo(0));

        // test ReachNodes()
        assertEquals(Set.of(8, 10), paths.connectedComponent(8));
    }


}
