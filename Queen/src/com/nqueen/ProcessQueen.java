package com.nqueen;

import java.awt.Dimension;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Generates the queen randomly on board using Hill Climbing WIth Randomly Restart
 *  Step 1: Randomly generate queen in different column
 *	Step 2: Calculate the heuristic
 *	Step 3: Generate the successors
 *	Step 4: Choose the successor with minimum h cost, in case of multiple successors with h cost, choose randomly
 *	Step 5: Repeat the step 2, 3, 4 until h value ==0 and if hvalue doesn’t decrease after certain move, do random restart
 *
 * @author apoorva
 *
 */
public class ProcessQueen {


	QueenOutput op= QueenOutput.getInstance();
	static int hCost=0;

	public void generateRandomQueen(int inputElement) {
		// TODO Auto-generated method stub
		String input[][]=Util.createMatrixWithQueenInDiffColumn(inputElement);
		hCost= 10000; // randomly big number

		Board board = new Board();	
		board.setCurrentState(input);
		Util.hValue=0;
		hCost=Util.calculateHeuristic(input);
		board.setHeuristic(hCost);
		ArrayList<Board> successors =Util.generateSuccesors(input);
		board.setSuccessors(successors);

		if(hCost==0){
			return;
		}

		while(hCost != 0){

			Board bObj=Util.getMinHValueNode(successors,hCost);
			
			op.setStepsClimbed(op.getStepsClimbed()+successors.size());
			
			if(bObj!=null){ //Hill Climbing
				hCost=bObj.getHeuristic();
				if(hCost != 0){
					successors =Util.generateSuccesors(bObj.getCurrentState());
					bObj.setSuccessors(successors);
				}else {

					// create UI for output
					JFrame outputFrame = new JFrame();
					JPanel panel = new JPanel();
					outputFrame.setSize(new Dimension(300, 350));
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					outputFrame.setVisible(true);
					panel.setEnabled(true);

					Label lblNoOfNodes = new Label("No of nodes " + QueenOutput.getInstance().getStepsClimbed());
					Label lblNoOfRandomRestart = new Label("No of Random Restart " + QueenOutput.getInstance().getNoOfRandomRestart());

					panel.add(lblNoOfNodes);
					panel.add(lblNoOfRandomRestart);

					JPanel subPanel = new JPanel();
					subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

					for(int a=0;a<bObj.getCurrentState().length;a++){ //row
						StringBuilder strOutput = new StringBuilder();
						JLabel lb = new JLabel();

						for(int b=0;b<bObj.getCurrentState().length;b++){
							strOutput.append(bObj.getCurrentState()[a][b] + " ");	

						}	
						strOutput.append("\n");
						lb.setText(strOutput.toString());
						subPanel.add(lb);
						panel.add(subPanel);
					}

					outputFrame.add(panel);
					hCost=0;
					break;

				}
			}else{
				// increase the random restart
				op.setNoOfRandomRestart(op.getNoOfRandomRestart() +1);
				generateRandomQueen(inputElement); // Random Restart

			}
		}
	}
}
