package commander;

import java.util.ArrayList;



public class RemoveStepdata {


int generationpass=0;
ArrayList<Integer> children=new ArrayList<Integer>();
ArrayList<Integer> gradChildren=new ArrayList<Integer>();

ArrayList<ArrayList<Integer>> descendant= new ArrayList<ArrayList<Integer>>();// first arraylist is for the child, gradchild and so on
//second arralist is for the classid collected.

RemoveStepdata(int level){
	for (int i=0; i<=level; i++){
		descendant.add(new ArrayList<Integer>());
	}
}

public int getGenerationpass() {
	return generationpass;
}
public void setGenerationpass(int generationpass) {
	this.generationpass = generationpass;
}
public ArrayList<Integer> getChildren() {
	return children;
}
public void setChildren(ArrayList<Integer> children) {
	this.children = children;
}
public ArrayList<Integer> getGradChildren() {
	return gradChildren;
}
public void setGradChildren(ArrayList<Integer> gradChildren) {
	this.gradChildren = gradChildren;
}
public ArrayList<ArrayList<Integer>> getDescendant() {
	return descendant;
}
public void setDescendant(ArrayList<ArrayList<Integer>> descendant) {
	this.descendant = descendant;
}




	
}
