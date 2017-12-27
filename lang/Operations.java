enum Operations {

	ADD("+"),
	SUB("-"),
	MUL("*"),
	DIV("/");

	private String value;

	private Operations(String value) {
		this.value = value;
	}

	String getValue() {
		return value;
	}
}
