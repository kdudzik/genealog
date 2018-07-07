package org.dudyngo.genealog.output;

import static com.google.common.collect.Lists.newArrayList;

import java.io.PrintStream;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;

import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.szukajwarchiwach.SwaLinkExtractor;

import com.google.common.base.Joiner;

public class ResultPrinter {
	private final PrintStream outputStream;

	public ResultPrinter(PrintStream out) {
		this.outputStream = out;
	}

	public void printResults(Collection<GenealogicalRecord> records) {
		records.stream()
				.map(this::recordSummary)
				.forEach(outputStream::println);
		outputStream.println(records.size() + " result(s)");
	}

	private String recordSummary(GenealogicalRecord record) {
			String out = Joiner.on(" ").skipNulls()
					.join(newArrayList(record.getYear(),
							record.getPlace(),
							record.getType().getSymbol(),
							record.getFirstName(),
							record.getParents()));

			if (record.getScanUrl() != null) {
				Optional<URL> imageUrl = SwaLinkExtractor.findImageUrl(record.getScanUrl());
				out += "\n" + imageUrl.orElse(record.getScanUrl()).toString();
			}
			out += "\n";
			return out;
	}
}
