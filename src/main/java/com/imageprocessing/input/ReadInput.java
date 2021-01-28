package com.imageprocessing.input;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.imageprocessing.constants.Constants;

/**
 * Reading input file using NIO package and utilities associated to it
 *
 */
public class ReadInput {
	
	private static Logger LOGGER = Logger.getLogger(ReadInput.class.getName());
	
	/**
	 * Read input urls from input File
	 * @param limit
	 * @param skipVal
	 * @param inputFile
	 * @return String stream
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public Stream<String> readInput(Long limit, long skipVal, String inputFile) throws IOException, URISyntaxException {
		Stream<String> inputStream = null;
		LOGGER.debug("Reading input from file for limit " + limit + " skipping " + skipVal + " lines...");
		if (inputFile != null) {
			if (limit == null) {
				inputStream = Files.lines(Paths.get(inputFile)).skip(skipVal);
			} else {
				inputStream = Files.lines(Paths.get(inputFile)).skip(skipVal).limit(limit);
			}
		} else {
			if (limit == null) {
				inputStream = Files.lines(Paths.get(ClassLoader.getSystemResource(Constants.INPUT_FILE).toURI()))
						.skip(skipVal);
			} else {
				inputStream = Files.lines(Paths.get(ClassLoader.getSystemResource(Constants.INPUT_FILE).toURI()))
						.skip(skipVal).limit(limit);
			}
		}
		return inputStream;
	}

	/**
	 * Check if content available in inputFile for the limit provided
	 * @param limit
	 * @param inputFile
	 * @return true if content available else false
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public boolean checkIfContentAvailable(long limit, String inputFile) throws IOException, URISyntaxException {

		LOGGER.debug("Checking if content available in input file for limitRead of 50 lines...");
		boolean result = false;
		String lineToRead = null;

		if (inputFile != null) {
			try (Stream<String> lines = Files.lines(Paths.get(inputFile))) {
				lineToRead = lines.skip(limit).findFirst().orElse(null);
			}
		} else {
			try (Stream<String> lines = Files
					.lines(Paths.get(ClassLoader.getSystemResource(Constants.INPUT_FILE).toURI()))) {
				lineToRead = lines.skip(limit).findFirst().orElse(null);
			}
		}
		if (lineToRead != null && !lineToRead.isEmpty()) {
			LOGGER.debug("Content available for line " + limit);
			result = true;
		} else {
			LOGGER.debug("Content not vailable for line " + limit);
		}
		return result;
	}
}
