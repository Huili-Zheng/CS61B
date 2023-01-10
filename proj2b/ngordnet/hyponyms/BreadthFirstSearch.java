package ngordnet.hyponyms;

import java.util.*;

public class BreadthFirstSearch {
    // a boolean array to store the record of visited nodes
    private boolean[] marked;
    // an array to store the edge-to information
    private int[] edgeTo;
    // the source vertex
    private int s;
    // the source graph
    private HyponymGraph graph;
    public BreadthFirstSearch(HyponymGraph G, int s) {
        marked = new boolean[G.size()] ;
        edgeTo = new int[G.size()];
        this.s = s;
        graph = G;
        bfs(G, s);
    }

    // return the path from s to v; null if no such path
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedList<Integer> path = new LinkedList<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    // is there a path from s to v?
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    // breadth-first search from a given source vertex
    private void bfs(HyponymGraph G, int s) {
        marked[s] = true;
        Queue<Integer> fringe = new LinkedList<>();
        fringe.add(s);
        while (!fringe.isEmpty()) {
            int v = fringe.poll();
            for (int w : G.getAdj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    fringe.add(w);
                }
            }
        }
    }

    // get d the nodes that the source can arrive by bfs
    public HashSet<Integer> connectedDComponent(int v, int d) {
        // create a list to store the connected component
        HashSet<Integer> component = new HashSet<>();
        // mark all the vertices are not visited
        boolean[] visited = new boolean[graph.size()];
        // create a queue for BFS
        Queue<Integer> fringe = new LinkedList<>();
        component.add(v);
        fringe.add(v);
        while (!fringe.isEmpty() && d > 0) {
            d -= 1;
            int u = fringe.poll();
            for (int w : graph.getAdj(u)) {
                if (!visited[w]) {
                    visited[w] = true;
                    edgeTo[w] = u;
                    fringe.add(w);
                    component.add(w);
                }
            }
        }
        return component;

    }

    // get all the nodes that the source can arrive by bfs
    public HashSet<Integer> connectedComponent(int v) {
        return connectedDComponent(v, graph.size());
    }
}
