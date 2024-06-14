import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   AN DINH (abd0060@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  08/30/2023
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int min(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int currentMinValue = a[0];
      for (int i = 0; i < a.length; i++) {
         if (a[i] < currentMinValue) {
            currentMinValue = a[i];
         }
      }
      return currentMinValue;
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int currentMaxValue = a[0];
      for (int i = 0; i < a.length; i++) {
         if (a[i] > currentMaxValue) {
            currentMaxValue = a[i];
         }
      }
      return currentMaxValue;
   }



   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      if (k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      
      int[] bList = Arrays.copyOf(a, a.length);
      Arrays.sort(bList);
      int distinctValue = 1;
      for (int i = 1; i < a.length; i++) {
         if (a[i] != a[i-1]) {
            distinctValue++;
         }
      }
      
      if (k > distinctValue) {
         throw new IllegalArgumentException();
      }
      
      if (k == 1) {
         return (min(a));
      }
      if (k == a.length) {
         return (max(a));
      }
      
      int kthMin = min(a);
      for (int i = 1; i < k; i++){
         int cMin = max(a);
         for(int j = 0; j < a.length; j++){
            if ((a[j] < cMin) && (a[j] > kthMin)){
               cMin = a[j];
            }
         }
         kthMin = cMin;
      }
      return kthMin;
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      if (k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
     
      int[] bList = Arrays.copyOf(a, a.length);
      Arrays.sort(bList);
      int distinctValue = 1;
      for (int i = 1; i < a.length; i++) {
         if (a[i] != a[i-1]) {
            distinctValue++;
         }
      }
      
      if (k > distinctValue) {
         throw new IllegalArgumentException();
      }
      
      if (k == 1) {
         return (max(a));
      }
      if (k == a.length) {
         return (min(a));
      }
      
      int kthMax = max(a);
      for (int i = 1; i < k; i++){
         int cMax = min(a);
         for(int j = 0; j < a.length; j++){
            if ((a[j] > cMax) && (a[j] < kthMax)){
               cMax = a[j];
            }
         }
         kthMax = cMax;
      }
      return kthMax;
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int[] rangeNumber = new int[0];
      for (int num : a) {
         if ((num >= low) && (num <= high)) {
            rangeNumber = Arrays.copyOf(rangeNumber, rangeNumber.length + 1);
            rangeNumber[rangeNumber.length-1] = num;
         }
      }
      return rangeNumber;
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int currentValue = max(a);
      for (int i = 0; i < a.length; i++) {
         if ((a[i] < currentValue) && (a[i] >= key)) {
            currentValue = a[i];
         }
      }
      if (currentValue < key) {
         throw new IllegalArgumentException();
      }
      return currentValue;
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int currentValue = min(a);
      for (int i = 0; i < a.length; i++) {
         if ((a[i] > currentValue) && (a[i] <= key)) {
            currentValue = a[i];
         }
      }
      if (currentValue > key) {
         throw new IllegalArgumentException();
      }
      return currentValue;
   }

}
