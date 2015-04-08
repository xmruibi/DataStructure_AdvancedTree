package tries.DS;

import java.util.Arrays;

/**
 * This class is the structure describing prefix Tree
 * And only fit for 26 English alphabets
 * @author xmrui_000
 *
 */
public class PrefixNode {

	int wordCount; // to current character, this branch has how many words
	int prefix; // the prefix alphabets count for current position 
	PrefixNode[] children;

	public PrefixNode() {
		this.wordCount = 0;
		this.prefix = 0;
		this.children = new PrefixNode[26]; 
		Arrays.fill(children, null); // seems not really necessary
	}
}
