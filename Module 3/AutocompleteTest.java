import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class AutocompleteTest {

   Term t1 = new Term("the", 23135851162L);
   Term t2 = new Term("of", 13151942776L);
   Term t3 = new Term("and", 12997637966L);
   Term t4 = new Term ("to", 12136980858L);
   Term t5 = new Term ("a", 9081174698L);
   Term t6 = new Term ("in", 8469404971L);
   Term t7 = new Term ("for", 5933321709L);
   Term t8 = new Term ("is", 4705743816L);
   Term t9 = new Term ("on", 3750423199L);
   Term t10 = new Term ("that", 3400031103L);

   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** Basic autocomplete test with small list. **/
   @Test public void autoCompleteTest1() {
      Term[] terms = {t1, t2, t3, t4};
      Autocomplete t = new Autocomplete (terms);
      Term[] actual = t.allMatches("t");
      Term[] expected = {t1, t4};
      Assert.assertEquals(expected, actual);
   }
   
   @Test public void autoCompleteTest2() {
      Term[] terms = {t1, t2, t3, t4, t5, t6, t7, t8, t9, t10};
      Autocomplete t = new Autocomplete (terms);
      Term[] actual = t.allMatches("an");
      Term[] expected = {t3};
      Assert.assertEquals(expected, actual);
   }
   
   @Test public void autoCompleteTest3() {
      Term m1 = new Term("Haruuta Sakurai", 2l);
      Term m2 = new Term("Haruuka Sakurai", 20l);
      Term m3 = new Term("Yuno Kashiki", 5l);
      Term m4 = new Term("MFuuta K.", 999l);
      Term m5 = new Term ("Muu Kusonoki", 420L);
      Term[] terms = {m1, m5, m2, m4, m3};
      Autocomplete t = new Autocomplete (terms);
      Term[] actual = t.allMatches("Haruu");
      Term[] expected = {m2, m1};
      Assert.assertEquals(expected, actual);
   }
}