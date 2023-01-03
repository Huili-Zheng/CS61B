package bstmap;
import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root; // root of BST

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private  int size;

        public Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BSTMap() {
    }


    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node cur) {
        if (cur == null) {
            return;
        }
        printInOrder(cur.left);
        System.out.println(cur.key + ": " + cur.value);
        printInOrder(cur.right);
    }

    @Override
    public void clear() {
        root = null;
    }

    /**
     * Dose the BSTMap contain the given key?
     * @param key the key
     * @return {@code true} if it does contain {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException in {@code key} is {@code null}
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("argument to containsKey() is null");
        if (root == null) {
            return false;
        }
        Node cur = root;
        int cmp;
        while (cur!=null) {
            cmp = key.compareTo(cur.key);
            if (cmp < 0) cur = cur.left;
            else if (cmp > 0) cur = cur.right;
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value associated with the given key
     * @param key the key
     * @return the value associated with the given key if the key is in the BSTMap
     *          and {@code null} if the key is not in the BSTMap
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public V get(K key) {
        return get(key, root);
    }

    private V get(K key, Node cur) {
        if (key ==null) throw new IllegalArgumentException("calls get() with a null key");
        if (cur == null) {
            return null;
        }
        int cmp = key.compareTo(cur.key);
        if (cmp < 0) return get(key, cur.left);
        else if (cmp > 0) return get(key, cur.right);
        else {
            return cur.value;
        }
    }

    /**
     * Returns the number of key-value pairs in the symbol table
     * @return the number of key-value pairs in the symbol table
     */
    @Override
    public int size() {
        return size(root);
    }

    private int size(Node cur) {
        if (cur == null) return 0;
        else {
            return cur.size;
        }
    }

    /**
     * Inserts the specified key-value pair into the symbol table.
     * Overwrite the old value with the new value if the symbol table already contains the specified key
     * Deletes the specified key-value pair if the specified value is {@code null}
     * @param key the key
     * @param value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, value);
    }

    private Node put(Node cur, K key, V value) {
        if (cur == null) return new Node(key, value, 1);
        int cmp = key.compareTo(cur.key);
        if (cmp > 0) {
            cur.right = put(cur.right, key, value);
        }
        else if (cmp < 0) {
            cur.left = put(cur.left, key, value);
        }
        else {
            cur.value = value;
        }
        cur.size = size(cur.left) + size(cur.right) + 1;
        return cur;
    }

    @Override
    public Set<K> keySet() {
        Set<K> BSTMapKeySet = new HashSet<>();

        for (K key: this) {
            BSTMapKeySet.add(key);
        }
        return BSTMapKeySet;
    }

    /**
     * Removes the specified key and its value from this symbol table is the key is in the table
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls remove() with a null key");
        }
        V deletedNodeValue = get(key);
        root = remove(root, key);
        return deletedNodeValue;
    }

    private Node remove(Node cur, K key) {
        if (cur == null) return null;

        int cmp = key.compareTo(cur.key);
        if (cmp > 0) cur.right = remove(cur.right, key);
        else if (cmp < 0) cur.left = remove(cur.left, key);
        else {
            if (cur.left == null ) return cur.right;
            if (cur.right == null) return cur.left;
            Node t = cur;
            cur = min(cur.right);
            cur.right = removeMin(t.right);
            cur.left = t.left;
        }
        cur.size = size(cur.right) + size(cur.left) + 1;
        return cur;
    }

    /**
     * Removes the smallest key from the symbol table.
     */
    public void removeMin() {
        root = removeMin(root);
    }

    private Node removeMin(Node cur) {
        if (cur.left == null) return cur.right;
        cur.left = removeMin(cur.left);
        cur.size = size(cur.left) + size(cur.right) + 1;
        return cur;
    }

    /**
     * Returns the smallest key in the symbol table.
     * @return the smallest key in the symbol table.
     */
    public K min(){
        return min(root).key;
    }

    private Node min(Node cur){
        if (cur.left == null) return cur;
        else {
            return min(cur.left);
        }
    }



    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls remove() with a null key");
        }
        V deletedNodeValue = get(key);
        if (deletedNodeValue != value) {
            return null;
        }
        root = remove(root, key);
        return deletedNodeValue;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {

        List<Node> list;

        public BSTMapIterator() {
            list = new LinkedList<>();
            list.add(root);
        }

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public K next() {
            Node cur = list.remove(0);

            if (cur.left != null) list.add(cur.left);
            if (cur.right != null) list.add(cur.right);

            return cur.key;
        }
    }
}
