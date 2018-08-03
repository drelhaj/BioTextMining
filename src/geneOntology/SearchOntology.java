package geneOntology;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyService.SearchOptions;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.bioportal.BioportalOntologyService;
import uk.ac.ebi.ontocat.file.FileOntologyService;
;

/**
 * Example 8
 * 
 * Shows how to search a subtree of an ontology
 */
public class SearchOntology {


		public static void main(String[] args) throws OntologyServiceException, URISyntaxException {
			// Create a reference to the ontology
			// Use new File().toURI() for local files
			//URI uri = new URI("http://www.ebi.ac.uk/efo/efo.owl");
			URI uri = new File("C:\\Users\\elhaj\\Downloads\\go-plus.owl").toURI();


			// Load the ontology
			OntologyService os = new FileOntologyService(uri);
			os = new BioportalOntologyService();

			// Find all terms containing string membrane located under GO:0043227
			List<OntologyTerm> result = ((BioportalOntologyService) os).searchSubtree("1070", "GO:0043227", "membrane",
					SearchOptions.INCLUDE_PROPERTIES);

			// Warn if empty list
			if (result.size() == 0)
				System.out.println("Nothing returned!");

			// Print the terms
			for (OntologyTerm ot : result)
				System.out.println(ot);

		}
}
