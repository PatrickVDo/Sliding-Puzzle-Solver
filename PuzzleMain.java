import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

public class PuzzleMain {
	public static void main(String[] args){
		System.out.println("------------------PROGRAM START-------------------------------");
		System.out.println("This program can be used to solve randomly generated or user generated N-puzzles.");
		System.out.println("This program uses the Java Scanner class to accept input from a user.");
		while(true){
		
		
			Scanner sc = new Scanner(System.in);
			System.out.println("------------------------NEW GAME--------------------");
			System.out.println("Type 0 and press enter to test a randomly generated gameboard.");
			System.out.println("Type 1 and press enter to enter your own gameboard.");
			int apple = sc.nextInt();
			while(apple != 0 && apple != 1){
				System.out.println("Please enter either 0 or 1");
				apple = sc.nextInt();
			}
			if(apple == 0){
				System.out.println("What length would you like your board to be? Only lengths that are non-zero perfect squares (9, 16...) are accepted.");
				int banana = sc.nextInt();
				while((int)Math.sqrt(banana) != Math.sqrt(banana)){
					System.out.println("Please enter a length that is a non-zero perfect square (Example: an 8 puzzle has a length of 9).");
					banana = sc.nextInt();
				}
				int[] startArr = PuzzleSolver.generateRandomGameBoard(banana);
				System.out.println(PuzzleSolver.validInversions(startArr));
				BoardPosition startPosition = new BoardPosition(startArr, null, PuzzleSolver.integerArrayToHash(startArr));
				System.out.println("Your randomly generated gameboard appears below. This gameboard has a valid number of inversions, and is confirmed to be solveable.");
				System.out.println(startPosition);
				System.out.println("How would you like your gameboard to be solved? Enter a key that corresponds to one of the following options:");
				System.out.println("0 : BFS: Warning: will exceed java heapspace on boards over length 9.");
				System.out.println("1 : DFS: Warning: Your solution may be very inefficient. Will exceed java heapspace on boards over length 9.");
				System.out.println("2 : AStar: Faster and more efficient than BFS and DFS, works on 16 and 25 boards. Warning: will exceed java heapspace on boards over length 25.");
				int coconut = sc.nextInt();
				while(coconut != 0 && coconut != 1 && coconut != 2){
					System.out.println("Please enter an integer between 0 and 2.");
				}
				int[] endArr = new int[banana];
				for(int k = 0; k < banana; k++){
					endArr[k] = k+1;
				}
				endArr[banana-1] = 0;
				System.out.println("Running...");
				long beginTime = System.currentTimeMillis();
				PuzzleSolver randomPS = new PuzzleSolver(startArr, endArr, coconut);
				long endTime = System.currentTimeMillis();
				System.out.println("Completed.");
				System.out.println("Runtime: "+(endTime-beginTime)+"ms");
				LinkedList llist = randomPS.movesMade;
				System.out.println(llist.size()+" moves made.");
				System.out.println("Enter 0 to display the full list of moves made.");
				int durian = sc.nextInt();
				while(durian!=0){
					durian = sc.nextInt();
				}
				for(int k = 0; k < llist.size(); k++){
					System.out.println(llist.get(k));
				}				
			} else if(apple == 1){
				System.out.println("What is the length of your custom board? Only lengths that are non-zero perfect squares (9, 16...) are accepted.");
				int banana = sc.nextInt();
				while((int)Math.sqrt(banana) != Math.sqrt(banana)){
					System.out.println("Please enter a length that is a non-zero perfect square (Example: an 8 puzzle has a length of 9).");
					banana = sc.nextInt();
				}
				
				System.out.println("Enter the numbers in your custom gameboard, one at a time, row by row. Use a zero for the blank. \nExample: 8 [enter] 0 [enter] 3 [enter] 5 [enter] 2 [enter] 4 [enter] 1 [enter] 6 [enter] 7");
				System.out.println("The program will check to see if your gameboard is valid. If it is not, it will ask you to input a different gameboard.");
				int[] arr = new int[banana];
				for(int k = 0; k < banana; k++){
					arr[k] = sc.nextInt();
				}
				while(PuzzleSolver.checkValidBoard(arr)!=""){
					System.out.println(PuzzleSolver.checkValidBoard(arr));
					System.out.println("TRY AGAIN");
					for(int k = 0; k < banana; k++){
						arr[k] = sc.nextInt();
					}					
				}
				BoardPosition startPosition = new BoardPosition(arr, null, PuzzleSolver.integerArrayToHash(arr));
				System.out.println("This is your valid custom gameboard.\n");
				System.out.println(startPosition);
				System.out.println("How would you like your gameboard to be solved? Enter a key that corresponds to one of the following options:");
				System.out.println("0 : BFS: Warning: will exceed java heapspace on boards over length 9.");
				System.out.println("1 : DFS: Warning: Your solution may be very inefficient. Will exceed java heapspace on boards over length 9.");
				System.out.println("2 : AStar: Faster than BFS and DFS, works on 16 and 25 boards. Warning: will exceed java heapspace on boards over length 25.");
				int coconut = sc.nextInt();
				while(coconut != 0 && coconut != 1 && coconut != 2){
					System.out.println("Please enter an integer between 0 and 2.");
				}	
				int[] endArr = new int[banana];
				for(int k = 0; k < banana; k++){
					endArr[k] = k+1;
				}
				endArr[banana-1] = 0;
				System.out.println("Running...");
				long beginTime = System.currentTimeMillis();
				PuzzleSolver customPS = new PuzzleSolver(arr, endArr, coconut);
				long endTime = System.currentTimeMillis();
				System.out.println("Completed.");
				System.out.println("Runtime: "+(endTime-beginTime)+"ms");
				LinkedList llist = customPS.movesMade;
				System.out.println(llist.size()+" moves made.");
				System.out.println("Enter 0 to display the full list of moves made.");
				int durian = sc.nextInt();
				while(durian!=0){
					durian = sc.nextInt();
				}
				for(int k = 0; k < llist.size(); k++){
					System.out.println(llist.get(k));
				}	
				
			}
			
			
			
			System.out.println(apple);
		}

//
//		
//		int[] arr2 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 10, 11, 13, 14, 15, 12};
//		int[] arr3 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 8};
//		System.out.println(PuzzleSolver.checkValidBoard(arr3));
////		System.out.println(bp);
////
////		for(int k = 0; k < llbp.size(); k++){
////			System.out.println(llbp.get(k));
////		}
//		long beginTime = System.currentTimeMillis();
////		PuzzleSolver ps = new PuzzleSolver(new int[]{6, 5, 4, 8, 9, 0, 14, 3, 1, 2, 12, 15, 10, 13, 7, 11}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0}, "AStar");
////		PuzzleSolver ps = new PuzzleSolver(new int[]{3, 8, 2, 4, 5, 6, 1, 0, 7}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0}, "AStar");
//		
//		long endTime = System.currentTimeMillis();
////		System.out.println(ps);
////		System.out.println(ps.checkDuplicates(ps.getMovesMade()));
////		System.out.println(ps.hashTable.size());
//		System.out.println("Time elapsed "+(endTime - beginTime));
	}
}
