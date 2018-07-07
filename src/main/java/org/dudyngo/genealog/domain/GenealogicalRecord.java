package org.dudyngo.genealog.domain;

import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class GenealogicalRecord {

	private String place;
	private RecordType type;
	private String firstName;
	private String parents;
	private Integer year;
	private URL scanUrl;

	public GenealogicalRecord() {
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public String getParents() {
		return parents;
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
}
