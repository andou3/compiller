class Expression {

	Types type;
	int stackShift;
	private MemoryManager allocator;
	private int value;
	private Operations operation;

	Expression e1, e2;

	Expression(Types type, int stackShift, int value, MemoryManager allocator) {
		this.type = type;
		this.stackShift = stackShift;
		this.value = value; //СРОЧНО УДАЛИТЬ ПОСЛЕ ОТЛАДКИ!!!!!!
		this.allocator = allocator;
		allocator.allocateStackLocalVariable(stackShift, value);

		//System.out.println(value);
	}

	Expression(Types type, int stackShift, Object e1, Object e2, Operations operation, MemoryManager allocator) {
		this.type = type;
		this.stackShift = stackShift;
		this.e1 = (Expression) e1;
		this.e2 = (Expression) e2;
		this.operation = operation;
		this.allocator = allocator;
		allocator.calculateStackExpression(this.e1.stackShift, this.operation, this.e2.stackShift, this.stackShift);	
	
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
	}

	int getValue() {
		return value;
	}

}

enum Types {

	CONSTANT,
	COMPOSITE
}