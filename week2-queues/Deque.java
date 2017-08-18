import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Deque is a generic double ended queue / stack hybrid where
 * new items can be added or removed from either end.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    private class Node {
        private Node left;
        private Node right;
        private Item item;
    }

    /**
     * Returns true when the Deque is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the Deque
     */
    public int size() {
        return size;
    }

    /**
     * Adds an item to the front of the Deque
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null items");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (oldFirst != null) {
            first.right = oldFirst;
            oldFirst.left = first;
        }
        if (last == null) {
            last = first;
        }
        size++;
    }

    /**
     * Adds an item to the end of the Deque
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (oldLast != null) {
            last.left = oldLast;
            oldLast.right = last;
        }
        if (first == null) {
            first = last;
        }
        size++;
    }

    /**
     * Removes the item from the front of the Deque
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Node oldFirst = first;
        first = first.right;
        if (first != null) {
            first.left = null;
        }
        if (first == null) {
            last = first;
        }
        size--;
        return oldFirst.item;
    }

    /**
     * Returns the item from the rear of the Deque
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Node oldLast = last;
        last = last.left;
        if (last != null) {
            last.right = null;
        }
        if (last == null) {
            first = last;
        }
        size--;
        return oldLast.item;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Item> iterator = iterator();
        iterator.forEachRemaining(sb::append);
        return sb.toString();
    }

    public Iterator<Item> iterator() {
        return new DequIterator();
    }

    private class DequIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more objects to iterate through");
            }
            Item item = current.item;
            current = current.right;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Iterator remove function not supported.");
        }
    }

}
