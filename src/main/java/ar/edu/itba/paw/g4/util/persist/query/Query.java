package ar.edu.itba.paw.g4.util.persist.query;

import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;

import java.util.LinkedList;
import java.util.List;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.apache.commons.lang3.tuple.Pair;

public class Query<A extends Enum<A>> {
	private List<Pair<A, Condition>> conditions;
	private Integer quantifier;
	private Pair<A, Orderings> ordering;

	@GeneratePojoBuilder
	public Query(List<Pair<A, Condition>> conditions,
			Pair<A, Orderings> ordering, Integer quantifier) {
		checkArgument(conditions, notEmptyColl());
		checkArgument(ordering, notNull());
		this.quantifier = quantifier;
		this.conditions = conditions != null ? conditions
				: new LinkedList<Pair<A, Condition>>();
		this.ordering = ordering;
	}

	public Pair<A, Orderings> getOrdering() {
		return ordering;
	}

	public Integer getQuantifier() {
		return quantifier;
	}

	public List<Pair<A, Condition>> getConditions() {
		return conditions;
	}

	@SuppressWarnings("rawtypes")
	public static QueryBuilder builder() {
		return new QueryBuilder();
	}
}
