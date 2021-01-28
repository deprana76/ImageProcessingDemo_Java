package com.imageprocessing;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.imageprocessing.input.ReadInput;
import com.imageprocessing.worker.WorkerThread;

/**
 * Main application
 */
public class Main {

    private static Logger LOGGER = Logger.getLogger(Main.class.getName());

    private ReadInput readInput = new ReadInput();

    private WorkerThread workerThread = new WorkerThread();

    private long limitToRead = 50;

    public static void main(String[] args) {

        Main main = new Main();
        main.run();
    }

    /**
     * Core function
     */
    private void run() {

        try {
            LOGGER.debug("Starting the application...");

            // First read input from command line args
            String inputFile = System.getProperty("inputFile");

            boolean isContentAvailable = readInput.checkIfContentAvailable(limitToRead, inputFile);

            // If content in input file is < limitToRead, then read the entire
            // file and process it.
            if (!isContentAvailable) {
                LOGGER.debug("Content in input file is < 50 lines, reading the entire file...");
                Stream<String> inputStream = readInput.readInput(null, 0, inputFile);
                workerThread.worker(inputStream);
            } else {
                // Content in inputFile is > limitToRead and process it in
                // incremental sequence approach
                long skipVal = 0;
                do {
                    // Read input
                    Stream<String> inputStream = readInput.readInput(limitToRead, skipVal, inputFile);
                    workerThread.worker(inputStream);
                    skipVal += limitToRead;
                } while (readInput.checkIfContentAvailable(skipVal, inputFile));
            }
        } catch (IOException | URISyntaxException e) {
            LOGGER.error(e);
        }
    }
}
