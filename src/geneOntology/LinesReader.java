package geneOntology;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinesReader {

	
	/**
	 * Method to read a text file into an Array List of Strings
	 * 
	 * @param fileName
	 * @return ArrayList of Strings
	 * @throws IOException
	 */
	public static String[] readLines(String fileName) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line.trim());
		}

		bufferedReader.close();
		return lines.toArray(new String[lines.size()]);
	}

}
