package org.dudyngo.genealog;

import static java.util.Comparator.comparing;

import java.util.Collection;
import java.util.Optional;

import org.dudyngo.genealog.basia.BasiaConnector;
import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.output.ResultConsumer;
import org.dudyngo.genealog.output.ResultConsumerFactory;

public class GenealogApplication {

	public static void main(String[] args) {
		printWelcomeMessage();

		BasiaConnector basia = new BasiaConnector();

		Collection<GenealogicalRecord> resultList = basia.fetchBasiaRecords();

		ResultConsumerFactory resultConsumerFactory = new ResultConsumerFactory();
		Optional<ResultConsumer> consumer = resultConsumerFactory.getBasedOnConfig();
		consumer.ifPresent(c -> c.consume(resultList));
	}

	private static void printWelcomeMessage() {
		System.out.println("Genealog by dudyngo");
	}

}
