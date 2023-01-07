package ngordnet.hyponyms;


import edu.princeton.cs.algs4.In;

import java.util.*;

public class HyponymGraph {

    private final int V;
    private List<Integer>[] adj;


    /**
     * Create empty graph
     */
    public HyponymGraph(int V) {
        this.V = V;
        adj = (List<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }


    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public boolean hasEdge(int v, int w) {
        return adj[v].contains(w);
    }

    public int size() {
        return V;
    }

}
