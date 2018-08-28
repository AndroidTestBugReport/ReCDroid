package commander;

import java.util.ArrayList;

public class Step {
	private String type;
	private String step;
	private ArrayList<ArrayList<String>> clickwhere;
	private String clicktype;
	private boolean clicktimes;
	private ArrayList<ArrayList<String>> typewhat;
	private ArrayList<ArrayList<String>> digittypewhat;
	private ArrayList<ArrayList<String>> typewhere;
	private ArrayList<ArrayList<String>> digittypewhere;
	private boolean typetimes;
	private ArrayList<ArrayList<String>> createwhat;
	private ArrayList<String> sentence;
	private int sentenceid;
	
	
	
	public Step(){
		clickwhere=new ArrayList<ArrayList<String>>();
		typewhat=new ArrayList<ArrayList<String>>();
		digittypewhat=new ArrayList<ArrayList<String>>();
		typewhere=new ArrayList<ArrayList<String>>();
		digittypewhere=new ArrayList<ArrayList<String>>();
		createwhat=new ArrayList<ArrayList<String>>();
		sentence=new ArrayList<String>();
	}



	public ArrayList<ArrayList<String>> getClickwhere() {
		return clickwhere;
	}



	public void setClickwhere(ArrayList<ArrayList<String>> clickwhere) {
		this.clickwhere = clickwhere;
	}



	public ArrayList<ArrayList<String>> getTypewhat() {
		return typewhat;
	}



	public void setTypewhat(ArrayList<ArrayList<String>> typewhat) {
		this.typewhat = typewhat;
	}



	public ArrayList<ArrayList<String>> getDigittypewhat() {
		return digittypewhat;
	}



	public void setDigittypewhat(ArrayList<ArrayList<String>> digittypewhat) {
		this.digittypewhat = digittypewhat;
	}



	public ArrayList<ArrayList<String>> getTypewhere() {
		return typewhere;
	}



	public void setTypewhere(ArrayList<ArrayList<String>> typewhere) {
		this.typewhere = typewhere;
	}



	public ArrayList<ArrayList<String>> getDigittypewhere() {
		return digittypewhere;
	}



	public void setDigittypewhere(ArrayList<ArrayList<String>> digittypewhere) {
		this.digittypewhere = digittypewhere;
	}



	public ArrayList<ArrayList<String>> getCreatewhat() {
		return createwhat;
	}



	public void setCreatewhat(ArrayList<ArrayList<String>> createwhat) {
		this.createwhat = createwhat;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getStep() {
		return step;
	}



	public void setStep(String step) {
		this.step = step;
	}




	public String getClicktype() {
		return clicktype;
	}



	public void setClicktype(String clicktype) {
		this.clicktype = clicktype;
	}



	public boolean isClicktimes() {
		return clicktimes;
	}


	public void setClicktimes(boolean clicktimes) {
		this.clicktimes = clicktimes;
	}



	public boolean isTypetimes() {
		return typetimes;
	}



	public void setTypetimes(boolean typetimes) {
		this.typetimes = typetimes;
	}




	public ArrayList<String> getSentence() {
		return sentence;
	}



	public void setSentence(ArrayList<String> sentence) {
		this.sentence = sentence;
	}



	public int getSentenceid() {
		return sentenceid;
	}



	public void setSentenceid(int sentenceid) {
		this.sentenceid = sentenceid;
	}
	
	
}
