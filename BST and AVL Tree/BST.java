/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
//
// Title:           BST and Balanced Search Tree AVL
// Author:          rshen27@wisc.edu
// Lecturer's Name: Andrew L KUEMMEL
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;  // allowed for creating traversal lists
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;       // required for returning List<K>
import java.util.Queue;
import java.util.Stack;

/**
 * This is the binary search tree class that do different sorting traversal methods.
 * And the class have many method that editing the binary search tree such as insert, 
 * remove, get, contains and number of keys. There are also method like get keys 
 * and height in this class.
 * @author Shen
 *
 * @param <K>
 * @param <V>
 */
public class BST<K extends Comparable<K>,V> implements BSTADT<K, V> {

	// Tip: Use protected fields so that they may be inherited by AVL
	protected BSTNode<K,V> root;
	protected int numKeys; // number of keys in BST

	// Must have a public, default no-arg constructor
	public BST() { 
		root = null;
		
	}
	
	/* *
	 * Returns the keys of the data structure in pre-order traversal order.
	 * In the case of binary search trees, the order is: V L R
	 * 
	 * If the SearchTree is empty, an empty list is returned.
	 * 
	 * @return List of Keys in-order
	 * @see SearchTreeADT#getPreOrderTraversal()
	 */
	@Override
	public  List<K> getPreOrderTraversal() {
		ArrayList<K> list = new ArrayList<K>();
		Stack<BSTNode<K,V>> nodes = new Stack<>();
		// If the SearchTree is empty, an empty list is returned.
		if (root == null) 
            return Collections.emptyList();
		
		nodes.push(root);
		while(!nodes.isEmpty()) {
			BSTNode<K,V> current = nodes.pop();
			list.add(current.key);
			
			if(current.right != null) {
				nodes.push(current.right);
			}
			
			if(current.left != null) {
				nodes.push(current.left);
			}
		}
		
		return list;
	}

     /**
	 * Returns the keys of the data structure in post-order traversal order.
	 * In the case of binary search trees, the order is: L R V 
	 * 
	 * If the SearchTree is empty, an empty list is returned.
	 * 
	 * @return List of Keys in pre-order
	 * @see SearchTreeADT#getPostOrderTraversal()
	 */
	@Override
	public List<K> getPostOrderTraversal() {
		ArrayList<K> list = new ArrayList<K>();
		Stack<BSTNode<K,V>> nodes = new Stack<>();
		// If the SearchTree is empty, an empty list is returned.
		if (root == null) 
            return Collections.emptyList();
		
		nodes.push(root);
		while (!nodes.isEmpty()) {
			BSTNode<K,V> current = nodes.peek();

		    boolean finishedSubtrees = (current.right == root || current.left == root);
		    boolean isLeaf = (current.left == null && current.right == null);
		    if (finishedSubtrees || isLeaf) {
		      nodes.pop();
		      list.add(current.key);
		      root = current;
		    }
		    else {
		      if (current.right != null) {
		         nodes.push(current.right);
		      }
		      if (current.left != null) {
		         nodes.push(current.left);
		      }
		    }
		  }
		
		return list;
	}

	/**
	 * Returns the keys of the data structure in level-order traversal order.
	 * 
	 * The root is first in the list, then the keys found in the next level down,
	 * and so on. 
	 * 
	 * If the SearchTree is empty, an empty list is returned.
	 * 
	 * @return List of Keys in level-order
	 * @see SearchTreeADT#getLevelOrderTraversal()
	 */
	@Override
	public List<K> getLevelOrderTraversal() {
		ArrayList<K> list = new ArrayList<K>();
		Queue<BSTNode<K,V>> nodes=new LinkedList<BSTNode<K,V>>();
		
		nodes.add(root);
		while(!nodes.isEmpty()) {
			BSTNode<K,V> current=nodes.poll();
			list.add(current.key);
			if(current.left != null)
			    nodes.add(current.left);
			if(current.right!=null)
			    nodes.add(current.right);
		}
		
		return list;
	}

	/**
	 * Returns the keys of the data structure in sorted order.
	 * In the case of binary search trees, the visit order is: L V R
	 * 
	 * If the SearchTree is empty, an empty list is returned.
	 * 
	 * @return List of Keys in-order
	 * @see SearchTreeADT#getInOrderTraversal()
	 */
	@Override
	public List<K> getInOrderTraversal() {
		ArrayList<K> list = new ArrayList<K>();
		Stack<BSTNode<K,V>> nodes = new Stack<>();
		// If the SearchTree is empty, an empty list is returned.
		if (root == null) 
            return Collections.emptyList();
		
		BSTNode<K,V> current = root;
		while(!nodes.isEmpty() || current != null) {
			
			if(current != null) {
				nodes.push(current);
				current = current.left;
			}
			else
			{
				current = (BSTNode<K,V>) nodes.pop();
				list.add(current.key);
				current = current.right;
			}
		}
		
		return list;
	}

