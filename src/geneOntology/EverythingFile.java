package geneOntology;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EverythingFile {

	static PrintWriter writerSW, writerMWE, writerPaths;

static String pathsCount = "";

	public static void main(String[] args) throws IOException {
		writerSW = new PrintWriter("output//OntologySW.txt");

		writerMWE = new PrintWriter("output//OntologyMWE.txt");
		writerPaths = new PrintWriter("output//pathsDetails.csv");
		writerPaths.println("ID" + "," + "Paths Count");
		writerPaths.flush();
		
/*		File file = new File("C:\\Users\\elhaj\\Box Sync\\Bio_Text_Mining\\Bio Text Mining Ontology\\everything.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");*/

		String oboFile = "output//everything.txt";
		String[] lines = readLines(oboFile);

		for (String line : lines) {
			if (line.trim().startsWith("[Item]")) {
			System.out.println("");
			System.out.print(line.replace("[Item]: ", "").trim());
			writerPaths.println("");
			writerPaths.flush();
			writerPaths.print(line.replace("[Item]: ", "").trim());
			writerPaths.flush();
			}
			if (line.trim().startsWith("Number of pathes")){
				pathsCount = line.replace("Number of pathes: ", "").trim();
				System.out.print("\t"+pathsCount);
				writerPaths.print(","+pathsCount);
				writerPaths.flush();
			}
			
			if (line.trim().startsWith("Path: 1 :")){
				System.out.print("\t"+line.substring(line.lastIndexOf("GO:"),line.lastIndexOf(","))); // replace("Number of pathes: ", "").trim());
				writerPaths.print(","+line.substring(line.lastIndexOf("GO:"),line.lastIndexOf(",")).trim());
				writerPaths.flush();
			}
			

			
			

				 

			

		}
	}

	
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

	public static String[] removeNulls(String[] array) {

		List<String> list = new ArrayList<String>();

		for (String s : array) {
			if (s != null && s.length() > 0) {
				list.add(s);
			}
		}

		return array = list.toArray(new String[list.size()]);
	}

}
