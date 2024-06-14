import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet; 


public class GameTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


//Tests for the setBoard method
    /** Tests setBoard when everything is valid.
    Also tests getBoard. **/
   @Test public void setBoardTest1(){
      WordGame game = new WordGame();
      String[] testArr = {"A", "B", "C", "D"};
      game.setBoard(testArr);
      
      String expected = "[A, B]\n[C, D]\n";
      String actual = game.getBoard();
      Assert.assertEquals (expected, actual);    
   }
   
   /** Tests setBoard when testArr.length is not a perfect square **/
   @Test public void setBoardTest2() {
      Assert.assertThrows(IllegalArgumentException.class, 
         ()->{
            String[] testArr = {"one", "two", "three"};
            WordGame game = new WordGame();
            game.setBoard(testArr);
         });
   }


/*Makes sure that the loadLexicon method throws an IllegalArgumentException when filename = null.*/
   @Test public void nullLexiconTest(){
      Assert.assertThrows(IllegalArgumentException.class, 
         ()->{
            WordGame game = new WordGame();
            game.loadLexicon(null);
         });
   }
   
   /**checks to make sure that isValidWord correctly returns**/
   @Test public void validWordTest(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      Assert.assertTrue(w.isValidWord("ABACK"));
      Assert.assertTrue(w.isValidWord("chain"));
      Assert.assertTrue(w.isValidWord("Zygote"));
   }
   
   @Test public void validWordTest2(){
      WordGame w = new WordGame();
      w.loadLexicon("OWL.txt");
      Assert.assertTrue(w.isValidWord("AA"));
      Assert.assertTrue(w.isValidWord("Chain"));
      Assert.assertTrue(w.isValidWord("zoogeographical"));
      Assert.assertFalse(w.isValidWord("Blacknight"));
   }
  
  /**Tests the isValidPrefix method when the prefix should be valid.**/
   @Test public void isValidPrefixTest1(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      Assert.assertTrue(w.isValidPrefix("ca"));
   } 
   
  /**Tests the isValidPrefix method when the prefix should not be valid.**/
   @Test public void isValidPrefixTest2(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      Assert.assertFalse(w.isValidPrefix("xxxxzzzxxsxs"));
   }
   /**Tests the isValidPrefix method when match is null. **/
   @Test public void isValidPrefixTest3(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      Assert.assertFalse(w.isValidPrefix("zzzzzzzzzz"));
   }
   
   /**Tests the isOnBoard method when word is on the board**/
   @Test public void isOnBoardTest1(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      List actual = new ArrayList();
      actual = w.isOnBoard("peace");
      
      List expected = new ArrayList();
      expected.add(7);
      expected.add(6);
      expected.add(3);
      expected.add(2);
      expected.add(1);
      
      Assert.assertEquals(expected, actual);
   }
   
   /**Tests the isOnBoard method when some of word is on the board**/
   @Test public void isOnBoardTest2(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      List actual = new ArrayList();
      actual = w.isOnBoard("pecs");
      
      List expected = new ArrayList();
      
      Assert.assertEquals(expected, actual);
   }


/**Tests the isOnBoard method when the search algorythm has to double back**/
   @Test public void isOnBoardTest3(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      List actual = new ArrayList();
      actual = w.isOnBoard("pebty");
      
      List expected = new ArrayList();
      expected.add(7);
      expected.add(6);
      expected.add(10);
      expected.add(14);
      expected.add(15);
      
      Assert.assertEquals(expected, actual);
   }


