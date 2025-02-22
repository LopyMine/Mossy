package net.lopymine.mossy.tasks.utils;

public class MossyUtils {

	public static String substringBefore(Object o, String ch) {
		if (o == null) {
			return "null";
		}
		String string = o.toString();
		int i = string.indexOf(ch);
		if (i == -1) {
			return string;
		} else {
			return string.substring(0, i);
		}
	}

}
