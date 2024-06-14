import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class TermTest {

   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void sortDefaultTest() {
      Term T1 = new Term ("of", 13151942776L);
      Term T2 = new Term ("the", 23135851162L);
      Term T3 = new Term ("and", 12997637966L);
      Term[] actual = {T1, T2, T3};
      Term[] expected = {T3, T1, T2};
      Arrays.sort(actual);
      Assert.assertArrayEquals(expected, actual);
   }
   
   @Test public void byWeightTest() {
      Term T1 = new Term ("of", 13151942776L);
      Term T2 = new Term ("the", 23135851162L);
      Term T3 = new Term ("and", 12997637966L);
      Comparator<Term> c = Term.byDescendingWeightOrder();
      Term[] actual = {T1, T2, T3};
      Term[] expected = {T3, T1, T2};
      Arrays.sort(actual, c);
      Assert.assertArrayEquals(expected, actual);
   }
   
   @Test public void byPrefixOrderTest() {
      Term T1 = new Term ("of", 13151942776L);
      Term T2 = new Term ("the", 23135851162L);
      Term T3 = new Term ("and", 12997637966L);
      Comparator<Term> c = Term.byPrefixOrder(2);
      int actual = c.compare (T1, T2);
      int expected = 1;
      Assert.assertEquals(expected, actual);
   }
   
   
}
