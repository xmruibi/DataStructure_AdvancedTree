package tries.DS;

import java.util.LinkedList;
import java.util.List;

import tries.Interface.Tries;

public class SuffixTree implements Tries {

	private SuffixNode root;

	public SuffixTree() {
		root = new SuffixNode();
	}

	@Override
	public void build(String dictName) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean search(String word) {
		if (word == null || word.length() == 0)
			return false;

		return true;
	}

	private void search(SuffixNode node, String word) {

	}

	@Override
	public boolean insert(String word) {
		if (word == null || word.length() == 0)
			return false;
		for (int i = 0; i < word.length() - 1; i++)
			insert(root, word.substring(i), i);
		return true;
	}

	/**
	 * @param node
	 * @param word
	 *            The word means the substring of actual input word
	 * @param idx
	 *            The index marks the start char's index in actual input word
	 */
	private void insert(SuffixNode node, String word, int idx) {
		boolean done = false;
		for (int i = 0; i < node.children.size(); i++) {
			SuffixNode child = node.children.get(i);
			String check = child.getKey();
			int len = check.length() < word.length() ? check.length() : word
					.length();
			int ptr = 0; // check "check string" and "current word"' first
							// different char
			while (check.charAt(ptr) == word.charAt(ptr++))

			if (ptr == 0) { // no match
				// insert new node according to the lexicographic order
				if (word.charAt(0) < check.charAt(0)) {
					SuffixNode newnode = new SuffixNode(word);
					newnode.minStartIndex = idx;
					newnode.terminal = true;
					node.children.add(i, newnode);
					done = true;
					break;
				} else
					continue; // check next child
			} else {
				if (ptr == len) // entirely match
				{// consider three conditions
					if (word.length() == check.length()) {
						if (child.terminal)// current node is terminal
							throw new IllegalArgumentException(
									"Duplicate key exception!");
						else { // set current node is a terminal node and update
								// minStart index
							child.terminal = true;
							child.minStartIndex = Math.min(
									node.children.get(i).minStartIndex, idx);
						}
					} else if (word.length() < check.length()) {
						// separate check into two parts according to the ptr!
						String head = check.substring(0, ptr);
						String tail = check.substring(ptr);

						// set up new child for current node
						// these children will become grandchildren of current
						// node
						List<SuffixNode> currentNodeChildren = child.children;

						SuffixNode newChild = new SuffixNode(tail);
						newChild.children = currentNodeChildren;
						newChild.minStartIndex = child.minStartIndex + ptr;
						newChild.terminal = child.terminal;

						// update current node and its children
						child.changeKey(head);
						child.terminal = true; // this must be true because the
						child.minStartIndex = Math.min(node.minStartIndex, idx);
						child.children = new LinkedList<SuffixNode>();
						child.children.add(newChild);

					} else { // word.lenght()>check.length
						child.minStartIndex = Math
								.min(child.minStartIndex, idx);
						String wordTail = word.substring(ptr);
						insert(child, wordTail, idx + ptr); // recursion
					}
				} else { // 0<ptr<len
							// separate check into two parts according to the
							// ptr!
					String head = check.substring(0, ptr);
					String tail = check.substring(ptr);

					// set up new child for current node
					// these children will become grandchildren of current node
					List<SuffixNode> currentNodeChildren = child.children;
					SuffixNode newSubchild = new SuffixNode(tail);
					newSubchild.children = currentNodeChildren;
					newSubchild.minStartIndex = child.minStartIndex + ptr;
					newSubchild.terminal = child.terminal;

					// update current node and its children
					child.changeKey(head);
					child.terminal = false;
					child.minStartIndex = Math.min(child.minStartIndex, idx);
					child.children = new LinkedList<SuffixNode>();

					// separate word and only need the tail part!
					// set it into current node children
					String wordTail = word.substring(ptr);
					SuffixNode newSubchild2 = new SuffixNode(wordTail);
					newSubchild2.terminal = true;
					newSubchild2.minStartIndex = idx + ptr;
					if (tail.charAt(0) < wordTail.charAt(0)) {
						child.children.add(newSubchild);
						child.children.add(newSubchild2);
					} else {
						child.children.add(newSubchild2);
						child.children.add(newSubchild);
					}
				}

				done = true;
				break;
			}
		}
		if (!done) { // this node may not have any child!
			SuffixNode newnode = new SuffixNode(word);
			newnode.terminal = true;
			newnode.minStartIndex = idx;
			node.children.add(newnode);
		}
	}

	@Override
	public boolean delete(String word) {
		// TODO Auto-generated method stub
		return false;
	}

	// for test purpose only
	public void printTree() {
		this.print(0, this.root);
	}

	private void print(int level, SuffixNode node) {
		for (int i = 0; i < level; i++) {
			System.out.format(" ");
		}
		System.out.format("|");
		for (int i = 0; i < level; i++) {
			System.out.format("-");
		}
		if (node.terminal)
			System.out.format("%s[%s]#%n", node.getKey(), node.minStartIndex);
		else
			System.out.format("%s[%s]%n", node.getKey(), node.minStartIndex);
		for (SuffixNode child : node.children) {
			print(level + 1, child);
		}

	}

	public static void main(String[] args) {
		SuffixTree tree = new SuffixTree();
		tree.insert("bananas");
		tree.printTree();
	}
}
