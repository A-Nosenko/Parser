package search.in.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

/**
 * Contains method to read page by URL.
 * 
 * @author Nosenko Anatolii
 * @version 11 November 2017
 */
public class URLReader {
	/**
	 * @param path URL to read.
	 * @return Text the page HTML.
	 */
	public static String read(String path) {
		BufferedReader in;
		StringBuilder builder = new StringBuilder();
		try {
				
		URLConnection connection = new URL(path).openConnection();
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();

		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = "";
		while (line != null) {
			line = in.readLine();
			builder.append(line);
			builder.append(System.lineSeparator());
		}
		in.close();
		} catch (IOException e) {
			final Logger log = Logger.getLogger(URLReader.class.getName()); 
			log.info(e.getMessage());
		}
		return builder.toString();
	} 
}
