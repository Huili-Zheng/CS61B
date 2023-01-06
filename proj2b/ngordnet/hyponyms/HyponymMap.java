package ngordnet.hyponyms;
import java.util.*;

/**
 * An object for mapping a word to its all hyponyms.
 */
public class HyponymMap implements Map<Integer, List<String>> {
    private Map<Integer, List<String>> map;
    public HyponymMap() {
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
    public List<String> get(Object key) {
        return map.get(key);
    }

    @Override
    public List<String> put(Integer key, List<String> value) {
        return map.put(key, value);
    }

    @Override
    public List<String> remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends List<String>> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<List<String>> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Integer, List<String>>> entrySet() {
        return map.entrySet();
    }


}
