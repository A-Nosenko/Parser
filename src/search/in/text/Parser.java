package search.in.text;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains method to parse text.
 * 
 * @author Nosenko Anatolii
 * @version 11.11.2017
 */
public class Parser {
	
	private static final Logger LOG = Logger.getLogger(Parser.class.getName());
	
	/** Pattern to find address marked by "src = ..."	*/
	private static final String IMAGE_SRC_SIMPLE = "(src=\")([^\"]+)(\")";
	
	/** Pattern to find image link from google search result. */
	private static final String GOOGLE_IMAGE_LINK = "(\"ou\":\")([^\"]+)(\")";
	
	/**
	 * Parses text by default patterns to find images.
	 * 
	 * @param source Text to parse.
	 * @return Lines of links.
	 */
	public static String parse(String source) {
		LOG.info("There are " + source.split(System.lineSeparator()).length + " lines in source text.");
		StringBuilder builder = new StringBuilder();
		Pattern pattern = Pattern.compile(IMAGE_SRC_SIMPLE, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			builder.append(matcher.group(2));
			builder.append(System.lineSeparator());
		}
				
		pattern = Pattern.compile(GOOGLE_IMAGE_LINK, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(source);
		while (matcher.find()) {
			builder.append(matcher.group(2));
			builder.append(System.lineSeparator());
		}
		
		return (builder.length() == 0)?("No matches.."):(builder.toString());
	}
		
	/**
	 * Parses text by custom pattern.
	 * 
	 * @param source Text to parse.
	 * @param regex Pattern to find coincidence in text.
	 * @return Lines of concurrentes was found.
	 */
	public static String parse(String source, String regex) {
		LOG.info("There are " + source.split(System.lineSeparator()).length + " lines in source text.");
		StringBuilder builder = new StringBuilder();
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			builder.append(matcher.group());
			builder.append(System.lineSeparator());
		}
		
		return (builder.length() == 0)?("No matches.."):(builder.toString());
	}
}
