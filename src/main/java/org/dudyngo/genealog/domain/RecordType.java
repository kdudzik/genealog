package org.dudyngo.genealog.domain;

public enum RecordType {
	BIRTH("U"),
	MARRIAGE("M"),
	DEATH("Z"),
	UNKNOWN("?");

	private final String symbol;

	RecordType(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
}
