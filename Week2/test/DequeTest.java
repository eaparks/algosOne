import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DequeTest {

    Deque<String> unit;
    Deque<Object> unitObject;

    @Before
    public void setUp() throws Exception {

        unit = new Deque<String>();
    }

    @Test
    public void testIsEmpty() throws Exception {

        assertTrue(unit.isEmpty());

        unit.addFirst("asdf");
        assertFalse(unit.isEmpty());

        unit.removeFirst();
        assertTrue(unit.isEmpty());


        assertTrue(unit.size() == 0);
        unit.addFirst("xxxxxxxx");
        assertFalse(unit.isEmpty());

        unit.removeLast();
        assertTrue(unit.isEmpty());
    }

    @Test
    public void testAddFirst() throws Exception {

        unit.addFirst("qwer");
        assertTrue(unit.size() == 1);
        assertFalse(unit.isEmpty());

        unit.addFirst("qwerasdf");
        assertTrue(unit.size() == 2);
    }

    @Test
    public void testAddLast() throws Exception {

        unit.addFirst("11111111");
        unit.addFirst("22222222");
        unit.addLast("333333333");

        assertEquals(3, unit.size());
    }

    @Test
    public void testRemoveFirst() throws Exception {

        try {
            unit.removeFirst();
            Assert.fail("Was expecting exception.");
        } catch (NoSuchElementException e) {
            // expected
        }

        unit.addFirst("22222222");
        unit.addFirst("11111111");
        unit.addLast("333333333");

        assertEquals("11111111", unit.removeFirst());
        assertEquals(2, unit.size());
    }

    @Test
    public void testRemoveLast() throws Exception {

        try {
            unit.removeLast();
            Assert.fail("Was expecting exception.");
        } catch (NoSuchElementException e) {
            // expected
        }

        unit.addFirst("22222222");
        unit.addFirst("1111111c");
        unit.addFirst("1111111b");
        unit.addFirst("1111111a");
        unit.addLast("333333333");

        assertEquals("333333333", unit.removeLast());
        assertEquals(4, unit.size());

        assertEquals("22222222", unit.removeLast());
        assertEquals(3, unit.size());
    }

    @Test
    public void testIterator() throws Exception {

        Iterator<String> it = unit.iterator();

        assertFalse(it.hasNext());

        unit.addFirst("22222222");
        unit.addFirst("11111111");
        unit.addLast("333333333");
        int counter = 0;
        while (it.hasNext()) {
            String item = it.next();
            counter++;
        }

        assertEquals(3, unit.size());
    }

    @Test
    public void testIteratorRemove() throws Exception {

        unitObject = new Deque<Object>();
        unitObject.addFirst(new RandomizedQueue<String>());
        unitObject.addLast(new RuntimeException());

        Iterator<Object> it = unitObject.iterator();

        try {
            it.next();
            it.remove();
            fail("remove() should have thrown exception.");
        } catch (UnsupportedOperationException uoe) {
            // ignore
        }
    }
}