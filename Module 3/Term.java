import java.util.Comparator;

/**
 * Autocomplete term representing a (query, weight) pair.
 * 
 */
public class Term implements Comparable<Term> {
   String query;
   long weight;
   /**
    * Initialize a term with the given query and weight.
    * This method throws a NullPointerException if query is null,
    * and an IllegalArgumentException if weight is negative.
    */
   public Term(String query, long weight) {
      if (query == null) {
         throw new java.lang.NullPointerException();
      }
      if (weight < 0) {
         throw new java.lang.IllegalArgumentException();
      }
      this.query = query;
      this.weight = weight;
   }

   /**
    * Compares the two terms in descending order of weight.
    */
   public static Comparator<Term> byDescendingWeightOrder() {
      Comparator<Term> weightOrder = new CompareTermsByWeight();
      return weightOrder;
   }
   /**
    * Compares the two terms in ascending lexicographic order of query,
    * but using only the first length characters of query. This method
    * throws an IllegalArgumentException if length is less than or equal
    * to zero.
    */
   public static Comparator<Term> byPrefixOrder(int length) {
      if (length <= 0) {
         throw new IllegalArgumentException();
      }
      Comparator<Term> orderByPrefix = 
         new Comparator<Term>() {
            public int compare (Term t1, Term t2) {
               String str1;
               String str2;
               if (t1.query.length() > length) {
                  str1 = t1.query.substring(0, length);
               }
               else {
                  str1 = t1.query;
               }
               if (t2.query.length() > length) {
                  str2 = t2.query.substring(0, length);
               }
               else {
                  str2 = t2.query;
               }
               return (str1.compareTo(str2));
            }
         };
      return orderByPrefix;
   }

   /**
    * Compares this term with the other term in ascending lexicographic order
    * of query.
    */
   @Override
   public int compareTo(Term other) {
      return this.query.compareTo(other.query);
   }

   /**
    * Returns a string representation of this term in the following format:
    * query followed by a tab followed by weight
    */
   @Override
   public String toString(){
      return query + "\t" + weight;
   }

   public static class CompareTermsByPrefix implements Comparator<Term> {
      public int compare(Term t1, Term t2) {
         return (t1.query.compareTo(t2.query));
      }
   }
   public static class CompareTermsByWeight implements Comparator<Term> {
      public int compare(Term t1, Term t2) {
         Long weight1 = t1.weight;
         Long weight2 = t2.weight;
         return (weight2.compareTo(weight1));
      }
   }
}