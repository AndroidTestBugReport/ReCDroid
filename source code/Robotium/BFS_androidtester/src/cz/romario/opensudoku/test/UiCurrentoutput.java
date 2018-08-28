package cz.romario.opensudoku.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.robotium.solo.Solo;

import android.R.integer;
import android.app.Activity;
//import android.renderscript.Element;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class UiCurrentoutput {

	private Solo solo;
	int nogroupid=0;
	int viewgroupid=0;
	Element newclass;
	Element viewgroup;
	Element nogroup;
	int count=0;
	Document doc;
	HashSet override;
	String activityclassname;
	
	public UiCurrentoutput(Solo soloinput,int tag) throws JDOMException, IOException{
		solo=soloinput;
		//////////////////////////////////////////
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("/sdcard/test.xml");
		doc = (Document) builder.build(xmlFile);
		Element rootNode =doc.getRootElement();
		
		Activity activity=solo.getCurrentActivity();
		activityclassname=activity.getClass().getSimpleName();//get current class name    	
	    
	    newclass=new Element(activityclassname);
	    /////set attribute
	    
	    if(tag==test.Main){
	        Attribute attribute=new Attribute("type","main");
	        newclass.setAttribute(attribute);
	        rootNode.addContent(newclass);
	    }else if(tag==test.Menu){
	    	Attribute attribute=new Attribute("type","menu");
		    newclass.setAttribute(attribute);
		    rootNode.addContent(newclass);
	    }
	    
	    //newclass=rootNode.addContent(classname);

    	viewgroup=new Element("Viewgroup");
    	nogroup=new Element("Nogroup");
    	newclass.addContent(viewgroup);
    	newclass.addContent(nogroup);
    	override=new HashSet<integer>();
    	
    	
    	//viewgroup=newclass.addContent("Viewgroup");
    	//nogroup=newclass.addContent("Nogroup");
		
		
	}
	
	public void Groupviewdeeper(View v,Element listsubclass, int subid){//v is from the first groupview to go to the deeper		
		for(int i=0; i<((ViewGroup)v).getChildCount(); i++){
			//++subid;
			if(v.isClickable()){
			  ++subid;	
			}
			System.out.println(++count); 
			View v1=((ViewGroup)v).getChildAt(i);
			if(v1 instanceof ViewGroup){// v1 is a viewgroup
				if(v1.isClickable()){
					Element error=new Element("error");
					error.setText("Both parent and child list are clickable."+"parent:"+v.getClass().getName()+"children:"+v1.getClass().getName());
					listsubclass.addContent(error);
					//listsubclass.addContent("Something wrong").setText("Both parent and child list are clickable");
					Groupviewdeeper(v1,listsubclass, subid);
				}else{
					Groupviewdeeper(v1,listsubclass, subid);
				}
			}else{//v1 is not a viewgroup
				if(v1.isClickable()){//it may a button in the listview case. The button override the listview
				    Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
				    Element alert=new Element("alert");
				    alert.setText("override case a clickable viewgroup and a clickable viewtext");
				    newnogroup.addContent(alert);
				    if(override.contains(v.getId())){
    					Element alert2=new Element("alert");
    					alert2.setText("override happens, the viewtext is added two times");
    					newnogroup.addContent(alert2);
    				}
				    
				    ////add viewclass
				    Element viewclass=new Element("viewclass");
				    viewclass.setText(v1.getClass().getName());
                    newnogroup.addContent(viewclass);//add the view style
				    if(v1 instanceof TextView){
				    	Element viewtext=new Element("viewtext");
						viewtext.setText(((TextView)v1).getText().toString());
						newnogroup.addContent(viewtext);//if the ungroupview is the textview, set the textview
				    }else{
				    	Element viewtext=new Element("viewtext");
 				    	viewtext.setText("not a TextView");
 				    	newnogroup.addContent(viewtext);
				    	
				    	//newnogroup.addContent("viewtext").setText("");// if not, set ""
				    }
				    nogroup.addContent(newnogroup);//add the the nogroup view to the element nogroup.
				
			    }else{//the viewtext is not clickable
					//Element newsubview= new Element("ID"+String.valueOf(++subid));
			    	Element newsubview= new Element("ID"+String.valueOf(subid));
			    	Element type=new Element("viewclass");
					
				//	if(override.contains(v.getId())){ it will return -1
    			//		Element alert2=new Element("alert");
    			//		alert2.setText("override happens, the viewtext is added two times");
    			//		newsubview.addContent(alert2);
    			//	}
					//add android id
				//	Element androidid=new Element("androidID");
				//	androidid.setText(String.valueOf(v.getId()));
				//	newsubview.addContent(androidid);
					
					type.setText(v1.getClass().getName());
					newsubview.addContent(type);
					
					//newsubview.addContent("Viewclass").setText(v.getClass().getName());//add the view style
					if(v1 instanceof TextView){
						Element viewtext=new Element("viewtext");
						viewtext.setText(((TextView)v1).getText().toString());
						newsubview.addContent(viewtext);
						//if the ungroupview is the textview, set the textview
				    }else{
				    	//newsubview.addContent("viewtext").setText("");// if not, set ""
				    	Element viewtext=new Element("viewtext");
				    	viewtext.setText("not a TextView");
				    	newsubview.addContent(viewtext);
				    }
				    
					listsubclass.addContent(newsubview);
				 }
			
		  }
		}
		
		
		
		//return subid;	
	}
	
    public void Outputcurrent() throws JDOMException, IOException{
    	System.out.println("outputcurrent");
    	
    	
    //	if(activityclassname.equals("SudokuListActivity")){
    //		System.out.println("bingotest");
    //	};
    	/////////////output the current all clickable competent////////
    	ArrayList<View> currentviews = solo.getCurrentViews();
    //	((ViewGroup)currentviews.get(6)).getChildCount();;
    //	((ViewGroup)((ViewGroup)currentviews.get(6)).getChildAt(1)).getChildCount();
    	//(ViewGroup(currentviews.get(6)));
    	
    	
    	for (View v:currentviews){//because the currentviews are a list, so it need to be processed at the first layer
    	
    		if(v.isClickable()){
    			if(v instanceof ViewGroup){//v is a viewgroup
    				Element newviewgroup=new Element("ID"+String.valueOf(++viewgroupid));//a new viewgroup
    				Element viewclass=new Element("viewclass");//add the view style
    				viewclass.setText(v.getClass().getName());
    				newviewgroup.addContent(viewclass);
    				viewgroup.addContent(newviewgroup);
    				Element subclass=new Element("subclass");
    				newviewgroup.addContent(subclass);
    				Groupviewdeeper(v,subclass,0);
    				
    			}else{//v is not a viewgroup
    				Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
    				Element viewclass=new Element("viewclass");
    				viewclass.setText(v.getClass().getName());
    				newnogroup.addContent(viewclass);
    				Element viewtext=new Element("viewtext");
					newnogroup.addContent(viewtext);
					viewtext.setText(((TextView)v).getText().toString());
    			//	if(override.contains(v.getId())){
    			//		Element alert=new Element("alert");
    			//		alert.setText("override happens");
    			//		newnogroup.addContent(alert);
    			//	}
    				nogroup.addContent(newnogroup);
    			}	
    		}
    	}
    	XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(doc, new FileWriter("/sdcard/test.xml"));
    	
    	
    	
    
  }  	
}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//////////////output the current view's competent////////////
		/*
    	ArrayList<View> views1 = solo.getCurrentViews();
	    for (View v : views1) {
	    	if (v instanceof ListView){//v,v1,v2,b3  layer by layer
	        	System.out.println("ListView");	            
	            System.out.println("clickable or not"+"    "+v.isClickable());//the first layer, this is a clickable view.
	            System.out.println("getId"+"    "+v.getId());
	            
	            for (int i = 0; i < ((ListView)v).getCount(); i++) {
	                  System.out.println("viewtype at v1"+"  "+((ListView)v).getChildAt(i).getClass().getName());
	                  View v1 = ((ListView)v).getChildAt(i);//the second layer as v2.getChildAt(j)
	                  
	                  System.out.println("v1 clickable or not"+"    "+v1.isClickable());
	                  if(v1 instanceof ViewGroup){
	                            for(int j=0; j<((ViewGroup)v1).getChildCount();j++ ){
	                	           ViewGroup v2=(ViewGroup)v1;
	                	          // v2.getChildAt(j);
	                	           System.out.println("viewtype at v2"+"   "+v2.getChildAt(j).getClass().getName());//the third layer as textview
	                	           System.out.println("v3 clickable or not"+"    "+v2.getChildAt(j).isClickable());//
	                	           System.out.println("v3's test"+"     "+((TextView)v2.getChildAt(j)).getText().toString());
	                	           //System.out.println("v2's string"+"    "+((TextView)v2);
	                	           
	                	           
	                	           //TexView v3
	                	           System.out.println("Is the v3 is instance of groupview" +"     "+(v2.getChildAt(j) instanceof ViewGroup));
	                            
	                            }
	                  }
	            
	            }
	        
	    	}
	    }
	    
	    
	    
	    	public void Groupviewdeeper(View v,int count){//v is from the first groupview to go to the deeper
		for(int i=0; i<((ViewGroup)v).getChildCount(); i++){					
			View v1=((ViewGroup)v).getChildAt(i);//the i-th cild view
			if(v1 instanceof ViewGroup){
				System.out.println("layer   "+count);
				System.out.println("view style    "+v1.getClass().getName());
				Groupviewdeeper(v1,count+1);
				
			}else{
				if(v1 instanceof TextView){
					System.out.println("layer   "+count);
					System.out.println("view style    "+ v1.getClass().getName());//style
					System.out.println("TEXT    "+((TextView)v1).getText().toString());//text
				}
			}
			
			
		}  	
	}
	
    public void Outputcurrent(){
    	System.out.println("outputcurrent");
    	/////////////output the current all clickable competent////////
    	ArrayList<View> currentviews = solo.getCurrentViews();
    	for (View v:currentviews){
    		if(v.isClickable()){//first to judge the v is clickable or not.
    			if(v instanceof ViewGroup){
    				System.out.println("layer 1");
    				System.out.println("view style"+v.getClass().getName());//pick up the first ViewGroup				
    				Groupviewdeeper(v,2);//let the next layer to be layer 2.
    				
    							
    				
    			}else{//not ViewGroup, may be a textview or buttonview. textview is the parent of the buttonview
    				if(v instanceof TextView){
    					System.out.println("layer 1");
    					System.out.println("view style"+ v.getClass().getName());//style
    					System.out.println("text"+((TextView)v).getText().toString());//text
    				}
    			}
    			
    			
    			
    			
    		}
    		
    		
    	}
    }
	    */

