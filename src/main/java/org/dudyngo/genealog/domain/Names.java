package org.dudyngo.genealog.domain;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableList;

public final class Names {

	private static final List<String> MALE_NAMES_ENDING_WITH_A =
			ImmutableList.of("Kosma", "Bonawentura");
	private static final List<String> FEMALE_NAMES_NOT_ENDING_WITH_A =
			ImmutableList.of("Agnes");

	private Names() {
	}

	public static Sex sex(String name) {
		if (MALE_NAMES_ENDING_WITH_A.contains(name)) {
			return Sex.MAN;
		} else if (FEMALE_NAMES_NOT_ENDING_WITH_A.contains(name)) {
			return Sex.WOMAN;
		} else if (name.endsWith("a")) {
			return Sex.WOMAN;
		} else {
			return Sex.MAN;
		}
	}

	public static String convertSurnameToSex(String name, Sex sex) {
		Pattern convertibleSuffix = Pattern.compile("(sk|ck|dzk)[ai]$");
		Matcher matcher = convertibleSuffix.matcher(name);
		if (matcher.find()) {
			Character lastCharacter = sex == Sex.WOMAN ? 'a' : 'i';
			return name.substring(0, name.length() - 1) + lastCharacter;
		} else {
			return name;
		}
	}

}
