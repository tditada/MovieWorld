package ar.edu.itba.paw.g4.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Validations {
	public static void checkNotNullOrEmpty(String string) {
		checkNotNull(string);
		checkArgument(!string.isEmpty());
	}
}
