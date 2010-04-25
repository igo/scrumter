package scrumter.service;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]+");
	private static final Pattern DASHES = Pattern.compile("\\-+");

	public static String stringToPrettyLink(String input) {
		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
		String multidash = NONLATIN.matcher(normalized).replaceAll("");
		String slug = DASHES.matcher(multidash).replaceAll("-");
		return slug.toLowerCase(Locale.ENGLISH);
	}

}
