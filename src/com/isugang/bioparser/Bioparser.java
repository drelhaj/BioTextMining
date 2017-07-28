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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sugang
 */
public class Bioparser {

	public static List<String> ancestors = new ArrayList<String>();
	static PrintWriter writerSW, writerMWE, writerAll;

	public static void main(String[] args) throws Exception {

		writerAll = new PrintWriter("output//all.csv");
		writerSW = new PrintWriter("output//OntologySW-New.txt");
		writerMWE = new PrintWriter("output//OntologyMWE-New.txt");
		
		writerAll.println("GoID" + "," + "Name" + "," + "Paths" + "," + "NameSpace" + "," + "Immune System Process?" + "," + "Ancestors" + "," + "Children");
		writerAll.flush();
		
		File file = new File("go-basic.obo.txt");
		FileInputStream in = new FileInputStream(file);

		SimpleOBOTree tree = SimpleOBOTree.parse("goObo", in);
		int counter = 0;
		
		for (String goID : tree.parentsTree.keySet()) {
			System.out.println(++counter);
			List<String> nodeParents = new ArrayList<String>(tree.getParentNodes(goID));
			// check list size>0
			String nodeName = tree.getEntry(goID).getName().toString();

			String nodeNameUsas = nodeName;
			System.out.println(nodeName);
			
			if (nodeNameUsas.split("\\w+").length > 1) {
				nodeNameUsas = nodeNameUsas + " ";
				nodeNameUsas = nodeNameUsas.replace(" ", "_* ").trim();
			} else {
				nodeNameUsas = nodeNameUsas + "\tNN1";
			}
			
			
			// System.out.println(nodeParents);
			ancestors.add(goID);
			ancestors.addAll(nodeParents);
			getNodeParents(nodeParents, tree);
			int pathsCount = 0;
			String nameSpace = "";
			String isImnSysPrs = "No";
			// if name-space is molecular function
			if (ancestors.contains("GO:0003674")) {
				pathsCount = Collections.frequency(ancestors, "GO:0003674");
				nameSpace = "molecular_function";
			}
			// if name-space is cellular component
			if (ancestors.contains("GO:0005575")) {
				pathsCount = Collections.frequency(ancestors, "GO:0005575");
				nameSpace = "cellular_component";
			}

			// if name-space if biological process
			if (ancestors.contains("GO:0008150")) {
				pathsCount = Collections.frequency(ancestors, "GO:0008150");
				nameSpace = "biological_process";
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
			writerMWE.println("Child: " + goID + "\t" +nodeNameUsas + "\t" + allEntries);
			writerSW.println("Child: " + goID + "\t" +nodeNameUsas + "\t" + allEntries);
			writerAll.flush();
			writerMWE.flush();
			writerSW.flush();
			
			ancestors.clear();
			ancestorsList.clear();
			ancestorsSet.clear();
			System.out.println();
			System.out.println();
			System.out.println("===================================================================================");
		}
	}

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
