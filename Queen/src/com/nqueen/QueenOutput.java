package com.nqueen;

/**
 * Pojo class for output
 * @author apoorva
 *
 */
public class QueenOutput {
	
	private static QueenOutput instance = null;	

	public static QueenOutput getInstance() {
		if(instance == null) {
			instance = new QueenOutput();
		}
		return instance;
	}

	int stepsClimbed = 0; //nodes generated including successors
	int noOfRandomRestart = 0;
	int maxN;
	
	
	public int getStepsClimbed() {
		return stepsClimbed;
	}
	public void setStepsClimbed(int stepsClimbed) {
		this.stepsClimbed = stepsClimbed;
	}
	public int getNoOfRandomRestart() {
		return noOfRandomRestart;
	}
	public void setNoOfRandomRestart(int noOfRandomRestart) {
		this.noOfRandomRestart = noOfRandomRestart;
	}
	public int getMaxN() {
		return maxN;
	}
	public void setMaxN(int maxN) {
		this.maxN = maxN;
	}
}
