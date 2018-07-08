package org.dudyngo.genealog.output;

import static com.google.common.collect.Lists.newArrayList;

import java.io.PrintStream;
import java.net.URL;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.domain.RecordType;
import org.dudyngo.genealog.szukajwarchiwach.SwaLinkExtractor;

import com.google.common.base.Joiner;

public class ResultPrinter implements ResultConsumer {
	private final PrintStream printStream;

	public ResultPrinter(PrintStream out) {
		this.printStream = out;
	}

	@Override
	public void consume(Collection<GenealogicalRecord> records) {
		records.stream()
				.filter(record -> record.getType() != RecordType.MARRIAGE)
				.sorted(
						Comparator.comparing(GenealogicalRecord::getPlace)
							.thenComparingInt(GenealogicalRecord::getYear))
				.map(this::recordSummary)
				.forEach(printStream::println);
		printStream.println(records.size() + " result(s)");
	}

	private String recordSummary(GenealogicalRecord record) {
		List<String> lines = newArrayList();
		lines.add(Joiner.on(" ")
				.skipNulls()
				.join(newArrayList(record.getYear(),
						record.getPlace(),
						record.getType().getSymbol())));

		lines.add(record.getPersonalDataSummary());
		lines.add(record.getUrl().toString());

		URL scanUrl = record.getScanUrl();
		if (scanUrl != null) {
			Optional<URL> imageUrl = SwaLinkExtractor.findImageUrl(scanUrl);
			lines.add(imageUrl.orElse(scanUrl).toString());
		}

		lines.add("");
		return Joiner.on("\n").join(lines);
	}
}
