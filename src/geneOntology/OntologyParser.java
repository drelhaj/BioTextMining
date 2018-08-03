package geneOntology;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class OntologyParser {

	
	public static String getNameById(String ID, String str) {
		int idStart = str.indexOf("id: " + ID);		
		String subText = str.substring(idStart, str.length());
		String name = subText.substring(subText.indexOf("name: "), subText.indexOf("namespace:"));
		name = name.replace("name: ", "");

		return name.replaceAll("\r?\n", "");

	}
	
	
	
	public static String[] getSynonymsById(String ID, String str) {
		int idStart = str.indexOf("id: " + ID);
		String subText = str.substring(idStart, str.length());
		String idBlock = "";
		if(subText.indexOf("[Term]")<0)
		idBlock = subText.substring(0, subText.indexOf("[Typedef]"));
		else
		idBlock = subText.substring(0, subText.indexOf("[Term]"));
		
		String idBlockSubText = idBlock.substring(idBlock.indexOf("namespace: ")+10, idBlock.length());
		String synonymStr = "";
		String[] syonymLines = idBlockSubText.split("\\r?\\n");
		for(int i=0; i<syonymLines.length;i++){
			if(syonymLines[i].startsWith("synonym: "))
					synonymStr = synonymStr +syonymLines[i]+ ";";
		}
		
		String[] synonyms = synonymStr.split(";");
		
		return synonyms;
		//[Typedef]
	}
	
	
	
	
	public static String[] getConsidersById(String ID, String str) {
		int idStart = str.indexOf("id: " + ID);
		String subText = str.substring(idStart, str.length());
		String idBlock = "";
		if(subText.indexOf("[Term]")<0)
		idBlock = subText.substring(0, subText.indexOf("[Typedef]"));
		else
		idBlock = subText.substring(0, subText.indexOf("[Term]"));
		
		String idBlockSubText = idBlock.substring(idBlock.indexOf("is_obsolete: true")+17, idBlock.length());
		String considerStr = "";
		String[] considerLines = idBlockSubText.split("\\r?\\n");
		for(int i=0; i<considerLines.length;i++){
			if(considerLines[i].startsWith("consider: "))
					considerStr = considerStr + getNameById(considerLines[i].replace("consider: ",""), str) + ",";
		}
		
		String[] considers = considerStr.split(",");
		
		return considers;
		//[Typedef]
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
		
		
		
		public static String getName(String childText) {
			return childText.substring(childText.indexOf("name:") + 5, childText.indexOf("namespace:"));
		}

		
		
		public static String getOboID(String childText) {
			return childText.substring(childText.indexOf("id: ") + 4, childText.indexOf("name:"));
		}

		
			public static String[] getParents2(String id, String str){
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
			
}
