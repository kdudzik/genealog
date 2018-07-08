package org.dudyngo.genealog.domain;

import java.net.URL;

import org.dudyngo.genealog.basia.RecordFiller;
import org.jsoup.nodes.Element;

public abstract class GenealogicalRecord {

	private String place;
	private RecordType type;
	private Integer year;
	private URL scanUrl;
	private URL url;

	public GenealogicalRecord(RecordType type) {
		this.type = type;
	}

	public abstract RecordFiller getBasiaRecordFiller(Element element);

	public abstract String getPersonalDataSummary();

	public static GenealogicalRecord forType(RecordType type) {
		switch (type) {
		case MARRIAGE:
			return new MarriageRecord();
		default:
			return new SinglePersonRecord(type);
		}
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPlace() {
		return place;
	}

	public void setType(RecordType type) {
		this.type = type;
	}

	public RecordType getType() {
		return type;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}

	public void setScanUrl(URL scanUrl) {
		this.scanUrl = scanUrl;
	}

	public URL getScanUrl() {
		return scanUrl;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public URL getUrl() {
		return url;
	}
}
