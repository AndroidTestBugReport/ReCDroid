package cz.romario.opensudoku.test;

import java.util.ArrayList;

import android.view.View;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

public class Mycondition implements Condition{

	int wait=0;
	ArrayList<View> newlist;
	Solo solo;
	
	Mycondition(int inputwait){
		wait=inputwait;
	}
	
	Mycondition(ArrayList<View> newlist, Solo solo){
		//wait=inputwait;
		this.newlist=newlist;
		this.solo=solo;
	}
	
	@Override
    public boolean isSatisfied() {
		
		System.out.println("wait");
		return (wait==1 || !newlist.containsAll(solo.getCurrentViews()));
	}


}
