package ngordnet.hyponyms;

import java.util.*;

public class HyponymGraph {
    // a list to store the vertices of the graph
    private List<Integer> vertices;
    // an adjacency list to store the edges of the graph
    private List<List<Integer>> adj;


    /**
     * Create empty graph
     */
    public HyponymGraph() {
        vertices = new ArrayList<>();
        adj = new ArrayList<>();
        adj.add(new ArrayList<>());
    }

    // add a vertex to the graph
    public void addVertex(int v) {
        vertices.add(v);
        // adj has v lists to store the edges of v vertexes
        if (adj.size() <= v) {
            for (int i = 0; i <= v-adj.size(); i++) {
                adj.add(new ArrayList<>());
            }
        }
    }

    // add an edge between two vertices
    public void addEdge(int v, int w) {
        adj.get(v).add(w);
    }

    // get the adj list of a vertex
    public List<Integer> getAdj(int v) {
        return adj.get(v);
    }

    // check the existence of the edge between two vertices
    public boolean hasEdge(int v, int w) {
        return adj.get(v).contains(w);
    }

    // return the # of vertices
    public int size() {
        return vertices.size();
    }

}
