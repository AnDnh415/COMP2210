import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;


public class BinarySearchTest {
   static Comparator<Integer> ascendingInteger =
        new Comparator<Integer>() {
           public int compare(Integer i1, Integer i2) {
              return i1.compareTo(i2);
           }
        };

   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void BinaryTest1() {
      Integer[] testArr = {1,2,3,4,5,6,7};
      int actual = BinarySearch.firstIndexOf(testArr, 2, ascendingInteger);
      int expected = 1;
      Assert.assertEquals(expected, actual);
   }
   
   @Test public void BinaryTest2() {
      Integer[] testArr = {1,2,3,4,5,6,7};
      int key = 6;
      int actual = BinarySearch.firstIndexOf(testArr, key, ascendingInteger);
      int expected = 5;
      Assert.assertEquals(expected, actual);
   }
   
   @Test public void BinaryTest3() {
      Integer[] testArr = {1,2,3,4,5,6,7};
      int key = 9;
      int actual = BinarySearch.firstIndexOf(testArr, key, ascendingInteger);
      int expected = -1;
      Assert.assertEquals(expected, actual);
   }
   
   @Test public void BinaryTest4() {
      Integer[] testArr = {1,2,2,2,2,2,7};
      int key = 2;
      int actual = BinarySearch.firstIndexOf(testArr, key, ascendingInteger);
      int expected = 1;
      Assert.assertEquals(expected, actual);
   }
   
   @Test public void BinaryTest5() {
      Integer[] testArr = {1,2,2,2,2,2,7};
      int key = 2;
      int actual = BinarySearch.lastIndexOf(testArr, key, ascendingInteger);
      int expected = 5;
      Assert.assertEquals(expected, actual);
   }
}
