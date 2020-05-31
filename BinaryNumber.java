/**
 * This class creates and adds binary numbers
 * @author Marjan Chowdhury
 */

 
public class BinaryNumber {
	// Data Fields
	private int data[];
	private boolean overflow;

	// Constructors
	/**
	 * This constructor creates a binary number based on an integer input
	 * 
	 * @param length the number of bits that make up the binary number and must be
	 *               >0
	 */
	public BinaryNumber(int length) {
		if (length > 0)
			data = new int[length];
		else
			throw new IllegalArgumentException("Error: integer input must be greater than 0");
	}

	/**
	 * This constructor takes in a string consisting of 0s and 1s and creates a
	 * binary number
	 * 
	 * @param str the string it takes in to create the binary number
	 */
	public BinaryNumber(String str) {
		if(str.isEmpty()) throw new IllegalArgumentException("Error: String input must not be empty");
		data = new int[str.length()];
		for (int i = 0; i <= str.length() - 1; i++) {
			int num = Character.getNumericValue(str.charAt(i));
			data[i] = num;
		}
	}

	// Methods - Basic Operations
	/**
	 * 
	 * @return the length of the BinaryNumber as an integer
	 */
	public int getLength() {
		return data.length;

	}

	/**
	 * 
	 * @param index the index of the BinaryNumber array
	 * @return the digit that is in the corresponding index of the array or error
	 *         message if out of bounds
	 */
	public int getDigit(int index) {
		if (index < 0 || index >= data.length) {
			System.out.println("Index out of bounds");
			return -1;
		} else {
			return data[index];
		}
	}

	/**
	 * 
	 * @return the decimal notation of the Binary Number
	 */
	public int toDecimal() {
		int result = 0;
		for (int i = 0; i <= data.length - 1; i++) {
			result += data[i] * ((int) Math.pow(2, i));
		}
		return result;
	}

	/**
	 * shifts all digits to the right
	 * 
	 * @param amount the number of places to shift to the right
	 */
	public void shiftR(int amount) {
		int[] result = new int[data.length + amount];
		for (int i = 0; i < data.length; i++) {
			result[amount + i] = data[i];
		}
		data = result;

	}

	// Methods - Addition of Binary Numbers
	/**
	 * adds two Binary Numbers together
	 * 
	 * @param aBinaryNumber the other BinaryNumber that is added to the received
	 *                      BinaryNumber
	 */
	public void add(BinaryNumber aBinaryNumber) {
		if (data.length != aBinaryNumber.getLength()) {
			System.out.println("Lengths do not coincide.");
		} else {
			for (int i = 0; i <= data.length - 1; i++) {
				data[i] += aBinaryNumber.getDigit(i);
				if (data[data.length - 1] > 1) {
					this.overflow = true;
				} else {
					if (data[i] == 2) {
						data[i] = 0;
						data[i + 1] += 1;
					} else if (data[i] == 3) {
						data[i] = 1;
						data[i + 1] += 1;
					}
				}
			}
		}
	}

	/**
	 * Clears the Overflow message
	 */
	public void clearOverflow() {
		this.overflow = false;
	}

	/**
	 * turns a BinaryNumber into a String
	 */
	public String toString() {
		if (this.overflow == true) {
			return "Overflow";
		} else {
			String result = "";
			for (int i = 0; i < data.length; i++) {
				result += data[i];
			}
			return result;
		}
	}

}
