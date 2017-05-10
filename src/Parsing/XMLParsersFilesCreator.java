package Parsing;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.PrintWriter;

public class XMLParsersFilesCreator {

	static PrintWriter writer;
	
	public static void main(String argv[]) throws NullPointerException {

		try {
			

			File fXmlFile = new File("C:\\Users\\elhaj\\Desktop\\pubMed\\QUERIESUPDATES\\immuneNEW.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("PubmedArticle");

			//System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {// nList.getLength()

				Node nNode = nList.item(temp);

				//System.out.println("\nCurrent Element : " + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					// System.out.println("Staff id : " +
					// eElement.getAttribute("id"));
					NodeList nListMedlineCitation = doc.getElementsByTagName("MedlineCitation");
					for (int i = 0; i < 1; i++) {

						Node nNodeMedlineCitation = nListMedlineCitation.item(i);

						//System.out.println("\nCurrent Element : " + nNodeMedlineCitation.getNodeName());
						String status = nListMedlineCitation.item(i).getAttributes().getNamedItem("Status").getNodeValue();
						String owner = nListMedlineCitation.item(i).getAttributes().getNamedItem("Owner").getNodeValue();
						String pmid = eElement.getElementsByTagName("PMID").item(0).getTextContent();
			           // System.out.println("Status : " + status);
			         //   System.out.println("Owner : " + owner);
					//	System.out.println("PMID : " + pmid);
						
						//writer.print(pmid + "," + status + "," + owner);
						
						NodeList nListDateCreated = doc.getElementsByTagName("DateCreated");
						for (int i2 = 0; i2 < 1; i2++) {// nList.getLength()

							Node nNodeDateCreated = nListDateCreated.item(i2);

							//System.out.println("\nCurrent Element : " + nNodeDateCreated.getNodeName());

							String year = eElement.getElementsByTagName("Year").item(0).getTextContent();
							String month = eElement.getElementsByTagName("Month").item(0).getTextContent();
							String day = eElement.getElementsByTagName("Day").item(0).getTextContent();
							//System.out.println("Year : " + year);
							//System.out.println("Month : " + month);
							//System.out.println("Day : " + day);
							if(year.equals(null))
								year = "---";
							if(month.equals(null))
								month = "---";
							if(day.equals(null))
								day = "---";
							//writer.print("," + year + "," + month + "," + day);
						}
						
						
						NodeList nListDateRevised = doc.getElementsByTagName("DateRevised");
						for (int i2 = 0; i2 < 1; i2++) {// nList.getLength()

							Node nNodeDateRevised = nListDateRevised.item(i2);

							//System.out.println("\nCurrent Element :" + nNodeDateRevised.getNodeName());

							//System.out.println("Year : " + eElement.getElementsByTagName("Year").item(0).getTextContent());
							//System.out.println("Month : " + eElement.getElementsByTagName("Month").item(0).getTextContent());
							//System.out.println("Day : " + eElement.getElementsByTagName("Day").item(0).getTextContent());

						}
						
				
					try{	
						NodeList nListJournal = doc.getElementsByTagName("Journal");
						for (int i2 = 0; i2 < 1; i2++) {// nList.getLength()

							Node nNodeJournal = nListJournal.item(i2);

							//System.out.println("\nCurrent Element :" + nNodeJournal.getNodeName());
							String issn = "---";
							String volume = "---";
try{							
	issn = eElement.getElementsByTagName("ISSN").item(0).getTextContent();
} catch (NullPointerException npe) {
	issn = "---";
}

try{							
	volume = eElement.getElementsByTagName("Volume").item(0).getTextContent();
} catch (NullPointerException npe) {
	volume = "---";
}
							
							//System.out.println("ISSN : " + issn);
							//System.out.println("Volume : " + volume);
							
							
							//writer.print("," + issn +"," + volume);

						}
					} catch (NullPointerException npe) {
						System.out.println(npe);
					}
					
					try{
					String articleTitle = "";
					String abstractText = "";

					
					try{							
						articleTitle = eElement.getElementsByTagName("ArticleTitle").item(0).getTextContent().replaceAll(" +", " ").replaceAll("[\\t\\n\\r]",", ").replace("\"", "");
					} catch (NullPointerException npe) {
						articleTitle = "---";
					}
					
					try{							
						abstractText = eElement.getElementsByTagName("AbstractText").item(0).getTextContent().replaceAll(" +", " ").replaceAll("[\\t\\n\\r]",", ").replace("\"", "");
					} catch (NullPointerException npe) {
						abstractText = "---";
					}
					
					
					
					System.out.println((temp+2));

					writer = new PrintWriter("C:\\Users\\elhaj\\Desktop\\pubMed\\QUERIESUPDATES\\immune\\"+(temp+2)+".txt");

					writer.println(articleTitle);
					writer.println(abstractText);
					writer.flush();

					} catch (NullPointerException npe) {
						System.out.println("");
					}		

					try{
						String authors = "";
					NodeList nListAuthorList = doc.getElementsByTagName("AuthorList");
					for (int i2 = 0; i2 < nListAuthorList.getLength(); i2++) {// nList.getLength()

//						Node nNodeAuthorList = nListAuthorList.item(i2);
						String auth =  "";
						try{
							auth = eElement.getElementsByTagName("Author").item(i2).getTextContent().trim().replaceAll(" +", " ").replaceAll("[\\t\\n\\r]",", ").replace(",  ,  ", ". ").replace("\"", "");
						} catch (NullPointerException npe) {
							i2 = nListAuthorList.getLength();
							auth = "---";
						}	
						int xx = i2 + 1;
						if(!auth.equals("---"))
						authors += "Author"+ (xx) + " : " + auth + " --- ";
						//System.out.println("Author : " + eElement.getElementsByTagName("Author").item(i2).getTextContent().trim().replaceAll(" +", " ").replaceAll("[\\t\\n\\r]",", ").replace(",  ,  ", ". "));

					}
						//System.out.println(authors);	
					//writer.print(",\"" + authors + "\"");
					} catch (NullPointerException npe) {
						System.out.println(npe);
					}	
					
					
					try{
					NodeList nListArticleIdList = doc.getElementsByTagName("ArticleIdList");
					for (int i2 = 0; i2 < 1; i2++) {// nList.getLength()

						Node nNodeArticleId = nListArticleIdList.item(i2);

						//System.out.println("\nCurrent Element :" + nNodeArticleId.getNodeName());
						String pubmedID = "";
						String doi = "";
						String pii = "";
						
						
						try{							
							pubmedID = eElement.getElementsByTagName("ArticleId").item(0).getTextContent();
						} catch (NullPointerException npe) {
							pubmedID = "---";
						}
						
						try{							
							doi = eElement.getElementsByTagName("ArticleId").item(1).getTextContent();
						} catch (NullPointerException npe) {
							doi = "---";
						}
						
						try{							
							pii = eElement.getElementsByTagName("ArticleId").item(2).getTextContent();
						} catch (NullPointerException npe) {
							pii = "---";
						}
						
						
						//System.out.println("ArticleId pubmed : " + pubmedID);
						//System.out.println("ArticleId doi : " + doi);
						//System.out.println("ArticleId pii : " + pii);


						//writer.print("," + pubmedID + "," + doi +"," + pii);
						
					}
					
					} catch (NullPointerException npe) {
						System.out.println("");
					}	
					
					}
				}
				
				//writer.println();
				//writer.flush();
				//System.out.println("=========================================================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
