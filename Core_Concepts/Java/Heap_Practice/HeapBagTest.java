import java.io.Serializable;

public class HeapBagTest {

	public static void main(String[] args) {
		HeapBag<Diamond> myDiamondHeap = new HeapBag<Diamond>();
		
		DiamondArrayBag.creatDiamondSer();
	        
	    DiamondArrayBag myDBag = DiamondArrayBag.loadFromFile("Diamonds.ser");
	        
		for (int i = 0; i < myDBag.getSize(); i++) {
			myDiamondHeap.add(myDBag.getPosition(i));
		}
		
		System.out.println("Heap 1 Data:\n");
		myDiamondHeap.printAll();
		
		System.out.println("\n\nHeap 2 Data (Exact copy of Heap 1):\n");
		HeapBag<Diamond> myDiamondHeap2 = new HeapBag<Diamond>(myDiamondHeap);
		myDiamondHeap2.printAll();
		

		System.out.println("\n\nHeap 3 Data (Union of Heap 1 and Heap 2):\n");
		HeapBag<Diamond> unionHeap = HeapBag.union(myDiamondHeap, myDiamondHeap2);
		unionHeap.printAll();
	}

}
