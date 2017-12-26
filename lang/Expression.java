class Expression {

	Types type;
	int stackShift;
	int value;//REMOVE!!!!

	Operations operation;

	Expression e1, e2;

	Expression(Types type, int stackShift, int value) {
		this.type = type;
		this.stackShift = stackShift;
		this.value = value; //СРОЧНО УДАЛИТЬ ПОСЛЕ ОТЛАДКИ!!!!!!
		allocate();
		//System.out.println(value);
	}

	Expression(Types type, int stackShift, Object e1, Object e2, Operations operation) {
		this.type = type;
		this.stackShift = stackShift;
		this.e1 = (Expression) e1;
		this.e2 = (Expression) e2;
		this.operation = operation;
		
	
		/*switch (operation) {
			case ADD: {
				value = this.e1.getValue() + this.e2.getValue();
				System.out.println(this.e1.getValue() + " + " + this.e2.getValue());
				break;
			}
			case SUB: {
				value = this.e1.getValue() - this.e2.getValue();
				System.out.println(this.e1.getValue() + " - " + this.e2.getValue());
				break;
			}
			case MUL: {
				value = this.e1.getValue() * this.e2.getValue();
				System.out.println(this.e1.getValue() + " * " + this.e2.getValue());
				break;
			}
			case DIV: {
				value = this.e1.getValue() / this.e2.getValue();
				System.out.println(this.e1.getValue() + " / " + this.e2.getValue());
				break;
			}
		}
		System.out.println("THIS.value = " + this.value);*/
		allocate();
	}

	int getValue() {
		return value;
	}

	private void allocate() {
		
		//Написать сюда код, который делает ассемблерные вставки в строку, а после, уже в файл 
	}
}

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

enum Types {

	CONSTANT,
	COMPOSITE
}