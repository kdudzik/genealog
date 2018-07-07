package org.dudyngo.genealog.basia;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.domain.RecordType;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BasiaResultPageParser {

	public static final String PARENTS_PREFIX = "rodzice:";
	public static final String OTHER_PERSONS_PREFIX = "Inne osoby";

	public Collection<GenealogicalRecord> parseDocument(Document document) {
		Elements results = document.body().getElementsByClass("result_box");
		return results.stream()
				.map(this::parseRecord)
				.collect(Collectors.toList());
	}

	private GenealogicalRecord parseRecord(Element element) {
		GenealogicalRecord record = new GenealogicalRecord();

		RecordType type = findRecordType(element);

		Element leftBox = element.getElementsByClass("lbox").first();
		Element rightBox = element.getElementsByClass("rbox").first();

		String place = leftBox.getElementsByTag("a").first().text().trim();
		String firstName = leftBox.getElementsByTag("b").first().text().trim();

		String leftBoxText = leftBox.text();
		Optional<String> parents = extractParents(leftBoxText);
		Optional<Integer> year = extractYear(leftBoxText);

		Optional<URL> scanUrl = extractScanUrl(rightBox);

		record.setType(type);
		record.setPlace(place);
		record.setFirstName(firstName);

		year.ifPresent(record::setYear);
		parents.ifPresent(record::setParents);
		scanUrl.ifPresent(record::setScanUrl);

		return record;
	}

	private Optional<URL> extractScanUrl(Element rightBox) {
		return Optional.ofNullable(
				rightBox
					.getElementsByAttributeValueContaining("href", "szukajwarchiwach")
					.first())
				.map(elem -> elem.attr("href"))
				.map(spec -> {
					try {
						return new URL(spec);
					} catch (MalformedURLException e) {
						return null;
					}
				});
	}

	private Optional<Integer> extractYear(String placeSuffix) {
		Pattern pattern = Pattern.compile("rok\\s+(\\d+)");
		Matcher matcher = pattern.matcher(placeSuffix);
		if (matcher.find()) {
			return Optional.of(Integer.parseInt(matcher.group(1)));
		} else {
			return Optional.empty();
		}
	}

	private Optional<String> extractParents(String leftBoxText) {
		int parentsIndex = leftBoxText.indexOf(PARENTS_PREFIX);
		int othersIndex = leftBoxText.indexOf(OTHER_PERSONS_PREFIX);

		if (parentsIndex == -1) {
			return Optional.empty();
		} else if (othersIndex == -1) {
			return Optional.of(leftBoxText.substring(parentsIndex + PARENTS_PREFIX.length()));
		} else {
			return Optional.of(leftBoxText.substring(parentsIndex + PARENTS_PREFIX.length(),
					othersIndex - 1));
		}
	}

	private RecordType findRecordType(Element element) {
		String typeClass = element.classNames()
				.stream()
				.filter(className -> className.contains("usc"))
				.findFirst()
				.orElse("unknown");
		switch (typeClass) {
		case "usca":
			return RecordType.BIRTH;
		case "uscb":
			return RecordType.MARRIAGE;
		case "uscc":
			return RecordType.DEATH;
		default:
			return RecordType.UNKNOWN;
		}
	}

}
