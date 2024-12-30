class BSTree<E extends Comparable<E>> {
	private BTNode<E> root;

	public BSTree() {
		root = null;
	}
	
	public BTNode<E> getRoot() {
		return root;
	}

	public void add(E data) {
		root = addNode(root, data);
	}

	private BTNode<E> addNode(BTNode<E> node, E data) {
		if (node == null) {
			return new BTNode<E>(data, null, null);
		}
		
		if (data.compareTo(node.getData()) < 0) {
			node.setLeft(addNode(node.getLeft(), data));
		}
		else if (data.compareTo(node.getData()) >= 0) {
			node.setRight(addNode(node.getRight(), data));
		}
		
		return node;
	}
	
	
	// Time Complexity O(n)
	public String print(BTNode<E> root) {
		String s = "[  " + toText(root) + "]";
		
		return s;
	}
	
	public String toText(BTNode<E> root) {
		String s = "";
		
		if (root == null) {
			return s;
		}
		
		if (root.getLeft() != null ) {
			s += toText(root.getLeft()) + " ";
		}
		s += root.getData() + " ";
		if (root.getRight() != null ) {
			s += toText(root.getRight()) + " ";
		}
		
		return s;
	}
	
	// Time Complexity is O(log(N))
	public boolean contains(BTNode<E> root, E element) {
		
		if (root == null) {
			return false;
		}
		
		if (root.getData().compareTo(element) == 0) {
			return true;
		}
		else if (root.getData().compareTo(element) < 0 && root.getLeft() != null) {
			return contains(root.getLeft(), element);
		}
		else if (root.getData().compareTo(element) > 0 && root.getRight() != null) {
			return contains(root.getRight(), element);
		}
		
		
		return false;
	}
	
	// Time Complexity O(n)
	public int size(BTNode<E> root) {
		int size = 0;
		
		if (root == null) {
			return size;
		}
		
		if (root.getLeft() != null) {
			size += size(root.getLeft());
		}
		if (root.getRight() != null) {
			size += size(root.getRight());
		}
		
		
		return size + 1;
		
	}
}
