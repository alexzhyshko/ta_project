package pkg;

import java.util.ArrayList;
import java.util.List;

public class LinkedList {
	private Node start;
	private Node end;

	protected LinkedList() {
		this.start = null;
		this.end = null;
	}

	protected Object getEnd() {
		return this.end.getValue();
	}

	protected Object getStart() {
		return this.start.getValue();
	}

	protected Node getIndex(int i) {
		Node current = this.start;
		while (current.nextNode() != null && --i > 0)
			current = current.nextNode();
		return current;
	}

	protected void add(String string) {
		if (this.start == null && this.end == null) {
			this.start = new Node(string);
		} else if (this.start != null && this.end == null) {
			this.end = new Node(string);
			this.start.changeNextNode(this.end);
		} else if (this.start != null && this.end != null) {
			Node temp = new Node(string);
			this.end.changeNextNode(temp);
			this.end = temp;
		}
	}

	protected void insert(String string) {
		this.start = new Node(string, this.start);
	}

	protected void insert(String string, int index) {
		Node current = getIndex(index);
		current.changeNextNode(new Node(string, current.nextNode()));
	}

	protected int length() {
		int quantity = 0;
		Node currentNode = this.start;
		while (currentNode != null) {
			currentNode = currentNode.nextNode();
			quantity++;
		}
		return quantity;
	}

	protected int getIndexByValue(String value) {
		Node current = this.start;
		int index = 0;
		while (current != null) {
			if (current.getValue() == value) {
				return index;
			}
			current = current.nextNode();
			index++;
		}
		return -1;
	}

	protected void replace(String string1, String string2) {
		Node current = this.start;
		while (current != null) {
			if (current.getValue() == string1) {
				current.setValue(string2);
			}
			current = current.nextNode();
		}
	}

	protected void remove() {
		getIndex(length() - 2).changeNextNode(null);
	}

	protected void removeFirst() {
		this.start = this.start.nextNode();
	}

	protected void remove(int index) {
		getIndex(index - 1).changeNextNode(getIndex(index + 1));
	}

	protected List<String> search(String string) {
		Node current = this.start;
		List<String> ret = new ArrayList<>();
		while (current != null) {
			if (current.getValue().contains(string))
				ret.add(current.getValue());
			current = current.nextNode();
		}
		return ret;
	}

	void printList() {
		Node currentNode = this.start;
		while (currentNode != null) {
			if (currentNode.nextNode() == null) {
				System.out.println(currentNode.getValue());
			} else {
				System.out.print(currentNode.getValue() + " ");
			}
			currentNode = currentNode.nextNode();
		}
	}

}
