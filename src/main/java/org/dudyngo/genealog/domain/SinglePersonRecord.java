package org.dudyngo.genealog.domain;

import org.dudyngo.genealog.basia.RecordFiller;
import org.dudyngo.genealog.basia.SinglePersonRecordFiller;
import org.jsoup.nodes.Element;

import com.google.common.base.Joiner;

public class SinglePersonRecord extends GenealogicalRecord {
	private Person person;

	public SinglePersonRecord(RecordType type) {
		super(type);
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public RecordFiller getBasiaRecordFiller(Element container) {
		return new SinglePersonRecordFiller(this, container);
	}

	@Override
	public String getPersonalDataSummary() {
		return Joiner.on(" - ")
				.skipNulls()
				.join(
						person,
						person.getFather(),
						person.getMother()
				);
	}
}
