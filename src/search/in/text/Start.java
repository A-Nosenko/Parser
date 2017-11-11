package search.in.text;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class to start application.
 * 
 * @author Nosenko Anatolii
 * @version 11.11.2017
 */
public class Start {

	private static final Logger LOG = Logger.getLogger(Start.class.getName());
	private static final String URL_DEFINITION = "Ведите URL сайта: ";
	private static final String PATTERN_DEFINITION =
			"Ведите регулярное выражение или нажмите Enter чтобы пропустить и сохранить изображения: ";
	private static final String EMPTY_LINE = "";
	private static final String RECEIVED = "Получено регулярное выражение: ";
	private static final String PARSING = " Идет парсинг...";
	private static final String RESTRACTION =
			"Ведите минимальную высоту/ширину изображения в пикселях \n или Enter чтобы сохранить все ";
	private static final String IMAGE_PARSING = "Парсинг с сохранением изображений у которых высота и ширина превышают ";
	private static final String PIXELS = " пикселей ";
	private static final String ALL_IMAGES_PARSING = "Парсинг с сохранением всех изображений..";
	private static final String OUT_FILE = "out.txt";
	private static final String PATTERN_TO_SPLIT = "\\.";
	private static final String READY = "\n\n\n\t\t\t READY! \n\n\n";
		
	/**
	 * Entry point. Makes dialog with user.
	 * 
	 * @param str Command line arguments.
	 */
	public static void main(String ... str) {
		
		while (true) {
			Scanner in = new Scanner(System.in);
			System.out.println(URL_DEFINITION);
			String addressURL = in.nextLine();
			System.out.println(PATTERN_DEFINITION);
			String regex = in.nextLine();
			if (regex != null && !regex.equals(EMPTY_LINE)) {
				System.out.println(RECEIVED + regex + PARSING);
			} else {
				regex = null;
			}
			String maxWidth = null;
			if (regex == null) {
				System.out.println(RESTRACTION);
				maxWidth = in.nextLine();
			}		
						 
			int max = 0;
			try {
				if (maxWidth != null && !maxWidth.equals(EMPTY_LINE)) {
					max = Integer.parseInt(maxWidth);
					System.out.println(IMAGE_PARSING + max + PIXELS);
				}
			} catch (Exception e) {
				LOG.info(ALL_IMAGES_PARSING);
			}
			String sourceLine = URLReader.read(addressURL);
			
			
			String addresses = 
					(regex != null)	? Parser.parse(sourceLine, regex) : Parser.parse(sourceLine);
			MyWriter.write(new File(OUT_FILE), addresses);
			if (regex == null) {
				for(String address : addresses.split(System.lineSeparator())) {
					String[] addressDisassebbled = address.split(PATTERN_TO_SPLIT);
					String extention = null;
					if(addressDisassebbled.length > 1) {
						extention = addressDisassebbled[addressDisassebbled.length - 1];
						Saver.save(address, max, extention);
					}
				}
			}
			//in.close();content="[^\"]*\"
			System.out.println(READY);
		}
	}
}
