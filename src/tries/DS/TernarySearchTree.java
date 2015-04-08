package tries.DS;

import tries.Interface.Tries;

public class TernarySearchTree implements Tries {

	private TernaryNode root;
	private int size;
	
	public TernarySearchTree() {
		this.size = 0;
	}

	@Override
	public void build(String dictName) {
		String[] words = dictName.trim().split("\\s+");
		for (String word : words)
			insert(word);
	}

	
	public boolean contain(String word) {
		return search(word);
	}
	
	@Override
	public boolean search(String word) {
		if (word == null || word.length() == 0)
			return false;
		TernaryNode res = search(root, word, 0);
		if (res== null)
			return false;
		return res.getValue()!=null;
	}

	private TernaryNode search(TernaryNode node, String word, int index) {
		if (node == null)
			return null;
		if (node.getLabel() > word.charAt(index)) {
			return search(node.left, word, index);
		} else if (node.getLabel() < word.charAt(index)) {
			return search(node.right, word, index);
		} else if (index + 1 < word.length()) {
			return search(node.centre, word, index + 1);
		} else
			return node;
	}

	@Override
	public boolean insert(String word) {
		if (word == null || word.length() == 0)
			return false;
		if(contain(word))
			return true;
		root = insert(root, word, 0);
		size++;
		return true;
	}

	/**
	 * DFS insert node in ternary structure
	 * 
	 * @param node
	 * @param word
	 * @param index
	 */
	private TernaryNode insert(TernaryNode node, String word, int index) {
		if (node == null) {
			node = new TernaryNode(word.charAt(index));
		}
		if (node.getLabel() > word.charAt(index))
			node.left = insert(node.left, word, index);
		else if (node.getLabel() < word.charAt(index))
			node.right = insert(node.right, word, index);
		else if (index + 1 < word.length())
			// be aware to preventing over index
			node.centre = insert(node.centre, word, index + 1);
		else
			node.setValue(word);
		return node;
	}

	@Override
	public String longestPrefix(String pattern) {
		int index = 0;
		TernaryNode node =root;
		while(index<pattern.length()){
			if(node==null)
				break;
			if(pattern.charAt(index)==node.getLabel()){
				node = node.centre;
				index++;
			}else if(pattern.charAt(index)<node.getLabel())
				node = node.left;
			else
				node = node.right;			
		}
		return pattern.substring(0, index);
	}
	
	
	@Override
	public boolean delete(String word) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {
		TernarySearchTree tree = new TernarySearchTree();
		tree.build("that their them they teach mean");
		System.out.println(tree.longestPrefix("teacsm"));
	}
}