	/**
	 * Add the key,value pair to the data structure and increase the number of keys.
	 * If key is null, throw IllegalNullKeyException;
	 * If key is already in data structure, throw DuplicateKeyException();
	 * @see DataStructureADT#insert(java.lang.Comparable, java.lang.Object)
	 * 
	 * @param key
	 * @param value
	 */
	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		if(key == null) {
	         throw new IllegalArgumentException("There is a null key");
		}
		if(this.contains(key) == true) {
			throw new DuplicateKeyException();
		}
		else
			root = insertHelper(root,key,value);  //use the helper method to do recursive 
	}
	
	/*
	 * This is my helper method for doing insertion
	 */
	private BSTNode<K,V> insertHelper(BSTNode<K,V> current, K key, V value) {
		if(current == null) {
			return new BSTNode<K,V>(key, value, null, null);
		}//set this key to current root directly
		if(key.compareTo(current.key) < 0) {
			current.setLeft(insertHelper(current.left, key, value));
			return current;
		}//insert to the left if is smaller
		else {
			current.setRight(insertHelper(current.right, key, value));
		return current;
		}//insert to the right if is larger
	}

	/**
	 * If key is found, remove the key,value pair from the data structure and decrease num keys.
	 * If key is null, throw IllegalNullKeyException
	 * If key is not found, throw KeyNotFoundException().
	 * @see DataStructureADT#remove(java.lang.Comparable)
	 * 
	 * @param key
	 * @param value
	 * @return true for removed
	 */
	@Override
	public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if( key == null) {
			throw new IllegalArgumentException("There is a null key");
		}
		else if(this.contains(key) == false) {
			throw new KeyNotFoundException();
		}
		else {
			root = removeHelper(root,key,get(key)); //use the helper method to do recursive
			return true; // return true if deleted the key
		}
	}
    
	/**
	 * this is my private method helper for doing remove keys.
	 * @param current
	 * @param key
	 * @return the new key that will replace the deleted key
	 */
	private BSTNode<K, V> removeHelper(BSTNode<K,V> current, K key, V value) {
		if (key.equals(current.key)) {
	        //if we find the key, then we do deletion
	        if (current.left == null && current.left == null) {
	            return null;
	        }// there will be no key replaced it
	        if (current.left== null) {
	            return current.right;
	        }// the right key will replace it
	        if (current.right == null) {
	            return current.left;
	        }// the left key will replace it
	        else {// if the key have two children
	        BSTNode<K, V> smaller = Min(current.right); // we are using successor here
	        current.setKey(smaller.key);// we are using successor here
	        current.setValue(smaller.value);
	        current.setRight(removeHelper(current.right, smaller.key, smaller.value) );
	        return current; 
	        }
	    }
		//Keep looking if we haven't find the key
	    else if (key.compareTo(current.key) < 0) {
	    	current.setLeft( removeHelper(current.left, key, value) );
	        return current;
	    }
	    else {
	    	current.setRight( removeHelper(current.right, key, value) );
	        return current;
	    }
	}
	
	/**
	 * This is the method that help with finding the predecessor
	 * @param root
	 * @return min the predecessor
	 */
    private BSTNode<K, V> Min(BSTNode<K,V> node) { 
    	BSTNode<K, V> min = node; 
        if(node.left == null)
        	return min;
        else
        	return Min(node.left);
    } 



	/**
	 * Returns the value associated with the specified key
	 * Does not remove key or decrease number of keys
	 * If key is null, throw IllegalNullKeyException 
	 * If key is not found, throw KeyNotFoundException().
	 * @see DataStructureADT#get(java.lang.Comparable)
	 */
	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null) {
	         throw new IllegalArgumentException("This is a null key");
		}
		if(this.contains(key) == false) {
			throw new KeyNotFoundException();
		}
		else
			return getHelper(root, key); //use the helper method to do recursive
	}
	
	/**
	 * This is the private helper method that help with get value of the key
	 * @param current
	 * @param key
	 * @return
	 */
	private V getHelper(BSTNode<K,V> current, K key) {
    	if(current.key.equals(key))
    		return current.value;
    	else if(key.compareTo(current.key) < 0)
    		return getHelper(current.left, key);
    	else 
    		return getHelper(current.right, key);
    		
    }
	

	/** 
	 * Returns true if the key is in the data structure
	 * If key is null, throw IllegalNullKeyException 
	 * Returns false if key is not null and is not present
	 * @see DataStructureADT#contains(java.lang.Comparable)
	 */
	@Override
	public boolean contains(K key) throws IllegalNullKeyException {
		if(key == null) {
			throw new IllegalArgumentException("This is a null key");
		}
		else {
			return containsHelper(root, key); //use the helper method to do recursive
		}
	}
	/**
	 * This is the helper method that helps with searching the binary tree that if
	 * it contains the key.
	 * @param current
	 * @param key
	 * @return
	 */
    private boolean containsHelper(BSTNode<K,V> current, K key) {
    	if(current == null)
    		return false;
    	else if(current.key.equals(key))
    		return true;
    	else if(key.compareTo(current.key) < 0) 
    		return containsHelper(current.left, key);
    	else
    		return containsHelper(current.right, key);
    }
    	
	/**
	 * Returns the number of key,value pairs in the data structure
	 * @see DataStructureADT#numKeys()
	 */
	@Override
 	public int numKeys() {
		return numKeys;
	}

	/**
	 * Returns the key that is in the root node of this BST.
	 * If root is null, returns null.
	 * @return key found at root node, or null
	 * @see BSTADT#getKeyAtRoot()
	 */
	@Override
	public K getKeyAtRoot() {
		if(root == null) {
			return null;
		}
		return root.key;
	}

	/**
	 * Tries to find a node with a key that matches the specified key.
	 * If a matching node is found, it returns the key that is in the left child.
	 * If the left child of the found node is null, returns null.
	 * 
	 * @param key A key to search for
	 * @return The key that is in the left child of the found key
	 * 
	 * @throws IllegalNullKeyException if key argument is null
	 * @throws KeyNotFoundException if key is not found in this BST
	 * @see BSTADT#getKeyOfLeftChildOf(java.lang.Comparable)
	 */
	@Override
	public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null) {
	         throw new IllegalArgumentException("This is a null key");
		}
		if(this.contains(key) == false) {
			throw new KeyNotFoundException();
		}
		else
			return getLeftHelper(root, key); //use the helper method to do recursive
	}
	
	/**
	 * This is the private helper method that help with get left child of the key
	 * @param current
	 * @param key
	 * @return
	 */
	private K getLeftHelper(BSTNode<K,V> current, K key) {
    	if(current.key.equals(key))
    		return current.left.key; // return the key of the left child
    	else if(key.compareTo(current.key) < 0)
    		return getLeftHelper(current.left, key);
    	else 
    		return getLeftHelper(current.right, key);
    		
    }

	/**
	 * Tries to find a node with a key that matches the specified key.
	 * If a matching node is found, it returns the returns the key that is in the right child.
	 * If the right child of the found node is null, returns null.
	 * 
	 * @param key A key to search for
	 * @return The key that is in the right child of the found key
	 * 
	 * @throws IllegalNullKeyException if key is null
	 * @throws KeyNotFoundException if key is not found in this BST
	 * @see BSTADT#getKeyOfRightChildOf(java.lang.Comparable)
	 */
	@Override
	public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null) {
	         throw new IllegalArgumentException("This is a null key");
		}
		if(this.contains(key) == false) {
			throw new KeyNotFoundException();
		}
		else
			return getRightHelper(root, key); //use the helper method to do recursive
	}
	
	/**
	 * This is the private helper method that help with get right child of the key
	 * @param current
	 * @param key
	 * @return
	 */
	private K getRightHelper(BSTNode<K,V> current, K key) {
   	if(current.key.equals(key))
   		return current.right.key; // return the key of the left child
   	else if(key.compareTo(current.key) < 0)
   		return getRightHelper(current.left, key);
   	else 
   		return getRightHelper(current.right, key);
   		
   }

	/**
	 * Returns the height of this BST.
	 * H is defined as the number of levels in the tree.
	 * 
	 * If root is null, return 0
	 * If root is a leaf, return 1
	 * Else return 1 + max( height(root.left), height(root.right) )
	 * 
	 * Examples:
	 * A BST with no keys, has a height of zero (0).
	 * A BST with one key, has a height of one (1).
	 * A BST with two keys, has a height of two (2).
	 * A BST with three keys, can be balanced with a height of two(2)
	 *                        or it may be linear with a height of three (3)
	 * ... and so on for tree with other heights
	 * 
	 * @return the number of levels that contain keys in this BINARY SEARCH TREE
	 * @see BSTADT#getHeight()
	 */
	@Override
	public int getHeight() {
		if(root == null) {
			return 0;
		}
		else if(root.left == null && root.right == null) {
			return 1;
		}
		else{
			return getHeightHelper(root);
		}
	}
	
	/**
	 * This is a helper method to get height of the tree
	 * @param root
	 * @return the height of the tree
	 */
	private int getHeightHelper(BSTNode<K,V> root){
		return (1+ Math.max(getHeightHelper(root.left),getHeightHelper(root.right)));
	}
}




