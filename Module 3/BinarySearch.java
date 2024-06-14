import java.util.Arrays;
import java.util.Comparator;

/**
 * Binary search.
 */
public class BinarySearch {

   /**
    * Returns the index of the first key in a[] that equals the search key, 
    * or -1 if no such key exists. This method throws a NullPointerException
    * if any parameter is null.
    */
   public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      if (a == null || key == null || comparator == null) {
         throw new NullPointerException();
      }
      int low = 0;
      int high = a.length - 1;
      int middle = high / 2;
      while (low <= high) {
         if (comparator.compare(a[middle], key) < 0) {
            low = middle + 1;
         }
         else if (comparator.compare(a[middle], key) > 0) {
            high = middle - 1;
         }
         else if (comparator.compare(a[middle], key) == 0) {
            if (middle == 0) {
               return middle;
            }
            while (comparator.compare(a[middle], a[middle - 1]) == 0) {
               middle--;
               if (middle == 0) {
                  break;
               }
            }
            return middle;
         }
      }
      return -1;
   }

   /**
    * Returns the index of the last key in a[] that equals the search key, 
    * or -1 if no such key exists. This method throws a NullPointerException
    * if any parameter is null.
    */
   public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      if (a == null || key == null || comparator == null) {
         throw new NullPointerException();
      }
      int low = 0;
      int high = a.length - 1;
      while (low <= high) {
         int middle = (low + (high - low)) / 2;
         if (comparator.compare(key, a[middle]) < 0) {
            high = middle - 1;
         }
         else if (comparator.compare(key, a[middle]) > 0) {
            low = middle + 1;
         }
         else if (comparator.compare(key, a[middle]) == 0) {
            if (middle == a.length - 1) {
               return middle;
            }
            while (comparator.compare(a[middle], a[middle + 1]) == 0) {
               middle++;
               if (middle == a.length - 1) {
                  break;
               }
            }
            return middle;
         }
      }
      return -1;
   }
}
