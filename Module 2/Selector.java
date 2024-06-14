import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods on Collections.
 *
 * @author  AN DINH (abd0060@auburn.edu)
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
     * Returns the minimum value in the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty, this method throws a
     * NoSuchElementException. This method will not change coll in any way.
     *
     * @param coll    the Collection from which the minimum is selected
     * @param comp    the Comparator that defines the total order on T
     * @return        the minimum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
   public static <T> T min(Collection<T> coll, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      Iterator<T> itr = coll.iterator();
      T min = itr.next();
      while (itr.hasNext()) {
         T val = itr.next();
         if (comp.compare(val, min) < 0) {
            min = val;
         }
      }
      return min;
   }


    /**
     * Selects the maximum value in the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty, this method throws a
     * NoSuchElementException. This method will not change coll in any way.
     *
     * @param coll    the Collection from which the maximum is selected
     * @param comp    the Comparator that defines the total order on T
     * @return        the maximum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
   public static <T> T max(Collection<T> coll, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      Iterator<T> itr = coll.iterator();
      T max = itr.next();
      while (itr.hasNext()) {
         T val = itr.next();
         if (comp.compare(val, max) > 0) {
            max = val;
         }
      }
      return max;
   }


    /**
     * Selects the kth minimum value from the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty or if there is no kth minimum
     * value, this method throws a NoSuchElementException. This method will not
     * change coll in any way.
     *
     * @param coll    the Collection from which the kth minimum is selected
     * @param k       the k-selection value
     * @param comp    the Comparator that defines the total order on T
     * @return        the kth minimum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
   public static <T> T kmin(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      if (k < 1 || k > coll.size()) {
         throw new NoSuchElementException();
      }
      ArrayList<T> collCopy = new ArrayList<T>(coll);
      Iterator<T> itr = coll.iterator();
      while (itr.hasNext()) {
         collCopy.add(itr.next());
      }
      java.util.Collections.sort(collCopy, comp);
      
      if (k == 1) {
         return Selector.min(collCopy, comp);
      }
      
      T cMin = collCopy.get(0);
      T kthMin = null;
      int distinctValue = 1;
      for (int i = 1; i < collCopy.size(); i++) {
         if (!collCopy.get(i).equals(cMin)) {
            distinctValue++;
            if (k == distinctValue) {
               kthMin = collCopy.get(i);
            }
         }
         cMin = collCopy.get(i);
      }
      if (kthMin == null || (distinctValue < k)) {
         throw new NoSuchElementException();
      }
      return kthMin;
   }


    /**
     * Selects the kth maximum value from the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty or if there is no kth maximum
     * value, this method throws a NoSuchElementException. This method will not
     * change coll in any way.
     *
     * @param coll    the Collection from which the kth maximum is selected
     * @param k       the k-selection value
     * @param comp    the Comparator that defines the total order on T
     * @return        the kth maximum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
   public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      if (k < 1 || k > coll.size()) {
         throw new NoSuchElementException();
      }
      
      ArrayList<T> collCopy = new ArrayList<T>();
      collCopy.addAll(coll);
      java.util.Collections.sort(collCopy, comp);
      java.util.Collections.reverse(collCopy);
      
      if (k == 1) {
         return Selector.max(coll, comp);
      }
      
      T cMax = collCopy.get(0);
      T kthMax = null;
      int distinctValue = 1;
      for (int i = 1; i < collCopy.size(); i++) {
         if (!collCopy.get(i).equals(cMax)) {
            distinctValue++;
            if (k == distinctValue) {
               kthMax = collCopy.get(i);
            }
         }
         cMax = collCopy.get(i);
      }
      if ((kthMax == null) || (distinctValue < k)) {
         throw new NoSuchElementException();
      }
      return kthMax;
   }


    /**
     * Returns a new Collection containing all the values in the Collection coll
     * that are greater than or equal to low and less than or equal to high, as
     * defined by the Comparator comp. The returned collection must contain only
     * these values and no others. The values low and high themselves do not have
     * to be in coll. Any duplicate values that are in coll must also be in the
     * returned Collection. If no values in coll fall into the specified range or
     * if coll is empty, this method throws a NoSuchElementException. If either
     * coll or comp is null, this method throws an IllegalArgumentException. This
     * method will not change coll in any way.
     *
     * @param coll    the Collection from which the range values are selected
     * @param low     the lower bound of the range
     * @param high    the upper bound of the range
     * @param comp    the Comparator that defines the total order on T
     * @return        a Collection of values between low and high
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
   public static <T> Collection<T> range(Collection<T> coll, T low, T high,
                                                      Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      Iterator<T> range = coll.iterator();
      Collection<T> rangeColl = new ArrayList<T>();
      while (range.hasNext()) {
         T val = range.next();
         if ((comp.compare(val, low) >= 0) && (comp.compare(val, high) <= 0)) {
            rangeColl.add(val);
         }
      }
      if (rangeColl.isEmpty()) {
         throw new NoSuchElementException();
      }
      return rangeColl;
   }


    /**
     * Returns the smallest value in the Collection coll that is greater than
     * or equal to key, as defined by the Comparator comp. The value of key
     * does not have to be in coll. If coll or comp is null, this method throws
     * an IllegalArgumentException. If coll is empty or if there is no
     * qualifying value, this method throws a NoSuchElementException. This
     * method will not change coll in any way.
     *
     * @param coll    the Collection from which the ceiling value is selected
     * @param key     the reference value
     * @param comp    the Comparator that defines the total order on T
     * @return        the ceiling value of key in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
   public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      T ceiling = Selector.max(coll, comp);
      boolean notQualify = true;
      for (T val : coll) {
         if ((comp.compare(val, key) >= 0) && (comp.compare(val, ceiling) <= 0)) {
            ceiling = val;
            notQualify = false;
         }
      }
      
      if (notQualify) {
         throw new NoSuchElementException();
      }
      return ceiling;
   }


    /**
     * Returns the largest value in the Collection coll that is less than
     * or equal to key, as defined by the Comparator comp. The value of key
     * does not have to be in coll. If coll or comp is null, this method throws
     * an IllegalArgumentException. If coll is empty or if there is no
     * qualifying value, this method throws a NoSuchElementException. This
     * method will not change coll in any way.
     *
     * @param coll    the Collection from which the floor value is selected
     * @param key     the reference value
     * @param comp    the Comparator that defines the total order on T
     * @return        the floor value of key in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
   public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      T floor = Selector.min(coll, comp);
      boolean notQualify = true;
      for (T val : coll) {
         if ((comp.compare(val, key) <= 0) && (comp.compare(val, floor) >= 0)) {
            floor = val;
            notQualify = false;
         }
      }
      
      if (notQualify) {
         throw new NoSuchElementException();
      }
      return floor;
   }

}
