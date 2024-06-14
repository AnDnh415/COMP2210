import java.io.File;
import java.lang.Math;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Arrays;
import java.io.FileNotFoundException;
/**
 * Creates a word search games from WordSearchGame.
 *
 * @author An Dinh (abd0060@auburn.edu)
 *
 */
public class WordGame implements WordSearchGame {
   private TreeSet<String> lexicon;
   private String[][] board;
   //track visited positions
   private boolean[][] visited;
   private int row;
   private int column;
   private Position position; 
   private static int MAX_NEIGHBORS = 8;
   //path for all words
   private ArrayList<Position> path2;
   //track path on board
   private ArrayList<Integer> path1;
   private SortedSet<String> allPossibleWords;
   private String currentWord;
 
 /** 
   * Create a default board.
   *
   */
   public WordGame() {
      String[][] defaultBoard = {
         {"E", "E", "C", "A"},
         {"A", "L", "E", "P"},
         {"H", "N", "B", "O"},
         {"Q", "T", "T", "Y"}
         };
      board = defaultBoard;
      markAllUnvisited();
   }  
 /**
 * Loads the lexicon into a data structure for later use. 
 * 
* @param fileName A string containing the name of the file to be opened.
* @throws IllegalArgumentException if fileName is null
* @throws IllegalArgumentException if fileName cannot be opened.
*/
   public void loadLexicon(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      lexicon = new TreeSet<String>();
      
      // check the file
      try {
         Scanner scan = new Scanner(new File(fileName));
         while (scan.hasNext()) {
            lexicon.add(scan.next().toUpperCase());
            if (scan.hasNextLine()) {
               scan.nextLine();
            }
            else {
               break;
            }
         }
      }
      catch (FileNotFoundException e) {
         throw new IllegalArgumentException();
      }
   }
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of board
    *     position (0,0) and index length-1 stores the contents of board position
    *     (N-1,N-1). Note that the board must be square and that the strings inside
    *     may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */
   public void setBoard(String[] letterArray) {
      if (letterArray == null || Math.sqrt(letterArray.length) % 1 != 0) {
         throw new IllegalArgumentException();
      }
      // find the square length
      int n = (int) Math.sqrt(letterArray.length);
      board = new String[n][n];
             
      // keep track of the letter
      int loc = 0;
      // set up the 2d boards
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            board[i][j] = letterArray[loc];
            loc++;
         }
      }
      markAllUnvisited();
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard() {
      String boardOut = "";
      for (String[] row : board) {
         boardOut += "[";
         for (String value : row) {
            boardOut += (value + ", ");
         }
         // remove the last comma and space
         boardOut = boardOut.substring(0, boardOut.length() - 2);
         boardOut += "]\n";
      }
      return boardOut;
   }
   
