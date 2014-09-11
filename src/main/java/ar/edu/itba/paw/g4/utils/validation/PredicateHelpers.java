package ar.edu.itba.paw.g4.utils.validation;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;

public class PredicateHelpers {
	private static Predicate<Object> NOT_NULL = Predicates.notNull();

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

	private static Predicate<String> NEITHER_NULL_NOR_EMPTY = new Predicate<String>() {
		@Override
		public boolean apply(String str) {
			return Strings.isNullOrEmpty(str);
		}
	};

	public static Predicate<Object> notNull() {
		return NOT_NULL;
	}

	public static Predicate<String> neitherNullNorEmpty() {
		return NEITHER_NULL_NOR_EMPTY;
	}

	public static Predicate<String> notEmptyStr() {
		return STR_NOT_EMPTY;
	}

	public static Predicate<Collection<?>> notEmptyColl() {
		return COLL_NOT_EMPTY;
	}
}