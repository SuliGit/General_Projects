import java.util.Random;

interface Cloneable<E> {
	E clone();

	Boolean equals(Diamond d);
}

class GenericNode<E> {
	private E data;
	private int count;
	private GenericNode link;

	public GenericNode(E initialData, GenericNode initialLink) {
		data = initialData;
		count = 0;
		link = initialLink;
	}

	public void setData(E newData) {
		data = newData;
	}

	public E getData() {
		return data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int c) {
		count = c;
	}

	public void incrementCount() {
		count++;
	}

	public void decrementCount() {
		count--;
	}

	public GenericNode getLink() {
		return link;
	}

	public void setLink(GenericNode newLink) {
		link = newLink;
	}

	public static GenericNode listSearch(GenericNode head, Object target) {
		GenericNode cursor;
		for (cursor = head; cursor != null; cursor = cursor.link)
			if (target == cursor.data)
				return cursor;
		return null;
	}

	public static GenericNode listPosition(GenericNode head, int position) {
		GenericNode cursor;
		int i;
		if (position <= 0)
			throw new IllegalArgumentException("position is not positive");
		cursor = head;
		for (i = 1; (i < position) && (cursor != null); i++)
			cursor = cursor.link;
		return cursor;
	}
}

class LinkedBag<E> {
	private GenericNode head;
	private GenericNode tail;
	private int manyNodes;
	private int count;

	public LinkedBag() {
		head = null;
		tail = null;
		manyNodes = 0;
		count = 0;
	}

	public LinkedBag(LinkedBag<E> bag) {
		head = null;
		tail = null;
		manyNodes = 0;
		count = 0;

		GenericNode<E> n = bag.head;

		while (n != null) {
			add((E) ((Cloneable) n.getData()).clone());
			n = n.getLink();
		}

	}

	public void add(Object object) {
		if (manyNodes == 0) {
			head = new GenericNode(object, null);
			head.incrementCount();

			tail = head;
			manyNodes++;

		} else {
			GenericNode n = head;
			Boolean found = false;
			for (int i = 0; i < manyNodes; i++) {
				if (n == null) {
					break;
				}
				if ((object.equals(n.getData()))) {
					n.incrementCount();
					found = true;
				}
				n = n.getLink();
			}

			if (!found) {
				GenericNode newNode = new GenericNode(object, null);
				newNode.incrementCount();
				tail.setLink(newNode);
				tail = newNode;
				manyNodes++;
			}
		}

		count++;
	}

	public int countOccurrences(E target) {
		GenericNode n = head;

		for (int i = 0; i < manyNodes; i++) {
			if (n == null) {
				break;
			}
			if ((target.equals(n.getData()))) {
				return n.getCount();
			}
			n = n.getLink();
		}

		return -1;

	}

	public boolean remove(E target) {
		GenericNode n = head;
		GenericNode prevN = head;
		Boolean removed = false;

		while (n != null) {
			if ((target.equals(n.getData()))) {
				
				if (n.getCount() > 1) {
	                n.decrementCount();
				}
				
				else {
					if (n == head) {
						head = n.getLink();
	                    if (head == null) {
	                        tail = null;
	                    }
					}
					
					else if (n == tail) {
						prevN.setLink(null);
	                    tail = prevN;
					}
					
					else {
						prevN.setLink(n.getLink());
					}
					manyNodes--;
				}
				count--;
				removed = true;
			}
			
			prevN = n;
			n = n.getLink();
		}

		return removed;

	}

	public int manyNodes() {
		return manyNodes;
	}

	public int size() {
		return count;
	}

	// method to make testing easier
	public Boolean view(int n) {

		if (n >= manyNodes) {
			System.out.println("Too Big");
			return false;
		}

		else {
			GenericNode g = head;

			for (int i = 0; i < n; i++) {
				g = g.getLink();
			}

			System.out.println("Data: " + g.getData() + "\nCount: " + g.getCount());

			return true;
		}

	}

	public E grab() {
		Random rand = new Random();

		int r = rand.nextInt(manyNodes);
		E obj;

		GenericNode n = head;
		for (int i = 0; i < r % manyNodes; i++) {
			if (n == null) {
				System.out.println("Grab Method Null Too Early");
				break;
			} else {
				n = n.getLink();
			}
		}

		return (E) n.getData();
	}
	
	public void printAll() {
		GenericNode g = head;

		for (int i = 0; i < manyNodes; i++) {
			if (g == null) {
				break;
			}
			System.out.println("Data at " + i + ":\n" + g.getData() + "Count: " + g.getCount() + "\n\n");
			g = g.getLink();
		}

		
	}

	public static LinkedBag clone(LinkedBag bag) throws CloneNotSupportedException {
		LinkedBag clonedBag = new LinkedBag();
		GenericNode n = bag.head;

		while (n != null) {

			clonedBag.add((((Cloneable) n.getData()).clone()));
			n = n.getLink();

		}

		return clonedBag;
	}
	
	
	public static LinkedBag intersect(LinkedBag b1, LinkedBag b2) {
		LinkedBag intersection = new LinkedBag();
		GenericNode n1 = b1.head;
		GenericNode n2 = b2.head;
		
		for (int i = 0; i < b1.manyNodes(); i++) {
			for (int j = 0; j < b2.manyNodes(); j++) {
				if (n1.getData().equals(n2.getData())) {
					intersection.add(n1.getData());
				}
				n2 = n2.getLink();
				if (n2 == null) {
					break;
				}
			}
			n2 = b2.head;
			n1 = n1.getLink();
		}
		
		return intersection;
	}

}

public class LinkedExample {
	public static void main(String[] args) throws CloneNotSupportedException {

		LinkedBag<Diamond> dBag1 = new LinkedBag<>();
		

		dBag1.add(new Diamond(1.0, 'D', "DMN0085", "VVS1", "Rose"));
		dBag1.add(new Diamond(1.0, 'D', "DMN0085", "VVS1", "Rose"));
		dBag1.add(new Diamond(2.0, 'X', "DSN1285", "VS1", "Baguette"));
		dBag1.add(new Diamond(2.0, 'X', "DSN1285", "VS1", "Baguette"));
		dBag1.add(new Diamond(2.0, 'X', "DSN1285", "VS1", "Baguette"));
		dBag1.add(new Diamond(3.0, 'H', "FRN9985", "IF", "Lozenge"));
		dBag1.add(new Diamond(1.5, 'I', "TTN9999", "VVS2", "Pearl"));
		dBag1.add(new Diamond(2.5, 'Z', "LNN1111", "SI1", "Brilliant"));
		dBag1.add(new Diamond(4.0, 'E', "FFF0000", "I1", "Radiant"));
		
		System.out.println("Nodes in dBag1: " + dBag1.manyNodes());
		System.out.println("Element Count of dBag1: " + dBag1.size() + "\n\n");
		
		dBag1.printAll();
		
		dBag1.remove(new Diamond(1.0, 'D', "DMN0085", "VVS1", "Rose"));
		dBag1.remove(new Diamond(4.0, 'E', "FFF0000", "I1", "Radiant"));
		
		
		System.out.println("Removed two elements, new dBag1: ");
		dBag1.printAll();
		
		System.out.println("Nodes in dBag1: " + dBag1.manyNodes());
		System.out.println("Element Count of dBag1: " + dBag1.size());
		
		
		// I added 9 elements to dBag1. Most elements have a count of 1, 
		// one element has a count of 2 and one of 3. I removed an element
		// from the Diamond of a count of two and from a Diamond of a
		// count of one. This is why the number of nodes decreased by 1
		// while the number of elements decreased by 2.
		

	}
}
