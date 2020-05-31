package Maze;
/**
 * 
 * @author marjanchowdhury
 * Creates pairs of integers that represent coordinates
 */
public class PairInt {
	//Data Fields
	private int x;
	private int y;
	
	//Constructor
	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//Getters and Setters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	//Other Methods
	public boolean equals(Object p) {
		if (!(p instanceof PairInt)) {return false;}
		PairInt myPair = (PairInt) p;
		return myPair.getX() == this.x && myPair.getY() == this.y;
		
	}
	
	public String toString() {
		return "(" + this.x +"," + this.y + ")";
	}
	
	public PairInt copy() {
		return new PairInt(this.x,this.y);
		
		
	}
	
}
