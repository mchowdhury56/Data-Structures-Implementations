package HuffmanTree;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Implements a Huffman encoding tree.
 * @author Marjan Chowdhury
 */
public class HuffmanTree {

	// ******************** Start of Stub Code ******************** //
	// ************************************************************ //
    /** Node<E> is an inner class and it is abstract.
     * There will be two kinds
     * of Node, one for leaves and one for internal nodes. */
    abstract static class Node implements Comparable<Node>{
        /** The frequency of all the items below this node */
        protected int frequency;
        
        public Node(int freq) {
        	this.frequency = freq;
        }
        
		/** Needed for the Minimum Heap used later in this stub. */
		public int compareTo(Node other) {
			return this.frequency - other.frequency;
		}
    }
    /** Leaves of a Huffman tree contain the data items */
    protected static class LeafNode extends Node {
        // Data Fields
        /** The data in the node */
        protected char data;
        /** Constructor to create a leaf node (i.e. no children) */
        public LeafNode(char data, int freq) {
            super(freq);
            this.data = data;
        }
        /** toString method */
        public String toString() {
            return "[value= "+this.data + ",freq= "+frequency+"]";
        }
    }
    /** Internal nodes contain no data,
     * just references to left and right subtrees */
    protected static class InternalNode extends Node {
        /** A reference to the left child */
        protected Node left;
        /** A reference to the right child */
        protected Node right;

        /** Constructor to create an internal node */
        public InternalNode(Node leftC, Node rightC) {
            super(leftC.frequency + rightC.frequency);
            left = leftC; right = rightC;
        }
        public String toString() {
            return "(freq= "+frequency+")";
        }
    }
	
	// Enough space to encode all "extended ascii" values
	// This size is probably overkill (since many of the values are not "printable" in the usual sense)
	private static final int codex_size = 256;
	
	/* Data Fields for Huffman Tree */
	private Node root;
	
	public HuffmanTree(String s) {
		root = buildHuffmanTree(s);
	}
	
	/**
	 * Returns the frequencies of all characters in s.
	 * @param s
	 * @return
	 */
	public static int[] frequency(String s) {
		int[] freq = new int[codex_size];
		for (char c: s.toCharArray()) {
			freq[c]++;
		}
		return freq;
	}
	
	/**
	 * Builds the actual Huffman tree for that particular string.
	 * @param s
	 * @return
	 */
	private static Node buildHuffmanTree(String s) {
		int[] freq = frequency(s);
		
		// Create a minimum heap for creating the Huffman Tree
		// Note to students: You probably won't know what this data structure
		// is yet, and that is okay.
		PriorityQueue<Node> min_heap = new PriorityQueue<Node>();
		
		// Go through and create all the nodes we need
		// as in, all the nodes that actually appear in our string (have a frequency greater then 0)
		for(int i = 0; i < codex_size; i++) {
			if (freq[i] > 0) {
				// Add a new node (for that character) to the min_heap, notice we have to cast our int i into a char.
				min_heap.add(new LeafNode((char) i, freq[i]));
			}
		}
		
		// Edge case (string was empty)
		if (min_heap.isEmpty()) {
			throw new NullPointerException("Cannot encode an empty String");
		}
		
		// Now to create the actual Huffman Tree 
		// NOTE: this algorithm is a bit beyond what we cover in cs284, 
		// you'll see this in depth in cs385
		
		// Merge smallest subtrees together
		while (min_heap.size() > 1) {
			Node left = min_heap.poll();
			Node right = min_heap.poll();
			Node merged_tree = new InternalNode(left, right);
			min_heap.add(merged_tree);
		}
		
		// Return our structured Huffman Tree
		return min_heap.poll();
	}
	
	// ******************** End of Stub Code ******************** //
	// ********************************************************** //
	
