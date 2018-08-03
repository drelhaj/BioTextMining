package Methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

	
	//removes HTML Tags
		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

		public static String removeTags(String string) {
			if (string == null || string.length() == 0) {
				return string;
			}

			Matcher m = REMOVE_TAGS.matcher(string);
			return m.replaceAll("");
		}

		
		
		
		// count number of words using white space delimiter
		public static int countWords(String in) {
			String trim = in.trim();
			if (trim.isEmpty())
				return 0;
			return trim.split("\\W+").length; // separate string by words. If spaces needed then trim.split("\\s+").length;
		}
		
		
		
		
		/**
		 * Reads text file into a string array (text file should have each token on
		 * a separate line)
		 * 
		 * @param filename
		 * @return
		 * @throws IOException
		 */
		public static String[] readLines(String filename) throws IOException {
			if(new File(filename).isFile()){
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			List<String> lines = new ArrayList<String>();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
			bufferedReader.close();
			return lines.toArray(new String[lines.size()]);
			}
			else{
				return null;
			}
		}

		// this methods saves time when counting frequency, it instead lookup items
		// in the HashMap that has been created using the countWordsFreq method
		public static int countKeyList(String[] keywords, HashMap<String, Integer> freq) {

			int totalCount = 0;
			for (String word : keywords) {
				if (freq.containsKey(word.toLowerCase())) {
					int count = freq.get(word.toLowerCase());
					totalCount += count;
				}
			}

			return totalCount;
		}

		/**
		 * countWords method takes text (string) as input and counts the frequency
		 * of each word in this text. countWords ignores digits and punctuation
		 * marks. Word's Delimiter used is a single-space.
		 * 
		 * @param text
		 *            (String).
		 * @return frequencies (HashMap).
		 */
		public static HashMap<String, Integer> countWordsFreq(String text) {
			String[] words = text.toLowerCase().split(" ");
			HashMap<String, Integer> frequencies = new HashMap<String, Integer>();
			for (String w : Arrays.asList(words)) {
				Integer num = frequencies.get(w.trim());
				if (num != null)
					frequencies.put(w.trim(), num + 1);
				else
					frequencies.put(w.trim(), 1);
			}

			return frequencies;
		}

		public static int countWordsRegEx(String[] keywords, String text) {
			int countMatches = 0;
	if(keywords == null || keywords.length<1){
		return 0;
	}
	else
			for (int i = 0; i < keywords.length; i++) {

				keywords[i] = "(?i)" + keywords[i].replace("#", "[a-z]{0,7}");

				// only search for lower case keyword (L)
				if (keywords[i].contains("(L)")) {
					keywords[i] = keywords[i].replace("(L)", "").replace("(?i)", "").toLowerCase();
				}
				// only search for upper case keyword (U)
				if (keywords[i].contains("(U)")) {
					keywords[i] = keywords[i].replace("(U)", "").replace("(?i)", "").toUpperCase();
				}
				// the keyword is case insensitive (LU)
				if (keywords[i].contains("(LU)")) {
					keywords[i] = keywords[i].replace("(LU)", "");
				}

				keywords[i] = keywords[i].replace(":", " : ");
				keywords[i] = keywords[i].replace("<", "\\s*((?:\\w+\\s*){");
				keywords[i] = keywords[i].replace(">", "})\\s*");
				keywords[i] = keywords[i].replace(" : ", ",");

				Pattern pattern = Pattern.compile("\\b(" + keywords[i].trim() + ")\\b", 1);

				// System.out.println(pattern);
				Matcher matcher = pattern.matcher(text);

				while (matcher.find()) {
					++countMatches;
				}

			}

			return countMatches;
		}
		
		
		
		public static String modifyDateLayout(String inputDate) throws ParseException {
			Date date;
			try
	         {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
	         }
			  catch (ParseException e)
	        {
	            return inputDate;
	        }
			return new SimpleDateFormat("dd MMMM yyyy").format(date);
		}
}
