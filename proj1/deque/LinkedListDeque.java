package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T>{
    public class ItemNode {
        public T item;
        public ItemNode next;
        public ItemNode prev;

        public ItemNode(T i, ItemNode m, ItemNode n) {
            item = i;
            prev = m;
            next = n;
        }
    }
    private ItemNode sentinel;
    private int size;

    /* create an empty deque*/
    public LinkedListDeque() {
        sentinel = new ItemNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(T item) {
        sentinel = new ItemNode(null, null, null);
        sentinel.next = new ItemNode(item, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    public void addFirst(T item) {
        sentinel.next = new ItemNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        sentinel.prev = new ItemNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }


    public int size() {
        return size;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        ItemNode rm = sentinel.next;
        T rmItem = rm.item;
        sentinel.next = rm.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        rm.item = null;
        rm.prev = null;
        rm.next = null;
        return rmItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        ItemNode rm = sentinel.prev;
        T rmItem = rm.item;
        sentinel.prev = rm.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        rm.item = null;
        rm.prev = null;
        rm.next = null;
        return rmItem;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        ItemNode p = sentinel.next;
        for (int nodeInd = 0; nodeInd < index; nodeInd += 1) {
            p = p.next;
        }
        return p.item;
    }

    private T getRecursiveHelper(int index, int nodeInd, ItemNode p) {
        if (p == null) {
            return null;
        }
        if (nodeInd == index) {
            return p.item;
        }
        return getRecursiveHelper(index, nodeInd + 1, p.next);
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        int nodeInd = 0;

        return getRecursiveHelper(index, nodeInd, sentinel.next);

    }

    public void printDeque() {
        ItemNode p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    class LinkedListDequeIterator implements Iterator<T> {
        int current = 0;

        public boolean hasNext() {
            return current < size;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = get(current);
            current += 1;
            return item;
        }
    }
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
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
            Deque oD = (Deque<T>) o;
            if (oD.size() == size) {
                for (int i = 0; i < size; i += 1) {
                    if (!(get(i).equals(oD.get(i)))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        lld.addFirst(1515);
        lld.addFirst(9);
        lld.addFirst(8);

        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        lld2.addLast(8);
        lld2.addLast(9);
        lld2.addLast(1515);

        lld.printDeque();

        for (int i: lld) {
            System.out.print(i + " ");
        }

        System.out.println(lld.equals(lld2));
    }



}