import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by eparks on 10/3/2014.
 */
public class Deque<Item> implements Iterable<Item> {

//    private Item[] items;
    private int queueCount;
    private Node<Item> first;
    private Node<Item> last;

    // construct an empty deque
    public Deque()   {

        first = null;
        last = null;
        queueCount = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {

        return first == null;
    }

    public int size() {

        return queueCount;
    }

    // insert the item at the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new NullPointerException("Cannot add null item.");
        }
        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        queueCount++;
        if (queueCount == 1) {
            last = first;
        }
    }

    // insert the item at the end
    public void addLast(Item item) {

        if (item == null) {
            throw new NullPointerException("Cannot add null item.");
        }

        Node<Item> oldLast = last;
        last = new Node<Item>(item);
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        queueCount++;
    }

    // delete and return the item at the front
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        Item firstItem = first.item;
        first = first.next;
        queueCount--;

        return firstItem;
    }

    // delete and return the item at the end
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        Item item = last.item;
        // Iterate til node.next = last

        // ...then set its next to null.
        queueCount--;
        if (isEmpty()) {
            last = null;   // to avoid loitering
        }
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {

        return new DequeueIterator<Item>(first);
    }

    public static void main(String[] args) {

    }


    private static class Node<Item> {

        private Item item;
        private Node<Item> next;

        Node(Item item) {
            this.item = item;
        }
    }

    private class DequeueIterator<Item> implements Iterator<Item> {

        private Node<Item> current;

        public DequeueIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {

            if (hasNext() == false) {
                throw new NoSuchElementException("Queue is empty.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove with this iterator.");
        }
    }
}
