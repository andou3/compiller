
class MemoryManager {
	
	final static int MAX_SHIFT = 0xFFFFF;

	private static String startProgram = "\nglobal main" +
    "extern scanf\n" +
    "extern printf\n" +

    "section .text\n" +
    "main:\n" +
    "push ebp\n" +
    "mov ebp, esp\n" + 
    "sub esp, 0xFFFFF ; start stack\n";
    private final static String END_OF_MAIN	=
            "	\nmov esp, ebp\n" +
    "pop ebp\n" +
    "ret";

	MemoryManager() {

	}

	private int currentMaxShift = 0;

	/**
	* Увеличивает сдвиг от регистра ebp 
	* @param byteCount Количество бит, на которое надо сдвигать
	* @return Сдвиг относительно середины стека для хранения переменной
	*/
	int getAvailableShift(int byteCount) {
		currentMaxShift += byteCount;
		return currentMaxShift;
	}

	void allocateStackLocalVariable(int shift, int value) {
		startProgram += "\nmov DWORD [ebp - " + shift + "], " + value;
	}

	void calculateStackExpression(int lvalueShift, Operations operation, int rValueShift, int resultShift) {
		switch (operation) {
            case ADD: {
                startProgram += "\nmov eax, [ebp - " + lvalueShift + "]\n" +
                        "mov ecx, [ebp - " + rValueShift + "]\n" +
                        "add eax, ecx\n" +
                        "mov DWORD [ebp - " + resultShift + "], eax\n";
                break;
            }
            case SUB: {
                startProgram += "\nmov eax, [ebp - " + lvalueShift + "]\n" +
                        "mov ecx, [ebp - " + rValueShift + "]\n" +
                        "sub eax, ecx\n" +
                        "mov DWORD [ebp - " + resultShift + "], eax\n";
                break;
            }
            case MUL: {
                startProgram += "\nmov eax, [ebp - " + lvalueShift + "]\n" +
                        "mov ecx, [ebp - " + rValueShift + "]\n" +
                        "mul ecx\n" +
                        "mov DWORD [ebp - " + resultShift + "], eax\n";
                break;
            }
            case DIV: {
                startProgram += "\nmov eax, [ebp - " + lvalueShift + "]\n" +
                        "mov ecx, [ebp - " + rValueShift + "]\n" +
                        "div ecx\n" +
                        "mov DWORD [ebp - " + resultShift + "], eax\n";
                break;
            }
        }
	}

	void printProgram() {
		System.out.println(startProgram + END_OF_MAIN);
	}

	void invalidate() {
		currentMaxShift = 0;
	}

	//Variable newVariable();
}