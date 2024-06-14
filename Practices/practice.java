/**
 * Count the number of even values in an array.
 *
 */
public class CountEvens {

   //  C O M P L E T E   T H I S    M E T H O D 

   /**
    * Returns the number of even values in the paramter.
    */
   public static int countEvens(int[] values) {
      int evenCount = 0;
      for (int i : values) {
         if (i % 2 == 0) {
            evenCount++;
         }
      }
      return evenCount;
   }

}
