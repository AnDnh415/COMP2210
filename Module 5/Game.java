import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.io.FileNotFoundException;
import java.util.Arrays; 
/**
 * Creat a word search game in 2 dimensions,
 * based on WordSearGame.
 * @author An Dinh (abd0060@auburn.edu)
 *
 */
public class Game implements WordSearchGame {
   private String[][] board;
   private TreeSet<String> lexicon;
   
   /**
    *Creates a default board.
    */
   public Game() {
      String[][] defaultBoard = {
         {"E", "E", "C", "A"},
         {"A", "L", "E", "P"},
         {"H", "N", "B", "O"},
         {"Q", "T", "T", "Y"}
         };
      board = defaultBoard;
   }
 /**
  *Loads in a lexicon from the given file.
  *The first word of each line in the text file is added to the lexicon,
  *which is represented by a TreeSet of Strings.
  *
  *@param fileName The name of the file to be used as a lexicon.
  *@throws IllegalArgumentException if fileName is null.
  *@throws IllegalArgumentException if file cannot be opened.
  */
   public void loadLexicon(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      lexicon = new TreeSet<String>();
      try {
         Scanner fileScanner = new Scanner(new File(fileName));
         while (fileScanner.hasNext()) {
            lexicon.add(fileScanner.next().toUpperCase());
            if (fileScanner.hasNextLine()) {
               fileScanner.nextLine();
            }
            else {
               break;
            }
         }
      }
      catch (FileNotFoundException e)
      {
         throw new IllegalArgumentException();
      }
   }
   
   /** 
    *A method that sets the board with the input array of strings.
    *This board is made up of a 2d array with N rows and N collumns.
    *N is the square root of the input String array's length.
    *This makes a square gameboard with as many tiles as 
    *there are values in the input array.
    *
    *@param letterArray This is the array that the board will be derived from. 
    *    The board stores this Array's values in a 2d array
    *    of size [n][n] with row-major ordering.
    *@throws IllegalArgumentException if letterArray is null 
    *    or if letterArray.length is not a perfect square.
    */
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
   
      if (Math.sqrt(letterArray.length) % 1 != 0) {
         throw new IllegalArgumentException();
      }
      
