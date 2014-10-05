import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by eparks on 10/3/2014.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int nonNullItemCount;

    // construct an empty randomized queue
    public RandomizedQueue() {

        items = (Item[]) new Object[4];
        nonNullItemCount = 0;
    }

    public boolean isEmpty() {

        return nonNullItemCount == 0;
    }

    // return the number of items on the queue
    public int size() {

        return nonNullItemCount;
    }

    // add the item
    public void enqueue(Item item) {

        if (item == null) {
            throw new NullPointerException("Cannot enqueue a null item.");
        }
        if (nonNullItemCount == items.length) {
            resize(items.length * 2);
        }

        // do enqueue
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                break;
            }
        }

        nonNullItemCount++;
    }

    private void resize(int newSize) {

        Item[] newItems = (Item[]) new Object[newSize];
        for (int i = 0; i < nonNullItemCount; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    // delete and return a random item
    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        int indexToRemove = StdRandom.uniform(nonNullItemCount);
        Item item = items[indexToRemove];
        items[indexToRemove] = items[nonNullItemCount - 1];


        nonNullItemCount--;

        if (nonNullItemCount > 0 && nonNullItemCount == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {

        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return items[StdRandom.uniform(nonNullItemCount - 1)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

        return new RandomizedQueueIterator(items, nonNullItemCount);
    }


    public static void main(String[] args) {

    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int i;
        private int startingSize;
        private Item[] shuffledItems;

        public RandomizedQueueIterator(Item[] items, int startingSize) {

            i = 0;
            if (items != null) {
                shuffledItems = (Item[]) new Object[startingSize];
                StdRandom.shuffle(items);
                for (Item item : items) {
                    if (item != null) {
                        shuffledItems[i++] = item;
                    }
                }
            }
        }

        public boolean hasNext() {

            return i > 0;
        }

        public void remove() {

            throw new UnsupportedOperationException();
        }

        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return shuffledItems[--i];
        }
    }

}
