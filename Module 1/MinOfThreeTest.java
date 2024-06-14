import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class MinOfThreeTest {


// Typical cases//
   /** A test that always fails. **/
   @Test public void min1Test1() {
      int a = -1;
      int b = 4;
      int c = 6;
      int expected = -1;
      int actual = MinOfThree.min1(a, b, c);
      Assert.assertEquals(expected, actual);
   }
   @Test public void min1Test2() {
      int a = 9;
      int b = 4;
      int c = 1;
      int expected = 1;
      int actual = MinOfThree.min1(a, b, c);
      Assert.assertEquals(expected, actual);
   }
// Special cases- duplicate numbers//
   @Test public void min1Test3() {
      int a = 1;
      int b = 1;
      int c = 8;
      int expected = 1;
      int actual = MinOfThree.min1(a, b, c);
      Assert.assertEquals(expected, actual);
   }
   @Test public void min1Test4() {
      int a = 9;
      int b = 11;
      int c = 9;
      int expected = 9;
      int actual = MinOfThree.min1(a, b, c);
      Assert.assertEquals(expected, actual);
   }
   @Test public void min1Test5() {
      int a = 1;
      int b = 1;
      int c = 1;
      int expected = 1;
      int actual = MinOfThree.min1(a, b, c);
      Assert.assertEquals(expected, actual);
   }
   
 // Typical cases//
   @Test public void min2Test1() {
      int a = 0;
      int b = 9;
      int c = 8;
      int expected = 0;
      int actual = MinOfThree.min2(a, b, c);
      Assert.assertEquals(expected, actual);
   }
   @Test public void min2Test2() {
      int a = -5;
      int b = -9;
      int c = 8;
      int expected = -9;
      int actual = MinOfThree.min2(a, b, c);
      Assert.assertEquals(expected, actual);
   }
 // Special cases//
   @Test public void min2Test3() {
      int a = 1;
      int b = 1;
      int c = 8;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
      Assert.assertEquals(expected, actual);
   }
   @Test public void min2Test4() {
      int a = 1;
      int b = 5;
      int c = 1;
      int expected = 1;
      int actual = MinOfThree.min2(a, b, c);
      Assert.assertEquals(expected, actual);
   }
   @Test public void min2Test5() {
      int a = 0;
      int b = 0;
      int c = 0;
      int expected = 0;
      int actual = MinOfThree.min2(a, b, c);
      Assert.assertEquals(expected, actual);
   }
}
