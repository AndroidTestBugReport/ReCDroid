package commander;

public class Matched {

	private int resultId;
	private boolean steporSent;
	private Step step;//this is for nlp case
	private int stepId;
	private Sents sent;//this is just sent case
	private int sentId;
	private String viewClass;
	private int wordIndex; // word index in a sentence
	private boolean longornot=false;//long or short
	

	
	public boolean isLongornot() {
		return longornot;
	}
	public void setLongornot(boolean longornot) {
		this.longornot = longornot;
	}
	
	private String matchedword;
	public int getResultId() {
		return resultId;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public boolean isSteporSent() {
		return steporSent;
	}
	public void setSteporSent(boolean steporSent) {
		this.steporSent = steporSent;
	}
	public Step getStep() {
		return step;
	}
	public void setStep(Step step) {
		this.step = step;
	}
	public Sents getSent() {
		return sent;
	}
	public void setSent(Sents sent) {
		this.sent = sent;
	}
	public String getMatchedword() {
		return matchedword;
	}
	public void setMatchedword(String matchedword) {
		this.matchedword = matchedword;
	}
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public int getSentId() {
		return sentId;
	}
	public void setSentId(int sentId) {
		this.sentId = sentId;
	}
	public String getViewClass() {
		return viewClass;
	}
	public void setViewClass(String viewClass) {
		this.viewClass = viewClass;
	}
	public int getWordIndex() {
		return wordIndex;
	}
	public void setWordIndex(int wordIndex) {
		this.wordIndex = wordIndex;
	}
	
	
}
