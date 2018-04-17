package media.utils;

public class ArrayUtils {
	
	public static String join(String[] array, String delim) {
		return join(array, delim, array.length);
	}
	
	public static String join(String[] array, String delim, int maxElems) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < maxElems; i++) {
			builder.append(array[i]);
			if (i != maxElems - 1) {
				builder.append(delim);
			}
		}
		return builder.toString();
	}

}
