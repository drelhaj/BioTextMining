package geneOntology;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OntologyArchive {

	static PrintWriter writer;


	public static void main(String[] args) throws IOException {
		writer = new PrintWriter("output//synonyms.tsv");

		
		File oboFile = new File("oboArchive\\gene_ontology_edit.obo.2018-05-01");
		FileInputStream fis = new FileInputStream(oboFile);
		byte[] data = new byte[(int) oboFile.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");

		String[] lines = LinesReader.readLines(oboFile.toString());

		for (int i=0; i< lines.length; i++) {
			String oboID = "";
			if (lines[i].trim().startsWith("id: GO:")) {
				oboID = lines[i].replace("id: ", "").trim();
System.out.println(oboID);
String geneName = OntologyParser.getNameById(oboID, str);

if(geneName.startsWith("obsolete"))
writer.print(oboID + "\t" + geneName + "\t" + "Yes");
else
writer.print(oboID + "\t" + geneName + "\t" + "No");

//String[] considers = getConsidersById(oboID, str);
String[] synonyms = OntologyParser.getSynonymsById(oboID, str);

/*for(int c=0; c<considers.length;c++){
	writer.print("\t" + considers[c]);
}*/

for(int x=0; x<synonyms.length;x++){
	writer.print("\t" + synonyms[x]);
	writer.flush();
}

writer.println();
writer.flush();

				

			}


		}
	}
	

}
