package ngordnet.hyponyms;

import java.util.HashSet;
import java.util.Stack;

public class DepthFirstPaths {
    // a boolean array to store the record of visited nodes
    private boolean[] marked;
    // an array to store the edge-to information
    private int[] edgeTo;
    // the source vertex
    private int s;
    // the source graph
    private HyponymGraph graph;
    public DepthFirstPaths(HyponymGraph G, int s) {
        marked = new boolean[G.size()] ;
        edgeTo = new int[G.size()];
        this.s = s;
        graph = G;
        dfs(G, s);
    }

    // depth-first search from a given source vertex
    private void dfs(HyponymGraph G, int v) {
        marked[v] = true;
        for (int w : G.getAdj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    // check if there is a path from source vertex to v given vertex
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    // get the path from source to v
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int x = v; x!=s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    // get all the nodes that the source can arrive
    public HashSet<Integer> connectedComponent(int v) {
        // create a list to store the connected component
        HashSet<Integer> component = new HashSet<>();
        // mark all the vertices as not visited
        boolean[] visited = new boolean[graph.size()];
        // create a stack for DFS
        Stack<Integer> stack = new Stack<>();
        // do DFS for vertex v
        component.add(v);
        stack.push(v);
        while (!stack.isEmpty()) {
            int u = stack.pop();
            for (int w : graph.getAdj(u)) {
                if (!visited[w]) {
                    visited[w] = true;
                    stack.push(w);
                    component.add(w);
                }
            }
        }
        return component;
    }
}
