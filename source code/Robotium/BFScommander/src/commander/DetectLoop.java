package commander;

import java.util.ArrayList;

public class DetectLoop {

	public DetectLoop(ArrayList<String> possibleLoop, int currentPoint, ArrayList<ArrayList<String>> allArivalSimilarList) {
		this.possibleLoop=possibleLoop;
		this.currentPoint=currentPoint;
		this.allArivalSimilarList=allArivalSimilarList;
		// TODO Auto-generated constructor stub
	}
	private ArrayList<String> possibleLoop=new ArrayList<String>();
	private ArrayList<ArrayList<String>> allArivalSimilarList=new ArrayList<ArrayList<String>>();
	private int currentPoint;
	private int looptimes=1;
	
	public ArrayList<String> getPossibleLoop() {
		return possibleLoop;
	}
	public void setPossibleLoop(ArrayList<String> possibleLoop) {
		this.possibleLoop = possibleLoop;
	}
	public int getCurrentPoint() {
		return currentPoint;
	}
	public void setCurrentPoint(int currentPoint) {
		this.currentPoint = currentPoint;
	}
	public int getLooptimes() {
		return looptimes;
	}
	public void setLooptimes(int looptimes) {
		this.looptimes = looptimes;
	}
	public ArrayList<ArrayList<String>> getAllArivalSimilarList() {
		return allArivalSimilarList;
	}
	public void setAllArivalSimilarList(
			ArrayList<ArrayList<String>> allArivalSimilarList) {
		this.allArivalSimilarList = allArivalSimilarList;
	}
    

}
