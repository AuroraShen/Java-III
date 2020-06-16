/////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           p4 - PacjageManager
// Filename:        GraphTest.java
// Course:          Comp Sci 400
// Lecture:         004
// Due date:        4/19/2019
// Author:          Ruoxi Shen
// Email:           rshen27@wisc.edu
// Lecturer's Name: Andrew L KUEMMEL
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Persons:         N/A
// Online Sources:  N/A
// Known bugs:      N/A
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The test class is to test main operations
 * of the graph class
 * 
 * @author Shen
 *
 */
public class GraphTest {
	Graph test;
	String v1 = new String("V1");
	String v2 = new String("V2");
	String v3 = new String("V3");
	String v4 = new String("V4");

	/**
	 * Junit test setup
	 */
	@Before
	public void setUp() throws Exception {
		this.test = new Graph();
	}

	/**
	 * Junit test ends
	 */
	@After
	public void endUp() throws Exception {
		this.test = null;
	}

	/**
	 * Test addVertex method and adding operation
	 * of the graph class
	 */
	@Test
    public void test001_addVertices() {
		test.addVertex(v1);
		test.addVertex(v2);
		test.addVertex(v3);
		test.addVertex(v4);
		if(test.order() != 4) {
			fail("Incorrect adding Vertices!");
		}
	}

	/**
	 * Test removeVertex method and removing operation
	 * of the graph class
	 */
	@Test
	public void test002_removeVertices() {
		test.addVertex(v1);
		test.addVertex(v2);
		test.addVertex(v3);
		test.addVertex(v4);
		test.removeVertex(v1);
		test.removeVertex(v2);
		if(test.order() != 2) {
			fail("Incorrect removing Vertices!");
			Set<String> set = test.getAllVertices();
			if(set.contains(v1) || set.contains(v2)) {
			fail("Incorrect removing Vertices!");
			}
		}
	}
	
	/**
	 * Test adding and removing operation
	 * of the Null vertexes
	 */
	@Test
	public void test003_NullVertices() {
		test.addVertex(null);
		if(test.order()!=0) {
			fail("It added the Null Vertices");
		}
		test.addVertex(v1);
		test.removeVertex(null);
		if (test.order() != 1) {
		     fail("It remove something when instruct to remove a null vertex");
		}
	}
	
	/**
	 * Test addEdge method and adding operation
	 * of Edge
	 */
	@Test
	public void test004_addEdges() {
		test.addVertex(v1);
		test.addVertex(v2);
		test.addEdge(v1,v2);
		if(test.size()!=1) {
			fail("Incorrect adding Edges!");
		}
	}
	
	/**
	 * Test removeEdge method and removing operation
	 * of Edge
	 */
	@Test
	public void test005_removeEdges() {
		test.addVertex(v2);
		test.addVertex(v3);
		test.addEdge(v2,v3);
		test.removeEdge(v2,v3);
		if(test.size() != 0) {
			fail("Incorrect removing Edges!");
		}
	}
	
	/**
	 * Test removeEdge method and removing a non exist operation
	 * of Edge
	 */
	@Test
	public void test006_removeNonExistEdges() {
		test.addVertex(v2);
		test.addVertex(v3);
		test.addEdge(v2,v3);
		test.removeEdge(v2,v1);
		if(test.size() != 1) {
			fail("Incorrect removing Non-exist Edges!");
		}
	}
}
