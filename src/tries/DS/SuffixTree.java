package tries.DS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tries.Interface.Tries;

/**
 * Suffix Tree 用途： 1.查找字符串o是否在字符串S中。 方案：用S构造后缀树，按在trie中搜索字串的方法搜索o即可。
 * 原理：若o在S中，则o必然是S的某个后缀的前缀。 例如S: leconte，查找o:
 * con是否在S中,则o(con)必然是S(leconte)的后缀之一conte的前缀.有了这个前提， 采用trie搜索的方法就不难理解了。
 * 
 * 2. 指定字符串T在字符串S中的重复次数。 方案：用S+’$'构造后缀树，搜索T节点下的叶节点数目即为重复次数
 * 原理：如果T在S中重复了两次，则S应有两个后缀以T为前缀，重复次数就自然统计出来了。
 * 
 * 3. 字符串S中的最长重复子串 方案：原理同2，具体做法就是找到最深的非叶节点。
 * 这个深是指从root所经历过的字符个数，最深非叶节点所经历的字符串起来就是最长重复子串。 为什么要非叶节点呢?因为既然是要重复，当然叶节点个数要>=2。
 * 
 * 4. 两个字符串S1，S2的最长公共部分 方案：将S1#S2$作为字符串压入后缀树，找到最深的非叶节点，且该节点的叶节点既有#也有$(无#)。
 * 
 * @author xmrui_000
 *
 */
public class SuffixTree implements Tries {

	private SuffixNode root;

	public SuffixTree() {
		root = new SuffixNode();
	}

	@Override
	public void build(String dictName) {
		String[] words = dictName.trim().split("\\s+");
		for (String word : words)
			insert(word);
	}

	@Override
	public boolean search(String word) {
		if (word == null || word.length() == 0)
			return false;
		System.out.println(search(root, word));
		return true;
	}

	private int search(SuffixNode node, String word) {
		for (int i = 0; i < node.children.size(); i++) {
			SuffixNode child = node.children.get(i);
			String check = child.getKey();
			int ptr = 0;
			int len = check.length() < word.length() ? check.length() : word
					.length();
			while (ptr < check.length() && ptr < word.length()
					&& check.charAt(ptr) == word.charAt(ptr))
				ptr++;

			if (ptr == 0) { // no match
				if (word.charAt(0) < check.charAt(0)) {
					// because children in list is in lexicographic order
					return -1;
				} else
					continue;
			} else {
				if (ptr == len) {
					if (check.length() == word.length()) {
						return child.minStartIndex;
					} else if (check.length() < word.length()) {
						String nextWord = word.substring(ptr);
						int res = search(child, nextWord);
						// return res = -1 or res - current check word's length
						return res == -1 ? -1 : res - check.length();
					} else { // check.length()>word.length()
						return child.minStartIndex;
					}
				} else
					// ptr!=len not complete match
					return -1;
			}
		}
		return -1;
	}

	@Override
	public boolean insert(String word) {
		if (word == null || word.length() == 0)
			return false;
		for (int i = 0; i < word.length(); i++)
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
		if (word == null || word.length() == 0)
			return;
		boolean done = false;
		for (int i = 0; i < node.children.size(); i++) {
			SuffixNode child = node.children.get(i);
			String check = child.getKey();
			int len = check.length() < word.length() ? check.length() : word
					.length();
			int ptr = 0; // check "check string" and "current word"' first
							// different char
			while (ptr < check.length() && ptr < word.length()
					&& check.charAt(ptr) == word.charAt(ptr))
				ptr++;
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
						else { // set current node is a terminal node and
								// update
								// minStart index
							child.terminal = true;
							child.minStartIndex = Math.min(child.minStartIndex,
									idx);
						}
					} else if (word.length() < check.length()) {
						// separate check into two parts according to the
						// ptr!
						String tail = check.substring(ptr);

						// set up new child for current node
						// these children will become grandchildren of
						// current
						// node
						SuffixNode newChild = new SuffixNode(tail);
						newChild.children = child.children;
						newChild.minStartIndex = child.minStartIndex + ptr;
						newChild.terminal = child.terminal;

						// update current node and its children
						child.changeKey(word);
						child.terminal = true;
						// this must be true
						child.minStartIndex = Math
								.min(child.minStartIndex, idx);
						child.children = new LinkedList<SuffixNode>();
						child.children.add(newChild);

					} else { // word.lenght()>check.length
						child.minStartIndex = Math
								.min(child.minStartIndex, idx);
						String wordTail = word.substring(ptr);
						insert(child, wordTail, idx + ptr); // recursion
					}
				} else { // 0<ptr<len
					// separate check into two parts according to the ptr!
					String head = check.substring(0, ptr);
					String tail = check.substring(ptr);

					// set up new child for current node
					// these children will become grandchildren of current
					// node
					SuffixNode newSubchild = new SuffixNode(tail);
					newSubchild.children = child.children;
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

	public String lowestCommonAncester() {

		return "";
	}

	@Override
	public String longestPrefix(String pattern) {
		// TODO Auto-generated method stub
		return null;
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
		tree.insert("misississi");
		tree.printTree();
	}

}
