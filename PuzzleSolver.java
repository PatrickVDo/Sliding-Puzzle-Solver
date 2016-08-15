import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class PuzzleSolver {
	Hashtable<BoardPosition, BoardPosition> hashTable; 
	LinkedList<BoardPosition> queue = new LinkedList<BoardPosition>();
	LinkedList<BoardPosition> movesMade = new LinkedList<BoardPosition>();
	BoardPosition startPosition;
	BoardPosition goalPosition;
	BoardPosition endGame;
	
	//Constructor, takes a starting boardPosition, a goal boardPosition, and a number that indicates which search type should take place
	public PuzzleSolver(int[] startPos, int[] endPos, int searchType){
		hashTable = new Hashtable<BoardPosition, BoardPosition>(100);
		hashTable.clear();
		goalPosition = new BoardPosition(endPos, null, integerArrayToHash(endPos));
		Hashtable<Integer, Integer> goalHash = integerArrayToHash(endPos);
		startPosition = new BoardPosition(startPos, null, goalHash);

		if(searchType == 0){
			endGame = BFS();
		} else if(searchType == 1){
			endGame = DFS();
		} else if(searchType == 2){
			endGame = AStar();
		} else {
			System.out.println("Invalid search type, defaulting to DFS");
			endGame = DFS();
		}
		movesMade = findMovesMade();
	}
	
	//generate a random gameboard by checking if the gameboard has a valid number of inversions
	public static int[] generateRandomGameBoard(int size){
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		for(int k = 0; k < size; k++){
			arrList.add(k);
		}
	    Collections.shuffle(arrList);
	    int[] arr = new int[arrList.size()];
	    for(int k = 0; k < arrList.size(); k++){
	    	arr[k] = arrList.get(k);
	    }
	    while(!validInversions(arr)){
		    Collections.shuffle(arrList);
		    for(int k = 0; k < arrList.size(); k++){
		    	arr[k] = arrList.get(k);
		    }	    	
	    }
	    return arr;
	}
	
	//returns an error message if the gameboard is invalid
	public static String checkValidBoard(int[] arr){
		if((int)Math.sqrt(arr.length) != Math.sqrt(arr.length)){
			return "The board you gave is of an invalid length. All boards must be perfect squares in length when the blank is included (lengths 4, 9, 16...)";
		}
		Hashtable<Integer, Integer> arrHash = integerArrayToHash(arr);
		for(int k = 0; k < arr.length; k++){
			if(arrHash.get(k) == null){
				return "Invalid board, your board must contain a blank (a zero) and consecutive integers from 1 to (board size - 1)";
			}
		}
		if(!validInversions(arr)){
			return "Invalid board. Your board has an illegal number of inversions";
		}
		
		return "";
	}

	private static int getZeroPos(int[] arr){
		for(int k = 0; k < arr.length; k++){
			if(arr[k] == 0){
				return k;
			}
		}
		return -1;
	}
	
	//checks the number of inversions (numbers with "switched" places in board)
	public static boolean validInversions(int[] arr){
		if(arr.length%2 == 1){
			if(countInversions(arr)%2 == 0){
				return true;
			} else {
				return false;
			}
		} else {
			//inversions are counted differently for boards of even lengths
			int zeroPos = getZeroPos(arr);
			int zeroRow = zeroPos/((int)Math.sqrt(arr.length));
			System.out.println(zeroRow);
			if((zeroRow + countInversions(arr))%2 == 1){
				return true;
			} else {
				return false;
			}
		}
		
	}
	
	public static int countInversions(int[] arr){
		int inversions = 0;
		for(int k = 0; k < arr.length; k++){
			for(int j = k; j < arr.length; j++){
				if(arr[k] > arr[j]){
					if(arr[k] != 0 && arr[j]!= 0){
						inversions+=1;
					}
				}
			}
		}
		return inversions;
	}
	
	//for easy lookup in the manhattan function of a boardposition
	public static Hashtable<Integer, Integer> integerArrayToHash(int[] arr){
		Hashtable<Integer, Integer> hash = new Hashtable<Integer, Integer>();
		for(int k = 0; k < arr.length; k++){
			hash.put(arr[k], k);
		}
		return hash;
	}
	
	//returns the full list of moves made to find the solution
	public LinkedList<BoardPosition> findMovesMade(){
		LinkedList<BoardPosition> movesMade = new LinkedList<BoardPosition>();
		BoardPosition current = endGame;
		if(endGame!=null){
			do {
				movesMade.addFirst(current);
				current = current.parent;
			} while(!current.position.equals(startPosition.position));
		}
		return movesMade;
	}
	
	@Override
	public String toString(){
		String printStr = "";
		for(int k = 0; k < movesMade.size(); k++){
			printStr+=movesMade.get(k).toString()+"\n\n";
		}
		return printStr;
	}
	
	//AStar algorithm using the manhattan function heuristic
	public BoardPosition AStar(){
		PriorityQueue<BoardPosition> pQueue = new PriorityQueue<BoardPosition>();
		pQueue.clear();
		pQueue.add(startPosition);
		hashTable.put(startPosition, startPosition);
		while(!pQueue.isEmpty()){
			BoardPosition apple = pQueue.remove();
			if(apple.equals(goalPosition)){
				return apple;
			}
			ArrayList<BoardPosition> children = apple.generateChildren();
			for(int k = 0; k < children.size(); k++){
				BoardPosition young = children.get(k);
				BoardPosition old = hashTable.get(young);
				if(old == null){
					hashTable.put(young, young);
					pQueue.add(young);
				} else {
					if(young.cost < old.cost){
						old.setParent(young.parent);
					}
				}
			}
		}
		System.out.println("Endposition could not be reached");
		return null;
	}
	
	//DFS algorithm
	public BoardPosition DFS(){
		queue.clear();
		queue.add(startPosition);
		hashTable.put(startPosition, startPosition);
		while(!queue.isEmpty()){
			BoardPosition apple = queue.removeLast();
			if(apple.equals(goalPosition)){
				return apple;
			}
			apple.generateChildren();
			ArrayList<BoardPosition> children = apple.generateChildren();
			for(int k = 0; k < children.size(); k++){
				BoardPosition young = children.get(k);
				BoardPosition old = hashTable.get(young);
				if(old == null){
					hashTable.put(young, young);
					queue.add(young);
				} else {
					if(old.cost > young.cost){
						old.setParent(young.parent);
					}
				}				
			}
		}
		System.out.println("POSITION CANNOT BE REACHED");
		return null;	
	}
	
	//BFS algorithm
	public BoardPosition BFS(){
		queue.clear();
		queue.add(startPosition);
		hashTable.put(startPosition, startPosition);
		while(!queue.isEmpty()){
			BoardPosition apple = queue.pop();
			if(apple.equals(goalPosition)){
				return apple;
			}
			apple.generateChildren();
			ArrayList<BoardPosition> children = apple.generateChildren();
			for(int k = 0; k < children.size(); k++){
				BoardPosition young = children.get(k);
				BoardPosition old = hashTable.get(young);
	
				if(old == null){
					hashTable.put(young, young);
					queue.add(young);
				} else {
					if(old.cost > young.cost){
						System.out.println("this shouldn't happen");
						old.setParent(young.parent);
//						old.parent = young.parent;
//						old.cost = young.cost;
					}
				}
			}
		}
		System.out.println("POSITION CANNOT BE REACHED");
		return null;
	}
}
