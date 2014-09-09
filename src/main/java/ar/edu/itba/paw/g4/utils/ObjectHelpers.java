package ar.edu.itba.paw.g4.utils;

import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Objects;

public class ObjectHelpers {
	public static ToStringHelper toStringHelper(Object obj) {
		return toStringHelper(obj);
	}

	public static int hash(Object... objects) {
		return Objects.hashCode(objects);
	}

	public static boolean equal(Object a, Object b) {
		return Objects.equal(a, b);
	}
}
