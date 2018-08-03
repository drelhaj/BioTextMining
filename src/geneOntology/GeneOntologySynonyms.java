package geneOntology;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class to extract GOIDs synonyms (Exact Only).
 * @author elhaj
 *
 */
public class GeneOntologySynonyms {


public static void main(String[] args) throws IOException {
	
	
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
		
	}
}
}

	
	
	
	
	
}
