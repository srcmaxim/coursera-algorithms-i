import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * RandomizedQueue is a generic array-implemented queue that
 * will dequeue items at random. It will also iterate at random.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int MIN_LENGTH = 10;
    private Item[] queue = (Item[]) new Object[MIN_LENGTH];
    private int next;

    /**
     * Returns true if the queue is empty.
     */
    public boolean isEmpty() {
        return (this.next == 0);
    }

    /**
     * Returns the next of the queue.
     */
    public int size() {
        return this.next;
    }

    /**
     * Adds a new item to the queue.
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot enqueue null objects.");
        }

        if (next == queue.length) {
            queue = Arrays.copyOf(queue, queue.length * 2);
        }

        queue[next] = item;

        this.next++;
    }

    /**
     * Removes and returns an item at random from the queue.
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is currently empty.");
        }

        swap(queue, randomIndex(), --next);
        Item dequeued = queue[next];
        queue[next] = null;

        if (next < queue.length / 4) {
            queue = Arrays.copyOf(queue, queue.length / 2);
        }

        return dequeued;
    }

    /**
     * Returns an item at random without deleting it.
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is currently empty.");
        }

        return queue[randomIndex()];
    }

    /**
     * Returns an integer of a random index which is not null.
     */
    private int randomIndex() {
        int rand = StdRandom.uniform(next);
        return rand;
    }

    /**
     * Returns an iterator object to allow random iteration through queue.
     */
    public Iterator<Item> iterator() {
        return new RandIterator();
    }

    private class RandIterator implements Iterator<Item> {

        private final Item[] items = knuthShuffle();
        private int i;

        @Override
        public boolean hasNext() {
            return (i < items.length);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more objects to iterate through");
            }
            return items[i++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove method not supported");
        }

    }

    /**
     * Knuth shuffle for the iterator queue
     */
    private Item[] knuthShuffle() {
        Item[] q = Arrays.copyOf(queue, next);
        for (int j = 1; j < q.length; j++) {
            int i = StdRandom.uniform(j + 1);
            swap(q, i, j);
        }
        return q;
    }

    /**
     * Swaps element at the a, b indexes
     */
    private void swap(Item[] array, int a, int b) {
        Item temp = array[b];
        array[b] = array[a];
        array[a] = temp;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);

        Iterator<Integer> iterator = rq.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();

            System.out.println(next);
        }
    }

}