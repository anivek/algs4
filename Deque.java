import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] items;
    private int arraySize;
    private int queueSize;
    private int head;
    private int tail;

    public Deque() {
        head = tail = 0;
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

    private void resizeArray(int newArraySize) {
        Item[] newItems = (Item[]) new Object[newArraySize];
        int newHead, newTail;

        if (newArraySize < arraySize) { // half
            if (head < tail) {
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
            if (head < tail) {
                newHead = head;
                newTail = tail;
                System.arraycopy(items, head, newItems, newHead, newTail - newHead + 1);
            } else {
                newHead = head;
                newTail = newHead + queueSize - 1;
                System.arraycopy(items, head, newItems, newHead, arraySize - head);
                System.arraycopy(items, 0, newItems, arraySize - 1, tail - 1);
            }
        }

        head = newHead;
        tail = newTail;
        arraySize = newArraySize;
        items = newItems;
    }

    public void addFirst(Item item) {
        int newHead;

        if (item == null)
            throw new IllegalArgumentException();

        if (queueSize == arraySize)
            resizeArray(arraySize * 2);

        if (head == 0)
            newHead = arraySize - 1;
        else
            newHead = head - 1;

        items[newHead] = item;
        head = newHead;
        queueSize++;
    }

    public void addLast(Item item) {
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

    public void removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        items[head] = null;
        head = head == arraySize - 1 ? 0 : head + 1;

        if (queueSize > 0 && queueSize == arraySize / 4)
            resizeArray(arraySize / 2);
    }

    public void removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        items[tail] = null;
        tail = tail == 0 ? arraySize - 1 : tail - 1;

        if (queueSize > 0 && queueSize == arraySize / 4)
            resizeArray(arraySize / 2);
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private int current = head;

        public boolean hasNext() {
            return  current != tail + 1;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item = items[current];
            current = current == arraySize - 1 ? 0 : current + 1;
            return item;
        }
    }

    public static void main (String[] args) {

    }
}