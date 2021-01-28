package com.imageprocessing.worker;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.imageprocessing.constants.Constants;
import com.imageprocessing.model.Result;

/**
 * Worker thread to assign tasks to worker threads to executors using callables
 *
 */
public class WorkerThread {
	
	private static Logger LOGGER = Logger.getLogger(WorkerThread.class.getName());

	/**
	 * Single worker executor which assigns worker threads to executor pool
	 * @param stream
	 */
	public void worker(Stream<String> stream) {
		List<Result> resultList = processImages(stream);
		saveToCSV(resultList);
	}

	public List<Result> processImages(Stream<String> stream){
		ExecutorService pool = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());

		List<Future<Result>> futures = new ArrayList<>();
		Iterable<String> iterable = stream::iterator;
		for (String sourceUrl : iterable) {
			futures.add(pool.submit(new ParallelTask(sourceUrl)));
		}
		List<Result> resultList = new LinkedList<>();
		for (Future<Result> future : futures) {
			try {
				resultList.add(future.get());
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.error(e);
			}
		}
		pool.shutdown();
		return resultList;
	}

	/**
	 * Save Result to CSV file
	 * @param resultList
	 */
	public void saveToCSV(List<Result> resultList) {
		try (PrintWriter out = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Constants.OUTPUT_FILE, true))))) {

			for (Result result : resultList) {
				StringBuffer output = new StringBuffer();
				output.append(result.getUrl());
				output.append(",");
				output.append(result.getFirstColor());
				output.append(",");
				output.append(result.getSecondColor());
				output.append(",");
				output.append(result.getThirdColor());
				out.println(output);
				LOGGER.debug(output);
			}
		} catch (IOException e) {
			LOGGER.error("Output file could not be loaded...", e);
		}
	}
}
