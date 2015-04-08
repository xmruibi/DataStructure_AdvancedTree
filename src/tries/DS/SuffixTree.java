package tries.DS;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(String word) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param node
	 * @param word IS the substring of input word
	 * @param idx mark the start char's index
	 */
	private void insert(SuffixNode node, String word, int idx) {
		boolean done = false;
		for (SuffixNode child : node.children) {
			String check = child.key;
			int len = check.length()<word.length()?check.length():word.length();
			int ptr=0; // check "check string" and "current word"' first different char
			while(check.charAt(ptr) == word.charAt(ptr))
				ptr++;
			
			if(ptr==0){ // no match 
				
			}else{
				if(ptr==len) // entirely match
				{}
			}
		}

	}

	@Override
	public boolean delete(String word) {
		// TODO Auto-generated method stub
		return false;
	}

}
