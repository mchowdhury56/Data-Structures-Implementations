package Maze;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 * @author Marjan Chowdhury
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    public boolean findMazePath(int x, int y) {
        // COMPLETE HERE FOR PROBLEM 1
    	if(x < 0 || x > maze.getNCols() -1  || y<0 || y > maze.getNRows()-1 ) {//boundaries
    		return false;
    	}
    	if (maze.getColor(x,y) != NON_BACKGROUND) {//if the chosen spot isn't red it's already false
    		return false;
    	}
    	if(x == maze.getNCols() -1 && y == maze.getNRows()-1) {//we landed on the exit spot
    		maze.recolor(x, y, PATH);
    		return true;
    		}
    	maze.recolor(x, y, PATH); //make the red spot black to mark that it is visited
    	
    	boolean up = findMazePath(x, y-1); //keep on going up and respectively 
    	boolean down= findMazePath(x, y+1);
    	boolean left= findMazePath(x-1, y);
    	boolean right= findMazePath(x+1, y);
    	if (!(up || down || left || right)){
    		maze.recolor(x, y, TEMPORARY);
    		return false;
    	}
    	return true;
    	
    }

    // ADD METHOD FOR PROBLEM 2 HERE
    /**
     * Provides all solutions to the maze. Each solution can be a list of coordinates.
     * The coordinates are made by PairInt class
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return An ArrayList of all of the solutions to the maze from that point
     */
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y){
    	ArrayList <ArrayList<PairInt>> result = new ArrayList<ArrayList<PairInt>>();
    	Stack <PairInt> trace = new Stack <PairInt>();
    	findMazePathStackBased (0 ,0 , result , trace );
    	return result ;
    }
    /**
     * 
     * @param x The x-coordinate currently being visited
     * @param y The y-coordinate currently being visited
     * @param result The list of successful paths recorded up to now
     * @param trace The trace of the current path being explored
     */
    public void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
    	if(x < 0 || x > maze.getNCols() -1  || y<0 || y > maze.getNRows()-1 ) {//boundaries
    		return;
    	}
    	if (maze.getColor(x,y) != NON_BACKGROUND) {//if the chosen spot isn't red it's already false
    		return;
    	}
    	if(x == maze.getNCols() -1 && y == maze.getNRows()-1) {//we landed on the exit spot
    		maze.recolor(x, y, PATH);
    		PairInt end = new PairInt(x,y);
    		trace.push(end);
    		ArrayList<PairInt> path = new ArrayList<PairInt>();
    		path.addAll(trace);
    		result.add(path);
    		trace.pop();
    		maze.recolor(x, y, NON_BACKGROUND);
    	}else {
    			maze.recolor(x, y, PATH);
    			PairInt cell = new PairInt(x,y);
    			trace.push(cell);
    			
    			findMazePathStackBased(x, y-1, result, trace);
    			findMazePathStackBased(x, y+1, result, trace);
    			findMazePathStackBased(x-1, y, result, trace);
    			findMazePathStackBased(x+1, y, result, trace);
    			
    			trace.pop();
    			maze.recolor(x, y, NON_BACKGROUND);
    	}
    }
    
    // ADD METHOD FOR PROBLEM 3 HERE
    /**
     * Finds the shortest path in the list of paths
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return The ArrayList of the shortest path
     */
    public ArrayList<PairInt> findMazePathMin(int x, int y){
        ArrayList<ArrayList<PairInt>> solutions = findAllMazePaths(x,y);
        if(solutions.size() == 0) throw new NullPointerException("No solution found");
    	ArrayList<PairInt> result = solutions.get(0);
    	for(int i=1;i<solutions.size();i++) {
    		if(result.size() > solutions.get(i).size()) {
    			result = solutions.get(i);
    		}
    	}
    	return result;
    	
    	
    }
    

    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/
}
/*</listing>*/