/**
    * Retrieves all scorable words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
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
      path2 = new ArrayList<Position>();
      allPossibleWords = new TreeSet<String>();
      currentWord = "";
      
      for (int i = 0; i < column; i++) {
         for (int j = 0; j < row; j++) {
            currentWord = board[i][j];
            if (isValidWord(currentWord) && currentWord.length() >= minimumWordLength) {
               allPossibleWords.add(currentWord);
            }
            if (isValidPrefix(currentWord)) {
               Position attempt = new Position(i, j);
               path2.add(attempt);
               
               dfs2(i, j, minimumWordLength);
               // remove if it fails
               path2.remove(attempt);
            }
         }
      }
      return allPossibleWords;
   }

/**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
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
         if ((word.length() >= minimumWordLength && isValidWord(word))
               && isOnBoard(word).size() > 0) {
            score += (word.length() - (minimumWordLength - 1));
         }
      }
      return score;
   }

/**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck){
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      return (lexicon.contains(wordToCheck.toUpperCase()));
   }

/**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      String prefix = prefixToCheck.toUpperCase();
      String word = lexicon.ceiling(prefix);
      if (word.startsWith(prefixToCheck) || word.equals(prefixToCheck)) {
         return true;
      }
      return false;
   }
   
/**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
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
      
      path1 = new ArrayList<Integer>();
      path2 = new ArrayList<Position>();
      wordToCheck = wordToCheck.toUpperCase();
      currentWord = "";
      
      for (int i = 0; i < column; i++) {
         for (int j = 0; j < row; j++) {
            // if first spot is a whole word, add and return
            if (wordToCheck.equals(board[i][j]) && isValidWord(wordToCheck)) {
               path1.add(i * row + j);
               return path1;
            }
            if (wordToCheck.startsWith(board[i][j])) {
               Position pos = new Position(i, j);
               path2.add(pos);
               currentWord = board[i][j];
               dfs(i, j, wordToCheck);
               if (!wordToCheck.equals(currentWord)) {
                  path2.remove(pos);
               }
               else {
                  for (Position p : path2) {
                     path1.add((p.x * row) + p.y);
                  }
                  return path1;
               }
            }
         }
      }
      return path1;
   }
   
   
   // mark all positions unvisited
   private void markAllUnvisited() {
      visited = new boolean[row][column];
      for (boolean[] numRow : visited) {
         Arrays.fill(numRow, false);
      }
   }
   // mark path visited
   private void markPathVisited() {
      for (int i = 0; i < path2.size(); i++) {
         visit(path2.get(i));
      }
   }
   
   ////////////////////////////////////////
   /////Position class and its methods////
   //////////////////////////////////////
   private class Position {
      int x;
      int y;
      
      // contruct a position (x, y).
      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
      // get the neighbors of this position.
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
   
   /**
    * Check if the position is valid.
    * @param p the position
    */
   private boolean isValid(Position p) {
      return (p.x >= 0) && (p.x < row) && (p.y >= 0) && (p.y < column);
   }
   /**
    * check if the position is visited.
    * @param p the position
    */
   private boolean isVisited(Position p) {
      return visited[p.x][p.y];
   }
   // mark the position visited
   private void visit (Position p) {
      visited[p.x][p.y] = true;
   }
   
   /////////////////////////////////
   ///////Depth-first search///////
   ///////////////////////////////
   /**
    * Depth-first Search to check word on board.
    * @param x x value
    * @param y y value
    * @param wordCheck word to check
    */
   private void dfs(int x, int y, String wordCheck) {
      Position start =  new Position(x, y);
      markAllUnvisited();
      markPathVisited();
      for (Position p : start.neighbors()) {
         if (!isVisited(p)) {
            visit(p);
            if (wordCheck.startsWith(currentWord + board[p.x][p.y])) {
               currentWord += board[p.x][p.y];
               path2.add(p);
               dfs(p.x, p.y, wordCheck);
               if (wordCheck.equals(currentWord)) {
                  return;
               }
               else {
                  path2.remove(p);
                  int end = currentWord.length() - board[p.x][p.y].length();
                  currentWord = currentWord.substring(0, end);
               }
            }
         }
      }
      markAllUnvisited();
      markPathVisited();
   }
   /**
    * Depth first search for word has minimum length
    * @param x x value
    * @param y y value
    * @param min minimum length
    */
   private void dfs2(int x, int y, int min) {
      Position start = new Position(x, y);
      markAllUnvisited();
      markPathVisited();
      for (Position p : start.neighbors()) {
         if (!isVisited(p)) {
            visit(p);
            if (isValidPrefix(currentWord + board[p.x][p.y])) {
               currentWord += board[p.x][p.y];
               path2.add(p);
               if (isValidWord(currentWord) && currentWord.length() >= min) {
                  allPossibleWords.add(currentWord);
               }
               dfs2(p.x, p.y, min);
                // backtrack and remove last part of currentWord
               path2.remove(p);
               int end = currentWord.length() - board[p.x][p.y].length();
               currentWord = currentWord.substring(0, end);
            }
         }
      }
      markAllUnvisited();
      markPathVisited();
   }
}