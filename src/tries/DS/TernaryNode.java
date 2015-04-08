package tries.DS;

public class TernaryNode {
	private final char label;
	public TernaryNode left, centre, right;
	private String value;

	public TernaryNode() { // rootNode
		this.label = '*';
		this.left = null;
		this.centre = null;
		this.right = null;
		this.value = null;
	}

	public TernaryNode(char label) {
		this.label = label;
		this.left = null;
		this.centre = null;
		this.right = null;
		this.value = null;
	}

	public String getValue() {
		if(value==null)
			return null;
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public char getLabel() {
		return label;
	}
}
