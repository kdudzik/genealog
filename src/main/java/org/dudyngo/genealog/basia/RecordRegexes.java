package org.dudyngo.genealog.basia;

import java.util.regex.Pattern;

public class RecordRegexes {

	private static final String WHITESPACE = "\\s+";
	private static final String NAME_WITHOUT_SURNAME = "(.+?)";
	private static final String SURNAME = "(\\S+?)";
	private static final String NAME_AND_SURNAME = NAME_WITHOUT_SURNAME + WHITESPACE + SURNAME;
	private static final String PARENTS_PREFIX = "rodzice:" + WHITESPACE;
	private static final String YEARS_SUFFIX = "(?:," + WHITESPACE + "lat\\s+\\d+)?";
	private static final String PARENT_SUFFIX = WHITESPACE + ",";

	public static final Pattern ONE_PARENT_PATTERN =
			Pattern.compile(PARENTS_PREFIX
					+ NAME_AND_SURNAME + YEARS_SUFFIX + PARENT_SUFFIX);

	public static final Pattern TWO_PARENTS_PATTERN =
			Pattern.compile(PARENTS_PREFIX
					+ NAME_AND_SURNAME + YEARS_SUFFIX + PARENT_SUFFIX + WHITESPACE
					+ NAME_AND_SURNAME + YEARS_SUFFIX + PARENT_SUFFIX);

	public static final Pattern ONE_PARENT_WITHOUT_SURNAME_PATTERN =
			Pattern.compile(PARENTS_PREFIX
					+ NAME_WITHOUT_SURNAME + YEARS_SUFFIX + PARENT_SUFFIX);

	public static final Pattern TWO_PARENTS_WITHOUT_SECOND_SURNAME_PATTERN =
			Pattern.compile(PARENTS_PREFIX
					+ NAME_AND_SURNAME + YEARS_SUFFIX + PARENT_SUFFIX + WHITESPACE
					+ NAME_WITHOUT_SURNAME + YEARS_SUFFIX + PARENT_SUFFIX);
}
