# Gene Ontology Semantic Tagger (GOST)

In many areas of academic publishing, there is an explosion of literature, and sub-division of fields into subfields, leading to stove-piping
where sub-communities of expertise become disconnected from each other. This is especially true in the genetics literature over the last
10 years where researchers are no longer able to maintain knowledge of previously related areas. This paper extends several approaches
based on natural language processing and corpus linguistics which allow us to examine corpora derived from bodies of genetics literature
and will help to make comparisons and improve retrieval methods using domain knowledge via an existing gene ontology. We derived
two open access medical journal corpora from PubMed related to psychiatric genetics and immune disorder genetics. We created a
novel Gene Ontology Semantic Tagger (GOST) and lexicon to annotate the corpora and are then able to compare subsets of literature
to understand the relative distributions of genetic terminology, thereby enabling researchers to make improved connections between them.

# A collaborative Project

The Gene Ontology (GO) project is a collaborative effort to
address the need for consistent descriptions of gene products across databases. The go-basic.obo is the basic version
of the GO ontology, filtered such that the graph is guaranteed to be acyclic paths, and annotations can be propagated
up the graph. We focused on the is_a relation in order to
trace ancestors and children for each entry in the ontology.
We chose the is_a relationship in the first instance because
it has a more intuitive meaning. Something is only considered is_a if an instance of the child process is an instance
of the entire parent process.
To parse the OBO file we created Java code that combines
the use of publicly available OBO library10 with Java Directed Graph (Digraphs) to trace the paths from a node child
to the root. The code used Breadth First and Depth First
algorithms to quickly and accurately extract the paths. Figure 1 shows an example of a directed graph for the basophil
homeostasis GO entry. The figure shows two paths starting
from the child entry up to the biological process root.

# What does GOST Do?

Our code allowed us to generate a USAS tagger dictionary
file where each entry in the OBO ontology is tagged with
the GO IDs shown in its path. Taking the “mucosal immune response” OBO entry shown in Figure 1 we can see
there are two paths starting from the child node towards the
“biological process” root. The dictionary creation process
works as follows:

  * 1. determine whether the child node is single word or multi-word expression. The example shows the latter.
  * 2. determine the number of paths towards the root.
  * 3. get each path’s GoID entries (child node’s ancestors)
  * 4. include the level of each ancestor by adding that to the end of each entry (e.g. .1 to refer to the first parent (GOO:0002251).
  * 5. determine whether the path passes through an “immune system process”, which is the one with GoID: 0002376. If so we add .I to the end of the GoID tag to refer to immune entry, other


# Publication
* Please cite out publications if you end up using our code, thanks:

  * El-Haj, M. , Rayson, P., Piao, S., and Knight, Jo.. "Profiling Medical Journal Articles Using a Gene Ontology Semantic Tagger". The 11th edition of the Language Resources and Evaluation Conference (LREC'18). May 2018. Miyazaki, Japan https://www.lancaster.ac.uk/staff/elhaj/docs/706_Paper.pdf

  * El-Haj, M. , Nathan Rutherford, Matthew Coole, Ignatius Ezeani, Sheryl Prentice, Nancy Ide, Jo Knight, Scott Piao, John Mariani, Paul Rayson and Keith Suderman "Infrastructure for Semantic Annotation in the Genomics Domain". 12th edition of the Language Resources and Evaluation Conference (LREC'20). May 2020. Marseille, France https://www.lancaster.ac.uk/staff/elhaj/docs/genomics.pdf

  * Sheryl Prentice; Jo Knight; Paul Rayson; El-Haj, M. and Nathan Rutherford. " Problematising Characteristicness: A Biomedical Association Case Study". In the IJCL Journal (International Journal of Corpus Linguistics) https://www.lancaster.ac.uk/staff/elhaj/docs/ijcl.19019.pre.pdf
