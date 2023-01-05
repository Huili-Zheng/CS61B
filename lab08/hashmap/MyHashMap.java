package hashmap;

import java.awt.desktop.SystemEventListener;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private static final int INIT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Collection<Node>[] buckets;
    private int n; // # of key-value pairs;
    private int m; // hash table size
    private double f; // load factor
    private HashSet<K> keySet;

    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        n = 0;
        m = INIT_CAPACITY;
        f = LOAD_FACTOR;
        buckets = createTable(m);
        keySet = new HashSet<>(m);
        for (int i = 0; i < m; i++) {
            buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialSize) {
        n = 0;
        m = initialSize;
        f = LOAD_FACTOR;
        buckets = createTable(m);
        keySet = new HashSet<>(m);
        for (int i = 0; i < m; i++) {
            buckets[i] = createBucket();
        }

    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        n = 0;
        m = initialSize;
        f = maxLoad;
        buckets = createTable(m);
        keySet = new HashSet<>(m);
        for (int i = 0; i < m; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        Node node = new Node(key, value);
        put(key, value);
        return node;
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        n = 0;
        for (int i = 0; i<m; i++) {
            this.buckets[i] = createBucket();
        }
    }

    /**
     * Returns true if this hash table contains the specified key.
     * @param key the key
     * @return {@code true} if the hash table contains {@code key}
     *          {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument containsKey() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value of the specified key is the key is in the hash table
     * @param key the key
     * @return the value associated with {@code}
     *          {@code null} if no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument get() is null");
        }
        Iterator<Node> iterator = buckets[hash(key)].iterator();
        while (iterator.hasNext()) {
            Node cur = iterator.next();
            if (cur.key.equals(key)) {
                return cur.value;
            }
        }
        return null;
    }

    private Node getNode(K key) {
        Iterator<Node> iterator = buckets[hash(key)].iterator();
        while (iterator.hasNext()) {
            Node cur = iterator.next();
            if (cur.key.equals(key)) {
                return cur;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return n;
    }

    /**
     * Inserts the specified key-value pair into the symbol table.
     * Overwrite the old value if the key has been already contained in the hash table
     * @param key the key
     * @param value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("argument put() is null");
        }
        if (containsKey(key)) {
            getNode(key).value = value;
            return;
        }
        if ((double)n/m > f) {
            resize(2 * m);
        }
        keySet.add(key);
        buckets[hash(key)].add(new Node(key, value));
        n++;
    }

    private void resize(int capacity) {
        Collection<Node>[] newBuckets = createTable(capacity);
        for (int i=0; i<capacity; i++) {
            newBuckets[i] = createBucket();
        }
        m = capacity;
        for (int i=0; i<m/2; i++) {
            Iterator<Node> iterator = buckets[i].iterator();
            while (iterator.hasNext()) {
                Node cur = iterator.next();
                newBuckets[hash(cur.key)].add(cur);
                }
            }
        buckets = newBuckets;
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /**
     * Removes the specified key-value pair if the key is in the hash table
     * @param key the key
     * @return the removed value associated with {@code key}
     *          {@code null} if no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument of remove() is null");
        }
        if (!containsKey(key)) {
            return null;
        }
        Iterator<Node> iterator = buckets[hash(key)].iterator();
        while (iterator.hasNext()) {
            Node cur = iterator.next();
            if (cur.key == key) {
                buckets[hash(key)].remove(cur);
                n--;
                keySet.remove(key);
                return cur.value;
            }
        }
        return null;
    }

    /**
     * Removes the specified key-value pair if the key-value pair is in the hash table
     * @param key the key
     * @param value the value
     * @return the removed value associated with {@code key}
     *           {@code null} if no such key
     */
    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }


    private int hash(K key) {
        return Math.floorMod(key.hashCode(), m);
    }

    public void printInOrder() {
        for (int i = 0; i < m; i++) {
            System.out.println("Bucket #" + i);
            Iterator<Node> iterator = buckets[i].iterator();
            while (iterator.hasNext()) {
                Node cur = iterator.next();
                System.out.println("\t" + cur.key + "\t" + cur.value);
            }
        }
    }

}
