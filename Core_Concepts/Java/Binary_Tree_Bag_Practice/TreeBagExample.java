
public class TreeBagExample {

	public static void main(String[] args) {
		BSTree<Diamond> bst = new BSTree<Diamond>();
        
        DiamondArrayBag.creatDiamondSer();
        
        DiamondArrayBag myDBag = DiamondArrayBag.loadFromFile("Diamonds.ser");
        
		for (int i = 0; i < myDBag.getSize(); i++) {
			bst.add(myDBag.getPosition(i));
		}
		
		System.out.println("Contents of Tree in Tree Format:");
		bst.drawTree();
		
		Diamond d = (Diamond) bst.grab();
		System.out.println("\nGrabbed Root:\n" + d);
		
		
		System.out.println("\nThe grab method finds a random element in the tree.\n"
				+ "It generates a random number and then iterates through the nodes of the\n"
				+ "nodes of the tree that many times at random choosing the left or right node\n"
				+ "reaches a leaf node then it grabs the element at that node. If it\n"
				+ "iterates through the tree for the number generated then it grabs that element.\n\n");
		
		
		System.out.println("\n\nNumber of occurrences of grabbed element: " + bst.countOccurrences(bst.getRoot(), d));
		
		System.out.println("\nDepth of Tree: " + bst.depth());
		System.out.println("Size of Tree: " + bst.size());
		System.out.println("\n*Removing the grabbed element...*\n");
		
		bst.remove(bst.getRoot(), d);

		System.out.println("New Depth of Tree: " + bst.depth());
		System.out.println("New Size of Tree: " + bst.size());
		// Removal Success, Size is 1 less.
		// Depending on what element was grabbed the bst possibly has 1 less depth
		// If the element was a leaf node at the deepest part of the tree then it will 
		// have gone down by 1
		
		
		System.out.println("\n\nNew Contents of Tree in Tree Format:");
		bst.drawTree();
		
		
		System.out.println("\n\nContents of Tree in Ascending Order:");
		bst.printAll();
		
		
	}

}
