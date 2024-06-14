import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedSetTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void addTest() {
      LinkedSet testset = new LinkedSet();
      testset.add(2);
      testset.add(0);
      testset.add(1);
      testset.add(3);
      testset.add(1);
      String expected = "[0, 1, 2, 3]";
      String actual = testset.toString();
      
      Assert.assertEquals(expected, actual);
   }
}