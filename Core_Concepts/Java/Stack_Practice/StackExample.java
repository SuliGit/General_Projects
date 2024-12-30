import java.util.EmptyStackException;

class ArrayStack<E> implements Cloneable {
	private E[] data;
	private int manyItems;

	public ArrayStack() {
		final int INITIAL_CAPACITY = 10;
		manyItems = 0;
		data = (E[]) new Object[INITIAL_CAPACITY];
	}

	public ArrayStack(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("initialCapacity too small " + initialCapacity);
		manyItems = 0;
		data = (E[]) new Object[initialCapacity];
	}

	public void ensureCapacity(int minimumCapacity) {
		E biggerArray[];

		if (data.length < minimumCapacity) {
			biggerArray = (E[]) new Object[minimumCapacity];
			System.arraycopy(data, 0, biggerArray, 0, manyItems);
			data = biggerArray;
		}
	}

	public boolean isEmpty() {
		return (manyItems == 0);
	}

	public E pop() {
		if (manyItems == 0)
			throw new EmptyStackException();
		return data[--manyItems];
	}

	public void push(E item) {
		if (manyItems == data.length) {
			ensureCapacity(manyItems * 2 + 1);
		}
		data[manyItems] = item;
		manyItems++;
	}

	public int size() {
		return manyItems;
	}
	
	public String toString() {
		String s = "";
		
		for (int i = manyItems - 1; i >= 0; i--) {
			s += data[i];
		}
		
		return s;
	}
	
	// The time complexity of the toString() is O(n)
}

public class StackExample {
	public static void main(String[] args) {
		String[] myLine = { "What", "is", "up", "other", "side!" };
		
		System.out.print("Forwards: ");
		for (int i = 0; i < myLine.length; i++) {
			System.out.print(myLine[i] + " ");
		}
		
		ArrayStack<String> stack1 = new ArrayStack<String>();
		
		
		
		for (String s : myLine) {
			stack1.push(s);
		}
			
		System.out.println();
		
		
		ArrayStack<Character> charLine = new ArrayStack<Character>();
		for(int i = 0; i < myLine.length; i++) {
			for (int j = 0; j < myLine[i].length(); j++) {
				charLine.push(myLine[i].charAt(j));
			}
			charLine.push(' ');
		}
		
		System.out.print("\nBackwards: " + charLine);
		
	}
}
