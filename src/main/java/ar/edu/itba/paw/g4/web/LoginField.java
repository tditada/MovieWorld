package ar.edu.itba.paw.g4.web;

public enum LoginField {
	EMAIL(0), PASSWORD(1);
	
	 public final int value;

	 LoginField(final int value) {
	     this.value = value;
	  }
	 public static int maxValue(){
		 return PASSWORD.value;
	 }
}
