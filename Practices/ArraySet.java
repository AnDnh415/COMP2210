import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides a scaled down implementation of the ArraySet class
 * from A4. The add method is provided for you and must not be
 * changed. 
 * 
 * You are to implement the equals method to get credit
 * for this exam question.
 *
 * Only the following methods are available to use:
 *
 *    - add
 *    - contains
 *    - size
 *    - isEmpty
 *    - toString
 *    - iterator
 * 
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 2017-10-31
 *
 */
public class ArraySet<T extends Comparable<? super T>> implements Set<T> {

   /////////////////////////////////////////////////////
   // YOU MUST COMPLETE THE BODY OF THE EQUALS METHOD //
   /////////////////////////////////////////////////////

   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    */
   @Override
   public boolean equals(Set<T> s) {
      if (size != s.size()) {
         return false;
      }
      for (T val : this) {
         if (!s.contains(this)) {
            return false;
         }
      }
      return true;
   }


   /***************************************************************************/

   ////////////////////////////////////////////////////////////////////////////
   // EVERYTHING FROM HERE DOWN IS PROVIDED FOR YOU AND MUST NOT BE CHANGED  //
   ////////////////////////////////////////////////////////////////////////////

   T[] elements;
   int size;

   /**
    * Instantiates an empty set.
    */
   @SuppressWarnings("unchecked")
   public ArraySet() {
      elements = (T[]) new Comparable[1];
      size = 0;
   }

   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   @Override
   public int size() {
      return size;
   }

   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements,
    *               false otherwise.
    */
   @Override
   public boolean isEmpty() {
      return size == 0;
   }

   /**
    * Return a string representation of this ArraySet.
    *
    * @return a string representation of this ArraySet
    */
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }

   /**
    * Ensures the collection contains the specified element. Elements are
    * maintained in ascending natural order at all times. Neither duplicate nor
    * null values are allowed.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   @Override
   public boolean add(T element) {
      if (element == null) {
         return false;
      }
   
      if (isEmpty()) {
         elements[size++] = element;
         return true;
      }
   
      int i = locate(element);
      int cmp = elements[i].compareTo(element);
   
      // don't insert a duplicate
      if (cmp == 0) {
         return false;
      }
   
      if (isFull()) {
         resize(elements.length * 2);
      }
   
      // make room at i or i+1
      int loc = (cmp > 0 ? i : i + 1);
      shiftRight(loc);
   
      elements[loc] = element;
      size++;
      return true;
   }

   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection
    *                   is to be tested.
    * @return  true if this collection contains the specified element,
    *               false otherwise.
    */
   @Override
   public boolean contains(T element) {
      if (element == null) {
         return false;
      }
   
      if (isEmpty()) {
         return false;
      }
   
      int i = locate(element);
      return elements[i].compareTo(element) == 0;
   }


   /**
    * Returns an iterator over the elements in this ArraySet.
    * No specific order can be assumed.
    *
    * @return  an iterator over the elements in this ArraySet
    */
   @Override
   public Iterator<T> iterator() {
      return new ArraySetIterator();
   }




   /////////////////////////////////
   // Private methods and classes //
   /////////////////////////////////

   /**
    * Returns true if this ArrayBag has no elements,
    * false otherwise.
    */
   private boolean isFull() {
      return size == elements.length;
   }

   /**
    * Returns true if this ArrayBag is less than 25% full.
    */
   private boolean isSparse() {
      return (size > 0) && (size < elements.length / 4);
   }

   /**
    * Resizes the internal array to the specified capacity.
    */
   @SuppressWarnings("unchecked")
   private void resize(int capacity) {
      T[] a = (T[]) new Comparable[capacity];
      System.arraycopy(elements, 0, a, 0, size);
      elements = a;
   }

   /**
    * Creates an ArraySet copy of the given Set.
    */
   private ArraySet<T> copyOf(Set<T> s) {
      ArraySet<T> result = new ArraySet<T>();
      for (T e : s) {
         result.add(e);
      }
      return result;
   }

   /**
    * Returns the index of element in this ArrayBag's
    * elements array. If element is not present, this
    * method returns the index of where it would be.
    * If this ArrayBag is empty, this method returns -1.
    *
    * @param  element the value to be searched for
    * @return         actual or potential location of element
    */
   private int locate(T element) {
      int l = 0;
      int r = size - 1;
      int m = -1;
      while (l <= r) {
         m = l + (r - l) / 2;
         int cmp = elements[m].compareTo(element);
         if (cmp == 0) {
            return m;
         }
         if (cmp < 0) {
            l = m + 1;
         }
         else {
            r = m - 1;
         }
      }
      return m;
   }

   /**
    * Shifts elements[loc] through elements[size - 1] to the right
    * by one position.
    */
   private void shiftRight(int loc) {
      assert size < elements.length;
      for (int i = size; i > loc; i--) {
         elements[i] = elements[i - 1];
      }
      elements[loc] = null;
   }

   /**
    * Shifts elements[loc] through elements[size - 1] to the left
    * by one position.
    */
   private void shiftLeft(int loc) {
      for (int i = loc; i < size; i++) {
         elements[i - 1] = elements[i];
      }
      elements[size - 1] = null;
   }

   /**
    * Provides iteration behavior over this ArrayBag's
    * elements array. The values are returned in the
    * order in which they appear in the array, from
    * index 0 to index size - 1. This results in
    * ascending natural order.
    */
   private class ArraySetIterator implements Iterator<T> {
      int current = 0;
   
      public boolean hasNext() {
         return current < size;
      }
   
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         return elements[current++];
      }
   
      public void remove() {
      
      }
   }

}
