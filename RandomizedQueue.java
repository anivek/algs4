import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int arraySize;
    private int queueSize;
    private int head;
    private int tail;


    private void resizeArray(int newArraySize) {
        Item[] newItems = (Item[]) new Object[newArraySize];
        int newHead, newTail;

        if (newArraySize < arraySize) { // half
            if (head <= tail) {
                newHead = 0;
                newTail = tail - head;
                System.arraycopy(items, head, newItems, newHead, newTail + 1);
            } else {
                newHead = newArraySize - (arraySize - head);
                newTail = tail;
                System.arraycopy(items, 0, newItems, 0, newTail + 1);
                System.arraycopy(items, head, newItems, newHead, arraySize - head);
            }
        } else { // double
            if (head <= tail) {
                newHead = head;
                newTail = tail;
                System.arraycopy(items, head, newItems, newHead, newTail - newHead + 1);
            } else {
                newHead = head;
                newTail = newHead + queueSize - 1;
                System.arraycopy(items, head, newItems, newHead, arraySize - head);
                System.arraycopy(items, 0, newItems, newHead + arraySize - head, tail + 1);
            }
        }

        head = newHead;
        tail = newTail;
        arraySize = newArraySize;
        items = newItems;
    }

    public RandomizedQueue() {
        head = 0;
        tail = 0;
        arraySize = 1;
        queueSize = 0;
        items = (Item[]) new Object[arraySize];
    }

    public boolean isEmpty() {
        return queueSize == 0;
    }

    public int size() {
        return queueSize;
    }

    public void enqueue(Item item) {
        int newTail;

        if (item == null)
            throw new IllegalArgumentException();

        if (queueSize == arraySize)
            resizeArray(arraySize * 2);

        if (tail == arraySize - 1)
            newTail = 0;
        else
            newTail = tail + 1;

        items[newTail] = item;
        tail = newTail;
        queueSize++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex;
        if (head > tail)
            randomIndex = StdRandom.uniform(tail, head + 1);
        else
            randomIndex = StdRandom.uniform(head, tail + 1);
        Item temp = items[randomIndex];
        items[randomIndex] = items[head];
        items[head] = null;
        head = head == arraySize - 1 ? 0 : head + 1;

        queueSize--;
        if (queueSize > 0 && queueSize == arraySize / 4)
            resizeArray(arraySize / 2);

        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex;
        if (head > tail)
            randomIndex = StdRandom.uniform(tail, head + 1);
        else
            randomIndex = StdRandom.uniform(head, tail + 1);
        return items[randomIndex];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // XXX: need to be randomized
    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int current = head;

        public boolean hasNext() {
            if (isEmpty())
                return false;
            return  current != tail + 1;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (isEmpty() || current == tail + 1)
                throw new UnsupportedOperationException();

            Item item = items[current];
            current = current == arraySize - 1 ? 0 : current + 1;
            return item;
        }
    }

    public static void main(String[] args) {

    }
}