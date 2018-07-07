package org.dudyngo.genealog.basia;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BasiaWebClient {
	private static final String HOST = "http://www.basia.famula.pl";
	public static final int TIMEOUT_SECONDS = 300;

	public Optional<Document> getResults(BasiaParameters parameters) {
		try {
			Document response = prepareRequest(parameters).post();
			return Optional.of(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	private Connection prepareRequest(BasiaParameters parameters) {
		return Jsoup.connect(HOST + "/pl/")
				.data(parameters.toMap())
				.timeout(TIMEOUT_SECONDS * 1000);
	}
}
