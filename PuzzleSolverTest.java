import static org.junit.Assert.*;

import org.junit.Test;

public class PuzzleSolverTest {
	@Test
	public void testConstructor(){
		int[] arr = new int[]{8, 0, 3, 5, 2, 4, 1, 6, 7};
		int[] endArr = new int[arr.length];
		for(int k = 0; k < arr.length; k++){
			endArr[k] = k+1;
		}
		endArr[arr.length-1] = 0;
		
		PuzzleSolver testPS = new PuzzleSolver(arr, endArr, 0);
		assertEquals("A new PuzzleSolver has been instantiated. Based on the given parameters, Puzzlesolver constructs the starting boardPosition and solves for the given end boardPosition based on one of three algorithms.", testPS.movesMade.size(), 23);
	}
	
	@Test
	public void testRandomizer(){
		//By counting the number of 'inversions' a given gameboard has, it can be determined whether or not the
		//given gameboard is solveable.
		
		//PuzzleSolver's generaterRandomGameBoard finds a random gameboard that is garaunteed to be solveable.
		int[] arr = PuzzleSolver.generateRandomGameBoard(9);
		int[] endArr = new int[arr.length];
		for(int k = 0; k < arr.length; k++){
			endArr[k] = k+1;
		}
		endArr[arr.length-1] = 0;
		
		PuzzleSolver testPS = new PuzzleSolver(arr, endArr, 2);
		assertTrue("The randomly generated gameboard can be solved.", testPS.movesMade.size()!=0);
	}
	
	@Test
	public void testDFS(){
		int[] arr = new int[]{8, 0, 3, 5, 2, 4, 1, 6, 7};
		int[] endArr = new int[arr.length];
		for(int k = 0; k < arr.length; k++){
			endArr[k] = k+1;
		}
		endArr[arr.length-1] = 0;
		//1 for DFS
		PuzzleSolver testPS = new PuzzleSolver(arr, endArr, 1);
		//DFS will return more moves than necessary, because DFS is not required to find an efficient path.
		assertEquals("Depth first search when performed on the given boardPosition will return an inefficient move list of length 42741.", testPS.movesMade.size(), 42741);		
	}
	
	@Test
	public void testBFS(){
		int[] arr = new int[]{8, 0, 3, 5, 2, 4, 1, 6, 7};
		int[] endArr = new int[arr.length];
		for(int k = 0; k < arr.length; k++){
			endArr[k] = k+1;
		}
		endArr[arr.length-1] = 0;
		//0 for BFS
		PuzzleSolver testPS = new PuzzleSolver(arr, endArr, 0);
		//BFS will return an efficient path, at the cost of searching many nodes.
		assertEquals("Breadth first search when performed on the given boardPosition will return an efficient move list of length 23.", testPS.movesMade.size(), 23);		
	}
	
	@Test
	public void testAStar(){
		int[] arr = new int[]{8, 0, 3, 5, 2, 4, 1, 6, 7};
		int[] endArr = new int[arr.length];
		for(int k = 0; k < arr.length; k++){
			endArr[k] = k+1;
		}
		endArr[arr.length-1] = 0;
		//2 for AStar
		PuzzleSolver testPS = new PuzzleSolver(arr, endArr, 2);
		//AStar, using the Manhattan heuristic, will return an efficient path, while searching fewer nodes than BFS.
		assertEquals("The AStar algorithm when applied to the given boardPosition will return an efficient move list of length 23.", testPS.movesMade.size(), 23);				
	}
}
