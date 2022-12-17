package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T>{
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    public class ArrayDequeIterator<T> implements Iterator<T> {
        int curr = 0;

        @Override
        public boolean hasNext() {
            return curr < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            T returnT = (T) get(curr);
            curr += 1;
            return returnT;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator<>();
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private int increment(int n) {
        return Math.floorMod(n + 1, items.length);
    }

    private int decrement(int n) {
        return Math.floorMod(n - 1, items.length);
    }

    private int getNextFirst() {
        return increment(nextFirst);
    }

    private int getNextLast() {
        return decrement(nextLast);
    }

    private boolean isFull() {
        return size == items.length;
    }

    @Override
    public int size() {
        return size;
    }

    public T get(int index) {
        index = Math.floorMod(getNextFirst() + index, items.length);
        return items[index];
    }

    public void printDeque() {
        for (T i: this) {
            System.out.print(i + " ");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Deque) {
            Deque oD = (Deque) o;
            if (oD.size() == size) {
                for (int i = 0; i < size; i+=1) {
                    if (!oD.get(i).equals(get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void resize() {
        if (isFull()) {
            resizeHelper(items.length * 2);
        } else if (getUsageRatio() < 0.25 && items.length >= 16) {
            resizeHelper(items.length / 2);
        }
    }

    private void resizeHelper(int capacity) {
        T[] a = (T[]) new Object[capacity];

        if (getNextFirst() > getNextLast()) {
            int startingPos = items.length - getNextFirst();
            System.arraycopy(items, getNextFirst(), a, 4, startingPos);
            System.arraycopy(items, 0, a, 4 + startingPos, nextLast);
        } else {
            System.arraycopy(items, getNextFirst(), a, 4, size);
        }
        items = a;
        nextFirst = 3;
        nextLast =  Math.floorMod((getNextFirst() + size), items.length);
    }

    private float getUsageRatio() {
        return (float) size / items.length;
    }

    /* Add operations:
     *       nextFirst decrement
     *       nextLast increment
     */
    @Override
    public void addFirst(T item) {
        if (isFull()) {
            resize();
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = decrement(nextFirst);
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            resize();
        }
        items[nextLast] = item;
        size += 1;
        nextLast = increment(nextLast);
    }

    /* Remove operations:
    *       nextFirst increment
    *       nextLast decrement
     */

    private T remove(int index) {
        T returnT = items[index];
        items[index] = null;
        size -= 1;
        return returnT;
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T returnT = remove(getNextFirst());
        nextFirst = increment(nextFirst);
        resize();
        return returnT;
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T returnT = remove(getNextLast());
        nextLast = decrement(nextLast);
        resize();
        return returnT;
    }





    public static void main(String[] args) {
        int n = 99;
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i <= n; i += 1) {
            ad1.addLast(i);
        }

        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        for (int i = n; i >= 0; i -= 1) {
            ad2.addFirst(i);
        }

        ad1.printDeque();
        System.out.println(ad1.equals(ad2));
    }

}