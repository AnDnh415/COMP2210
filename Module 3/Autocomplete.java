import java.util.Arrays;
import java.util.Comparator;

/**
 * Autocomplete.
 */
public class Autocomplete {

   private Term[] terms;

	/**
	 * Initializes a data structure from the given array of terms.
	 * This method throws a NullPointerException if terms is null.
	 */
   public Autocomplete(Term[] terms) {
      if (terms == null) {
         throw new NullPointerException();
      }
      this.terms = terms;
      Arrays.sort(terms);
   }

	/** 
	 * Returns all terms that start with the given prefix, in descending order of weight. 
	 * This method throws a NullPointerException if prefix is null.
	 */
   public Term[] allMatches(String prefix) {
      if (prefix == null) {
         throw new NullPointerException();
      }
      Term[] inTerms = Arrays.copyOf(terms, terms.length);
      Arrays.sort(inTerms);
      
      Term prefixTerm = new Term(prefix, 0l);
      int first = BinarySearch.<Term>firstIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));
      int last = BinarySearch.<Term>lastIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));
      Term[] tMatches = Arrays.copyOfRange(terms, first, last + 1);
      
      Comparator<Term> tW = new Term.CompareTermsByWeight();
      Arrays.sort(tMatches, tW);
      return tMatches;
   }
}