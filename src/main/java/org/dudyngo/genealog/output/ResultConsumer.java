package org.dudyngo.genealog.output;

import java.util.Collection;

import org.dudyngo.genealog.domain.GenealogicalRecord;

public interface ResultConsumer {
	void consume(Collection<GenealogicalRecord> records);
}
