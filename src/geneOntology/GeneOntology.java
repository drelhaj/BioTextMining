package geneOntology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class GeneOntology {

	public static String oboID = "";
	public static String str = "";
	public static List<String> printArray = new ArrayList<>();
	public static List<String> parentsLines = new ArrayList<>();

	public static int arrayCount = 0;
	public static List<String> printArrayTags = new ArrayList<>();
	public static int level = -1;
	public static int position = 0;
	public static int parentLevel = 0;
	public static int lastLevelWithParents = -1;
	public static int lastLevelNoOfParents = 0;
	public static boolean once = true;
	public static PrintWriter writer, errorWriter;
	public static void clearValues() {

		str = "";
		printArray.clear();// = new String[1000];
		arrayCount = 0;
		level = -1;
		position = 0;
		parentLevel = 0;
		lastLevelWithParents = -1;
		lastLevelNoOfParents = 0;
		oboID = "";
		printArrayTags.clear();
	}

	public static String getOntologyTags(List<String> Printlist, String ID) {


		String childName = GeneOntology.getNameById(ID).trim();
		if (childName.split("\\w+").length > 1) {
			childName = childName + " ";
			childName = childName.replace(" ", "_* ").trim();
		} else {
			childName = childName + "\tNN1";
		}

		Set<String> set = new LinkedHashSet<>(Printlist);
		List<String> list = new ArrayList<String>(set);

		String entryName = "";
		if (list.size() > 0) {
			entryName = list.get(0).substring(0, list.get(0).indexOf("."));

			return "Child: " + ID + "\t" + childName + "\t" + Arrays.toString(list.toArray());
		} else {
			return "Child is not Immune System Process";
		}
	
	}


	public static String mainMethod(String text, String ID) throws FileNotFoundException {
		
		if (text.indexOf("id: " + ID) > -1) {
			clearValues();
			oboID = ID;
			getMainEntry(text, ID);
			tracePaths(parentsLines, ID);
			parentsLines.clear();
	

			if(printArrayTags.size()<1)
				return "Child: " + ID + "\t Has No Parents!";
			
			return getOntologyTags(printArrayTags, oboID);
		} else
			return "ID doesn't exist!";

	}
	
public static void tracePaths(List<String> lines, String ID) throws FileNotFoundException{
	
	String errorsFile = "output\\errors.txt"; 
	File f = new File(errorsFile);
	errorWriter = null;
	
	if ( f.exists() && !f.isDirectory() ) {
		errorWriter = new PrintWriter(new FileOutputStream(new File(errorsFile), true));
	}
	if(!f.exists()) {
		errorWriter = new PrintWriter(errorsFile);
		errorWriter.println("Children with Parents containing more than two parents");
		errorWriter.flush();
	}
	
	String everything = "output\\everything.txt"; 
	File f2 = new File(everything);
	writer = null;
	
	if ( f2.exists() && !f2.isDirectory() ) {
		writer = new PrintWriter(new FileOutputStream(new File(everything), true));
	}
	if(!f2.exists()) {
		writer = new PrintWriter(everything);
		writer.println("Ontology All Paths");
		writer.flush();
	}
	
	writer.println("==================================================================================================");
	writer.flush();
	System.out.println(ID);
	writer.println("[Item]: "+ID);
	writer.flush();
	String allParents = "";
	for(int y=0; y<lines.size(); y++){
		allParents = allParents + "\t" + lines.get(y).substring(11,lines.get(y).length());
	}

	int pathsCount = getPathsCount(parentsLines);
	System.out.println("Number of pathes: "  + pathsCount);
	writer.println("Number of pathes: "  + pathsCount);
	writer.flush();
	List<String> nextParents = new ArrayList<String>();
	List<String> path = new ArrayList<String>();
	//path.add(ID);
	int lineBreak = 0;
	int pointer = 0;
	int parentPointer = 0;
	int parentPointerFlag = 0;
	int parentFlag = 0;

	int parentLvl = 0;
	String previous = "";
	String startingID = ID;
	for (int x = 1; x <= pathsCount; x++) {
		if(x == 1){
		System.out.print("Path: "+x + " : [" +startingID + ",\t");
		writer.print("Path: "+x + " : [" +startingID + ",\t");
		writer.flush();
		path.add(startingID);

		}
		else{
			if(!(lines.size()==pointer)){
			System.out.print("Path: "+x + " : [");
			writer.print("Path: "+x + " : [");
			writer.flush();
			if(!(allParents.contains(lines.get(pointer).substring(0,lines.get(pointer).indexOf(" ")).trim()))){
				path.clear();
				path.add(0, ID);
				System.out.print(path.get(0) + ",\t");
				writer.print(path.get(0) + ",\t");
				writer.flush();

			}
			else{
				if(lines.get(parentPointer).contains(lines.get(pointer).substring(0,lines.get(pointer).indexOf(" ")).trim())){
					//System.out.println("YES >>>>>>>>>>:   " + lines.get(pointer).substring(0,lines.get(pointer).indexOf(" ")).trim());
				}
				else{
					
					for(int w =0 ; w< lines.size(); w++){
						if(lines.get(w).contains(", "+lines.get(pointer).substring(0,lines.get(pointer).indexOf(" ")).trim())){
							parentPointer = w;
						}
					}
					
				}
			String idNew = lines.get(parentPointer);
			idNew = idNew.substring(0,10);
			int location = path.indexOf(idNew);
			if(location > -1){
			path = path.subList(0, location+1);
			for(int w =0 ; w< path.size(); w++){
				System.out.print(path.get(w) + ",\t");
				writer.print(path.get(w) + ",\t");
				writer.flush();
			}
			}
			else{
				path.clear();
				path.add(0, ID);
				System.out.print(path.get(0) + ",\t");
				writer.print(path.get(0) + ",\t");
				writer.flush();

			}
			}

			

		}
		}
	for (int i = pointer; i < lines.size(); i++) {
		
		String part1 = lines.get(i).substring(0,lines.get(i).indexOf(" ")).trim();


		if(!(path.contains(part1))){
		path.add(part1);
		System.out.print(part1 + ",\t");
		writer.print(part1 + ",\t");
		writer.flush();

		}
		String part2 = lines.get(i).substring(lines.get(i).indexOf("[")+1,lines.get(i).indexOf("]")).trim();
		if(part2.contains(",")){
			if(part2.split(",").length>2){
				errorWriter.println(ID);
				errorWriter.flush();
				
			}
			
			parentFlag++;
			parentPointer = i;
			String xx = part2.substring(part2.lastIndexOf(","), part2.length()).replace(",", "").replaceAll("]", "").trim();
			for(int k =0; k<lines.size(); k++){
				if(lines.get(k).startsWith(xx)){
					pointer = k;
					startingID = xx;
					
					break;
				}
			}
		}


		
		if(part2.trim().length()<1){

			if(part1.equals(previous)){
				pointer = i+1;
				parentPointerFlag = -1;
			}
			previous = part1;
			System.out.println("]");
			writer.println("]");
			writer.flush();
			break;
		}

		}
	String immune = ""; //I = Immune , N = Not Immune
	if(path.contains("GO:0002376"))
		immune = "I";
	else
		immune = "N";
	
	for (int k = 0; k < path.size(); k++) {
		printArrayTags.add(path.get(k) + "." + (k + 1)+"."+immune);
	}
	

	}

	// add elements to al, including duplicates
	Set<String> hs = new HashSet<>();
	hs.addAll(printArrayTags);
	printArrayTags.clear();
	printArrayTags.addAll(hs);
	
		//System.out.println("Path: x : "+Arrays.toString(printArrayTags.toArray()));


	

	
	
}


public static void loopNextParents(String parent, int parentLevel, List<String> lines, int lineBreak){
	
	for(int r=lineBreak; r<lines.size(); r++){
		if(lines.get(r).startsWith(parent)){
		String part1 = lines.get(r).substring(0,lines.get(r).indexOf(" ")).trim();
		String part2 = lines.get(r).substring(lines.get(r).indexOf("[")+1,lines.get(r).indexOf("]")).trim();
		}

	}
	
}


	public static void getMainEntry(String text, String ID) {
		str = text;
		level++;
		int idStart = text.indexOf("id: " + ID);
		String subText = text.substring(idStart, text.length());
		String idBlock = subText.substring(0, subText.indexOf("[Term]"));
		// System.out.println("Child: ["+ID + "]\t Name: [" +
		// getName(idBlock).trim()+"]");
		getParent(idBlock,ID);

	}


	public static void getParent(String idBlock, String childID) {
		int counter = 0;
		int size = StringUtils.countMatches(idBlock, "is_a:");
		String[] parents = new String[size];
		if (once == true) {
			position = size;
			once = false;
		}
		String[] lines = idBlock.split("\\r?\\n");
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].contains("is_a:")) {
				parents[counter] = lines[i].substring(lines[i].indexOf("is_a: ") + 6, lines[i].indexOf(" !")).trim();
				++counter;
			}
		}
		if (parents.length > 1) {
			lastLevelWithParents++;
			lastLevelNoOfParents = parents.length;

		}

		if (parents.length > 0) {

			printArray.add(level, getOboID(idBlock).trim());
		} else {
			lastLevelNoOfParents--;

			printArray.add(level, getOboID(idBlock).trim());
			for (int w = level + 1; w < printArray.size(); w++) {
				printArray.remove(w);
			}

			/*
			 * String childName = getNameById(printArray.get(0)).trim();
			 * if(childName.split("\\w+").length>1){childName = childName+ " ";
			 * childName = childName.replace(" ", "_* ").trim();} else{childName
			 * = childName+ " NN1";}
			 */
	

			// System.out.println("Child: " + printArray.get(0) + "\t"
			// +childName + "\t"+Arrays.toString(printArray.toArray()));

			arrayCount = 0;

			if (lastLevelNoOfParents < 0)
				level = lastLevelWithParents - 1;
			else
				level = lastLevelWithParents;
			if (position < 2)
				level = lastLevelWithParents - 1;

		}
		// System.out.print("Parent(s): "+((parents.length<1)? "[Root]" :
		// java.util.Arrays.toString(parents))) ;
		// System.out.print("\t Name: [");
		// for(int x=0;
		// x<parents.length;x++){System.out.print(getNameById(parents[x]).replaceAll("\\r?\\n",
		// " ").trim()+ ", ");}
		// System.out.println("]");
		loopParents(parents, childID);
	}

	public static void loopParents(String[] parents, String childID) {
		//parentsLines.add(childID);
		for (int i = 0; i < parents.length; i++) {
			if (i > 0)
				position--;
			String entry = parents[i] + " : "+Arrays.toString(getParents2(parents[i]));
			//System.out.println(entry);
			parentsLines.add(entry);
			getMainEntry(str, parents[i]);

		}
	}
	
	public static String[] getParents2(String id){
		int counter = 0;
		int idStart = str.indexOf("id: " + id);
		String subText = str.substring(idStart, str.length());
		String idBlock = subText.substring(0, subText.indexOf("[Term]"));
		int size = StringUtils.countMatches(idBlock, "is_a:");
		String[] parents = new String[size];
		String[] lines = idBlock.split("\\r?\\n");
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].contains("is_a:")) {
				parents[counter] = lines[i].substring(lines[i].indexOf("is_a: ") + 6, lines[i].indexOf(" !")).trim();
				++counter;
			}
		}
		
		return parents;
	}
	
	

	public static String getName(String childText) {
		return childText.substring(childText.indexOf("name:") + 5, childText.indexOf("namespace:"));
	}

	public static String getOboID(String childText) {
		return childText.substring(childText.indexOf("id: ") + 4, childText.indexOf("name:"));
	}

	public static String getNameById(String ID) {
		//System.out.println("id: " +ID);
		//writer.println("id: " +ID);
		//writer.flush();

		int idStart = str.indexOf("id: " + ID);
		
		String subText = str.substring(idStart, str.length());
		String idBlock = subText.substring(0, subText.indexOf("[Term]"));

		return idBlock.substring(idBlock.indexOf("name:") + 5, idBlock.indexOf("namespace:")).replaceAll("\r?\n", "");

	}

	public static String[] removeNulls(String[] array) {

		List<String> list = new ArrayList<String>();

		for (String s : array) {
			if (s != null && s.length() > 0) {
				list.add(s);
			}
		}

		return array = list.toArray(new String[list.size()]);
	}

	
	//count the number of paths for an entry
	public static int getPathsCount(List<String> paths){
		
		int occurrences = 0;
		for (int i = 0; i < paths.size(); i++) {
			if(paths.get(i).contains("[]"))
				occurrences++;
		}
		return occurrences;
	}
	
}
