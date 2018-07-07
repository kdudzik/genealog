package org.dudyngo.genealog;

import static java.util.Comparator.comparing;

import java.util.Collection;

import org.dudyngo.genealog.basia.BasiaConnector;
import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.output.ResultPrinter;

public class GenealogApplication {

	public static void main(String[] args) {
		printWelcomeMessage();

		BasiaConnector basia = new BasiaConnector();
		ResultPrinter printer = new ResultPrinter(System.out);

		Collection<GenealogicalRecord> resultList = basia.fetchBasiaRecords();
		printer.printResults(resultList);
	}

	private static void printWelcomeMessage() {
		System.out.println("Genealog by dudyngo");
	}

}
