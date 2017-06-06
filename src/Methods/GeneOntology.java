package Methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class GeneOntology {
public static  String str = "";
public static String[] printArray = new String[10];
public static int arrayCount = 0;
public static int level = -1;
public static int position = 0;
public static int parentLevel = 0;
public static int lastLevelWithParents = -1;
public static int lastLevelNoOfParents = 0;
public static boolean once = true;
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
		level++;
		//System.out.println("*****************************************************************************************************************>  Level: " + level);
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
		if(once==true){
			position = size;
			once = false;
		}
		String[] lines = idBlock.split("\\r?\\n");
				for(int i=0; i<lines.length;i++){
					if(lines[i].contains("is_a:")){
						parents[counter] = lines[i].substring(lines[i].indexOf("is_a: ")+6, lines[i].indexOf(" !")).trim();
						++counter;
						//System.out.println("*****************************************************************************************************************>  Position: " + position);
					}
				}
				if(parents.length>1){
					lastLevelWithParents++;
					lastLevelNoOfParents = parents.length;

				}

				if(parents.length>0){
					
					//System.out.println("*****************************************************************************************************************>  lastLevelWithParents: " + lastLevelWithParents + "lastLevelparentsCount: " + lastLevelNoOfParents);

					printArray[level]=getName(idBlock).trim();
				}
				else{
					lastLevelNoOfParents--;

					//if(position==2)
						//lastLevelNoOfParents--;

					printArray[level]=getName(idBlock).trim();
					for(int w=level+1; w<printArray.length;w++){
						printArray[w] = null;
					}
					
					System.out.println("Path = "+java.util.Arrays.toString(removeNulls(printArray)));
					arrayCount = 0;

					if(lastLevelNoOfParents<0)
						level = lastLevelWithParents-1;
	else
						level = lastLevelWithParents;
					if(position<2)
						level = lastLevelWithParents-1;


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
if(i>0)
position--;

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
		
		public static String[] removeNulls(String[] array){
			
			   List<String> list = new ArrayList<String>();

			    for(String s : array) {
			       if(s != null && s.length() > 0) {
			          list.add(s);
			       }
			    }

			    return array = list.toArray(new String[list.size()]);
		}
	
}
