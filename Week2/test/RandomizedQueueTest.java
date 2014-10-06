import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class RandomizedQueueTest {

    RandomizedQueue<String> stringUnit;

    @Before
    public void setUp() throws Exception {

        stringUnit = new RandomizedQueue<String>();
    }

    @Test
    public void testIsEmpty() throws Exception {

        assertTrue(stringUnit.isEmpty());

    }

    @Test
    public void testSize() throws Exception {

        assertTrue(stringUnit.size() == 0);

        stringUnit.enqueue("asdf");
        stringUnit.enqueue("qwer");
        stringUnit.enqueue("xxxx");
        stringUnit.enqueue("yyyyyy");
        stringUnit.enqueue("asdf");
        assertTrue(stringUnit.size() == 5);
        System.out.println(stringUnit.toString());

        stringUnit.enqueue("qwer");
        stringUnit.enqueue("xxxx");
        stringUnit.enqueue("yyyyyy");
        assertTrue(stringUnit.size() == 8);
        System.out.println(stringUnit.toString());
    }

    @Test
    public void testSizeWithDequeue() throws Exception {

        assertTrue(stringUnit.size() == 0);

        stringUnit.enqueue("asdf");
        stringUnit.enqueue("qwer");
        stringUnit.enqueue("xxxx");
        stringUnit.enqueue("yyyyyy");
        stringUnit.enqueue("uuuuuuu");
        assertTrue(stringUnit.size() == 5);
        System.out.println(stringUnit.toString());

        stringUnit.dequeue();
        assertTrue(stringUnit.size() == 4);
        System.out.println(stringUnit.toString());

        stringUnit.enqueue("111111");
        stringUnit.enqueue("222222");
        stringUnit.enqueue("333333");
        assertTrue(stringUnit.size() == 7);
        System.out.println(stringUnit.toString());
    }

    @Test
    public void testEnqueue() throws Exception {

        try {
            stringUnit.sample();
            fail("Should have seen NoSuchElementException!");
        } catch (NoSuchElementException nse) {
            // expected
        }
        stringUnit.enqueue("11111");
        assertEquals("11111", stringUnit.sample());

        stringUnit.enqueue("22222");
        stringUnit.enqueue("33333");
        stringUnit.enqueue("44444");
        stringUnit.enqueue("55555");
        assertTrue(stringUnit.size() == 5);
        System.out.println(stringUnit.sample());
        assertTrue(stringUnit.size() == 5);

        stringUnit.enqueue("66666");
        stringUnit.enqueue("77777");
        stringUnit.enqueue("88888");
        assertTrue(stringUnit.size() == 8);
        System.out.println(stringUnit.sample());
        assertTrue(stringUnit.size() == 8);
    }

    @Test
    public void testDequeueWithUniqueStrings() throws Exception {

        stringUnit.enqueue("11111");
        stringUnit.enqueue("22222");
        stringUnit.enqueue("33333");
        stringUnit.enqueue("44444");
        stringUnit.enqueue("55555");
        assertTrue(stringUnit.size() == 5);
        System.out.println(stringUnit.toString());

        String dequeued = stringUnit.dequeue();
        assertTrue(stringUnit.size() == 4);
        System.out.println(">>> Dequeued: " + dequeued);
        System.out.println(stringUnit.toString());

        stringUnit.enqueue("66666");
        stringUnit.enqueue("77777");
        stringUnit.enqueue("88888");
        assertTrue(stringUnit.size() == 7);
        System.out.println(stringUnit.toString());

        Iterator<String> it = stringUnit.iterator();

        assertTrue(it.hasNext());

        while (it.hasNext()) {
            assertTrue(!dequeued.equals(it.next()));
        }

    }

    @Test
    public void testSample() throws Exception {

        stringUnit.enqueue("11111");
        stringUnit.enqueue("22222");
        stringUnit.enqueue("33333");
        stringUnit.enqueue("44444");
        stringUnit.enqueue("55555");
        assertTrue(stringUnit.size() == 5);
        assertNotNull(stringUnit.sample());
        assertTrue(stringUnit.size() == 5);



        stringUnit.enqueue("66666");
        stringUnit.enqueue("77777");
        stringUnit.enqueue("88888");
        stringUnit.sample();
        assertTrue(stringUnit.size() == 8);
    }
}