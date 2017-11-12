package search.in.text;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * Class to start application.
 * 
 * @author Nosenko Anatolii
 * @version 11 November 2017
 */
public class Start {

	private static final Logger LOG = Logger.getLogger(Start.class.getName());
	private static final String URL_DEFINITION = "Ведите URL сайта: ";
	private static final String PATTERN_DEFINITION =
			"Ведите регулярное выражение или нажмите Enter чтобы пропустить и сохранить изображения: ";
	private static final String EMPTY_LINE = "";
	private static final String RECEIVED = "Получено регулярное выражение: ";
	private static final String PARSING = " Идет парсинг...";
	private static final String RESTRACTION_MIN_WIDTH =
			"Ведите минимальную высоту изображения в пикселях \n или Enter чтобы пропустить ";
	private static final String RESTRACTION_MAX_WIDTH =
			"Ведите максимальную высоту изображения в пикселях \n или Enter чтобы пропустить ";
	private static final String RESTRACTION_MIN_HIGH =
			"Ведите минимальную ширину изображения в пикселях \n или Enter чтобы пропустить ";
	private static final String RESTRACTION_MAX_HIGH =
			"Ведите максимальную ширину изображения в пикселях \n или Enter чтобы пропустить ";
	private static final String IMAGE_PARSING = "Парсинг с сохранением изображений с параметрами: ";
	
	private static final String OUT_FILE = "out.txt";
	private static final String PATTERN_TO_SPLIT = "\\.";
	private static final String READY = "\n\n\n\t\t\t READY! \n\n\n";
	
	/** 
	 * Time out to wait if some exception has place.
	 * For example the site is overloaded.
	 */
	private static final long TIME_OUT = 1000;
		
	/**
	 * Entry point. Makes dialog with user.
	 * 
	 * @param str Command line arguments.
	 */
	public static void main(String ... str) {
		
		BufferedImage image = null;
		
		String minWidth = null;
		String maxWidth = null;
		String minHigh = null;
		String maxHigh = null;
		
		Integer minW = 0;
		Integer maxW = Integer.MAX_VALUE;;
		Integer minH = 0;
		Integer maxH = Integer.MAX_VALUE;
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		while (true) {
			System.out.println(URL_DEFINITION);
			String addressURL = in.nextLine();
			System.out.println(PATTERN_DEFINITION);
			String regex = in.nextLine();
			if (regex != null && !regex.equals(EMPTY_LINE)) {
				System.out.println(RECEIVED + regex + PARSING);
			} else {
				regex = null;
			}
						
			if (regex == null) {
				System.out.println(RESTRACTION_MIN_WIDTH);
				minWidth = in.nextLine();
				System.out.println(RESTRACTION_MAX_WIDTH);
				maxWidth = in.nextLine();
				System.out.println(RESTRACTION_MIN_HIGH);
				minHigh = in.nextLine();
				System.out.println(RESTRACTION_MAX_HIGH);
				maxHigh = in.nextLine();
				
				try {
					if (minWidth != null && !minWidth.equals(EMPTY_LINE)) {
						minW = Integer.parseInt(minWidth);
					}
					if (maxWidth != null && !maxWidth.equals(EMPTY_LINE)) {
						maxW = Integer.parseInt(maxWidth);
					}
					if (minHigh != null && !minHigh.equals(EMPTY_LINE)) {
						minH = Integer.parseInt(minHigh);
					}
					if (maxHigh != null && !maxHigh.equals(EMPTY_LINE)) {
						maxH = Integer.parseInt(maxHigh);
					}
					System.out.println(IMAGE_PARSING +
							" (" + minW + " - " + maxW + ") X (" + minH + " - " + maxH + ")" );
				} catch (Exception e) {
					LOG.info(e.getMessage());
				}
			}		
			
			String sourceLine = URLReader.read(addressURL);	
			
			String addresses = 
					(regex != null)	? Parser.parse(sourceLine, regex) : Parser.parse(sourceLine);
			MyWriter.write(new File(OUT_FILE), addresses);
			if (regex == null) {
				
				for(String address : addresses.split(System.lineSeparator())) {
					
					try {
						URL url = new URL(address);
						image = ImageIO.read(url);
						if (ImageValidator.validate(minW, maxW, minH, maxH, image)) {
							String[] addressDisassebbled = address.split(PATTERN_TO_SPLIT);
							String extension = null;
							if(addressDisassebbled.length > 1) {
								extension = addressDisassebbled[addressDisassebbled.length - 1];
							}
							Saver.save(address, extension, image);
						}
					} catch (IOException e) {
						LOG.info(e.getMessage());
						try {
							Thread.sleep(TIME_OUT);
						} catch (InterruptedException e1) {
							LOG.info(e1.getMessage());
						}
					}
				}
			}
			
			System.out.println(READY);
		}
	}
}
