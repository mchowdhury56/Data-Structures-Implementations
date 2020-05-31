package HuffmanTree;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Test cases for HuffmanTree
 * @author Marjan Chowdhury
 */
class HuffmanTest {

	@Test
	void test() {
		
		String s = "Some";
		HuffmanTree t = new HuffmanTree(s);
		Boolean[] u = t.encode(s);
		Boolean[] v = t.efficientEncode(s);
		
		assertEquals(t.toString(),
				"(freq= 4)\n" +
				"\t(freq= 2)\n" +
				"\t\t[value= S,freq= 1]\n" +
				"\t\t[value= o,freq= 1]\n" +
				"\t(freq= 2)\n" +
				"\t\t[value= m,freq= 1]\n" +
				"\t\t[value= e,freq= 1]\n");
		
		assertEquals(Arrays.toString(u),"[false, false, false, true, true, false, true, true]");
		assertEquals(t.bitsToString(u),"00011011");
		assertEquals(t.decode(u),"Some");
		
		assertEquals(Arrays.toString(v),"[false, false, false, true, true, false, true, true]");
		assertEquals(t.bitsToString(v),"00011011");
		assertEquals(t.decode(v),"Some");
		
		//not all letters are used
		Boolean[] m = t.encode("moe");
		Boolean[] n = t.efficientEncode("moe");
		assertEquals(Arrays.toString(m),"[true, false, false, true, true, true]");
		assertEquals(Arrays.toString(n),"[true, false, false, true, true, true]");
		assertEquals(t.bitsToString(m),"100111");
		assertEquals(t.bitsToString(n),"100111");
		assertEquals(t.decode(m),"moe");
		assertEquals(t.decode(n),"moe");
		
		//empty string
		Boolean[] e = t.encode("");
		Boolean[] f = t.efficientEncode("");
		assertEquals(Arrays.toString(e),"[]");
		assertEquals(Arrays.toString(f),"[]");
		assertEquals(t.bitsToString(e),"");
		assertEquals(t.bitsToString(f),"");
		assertEquals(t.decode(e),"");
		assertEquals(t.decode(f),"");
		
		//letters are used twice
		Boolean[] a = t.encode("moom");
		Boolean[] b = t.efficientEncode("moom");
		assertEquals(Arrays.toString(a), 
				"[true, false, false, true, false, true, true, false]");
		assertEquals(Arrays.toString(b), 
				"[true, false, false, true, false, true, true, false]");
		assertEquals(t.bitsToString(a),"10010110");
		assertEquals(t.bitsToString(b),"10010110");
		assertEquals(t.decode(a),"moom");
		assertEquals(t.decode(b),"moom");
		
		//starting from created boolean array
		Boolean[] x = {true, false, false, true, true, false};
		assertEquals(t.bitsToString(x),"100110");
		assertEquals(t.decode(x),"mom");
		
		//invalid encoding
		Boolean[] y = {true, false, false, true, false, true, true, false, true};
		assertThrows(IllegalArgumentException.class, () -> t.encode("moon"));
		assertThrows(IllegalArgumentException.class, () -> t.efficientEncode("moon"));
		assertThrows(IllegalArgumentException.class, () -> t.decode(y));		
		
		String ss = "Some string you want to encode";
		HuffmanTree tt = new HuffmanTree(ss);
		assertThrows(IllegalArgumentException.class, () -> tt.decode(y));
	}

}
