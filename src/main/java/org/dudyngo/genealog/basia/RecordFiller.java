package org.dudyngo.genealog.basia;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.domain.RecordType;
import org.jsoup.nodes.Element;

public abstract class RecordFiller {

	private static final String YEAR_REGEX = "(?:rok|inne)\\s+(\\d+)";
	final Element container;
	final Element leftBox;
	final Element rightBox;
	final GenealogicalRecord record;

	RecordFiller(GenealogicalRecord record, Element container) {
		this.record = record;
		this.container = container;
		this.leftBox = extractLeftBox();
		this.rightBox = extractRightBox();
	}

	static GenealogicalRecord parse(Element container) {
		RecordType type = extractRecordType(container);
		GenealogicalRecord record = GenealogicalRecord.forType(type);
		RecordFiller parser = record.getBasiaRecordFiller(container);
		parser.extractCommonAttributes();
		parser.extractPersonalData();
		return parser.getRecord();
	}

	private GenealogicalRecord getRecord() {
		return record;
	}

	private static RecordType extractRecordType(Element element) {
		String typeClass = element.classNames()
				.stream()
				.filter(className -> className.contains("usc"))
				.findFirst()
				.orElse("");
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

	private void extractCommonAttributes() {
		String place = extractPlace(leftBox);
		URL url = extractRecordUrl(rightBox);
		Optional<Integer> year = extractYear(leftBox.text());
		Optional<URL> scanUrl = extractScanUrl(rightBox);

		record.setPlace(place);
		record.setUrl(url);
		year.ifPresent(record::setYear);
		scanUrl.ifPresent(record::setScanUrl);
	}

	abstract void extractPersonalData();

	private Element extractLeftBox() {
		return container.getElementsByClass("lbox").first();
	}

	private Element extractRightBox() {
		return container.getElementsByClass("rbox").first();
	}

	private URL extractRecordUrl(Element rightBox) {
		String path = rightBox
				.getElementsByAttributeValueStarting("id", "link")
				.first()
				.attr("href");
		URL baseUrl = BasiaWebClient.getBaseUrl();
		try {
			return new URL(baseUrl.getProtocol(), baseUrl.getHost(), path);
		} catch (MalformedURLException e) {
			return null;
		}
	}

	private String extractPlace(Element leftBox) {
		return leftBox.getElementsByTag("a").first().text().trim();
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
		Pattern pattern = Pattern.compile(YEAR_REGEX);
		Matcher matcher = pattern.matcher(placeSuffix);
		if (matcher.find()) {
			return Optional.of(Integer.parseInt(matcher.group(1)));
		} else {
			return Optional.empty();
		}
	}
}
