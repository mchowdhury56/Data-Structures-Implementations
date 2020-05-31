package IDLList;
import java.util.ArrayList;

/**
 * This class creates a doubly linked list with fast indexing
 * @author Marjan Chowdhury
 */
public class IDLList<E> {
	
	/**
	 * This class creates the nodes used in linked lists
	 * @param <F> the same generic type as E
	 */
	private class Node<F>{
		//data fields
		private F data;
		private Node<F> next;
		private Node <F> prev;
		
		//constructors
		/**
		 * creates a Node holding elem
		 * @param elem the data that the Node will contain
		 */
		Node (F elem){
			this.data=elem;
			next=null;
			prev = null;
		}
		/**
		 * creates a Node holding elem as well as a pointer to the next and previous Node
		 * @param elem the data the Node will contain
		 * @param prev the Node before itself that it will point to
		 * @param next the Node after itself that it will point to
		 */
		Node (F elem, Node<F> prev, Node<F> next){
			this.data=elem;
			this.prev = prev;
			this.next=next;
		}		
		
	}
	
	//data fields
	private Node<E> head;
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;
	
	//constructor
	/**
	 * creates an empty doubly linked list
	 * requires index maintenance
	 */
	public IDLList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
		this.indices = new ArrayList<Node<E>>();
		
	}
	
	//operations 
	
	/**
	 * Adds elem at position index uses index for fast access
	 * requires index maintenance
	 * @param index the position where elem will be added
	 * @param elem the data the Node being added will contain
	 * @return true if operation is successful
	 */
	public boolean add (int index, E elem) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
			
		}
		if(index==0|| size == 0) {//add to the front
			return add(elem);
		}
		if(index==size) {//add to the end
			return append(elem);
		}else {
		Node<E> newNode = new Node<E>(elem, indices.get(index).prev, indices.get(index));
		indices.get(index).prev.next = newNode;
		indices.get(index).prev = newNode;
		indices.add(index, newNode);
		size++;
		return true;
		}
		
	}
	/**
	 * Adds elem at the head of the list
	 * requires index maintenance
	 * @param elem the data the Node being added will contain
	 * @return true if operation is successful
	 */
	public boolean add (E elem){
		if(size == 0) {//if the list is empty it becomes the head and tail
			head = new Node<E>(elem);
			tail = head;

		}else {
		Node<E> newNode = new Node<E>(elem,null,head);
		head.prev = newNode;
		head = newNode;
		}
		indices.add(0, head);
		size++;
		return true;
	}
	/**
	 * Adds elem as the last new element of the list
	 * requires index maintenance
	 * @param elem the data the Node being added will contain
	 * @return true if operation is successful
	 */
	public boolean append (E elem) {
		if(size==0) {//if the list is empty then this element will become the front of the list
			return add(elem);
		}else {
		Node<E> newNode = new Node<E>(elem,tail,null);
		tail.next = newNode;
		tail = newNode;
		indices.add(size, newNode);
		size++;
		return true;
		}
	}
	/**
	 * Uses fast indexing to obtain the object
	 * @param index the desired object's position from the head
	 * @return the object's data
	 */
	public E get (int index) {
		if(index< 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
			
		}
		if(index==0) {// get first element aka head
			getHead();
		}
		if(index==size-1) {// get end element aka last
			getLast();
		}
		return indices.get(index).data;
	}
	/**
	 * @return the object at the head
	 */
	public E getHead () {
		return head.data;
	}
	/**
	 * @return the object at the tail
	 */
	public E getLast () {
		return tail.data;
	}
	/**
	 * @return the list's size
	 */
	public int size() {
		return this.size;
	}
	/**
	 * Gets the element at the head after removing it
	 * requires index maintenance
	 * @return the element at the head
	 */
	public E remove() {
		if(size==0) {
			throw new IllegalStateException();
		}
		E temp = getHead();
		if(size==1) {// removing the only element in the list makes the list empty
			head = null;
			tail = null;
			
		}else {
			head.next.prev=null;
			head = head.next;
		}
		indices.remove(0);
		size--;
		return temp;
	}
	/**
	 * Gets the element at the tail after removing it
	 * requires index maintenance
	 * @return the element at the tail
	 */
	public E removeLast () {
		if(size==0) {
			throw new IllegalStateException();
		}
		if(size==1) {//if there is only 1 element in the list it is also the first element
			return remove();
		}else {
			E temp = getLast();
			tail.prev.next = null;
			tail = tail.prev;
			indices.remove(size-1);
			size--;
			return temp;
		}
	}
	/**
	 * Removes the element at the position index uses index for fast access
	 * requires index maintenance
	 * @param index the position of the element that is being removed
	 * @return the element that was removed
	 */
	public E removeAt (int index) {
		if(size==0) {
			throw new IllegalStateException();
		}
		if(index< 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
			
		}
		if(index==0|| size==1) {//remove the first element
			return remove();
		}
		if(index==size-1) {//remove the last element
			return removeLast();
		}else {
			E temp = get(index);
			Node<E> removed = indices.get(index);
			removed.prev.next = removed.next;
			removed.next.prev = removed.prev;
			removed.next = null;
			removed.prev = null;
			indices.remove(index);
			size--;
			return temp;
		}
	}
	/**
	 * Removes the first occurrence of the element in the list
	 * requires index maintenance
	 * @param elem the element that needs to be removed
	 * @return true if the element was removed false if the element was not in the list
	 */
	public boolean remove (E elem) {
		if(size==0) {
			throw new IllegalStateException();
		}else {
			for(int i = 0; i<size; i++) {
				if (get(i).equals(elem)){
					removeAt(i);
					return true;
				}
			}
			return false;
		}
		
	}
	/**
	 * Overrides toString to return a string representation of the list 
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[");
		Node<E> current = head;
		if(size == 1) {
			s.append(current.data.toString());
		}else {
		while (current!=null) {
			s.append(current.data.toString()+",");
			current = current.next;
		}
		}
		s.append("]");
		return s.toString();
	}
	
}
