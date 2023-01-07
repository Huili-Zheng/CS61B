package ngordnet.hyponyms;

import java.util.HashSet;
import java.util.Stack;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;
    private HyponymGraph graph;
    public DepthFirstPaths(HyponymGraph G, int s) {
        marked = new boolean[G.size()] ;
        edgeTo = new int[G.size()];
        graph = G;
        dfs(G, s);
    }

    private void dfs(HyponymGraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

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
            for (int w : graph.adj(u)) {
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
