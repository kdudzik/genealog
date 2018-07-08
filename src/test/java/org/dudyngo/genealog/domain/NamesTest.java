package org.dudyngo.genealog.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class NamesTest {

	@Test
	public void sex() {
		assertEquals(Sex.WOMAN, Names.sex("Maria"));
		assertEquals(Sex.WOMAN, Names.sex("Jadwiga"));
		assertEquals(Sex.MAN, Names.sex("Andrzej"));
		assertEquals(Sex.MAN, Names.sex("Bonawentura"));
		assertEquals(Sex.MAN, Names.sex("Jan"));
	}

	@Test
	public void convertSurnameToSex() {
		assertEquals("Kowalski", Names.convertSurnameToSex("Kowalski", Sex.MAN));
		assertEquals("Kowalski", Names.convertSurnameToSex("Kowalska", Sex.MAN));
		assertEquals("Kowalska", Names.convertSurnameToSex("Kowalski", Sex.WOMAN));
		assertEquals("Kowalska", Names.convertSurnameToSex("Kowalska", Sex.WOMAN));
		assertEquals("Zawadzka", Names.convertSurnameToSex("Zawadzki", Sex.WOMAN));
		assertEquals("Nawrocki", Names.convertSurnameToSex("Nawrocka", Sex.MAN));
		assertEquals("Krowa", Names.convertSurnameToSex("Krowa", Sex.MAN));
		assertEquals("Grygier", Names.convertSurnameToSex("Grygier", Sex.WOMAN));
	}
}
