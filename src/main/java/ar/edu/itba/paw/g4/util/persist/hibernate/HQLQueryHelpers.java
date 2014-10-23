package ar.edu.itba.paw.g4.util.persist.hibernate;

import ar.edu.itba.paw.g4.util.persist.Orderings;

public class HQLQueryHelpers {
	public static String asHQLOrdering(Orderings ordering) {
		switch (ordering) {
		case ASC:
			return "asc";
		case DESC:
			return "desc";
		default:
			throw new IllegalArgumentException("Not a valid Ordering");
		}
	}
}
