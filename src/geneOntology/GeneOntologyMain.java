package geneOntology;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Methods.Utilities;

public class GeneOntologyMain {

	static PrintWriter writerSW, writerMWE;


	public static void main(String[] args) throws IOException {
		
		//get the recent obo file from the gene ontology download page
		
		writerSW = new PrintWriter("output//OntologySW.txt");

		writerMWE = new PrintWriter("output//OntologyMWE.txt");
		
		File file = new File("go-basic.obo.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");

		String oboFile = "go-basic.obo.txt";
		String[] lines = readLines(oboFile);

		for (String line : lines) {
			String oboID = "";
			if (line.trim().startsWith("id: GO:")) {
				oboID = line.replace("id: ", "").trim();

				String ontologyTags = GeneOntology.mainMethod(str, oboID);//0002458

				//if (ontologyTags.contains("GO:0002376")) {// GO:0002376 = immune
															// system process
				
				if(ontologyTags.contains("obsolete") || ontologyTags.contains("Has No Parents!")){
				System.out.println("Ignored!!! as it has not goID tags");
				}
				else{
			
					System.out.println("Ontologies: " + ontologyTags);
					System.out.println("=====================================================");

					String[] synonyms = OntologyParser.getSynonymsById(oboID, str);
					String oboName = OntologyParser.getNameById(oboID, str);
					System.out.println("=============================================> OboName: " + oboName);

					oboName = oboName.replace("\"", "");
					
					if (oboName.replaceAll("[^ ]", "").length() > 0){
					oboName = oboName.replace(" ", "_* ").trim();
					}
					
					
					if(ontologyTags.contains("_*")){
						writerMWE.println(ontologyTags);
						writerMWE.flush();
						
						for(int i=0; i<synonyms.length; i++){
							
						if(synonyms[i].contains("EXACT")){
						String synTerm = synonyms[i].substring(synonyms[i].indexOf("\""), synonyms[i].lastIndexOf("\""));
						synTerm = synTerm.replace("\"", "");
						synTerm = synTerm.replaceAll(" +", " ");

						

						if (Utilities.countWords(synTerm) > 1){
							System.out.println("=-===================================================================>>> "+synTerm);
							synTerm = synTerm+" ";
							synTerm = synTerm.replaceAll(" +", " ");
							synTerm = synTerm.replace(" ", "_* ").trim();
							String synOntologyTags = ontologyTags.replace(oboName, synTerm);
							writerMWE.println(synOntologyTags.replace("_*_*", "_*"));
							writerMWE.flush();

							System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MWE_"+i+": " + synOntologyTags.replace("_*_*", "_*"));

							}
							else{
								oboName = oboName.replace("_*", "").trim();
								//ontologyTags.replace("_*", "").trim();
								String synOntologyTags = ontologyTags.replace("_*", "").replace(oboName, synTerm + "\tNN1").trim();
								writerSW.println(synOntologyTags);
								writerSW.flush();
								System.out.println("was MWE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SW_"+i+": " + synOntologyTags);
								System.out.println("was MWE --------------------------------------"+ synTerm);
								System.out.println("was MWE --------------------------------------"+ oboName);
							}
							}
						}
					}
					else{
						writerSW.println(ontologyTags);
						writerSW.flush();
						for(int i=0; i<synonyms.length; i++){
							if(synonyms[i].contains("EXACT")){
							String synTerm = synonyms[i].substring(synonyms[i].indexOf("\""), synonyms[i].lastIndexOf("\""));
							synTerm = synTerm.replace("\"", "").trim();


							if (Utilities.countWords(synTerm) > 1){
								synTerm = synTerm+" ";
								synTerm = synTerm.replaceAll(" +", " ");
								synTerm = synTerm.replace(" ", "_* ").trim();
								String synOntologyTags = ontologyTags.replace("\tNN1", "");
								System.out.println("--------------------------------------"+ synTerm);
								System.out.println("--------------------------------------"+ oboName);
								synOntologyTags = synOntologyTags.replace(oboName, synTerm);
								writerMWE.println(synOntologyTags.replace("_*_*", "_*"));
								writerMWE.flush();
								System.out.println("was SW >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MWE_"+i+": " + synOntologyTags.replace("_*_*", "_*"));

								}
								else{
									//oboName = oboName.replace("_*", "").trim();
									String synOntologyTags = ontologyTags.replace(oboName, synTerm);
									writerSW.println(synOntologyTags);
									writerSW.flush();
									System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SW_"+i+": " + synOntologyTags);
			
								}
						}
					}
					}

				 

			}
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
