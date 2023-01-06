package ngordnet.hyponyms;

import java.util.Stack;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;
    public DepthFirstPaths(HyponymGraph G, int s) {
        marked = new boolean[G.size()] ;
        edgeTo = new int[G.size()];
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
        for (int x = v; x!=v; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }




}
