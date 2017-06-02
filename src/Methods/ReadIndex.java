package Methods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadIndex {

	static String indexEntry = "";
	public static void main(String[] args) throws IOException{
		
	String[] lines = readLines("index.txt");
	
	for(int i=0; i<lines.length;i++){
		if(Character.isDigit(lines[i].trim().charAt(lines[i].trim().length()-1))){
			indexEntry = indexEntry + " " + lines[i];

System.out.println(indexEntry);
indexEntry = "";
	}
		else{
indexEntry = indexEntry + " " + lines[i];
		}
			
	}
	
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
	
}
