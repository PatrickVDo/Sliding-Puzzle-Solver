import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;

public class BoardPosition implements Comparable<BoardPosition>{
	int[] position;
	String positionStr;
	BoardPosition parent;
	Hashtable<Integer, Integer> goalHash;
	
	int cost;
	int manhattan;
	
	int boardSize;
	int col;
	int row;
		
	//Takes an integer array representing the starting position, the parent BoardPosition, and takes an hashtable representing the end state of the table for an easier manhattan function calculation
	public BoardPosition(int[] pos, BoardPosition par, Hashtable<Integer, Integer> endHash){
		position = Arrays.copyOf(pos, pos.length);
		parent = par;
		positionStr = integerArrayToString(position);
		
		boardSize = position.length;
		col = (int)Math.sqrt(boardSize);
		row = (int)Math.sqrt(boardSize);
		
		if(par == null){
			cost = 0;
		} else {
			cost = par.cost + 1;
		}
		goalHash = endHash;
		manhattan =  manhattan();
	}
	
	//returns the Manhattan value of the function
	public int manhattan(){
		int manhattanCount = 0;

		for(int k = 0; k < position.length; k++){
			if(position[k]!=0){
				int kCol = k%col;
				int kRow = k/col;
				int goalPos = goalHash.get(position[k]);
				int gCol = goalPos%col;
				int gRow = goalPos/col;
				manhattanCount+=Math.abs(kCol-gCol)+Math.abs(kRow-gRow);
			}
		}
		return manhattanCount;
	}
	
	//changes the integer array representing the board to a string, the string representation is used for hashing
	public String integerArrayToString(int[] arr){
		String str = "";
		for(int k = 0; k < arr.length; k++){
			str+=arr[k];
		}
		return str;
	}
	
	@Override
	public int hashCode(){
		return positionStr.hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof BoardPosition))
			return false;
		BoardPosition temp = (BoardPosition)obj;
		return positionStr.equals(temp.positionStr);
	}
	
	@Override
	public String toString(){
		String boardStr = "";
		for(int k = 0; k < row; k++){
			String rowStr = "";
			for(int j = 0; j < col; j++){
					if(position[k*col+j] == 0){
						rowStr+="blank"+"\t";
					} else {
						rowStr+=position[k*col+j]+"\t";
					}
			}
			boardStr+=rowStr+"\n";
		}
		return boardStr;
	}
	
	//get the position of the blank space in the grid
	private int getBlankPos(){
		for(int k = 0; k < position.length; k++){
			if(position[k] == 0){
				return k;
			}
		}
		return -1;
	}
	
	//set the parent of the boardPosition
	public void setParent(BoardPosition newPar){
		parent = newPar;
		cost = newPar.cost + 1;
	}
	
	//use to move the blank in the board position. This should be used on the board's children.
	private void moveBlank(int pos){
		int blankPos = getBlankPos();
		int temp = position[pos];
		position[pos] = position[blankPos];
		position[blankPos] = temp;
		positionStr = integerArrayToString(position);
		manhattan = manhattan();
	}
	
	
	//generate the children
	public ArrayList<BoardPosition> generateChildren(){
		ArrayList<BoardPosition> children = new ArrayList<BoardPosition>();
		int blankPos = getBlankPos();
		int numPossMoves = 0;
		int[] possMoves = new int[4];
		
		int pRow = blankPos/col;
		int pCol = blankPos%col;

		if(pRow != row-1){
			possMoves[numPossMoves] = blankPos + col;
			numPossMoves += 1;
		}	
		if(pRow !=0){
			possMoves[numPossMoves] = blankPos - col;
			numPossMoves +=1;
		}		
		if(pCol != col-1){
			possMoves[numPossMoves] = blankPos + 1;
			numPossMoves +=1;
		}		
		if(pCol != 0){
			possMoves[numPossMoves] = blankPos - 1;
			numPossMoves +=1;
		}		
		for(int k = 0 ; k < numPossMoves; k++){
			BoardPosition banana = new BoardPosition(position, this, goalHash);
			banana.moveBlank(possMoves[k]);
			children.add(banana);
		}
		return children;
	}

	@Override
	public int compareTo(BoardPosition bp) {
		//manhattan function fails when boardSize is too great, because the distance from the parent function begins to overshadow the manhattan function by a huge amount
		if(boardSize > 9){
			if(manhattan > bp.manhattan){
				return 1;
			} else if(manhattan == bp.manhattan){
				return 0;
			}	
			return -1;
		}
		if(cost+manhattan > bp.cost+bp.manhattan){
			return 1;
		} else if(cost+manhattan == bp.cost+bp.manhattan){
			return 0;
		}
		return -1;
	}
}
