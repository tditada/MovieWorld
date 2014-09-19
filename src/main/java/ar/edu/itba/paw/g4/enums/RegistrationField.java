package ar.edu.itba.paw.g4.enums;

public enum RegistrationField {
	NAME(0), LASTNAME(1), EMAIL(2), PASSWORD(3), SECONDPASSWORD(4), BIRTHDAY(5);
	
	 public final int value;

	 RegistrationField(final int value) {
	     this.value = value;
	  }
}
