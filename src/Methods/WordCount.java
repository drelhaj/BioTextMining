package Methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WordCount {

	static int count = 0;
	public static void main(String[] args) throws IOException{
		
		File f2 = new File("C:\\Users\\elhaj\\Desktop\\pubMed\\new\\refCorpus");
		ArrayList<File> files2 = new ArrayList<File>(Arrays.asList(f2.listFiles()));
			for (int i=0; i<files2.size(); i++){
			if(files2.get(i).isFile()){
			
count+= wordCounter(files2.get(i).getAbsolutePath().toString());
System.out.println(count);
	}
			
			
			}
			
			System.out.println("Total word count = " + count);
			
	}
			
			public static int wordCounter(String fileName) throws IOException 
			{ 
				File file = new File(fileName);
				FileInputStream fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();

				String str = new String(data, "UTF-8");
				str = str.trim();
				   if (str.isEmpty())
				        return 0;
				    return str.split("\\W").length;

			}
}
