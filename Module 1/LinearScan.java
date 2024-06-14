/**
    * Program count negative numbers in an array.
    * 
    * @author An Dinh
    * @version 08/21/2023
    */
public class LinearScan {
   public static int countNegative(int[] a) {
      int negativeCount = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] < 0) {
            negativeCount++;
         }
      }
      return negativeCount;
   }
}
