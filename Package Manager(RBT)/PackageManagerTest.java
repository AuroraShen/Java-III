//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           p4 - PacjageManager
// Filename:        PackageManagerTest.java
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

import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.json.simple.parser.ParseException;

/**
 * The test tests every method of package manager class.
 * @author Shen 
 * 
 */
class PackageManagerTest {
	PackageManager me;

	/**
	 * Junit test setup
	 * @throws Exception the exception
	 */
    @Before
    public void setUp() throws Exception {
        me = new PackageManager();
    }

    /**
     * Junit test ends up 
     *
     * @throws Exception the exception
     */

    @After
    public void tearDown() throws Exception {
        me = null;
    }
    
    
    /**
     * This test tests the construct graph method
     * in the package manager class
     */
	@Test
	public void test001_constructTest() {
		try {
			me.constructGraph("valid.json");
			Set<String> good = new HashSet<String>();
			good.add("A");
			good.add("B");
			good.add("C");
			good.add("D");
			good.add("E");
			
			Set<String> maybeGood = me.getAllPackages();
			for(String i : good) {
				if(maybeGood.contains(i) == false)
					fail("PackageManager construct error occured");
			}
		}catch (FileNotFoundException e) {
    		e.printStackTrace();
    		System.out.println("Error: File cannot be found");
    	  } catch (IOException e) {
    		e.printStackTrace();
    		System.out.println("Error: IOExcepion");
    	  } catch (ParseException e) {
    		e.printStackTrace();
    		System.out.println("Error: Cannot parse file");
	}
	}
	
	/**
     * This test tests the getAllPackages method
     * in the package manager class
     */
	@Test
	public void test002_getAllPackages() {
		 me.constructGraph("valid.json");
		 Set<String> good = new HashSet<String>();
		 good.add("A");
		 good.add("B");
		 good.add("C");
		 good.add("D");
		 good.add("E");
		
		Set<String> maybeGood = me.getAllPackages();
		for(String i : good) {
			if(maybeGood.contains(i) == false)
				fail("PackageManager getAllPackages error");
		}
	
	}

		
		/**
	     * This test tests the getInstallationOrder method
	     * in the package manager class
	     */
		@Test
		public void test003_getInstallationOrder() {
			try {
				me.constructGraph("valid.json");
				List<String> good;
				good.add("C");
				good.add("D");
				good.add("B");
				good.add("A");
				
				List<String> maybeGood = me.getInstallationOrder("A");
				for(String i : good) {
					if(maybeGood.contains(i) == false)
						fail("PackageManager getInstallationOrder error");
				}
			}catch (FileNotFoundException e) {
	    		e.printStackTrace();
	    		System.out.println("Error: File cannot be found");
			}catch (CycleException e) {
	        	    e.printStackTrace();
	        	    System.out.println("Error: Encounter a cycle in the graph ");
			}
		}


		
		/**
	     * This test tests the toInstall method
	     * in the package manager class
	     */
		@Test
		public void test004_toInstall() {
			try {
				me.constructGraph("valid.json");
				Set<String> good = new HashSet<String>();
				good.add("A");
				
				List<String> maybeGood = me.toInstall("A","B");
				for(String i : good) {
					if(maybeGood.contains(i) == false)
						fail("PackageManager toInstall error");
				}
			
		}catch (FileNotFoundException e) {
    		e.printStackTrace();
    		System.out.println("Error: File cannot be found");
		}catch (CycleException e) {
        	    e.printStackTrace();
        	    System.out.println("Error: Encounter a cycle in the graph ");
		}
		}
	
		/**
	     * This test tests the getInstallationOrderForAllPackages method
	     * in the package manager class
	     */
		@Test
		public void test005_getInstallationOrderForAllPackages() {
			try {
				me.constructGraph("valid.json");
				List<String> good;
				good.add("A");
				good.add("E");
				good.add("B");
				good.add("C");
				good.add("D");
				
				List<String> maybeGood = (List<String>) me.getAllPackages();
				for(String i : good) {
					if(maybeGood.contains(i) == false)
						fail("PackageManager construct error occured");
				}
			}catch (FileNotFoundException e) {
	    		e.printStackTrace();
	    		System.out.println("Error: File cannot be found");
	    	  } catch (IOException e) {
	    		e.printStackTrace();
	    		System.out.println("Error: IOExcepion");
	    	  } catch (ParseException e) {
	    		e.printStackTrace();
	    		System.out.println("Error: Cannot parse file");
		}
		}
		/**
	     * This test tests the getPackageWithMaxDependencies method
	     * in the package manager class
	     */
		@Test
		public void test006_getPackageWithMaxDependencies() {
			try {
				me.constructGraph("valid.json");
				String good = "E";
				
				String maybeGood = me.getPackageWithMaxDependencies();
					if(maybeGood != good)
						fail("PackageManager getPackageWithMaxDependencies error");
				
			}catch (FileNotFoundException e) {
	    		e.printStackTrace();
	    		System.out.println("Error: File cannot be found");
			}catch (CycleException e) {
	        	    e.printStackTrace();
	        	    System.out.println("Error: Encounter a cycle in the graph ");
		}
}
