package org.dudyngo.genealog.basia;

import java.util.Collection;

import org.dudyngo.genealog.GenealogConfig;
import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.domain.Places;
import org.jsoup.nodes.Document;

public class BasiaConnector {
	public Collection<GenealogicalRecord> fetchBasiaRecords() {
		BasiaWebClient client = new BasiaWebClient();
		Document document = client.getResults(readBasiaParameters())
				.orElseThrow(IllegalStateException::new);

		BasiaResultPageParser parser = new BasiaResultPageParser();
		return parser.parseDocument(document);
	}

	private BasiaParameters readBasiaParameters() {
		GenealogConfig config = GenealogConfig.getInstance();
		return BasiaParameters.builder()
				.setLastName(config.getProperty("basia.surname").orElseThrow(IllegalStateException::new))
				.setFirstName(config.getProperty("basia.name").orElse(null))
				.setYearFrom(Integer.valueOf(config.getProperty("basia.from").orElse("1700")))
				.setYearTo(Integer.valueOf(config.getProperty("basia.to").orElse("2018")))
				.setPlace(config.getProperty("basia.place").orElse(null))
				.setDistance(Integer.valueOf(config.getProperty("basia.distance").orElse("5")))
				.setSimilarity(Integer.valueOf(config.getProperty("basia.similarity").orElse("100")))
				.buildParameters();
	}
}
