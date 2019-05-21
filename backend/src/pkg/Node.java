package pkg;

public class Node {
	private String value;
	private Node nextNode = null;

	Node(String value) {
		this.value = value;
	}

	Node(String value, Node node) {
		this.value = value;
		this.nextNode = node;
	}

	void changeNextNode(Node node) {
		this.nextNode = node;
	}

	String getValue() {
		return this.value;
	}

	public void setValue(String string) {
		this.value = string;
	}

	Node nextNode() {
		return this.nextNode;
	}
}
