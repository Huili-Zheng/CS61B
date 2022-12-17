package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

public class ArrayDequeTest {

    @Test
    /** Add a few things to list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized array deque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {


        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        // should be empty
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String>  ad1 = new ArrayDeque<String>();
        ArrayDeque<Double>  ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigArraydequeTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            ad1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
        }
    }
    
    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<Integer>();
        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                ad1.addLast(randVal);
                ad2.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");

            } else if (operationNumber == 1) {
                // size
                int size = ad1.size();
                int bsize = ad2.size();
                System.out.println("size: " + size);
                System.out.println("size: " + bsize);
                assertEquals(size, bsize);
            } else if (operationNumber == 2) {
                // getLast
                int size = ad1.size();
                int bsize = ad2.size();
                assertEquals(size, bsize);
                if (size > 0) {
                    int aLast = ad1.get(size - 1);
                    int bLast = ad2.get(bsize - 1);
                    System.out.println("A size: " + size);
                    System.out.println("B size: " + bsize);
                    assertEquals(aLast, bLast);
                }
            }
            else {
                // removeLast
                int size = ad1.size();
                int bsize = ad2.size();
                assertEquals(size, bsize);
                if (size > 0) {
                    ad1.removeLast();
                    ad2.removeLast();
                    System.out.println("size: " + size);
                    System.out.println("size: " + bsize);
                }
            }
        }
    }

    @Test
    public void equalTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        LinkedListDeque<Integer> lld = new LinkedListDeque<Integer>();
        ad.addLast(5);
        ad.addLast(10);
        ad.addLast(15);
        lld.addLast(5);
        lld.addLast(10);
        lld.addLast(15);
        assertTrue(ad.equals(lld));
    }
}
