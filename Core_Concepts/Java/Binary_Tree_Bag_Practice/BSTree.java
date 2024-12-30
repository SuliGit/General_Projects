import java.io.Serializable;

class BSTree<E extends Comparable<E> & Serializable> {
	private BTNode<E> root;
	private int manyItems;

	public BSTree() {
		root = null;

		manyItems = 0;
	}
	
	public BTNode<E> getRoot() {
		return root;
	}

	public void add(E data) {
		manyItems++;
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
	
	// Runtime O(n) for this method
	public int depth() {
		return getDepth(root) - 1;
	}
	public int getDepth(BTNode<E> root) {
		int curLeft = 0, curRight = 0;
		
		if (root == null) {
			return 0;
		}
		
		curLeft += getDepth(root.getLeft()) ;
		
		curRight += getDepth(root.getRight());
		
		if (curLeft > curRight) {
			return curLeft + 1;
		}
		else {
			return curRight + 1;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public E clone(E element) {
	        try {
	            return (E) element.getClass().getMethod("clone").invoke(element);
	        } catch (Exception e) {
	            throw new RuntimeException("Cloning failed", e);
	        }
	}
	
	public int countOccurrences(BTNode<E> root, E element) {
		int count = 0;
		
		if (root == null) {
			return count;
		}
		
		System.out.println("Checked Node:\n" + root.getData());
		
		if (root.getData().compareTo(element) == 0) {
			count = 1;
			count += countOccurrences(root.getLeft(), element);
			count += countOccurrences(root.getRight(), element); 
	    }
		else if (root.getData().compareTo(element) > 0) {
			
	        count += countOccurrences(root.getLeft(), element);

	    }
		
		else if (root.getData().compareTo(element) < 0) {
	    	
	        count += countOccurrences(root.getRight(), element); 

	    }
		else {
			System.out.println("Error in count occurrences. Neither equal, left or right.");
		}
		
		
		return count;
	}
	
	public BTNode<E> remove(BTNode<E> root, E element) {
		
		if (root == null ) {
			return null;
		}
		
		if (root.getData().compareTo(element) > 0) {
			root.setLeft(remove(root.getLeft(), element));
		}
		else if (root.getData().compareTo(element) < 0) {
			root.setRight(remove(root.getRight(), element));
		}
		else if (root.getData().compareTo(element) == 0) {
			if (root.getLeft() == null && root.getRight() == null) {
				manyItems--;
	            return null; 
	        }
			
			else if (root.getLeft() == null) {
				manyItems--;
				return root.getRight();
			}
			
			else if (root.getRight() == null) {
				manyItems--;
				return root.getLeft();
			}
			
			else {
				BTNode<E> smallestNode = findSmallestNode(root.getRight());
				root.setData(smallestNode.getData());
				root.setRight(remove(root.getRight(), smallestNode.getData()));
			}
			
		}
		else {
			System.out.println("Error in remove. Neither equal, left or right.");
		}
		
		return root;
	}
	
	public E grab() {
		int count = (int) (Math.random() * manyItems);
		
		BTNode<E> n = findRandom(root, count);
		
		return n.getData();
	}
	
	public BTNode<E> findRandom(BTNode<E> root, int count) {
		if (count == 0) {
			return root;
		}
		else if (root == null) {
			return null;
		}
		else {
			BTNode<E> n;
			
			if ((Math.random() + 2) % 2 == 0) {
				n = findRandom(root.getLeft(), count - 1);
				if (n == null) {
					return root;
				}
				else {
					return n;
				}
			}
			else {
				n = findRandom(root.getRight(), count - 1);
				if (n == null) {
					return root;
				}
				else {
					return n;
				}
			}
		}
	}
	
	public BTNode<E> findSmallestNode(BTNode<E> root) {
		while (root.getLeft() != null) {
			root = root.getLeft();
		}
		
		return root;
	}
	
	public String toString() {
		return toText(root);
	}
	
	// Time Complexity O(n)
	public void printAll() {
		System.out.println(toText(root));
	}
	
	public String toText(BTNode<E> root) {
		String s = "";
		
		if (root == null) {
			return s;
		}
		
		if (root.getLeft() != null ) {
			s += toText(root.getLeft());
		}
		s += root.getData() + "\n\n";
		
		if (root.getRight() != null ) {
			s += toText(root.getRight());
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
		else if (root.getData().compareTo(element) < 0) {
			return contains(root.getLeft(), element);
		}
		else if (root.getData().compareTo(element) > 0) {
			return contains(root.getRight(), element);
		}
		else {
			System.out.println("Error in contains method. Neither equal, left or right.");
		}
		
		
		return false;
	}
	
	// Time Complexity O(1)
	public int size() {
		return getSize();
		
	}
	
	public int getSize() {
		
		return manyItems;
		
	}
	public void draw(int depth, BTNode<E> root) {
	    for (int i = 0; i < depth; i++) {
	        System.out.print("\t");
	    }
	    System.out.println(root.getData());

	    if (root.getLeft() != null) {
	    	draw(depth + 1, root.getLeft());
	    } 
	    
	    else if (root.getRight() != null) {
	        for (int i = 0; i < depth + 1; i++) {
	            System.out.print("\t");
	        }
	        System.out.println("--");
	    }

	    if (root.getRight() != null) {
	    	draw(depth + 1, root.getRight());
	    } 
	    
	    else if (root.getLeft() != null) {
	        for (int i = 0; i < depth + 1; i++) {
	            System.out.print(" \t");
	        }
	        System.out.println("--");
	    }
	}
	
	public void drawTree() {
	    draw(0, root);
	}
	
	public static <E extends Comparable<E> & Serializable> BSTree<E> union(BSTree<E> tree1, BSTree<E> tree2) {
	    BSTree<E> bst = new BSTree<>();
	    
	    fill(bst, tree1.root);
	    
	    fill(bst, tree2.root);
	    
	    return bst;
	}
	
	public static <E extends Comparable<E> & Serializable> void fill(BSTree<E> target, BTNode<E> source) {
		if (source == null) {
			return;
		}
		
		target.add(source.getData());
		fill(target, source.getLeft());
		fill(target, source.getRight());
	}
	
}
