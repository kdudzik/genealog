package org.dudyngo.genealog.basia;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class BasiaWebClient {
	private static final String HOST = "http://www.basia.famula.pl";
	private static final int TIMEOUT_SECONDS = 300;

	static URL getBaseUrl() {
		try {
			return new URL(HOST);
		} catch (MalformedURLException e) {
			return null;
		}
	}

	Optional<Document> getResults(BasiaParameters parameters) {
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
