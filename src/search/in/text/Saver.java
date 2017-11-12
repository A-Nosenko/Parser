package search.in.text;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * Contains method to save images.
 * 
 * @author Nosenko Anatolii
 * @version 11 November 2017
 */
public class Saver {
	
	/** Variable to build filename. */
	private static int nameCount = 0;
		
	private static final Logger LOG = Logger.getLogger(Saver.class.getName());

	/** Folder to save images. */
	private static final String PATH = "images\\";
	
	/**
	 * Saves images from URL to folder.
	 * 
	 * @param address URL parser to connect.
	 * @param extension Image extension, default "jpg".
	 * @param image Image to save.
	 */
	public static void save(String address, String extension, BufferedImage image) {
			
		String name = null;
		boolean flag = true;
		try {
			if (extension != null) {
				name = nameCount++ + "." + extension ;
				ImageIO.write(image, extension, new File(PATH + name));
			} else {
				while(flag) {
					for(String extensionTest : ImageIO.getReaderFileSuffixes()) {
						LOG.info(extensionTest);
						if (ImageIO.write(image, extensionTest, new File(PATH + name))) {
							System.out.println(extensionTest);
							flag = false;
						}
					}
				}
			}
		
			ImageIO.write(image, extension, new File(PATH + name));
		} catch (IOException e) {
			LOG.info(e.getMessage());
		}
		LOG.info(name);
	}
}
