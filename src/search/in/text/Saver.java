package search.in.text;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * Contains method to save images.
 * 
 * @author Nosenko Anatolii
 * @version 11.11.2017
 */
public class Saver {
	
	/** Variable to build filename. */
	private static int nameCount = 0;
	
	private static final Logger LOG = Logger.getLogger(Saver.class.getName());

	/** 
	 * Time out to wait if some exception has place.
	 * For example the site is overloaded.
	 */
	private static final long TIME_OUT = 1000;

	/** Folder to save images. */
	private static final String PATH = "images\\";
	
	/**
	 * Saves images from URL to folder.
	 * 
	 * @param address URL parser to connect.
	 * @param maxWidth Image limit to save.
	 * @param extension Image extension, default "jpg".
	 */
	public static void save(String address, int maxWidth, String extension) {
		
		BufferedImage image = null;
		String name = null;
		try {
			URL url = new URL(address);
			image = ImageIO.read(url);
			extension = (extension == null) ? "jpg" : extension;
			name = nameCount++ + "." + extension ;
			if (image != null && image.getWidth() >= maxWidth && image.getHeight() >= maxWidth) {
				ImageIO.write(image, extension, new File(PATH + name));
			}
		} catch (IOException e) {
			LOG.info(e.getMessage());
			try {
				Thread.sleep(TIME_OUT);
			} catch (InterruptedException e1) {
				LOG.info(e1.getMessage());
			}
		}
		LOG.info(name);
	}
}
