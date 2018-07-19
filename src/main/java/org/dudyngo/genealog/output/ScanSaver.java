package org.dudyngo.genealog.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;

import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.szukajwarchiwach.SwaLinkExtractor;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class ScanSaver implements ResultConsumer {
	private final Path directory;

	public ScanSaver(Path directory) {
		this.directory = directory;
	}

	@Override
	public void consume(GenealogicalRecord record) {
		if (record.getScanUrl() != null) {
			downloadScan(record);
		}
	}

	private void downloadScan(GenealogicalRecord record) {
		Path path = getFilenameForRecord(record);
		Optional<URL> imageUrl = SwaLinkExtractor.findImageUrl(record.getScanUrl());

		if (!imageUrl.isPresent()) {
			failedToDownload(record);
			return;
		}

		try {
			Response response = Jsoup.connect(imageUrl.get().toString())
					.validateTLSCertificates(false)
					.ignoreContentType(true)
					.maxBodySize(Integer.MAX_VALUE)
					.execute();

			File outputFile = Files.createFile(path).toFile();
			System.out.println(String.format("Downloading %s...", outputFile.getName()));
			try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
				outputStream.write(response.bodyAsBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
			failedToDownload(record);
		}
	}

	private Path getFilenameForRecord(GenealogicalRecord record) {
		String coreFilename = filenameFor(record);
		Path candidate = Paths.get(directory.toString(), String.format("%s.jpg", coreFilename));

		int suffix = 2;
		while (candidate.toFile().exists()) {
			candidate = Paths.get(directory.toString(), String.format("%s %d.jpg", coreFilename, suffix++));
		}
		return candidate;
	}

	private void failedToDownload(GenealogicalRecord record) {
		System.err.println(String.format("Failed to download scan for %s", record.getPersonalDataSummary()));
	}

	private String filenameFor(GenealogicalRecord record) {
		return String.format("%s %s %s",
				record.getType().getSymbol(), record.getYear(), record.getPersonalDataSummary());
	}
}
