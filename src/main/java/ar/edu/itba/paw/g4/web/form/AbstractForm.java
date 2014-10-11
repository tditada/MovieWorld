package ar.edu.itba.paw.g4.web.form;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractForm<E extends Enum<E>> {
	private Class<E> clazz;

	protected AbstractForm(Class<E> clazz) {
		this.clazz = clazz;
	}

	public boolean isValid() {
		for (E field : clazz.getEnumConstants()) {
			if (!isValidField(field)) {
				return false;
			}
		}
		return true;
	}

	public Map<String, String> getErrors() {
		Map<String, String> errors = new HashMap<String, String>();
		for (E field : getFormFields()) {
			if (!isValidField(field)) {
				errors.put(getFieldKey(field), getFieldValue(field));
			}
		}
		return errors;
	}

	private E[] getFormFields() {
		return clazz.getEnumConstants();
	}

	protected abstract boolean isValidField(E field);

	public abstract String getFieldKey(E field);

	public abstract String getFieldValue(E field);
}
