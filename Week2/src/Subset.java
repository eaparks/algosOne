/**
 * Created by edparks on 2/13/14.
 */
public class Subset {

    private RandomizedQueue<String> queue;

    public Subset() {

        queue = new RandomizedQueue<String>();
    }

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        if (k < 0) {
            throw new IllegalArgumentException("k must be >= zero");
        }
        if (k > 0) {
            Subset subset = new Subset();
            while (!StdIn.isEmpty()) {
                String item = StdIn.readString();
                subset.queue.enqueue(item);
            }
            if (k > subset.queue.size()) {
                throw new IllegalArgumentException("k must be <= input count.");
            }
            while (k-- > 0) {
                StdOut.println(subset.queue.dequeue());
            }
        }
    }
}
