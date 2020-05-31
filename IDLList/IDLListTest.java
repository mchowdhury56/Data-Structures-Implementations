package IDLList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * Test cases for IDLList
 * @author MarjanChowdhury
 *
 */


 class IDLListTest {

	/**
	 * testing public boolean add (int index, E elem)
	 */
	@Test
	void add1Test() {
		IDLList<Integer> test = new IDLList<Integer>();
		test.add(0,1);//add at the beginning
		assertEquals(test.toString(),"[1]");
		test.add(1,2);//add at the end
		assertEquals(test.toString(),"[1,2,]");
		test.add(1,3);//add at the middle
		assertEquals(test.toString(),"[1,3,2,]");
		assertEquals(test.getHead().toString(), "1"); //testing getHead
		assertEquals(test.getLast().toString(), "2"); //testing getLast
		assertEquals(test.get(1).toString(), "3"); //testing get
		assertEquals(test.add(1,3),true);// testing to see if the method returns true
		assertEquals(test.size(), 4);//testing size
	}

	/**
	 * testing public boolean add (E elem)
	 */
	@Test
	void add2Test() {
		IDLList<Integer> test = new IDLList<Integer>();
		test.add(1);
		assertEquals(test.toString(),"[1]");
		test.add(2);
		assertEquals(test.toString(),"[2,1,]");
		test.add(3);
		assertEquals(test.toString(),"[3,2,1,]");
		assertEquals(test.add(4),true);// testing to see if the method returns true
		assertEquals(test.toString(),"[4,3,2,1,]");
	}
	
	/**
	 * testing public boolean append (E elem)
	 */
	@Test
	void appendTest() {
		IDLList<Integer> test = new IDLList<Integer>();
		test.append(1);
		assertEquals(test.toString(),"[1]");
		test.append(2);
		assertEquals(test.toString(),"[1,2,]");
		test.append(3);
		assertEquals(test.toString(),"[1,2,3,]");
		assertEquals(test.append(4),true);// testing to see if the method returns true
		assertEquals(test.toString(),"[1,2,3,4,]");
		
	}
	
	/**
	 * testing public E remove()
	 */
	@Test
	void remove1Test() {
		IDLList<Integer> test = new IDLList<Integer>();
		test.add(1);//create an IDLList with one element [1]
		test.remove();//make the IDLList empty
		assertEquals(test.toString(),"[]");
		test.add(1); test.add(2); test.add(3); // creates a IDLList [3,2,1,]
		assertEquals(test.remove().toString(),"3");//testing to see if remove returns head.data
		assertEquals(test.toString(),"[2,1,]");
	}
	
	/**
	 * testing public E removeLast ()
	 */
	@Test
	void removeLastTest() {
		IDLList<Integer> test = new IDLList<Integer>();
		test.add(1);//create an IDLList with one element [1]
		test.removeLast();//make the IDLList empty
		assertEquals(test.toString(),"[]");
		test.add(1); test.add(2); test.add(3); // creates a IDLList [3,2,1,]
		assertEquals(test.removeLast().toString(),"1");//testing to see if remove returns head.data
		assertEquals(test.toString(),"[3,2,]");
	}
	
	/**
	 * testing public E removeAt (int index)
	 */
	@Test
	void removeAtTest() {
		IDLList<Integer> test = new IDLList<Integer>();
		test.add(1); test.add(2); test.add(3); // creates a IDLList [3,2,1,]
		assertEquals(test.removeAt(0).toString(),"3"); //remove 1st element and return it
		assertEquals(test.toString(),"[2,1,]");
		assertEquals(test.removeAt(1).toString(),"1");//remove last element and return it
		assertEquals(test.toString(),"[2]");
		test.add(1); test.add(2); test.add(3); //makes the list [3,2,1,2,]
		assertEquals(test.removeAt(2).toString(),"1");//remove middle element and return it
		assertEquals(test.toString(),"[3,2,2,]");
	}
	
	/**
	 * testing public boolean remove (E elem)
	 */
	@Test
	void remove2Test() {
		IDLList<Integer> test = new IDLList<Integer>();
		test.add(1);//create an IDLList with one element [1]
		assertEquals(test.remove(1),true);//make the IDLList empty and returns true
		assertEquals(test.toString(),"[]");
		test.add(1); test.add(2); test.add(2); test.add(3); // creates a IDLList [3,2,2,1,]
		assertEquals(test.remove(2),true);// remove the first 2 and return true
		assertEquals(test.toString(),"[3,2,1,]");
		assertEquals(test.remove(4),false);// try to remove an element not in the list and return false
		test.remove(1);
		assertEquals(test.toString(),"[3,2,]");
		
	}

}
