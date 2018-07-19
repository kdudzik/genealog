package org.dudyngo.genealog.output;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dudyngo.genealog.GenealogConfig;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ResultConsumerFactory {

	private ResultConsumer getConsolePrinter() {
		return new ResultPrinter(System.out);
	}

	private ResultConsumer getScanSaver(Path directory) {
		try {
			Files.createDirectories(directory);
			return new ScanSaver(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ResultConsumer> getBasedOnConfig() {
		Optional<String> property = GenealogConfig.getInstance().getProperty("output.type");
		return property
				.map(s -> Arrays.asList(s.split(",")))
				.orElseGet(ImmutableList::of)
				.stream()
				.map(this::getConsumerForType)
				.collect(toList());
	}

	private ResultConsumer getConsumerForType(String type) {
		switch (type) {
			case "console":
				return getConsolePrinter();
			case "saver":
				String directory = GenealogConfig.getInstance().getProperty("saver.outputdir")
						.orElseThrow(() -> new IllegalArgumentException("saver.outputdir property missing"));
				return getScanSaver(Paths.get(directory));
			default:
				return null;
		}

	}
}
