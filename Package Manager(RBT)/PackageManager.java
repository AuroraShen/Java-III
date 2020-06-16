/////////////////////////////// 80 COLUMNS WIDE /////////////////////////////// 
//
// Title:           Package Manager
// Author:          rshen27@wisc.edu
// Lecturer's Name: Andy Kuemmel
//
/////////////////////////////// 80 COLUMNS WIDE /////////////////////////////// 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**  
 * 
 * PackageManager is used to process json package dependency files
 * and provide function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own
 * entry in the json file.  
 * 
 * Package dependencies are important when building software, 
 * as you must install packages in an order such that each package 
 * is installed after all of the packages that it depends on 
 * have been installed.
 * 
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 * 
 * This program will read package information and 
 * provide information about the packages that must be 
 * installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

public class PackageManager {
    
    private Graph myGraph;
    
    /*
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
    	this.myGraph = new Graph();
    }
    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the give file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
       	try{
       		Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
			JSONObject jo = (JSONObject) obj; 
			JSONArray packages = (JSONArray) jo.get("packages");
			String edgeEnd;
		    
			//load in the JsonArray one by one
			for(int i = 0; i < packages.size(); i++) {
				JSONObject jsonPackage = (JSONObject) packages.get(i);
				String name = (String) jsonPackage.get("name");
				JSONArray dependencies = (JSONArray) jsonPackage.get("dependencies");
		        myGraph.addVertex(name);
		        
		        //for each package, load in it's edges one by one
			    for(int j = 0; j < dependencies.size(); j++) {
					edgeEnd = (String) dependencies.get(j);
					myGraph.addEdge(name, edgeEnd);
			    }
			}
    	}catch (FileNotFoundException e) {
            throw new FileNotFoundException();
    	} catch (IOException e) {
            throw new IOException();
    	} catch (ParseException e) {
            throw new ParseException(0);
    	}
    }
		
			
    
    
    /**
     * Helper method to get all packages in the graph.
     * 
     * @return Set<String> of all the packages
     */
    public Set<String> getAllPackages() {
        //In package manager, each package is a vertex
    	//all package means all the Vertices in the graph
        return myGraph.getAllVertices();
    }
    
    /**
     * Given a package name, returns a list of packages in a
     * valid installation order.  
     * 
     * Valid installation order means that each package is listed 
     * before any packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the installation order for a particular package. Tip: Cycles in some other
     * part of the graph that do not affect the installation order for the 
     * specified package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the 
     * dependency graph.
     */
    public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {
    	Set<String> packageSet = getAllPackages();
    	List<String> order = new Stack<String>();	
    	if (!packageSet.contains(pkg))
			throw new PackageNotFoundException();
    	
    	try {
    		//call the private helper for recursion
            order = installationOrderHelp(pkg, order);
    	 } 
    	//detect cycle exception
    	catch (CycleException e) {
    		 throw new CycleException();
    	 }
    	return order;
    }
    
    
    /**
     * The installation order helper to help with the recursion
     * process, the helper also do cycle detection
     * 
     * @return order
     * @throws CycleException if you encounter a cycle in the graph
     */
    private List<String> installationOrderHelp(String pkg, List<String> order)throws CycleException {
    	List<String> packages = myGraph.getAdjacentVerticesOf(pkg);
        
    	//for the first vertex
    	if (packages == null) {
    	  if (!order.contains(pkg)) {
    		  order.add(pkg);
    	  }
    	  return order;
    	} 
    	//for more vertices
    	else {
    		//for each vertices that in the dependencies
    		for (int i = 0; i < packages.size(); i++) {
    	      String name = packages.get(i); // get the name of the vertex
    	      List<String> dependencies = myGraph.getAdjacentVerticesOf(name);
    	      //if the vertex has a cycle
    	      if (dependencies.contains(pkg) && dependencies != null) {
    	         throw new CycleException();
    	      }
    	      order = installationOrderHelp(packages.get(i), order);
    	  }
    	  order.add(pkg);
    	}
    
    return order;
}
    
