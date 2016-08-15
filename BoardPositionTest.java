import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class BoardPositionTest {
	//To test a random board of any length, or to test a custom board of your choosing, run the program.
	@Test
	public void testConstructor(){
		int[] arr = new int[]{8, 0, 3, 5, 2, 4, 1, 6, 7};
		BoardPosition apple = new BoardPosition(arr, null, PuzzleSolver.integerArrayToHash(arr));	
		System.out.println(apple);
	}
	@Test
	public void testGenerateChildren(){
		int[] arr = new int[]{8, 0, 3, 5, 2, 4, 1, 6, 7};
		BoardPosition apple = new BoardPosition(arr, null, PuzzleSolver.integerArrayToHash(arr));
		
		ArrayList<BoardPosition> children = apple.generateChildren();
		for(int k = 0; k < children.size(); k++){
			System.out.println(children.get(k));
		}
		assertEquals("The given boardPosition should return three children.", 3, children.size());
	}

	@Test
	public void testCostFunction(){
		int[] arr = new int[]{8, 0, 3, 5, 2, 4, 1, 6, 7};
		BoardPosition apple = new BoardPosition(arr, null, PuzzleSolver.integerArrayToHash(arr));

		ArrayList<BoardPosition> children = apple.generateChildren();
		for(int k = 0; k < children.size(); k++){
			System.out.println("Child cost: "+children.get(k).cost);
		}

		assertTrue("The cost of a BoardPosition's children should be greater than the cost of the given BoardPosition", children.get(0).cost>apple.cost);
		
	}
	
	@Test
	public void testManhattanFunction(){
		int[] arr = new int[]{8, 0, 3, 5, 2, 4, 1, 6, 7};
		int[] endArr = new int[arr.length];
		for(int k = 0; k < arr.length; k++){
			endArr[k] = k+1;
		}
		endArr[arr.length-1] = 0;
		BoardPosition apple = new BoardPosition(arr, null, PuzzleSolver.integerArrayToHash(endArr));
		
		assertEquals("The given BoardPosition should return a Manhattan value of 13 when compared to an endGame board.", 13, apple.manhattan);
		
	}

}
