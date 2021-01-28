package com.imageprocessing.rgbscheme;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.imageprocessing.model.Result;

import javax.imageio.ImageIO;

/**
 * Read sourceImage and find 3 most prevalent colors in RGB scheme and set
 * Result object
 *
 */
public class ImageProcessing {

	private static Logger LOGGER = Logger.getLogger(ImageProcessing.class.getName());


	public Result processImage(String sourceUrl) throws IOException {
		URL url = new URL(sourceUrl);
		BufferedImage image = ImageIO.read(url);
		if(image!=null) {
			Result result = process(image);
			result.setUrl(sourceUrl);
			return result;
		}else{
			return Result.builder().url(sourceUrl).firstColor("    ").secondColor("Image not found").build();
		}
	}

	/**
	 * Process image by checking the pixel count for the image and increment the
	 * rgb color count for pixel range
	 * 
	 * @param sourceImage
	 * @return result object
	 */

	public Result process(BufferedImage sourceImage) {
		Map<Integer, Integer> colorMap = new HashMap<Integer, Integer>();

		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();

		int pixelCount = width * height;

		int r, g, b;

		for (int i = 0; i < pixelCount; i++) {
			int row = i / width;
			int col = i % width;
			int rgb = sourceImage.getRGB(col, row);

			r = (rgb >> 16) & 0xFF;
			g = (rgb >> 8) & 0xFF;
			b = (rgb) & 0xFF;
			if (!(r > 250 && g > 250 && b > 250)) {

				Integer counter = colorMap.get(rgb);
				if (counter == null) {
					counter = 0;
				}

				colorMap.put(rgb, ++counter);
			}
		}

		return getTopPrevalentColors(colorMap);
	}

	/**
	 * Get top 3 prevalent colors for given colorMap
	 * 
	 * @param map
	 * @return result object with colors in hex format set in it
	 */
	private Result getTopPrevalentColors(Map<Integer, Integer> map) {
		Result result = new Result();
		List<Integer> top3PrevalentColors = map.entrySet().stream()
				.sorted((e2, e1) -> e1.getValue().compareTo(e2.getValue())).map(Map.Entry::getKey).limit(3)
				.collect(Collectors.toList());
		List<String> resultList = new LinkedList<>();
		for (Integer colorCode : top3PrevalentColors) {
			int[] rgb = getRGBArray(colorCode);
			resultList.add("#" + Integer.toHexString(0x100 | rgb[0]).substring(1)
					+ Integer.toHexString(0x100 | rgb[1]).substring(1)
					+ Integer.toHexString(0x100 | rgb[2]).substring(1));

		}
		result.setFirstColor(resultList.get(0));
		result.setSecondColor(resultList.get(1));
		result.setThirdColor(resultList.get(2));
		return result;
	}

	/**
	 * Get rgb color array
	 * @param rgb
	 * @return rgb array
	 */
	private int[] getRGBArray(int rgb) {
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = (rgb) & 0xFF;

		return new int[] { r, g, b };
	}
}
