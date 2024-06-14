import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author An Dinh (abd0060@auburn.edu)
 */
public class Doublets implements WordLadderGame {

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   HashSet<String> lexicon;

   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
   public Doublets(InputStream in) {
      try {
         lexicon = new HashSet<String>();
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
            lexicon.add(str.toUpperCase());
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }


   //////////////////////////////////////////////////////////////
   // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
   //////////////////////////////////////////////////////////////
   /**
    * Returns the total number of words in the current lexicon.
    *
    * @return number of words in the lexicon
    */
   public int getWordCount(){
      return lexicon.size();
   }


   /**
    * Checks to see if the given string is a word.
    *
    * @param  str the string to check
    * @return     true if str is a word, false otherwise
    */
   public boolean isWord(String str) {
      str = str.toUpperCase();
      return lexicon.contains(str);
   }


   /**
    * Returns the Hamming distance between two strings, str1 and str2. The
    * Hamming distance between two strings of equal length is defined as the
    * number of positions at which the corresponding symbols are different. The
    * Hamming distance is undefined if the strings have different length, and
    * this method returns -1 in that case. See the following link for
    * reference: https://en.wikipedia.org/wiki/Hamming_distance
    *
    * @param  str1 the first string
    * @param  str2 the second string
    * @return      the Hamming distance between str1 and str2 if they are the
    *                  same length, -1 otherwise
    */
   public int getHammingDistance(String str1, String str2) {
      str1 = str1.toUpperCase();
      str2 = str2.toUpperCase();
      if (str1.length() != str2.length()) {
         return -1;
      }
      
      int distance = 0;
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            distance++;
         }
      }
      return distance;
   }


   /**
    * Returns all the words that have a Hamming distance of one relative to the
    * given word.
    *
    * @param  word the given word
    * @return      the neighbors of the given word
    */
   public List<String> getNeighbors(String word) {
      List<String> neighbors = new ArrayList<String>();
      
      for (int i = 0; i < word.length(); i++) {
         String hammingWord = word;
         char[] hammingWordChars = hammingWord.toCharArray();
         for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            hammingWordChars[i] = alphabet;
            hammingWord = String.valueOf(hammingWordChars);
            if (isWord(hammingWord) && !neighbors.contains(hammingWord.toUpperCase()) 
               && !hammingWord.equals(word)) {
               neighbors.add(hammingWord.toUpperCase());
            }
         }
      }
      return neighbors;
   }


   /**
    * Checks to see if the given sequence of strings is a valid word ladder.
    *
    * @param  sequence the given sequence of strings
    * @return          true if the given sequence is a valid word ladder,
    *                       false otherwise
    */
   public boolean isWordLadder(List<String> sequence) {
      if (sequence.isEmpty()) {
         return false;
      }
      for (String word : sequence) {
         if (!isWord(word)) {
            return false;
         }
      }
      for (int i = 0; i < sequence.size() - 1; i++) {
         if (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1) {
            return false;
         }
      }
      return true;
   }


  /**
   * Returns a minimum-length word ladder from start to end. If multiple
   * minimum-length word ladders exist, no guarantee is made regarding which
   * one is returned. If no word ladder exists, this method returns an empty
   * list.
   *
   * Breadth-first search must be used in all implementing classes.
   *
   * @param  start  the starting word
   * @param  end    the ending word
   * @return        a minimum length word ladder from start to end
   */
   public List<String> getMinLadder(String start, String end) {
      List<String> minLadder = new ArrayList<String>();
      ArrayList<String> memo = new ArrayList<String>();
      
      start = start.toUpperCase();
      end = end.toUpperCase();
      
      if (start.equals(end)) {
         minLadder.add(start.toLowerCase());
         return minLadder;
      }
      
      if (getHammingDistance(start, end) == -1) {
         return minLadder;
      }
      
      if (isWord(start) && isWord(end)) {
         memo = bfs(start,end);
      }
      for (int i = memo.size()-1; i >= 0; i--) {
         minLadder.add(memo.get(i));
      }
      return minLadder;
   }

   //////////////////////////////////
   ///Breath-first Search method////
   ////////////////////////////////
   private ArrayList<String> bfs(String start, String end) {
      Deque<Node> queue = new ArrayDeque<>();
      HashSet<String> visited = new HashSet<String>();
      
      visited.add(start);
      queue.addLast(new Node(start, null));
      Node endNode = new Node(end, null);
      
      while (!queue.isEmpty()) {
         Node n = queue.removeFirst();
         String word = n.word;
         List<String> neighbors = getNeighbors(word);
         
         for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
               visited.add(neighbor);
               queue.addLast(new Node(neighbor, n));
               
               if (neighbor.equals(end)) {
                  endNode.predecessor = n;
               }
            }
         }
      }
      
      ArrayList<String> memo = new ArrayList<String>();
      
      if (endNode.predecessor == null) {
         return memo;
      }
      Node m = endNode;
      while (m != null) {
         memo.add(m.word);
         m = m.predecessor;
      }
      return memo;
   }
   
   private class Node {
      String word;
      Node predecessor;
      
      public Node(String s, Node pred) {
         word = s;
         predecessor = pred;
      }
   }
}

