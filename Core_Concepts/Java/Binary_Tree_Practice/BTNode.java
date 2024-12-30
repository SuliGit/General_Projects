class BTNode<E> {
	private E data;
	private BTNode<E> left, right;

	public BTNode(E initialData, BTNode<E> initialLeft, BTNode<E> initialRight) {
		data = initialData;
		left = initialLeft;
		right = initialRight;
	}

	public E getData() {
		return data;
	}

	public BTNode<E> getLeft() {
		return left;
	}

	public BTNode<E> getRight() {
		return right;
	}

	public void setData(E newData) {
		data = newData;
	}

	public void setLeft(BTNode<E> newLeft) {
		left = newLeft;
	}

	public void setRight(BTNode<E> newRight) {
		right = newRight;
	}
}
