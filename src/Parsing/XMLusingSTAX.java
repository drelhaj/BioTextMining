package Parsing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.jsoup.Jsoup;

import java.nio.file.Path;
        
public class XMLusingSTAX {


 static String abstractText = "";
 static boolean read = false;
 static PrintWriter writer;
 static int count = 0;
            public static void main(String[] args) throws Exception {
           			
            	FileInputStream fstream = new FileInputStream("C:\\Users\\elhaj\\Desktop\\pubMed\\QUERIESUPDATES\\ref.xml");
            	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            	String strLine;

            	//Read File Line By Line
            	while ((strLine = br.readLine()) != null)   {
            	  // Print the content on the console
            	
                       String text = strLine.toString();
if(text.contains("<Abstract>")){
	read = true;
}

if(read == true){
    abstractText = abstractText + " " + text;
}

if(text.contains("</Abstract>")){
	read = false;
	String noHtml = Jsoup.parse(abstractText).text();	
	writer = new PrintWriter("C:\\Users\\elhaj\\Desktop\\pubMed\\QUERIESUPDATES\\ref\\"+(count++)+".txt");
	System.out.println(count);
	writer.println(noHtml);
	writer.flush();
	abstractText = "";
}
                        
            	
            }
        		

            }
            
}

        