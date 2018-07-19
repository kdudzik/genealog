package org.dudyngo.genealog.basia;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import org.dudyngo.genealog.domain.Names;
import org.dudyngo.genealog.domain.Person;
import org.dudyngo.genealog.domain.Sex;
import org.dudyngo.genealog.domain.SinglePersonRecord;
import org.jsoup.nodes.Element;

public class SinglePersonRecordFiller extends RecordFiller {

	public SinglePersonRecordFiller(SinglePersonRecord record, Element container) {
		super(record, container);
	}

	@Override
	public void extractPersonalData() {
		Person person = new Person();

		Optional<Person> father = extractFather();
		Optional<Person> mother = extractMother();
		father.ifPresent(person::setFather);
		mother.ifPresent(person::setMother);

		String firstName = extractFirstName();

		Sex sex = Names.sex(firstName);
		person.setSex(sex);

		Optional<String> lastName = Stream.of(father, mother)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(p -> Names.convertSurnameToSex(p.getLastName(), sex))
				.findFirst();
		lastName.ifPresent(person::setLastName);

		if (lastName.isPresent() && firstName.endsWith(lastName.get())) {
			firstName = firstName.substring(0, firstName.lastIndexOf(' '));
		}
		person.setFirstName(firstName);

		SinglePersonRecord singlePersonRecord = (SinglePersonRecord) record;
		singlePersonRecord.setPerson(person);
	}

	private Optional<Person> extractFather() {
		String text = leftBox.text();
		Matcher matcher = RecordRegexes.ONE_PARENT_PATTERN.matcher(text);
		if (matcher.find()) {
			String firstName = matcher.group(1);
			if (Names.sex(firstName) == Sex.WOMAN) {
				return Optional.empty();
			} else {
				return Optional.of(Person.man(matcher.group(1), matcher.group(2)));
			}
		}

		matcher = RecordRegexes.ONE_PARENT_WITHOUT_SURNAME_PATTERN.matcher(text);
		if (matcher.find()) {
			String firstName = matcher.group(1);
			if (Names.sex(firstName) == Sex.WOMAN) {
				return Optional.empty();
			} else {
				return Optional.of(Person.man(matcher.group(1), null));
			}
		}
		return Optional.empty();
	}

	private Optional<Person> extractMother() {
		String text = leftBox.text();
		Matcher matcher = RecordRegexes.TWO_PARENTS_PATTERN.matcher(text);
		if (matcher.find()) {
			return Optional.of(Person.woman(matcher.group(3), matcher.group(4)));
		}

		matcher = RecordRegexes.TWO_PARENTS_WITHOUT_SECOND_SURNAME_PATTERN.matcher(text);
		if (matcher.find()) {
			return Optional.of(Person.woman(matcher.group(3), null));
		}

		matcher = RecordRegexes.ONE_PARENT_PATTERN.matcher(text);
		if (matcher.find()) {
			String firstName = matcher.group(1);
			if (Names.sex(firstName) == Sex.WOMAN) {
				return Optional.of(Person.woman(matcher.group(1), matcher.group(2)));
			}
		}
		return Optional.empty();
	}

	private String extractFirstName() {
		return leftBox.getElementsByTag("b").first().text().trim();
	}

}
