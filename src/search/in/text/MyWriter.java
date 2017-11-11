package search.in.text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Logger;


/**
 * Contains method to text in file.
 * 
 * @author Nosenko Anatolii
 * @version 11.11.2017
 */
public class MyWriter {
	
	/**
	 * @param file Destination file to write text.
	 * @param line Text to save in file.
	 */
	public static void write(final File file, final String line) {
		final String encoding = "Cp1251";
		try (Writer writer = 
				new OutputStreamWriter(new FileOutputStream(file), encoding);) {			
			writer.write(line);
			
		} catch (IOException ex) {
			final Logger log = Logger.getLogger(MyWriter.class.getName());
			log.info(ex.getMessage());
		}
	}
}
