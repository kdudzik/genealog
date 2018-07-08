package org.dudyngo.genealog.basia;

import static java.util.stream.Collectors.toList;

import java.util.Collection;

import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class BasiaResultPageParser {

	Collection<GenealogicalRecord> parseDocument(Document document) {
		Elements results = document.body().getElementsByClass("result_box");
		return results.stream()
				.map(this::parseRecord)
				.collect(toList());
	}

	private GenealogicalRecord parseRecord(Element element) {
		return RecordFiller.parse(element);
	}

}
