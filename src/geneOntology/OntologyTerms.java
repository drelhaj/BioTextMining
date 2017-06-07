package geneOntology;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OntologyTerms {

	private String oboIdValue;
	

	public static void main(String[] args) throws IOException{
	OntologyTerms ot = new OntologyTerms();
	
		 File file = new File("go-basic.obo.txt");
		 FileInputStream fis = new FileInputStream(file);
		 byte[] data = new byte[(int) file.length()];
		 fis.read(data);
		 fis.close();

		 String str = new String(data, "UTF-8");
		 
		String oboFile = "go-basic.obo.txt";
		String[] lines = readLines(oboFile);
	
		for(String line : lines){
			String oboID = "";
			if(line.trim().startsWith("id: GO:")){
			oboID = line.replace("id: ", "").trim();
			GeneOntology.mainMethod(str, oboID);
			GeneOntology.clearValues();

			}
				
		}
	}
	
	public String getOboIdvalue(){
		return this.oboIdValue;
	}
	public void setOboIdvalue(String oboIdValue){
		this.oboIdValue = oboIdValue ;
	}
	
	
	/**
	 * Method to read a text file into an Array List of Strings
	 * @param fileName
	 * @return ArrayList of Strings
	 * @throws IOException
	 */
	public static String[] readLines(String fileName) throws IOException{
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while((line = bufferedReader.readLine()) != null) {
			lines.add(line.trim());
		}
		
		bufferedReader.close();
		return lines.toArray(new String[lines.size()]);
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
