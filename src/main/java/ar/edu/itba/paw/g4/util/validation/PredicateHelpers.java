package ar.edu.itba.paw.g4.util.validation;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;

public class PredicateHelpers {
	private static Predicate<Object> NOT_NULL = Predicates.notNull();

	private static Predicate<String> STR_NOT_EMPTY = new Predicate<String>() {
		@Override
		public boolean apply(String str) {
			return !str.isEmpty();
		}
	};

	private static Predicate<Collection<?>> COLL_NOT_EMPTY = new Predicate<Collection<?>>() {
		@Override
		public boolean apply(Collection<?> coll) {
			return !coll.isEmpty();
		}
	};

	private static Predicate<Object[]> ARR_NOT_EMPTY = new Predicate<Object[]>() {

		@Override
		public boolean apply(Object[] arr) {
			return arr.length > 0;
		}

	};

	private static Predicate<String> NEITHER_NULL_NOR_EMPTY = new Predicate<String>() {
		@Override
		public boolean apply(String str) {
			return !Strings.isNullOrEmpty(str);
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

	public static Predicate<Object[]> notEmptyArr() {
		return ARR_NOT_EMPTY;
	}
}