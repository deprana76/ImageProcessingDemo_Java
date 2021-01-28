package com.imageprocessing.worker;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import com.imageprocessing.model.Result;
import com.imageprocessing.rgbscheme.ImageProcessing;

/**
 * Task executor reading the source image from url and doing image processing on
 * it
 *
 */
public class ParallelTask implements Callable<Result> {

	private String sourceUrl = null;
	
	private ImageProcessing imageProcessing;

	public ParallelTask(String sourceUrl) {
		this.sourceUrl = sourceUrl;
		imageProcessing = new ImageProcessing();
	}

	@Override
	public Result call() throws Exception {
		return imageProcessing.processImage(sourceUrl);
	}

}
