package org.dudyngo.genealog;

import static java.util.Comparator.comparing;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.dudyngo.genealog.basia.BasiaConnector;
import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.domain.RecordType;
import org.dudyngo.genealog.output.ResultConsumer;
import org.dudyngo.genealog.output.ResultConsumerFactory;

public class GenealogApplication {

	public static void main(String[] args) {
		printWelcomeMessage();

		BasiaConnector basia = new BasiaConnector();

		Collection<GenealogicalRecord> resultList = basia.fetchBasiaRecords();
		ResultConsumerFactory resultConsumerFactory = new ResultConsumerFactory();
		List<ResultConsumer> consumers = resultConsumerFactory.getBasedOnConfig();

		resultList.stream()
				.sorted(
						Comparator.comparing(GenealogicalRecord::getPlace)
								.thenComparingInt(GenealogicalRecord::getYear))
				.forEach(result -> consumers.forEach(c -> c.consume(result)));

		System.out.println(resultList.size() + " result(s)");
	}

	private static void printWelcomeMessage() {
		System.out.println("Genealog by dudyngo");
	}

}
