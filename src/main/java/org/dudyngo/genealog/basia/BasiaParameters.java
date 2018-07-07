package org.dudyngo.genealog.basia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class BasiaParameters {

	private Optional<String> firstName;
	private String lastName;
	private Optional<Integer> similarity;
	private Optional<Integer> yearFrom;
	private Optional<Integer> yearTo;
	private Optional<String> place;
	private Optional<Integer> distance;

	public static BasiaParametersBuilder builder() {
		return new BasiaParametersBuilder();
	}

	public Map<String, String> toMap() {
		HashMap<String, String> map = Maps.newHashMap();

		map.put("fname0", firstName.orElse(""));
		map.put("lname0", lastName);
		map.put("sim0", String.valueOf(similarity.orElse(80)));
		map.put("od", String.valueOf(yearFrom.orElse(1700)));
		map.put("do", String.valueOf(yearTo.orElse(LocalDate.now().getYear())));
		map.put("placename", place.orElse(""));
		map.put("distance", String.valueOf(distance.orElse(5)));
		map.put("dateto", LocalDate.now().format(DateTimeFormatter.ISO_DATE));

		System.out.println(map);

		ImmutableMap bloatParams = ImmutableMap.builder()
				.put("search_ext", "szukaj")
				.put("datefrom", "2011-01-28")
				.put("sex0", "any")
				.put("type0", "any")
				.put("p_count", "1")
				.put("showplaces", "block")
				.put("type_unit", "any")
				.put("type_record", "any")
				.put("showtype", "none")
				.put("showdate", "none")
				.build();

		map.putAll(bloatParams);
		return ImmutableMap.copyOf(map);
	}

	private BasiaParameters(String firstName, String lastName, Integer similarity, Integer yearFrom,
			Integer yearTo, String place, Integer distance) {
		this.firstName = Optional.ofNullable(firstName);
		this.lastName = Preconditions.checkNotNull(lastName);
		this.similarity = Optional.ofNullable(similarity);
		this.yearFrom = Optional.ofNullable(yearFrom);
		this.yearTo = Optional.ofNullable(yearTo);
		this.place = Optional.ofNullable(place);
		this.distance = Optional.ofNullable(distance);
	}

	public static class BasiaParametersBuilder {
		private String firstName;
		private String lastName;
		private Integer similarity;
		private Integer yearFrom;
		private Integer yearTo;
		private String place;
		private Integer distance;

		public BasiaParametersBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public BasiaParametersBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public BasiaParametersBuilder setSimilarity(Integer similarity) {
			this.similarity = similarity;
			return this;
		}

		public BasiaParametersBuilder setYearFrom(Integer yearFrom) {
			this.yearFrom = yearFrom;
			return this;
		}

		public BasiaParametersBuilder setYearTo(Integer yearTo) {
			this.yearTo = yearTo;
			return this;
		}

		public BasiaParametersBuilder setPlace(String place) {
			this.place = place;
			return this;
		}

		public BasiaParametersBuilder setDistance(Integer distance) {
			this.distance = distance;
			return this;
		}

		public BasiaParameters buildParameters() {
			return new BasiaParameters(firstName, lastName, similarity, yearFrom, yearTo, place, distance);
		}
	}

}
