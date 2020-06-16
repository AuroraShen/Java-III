/**
 * Filename:   MyProfiler.java   
 * Authors:    rshen27@wisc.edu
 */

// Used as the data structure to test our hash table against
import java.util.TreeMap;

/**
 * The MyProfiler class is to analyze the performance of the hash table 
 * against Java's built-in TreeMap. In this class, each method performs
 * the same operations on both HashTable class and Java's TreeMap class.
 * @author shen
 *
 * @param <K>
 * @param <V>
 */
public class MyProfiler<K extends Comparable<K>, V> {

    HashTableADT<K, V> hashtable;
    TreeMap<K, V> treemap;
    
    
    /**
     * The Profile constructor, it instantiate 
     * the HashTable and Java's TreeMap 
     */
    public MyProfiler() {
    	hashtable = new HashTable<K,V>();
    	treemap = new TreeMap<K,V>();
        
    }
    
    /**
     * The insert method inserts K, V into both data structures
     * @param key
     * @param value
     */
    public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
        treemap.put(key, value);
        try {
        	hashtable.insert(key, value);
        }
        catch(IllegalNullKeyException e) {
        	System.out.println(e);
        }
        catch(DuplicateKeyException e) {
        	System.out.println(e);
        }
        		
    }
    
    
    /**
     * The retrieve method get value V for key K 
     * from the data structures. This method operates the
     * same performance for the both data structures.
     * @param key
     */
    public void retrieve(K key) throws IllegalNullKeyException, KeyNotFoundException{
    	treemap.get(key);
    	try {
    		hashtable.get(key);
    	}
        catch(IllegalNullKeyException e) {
        	System.out.println(e);
        }
        catch(KeyNotFoundException e) {
        	System.out.println(e);
        }
    }
    
    
    /**
     * The main method is performancing the same operations for the
     * both data structures. 
     * @param args
     */
    public static void main(String[] args) {
        try {
            int numElements = Integer.parseInt(args[0]);     // Command line arg input
            // Create a profile object.
            MyProfiler<Integer, Integer> mp = new MyProfiler<Integer, Integer>();  

            // execute the insert method of profile as many times as numElements
            for (Integer i = 0; i < numElements; i++) {
                mp.insert(i,i); 
            }
            
            // execute the retrieve method of profile as many times as numElements
            for (Integer i = 0; i < numElements; i++) {
                mp.retrieve(i); 
            }
            
            String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
            System.out.println(msg);
        }
        catch (Exception e) {
            System.out.println("Usage: java MyProfiler <number_of_elements>");
            System.exit(1);
        }
    }
}
