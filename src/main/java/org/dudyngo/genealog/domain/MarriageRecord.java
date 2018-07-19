package org.dudyngo.genealog.domain;

import org.dudyngo.genealog.basia.MarriageRecordFiller;
import org.dudyngo.genealog.basia.RecordFiller;
import org.jsoup.nodes.Element;

public class MarriageRecord extends GenealogicalRecord {
	private Person husband;
	private Person wife;

	MarriageRecord() {
		super(RecordType.MARRIAGE);
	}

	public Person getHusband() {
		return husband;
	}

	public void setHusband(Person husband) {
		this.husband = husband;
	}

	public Person getWife() {
		return wife;
	}

	public void setWife(Person wife) {
		this.wife = wife;
	}

	@Override
	public RecordFiller getBasiaRecordFiller(Element element) {
		return new MarriageRecordFiller(this, element);
	}

	@Override
	public String getPersonalDataDetails() {
		return "Marriage details";
	}

	@Override
	public String getPersonalDataSummary() {
		return "Marriage";
	}
}
