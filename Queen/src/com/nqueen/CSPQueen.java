package com.nqueen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CSPQueen {

	public static void main(String args[]) {

		generateRandomQueen(4);
	}

	
	public static void generateRandomQueen(int inputElement) {
		// TODO Auto-generated method stub
		String input[][]=Util.createMatrixWithQueenInDiffColumn(inputElement);

		Board b = new Board();
		b.setCurrentState(input);
		int length= input.length;


		Random rand = new Random();
		int col = rand.nextInt(length);
		int row=-1;

		for(int k=0;k<inputElement;k++){ //row
			for(int j=0;j<inputElement;j++){

				if(j==col && input[k][j].equals("Q")){
					row=k;
				}				
			}	

		}
		int hCost = Util.calculateHeuristic(input);
		hValue=calculateHeuristicMinConflict(input, row,col);
		b.setHeuristic(hValue);
		List<Board> successors=generateSuccesors(input,col);
		b.setSuccessors(successors);

		List<Integer> colVal = new ArrayList<Integer>();
		colVal.add(col);

		while(hCost!=0){

			Board bObj=getMinHValueNode(successors,hValue);
			hCost=0;
			if(bObj!=null)
				hCost = Util.calculateHeuristic(bObj.getCurrentState());

			display(inputElement,bObj);

			col = rand.nextInt(length);
			for(int k=0;k<inputElement;k++){ //row
				for(int j=0;j<inputElement;j++){
					if(j==col && input[k][j].equals("Q"))
						row=k;			
				}	
			}

			if(bObj!=null){
				hValue=bObj.getHeuristic();
				successors=generateSuccesors(bObj.getCurrentState(),col);
				bObj.setSuccessors(successors);
			}
			
			
		}
	}


	public static void display(int inputElement, Board bObj){
		for(int i=0;i<inputElement;i++){ //row
			for(int j=0;j<inputElement;j++){
				System.out.print(bObj.getCurrentState()[i][j] + " ");	
			}	
			System.out.println();
		}

	}
	public static ArrayList<Board> generateSuccesors(String input[][],int col) {
		// TODO Auto-generated method stub

		List<Board> successors = new ArrayList<Board>();
		int steps = 0;
		//column
		System.out.println("column chosen "+ col);
		int currentRow=Util.findRowPos(input,col);

		for(int j=1;j<input.length;j++){ //row
			Board board = new Board();
			String [][] copyArray =Util.createCopy(input);
			copyArray[currentRow][col]="X";
			steps = currentRow+j;
			if(steps >= input.length)
				steps =  steps-input.length;
			copyArray[steps][col]="Q";			
			board.setCurrentState(copyArray);

			hValue=0;
			board.setHeuristic(calculateHeuristicMinConflict(copyArray,steps,col));
			successors.add(board);
		}

		return (ArrayList<Board>) successors;
	}

	static int hValue=0;
	public static int calculateHeuristicMinConflict(String[][] input,int row, int col) {

		for(int i=0;i<input.length;i++){
			for(int j=0;j<input.length;j++){

				if(j==col)continue;
				if(input[i][j].equals("Q") ){
					if(i==row || Math.abs(i-row)==Math.abs(j-col))
						hValue++;

				}
			}
		}
		return hValue;

	}


	public static Board getMinHValueNode(List<Board> successors , int parentHValue){

		Board b =null;
		List<Board> smallest = new ArrayList<Board>();

		for(Board bObj:successors){
			if(bObj.getHeuristic()<=parentHValue || bObj.getHeuristic()==0){

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
}

