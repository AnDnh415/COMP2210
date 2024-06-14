/**
 * Complete the min method in the nested BinarySearchTree class below.
 *
 */
public class BSTMin {

   /** Provides an example. */
   public static void main(String[] args) {
      BinarySearchTree<Integer> iBst = new BinarySearchTree<>();
      iBst.add(10);
      iBst.add(12);
      iBst.add(8);
      iBst.add(2);
      iBst.add(6);
      iBst.add(4);
      Integer imin = iBst.min();
      // The following statement should print 2.
      System.out.println(imin);
   
      BinarySearchTree<String> sBst = new BinarySearchTree<>();
      sBst.add("W");
      sBst.add("A");
      sBst.add("R");
      sBst.add("E");
      sBst.add("A");
      sBst.add("G");
      sBst.add("L");
      sBst.add("E");
      String smin = sBst.min();
      // The following statement should print A.
      System.out.println(smin);
   }



   /** Defines a binary search tree. */
   static class BinarySearchTree<T extends Comparable<T>> {
   
      // the root of this binary search tree
      private Node root;
   
      // the number of nodes in this binary search tree
      private int size;
   
      /** Defines the node structure for this binary search tree. */
      private class Node {
         T element;
         Node left;
         Node right;
      
         /** Constructs a node containing the given element. */
         public Node(T elem) {
            element = elem;
            left = null;
            right = null;
         }
      }
   
   
      /* >>>>>>>>>>>>>>>>>> YOUR WORK STARTS HERE <<<<<<<<<<<<<<<< */
   
   
      ///////////////////////////////////////////////////////////////////////////////
      //       I M P L E M E N T   T H E   M I N   M E T H O D   B E L O W         //
      ///////////////////////////////////////////////////////////////////////////////
   
      /**
       * Returns the minimum value in the binary search tree.
       */
      public T min() {
         Node current = this.root;
         while (current.left != null) {
            current = current.left;
         }
         return current.element;
      }
   
   
      /* >>>>>>>>>>>>>>>>>> YOUR WORK ENDS HERE <<<<<<<<<<<<<<<< */
   
   
   
      ////////////////////////////////////////////////////////////////////
      //  D O   N O T   M O D I F Y   B E L O W   T H I S   P O I N T   //
      ////////////////////////////////////////////////////////////////////
   
   
   
      ////////////////////
      // M E T R I C S  //
      ////////////////////
   
      /**
       * Returns the number of elements in this bst.
       */
      public int size() {
         return size;
      }
   
      /**
       * Returns true if this bst is empty, false otherwise.
       */
      public boolean isEmpty() {
         return size == 0;
      }
   
      /**
       * Returns the height of this bst.
       */
      public int height() {
         return height(root);
      }
   
      /**
       * Returns the height of node n in this bst.
       */
      private int height(Node n) {
         if (n == null) {
            return 0;
         }
         int leftHeight = height(n.left);
         int rightHeight = height(n.right);
         return 1 + Math.max(leftHeight, rightHeight);
      }
   
   
      ////////////////////////////////////
      // A D D I N G   E L E M E N T S  //
      ////////////////////////////////////
   
      /**
       * Ensures this bst contains the specified element. Uses an iterative implementation.
       */
      public void add(T element) {
         // special case if empty
         if (root == null) {
            root = new Node(element);
            size++;
            return;
         }
      
         // find where this element should be in the tree
         Node n = root;
         Node parent = null;
         int cmp = 0;
         while (n != null) {
            parent = n;
            cmp = element.compareTo(parent.element);
            if (cmp == 0) {
               // don't add a duplicate
               return;
            } else if (cmp < 0) {
               n = n.left;
            } else {
               n = n.right;
            }
         }
      
         // add element to the appropriate empty subtree of parent
         if (cmp < 0) {
            parent.left = new Node(element);
         } else {
            parent.right = new Node(element);
         }
         size++;
      }
   
   }

}
