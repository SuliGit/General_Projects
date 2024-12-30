public class TreeExample {
	public static void main(String[] args) {
		BSTree<Integer> bst = new BSTree<Integer>();
		bst.add(50);
		bst.add(30);
		bst.add(70);
		bst.add(20);
		bst.add(40);
		bst.add(60);
		bst.add(80);
		bst.add(40);
		
		System.out.println(bst.print(bst.getRoot()));
		
		if (bst.contains(bst.getRoot(), 50)) {
			System.out.println("BST contains element 50");
		}
		else {
			System.out.println("BST does not contain element 50");
		}
		
		if (bst.contains(bst.getRoot(), 100)) {
			System.out.println("BST contains element 100");
		}
		else {
			System.out.println("BST does not contain element 100");
		}
		
		System.out.println("Size: " + bst.size(bst.getRoot()));
	}
}
