package ar.edu.itba.paw.g4.utils.validation;

import java.util.Collection;

import com.google.common.base.Predicate;

public class PredicateExtras {
	private static Predicate<String> STR_NOT_EMPTY = new Predicate<String>() {
		@Override
		public boolean apply(String arg) {
			return !arg.isEmpty();
		}
	};

	private static Predicate<Collection<?>> COLL_NOT_EMPTY = new Predicate<Collection<?>>() {
		@Override
		public boolean apply(Collection<?> arg) {
			return !arg.isEmpty();
		}
	};

	public static Predicate<String> notEmptyStr() {
		return STR_NOT_EMPTY;
	}

	public static Predicate<Collection<?>> notEmptyColl() {
		return COLL_NOT_EMPTY;
	}
}