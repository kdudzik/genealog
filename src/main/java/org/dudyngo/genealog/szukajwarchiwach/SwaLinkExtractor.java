package org.dudyngo.genealog.szukajwarchiwach;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SwaLinkExtractor {
	public static Optional<URL> findImageUrl(URL scanUrl) {
		String imageId = scanUrl.getRef().replace("select", "");
		try {
			Document document = Jsoup.connect(scanUrl.toString()).validateTLSCertificates(false).get();
			String medium = document.getElementById("link_scan_" + imageId).dataset().get("medium");
			String big = medium.replace("/medium/", "/img/");
			return Optional.of(new URL(scanUrl.getProtocol(), scanUrl.getHost(), big));
		} catch (IOException e) {
			return Optional.empty();
		} catch (NullPointerException npe) {
			System.err.println(scanUrl.toString());
			return Optional.empty();
		}
	}
}
