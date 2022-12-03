package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    public static void testThreeAddThreeRemove() {
      AListNoResizing<Integer> A = new AListNoResizing();
      BuggyAList<Integer> B = new BuggyAList();

      A.addLast(4);
      B.addLast(4);
      assertEquals(A.getLast(), B.getLast());
      A.addLast(5);
      B.addLast(5);
      A.addLast(6);
      B.addLast(6);
      assertEquals(A.size(), B.size());

      assertEquals(A.removeLast(), B.removeLast());
      assertEquals(A.removeLast(), B.removeLast());
      assertEquals(A.removeLast(), B.removeLast());
    }

  public static void randomizedTest() {
    AListNoResizing<Integer> A = new AListNoResizing();
    BuggyAList<Integer> B = new BuggyAList();
    int N = 500;
    for (int i = 0; i < N; i += 1) {
      int operationNumber = StdRandom.uniform(0, 4);
      if (operationNumber == 0) {
        // addLast
        int randVal = StdRandom.uniform(0, 100);
        A.addLast(randVal);
        B.addLast(randVal);
        System.out.println("addLast(" + randVal + ")");

      } else if (operationNumber == 1) {
        // size
        int size = A.size();
        int bsize = B.size();
        System.out.println("size: " + size);
        System.out.println("size: " + bsize);
        assertEquals(size, bsize);
      } else if (operationNumber == 2) {
        // getLast
        int size = A.size();
        int bsize = B.size();
        assertEquals(size, bsize);
        if (size > 0) {
          int aLast = A.getLast();
          int bLast = B.getLast();
          System.out.println("A size: " + size);
          System.out.println("B size: " + bsize);
          assertEquals(aLast, bLast);
        }
      }
      else {
        // removeLast
        int size = A.size();
        int bsize = B.size();
        assertEquals(size, bsize);
        if (size > 0) {
          A.removeLast();
          B.removeLast();
          System.out.println("size: " + size);
          System.out.println("size: " + bsize);
        }
      }
    }
  }
  public static void main(String[] args) {
    testThreeAddThreeRemove();
    randomizedTest();
  }
}
