package com.nqueen;

import java.util.List;

/**
 * POJO
 * @author apoorva
 *
 */
public class Board {

	private int heuristic;
	private String [][] currentState;
	private List<Board> successors;
		
	public int getHeuristic() {
		return heuristic;
	}
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	public String[][] getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String[][] currentState) {
		this.currentState = currentState;
	}
	public List<Board> getSuccessors() {
		return successors;
	}
	public void setSuccessors(List<Board> successors) {
		this.successors = successors;
	}
}
