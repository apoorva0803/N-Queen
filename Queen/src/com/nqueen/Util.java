package com.nqueen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class
 * @author apoorva
 *
 */
public class Util {



	/**
	 * Create board with randomly positioned queen
	 * @param inputElement
	 * @return
	 */
	public static String[][] createMatrixWithQueenInDiffColumn(int inputElement) {
		// TODO Auto-generated method stub

		String queenArray[][] = new String[inputElement][inputElement];

		for(int i=0;i<inputElement;i++){ //row
			Random rand = new Random();
			int x = rand.nextInt(inputElement);			
			queenArray[x][i] = "Q";			

		}	

		//Display randomly generated queen
		for(int i=0;i<inputElement;i++){ //row
			for(int j=0;j<inputElement;j++){
				if(queenArray[i][j]==null) queenArray[i][j]="X";
				System.out.print(queenArray[i][j] + " ");	
			}	
			System.out.println();
		}
		return queenArray;
	}

	/**
	 * Calculate h for attacking at upper right diagonal
	 * @param input
	 * @param i
	 * @param j
	 * @param hMap
	 */
	public static void checkUpperRightDiagonal(String[][] input,
			int i, int j, Map<String, List<String>> hMap) {
		// TODO Auto-generated method stub

		for(int k=i-1,a=j+1;k>0;k--,a++){
			if(a<input.length && input[k][a].equals("Q")){
				String key="Q"+a; //other queen
				String value = "Q"+j; //current queen
				List<String> strList = new ArrayList<String>();	
				if(hMap.containsKey(key))strList=hMap.get(key);			
				if(!strList.contains(value)) {
					strList.add(value);
					hValue++;
				}
				hMap.put(key, strList);
			}
		}

	}

	/**
	 * Calculate h for attacking at bottom right diagonal
	 * @param input
	 * @param i
	 * @param j
	 * @param hMap
	 */
	public static void checkBottomRightDiagonal(String[][] input,
			int i, int j, Map<String, List<String>> hMap) {
		// TODO Auto-generated method stub

		for(int k=i+1,a=j+1;k<input.length;k++,a++){
			if(a<input.length && input[k][a].equals("Q")){
				String key="Q"+a; //other queen
				String value = "Q"+j; //current queen
				List<String> strList = new ArrayList<String>();	
				if(hMap.containsKey(key))strList=hMap.get(key);			
				if(!strList.contains(value)){
					strList.add(value);
					hValue++;
				}
				hMap.put(key, strList);

			}
		}
	}

	/**
	 * Calculate h for attacking present horizontally
	 * @param input
	 * @param i
	 * @param j
	 * @param hMap
	 */
	public static void checkHorizontal(String[][] input,int i,int j,
			Map<String, List<String>> hMap) {
		// TODO Auto-generated method stub
		for(int k=j+1;k<input.length;k++){
			if(input[i][k].equals("Q")){
				String key="Q"+k; //other queen
				String value = "Q"+j; //current queen
				List<String> strList = new ArrayList<String>();	
				if(hMap.containsKey(key))strList=hMap.get(key);			
				if(!strList.contains(value)){
					strList.add(value);
					//ProcessQueen.hValue++;
					hValue++;
				}
				hMap.put(key, strList);
			}

		}
	}

	/**
	 * Generate all possible successors of node : n*n-1
	 * @param input
	 * @return
	 */
	public static ArrayList<Board> generateSuccesors(String input[][]) {
		// TODO Auto-generated method stub

		List<Board> successors = new ArrayList<Board>();
		int steps = 0;
		for(int i=0;i<input.length;i++){ //column
			int currentRow=findRowPos(input,i);

			for(int j=1;j<input.length;j++){ //row
				Board board = new Board();
				String [][] copyArray =createCopy(input);
				copyArray[currentRow][i]="X";
				steps = currentRow+j;
				if(steps >= input.length)
					steps =  steps-input.length;
				copyArray[steps][i]="Q";			
				board.setCurrentState(copyArray);

				hValue=0;
				hMap = new HashMap<String, List<String>>();
				board.setHeuristic(calculateHeuristic(copyArray));
				successors.add(board);
			}
		}	
		return (ArrayList<Board>) successors;
	}

	/**
	 * Create a copy of input : used while generating successors
	 * @param input
	 * @return
	 */
	static String[][] createCopy(String[][] input) {
		String [][] copyArray = new String[input.length][];
		for(int i = 0; i < input.length; i++)
			copyArray[i] = input[i].clone();

		return copyArray;
	}

	/**
	 * find row position of queen while generating successors
	 * @param inputElement
	 * @param column
	 * @return
	 */
	static int findRowPos(String[][] inputElement , int column) {
		// TODO Auto-generated method stub
		for(int i=0;i<inputElement.length;i++){ //row
			if(inputElement[i][column].equals("Q"))
				return i;
		}	
		System.out.println();
		return -1;		
	}


	/**
	 * Choose the node with least m value
	 * @param successors
	 * @param parentHValue
	 * @return
	 */
	public static Board getMinHValueNode(List<Board> successors , int parentHValue){

		Board b =null;
		List<Board> smallest = new ArrayList<Board>();

		for(Board bObj:successors){
			if(bObj.getHeuristic()<parentHValue || bObj.getHeuristic()==0){

				if(smallest.size()==0||bObj.getHeuristic()==smallest.get(0).getHeuristic()){
					smallest.add(bObj);
					System.out.print("min hVal " + bObj.getHeuristic());
				}
				else if(bObj.getHeuristic()< smallest.get(0).getHeuristic()){
					smallest.clear();
					smallest.add(bObj);
				}	
			}			
		}

		if(smallest.size()>0){
			if(smallest.size()==1)
				b=smallest.get(0);
			else{
				int smallestSize = smallest.size();
				Random rand = new Random();
				int x = rand.nextInt(smallestSize); // randomly choose among same generated node
				b=smallest.get(x);			
			}
		}
		return b;

	}

	static Map<String,List<String>> hMap= new HashMap<String,List<String>>();
	static int hValue = 0;

	/**
	 * heuristic is queen attacking directly or indirectly
	 * @param input
	 * @return
	 */
	public static int calculateHeuristic(String[][] input) {
		// TODO Auto-generated method stub
		int noOfColumns = input.length;

		for(int j=0;j<noOfColumns-1;j++){
			for(int i=0;i<input[0].length;i++){
				if(input[i][j].equals("Q")){
					Util.checkUpperRightDiagonal(input,i,j,hMap);
					Util.checkBottomRightDiagonal(input,i,j,hMap);
					Util.checkHorizontal(input,i,j,hMap);
				}
			}
			System.out.println();
		}
		return hValue;
	}


}


//for book n-queen
//		for(int i=0;i<inputElement;i++){ //row
//			for(int j=0;j<inputElement;j++){
//				queenArray[i][j]="1";
//			}
//		}	
//		queenArray[4][0]="Q";
//		queenArray[5][1]="Q";
//		queenArray[6][2]="Q";
//		queenArray[3][3]="Q";
//		queenArray[4][4]="Q";
//		queenArray[5][5]="Q";
//		queenArray[6][6]="Q";
//		queenArray[5][7]="Q";