	/**
	 * Turns array of booleans into bit strings
	 * @param encoding the array of booleans
	 * @return the bit string of the input array
	 */
	public String bitsToString(Boolean[] encoding) {
		// TODO Complete bitsToString method
		String bitString = "";
		for(int i = 0; i<encoding.length; i++) {
			if(encoding[i]) {//the bit element is true which corresponds to 1
				bitString += "1";
			}else { // false which corresponds to 0
				bitString += "0";
			}
		}
		return bitString;
	}
	/**
	 * Prints preorder traversal of the whole Huffman tree
	 */
	public String toString() {
		// TODO Complete toString method (see assignment specification)
		// HINT: Might need helper method for preOrderTraversal
		return toString(0, root);
    }
	/**
	 * Prints the preorder traversal of a tree based on starting node
	 * @param l the current level of the tree
	 * @param current the current node being printed
	 * @return a StringBuilder for toString method to print
	 */
	private String toString(int l, Node current) {
		StringBuilder r = new StringBuilder();
		
		for(int i = 0; i<l; i++) {
			r.append("\t");
		}
		if(current instanceof InternalNode) {
			InternalNode internalcurrent = (InternalNode) current;
			r.append(current.toString()+"\n");
			r.append(toString(l+1, internalcurrent.left));
			r.append(toString(l+1, internalcurrent.right));
		}else {
			r.append(current.toString()+"\n");
		}
		return r.toString();
	}
	/**
	 * Converts an encoded string into a regular string
	 * @param coding the sequence of bits in the form of an array of booleans
	 * @return the output string from decoding the array
	 */
	public String decode(Boolean[] coding) {
		// TODO Complete decode method
		String bitString = bitsToString(coding);//turn the array into a bitstring
		String result = "";
		Node current = root;
		for(int i = 0; i < bitString.length(); i++ ) {
			if(bitString.charAt(i)=='0') {//go left
				current = ((InternalNode) current).left;
			}else {//go right
				current = ((InternalNode) current).right;
			}
			if(current instanceof LeafNode) {//reached a leaf to append to string
				result += ((LeafNode) current).data;
				current = root;
			}
			if (i + 1 == bitString.length() && current != root) {//the encoding is invalid
				throw new IllegalArgumentException();
			}
			
		}
		return result;
	}
	/**
	 * Turns a given string and encodes it into an array of booleans based on the huffman tree
	 * @param inputText the given string
	 * @return the encoded string
	 */
	public Boolean[] encode(String inputText) {
		String result = "";
		for(int i = 0; i<inputText.length(); i++) {
			String lettercode = encode(inputText.charAt(i), root, "");//convert each letter into a bitstring
			if(lettercode.length()==0) {//empty string means letter could not be encoded
				throw new IllegalArgumentException();
			}else {//add each bitstring to the main bitstring
				result+= lettercode;
			}
			
		}
		Boolean[] finalresult = new Boolean[result.length()];//turn the bitstring into booleans
		for(int k= 0; k<result.length(); k++) {
			if(result.charAt(k) == '0') {
				finalresult[k] = false;
			}else {
				finalresult[k]=true;
			}
		}
		return finalresult;
	}
	/**
	 * Helper function to encode each individual letter
	 * @param letter the letter of the string that is being encoded
	 * @param current the node of the huffman tree being examined starting from the root
	 * @param result the encoded letter based on the huffman tree
	 * @return the encoded letter's bitstring
	 */
	private String encode(char letter, Node current, String result) {
		if(current == null) {//the recursive calls cannot find the letter 
			return "";
		}
		else if(current instanceof LeafNode) {
			if(((LeafNode)current).data == letter) {//letter was found and encoded
				return result;
			}else{//leaf was reached but it was not the desired letter
				return "";
			}
		}else {//traverse through the huffman tree while creating the encoding
			return encode(letter,((InternalNode)current).left,result+"0") + 
					encode(letter,((InternalNode)current).right,result+"1");
		}
	}
	
	/**
	 * Uses a dictionary to to make encoding more efficient
	 * @param inputText the given string
	 * @return the encoded string
	 */
	public Boolean[] efficientEncode(String inputText) {
		// TODO Complete efficientEncode method
		// NOTE: Should only go through the tree once.
		HashMap<Character,String> map = new HashMap<Character,String>();
		efficientEncode("", map, root);//store values of letters in huffman tree into dictionary
		String result = "";
		for(int i = 0; i<inputText.length(); i++) {
			if(!map.containsKey(inputText.charAt(i))){//the letter does not exist in the dictionary and tree
				throw new IllegalArgumentException();	
			}
			result += map.get(inputText.charAt(i));//add the value associated to letter to the main bitstring
			
		}
		Boolean[] finalresult = new Boolean[result.length()];//turn the bitstring into booleans
		for(int k= 0; k<result.length(); k++) {
			if(result.charAt(k) == '0') {
				finalresult[k] = false;
			}else {
				finalresult[k]=true;
			}
		}
		return finalresult;
	}
	/**
	 * helper function to create the dictionary
	 * @param code the encoding of the letter based on the huffman tree
	 * @param map the dictionary that will contain the encoding for every letter in the huffman tree
	 * @param current the current node being examined in the tree starting from the root
	 */
	private void efficientEncode(String code, HashMap<Character,String> map, Node current) {
		if(current == null) {//letter was not found
			return;
		}
		else if(current instanceof LeafNode) {//letter was found and its encoding is stored in the dictionary
			map.put(((LeafNode)current).data, code);
		}else {//traverse through the huffman tree while building the letter's encoding
			efficientEncode(code+"0",map,((InternalNode)current).left);
			efficientEncode(code+"1",map,((InternalNode)current).right);
		}
	}
	
	public static void main(String[] args) {
		// Code to see if stuff works...
		String s = "Some string you want to encode";
		HuffmanTree t = new HuffmanTree(s); // Creates specific Huffman Tree for "s"
		// Now you can use encode, decode, and toString to interact with your specific Huffman Tree
		Boolean[] u = t.encode(s);
		System.out.println(t.toString());
		System.out.println(Arrays.toString(u));
		System.out.println(t.bitsToString(u));
		System.out.println(t.decode(u));
		System.out.println(Arrays.toString(t.encode("mom")));
		 int[] data = new int[10];
	     for(int i = 0; i <data.length; i++ ) {
	    	 System.out.println(data[i]);
	    	 }
		
	}
}
