import java.io.Serializable;
import java.util.Arrays;

public class HeapBag<E extends Comparable<E> & Serializable> {
	private E[] heap;
	private int capacity;
	private int size;
	
	public HeapBag() {
		capacity = 2;
		size = 0;
		heap = (E[]) new Comparable[capacity];
	}
	
	public HeapBag(HeapBag origHeap) {
		capacity = origHeap.getCapacity();
		size = origHeap.size();
		heap = (E[]) new Comparable[capacity];
	    for (int i = 0; i < size; i++) {
	        heap[i] = (E) DeepCopy.deepCopy(origHeap.get(i));
	    }
	}
	
	public void add(E element) {
		if (size == capacity) {
			ensureCapacity();
		}
		
		heap[size] = element;
		size++;
		
		// Sorting the heap
		int curIndex = size - 1;
		while (curIndex > 0) {
			int prevIndex = (curIndex - 1) /2;
			
			if (heap[curIndex].compareTo(heap[prevIndex]) >= 0) {
	            break;  // If the heap property is satisfied, stop bubbling up
	        }
			
			E temp = heap[curIndex];
			heap[curIndex] = heap[prevIndex];
			heap[prevIndex] = temp;
			
			curIndex = prevIndex;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void ensureCapacity() {
		capacity *= 2;
		E[] newHeap = (E[]) new Comparable[capacity];
		
		for (int i = 0; i < size; i++) {
			newHeap[i] = (E) heap[i];
		}
		
		heap = newHeap;
	}
	
	public int size() {
		return size;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public E get(int i) {
		return heap[i];
	}
	
	// The time analysis of this method is O(n) as it iterates through the total number
	// of elements in the two bags
	public static HeapBag union(HeapBag heap1, HeapBag heap2) {
		HeapBag newHeap = new HeapBag();
		for (int i = 0; i < heap1.size(); i++) {
			newHeap.add(DeepCopy.deepCopy(heap1.get(i)));
		}
		
		for (int i = 0; i < heap2.size(); i++) {
			newHeap.add(DeepCopy.deepCopy(heap2.get(i)));
		}
		
		
		return newHeap;
	}
	
	public void printAll() {
		E[] newArr = Arrays.copyOf(heap, size);
		Arrays.sort(newArr);
		
		for (int i = 0; i < size; i++) {
			System.out.println(newArr[i]);
		}
	}
	
	public HeapBag clone(HeapBag heap) {
		HeapBag clonedHeap = new HeapBag();
		
		for (int i = 0; i < heap.size(); i++) {
			clonedHeap.add(DeepCopy.deepCopy(heap.get(i)));
		}
		
		return clonedHeap;
		
	}

}
