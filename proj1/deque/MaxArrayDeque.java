package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public MaxArrayDeque(Comparator<T> c, T bacon) {
        this.comparator = c;
        this.addFirst(bacon);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);

        for (int i = 0; i < size(); i += 1) {
            T curr = get(i);
            if (c.compare(max, curr) < 0) {
                max = curr;
            }
        }
        return max;
    }

    public T max() {
        return max(comparator);
    }
}
