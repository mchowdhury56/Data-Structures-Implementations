package Treap;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test cases for Treap
 * @author Marjan Chowdhury
 */
class TreapTest {

	@Test
	void test() {
		Treap<Integer> testTree = new Treap <Integer>();
		
		assertEquals(testTree.add (4 ,19), true);
		assertEquals(testTree.add (4 ,19), false);
		assertEquals(testTree.add (2 ,31), true);
		assertEquals(testTree.add (6 ,70), true);
		assertEquals(testTree.add (1 ,84), true);
		assertEquals(testTree.add (2,44), false);
		assertEquals(testTree.add (3 ,12), true);
		assertEquals(testTree.add (6), false);
		assertEquals(testTree.add (5 ,83), true);
		assertEquals(testTree.add (7 ,26), true);
		assertEquals(testTree.add (1), false);
		assertEquals(testTree.add (5), false);
		assertEquals(testTree.toString(),
				"(key=1, priority=84)\n" +
				"  null\n" +
				"  (key=5, priority=83)\n" +
				"    (key=2, priority=31)\n" +
				"      null\n" +
				"      (key=4, priority=19)\n" +
				"        (key=3, priority=12)\n" +
				"          null\n" +
				"          null\n" +
				"        null\n" +
				"    (key=6, priority=70)\n" +
				"      null\n" +
				"      (key=7, priority=26)\n" +
				"        null\n" +
				"        null\n");
		
		assertEquals(testTree.find(6), true);
		assertEquals(testTree.find(11), false);
		
		assertEquals(testTree.delete(10),false);
		assertEquals(testTree.delete(5),true);
		assertEquals(testTree.toString(),
				"(key=1, priority=84)\n" +
				"  null\n" +
				"  (key=6, priority=70)\n" +
				"    (key=2, priority=31)\n" +
				"      null\n" +
				"      (key=4, priority=19)\n" +
				"        (key=3, priority=12)\n" +
				"          null\n" +
				"          null\n" +
				"        null\n" +
				"    (key=7, priority=26)\n" +
				"      null\n" +
				"      null\n");
		
		assertEquals(testTree.add(5,83),true);
		assertEquals(testTree.toString(),
				"(key=1, priority=84)\n" +
				"  null\n" +
				"  (key=5, priority=83)\n" +
				"    (key=2, priority=31)\n" +
				"      null\n" +
				"      (key=4, priority=19)\n" +
				"        (key=3, priority=12)\n" +
				"          null\n" +
				"          null\n" +
				"        null\n" +
				"    (key=6, priority=70)\n" +
				"      null\n" +
				"      (key=7, priority=26)\n" +
				"        null\n" +
				"        null\n");
		
		assertEquals(testTree.delete(1),true);
		assertEquals(testTree.toString(),
				"(key=5, priority=83)\n" +
				"  (key=2, priority=31)\n" +
				"    null\n" +
				"    (key=4, priority=19)\n" +
				"      (key=3, priority=12)\n" +
				"        null\n" +
				"        null\n" +
				"      null\n" +
				"  (key=6, priority=70)\n" +
				"    null\n" +
				"    (key=7, priority=26)\n" +
				"      null\n" +
				"      null\n");
		
		Treap<Integer> testTree2 = new Treap <Integer>();
		assertEquals(testTree2.find(6), false);
		assertThrows(NullPointerException.class, () -> testTree.add(null));
		
	}

}
