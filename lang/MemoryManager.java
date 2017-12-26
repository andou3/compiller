
class MemoryManager {
	
	final static int MAX_SHIFT = 0xFFFFF;

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

	void invalidate() {
		currentMaxShift = 0;
	}
}