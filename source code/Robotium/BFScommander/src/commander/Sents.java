package commander;

import java.util.ArrayList;
import java.util.HashMap;

public class Sents {
	private int sentid;
	private ArrayList<String> sentswords;
	private ArrayList<String> nounlist;
	private HashMap<String,String> verbs;
	
	public HashMap<String, String> getVerbs() {
		return verbs;
	}

	public void setVerbs(HashMap<String, String> verbs) {
		this.verbs = verbs;
	}

	public Sents(){
		sentswords=new ArrayList<String>();
		nounlist=new ArrayList<String>();
		verbs=new HashMap<String,String>();
	}

	public int getSentid() {
		return sentid;
	}

	public void setSentid(int sentid) {
		this.sentid = sentid;
	}

	public ArrayList<String> getSentswords() {
		return sentswords;
	}

	public void setSentswords(ArrayList<String> sentswords) {
		this.sentswords = sentswords;
	}

	public ArrayList<String> getNounlist() {
		return nounlist;
	}

	public void setNounlist(ArrayList<String> nounlist) {
		this.nounlist = nounlist;
	}
	
	
	
	
}
