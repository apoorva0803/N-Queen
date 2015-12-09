package com.nqueen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CSP {

	public static void main(String args[]) {
		
		generateRandomQueen(N);
	}
	static int N=16;
	static int completeBoardHeuristic = 0;
	static int hCost=0;
	public static void generateRandomQueen(int inputElement) {
		// TODO Auto-generated method stub
		String input[][]=Util.createMatrixWithQueenInDiffColumn(inputElement);

		Board b = new Board();
		b.setCurrentState(input);
		int length= input.length;

		Random rand = new Random();
		int col = rand.nextInt(length); //Choose random column
		int row=-1;

		for(int k=0;k<inputElement;k++){ //row
			for(int j=0;j<inputElement;j++){

				if(j==col && input[k][j].equals("Q")){
					row=k;
				}				
			}	

		}
		completeBoardHeuristic = Util.calculateHeuristic(input); //calculate heuristic of complete board

		//Calculate heuristic of the queen in that column
		int hValue=calculateHeuristicMinConflict(input, row,col);
		b.setHeuristic(hValue);
		//Generate successors
		List<Board> successors=generateSuccesors(input,col);
		b.setSuccessors(successors);

		while(completeBoardHeuristic!=0){

			Board bObj=getMinHValueNode(successors,hValue);

			if(bObj!=null){
				Util.hMap = new HashMap<String, List<String>>();
				Util.hValue =0;
				completeBoardHeuristic = Util.calculateHeuristic(bObj.getCurrentState());
				hValue = bObj.getHeuristic();
				display(inputElement,bObj);
			}
			if(completeBoardHeuristic==0)
				break;

			if(bObj==null) generateRandomQueen(N);
			//choose next random column with heuristic !=0

			int column =chooseNextRandomColumn(rand,length,input);
			counter=0;
			if(bObj!=null){
				
				successors=generateSuccesors(bObj.getCurrentState(),column);
				bObj.setSuccessors(successors);
			}
		}

	}



	static int counter = 0; 
	private static int chooseNextRandomColumn(Random rand, int length , String input[][]) {
		// TODO Auto-generated method stub

		int col = rand.nextInt(length);
		int row= 0;
		int val =-1;


		for(int k=0;k<length;k++){ //row
			for(int j=0;j<length;j++){
				if(j==col && input[k][j].equals("Q")){
					row=k;	
					break;
				}
			}	
		}

		//calculateHeuristicOfQueen(input, row, col);
		val = calculateHeuristicMinConflict(input, row, col);
		counter++;
		if(counter>length+100){generateRandomQueen(N); //Random Restart
		System.out.println("Random Restarted");}
		if(val==0 && counter<(length+100)) chooseNextRandomColumn(rand, length, input);


		return col;
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

			//hValue=0;
			board.setHeuristic(calculateHeuristicMinConflict(copyArray,steps,col));
			successors.add(board);
		}

		return (ArrayList<Board>) successors;
	}


	public static int calculateHeuristicMinConflict(String[][] input,int row, int col) {

		int hValue=0;
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


	public static void display(int inputElement, Board bObj){
		for(int i=0;i<inputElement;i++){ //row
			for(int j=0;j<inputElement;j++){
				System.out.print(bObj.getCurrentState()[i][j] + " ");	
			}	
			System.out.println();
		}

	}


}
