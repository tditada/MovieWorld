package ar.edu.itba.paw.g4.util.persist.query;

public class Condition {
	private final Conditionals conditional;
	private final Object parameter;

	public static Condition equalsVal(Object parameter) {
		return new Condition(Conditionals.EQ, parameter);
	}

	public Condition(Conditionals conditional, Object parameter) {
		this.conditional = conditional;
		this.parameter = parameter;
	}

	public Conditionals getConditional() {
		return conditional;
	}

	public Object getParameter() {
		return parameter;
	}

}
