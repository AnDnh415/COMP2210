import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author AN DINH (abd0060@auburn.edu)
 *
 */
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the list. */
   Node front;
   Node rear;

   /** The number of nodes in the list. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
    * Instantiates an empty LinkedSet.
    */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this LinkedSet.
    *
    * @return a string representation of this LinkedSet
    */
   @Override
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


   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements, false otherwise.
    */
   public boolean isEmpty() {
      return (size == 0);
   }


   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
      Node n = new Node (element);
      Node f = front;
      
      if (isEmpty()){
         front = new Node (element);
         rear = front;
         size++;
         return true;
      }
      
      while (f != null){
         if (f.element.equals(element)){
            return false;
         }
         else if (element.compareTo(f.element) < 0){
            //adds to the front
            if (n == front){
               n.next = front;
               front.prev = n;
               front = front.prev;
               size++;
               return true;
            }
            //adding to middle
            else{
               n.prev = f.prev;
               n.next = f;
               f.prev.next = n;
               f.prev = n;
               size++;
               return true;
            }
         }
         f = f.next;
      }
      
      //adding to end if no dupes
      rear.next = n;
      n.prev = rear;
      rear = rear.next;
      size++;
      return true;
   }
   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
      Node n = front;
      while (n != null) {
         if (n.element.equals(element)) {
            if (size == 1) {
               front = null;
               rear = null;
               size--;
               return true;
            }
            if (n == front) {
               front = front.next;
               front.prev = null;
               size--;
               return true;
            }
            else if (n == rear) {
               rear = rear.prev;
               rear.next = null;
               size--;
               return true;
            }
            else {
               n.prev.next = n.next;
               n.next.prev = n.prev;
               size--;
               return true;
            }
         }
         n = n.next;
      }
      return false;
   }


   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   public boolean contains(T element) {
      if (front == null || isEmpty()) {
         return false;
      }
      Node n = front;
      while (n != null) {
         if (n.element.equals(element)) {
            return true;
         }
         n = n.next;
      }
      return false;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(Set<T> s) {
      if (s.size() != this.size()) {
         return false;
      }
      for (T item : s) {
         if (this.contains(item) == false) {
            return false;
         }
      }
      return true;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(LinkedSet<T> s) {
      if (s.size() != this.size()) {
         return false;
      }
      Node n1 = this.front;
      Node n2 = s.front;
      
      Iterator itr1 = this.iterator();
      Iterator itr2 = s.iterator();
      while (itr1.hasNext()) {
         if (itr1.next() != itr2.next()) {
            return false;
         }
      }
      return true;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(Set<T> s){
      Set<T> union = new LinkedSet<T>();
      for (T value : this) {
         union.add(value);
      }
      for (T value : s) {
         union.add(value);
      }
      return union;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(LinkedSet<T> s){
      Set<T> union = new LinkedSet<T>();
      Node n1 = this.front;
      Node n2 = s.front;
      
      while (n1 != null || n2 != null) {
         if (n1 != null) {
            union.add(n1.element);
            n1 = n1.next;
         }
         if (n2 != null) {
            union.add(n2.element);
            n2 = n2.next;
         }
      }
      return union;
   }


   /**
    * Returns a set that is the intersection of this set and the parameter set.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      Set<T> intersection = new LinkedSet<T>();
      for (T item : this) {
         if (s.contains(item)) {
            intersection.add(item);
         }
      }
      return intersection;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(LinkedSet<T> s) {
      Set<T> intersection = new LinkedSet<T>();
      Node n1 = this.front;
      Node n2 = s.front;
      while (n1 != null && n2 != null) {
         if (n1.element.compareTo(n2.element) == 0) {
            intersection.add(n1.element);
            n1 = n1.next;
            n2 = n2.next;
         }
         else if (n1.element.compareTo(n2.element) > 0) {
            n2 = n2.next;
         }
         else {
            n1 = n1.next;
         }
      }
      return intersection;
   }


   /**
    * Returns a set that is the complement of this set and the parameter set.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      Set<T> complement = new LinkedSet<T>();
      for (T item : this) {
         if (!s.contains(item)) {
            complement.add(item);
         }
      }
      return complement;
   }


   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
      Set<T> complement = new LinkedSet<T>();
      Node n1 = this.front;
      Node n2 = s.front;
      while (n1 != null && n2 != null) {
         if (n1.element.compareTo(n2.element) < 0) {
            complement.add(n1.element);
            n1 = n1.next;
            continue;
         }
         else if (n1.element.compareTo(n2.element) > 0) {
            n2 = n2.next;
            continue;
         }
         else {
            n1 = n1.next;
            continue;
         }
      }
      
      while (n1 != null) {
         complement.add(n1.element);
         n1 = n1.next;
      }
      return complement;
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in ascending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> iterator() {
      if (this.isEmpty()) {
         Iterator<T> empty = 
            new Iterator<T>() {
               public boolean hasNext() {
                  return (false);
               }
               public T next() {
                  throw new NoSuchElementException();
               }
            };
         return empty;
      }
      Iterator<T> standard = 
         new Iterator<T>() {
            Node current = front;
            public boolean hasNext() {
               return (current != null);
            }
            public T next() {
               T value = current.element;
               current = current.next;
               return value;
            }
         };
      return standard;
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      if (this.isEmpty()) {
         Iterator<T> empty = 
            new Iterator<T>() {
               public boolean hasNext() {
                  return (false);
               }
               public T next() {
                  throw new NoSuchElementException();
               }
            };
         return empty;
      }
      Iterator<T> inverseStandard = 
         new Iterator<T>() {
            Node current = rear;
            public boolean hasNext() {
               return (current != null);
            }
            public T next() {
               T value = current.element;
               current = current.prev;
               return value;
            }
         };
      return inverseStandard;
   }


   /**
    * Returns an iterator over the members of the power set
    * of this LinkedSet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      Iterator<Set<T>> powerItr = 
         new Iterator<Set<T>>() {
            int N = size();
            int S = (int) Math.pow(2, size);
            int current = 0;
         
            public boolean hasNext() {
               return (current < S);
            }
            public Set<T> next() {
               LinkedSet<T> setItr = new LinkedSet<T>();
               char[] bitString = Integer.toBinaryString(current).toCharArray();
               for (int i = bitString.length - 1; i >= 0; i--) {
                  Node n = rear;
                  if ((int) bitString[i] == (49)) {
                     int j = bitString.length - 1;
                     while (j > i) {
                        n = n.prev;
                        j--;
                     }
                     setItr.add(n.element);
                  }
               }
               System.out.println("");
            
               current++;
               return setItr;
            }
         };
      return powerItr;
   }



   //////////////////////////////
   // Private utility methods. //
   //////////////////////////////

   // Feel free to add as many private methods as you need.

   ////////////////////
   // Nested classes //
   ////////////////////

   //////////////////////////////////////////////
   // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
   //////////////////////////////////////////////

   /**
    * Defines a node class for a doubly-linked list.
    */
   class Node {
      /** the value stored in this node. */
      T element;
      /** a reference to the node after this node. */
      Node next;
      /** a reference to the node before this node. */
      Node prev;
   
      /**
       * Instantiate an empty node.
       */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }
   
      /**
       * Instantiate a node that containts element
       * and with no node before or after it.
       */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }

}