    /**
     * Given two packages - one to be installed and the other installed, 
     * return a List of the packages that need to be newly installed. 
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") 
     * If package A needs to be installed and packageB is already installed, 
     * return the list ["A", "C"] since D will have been installed when 
     * B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the dependencies of the given packages. If there is a cycle in some other
     * part of the graph that doesn't affect the parsing of these dependencies, 
     * cycle exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed 
     * do not exist in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
    	  Set<String> packageSet = this.getAllPackages();
    	  List<String> installed;
    	  List<String> toBeInstall = null; 
    	  String name;
    	  
    	//if any of the packages passed do not exist
	      if (!packageSet.contains(newPkg) || !packageSet.contains(installedPkg))
	    	  throw new PackageNotFoundException();
    	  try {
    	      //load the two packages.
    	      installed = this.getInstallationOrder(installedPkg);
    	      toBeInstall = this.getInstallationOrder(newPkg);
    	      //check each dependencies for newPkg
    	      for(int i = 0; i< toBeInstall.size(); i++) {
    	    	  name = toBeInstall.get(i);
    	    	  //if the dependencies is installed
    	    	  if(installed.contains(name)) {
    	    		  toBeInstall.remove(i);
    	    		  i--;
    	    	  }
    	      }
    	  }catch(CycleException e) {
    		  throw new CycleException();
    	  }
    	  return toBeInstall;
    }
    
    /**
     * Return a valid global installation order of all the packages in the 
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install 
     * all the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     * @throws PackageNotFoundException 
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException, PackageNotFoundException{
    	Set<String> packageSet = this.getAllPackages();
    	List<String> before ;
    	List<String> installationOrder = null;
    	Iterator<String> trace;
    	
    	String currentName; 
    	String depenPackage;
    	boolean firstV = false;
    	
    	try {
    		//iteration of the set
    		for (trace = packageSet.iterator(); trace.hasNext(); ) {
    			//get the name as current vertex in the graph
    			currentName = trace.next();
    			//get the installation Order of current vertex
    	        before = this.getInstallationOrder(currentName);
    	        //for the first vertex
                if(firstV == false) {
                	//only for the first vertex
                	firstV = true;
                	installationOrder = before;
                	}
                else {
                	//for each dependencies of the current vertex
    	            for(int i=0; i <before.size() ; i++ ) {
    	              depenPackage = before.get(i);
    	              //if the vertex not exists in the order
    	              if(!installationOrder.contains(depenPackage))
    	            	  installationOrder.add(depenPackage);
    	            }
                }
    		}
    	}catch (CycleException e) {
    		throw new CycleException();
    	}
    	return installationOrder;
    }
    
    /**
     * Find and return the name of the package with the maximum number of dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file.  
     * The number of dependencies includes the dependencies of its dependencies.  
     * But, if a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.  
     * Then,  A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     */
    public String getPackageWithMaxDependencies() throws CycleException,PackageNotFoundException {
    	List<String> maxList = null;
    	String maxName = ""; 
    	List<String> currentList;
    	String currentName;
    	Set<String> packageSet = myGraph.getAllVertices();
    	Iterator<String> trace;
    	boolean firstV = false;
    	
    	try {
    		for ( trace = packageSet.iterator(); trace.hasNext(); ) {
    		    //get the name as current vertex in the graph
    		    currentName = trace.next();
    		    currentList = this.getInstallationOrder(currentName);
    		    //for the first vertex
    	        if (firstV == false) {
    	        	//only for the first vertex
    	        	firstV = true;
    	        	//set the first vertex as the max
    	        	maxName = currentName;
    	        	maxList = currentList;
    	        }
    	        else if(currentList.size() > maxList.size()) {
    	        	//update the max 
    	            maxName = currentName;
    	            maxList = currentList;
    	            }
    	        }
    		}catch (CycleException e) {
    			throw new CycleException();
    		}
    		return maxName;
    }

    /**
     * Main method that
     * @param args
     */
    public static void main (String [] args) {
    	  //use the valid.json that given
    	  String jsonFilepath = "valid.json";
    	  System.out.println("PackageManager.main()");
    	  PackageManager me = new PackageManager();
    	  try {
    	    me.constructGraph(jsonFilepath);
    	    me.getInstallationOrder("A");
    	    me.getPackageWithMaxDependencies();
    	    me.toInstall("Z", "A");
    	    me.getInstallationOrderForAllPackages();
    	  } catch (FileNotFoundException e) {
    		e.printStackTrace();
    		System.out.println("Error: "+ jsonFilepath +" cannot be found");
    	  } catch (IOException e) {
    		e.printStackTrace();
    		System.out.println("Error: IOException "); 
    	  } catch (ParseException e) {
    		e.printStackTrace();
    		System.out.println("Error: "+  jsonFilepath +" cannot be parsed");
    	  } catch (CycleException e) {
    	    e.printStackTrace();
    	    System.out.println("Error: Encounter a cycle in the "+ jsonFilepath + " graph ");
    	  } catch (PackageNotFoundException e) {
    		e.printStackTrace();
    	    System.out.println("Error: PackageNotFoundEXCEPTION");
    	  }   
    }
}
