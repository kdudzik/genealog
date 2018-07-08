package org.dudyngo.genealog.basia;

import org.dudyngo.genealog.domain.GenealogicalRecord;
import org.dudyngo.genealog.domain.MarriageRecord;
import org.dudyngo.genealog.domain.Person;
import org.jsoup.nodes.Element;

public class MarriageRecordFiller extends RecordFiller {

	public MarriageRecordFiller(GenealogicalRecord record, Element container) {
		super(record, container);
	}

	@Override
	public void extractPersonalData() {
		MarriageRecord marriageRecord = (MarriageRecord) record;
		marriageRecord.setHusband(extractHusband(container));
		marriageRecord.setWife(extractWife(container));
	}

	private Person extractHusband(Element container) {
		return null;
	}

	private Person extractWife(Element container) {
		return null;
	}

}
