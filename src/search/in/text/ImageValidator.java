package search.in.text;

import java.awt.image.BufferedImage;

/**
 * Contains method to check image parameters.
 * 
 * @author Nosenko Anatolii
 * @version 12 November 2017
 */
public class ImageValidator {
	/**
	 * @param minW Min number of pixels in width.
	 * @param maxW Max number of pixels in width.
	 * @param minH Min number of pixels in heigth.
	 * @param maxH Max number of pixels in heigth.
	 * @param image Image to check.
	 * @return True if image parameters confirmed.
	 */
	public static boolean validate (Integer minW, Integer maxW, Integer minH,
			Integer maxH, BufferedImage image) {
		int width = image.getWidth();
		int hight = image.getHeight();
		if ((width >= minW) && (width <= maxW)
				&& (hight >= minH) && (hight <= maxH)) {
			return true;
		}
			
		return false;
	}
}
