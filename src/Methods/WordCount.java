package Methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WordCount {

	static int count = 0;
	static int totalCount = 0;
	static PrintWriter writer;
	public static void main(String[] args) throws IOException{
		writer = new PrintWriter("E:\\ArabicSharedTask\\all\\allwords.txt","UTF-8");
		File f2 = new File("E:\\ArabicSharedTask\\all\\ReducedWords");
		ArrayList<File> files2 = new ArrayList<File>(Arrays.asList(f2.listFiles()));
			for (int i=0; i<files2.size(); i++){
			if(files2.get(i).isFile()){
			
count= wordCounter(files2.get(i).getAbsolutePath().toString());
totalCount += count;
System.out.println(files2.get(i).getName().toString()+"\t"+count);
	}
			
			
			}
			
			System.out.println("Total word count = " + totalCount);
			
	}
			
			public static int wordCounter(String fileName) throws IOException 
			{ 
				File file = new File(fileName);
				FileInputStream fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();

				String str = new String(data, "UTF-8");
				str = str.replaceAll("\\p{P}", "").replaceAll("\\d", "");
				String[] words = str.trim().split("\\s");
				for(String word:words){
					writer.println(word);
					writer.flush();
				}
				str = str.trim();
				   if (str.isEmpty())
				        return 0;
				    return str.trim().split("\\s").length;

			}
}
