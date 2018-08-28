package cz.romario.opensudoku.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.robotium.solo.Solo;

import android.app.Activity;
//import android.renderscript.Element;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class Currentoutput {

	private Solo solo;
	int nogroupid=0;
	int viewgroupid=0;
	Element newclass;
	Element viewgroup;
	Element nogroup;
	int count=0;
	Document doc;
	
	public Currentoutput(Solo soloinput) throws JDOMException, IOException{
		solo=soloinput;
		//////////////////////////////////////////
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("/sdcard/test.xml");
		doc = (Document) builder.build(xmlFile);
		Element rootNode =doc.getRootElement();
		
		Activity activity=solo.getCurrentActivity();
	    String classname=activity.getClass().getSimpleName();//get current class name
    	
	    newclass=new Element(classname);
	    rootNode.addContent(newclass);
	    //newclass=rootNode.addContent(classname);

    	viewgroup=new Element("Viewgroup");
    	nogroup=new Element("Nogroup");
    	newclass.addContent(viewgroup);
    	newclass.addContent(nogroup);
    	
    	
    	//viewgroup=newclass.addContent("Viewgroup");
    	//nogroup=newclass.addContent("Nogroup");
		
		
	}
	
	public int Groupviewdeeper(View v,Element listsubclass, boolean clickable, int subid){//v is from the first groupview to go to the deeper
		for(int i=0; i<((ViewGroup)v).getChildCount(); i++){	
			
			System.out.println(++count);
	        
			if(count==16){
				System.out.println(count);
			}
			
			View v1=((ViewGroup)v).getChildAt(i);
			if(v1 instanceof ViewGroup){
				if(clickable==true){
					if(v1.isClickable()){
						Element error=new Element("error");
						error.setText("Both parent and child list are clickable."+"parent:"+v.getClass().getName()+"children:"+v1.getClass().getName());
						listsubclass.addContent(error);
						//listsubclass.addContent("Something wrong").setText("Both parent and child list are clickable");
						subid=Groupviewdeeper(v1,listsubclass,true, subid);
					}else{
						
						subid=Groupviewdeeper(v1,listsubclass,true, subid);
					}
				}else{
					if(v1.isClickable()){
						Element newviewgroup=new Element("ID"+String.valueOf(++viewgroupid));//a new viewgroup
	    				Element viewclass=new Element("viewclass");
	    				viewclass.setText(v1.getClass().getName());
	    				newviewgroup.addContent(viewclass);
						
						//newviewgroup.addContent("Viewclass").setText(v.getClass().getName());//add the view style
			            viewgroup.addContent(newviewgroup);
	    				Element subclass=new Element("subclass");
	    				viewgroup.addContent("subclass");
	    		
	    				subid=Groupviewdeeper(v1,subclass,true, subid);
	    				
					}else{
						subid=Groupviewdeeper(v1,null,false, subid);
					}
				}
			}else{// view is not a viewgroup				
				if(clickable==true){ 
					if(v1.isClickable()){//it may a button in the listview case. The button override the listview
					    Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
					    Element alert=new Element("alert");
					    alert.setText("override case");
					    Element viewclass=new Element("viewclass");
					    viewclass.setText(v1.getClass().getName());
					   
					    newnogroup.addContent(alert);
    				    newnogroup.addContent(viewclass);//add the view style
    				    if(v1 instanceof TextView){
    				    	Element viewtext=new Element("viewtext");
    						viewtext.setText(((TextView)v1).getText().toString());
    						newnogroup.addContent(viewtext);
    				    	
    				      // newnogroup.addContent("viewtext").setText(((TextView)v).getText().toString());//if the ungroupview is the textview, set the textview
    				    }else{
    				    	Element viewtext=new Element("viewtext");
     				    	viewtext.setText("not a TextView");
     				    	newnogroup.addContent(viewtext);
    				    	
    				    	//newnogroup.addContent("viewtext").setText("");// if not, set ""
    				    }
    				    nogroup.addContent(newnogroup);//add the the nogroup view to the element nogroup.
    				}else{
    					Element newsubview= new Element("ID"+String.valueOf(++subid));
    					Element type=new Element("viewclass");
    					
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
				}else{// if the listview's cliclable is false, the textview should be added on the nogroup class.
					if(v1.isClickable()){
						Element newnogroup= new Element("IDhoumian"+String.valueOf(++nogroupid));
					    //Element alert=new Element("alert");
					    //alert.setText("override case count:"+String.valueOf(count));
					    Element viewclass=new Element("viewclass");
					    viewclass.setText(v1.getClass().getName()+String.valueOf(count));
					    
					    //newnogroup.addContent(alert);
    				    newnogroup.addContent(viewclass);//add the view style
							
					  //  newnogroup.addContent("alert").setText("override case");
    				  //  newnogroup.addContent("Viewclass").setText(v.getClass().getName());//add the view style
    				    if(v1 instanceof TextView){
    				    	Element viewtext=new Element("viewtext");
    						viewtext.setText(((TextView)v1).getText().toString());
    						newnogroup.addContent(viewtext);  	
    				      // newnogroup.addContent("viewtext").setText(((TextView)v).getText().toString());//if the ungroupview is the textview, set the textview
    				    }else{
    				    	Element viewtext=new Element("viewtext");
     				    	viewtext.setText("not a TextView");
     				    	newnogroup.addContent(viewtext);
    				    	
    				    	//newnogroup.addContent("viewtext").setText("");// if not, set ""
    				    }
    				    nogroup.addContent(newnogroup);//add the the nogroup view to the element nogroup.
					}
				}
			}
		
		}
		
		return subid;	
	}
	
    public void Outputcurrent() throws JDOMException, IOException{
    	System.out.println("outputcurrent");
    	
    	/////////////output the current all clickable competent////////
    	ArrayList<View> currentviews = solo.getCurrentViews();
    	for (View v:currentviews){//because the currentviews are a list, so it need to be processed at the first layer
    	
    		if(v instanceof ViewGroup){
    			if(v.isClickable()){
    				//newviewgroup
    				Element newviewgroup=new Element("ID"+String.valueOf(++viewgroupid));//a new viewgroup
    				Element viewclass=new Element("viewclass");//add the view style
    				viewclass.setText(v.getClass().getName());
    				newviewgroup.addContent(viewclass);
    				viewgroup.addContent(newviewgroup);
    				Element subclass=new Element("subclass");
    				newviewgroup.addContent(subclass);
    				//Groupviewdeeper(v,newviewgroup,true);
    				Groupviewdeeper(v,subclass,true,0);//transfer the subclass. The subclass is designed to store the textview list.

    				//newviewgroup;
    				
    			}else{// the viewgroup is unclickable, as the linearlayout
    				Groupviewdeeper(v,null,false,0);
    			}
    		}else{
    			if(v.isClickable()){
    				Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
    				Element viewclass=new Element("viewclass");
    				viewclass.setText(v.getClass().getName());
    				newnogroup.addContent(viewclass);
    				//newnogroup.addContent("Viewclass").setText(v.getClass().getName());//add the view style
    				if(v instanceof TextView){
    					Element viewtext=new Element("viewtext");
    					newnogroup.addContent(viewtext);
    					viewtext.setText(((TextView)v).getText().toString());
    					
    					
    				//newnogroup.addContent("viewtext").setText(((TextView)v).getText().toString());//if the ungroupview is the textview, set the textview
    				}else{
    					Element viewtext=new Element("viewtext");
    					newnogroup.addContent(viewtext);
    					viewtext.setText("");
    				//newnogroup.addContent("viewtext").setText("");// if not, set ""
    				}
    				nogroup.addContent(newnogroup);//add the the nogroup view to the element nogroup.
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

