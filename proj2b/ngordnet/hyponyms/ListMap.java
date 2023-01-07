package ngordnet.hyponyms;
import java.util.*;

/**
 * An object for mapping a K to its all V.
 */
public class ListMap<K, V> implements Map<K, List<V>> {
    private Map<K, List<V>> map;
    public ListMap() {
        map = new HashMap<>();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public List<V> get(Object key) {
        return map.get(key);
    }

    @Override
    public List<V> put(K key, List<V> value) {
        return map.put(key, value);
    }

    public void putOneValue(K key, V value) {
        List<V> list = map.computeIfAbsent(key, k -> new ArrayList<>());
        list.add(value);
    }


    @Override
    public List<V> remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<List<V>> values() {
        return map.values();
    }

    @Override
    public Set<Map.Entry<K, List<V>>>  entrySet() {
        return map.entrySet();
    }


}
