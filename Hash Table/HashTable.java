//////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
//
// Title:           Hash Table
// Author:          rshen27@wisc.edu
// Lecturer's Name: Andrew L KUEMMEL
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;

/**
 * This HashTable class implement the HashTableADT interface and the 
 * DataStructureADT interface. In this class, many operations and useful
 * menthods are implemented.
 * @author Shen
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	
	/**
	 * This is a inner class that define in the HashTable class to stroing
	 * key and value as a hash node in the data structure.
	 */
	private class HashNode{
		private K  key;             // An instance field to store the key
		private V value;            // An instance field to store the value
		private HashNode next;      // Reference to the next node
		
		/**
    	 	* This is a private constructor of the HashNode inner class.
    	 	* 
    		 * @param K where is the type for key
    	 	* @param V where is the type for value
    	 	*/
        	private HashNode(K key, V value) {
        		this.key = key ;
        		this.value = value;
		}
	}
	
	private ArrayList<HashNode> buckets;  //the array to store hashes 
	private int numBuckets; // table size
	private int size;     // current size of the array list
	private double LoadFactorThreshold; // The oad factor threshold of hash table
		
	/**
	 * The default no-arg constructor of the HashTable class
	 */
	public HashTable() {
		this.size = 0;
		this.numBuckets = 2019;
		this.LoadFactorThreshold = 0.75; 
		buckets = new ArrayList<HashNode>();
		for(int i = 0; i < numBuckets; i++)
        	buckets.add(null);
	}
	 
	/**
	 * The override constructor to initial capacity and load factor threshold
	 * threshold is the load factor that causes a resize and rehash
	 * @param initialCapacity  
	 * @param loadFactorThreshold 
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		this.numBuckets = initialCapacity;
		this.size = 0;
		this.LoadFactorThreshold = loadFactorThreshold; 
		
		buckets = new ArrayList<HashNode>();
		for(int i = 0; i < numBuckets; i++)
        	buckets.add(null);
	}

    /**
     * This method returns the load factor threshold that was      
     * passed into the constructor when creating 
     * the instance of the HashTable.
     * 
     * When the current load factor is greater than or 
     * equal to the specified load factor threshold,
     * the table is resized and elements are rehashed.
     * @return LoadFactorThreshold 
     */
    public double getLoadFactorThreshold() {     	
    	return this.LoadFactorThreshold;
    }

    /**
     * The method returns the current load factor for this hash table
     * load factor = number of items / current table size 
     */    
    public double getLoadFactor() {
		return (double) size/numBuckets;
    }
    
    /**
     * This method return the current Capacity (table size)
     * of the hash table array.
     * The initial capacity must be a positive integer, 1 or greater
     * and is specified in the constructor.
     * REQUIRED: When the load factor threshold is reached, 
     * the capacity must increase to: 2 * capacity + 1
     * Once increased, the capacity never decreases
     */
    public int getCapacity() {
    	return numBuckets;
    }
   

    /**
     * This method returns the collision resolution scheme I used 
     * for this hash table class.
     * Bellow are the return value and it's implementation
     * 1 OPEN ADDRESSING: linear probe
     * 2 OPEN ADDRESSING: quadratic probe
     * 3 OPEN ADDRESSING: double hashing
     * 4 CHAINED BUCKET: array of arrays 
     * 5 CHAINED BUCKET: array of linked nodes
     * 6 CHAINED BUCKET: array of search trees
     * 7 CHAINED BUCKET: linked nodes of arrays
     * 8 CHAINED BUCKET: linked nodes of linked node
     * 9 CHAINED BUCKET: linked nodes of search trees
     * @return 5 
     */
    public int getCollisionResolution() {
    	return 5;
    }
    
    /**
     * This method insert and add the key,value pair to the data structure and 
     * increase the number of keys.
     * If key is null, throw IllegalNullKeyException;
     * If key is already in data structure, throw DuplicateKeyException();
     * @param key
     * @param value
     * @throws IllegalNullKeyException
     * @throws DuplicateKeyException
     */
    public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    	if(key == null)
    		throw new IllegalNullKeyException();
    	if(containsKey(key) == true)
    		throw new DuplicateKeyException();
    	
    	int index = hash(key);  // getting hash index of the key
    	double loadfactor = getLoadFactor(); 
    	HashNode head = buckets.get(index);  // get head of the chain
    	size++;
    	
		head = buckets.get(index); 
        HashNode newNode = new HashNode(key, value); 
        newNode.next = head; 
        buckets.set(index, newNode); 
    	
    	if(loadfactor >= this.LoadFactorThreshold) { // rehash
    		ArrayList<HashNode> hi = buckets; 
    		int OldnumBuckets = numBuckets;
    		numBuckets = 2 * numBuckets + 1;
    		
            buckets = new ArrayList<>(numBuckets); 
            for (int i = 0; i < numBuckets; i++) 
                buckets.add(null); 
            size = 0;
            
            for(int j = 0; j < OldnumBuckets; j++) {
    			HashNode node = hi.get(j);
    			while (node != null) {
    				insert(node.key,node.value);
    				node = node.next;
    			}

            }
    	}
    }


    /** 
     * This method remove the indicated key. It removes the key,value pair from the 
     * data structure.
     * If key is found, it decrease number of keys and returns true
     * If key is null, throw IllegalNullKeyException
     * If key is not found, return false
     * @param key the key
     * @return true/flase/null
     * @throws IllegalNullKeyException
     */
    public boolean remove(K key) throws IllegalNullKeyException{
    	if(key == null)
    		throw new IllegalNullKeyException();
    	if(containsKey(key) == false)
    		return false;  //return false if key not found
    	
    	int index = hash(key);  // getting hash index of the key
    	if(index < 0)
    		return false;
    	HashNode head = buckets.get(index);  // get head of the chain
    		
    	HashNode previousNode = null;   //set the previous noe to null
    	while(head !=null) {
    		if(head.key.equals(key)) 
    			break;//if we find the key
    		previousNode = head;
			head = head.next;
    	}
    	size--;
    	
    	if(previousNode == null) {
    		buckets.set(index, head.next); 
    	}
    	// set next node to this index if previous node is null
    	else {
    		previousNode.next = head.next;
    	}
    	// set next node to the next node of the previous node
    				
    	return true;
    }

    /**
     *  This method returns the value associated with the specified key
     *  It does not remove key or decrease number of keys
     *  If key is null, throw IllegalNullKeyException 
     *  If key is not found, throw KeyNotFoundException().
     *  @param key
     *  @return value
     *  @throws IllegalNullKeyException
     *  @throws DuplicateKeyException
     */
    public V get(K key) throws IllegalNullKeyException, KeyNotFoundException{
    	if(key == null)
    		throw new IllegalNullKeyException();
    	int index = hash(key);  // getting hash index of the key
		if (index < 0)
			throw new KeyNotFoundException();
	
    	HashNode head = buckets.get(index);  // get head of the chain
    	    
    	while(head !=null) {
    	    if(head.key.equals(key))
    	    	return head.value;  //return value of the Hash node
    	    head = head.next;     // keep moving in the chain
    	}
    	throw new KeyNotFoundException();	   
    }

    /**
     *  This method returns the number of key,value 
     *  pairs in the data structure
     *  @return size
     */
    public int numKeys() {
    	return size;
    }
    
    /**
     * This is a private helper method that helps to check
     * if the key is contains in the array
     * @param key the key
     * @return true or false
     */
    private boolean containsKey(K key) {
    	int index = hash(key);
    	HashNode head = buckets.get(index) ;
    	
    	while(head != null) {
    		if(head.key.equals(key))
    			return true;
    		head = head.next;
    	}
    	return false ;
    }
    
    /**
     * This is a private helper method that get the index
     * of the key from the hash code 
     * @param key the key
     * @return hash the positive hash index
     */
    private int hash(K key) {
    	int hash = key.hashCode() % numBuckets;
    	return hash;
    }

	/**
	 * This is my private helper rehash method that do rehashing and resize
	 * when the current load factor is greater than or 
     * equal to the specified load factor threshold. When the load factor 
     * threshold is reached, the capacity must increase to: 2 * capacity + 1
     * Once increased, the capacity never decreases.
	 */
    private void rehash() {
    	ArrayList<HashNode> Old = buckets; // store the buckets in the Old array
    	int newnumBuckets = (buckets.size()*2)+1; //resize the capacity of the array
    	double loadfactor = getLoadFactor();  
    	this.LoadFactorThreshold =(double)(newnumBuckets*loadfactor);
    	// update the loadfactorthreshold
    	this.size = 0;
    	
    	buckets = new ArrayList<HashNode> (newnumBuckets) ;// the new buckets
    	for(int i = 0; i< newnumBuckets; i++)
    		buckets.add(null);
    	
    	// rehashing
    	for( int j = 0; j < Old.size() ; j++) {
    		HashNode head = Old.get(j);  // get the node from old buckets
    		
    		while(head != null) {
    			//insertHelper(head.key, head.value, buckets);
    			head = head.next;
		}
	}
    }
}
    
    
