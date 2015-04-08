package tries.DS;

import tries.Interface.Tries;

public class PrefixTree implements Tries {

	private PrefixNode root;

	public PrefixTree() {
		root = new PrefixNode();
	}

	@Override
	public void build(String dictName) {
		String[] words = dictName.trim().split("\\s+");
		for (String word : words)
			insert(word);
	}

	@Override
	public boolean search(String word) {
		return search(root, word);
	}

	private boolean search(PrefixNode node, String word) {
		if (word.length() == 0) {
			return node.wordCount != 0;
		} else {
			char c = Character.toLowerCase(word.charAt(0));
			int idx = c - 'a';
			if (node.children[idx] != null)
				return search(node.children[idx], word.substring(1));
			// dfs for the rest of alphabets of this word
			else
				return false;
		}
	}

	@Override
	public boolean insert(String word) {
		if (word == null || word.length() == 0)
			return false;
		insert(root, word);
		return true;
	}

	private void insert(PrefixNode node, String word) {
		if (word.length() == 0) {
			node.prefix++;
			node.wordCount++; // this means this node is the end of certain word
			return;
		} else {
			node.prefix++; // previous alphabet count
			char c = Character.toLowerCase(word.charAt(0));
			int idx = c - 'a';
			if (node.children[idx] == null)
				node.children[idx] = new PrefixNode();
			insert(node.children[idx], word.substring(1)); // dfs for the rest
															// of alphabets of
															// this word
		}
	}

	@Override
	public String longestPrefix(String pattern) {
		int len = 0;
		PrefixNode node = root;
		while (len < pattern.length()) {
			char c = Character.toLowerCase(pattern.charAt(len));
			int idx = c - 'a';
			if(node.children[idx]==null)
				break;
			node = node.children[idx];
			len++;
		}
		return pattern.substring(0, len);
	}

	public int countPrefix(String prefix) {
		return countPrefix(root, prefix).prefix;
	}

	/**
	 * Input prefix to get how many words has this prefix
	 * 
	 * @param node
	 * @param prefix
	 * @return
	 */
	private PrefixNode countPrefix(PrefixNode node, String prefix) {
		if (prefix.length() == 0)
			return node;
		else {
			char c = Character.toLowerCase(prefix.charAt(0));
			int idx = c - 'a';
			if (node.children[idx] != null)
				return countPrefix(node.children[idx], prefix.substring(1));
			else
				return null;
		}
	}

	public int countWords(String word) {
		return countWords(root, word);
	}

	private int countWords(PrefixNode node, String wordSegment) {
		if (wordSegment.length() == 0) { // reach the last character of the word
			return node.wordCount;
		}

		char c = wordSegment.charAt(0);
		int index = c - 'a';
		if (node.children[index] == null) { // the word does NOT exist
			return 0;
		} else {
			return countWords(node.children[index], wordSegment.substring(1));
		}
	}

	@Override
	public boolean delete(String word) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {
		PrefixTree prefixTree = new PrefixTree();
		prefixTree.build("the them their");

		// System.out.println(prefixTree.countWords("the"));
		System.out.println(prefixTree.countPrefix("th"));
	}
}