      int n = (int) Math.sqrt(letterArray.length);
      board = new String[n][n];
      int count = 0;
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            board[i][j] = letterArray[count];
            count++;
         }
      }
   }
   
  /**
   * A method that returns the Board.
   * @return String
   */ 
   public String getBoard() {
      String boardOut = "";
      for (String[] row: board) {
         boardOut += "[";
         for (String value: row) {
            boardOut += (value + ", ");
         }
         boardOut = boardOut.substring(0, boardOut.length() - 2);
         boardOut += "]\n";
      }
      
      return boardOut;
   }
   
   
 /**
  * Returns all possible words on the board that would be legal to play.
  *
  * @param minimumWordLength the minimum length
  *   required for a word to be scorable
  * @return java,util.sortedSet containing all scorable words on the board.
  *   A scorable word is a word that is in the currently loaded lexicon
  *   and has a length <= minimumWordLength.
  * @throws IllegalArgumentException if minimumWordLength < 1
  * @throws IllegalStateException if loadLexicon has not been called.
  */

   public SortedSet<String> getAllScorableWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
   
      if (board.length == 1 && board[0].length == 1){
         SortedSet<String> returnSet = new TreeSet<String>();
         if (board[0][0].length() >= minimumWordLength && isValidWord(board[0][0])){
            returnSet.add(board[0][0]);
         }
         return returnSet;
         
      }
      TreeSet<String> word = new TreeSet<String>();
      Grid walker = new Grid(board);
      
      for (int row = 0; row < board.length; row++) {
         for (int col = 0; col < board[row].length; col++) {
            word.addAll(
               walker.searchAllPossibleWords(row, col, minimumWordLength));
         }
      }
      return word;
   }
   
 /**
  * Calculates the total amount of score for a given set of words.
  * the score of a word is calculated as follows:
  * one point is given if the word has a length equal to minimumWordLength
  * and an extra point is given for 
  * each character beyond the minimum word length.
  * A word is only scorible if all three conditions are met:
  *   (1) The word is longer than the minimum word length
  *   (2) The word is in the currently loaded dictionary
  *   (3) the word is on the board.
  * 
  * @param words the set of words to be scored.
  * @param minimumWordLength the minumum amount
  *   of characters for a word to be scorable
  * @return the total score of all words in the given set.
  * @throws IllegalArgumentException if minimumWordLength < 1
  * @throws IllegalStateException if loadLexicon has not been called.
  */
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
   
      int score = 0;
      
      for (String word : words) {
         if ((word.length() >= minimumWordLength
            && isValidWord(word))
               && isOnBoard(word).size() > 0) {
            score += (word.length() - (minimumWordLength - 1));
         }
      }
      return score;
   }
   
   /**
    *checks if the given word is in the curently loaded dictionary.
    *@param wordToCheck the word to validate within the lexicon
    *@return true if wordToCheck is in the lexicon, false otherwise.
    *@throws IllegalArgumentException if wordToCheck is null.
    *@throws IllegalStateException if no lexicon is currently loaded.
    */
   public boolean isValidWord(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon.size() == 0) {
         throw new IllegalStateException();
      }
      return (lexicon.contains(wordToCheck.toUpperCase()));
   }
  
  /**
   *Determines if there is at least one word in the lexicon
   *whose first characters match the given prefix.
   *
   *@param prefixToCheck the prefix to validate within the lexicon
   *@return true if prefixToCheck is the prefix
   *of at least one word in the lexicon, false otherwise.
   *@throws IllegalArgumentException if wordToCheck is null.
   *@throws IllegalStateException if no lexicon is currently loaded.
   */ 
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon.size() == 0) {
         throw new IllegalStateException();
      }
   
      String match  = lexicon.ceiling(prefixToCheck.toUpperCase());
      try {
      //A standard string method
         if (match.regionMatches(true, 
            0, prefixToCheck, 0, prefixToCheck.length())) {
            return true;
         }
         return false;
      } 
      catch (NullPointerException e) {
         return false;
      } 
      
   }
   /**
    * Determines if a particular word is on the board.
    * If it is, it returns a list with the locations
    * of the tiles to make the word in order.
    *
    * @param wordToCheck The word to validate
    * @return java.util.List containing a set of locations
    *    which make a path that makes up the given word on the game board.
    *    tiles are numbered starting from zero top to bottom,
    *    left to right (row major order), as in the example below
    *    [1,2,3]
    *    [4,5,6]
    *    [7,8,9]
    *    If no path for the given word exists, returns an empty list.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
    
      Grid walker = new Grid(board);
      for (int row = 0; row < board.length; row++) {
         for (int col = 0; col < board[row].length; col++) {
            if (wordToCheck.toUpperCase().startsWith(board[row][col])) {
               if (walker.searchForWord(row, col, wordToCheck.toUpperCase())) {
                  return walker.sendQueue();
               }
            }
         }
      }
      return new ArrayList<Integer>();
   }
  
  
   class Grid {
   
      private String [][] gridBoard;
      private boolean[][] visited;
      private int numRows;
      private int numCols;
      private Position location;
      private static final int MAX_NEIGHBORS = 8;
      private String wordFormed = "";
      private Deque<Position> queue = new ArrayDeque<>();
      
   
      Grid(String[][] gridIn) {
         gridBoard = gridIn;
         numRows = gridBoard.length;
         numCols = gridBoard[0].length;
         visited = new boolean[numRows][numCols];
      }
      
      public boolean searchForWord(int row, int col, String wordSearched) {
         location = new Position(row, col);
         String wordRemaining = wordSearched;
         visit(location);
         wordRemaining = wordRemaining.substring(
            gridBoard[location.getX()][location.getY()].length());
         queue.addLast(location);
        
        
         if (wordRemaining == "") {
            visited = new boolean[numRows][numCols];
            return true;
         }
         
         //searches through the maze
         for (Position neighbor:location.neighbors()) {
            if (wordRemaining.startsWith(gridBoard[neighbor.getX()][neighbor.getY()])
               && !isVisited(neighbor)) {
               boolean wordFound = searchForWord(neighbor.getX(),
                  neighbor.getY(), wordRemaining);
               if (wordFound) {
                  visited = new boolean[numRows][numCols];
                  return true;
               }
               
            }
         }
         queue.removeLast();
         visited[row][col] = false;
         return false;
      }
      
      public ArrayList<Integer> sendQueue() {
         ArrayList<Integer> list = new ArrayList<Integer>();
         while (queue.peek() != null) {
            Position p = queue.remove();
            list.add(p.getX() * numRows + p.getY());
         }
         return list;
      }
      public SortedSet<String> searchAllPossibleWords(
         int row, int col, int minLength) {
         visited[row][col] = true;
         wordFormed += (gridBoard[row][col]);
         SortedSet<String> wordsFound = new TreeSet<String>();
         location = new Position(row, col);
         if (gridBoard[row][col].length() >= minLength
               && isValidWord(gridBoard[row][col])){
            wordsFound.add(gridBoard[row][col]);
         }
        
         for (Position neighbor : location.neighbors()) {
            if ((isValidPrefix(
               wordFormed + gridBoard[neighbor.getX()][neighbor.getY()]))
                  && (!visited[neighbor.getX()][neighbor.getY()])) {
               if ((isValidWord(
                  wordFormed + gridBoard[neighbor.getX()][neighbor.getY()]))
                     && (wordFormed + gridBoard[neighbor.getX()][neighbor.getY()])
                        .length() >= minLength) {
                  wordsFound.add(
                     wordFormed + gridBoard[neighbor.getX()][neighbor.getY()]);
               }
               wordsFound.addAll(searchAllPossibleWords(neighbor.getX(),
                   neighbor.getY(), minLength));
            }
         }
         
         //backtrack
        
         wordFormed = wordFormed.substring(0, wordFormed.length()
            - (gridBoard[row][col].length()));
         visited[row][col] = false;
         return wordsFound;
      } 
      private boolean isValid(Position p) {
         return (p.getX() >= 0) && (p.getX() < numRows)
            && (p.getY() >= 0) && (p.getY() < numCols);
      }
         
      private boolean isVisited(Position p) {
         return visited [p.getX()][p.getY()];
      }
         
      private void visit(Position p) {
         visited [p.getX()][p.getY()] = true;
      }
         
      //Position class and its methods
      
      public class Position {
         private int x;
         private int y;
      
         Position(int xIn, int yIn) {
            x = xIn;
            y = yIn;
         }
       
         public int getX() {
            return x;
         }
      
         public int getY() {
            return y;
         }
      //Generate all possible neighboring positions
         public Position[] neighbors() {
            Position[] nbrs = new Position[MAX_NEIGHBORS];
            int count = 0;
            Position p;
         
            for (int i = -1; i <= 1; i++) {
               for (int j = -1; j <= 1; j++) { 
                  if (!((i == 0) && (j == 0))) {
                     p = new Position(x + i, y + j);
                     if (isValid(p)) {
                        nbrs[count++] = p;
                     }
                  }
               }
            }
            return Arrays.copyOf(nbrs, count);
         }
                  
         @Override
         public String toString() {
            return "(" + x + ", " + y + ")";
         }
         
      }
   }
}
