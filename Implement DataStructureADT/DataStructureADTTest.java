//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Data Structure Program 1
// Files:           p1
// Course:          Comp Sci 400, LEC-004, Spring 2019
//
// Author:          Ruoxi Shen
// Email:           rshen2793@wisc.edu
// Lecturer's Name: Andy Kuemmel
// Due Date:        Before 10pm Thursday 2/7
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    n/a
// Partner Email:   n/a
// Partner Lecturer's Name: n/a
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         (identify each person and describe their help in detail)
// Online Sources:  (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * This test extends DataStructureADT and testing if DS works good.
 * 
 * @author Shen
 * @param T which extends DataStructureADT
 */
abstract class DataStructureADTTest<T extends DataStructureADT<String,String>> {
	
	private T dataStructureInstance;
	
	protected abstract T createInstance();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		dataStructureInstance = createInstance();
	}

	@AfterEach
	void tearDown() throws Exception {
		dataStructureInstance = null;
	}

	/**
	 * test00 tests if the size of the data structure is 0
	 **/
	@Test
	void test00_empty_ds_size() {
		if (dataStructureInstance.size() != 0)
		fail("data structure should be empty, with size=0, but size="+dataStructureInstance.size());
	}
	
	/**
	 * test01 tests if the size is one after insert a pair
	 **/
	@Test 
	void test01_after_insert_one_size_is_one() {
		dataStructureInstance.insert("10", "Aurora"); 
		if(dataStructureInstance.size() != 1) {
			fail("The size of the data structure should be 1 after inserting, but size=" 
		          + dataStructureInstance.size());
		}
	}
	
	/**
	 * test02 tests if the size is 0 after insert a pair and remove it
	 **/
	@Test 
	void test02_after_insert_one_remove_one_size_is_zero() {
		dataStructureInstance.insert("20", "Reachel");
		dataStructureInstance.remove("10");
		if (dataStructureInstance.size() != 0) {
			fail("data structure should be empty after interstion and removeing, but size=" 
		            + dataStructureInstance.size());
		}
	}
	
	/**
	 * test03 tests if there is duplicate key exception throws after insertions
	 **/
	@Test
	void test03_duplicate_exception_is_thrown() {
		dataStructureInstance.insert("30", "Abby" );
		dataStructureInstance.insert("40", "Lora");
		dataStructureInstance.insert("70", "Mouna");
		if(dataStructureInstance.contains("30")) 
			throw new RuntimeException("duplicate key");
	}
	
	/**
	 * test04 tests if DS returns false when trying to remove key that
	 * no present in the data structure
	 **/
	@Test
	void test04_remove_returns_false_when_key_not_present() {
		dataStructureInstance.insert("90", "Heby");
		dataStructureInstance.insert("100", "Hally");
		dataStructureInstance.insert("200", "Joye");
		if(dataStructureInstance.remove("60")) {
			fail("You are removing the wrong key");
		}
	}
	
	/**
	 * additionaltest01 tests if the size method works
	 **/
    @Test
    void additionaltest01_if_size_works() {
    	
    }
    
    /**
	 * additionaltest02 tests if the contains method works
	 **/
    @Test
    void additionaltest02_if_contains_works() {
    }
    
    /**
	 *additionaltest03 tests if the remove method works
	 **/
    @Test
    void additionaltest03_if_remove_works() {
    }

}
