package Treap;
import java.util.Random;
import java.util.Stack;

/**
 * Implements a Treap using Nodes
 * @author Marjan Chowdhury
 */
public class Treap<E extends Comparable<E>> {
	
	private static class Node<E extends Comparable<E>>{
		
		//data fields
		public E data; // key for the search
		public int priority; // random heap priority
		public Node <E> left;
		public Node <E> right;
		
		//constructor
		/**
		 * Creates a Node with pointers to the child node being null
		 * @param data the data stored in the Node. Exception thrown when data is null
		 * @param priority
		 */
		public Node (E data , int priority ) {
			if(data==null) {
				throw new NullPointerException();
			}
			this.data= data;
			this.priority = priority;
			this.left=null;
			this.right=null;
		}
		
		//methods
		/**
		 * performs a right rotation
		 * @return a reference to the root of the result
		 */
		Node <E> rotateRight (){
			if(this.left == null) {
	        	return this;
	        }else {
	        	Node<E> pivot = this.left; 
	        	this.left = pivot.right;
	        	pivot.right = this;
	        	return pivot;
	        }
		}
		/**
		 * performs a left rotation
		 * @return a reference to the root of the result
		 */
		Node <E> rotateLeft (){
			if(this.right == null) {
	        	return this;
	        }else {
	        	Node<E> pivot = this.right; 
	        	this.right = pivot.left;
	        	pivot.left = this;
	        	return pivot;
	        }
		}
		/**
		 * prints the node as a pair with the key and priority
		 */
		public String toString() {
			return "(key=" + this.data +", priority="+ this.priority +")";
		}
	}
	
	//data fields
	private Random priorityGenerator;
	private Node<E> root;
	
	//constructors
	public Treap() {
		this.priorityGenerator = new Random();
		this.root= null;
	}

	public Treap(long seed) {
		this.priorityGenerator = new Random(seed);
		this.root= null;
	}
	
	//methods
	/**
	 * Inserts a new element into the tree as a Node uses helper function with random priority
	 * @param key the data in the Node
	 * @return boolean from helper function
	 */
	boolean add (E key ) {
		int x = priorityGenerator.nextInt();
		return add(key,x);
	}
	/**
	 * Helper function
	 * @param key the data in the Node
	 * @param priority random generated priority
	 * @return true if successfully added, false if there is a node already containing the same key
	 */
	boolean add (E key , int priority ) {
		if(root==null) {
			root = new Node<E>(key, priority);
			return true;
		}
		Node<E> current = root;
		Stack<Node<E>> nodes = new Stack<Node<E>>();
		while(true) {
			if(current.data.compareTo(key)== 0 ) {
				return false;
			}
			nodes.push(current);
			
			if(current.data.compareTo(key) < 0 ) {
				if(current.right == null) {
					current.right = new Node<E>(key, priority);
					current = current.right;
					reheap(current,nodes);
					return true;
				}
				current = current.right;
			} else if(current.data.compareTo(key) > 0 ) {
				if(current.left == null) {
					current.left = new Node<E>(key, priority);
					current = current.left;
					reheap(current,nodes);
					return true;
				}
				current = current.left;
			}
		}
	}
	/**
	 * helper function to restore heap invariant
	 * @param current the node inserted at the bottom of the tree
	 * @param nodes the stack of previous nodes 
	 */
	private void reheap(Node<E> current, Stack<Node<E>> nodes) {
		while(!nodes.empty()) {
			Node<E> parent = nodes.pop();
			if (current.priority < parent.priority) {
				return;
			}
			if (parent.right != null && parent.right == current) {
				parent.rotateLeft();
			} else {
				parent.rotateRight();
			}
			if (!nodes.empty()) {
				if (nodes.peek().left != null && nodes.peek().left == parent) {
					nodes.peek().left = current;
				} else {
					nodes.peek().right = current;
				}
			}
		}
		root = current;
		return;
	}
	/**
	 * deletes a node with the key given from the treap using helper function
	 * @param key the given key
	 * @return true if found and successfully deleted false if key was not found
	 */
	boolean delete(E key) {
		if(!find(key)) {
			return false;
		}else {
	        root = delete(key, root);
	        return true;
		}
    }
	/**
	 * helper function that finds and deletes node through rotation
	 * @param key key of the node to find
	 * @param root the node being examined starting from the root
	 * @return the root with the new tree in each recursive call
	 */
    private Node<E> delete(E key, Node<E> root){
    	if (root.data.compareTo(key) > 0){ 
            root.left = delete(key, root.left);
        }else if(root.data.compareTo(key) < 0){
            root.right = delete(key, root.right);
        }else{
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                root = root.rotateLeft();
                root.left = delete(key, root.left);
                return root;
            } else if (root.right == null) {
                root = root.rotateRight();
                root.right = delete(key, root.right);
                return root;
            } else if (root.left.priority > root.right.priority) {
                root = root.rotateRight();
                root.right = delete(key, root.right);
            } else {
                root = root.rotateLeft();
                root.left = delete(key, root.left);
            }
        }
    	return root;
    }
	/**
	 * Helper function that finds a node in the treap rooted at root like a BST
	 * @param root the root of the tree to search
	 * @param key the given key
	 * @return true if found false otherwise
	 */
	private boolean find (Node <E> root , E key ) {
		if (root  == null){ 
			return  false;
		}
		int  compResult = key.compareTo(root.data);
		if (compResult  == 0) {
			return  true;
		} else if (compResult  < 0) {
			return  find(root.left , key);
		} else {
			return  find(root.right , key);
		}
		
	}
	/**
	 * Finds a node in the treap
	 * @param key given key to find
	 * @return true if found false otherwise
	 */
	public boolean find (E key) {
		return  find(root , key);
	}
	/**
	 * Prints the preorder traversal of a tree based on starting node
	 * @param l the current level of the tree
	 * @param current the current node being printed
	 * @return a StringBuilder for toString method to print
	 */
	private StringBuilder toString(int l, Node<E> current) {
		StringBuilder r = new StringBuilder();
		
		for(int i = 0; i<l; i++) {
			r.append("  ");
		}
		if(current == null) {
			r.append("null\n");
		}else {
			r.append(current.toString()+"\n");
			r.append(toString(l+1, current.left));
			r.append(toString(l+1, current.right));
			
		}
		return r;
	}
	/**
	 * Prints preorder traversal of the whole treap
	 */
	public String toString() {
		return toString(0, root).toString();
	}

}
