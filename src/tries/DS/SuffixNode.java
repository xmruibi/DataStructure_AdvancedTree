package tries.DS;

import java.util.LinkedList;
import java.util.List;

public class SuffixNode {
	String key;
	List<SuffixNode> children;
	boolean terminal;
	int minStartIndex;

	public SuffixNode(String key) {
		this.key = key;
		children = new LinkedList<SuffixNode>();
	}

	// for the root node!!!
	public SuffixNode() {
		this.key = "";
		this.minStartIndex = -1;
		children = new LinkedList<SuffixNode>();
	}

	@Override
	public String toString() {
		return this.key + " [" + this.minStartIndex + "] "
				+ (this.terminal ? "#" : "");
	}
}
