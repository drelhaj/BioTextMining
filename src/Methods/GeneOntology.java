package Methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class GeneOntology {
public static  String str = "";
	public static void main(String[] args) throws IOException{
		

		 File file = new File("go-basic.obo.txt");
		 FileInputStream fis = new FileInputStream(file);
		 byte[] data = new byte[(int) file.length()];
		 fis.read(data);
		 fis.close();

		 str = new String(data, "UTF-8");
				  
		 getMainEntry(str, "GO:0001780");
		 //String[] parents1 = getParent(idText);
		 
		// System.out.println(idText);
		// System.out.println(java.util.Arrays.toString(parents1));

		 
	}

	
	public static void getMainEntry(String text, String ID){
		int idStart = text.indexOf("id: "+ID);
		String subText = text.substring(idStart, text.length());
		String idBlock = subText.substring(0,subText.indexOf("[Term]")); 
		System.out.println("Child: ["+ID + "]\t Name: [" + getName(idBlock).trim()+"]");
		getParent(idBlock);
		
		//return idBlock;
	}
	
	
	public static void getParent(String idBlock){
		int counter = 0;
		int size = StringUtils.countMatches(idBlock, "is_a:");
		String[] parents = new String[size];
		String[] lines = idBlock.split("\\r?\\n");
				for(int i=0; i<lines.length;i++){
					if(lines[i].contains("is_a:")){
						parents[counter] = lines[i].substring(lines[i].indexOf("is_a: ")+6, lines[i].indexOf(" !")).trim();
						++counter;
					}
				}
				System.out.print("Parent(s): "+((parents.length<1)? "[Root]" : java.util.Arrays.toString(parents))) ;
				System.out.print("\t Name: [");
				for(int x=0; x<parents.length;x++){System.out.print(getNameById(parents[x]).replaceAll("\\r?\\n", " ").trim()+ ", ");}
				System.out.println("]");
				System.out.println("===================================================================================================================");
				loopParents(parents);
				//return parents;
	}
	
	public static void loopParents(String[] parents){
		for(int i=0; i<parents.length;i++){
			getMainEntry(str, parents[i]);
		}
	}
	
		public static String getName(String childText){
			return childText.substring(childText.indexOf("name:")+5, childText.indexOf("namespace:"));
		}
		
		public static String getNameById(String ID){
			int idStart = str.indexOf("id: "+ID);
			String subText = str.substring(idStart, str.length());
			String idBlock = subText.substring(0,subText.indexOf("[Term]")); 
			
			return idBlock.substring(idBlock.indexOf("name:")+5, idBlock.indexOf("namespace:"));

		}
	
}
