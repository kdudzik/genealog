package org.dudyngo.genealog.output;

import java.util.Optional;

import org.dudyngo.genealog.GenealogConfig;

public class ResultConsumerFactory {

	private ResultConsumer getConsolePrinter() {
		return new ResultPrinter(System.out);
	}

	public Optional<ResultConsumer> getBasedOnConfig() {
		Optional<String> resultHandlerType = GenealogConfig.getInstance().getProperty("output.type");
		return resultHandlerType.map(this::getConsumerForType);
	}

	private ResultConsumer getConsumerForType(String type) {
		switch (type) {
			case "console":
				return getConsolePrinter();
			default:
				return null;
		}

	}
}
