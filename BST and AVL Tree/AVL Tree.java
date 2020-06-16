/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
//
// Title:           BST and Balanced Search Tree AVL
// Author:          rshen27@wisc.edu
// Lecturer's Name: Andrew L KUEMMEL
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * This is the AVL tree class that extends BST<K,V> class.
 * This class will do AVL rotations.
 * It is also a BST search tree that maintains its balance using AVL rotations.
 * @author Shen
 */
public class AVL<K extends Comparable<K>,V> extends BST<K, V> {
	
	protected BSTNode<K,V> root;
	protected int numKeys; // number of keys in BST

	//  constructor
	public AVL() { 
		root = null;
		
	}
	
	/**
	 * This method do setting the balance factor of the AVl tree
	 * @param root
	 */
	public void SetBF(BSTNode<K,V> root) {
		//BF = height of right sub tree minus height of left sub tree
		root.balanceFactor = height(root.right)-height(root.left);
	}
	/**
	 * This is the method that counts the height of AVL tree
	 * @param root
	 * @return
	 */
	public int height(BSTNode<K,V> root) {
    if (root == null) {
        return 0;
    }
    int lefth = height(root.left);
    int righth = height(root.right);

    if (lefth > righth) {
        return lefth + 1;
    } else {
        return righth + 1;
    }
	}
	
    /**
     * This method do RightRotation of AVL tree
     * @param current current node
     */
	public BSTNode<K,V> RightRotate(BSTNode<K,V> current)  
    {  
		BSTNode<K,V> left = current.left;  
		BSTNode<K,V> right= left.right;  
        
		//Rotate
        left.right = current;  
        current.left = right;   
        current.height = max(height(current.left), height(current.right)) + 1;  
        left.height = max(height(left.left), height(left.right)) + 1;  
  
        return left;  
    }  
  
  

	 /**
     * This method do LeftRotation of AVL tree
     * @param current current node
     */
	public BSTNode<K,V> LeftRotate(BSTNode<K,V> current)  
    {  
		BSTNode<K,V> left = current.right;  
		BSTNode<K,V> right= left.left;  
        
		//Rotate
        left.left = current;  
        current.right = right;   
        current.height = max(height(current.right), height(current.left)) + 1;  
        left.height = max(height(left.right), height(left.left)) + 1;  
  
        return left;  
    }  
	
	public int max(int a, int b) { 
	        return (a > b) ? a : b; 
	    } 
    
    /**
     * This method do AVL insertion
     * @param node
     * @param key
     * @return
     */
    BSTNode<K,V> insert(BSTNode<K,V> node, int key)  
    {  
            return node;
    }
    BSTNode<K,V> deleteNode(BSTNode<K,V> root, int key)  
    {  
    	return root;
    }  
	/**
	 * The right rotation of the AVL tree
	 * @param node
	 */
	public BSTNode<K,V> RightLeftRotation(BSTNode<K,V> node){
		return node;
	}
	/**
	 * The right rotation of the AVL tree
	 * @param node
	 */
	public BSTNode<K,V> LedtLeftRotation(BSTNode<K,V> node){
		return node;
	}
	/**
	 * The right rotation of the AVL tree
	 * @param node
	 */
	public BSTNode<K,V> RightRightRotation(BSTNode<K,V> node){
		return node;
	}
	/**
	 * The right rotation of the AVL tree
	 * @param node
	 */
	
	
	/* you can find this page at      tinyurl.com/AK-02-20-19
	*  prints a tree diagram sideways on your screen
	*  this is meant to help you debug your rotations
	*/ 
	protected void printSideways() {
		System.out.println("------------------------------------------");
		recursivePrintSideways(root, "");
		System.out.println("------------------------------------------");
	}

	// private recursive helper method for printSideways above
	protected void recursivePrintSideways(BSTNode<K,V> current, String indent) {
	if (current != null) {
			recursivePrintSideways(current.right, indent + "    ");
			System.out.println(indent + current.key + "," + current.height ); 
			recursivePrintSideways(current.left, indent + "    ");
		}
	}




}
