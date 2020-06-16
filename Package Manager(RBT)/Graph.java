/////////////////////////////// 80 COLUMNS WIDE /////////////////////////////// 
//
// Title:           Package Manager
// Author:          rshen27@wisc.edu
// Lecturer's Name: Andy Kuemmel
//
/////////////////////////////// 80 COLUMNS WIDE /////////////////////////////// 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 
 * The Graph class implement the directed and unweighed graph.
 * It also perform many operations of the graph.
 * 
 * @author shen
 */
public class Graph implements GraphADT {
	
	
	private HashMap<String,HashSet<String>> myMap;
	private int numVertices;
	private int numEdges;
	

	/**
	 * The default no-argument constructor
	 */ 
	public Graph() {
		this.myMap = new HashMap<>();
		numVertices = 0;
		numEdges = 0;
	}

	/**
	 * The private helper method that determines
	 * if the vertex is contained in the graph or 
	 * not.
	 * 
	 * @param vertex the vertex to determine
	 * @Return true  if the graph contains the vertex
	 *         false if the graph not 
	 */
	private boolean hasVertex(String vertex) {
		return myMap.containsKey(vertex);
	}
	
     /**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     * 
     * @param vertex the vertex to be added
     */
     public void addVertex(String vertex) {
	     if(vertex == null || hasVertex(vertex) == true)
		return;
	     else {
		this.myMap.put(vertex, new HashSet<>());
		this.numVertices++;
	     }
     }

     /**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     *  
     * @param vertex the vertex to be removed
     */
     public void removeVertex(String vertex) {
	     if(vertex == null || !hasVertex(vertex))
		return;
	     else {
		this.myMap.remove(vertex);
		//remove self-edge vertex
		for(Map.Entry<String,HashSet<String>> entry : myMap.entrySet()) {
			if(entry.getValue().contains(vertex)) 					
				myMap.get(entry.getKey()).remove(vertex);
		}
		this.numVertices--;
	}		
     }

     /**
     * Add the edge from vertex1 to vertex2
     * to this graph. (edge is directed and unweighed)
     * If either vertex does not exist, 
     * add the non-existing vertex to the graph and then create an edge.
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. the edge is not in the graph
     *  
     * @param vertex1 the first vertex (source)
     * @param vertex2 the second vertex (destination)
     */
	public void addEdge(String vertex1, String vertex2) {
		//return if Vertices are null key
		if(vertex1 == null || vertex2 == null)
			return;
		//if the Vertices are not null
		else{
			//add vertex1 if the map not contains it
			if(!myMap.containsKey(vertex1)) 
				addVertex(vertex1);
			//add vertex2 if the map not contains it
			if(!myMap.containsKey(vertex2))
				addVertex(vertex2);
		    //add the edge if the edge not exists
			if(!myMap.get(vertex1).contains(vertex2)) {
				myMap.get(vertex1).add(vertex2);
				this.numEdges++;
			}
			//return if edge exists
			else
				return;
		}
	}
	
	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighed)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */	
     public void removeEdge(String vertex1, String vertex2) {
	     //return if Vertices are null key
	     if(vertex1 == null || vertex2 == null)
		return;
	     //if the map contains vertex1
	     else if(myMap.containsKey(vertex1)) {
		//return if the map not contains vertex2
		if(!myMap.containsKey(vertex2))
			return;
		//if contains vertex2
		else{
			//remove the edge if the edge exists
			if(myMap.get(vertex1).contains(vertex2)) {
				myMap.get(vertex1).remove(vertex2);
			    this.numEdges--;
			}
			//return if edge not exists
			else
				return;
		}
	     }
	     //return if not contains vertex1
	     else
		return;
     }

     /**
     * Returns a Set that contains all the vertices
     * 
     * @return a Set<String> which contains all the vertices in the graph
     */
     public Set<String> getAllVertices() {
	     Set<String> set = new HashSet<String>(myMap.keySet());
	     return set;
     }

     /**
     * Get all the neighbor (adjacent-dependencies) of a vertex
     * 
     * 4/9 Clarification of getAdjacentVerticesOf method: 
     * For the example graph, A->[B, C], D->[A, B] 
     *     getAdjacentVerticesOf(A) should return [B, C]. 
     * 
     * In terms of packages, this list contains the immediate 
     * dependencies of A and depending on your graph structure, 
     * this could be either the predecessors or successors of A.
     * 
     * @param vertex the specified vertex
     * @return an List<String> of all the adjacent vertices for specified vertex
     */
     public List<String> getAdjacentVerticesOf(String vertex) {
	     List<String> list = new ArrayList<String>(myMap.get(vertex));
	     return list;
     }
	
     /**
     * Returns the number of edges in this graph.
     * @return number of edges in the graph.
     */
     public int size() {
	     return this.numEdges;
     }


    /**
     * Returns the number of vertices in this graph.
     * @return number of vertices in graph.
     */
     public int order() {
	     return this.numVertices;
     }
}
