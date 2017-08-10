/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isugang.bioparser;

import com.isugang.bioparser.simpleobo.SimpleOBOTree;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sugang
 */
public class BioparserTest {

	public static List<String> ancestors = new ArrayList<String>();
	static PrintWriter writerSW, writerMWE, writerAll;

	public static void main(String[] args) throws Exception {

		writerAll = new PrintWriter("output//allTest.csv");
		writerSW = new PrintWriter("output//OntologySW-NewTest.usas");
		writerMWE = new PrintWriter("output//OntologyMWE-NewTest.usas");
		
		writerAll.println("GoID" + "," + "Name" + "," + "Paths" + "," + "NameSpace" + "," + "Immune System Process?" + "," + "Ancestors" + "," + "Children");
		writerAll.flush();
		
		File file = new File("go-basic.obo.txt");
		FileInputStream in = new FileInputStream(file);

		SimpleOBOTree tree = SimpleOBOTree.parse("goObo", in);
		int counter = 0;
		int once = 1;
		for (String goID : tree.parentsTree.keySet()) {
			if(once == 1){
				once = 2;
				goID = "GO:0000001";
			System.out.println(++counter);
			List<String> nodeParents = new ArrayList<String>(tree.getParentNodes(goID));
			// to do: check list size>0
			String nodeName = tree.getEntry(goID).getName().toString();

			String nodeNameUsas = nodeName;
			String singleMulti = "";
			System.out.println(nodeName);
			
			if (nodeNameUsas.split("\\w+").length > 1) {
				nodeNameUsas = nodeNameUsas + " ";
				nodeNameUsas = nodeNameUsas.replace(" ", "_* ").trim();
				singleMulti = "Multi";
			} else {
				nodeNameUsas = nodeNameUsas + "\tNN1";
				singleMulti = "Single";
			}
			
			
			// System.out.println(nodeParents);
			ancestors.add(goID);
			ancestors.addAll(nodeParents);
			getNodeParents(nodeParents, tree);
			int pathsCount = 0;
			String nameSpace = "";
			String isImnSysPrs = "No";
			int rootIndex = 0;
			// if name-space is molecular function
			if (ancestors.contains("GO:0003674")) {
				pathsCount = Collections.frequency(ancestors, "GO:0003674");
				nameSpace = "molecular_function";
				rootIndex = ancestors.indexOf("GO:0003674");
			}
			// if name-space is cellular component
			if (ancestors.contains("GO:0005575")) {
				pathsCount = Collections.frequency(ancestors, "GO:0005575");
				nameSpace = "cellular_component";
				rootIndex = ancestors.indexOf("GO:0005575");
			}

			// if name-space if biological process
			if (ancestors.contains("GO:0008150")) {
				pathsCount = Collections.frequency(ancestors, "GO:0008150");
				nameSpace = "biological_process";
				rootIndex = ancestors.indexOf("GO:0008150");

			}

			System.out.println("Paths:" + pathsCount);
			System.out.println("Namespace:" + nameSpace);
			if (ancestors.contains("GO:0002376")) {
				System.out.println("Immune System Process");
				isImnSysPrs = "Yes";
			}

			Set<String> ancestorsSet = new LinkedHashSet<>(ancestors);
			List<String> ancestorsList = new ArrayList<String>(ancestorsSet);
			System.out.println(ancestorsList);

			
			String immune = ""; //I = Immune , N = Not Immune
			if(isImnSysPrs.equals("Yes"))
				immune = ".I";
			else
				immune = ".N";
		
			String allEntries = ancestorsList.toString();
			allEntries = allEntries.replace(",", immune+",").replace("]", immune).replace("[", "");
			
			
			writerAll.println(goID + "," + "\""+nodeName + "\"," + pathsCount + "," + "\"" + nameSpace + "\""+ "," + isImnSysPrs + "," + "\"" + ancestorsList + "\"" + "," + "\"" + tree.getChildNodes(goID) + "\"");
			writerAll.flush();
			if(singleMulti.equals("Multi")){
					writerMWE.println("Child: " + goID + "\t" +nodeNameUsas + "\t" + allEntries);
					writerMWE.flush();
			}
			if(singleMulti.equals("Single")){
			writerSW.println("Child: " + goID + "\t" +nodeNameUsas + "\t" + allEntries);
			writerSW.flush();
			}
			List<String> levels = new ArrayList<String>();
			List<String> levelsReverse = new ArrayList<String>();
			List<String> goIDs = new ArrayList<String>();


			for(int i=1; i<ancestorsList.size(); i++){
				String node = ancestorsList.get(i);
				Set<String> children = tree.getChildNodes(node);
				//System.out.println(node + "   :    "+children);
				
	
			      // intersection as set
			      Set<String> intersect = new HashSet<String>(children);
			      intersect.retainAll(ancestorsList);
			      System.out.println(node + ":\t" + intersect.size() + "\tMatches: " + intersect);
			      for(String n : intersect){
			    	 levels.add(ancestorsList.indexOf(n) + "." + i);
			    	 levelsReverse.add(i + ", " + ancestorsList.indexOf(n)+",");

			      }
			      
			    
			        
			        
				
			}
			
			System.out.println("all Levels: "+levels);
			AllPaths.printAllPaths(levels, levelsReverse, rootIndex, ancestorsList);

			
			ancestors.clear();
			rootIndex = 0;
			ancestorsList.clear();
			ancestorsSet.clear();
			System.out.println();
			System.out.println();
			System.out.println("===================================================================================");
		}
	}}

	public static void getNodeParents(List<String> nodeParents, SimpleOBOTree tree) {

		for (int i = 0; i < nodeParents.size(); i++) {
			List<String> parents = loopNodeParents(nodeParents.get(i), tree);
			if (parents.size() > 0) {
				// System.out.println(parents);
				ancestors.addAll(parents);
				getNodeParents(parents, tree);
			}

		}

	}


	
	
	public static List<String> loopNodeParents(String nodeID, SimpleOBOTree tree) {

		return new ArrayList<String>(tree.getParentNodes(nodeID));
	}
}