/**Tests the isOnBoard method when the search has to go through many starting points**/
   @Test public void isOnBoardTest4(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      List actual = new ArrayList();
      actual = w.isOnBoard("eb");
      
      List expected = new ArrayList();
      expected.add(6);
      expected.add(10);
      
      Assert.assertEquals(expected, actual);
   }
   /**Tests the isOnBoard method when it is called multiple times**/
   @Test public void isOnBoardTest5(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      List actual = new ArrayList();
      actual = w.isOnBoard("peace");
      actual = w.isOnBoard("eb");
      
      List expected = new ArrayList();
      expected.add(6);
      expected.add(10);
      
      Assert.assertEquals(expected, actual);
   }

 /**isOnBoard method test from project document**/
   @Test public void isOnBoardTest6(){
      WordGame w = new WordGame();
      w.loadLexicon("words.txt");
      w.setBoard(new String[]{"E", "E", "C", "A", "A", "L", "E", "P", "H", 
                                  "N", "B", "O", "Q", "T", "T", "Y"});
      List<Integer> actual = w.isOnBoard("LENT");
      List<Integer> expected = new ArrayList<Integer>();
      expected.add(5);
      expected.add(6);
      expected.add(9);
      expected.add(13);
      Assert.assertEquals(expected, actual);
   }
   
   @Test public void getallScorableWordsTest(){
      WordGame w = new WordGame();
      w.loadLexicon("words.txt");
      w.setBoard(new String[]{"E", "E", "C", "A", "A", "L", "E", "P", "H", 
                                  "N", "B", "O", "Q", "T", "T", "Y"});
                                  
      SortedSet<String> actual = w.getAllScorableWords(6);
      
      SortedSet<String> expected = new TreeSet<String>();
      expected.add("ALEPOT");
      expected.add("BENTHAL");
      expected.add("PELEAN");
      expected.add("TOECAP");
      
      Assert.assertEquals(expected, actual);
   }
   
   @Test public void getScoreForWordsTest(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      SortedSet<String> wordList = new TreeSet<String>();
      wordList.add ("peace");
      wordList.add ("eel");
      wordList.add ("cape");
      wordList.add ("Smitres");
      wordList.add ("elan");
      wordList.add ("dressmake");
   
      int actual = w.getScoreForWords(wordList, 4);
      int expected = 4;
   
      Assert.assertEquals (expected, actual); 
   }

   @Test public void vocareumTest1(){
      WordGame w = new WordGame();
      w.loadLexicon("words_medium.txt");
      w.setBoard(new String[]{"TIGER"});
      SortedSet<String> actual = w.getAllScorableWords(7); 
      SortedSet<String> expected = new TreeSet();
      Assert.assertEquals(expected, actual);  
   }
   
   @Test public void vocareumTest2(){
      WordGame w = new WordGame();
      w.loadLexicon("words_medium.txt");
      w.setBoard(new String[]{"C","A","X","T",});
      List<Integer> actual = w.isOnBoard("CAT");
      List<Integer> expected = new ArrayList();
      expected.add(0);
      expected.add(1);
      expected.add(3);
      Assert.assertEquals(expected, actual);
   }
   
   @Test public void vocareumTest3(){
      WordGame w = new WordGame();
      w.loadLexicon("words_medium.txt");
      w.setBoard(new String[]{
         "O","Y","D","D","T","P","N","R","A","H","E","L","C","S","B","P","S","U","B","G",
         "U","P","Y","H","R","R","X","R","E","F","H","D","H","T","K","X","K","O","Z","F",
         "W","Y","H","Y","T","C","H","M","V","P","R","T","A","K","N","E","S","I","B","T",
         "M","V","Y","Q","E","U","O","E","F","A","K","J","C","W","I","K","I","U","K","T",
         "P","O","F","E","G","Z","T","X","O","Z","T","H","K","B","M","G","D","P","P","P",
         "G","U","E","S","C","J","C","B","Q","F","T","R","I","P","N","I","E","W","P","K",
         "H","K","G","B","B","L","Y","J","P","J","E","O","N","Q","V","N","B","S","H","R",
         "N","Z","R","G","A","E","W","P","L","L","Z","R","G","I","E","T","U","N","R","L",
         "I","K","T","J","K","J","F","C","I","T","M","R","D","T","R","E","G","L","J","G",
         "I","K","H","L","C","V","P","P","D","S","Q","E","W","O","C","R","L","V","L","P",
         "T","A","T","N","O","R","M","W","K","O","D","O","U","O","V","F","M","H","V","V",
         "S","I","X","Z","L","O","T","Z","L","B","R","G","F","Q","P","A","Y","P","D","L",
         "B","K","S","N","C","H","O","P","Y","K","H","C","R","R","I","C","S","B","J","X",
         "R","F","I","Y","R","H","B","Z","I","P","C","K","I","N","O","E","C","C","U","C",
         "P","I","J","R","E","Y","E","Z","U","R","R","M","F","S","M","R","N","J","I","B",
         "T","Q","O","C","V","R","O","T","X","H","C","R","W","S","A","V","T","N","U","I",
         "O","W","X","C","O","R","X","Q","A","S","A","S","S","E","M","B","L","Y","O","Z",
         "F","P","L","S","C","I","T","L","U","M","O","N","I","T","O","R","J","W","I","N",
         "L","L","L","E","L","J","R","R","E","M","M","O","B","D","X","I","J","D","S","R",
         "L","C","H","S","H","Y","U","L","P","M","O","U","S","E","C","B","I","I","U","I"});
      List<Integer> actual = w.isOnBoard("CHURCHGOER");
      List<Integer> expected = new ArrayList();
      Assert.assertEquals(10, actual.size());
   }

   @Test public void vocareumTest4(){
      WordGame game = new WordGame();
      game.setBoard(new String[] {"CAT","X","FISH","XXXX"});
      game.loadLexicon("words_medium.txt");
      SortedSet actual = game.getAllScorableWords(3);
      SortedSet expected = new TreeSet();
      expected.add("CAT");
      expected.add("FISH");
      expected.add("CATFISH");
      
      Assert.assertEquals(expected, actual);
   }
/*These tests require direct acess to the lexicon, so don't do them without making lexicon public, K?

** Makes sure that the loadLexicon method creates a collection with the correct amount of words. **
   @Test public void lexiconTest1(){
      WordGame w = new WordGame();
      w.loadLexicon("words_small.txt");
      Assert.assertEquals (19912, w.lexicon.size());
   }
   
   @Test public void lexiconTest2(){
      WordGame w = new WordGame();
      w.loadLexicon("OWL.txt");
      Assert.assertEquals (167964, w.lexicon.size());
   }
   
   @Test public void lexiconTest3(){
      WordGame w = new WordGame();
      w.loadLexicon("CSW12.txt");
      Assert.assertEquals (270163, w.lexicon.size());
   }
   */
   
}
