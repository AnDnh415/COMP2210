/**
 * Implements shift-right behavior in an array.
 *
 */

public class ShiftRight {


   /**
    * Shifts the elements at a[index] through a[a.length - 2] one
    * position to the right. 
    */
   public static void shiftRight(Object[] array, int index) {
   
      // FILL IN THE BODY OF THIS METHOD
      for (int i = array.length - 2; i >= index; i--) {
         array[i + 1] = array[i];
      }
      array [index] = null;
   }

}