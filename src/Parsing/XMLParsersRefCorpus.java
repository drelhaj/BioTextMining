package Parsing;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class XMLParsersRefCorpus {

	static PrintWriter writer, writer2;
	static String publicationLine="";
	static String journalTitle = "";	
	public static void main(String argv[]) throws NullPointerException {

		try {
			
			
			String[] journalTitles = readLines("C:\\Users\\elhaj\\Desktop\\pubMed\\journalTitles.txt");

			
			
			writer = new PrintWriter("C:\\Users\\elhaj\\Desktop\\pubMed\\pubmed_refCorpus.csv");
			writer.println("PMID" + "," + "Status" + "," + "Owner" + "," + "Year" + "," + "Month" + "," + "Day"
				   + "," + "ISSN"+ "," + "Volume"+ "," + "Journal Title" + "," + "ArticleTitle"+ "," + "Abstrtact"+ "," + "AuthorsAndAffiliations" + "," + "PubMedID"+ "," + "DOI"+ "," + "PII");
			writer.flush();
			File fXmlFile = new File("C:\\Users\\elhaj\\Desktop\\pubMed\\pubmed_result.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			////System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("PubmedArticle");

			//System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {// nList.getLength()

				Node nNode = nList.item(temp);

				//System.out.println("\nCurrent Element : " + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					// //System.out.println("Staff id : " +
					// eElement.getAttribute("id"));
					NodeList nListMedlineCitation = doc.getElementsByTagName("MedlineCitation");
					for (int i = 0; i < 1; i++) {

						Node nNodeMedlineCitation = nListMedlineCitation.item(i);

						//System.out.println("\nCurrent Element : " + nNodeMedlineCitation.getNodeName());
						String status = nListMedlineCitation.item(i).getAttributes().getNamedItem("Status").getNodeValue();
						String owner = nListMedlineCitation.item(i).getAttributes().getNamedItem("Owner").getNodeValue();
						String pmid = eElement.getElementsByTagName("PMID").item(0).getTextContent();
			            //System.out.println("Status : " + status);
			            //System.out.println("Owner : " + owner);
						//System.out.println("PMID : " + pmid);
						
						//writer.print(pmid + "," + status + "," + owner);
						publicationLine = publicationLine + pmid + "," + status + "," + owner; 
						
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
//							writer.print("," + year + "," + month + "," + day);
							publicationLine = publicationLine + "," + year + "," + month + "," + day; 

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
							String title = "---";
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
							

							try{	
								title = eElement.getElementsByTagName("Title").item(0).getTextContent();
							
							//System.out.println("Journal Title : " + title);
						} catch (NullPointerException npe) {
							title = "---";
						}
							
//							writer.print("," + issn +"," + volume + "," + title);
							publicationLine = publicationLine + "," + issn +"," + volume + "," + title;
							journalTitle = title;

						}
					} catch (NullPointerException npe) {
						//System.out.println(npe);
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
					
					
					
					//System.out.println("ArticleTitle : " + articleTitle);
					//System.out.println("AbstractText : " + abstractText);

					
					//writer.print(",\"" + articleTitle + "\",\"" + abstractText + "\"");
					publicationLine = publicationLine + ",\"" + articleTitle + "\",\"" + abstractText + "\"";
					
					
					System.out.println(temp);

					try{
					writer2 = new PrintWriter("C:\\Users\\elhaj\\Desktop\\pubMed\\new\\refCorpus\\"+(temp+2)+".txt");

					for(int x= 0; x<journalTitles.length; x++){
						if(journalTitle.equalsIgnoreCase(journalTitles[x])){
					writer2.println(articleTitle);
					writer2.println(abstractText);
					writer2.flush();
					break;
						}
					}
					} catch (NullPointerException npe) {
						System.out.println("");
					}	
					
					

					} catch (NullPointerException npe) {
						//System.out.println("");
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
						////System.out.println("Author : " + eElement.getElementsByTagName("Author").item(i2).getTextContent().trim().replaceAll(" +", " ").replaceAll("[\\t\\n\\r]",", ").replace(",  ,  ", ". "));

					}
						//System.out.println(authors);	
					//writer.print(",\"" + authors + "\"");
					publicationLine = publicationLine + ",\"" + authors + "\""; 
					} catch (NullPointerException npe) {
						//System.out.println(npe);
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
						publicationLine = publicationLine + "," + pubmedID + "," + doi +"," + pii; 

						
					}
					
					} catch (NullPointerException npe) {
						//System.out.println("");
					}	
					
					}
				}
				
				for(int x= 0; x<journalTitles.length; x++){
					if(journalTitle.equalsIgnoreCase(journalTitles[x])){
						System.out.println(publicationLine);
						writer.println(publicationLine);
						writer.flush();
						System.out.println("=========================================================================");
				break;
					}
				}
				publicationLine = "";
				journalTitle = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			lines.add(line.toLowerCase().trim());
		}
		
		bufferedReader.close();
		return lines.toArray(new String[lines.size()]);
	}
	
}
