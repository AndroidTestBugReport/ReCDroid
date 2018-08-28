package cz.romario.opensudoku.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.robotium.solo.Solo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputType;
//import android.renderscript.Element;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class Outputresult {

	private Solo solo;
	private Element rootNode;
	
	
	
	
	int nogroupid=0;
	int viewgroupid=0;
	Element step;
	
	
	//Element newclass;
	Element viewgroup;
	Element nogroup;
	Element easyoperate;
	Element alltext;
	int count=0;
	int number=0;
	Document doc;
	//HashSet override;
	String activityclassname;
	int currentnumber=0;
	boolean besttext=false;
	Element runableID;
	int imagebuttoncount=0;
	int stepid;
	
	int alltextcount=0;
	
	public Outputresult(Solo soloinput){
		solo=soloinput;
	}	

	
	
	
	
	/*
	public Outputresult(Solo soloinput,int stepid) throws JDOMException, IOException{
		solo=soloinput;
		//////////////////////////////////////////

		this.stepid=stepid;//this is for "ok","close"case
		
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("/sdcard/result.xml");
		doc = (Document) builder.build(xmlFile);
		Element rootNode =doc.getRootElement();
		
		Activity activity=solo.getCurrentActivity();	
	    
		if(stepid!=0){
			this.step=new Element("Step"+String.valueOf(stepid));
			rootNode.addContent(step);
		}else{//it is the fail case.
			this.step=new Element("Step"+String.valueOf(stepid));
			rootNode.addContent(step);
		}
		
		activityclassname=activity.getClass().getSimpleName();//get current class name    
	    Element classname=new Element("classname");
	    classname.setText(activityclassname);
	    step.addContent(classname);
	    viewgroup=new Element("Viewgroup");
    	nogroup=new Element("Nogroup");
    	easyoperate=new Element("Easyoperate");
    	step.addContent(viewgroup);
    	//viewgroup.setText("none");
    	step.addContent(nogroup);
    	//nogroup.setText("none");
    	step.addContent(easyoperate);
	}
	*/
	
	/*
	public Outputresult(Solo soloinput,int stepid, boolean fail) throws JDOMException, IOException{
		
		
		
		
		
		
		
		solo=soloinput;
		//////////////////////////////////////////
		this.stepid=stepid;//this is for "ok","close"case
		
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("/sdcard/result.xml");
		doc = (Document) builder.build(xmlFile);
		Element rootNode =doc.getRootElement();
		
		Activity activity=solo.getCurrentActivity();	
	    
		if(!fail){
			this.step=new Element("Step"+String.valueOf(stepid));
			rootNode.addContent(step);
		}else{//it is the fail case.
			this.step=new Element("Fail");
			rootNode.addContent(step);
			Element stepidfail=new Element("stepid");//before action
			stepidfail.setText(String.valueOf(stepid));
			step.addContent(stepidfail);
		}
		
		activityclassname=activity.getClass().getSimpleName();//get current class name    
	    Element classname=new Element("classname");
	    classname.setText(activityclassname);
	    step.addContent(classname);
	    viewgroup=new Element("Viewgroup");
    	nogroup=new Element("Nogroup");
    	easyoperate=new Element("Easyoperate");
    	step.addContent(viewgroup);
    	//viewgroup.setText("none");
    	step.addContent(nogroup);
    	//nogroup.setText("none");
    	step.addContent(easyoperate);
	}
	*/
	   /* 
	    
	    /////set attribute
	    
	    if(stepid==test.Main){
	        Attribute attribute=new Attribute("type","main");
	        newclass.setAttribute(attribute);
	        rootNode.addContent(newclass);
	    }else if(stepid==test.Menu){
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
    	*/
    	
    	//viewgroup=newclass.addContent("Viewgroup");
    	//nogroup=newclass.addContent("Nogroup");
		
		
	
	
	@SuppressLint("NewApi")
	public void Groupviewdeeper(View v,Element listsubclass, int subid,String motherclass, String clicktypestr) throws InterruptedException{//v is from the first groupview to go to the deeper		
		for(int i=0; i<((ViewGroup)v).getChildCount(); i++){
			//++subid;
			if(v.isClickable()){
			  ++subid;	//the first v is a listview
			  ++number; //every list item ++number
			}
			System.out.println(++count); 
			View v1=((ViewGroup)v).getChildAt(i);
			if(v1 instanceof ViewGroup){// v1 is a viewgroup
				if(v1.isClickable()){
					Element error=new Element("error");
					error.setText("Both parent and child list are clickable."+"parent:"+v.getClass().getName()+"children:"+v1.getClass().getName());
					listsubclass.addContent(error);
					//listsubclass.addContent("Something wrong").setText("Both parent and child list are clickable");
					Groupviewdeeper(v1,listsubclass, subid, motherclass,clicktypestr);
				}else{
					if(v.isClickable() && ((ViewGroup)v1).getChildCount()==0){
						--number;//here is a patch of the easyoperate continual ID. The detail can find the weekreport in the 8/2/2018
					}else{
						Groupviewdeeper(v1,listsubclass, subid, motherclass,clicktypestr);
					}
				}
			}else{//v1 is not a viewgroup
				if(v1.isClickable()){//it may a button in the listview case. The button override the listview
				    Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
				    //Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
    				Attribute attrid=new Attribute("id",String.valueOf(nogroupid));
    				newnogroup.setAttribute(attrid); 
				    
				 
				    Element alert=new Element("alert");
				    alert.setText("override case a clickable viewgroup and a clickable viewtext");
				    newnogroup.addContent(alert);
				   /*
				    if(override.contains(v.getId())){
    					Element alert2=new Element("alert");
    					alert2.setText("override happens, the viewtext is added two times");
    					newnogroup.addContent(alert2);
    				}
				    */
				    ////add viewclass
				    Element viewclass=new Element("viewclass");
				    viewclass.setText(v1.getClass().getName());
                    newnogroup.addContent(viewclass);//add the view style
                    
                    //clicktype
                	////short or long
					Element clicktype=new Element("clicktype");
					newnogroup.addContent(clicktype);
					clicktype.setText(clicktypestr);
                    
                    
				    if(v1 instanceof TextView){
				    	Element viewtext=new Element("viewtext");
						viewtext.setText(((TextView)v1).getText().toString());
						newnogroup.addContent(viewtext);//if the ungroupview is the textview, set the textview
				    }else{
				    	Element viewtext=new Element("viewtext");
 				    	//viewtext.setText("not a TextView");modiy 9/7/2017
 				    	newnogroup.addContent(viewtext);
				    	
				    	//newnogroup.addContent("viewtext").setText("");// if not, set ""
				    }
				    nogroup.addContent(newnogroup);//add the the nogroup view to the element nogroup.
				
			    }else{//the viewtext is not clickable
			    	Element newsubview= new Element("ID"+String.valueOf(subid));
    				Attribute attrid=new Attribute("id",String.valueOf(subid));
    				newsubview.setAttribute(attrid); 
			    	
			    	Element viewId=addAndroidId(newsubview,v1);
    				
    				
			    	Element type=new Element("childviewclass");
					
					type.setText(v1.getClass().getName());
					newsubview.addContent(type);
					Element viewtext;
					
				  	////short or long
					Element clicktype=new Element("clicktype");
					newsubview.addContent(clicktype);
					clicktype.setText(clicktypestr);
					
					//float xPoisition=v1.getX();
					//float yPoisition=v1.getY();
					
					
					int[] location = new int[2];
					v1.getLocationOnScreen(location);
					
					Element xPositionele=new Element("xposition");
					xPositionele.setText(String.valueOf(location[0]));
					
					Element yPositionele=new Element("yposition");
					yPositionele.setText(String.valueOf(location[1]));
					
					/*
					String pos="asd";
					try{
					   pos=v1.getTag().toString();
					}catch(Exception e){
						
					}
					
					Element tag123=new Element("tag123");
						tag123.setText(pos);
						newsubview.addContent(tag123);
					*/
							
							
					//newsubview.addContent("Viewclass").setText(v.getClass().getName());//add the view style
					if(v1 instanceof TextView){
					    viewtext=new Element("viewtext");
						viewtext.setText(((TextView)v1).getText().toString());
						newsubview.addContent(viewtext);
						//if the ungroupview is the textview, set the textview
				    }else{
				    	//newsubview.addContent("viewtext").setText("");// if not, set ""
				        viewtext=new Element("viewtext");
				    	//viewtext.setText("not a TextView");modiy 9/7/2017
				    	newsubview.addContent(viewtext);
				    }
					
					
				    ///////need to add easyoperation
					if(number==currentnumber){//every list item add number. If currentnumber==number, the subviews are on the same list item
						  if(v1 instanceof TextView){
							  runableID.addContent(type.clone());
							  runableID.addContent(viewtext.clone());
						  }

					}else{
					     
					
					      runableID=new Element("runableID");
					      runableID.setAttribute(new Attribute("id",String.valueOf(number)));
		                  //viewclass.clone();
					      Element motherviewclass=new Element("motherviewclass");
					      motherviewclass.setText(motherclass);
					      runableID.addContent(motherviewclass);
					      
					      
					      
					      Element Listindex=new Element("listindex");
					      Listindex.setText(String.valueOf(subid));
					      runableID.addContent(Listindex);
					      //rubableID.addContent();
		//			      runableID.addContent(type.clone());
					      runableID.addContent(type.clone());
		                  runableID.addContent(viewtext.clone());
		                  runableID.addContent(clicktype.clone());
		                  runableID.addContent(viewId.clone());
		            
		                  runableID.addContent(xPositionele.clone());
		                  runableID.addContent(yPositionele.clone());
		                  //runableID.getchild
		            
					      easyoperate.addContent(runableID);
					      currentnumber=number;		      		      
					}
				
					listsubclass.addContent(newsubview);
				 }
			
		  }
		}
		
		
		
		//return subid;	
	}

	private Element addAndroidId(Element newsubview, View v) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
    	Element viewid = new Element("androidid");
    	newsubview.addContent(viewid);
    	
    	if(v.getId()<10000){//find its father
    		ViewGroup father= (ViewGroup)v.getParent();
    		int ancestorsid=0;
    		View son=v;
    		while(true){
    			if(father.getId()>10000){
    				//Element fatherele= new Element("father");
		    	    
		    	    //viewid.addContent(fatherele);
		    		
		    		
		    	    Element ancestors=new Element("ancestors"+String.valueOf(ancestorsid++));
		    	    viewid.addContent(ancestors);
		    	    
		    	    
		    	    
		    		///set father id
		    		Element fatheridele=new Element("fatherid");
		    		ancestors.addContent(fatheridele);
		    		fatheridele.setText(String.valueOf(father.getId()));
		    		
		    		///set index
		    		Element fatherindex=new Element("index");
		    		ancestors.addContent(fatherindex);
		    		fatherindex.setText(String.valueOf(father.indexOfChild(son)));
		    		
		    		///set father string
		    		Element fatherstring=new Element("fathertext");
		    		ancestors.addContent(fatherstring);
		    		fatherstring.setText(father.getResources().getResourceName(father.getId()));
    				break;
    			}else{
		    	    
    				Element ancestors=new Element("ancestors"+String.valueOf(ancestorsid++));
		    	    viewid.addContent(ancestors);
    				
    				///set index
		    		Element fatherindex=new Element("index");
		    		ancestors.addContent(fatherindex);
		    		fatherindex.setText(String.valueOf(father.indexOfChild(son)));
		    		son=father;
		    		father= (ViewGroup)father.getParent();
    			}

    		}

    	}else{

    		Element itsownid= new Element("ownid");
    		viewid.addContent(itsownid);
    		itsownid.setText(String.valueOf(v.getId()));
    		/*
    		if(v.getId()==16908890){
    			System.out.println("bingo");
    		    View v1=solo.getView(v.getId());
    		    Thread.sleep(5000);
    		    solo.clickOnView(v1);
    		    
    		}
    		*/
    		
    		
    		Element owntext=new Element("ownText");
    		viewid.addContent(owntext);
    		owntext.setText(v.getResources().getResourceName(v.getId()));
    	}
		return viewid;
		
		
		
		
		
	}





	public Element Outputcurrent(int stepid, Element rootNode) throws InterruptedException{
		this.rootNode=rootNode; 
		
		////set id
		this.stepid=stepid;
		this.step=new Element("Step"+String.valueOf(stepid));
		rootNode.addContent(step);
		
		
		////set classname
		Activity activity=solo.getCurrentActivity();
		activityclassname=activity.getClass().getSimpleName();//get current class name    
	    
		Element classname=new Element("Classname");
	    classname.setText(activityclassname);
	    step.addContent(classname);
	    
	    
	    viewgroup=new Element("Viewgroup");
    	nogroup=new Element("Nogroup");
    	easyoperate=new Element("Easyoperate");
    	step.addContent(viewgroup);
    	//viewgroup.setText("none");
    	step.addContent(nogroup);
    	//nogroup.setText("none");
    	step.addContent(easyoperate);
		
    	alltext=new Element("AllText");
    	step.addContent(alltext);
		
		ArrayList<View> currentviews=solo.getCurrentViews();
		
		/*
		if(stepid==2){
			System.out.println("bingo");
		}
		*/
		for (View v:currentviews){
			
			if(v.isShown()){
				if(v instanceof EditText){
				   addedittext(v);
				}else if(v.isClickable()){
				   addview(v,"short");	
				   if(v.isLongClickable()){//it is possible a short clickable and a long clickable
					   addview(v,"long");
				   }
				}
				
				if(v instanceof TextView){
					addalltext(v);
				}			
			}
		}
		
		
		return step;
	}

	@SuppressLint("NewApi")
	private void addalltext(View v){
		if(!((TextView)v).getText().toString().isEmpty()){
		
				Element onetext= new Element("ID"+String.valueOf(++alltextcount)+"text");
			    alltext.addContent(onetext);
			    onetext.setText(((TextView)v).getText().toString());
		}
	}
	
	
	

	private void addedittext(View v){
		    Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
		    Attribute attrid=new Attribute("id",String.valueOf(nogroupid));
	     	newnogroup.setAttribute(attrid);  				
		
	     	
	     	
	     	////setview class
	    	Element viewclass=new Element("viewclass");
	    	//viewclass.setText(v.getClass().getName());
	    	viewclass.setText("android.widget.EditText");//2018.2.28
	    	newnogroup.addContent(viewclass);
		    
	    	
	    	////set resource id
	    	Element viewid = new Element("androidid");
	    	newnogroup.addContent(viewid);
	    	
	    	if(v.getId()<10000){//find its father   //before the 3.23 it is "<10"
	    		ViewGroup father= (ViewGroup)v.getParent();
	    		int ancestorsid=0;
	    		View son=v;
	    		while(true){
	    			if(father.getId()>10000){
	    				//Element fatherele= new Element("father");
			    	    
			    	    //viewid.addContent(fatherele);
			    		
			    		
			    	    Element ancestors=new Element("ancestors"+String.valueOf(ancestorsid++));
			    	    viewid.addContent(ancestors);
			    	    
			    	    
			    	    
			    		///set father id
			    		Element fatheridele=new Element("fatherid");
			    		ancestors.addContent(fatheridele);
			    		fatheridele.setText(String.valueOf(father.getId()));
			    		
			    		///set index
			    		Element fatherindex=new Element("index");
			    		ancestors.addContent(fatherindex);
			    		fatherindex.setText(String.valueOf(father.indexOfChild(son)));
			    		
			    		///set father string
			    		Element fatherstring=new Element("fathertext");
			    		ancestors.addContent(fatherstring);
			    		fatherstring.setText(father.getResources().getResourceName(father.getId()));
	    				break;
	    			}else{
			    	    
	    				Element ancestors=new Element("ancestors"+String.valueOf(ancestorsid++));
			    	    viewid.addContent(ancestors);
	    				
	    				///set index
			    		Element fatherindex=new Element("index");
			    		ancestors.addContent(fatherindex);
			    		fatherindex.setText(String.valueOf(father.indexOfChild(son)));
			    		son=father;
			    		father= (ViewGroup)father.getParent();
	    			}

	    		}
	
	    	}else{
	
	    		Element itsownid= new Element("ownid");
	    		viewid.addContent(itsownid);
	    		itsownid.setText(String.valueOf(v.getId()));

	    		Element owntext=new Element("ownText");
	    		viewid.addContent(owntext);
	    		owntext.setText(v.getResources().getResourceName(v.getId()));
	    	}
	    	
	    	
	    	
	    	
	    	/////content
			if (v.getContentDescription()!=null) {
				Element contentEle=new Element("contentext");
			    newnogroup.addContent(contentEle);
			    contentEle.setText(v.getContentDescription().toString());
			}
			
			////hint
			if (((EditText)v).getHint()!=null){
				Element hintEle=new Element("hintext");
				newnogroup.addContent(hintEle);
				hintEle.setText(((EditText) v).getHint().toString());
			}
			/*
			if(v.getId()==2131361812){
				System.out.println("bingonew");
			}
			*/
			
			////edittype
			Element edittype=new Element("edittype");
			newnogroup.addContent(edittype);
			if(((EditText)v).getInputType()==InputType.TYPE_NUMBER_FLAG_DECIMAL | ((EditText)v).getInputType()==InputType.TYPE_CLASS_NUMBER | ((EditText)v).getInputType()==(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER)){
				edittype.setText("digit");
			}else{
				edittype.setText("string");
			}
			
			
			////find text on adjacy view
			//first level
			Element adjacy=new Element("adjacyText");
			newnogroup.addContent(adjacy);
			
			Element level1=new Element("level1");
			adjacy.addContent(level1);
			
			Element level2=new Element("level2");
			adjacy.addContent(level2);
			
			Element level3=new Element("level3");
			adjacy.addContent(level3);
			
			//first level
			findadjacy(level1,v);
			//second level
			if(v.getParent()!=null){
				ViewGroup vfather= (ViewGroup)v.getParent();
				findadjacy(level2,vfather);
				//third level
				if(vfather.getParent()!=null){
					ViewGroup vgrandpa= (ViewGroup)vfather.getParent();
					findadjacy(level3,vgrandpa);
				}
				
				
			}
	    	
	        //add viewtext	    			    	
	    	Element viewtext=new Element("viewtext");
	    	newnogroup.addContent(viewtext);
	    	viewtext.setText(((TextView)v).getText().toString());
		
	    	//add focusable
	    	Element focusable=new Element("focusable");
	    	newnogroup.addContent(focusable);
	    	focusable.setText(String.valueOf(v.isFocusable()));
	    	
	    	
	    	
//////////////easyoperate case////////////
				    runableID=new Element("runableID");
			    	runableID.setAttribute(new Attribute("id",String.valueOf(++number)));
			    //viewclass.clone();
				
			    	for(Element childitem:newnogroup.getChildren()){
			    		runableID.addContent(childitem.clone());
			    		
			    	}
			    	/*
			    	 runableID.addContent(viewclass.clone());
			         runableID.addContent(viewtext.clone());
			         runableID.addContent(clicktype.clone());
			     */
				     easyoperate.addContent(runableID);
				     currentnumber=number;
	    	
	    	
	    	
	    	/*
	    	
		    ////short
	    	Element clicktype=new Element("clicktype");
		    newnogroup.addContent(clicktype);
		    clicktype.setText("short");
		*/
		    //////////////easyoperate case////////////
			/*
		    runableID=new Element("runableID");
	    	runableID.setAttribute(new Attribute("id",String.valueOf(++number)));
	    //viewclass.clone();
		
	    	 runableID.addContent(viewclass.clone());
	         runableID.addContent(viewtext.clone());
	         runableID.addContent(clicktype.clone());
	     
		     easyoperate.addContent(runableID);
		     currentnumber=number;
		     */
		     
		     
		     nogroup.addContent(newnogroup);
		 
	}
	
	
	@SuppressLint("NewApi")
	private void addview(View v, String type) throws InterruptedException{
			    if(v instanceof ViewGroup){//v is a viewgroup	
			    	    Element newviewgroup=new Element("viewID"+String.valueOf(++viewgroupid));//a new viewgroup
						Element viewclass=new Element("viewclass");//add the view style
						viewclass.setText(v.getClass().getName());
						newviewgroup.addContent(viewclass);
						viewgroup.addContent(newviewgroup);
						Element subclass=new Element("subclass");
						newviewgroup.addContent(subclass);
						Groupviewdeeper(v,subclass,0,v.getClass().getName(),type);	
						
						
						
				}else if(v instanceof TextView){
					    
					    Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
					    Attribute attrid=new Attribute("id",String.valueOf(nogroupid));
				     	newnogroup.setAttribute(attrid);  				
					
				     	
				     	////setview class
				    	Element viewclass=new Element("viewclass");
				    	viewclass.setText(v.getClass().getName());
				    	newnogroup.addContent(viewclass);
					    
				    	
				    	
				    	////set resource id
				    	Element viewid = new Element("androidid");
				    	newnogroup.addContent(viewid);
				    	
				    	
				    	if(v.getId()<10000){//find its father
				    		int maxcycle=0;
				    		ViewGroup father= (ViewGroup)v.getParent();
				    		int ancestorsid=0;
				    		View son=v;
				    		while(true){
				    			if(maxcycle==5){
				    				return;
				    			}else{
				    				maxcycle++;
				    			}
				    			
				    			
				    			
				    			if(father.getId()>10000){
				    				//Element fatherele= new Element("father");
						    	    
						    	    //viewid.addContent(fatherele);
						    		
						    		
						    	    Element ancestors=new Element("ancestors"+String.valueOf(ancestorsid++));
						    	    viewid.addContent(ancestors);
						    	    
						    	    
						    	    
						    		///set father id
						    		Element fatheridele=new Element("fatherid");
						    		ancestors.addContent(fatheridele);
						    		fatheridele.setText(String.valueOf(father.getId()));
						    		
						    		///set index
						    		Element fatherindex=new Element("index");
						    		ancestors.addContent(fatherindex);
						    		fatherindex.setText(String.valueOf(father.indexOfChild(son)));
						    		
						    		///set father string
						    		Element fatherstring=new Element("fatherstring");
						    		ancestors.addContent(fatherstring);
						    		fatherstring.setText(father.getResources().getResourceName(father.getId()));
				    				break;
				    			}else{
						    	    
				    				Element ancestors=new Element("ancestors"+String.valueOf(ancestorsid++));
						    	    viewid.addContent(ancestors);
				    				
				    				///set index
						    		Element fatherindex=new Element("index");
						    		ancestors.addContent(fatherindex);
						    		fatherindex.setText(String.valueOf(father.indexOfChild(son)));
						    		son=father;
						    		try {
						    		  father= (ViewGroup)father.getParent();
						    		}catch(Exception e){
						    			return;
						    		}
				    			}

				    		}
				    			
				    		
				    		/*
				    		
				    		if(father.getId()>10){
				    		
					    	    Element fatherele= new Element("father");
					    	    
					    	    viewid.addContent(fatherele);
					    		
					    		
					    		///set father id
					    		Element fatheridele=new Element("fatherid");
					    		viewid.addContent(fatheridele);
					    		fatheridele.setText(String.valueOf(father.getId()));
					    		
					    		///set index
					    		Element fatherindex=new Element("index");
					    		viewid.addContent(fatherindex);
					    		fatherindex.setText(String.valueOf(father.indexOfChild(v)));
					    		
					    		///set father string
					    		Element fatherstring=new Element("fatherstring");
					    		viewid.addContent(fatherstring);
					    		fatherstring.setText(father.getResources().getResourceName(father.getId()));
				    		}else{
				    			System.out.println("fathernoid:"+((TextView)v).getText().toString());
				    		}
				    		*/
				    	}else{
				
				    		
				    		
				    		Element itsownid= new Element("ownid");
				    		viewid.addContent(itsownid);
				    		itsownid.setText(String.valueOf(v.getId()));
				    		//v.
				    		
				    		Element owntext=new Element("ownText");
				    		viewid.addContent(owntext);
				    		owntext.setText(v.getResources().getResourceName(v.getId()));
				    	}
				    	
				    	
				    	/////content
						if (v.getContentDescription()!=null) {
							Element contentEle=new Element("contentext");
						    newnogroup.addContent(contentEle);
						    contentEle.setText(v.getContentDescription().toString());
						}
				    	
						//if it is a checkbox, set a checked or not in the current state
						if(v instanceof CheckBox){
							boolean checked=((CheckBox) v).isChecked();
							Element checkedEle=new Element("compoundbutton");
							newnogroup.addContent(checkedEle);
							checkedEle.setText(String.valueOf(checked));
						}
						
						///add before due
						Element enableEle =new Element("enable");
						boolean enableBoolean=((TextView)v).isEnabled();
						enableEle.setText(String.valueOf(enableBoolean));
						newnogroup.addContent(enableEle);
						
						
						
				        //add viewtext	    			    	
				    	Element viewtext=new Element("viewtext");
				    	newnogroup.addContent(viewtext);
				    	viewtext.setText(((TextView)v).getText().toString());
				    	
				    	
				    	if(v instanceof CompoundButton){
				    		boolean checked=((CompoundButton) v).isChecked();
				    		Element checkedEle=new Element("compoundbutton");
							newnogroup.addContent(checkedEle);
							checkedEle.setText(String.valueOf(checked));
				    	}
				    	
				    			    	
					    ////short
				    	Element clicktype=new Element("clicktype");
					    newnogroup.addContent(clicktype);
					    clicktype.setText(type);
					    //////////////easyoperate case////////////
						
					    runableID=new Element("runableID");
				    	runableID.setAttribute(new Attribute("id",String.valueOf(++number)));
				    //viewclass.clone();
					
				    	for(Element childitem:newnogroup.getChildren()){
				    		runableID.addContent(childitem.clone());
				    		
				    	}
				    	/*
				    	 runableID.addContent(viewclass.clone());
				         runableID.addContent(viewtext.clone());
				         runableID.addContent(clicktype.clone());
				     */
					     easyoperate.addContent(runableID);
					     currentnumber=number;
					     
					     nogroup.addContent(newnogroup);
					     
					     
				}else if(v instanceof ImageView){//2018.2.8 change the ImageButton to ImageView due to the app acv.
					     
				    Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
				    Attribute attrid=new Attribute("id",String.valueOf(nogroupid));
			     	newnogroup.setAttribute(attrid);  				
				
			     	
			     	////setview class
			    	Element viewclass=new Element("viewclass");
			    	viewclass.setText(v.getClass().getName());
			    	newnogroup.addContent(viewclass);
				    
			    	
			    	
			    	////set resource id
			    	Element viewid = new Element("androidid");
			    	newnogroup.addContent(viewid);
			    	
			    	if(v.getId()<10){//find its father
			    		ViewGroup father= (ViewGroup)v.getParent();
			    		int ancestorsid=0;
			    		View son=v;
			    		while(true){
			    			if(father.getId()>10){
			    				//Element fatherele= new Element("father");
					    	    
					    	    //viewid.addContent(fatherele);
					    		
					    		
					    	    Element ancestors=new Element("ancestors"+String.valueOf(ancestorsid++));
					    	    viewid.addContent(ancestors);
					    	    
					    	    
					    	    
					    		///set father id
					    		Element fatheridele=new Element("fatherid");
					    		ancestors.addContent(fatheridele);
					    		fatheridele.setText(String.valueOf(father.getId()));
					    		
					    		///set index
					    		Element fatherindex=new Element("index");
					    		ancestors.addContent(fatherindex);
					    		fatherindex.setText(String.valueOf(father.indexOfChild(son)));
					    		
					    		///set father string
					    		Element fatherstring=new Element("fatherstring");
					    		ancestors.addContent(fatherstring);
					    		fatherstring.setText(father.getResources().getResourceName(father.getId()));
			    				break;
			    			}else{
					    	    
			    				Element ancestors=new Element("ancestors"+String.valueOf(ancestorsid++));
					    	    viewid.addContent(ancestors);
			    				
			    				///set index
					    		Element fatherindex=new Element("index");
					    		ancestors.addContent(fatherindex);
					    		fatherindex.setText(String.valueOf(father.indexOfChild(son)));
					    		son=father;
					    		father= (ViewGroup)father.getParent();
			    			}

			    		}
		
			    	}else{
			
			    		Element itsownid= new Element("ownid");
			    		viewid.addContent(itsownid);
			    		itsownid.setText(String.valueOf(v.getId()));
			    		
			    		Element owntext=new Element("ownText");
			    		viewid.addContent(owntext);
			    		owntext.setText(v.getResources().getResourceName(v.getId()));
			    	}
			    	
			    	/////content
					if (v.getContentDescription()!=null) {
						Element contentEle=new Element("contentext");
					    newnogroup.addContent(contentEle);
					    contentEle.setText(v.getContentDescription().toString());
					}
			    			    	
				    ////short
			    	Element clicktype=new Element("clicktype");
				    newnogroup.addContent(clicktype);
				    clicktype.setText(type);
				    //////////////easyoperate case////////////
				    runableID=new Element("runableID");
			    	runableID.setAttribute(new Attribute("id",String.valueOf(++number)));
			    //viewclass.clone();
				
			    	for(Element childitem:newnogroup.getChildren()){
			    		runableID.addContent(childitem.clone());
			    		
			    	}
			    	/*
			    	 runableID.addContent(viewclass.clone());
			         runableID.addContent(viewtext.clone());
			         runableID.addContent(clicktype.clone());
			     */
				     easyoperate.addContent(runableID);
				     currentnumber=number;
				    
				    
					/*
				    runableID=new Element("runableID");
			    	runableID.setAttribute(new Attribute("id",String.valueOf(++number)));
			    //viewclass.clone();
				
			    	 runableID.addContent(viewclass.clone());
			         runableID.addContent(viewtext.clone());
			         runableID.addContent(clicktype.clone());
			     
				     easyoperate.addContent(runableID);
				     currentnumber=number;
				     */
				     
				     
				     nogroup.addContent(newnogroup);
				     
				}
				
				
	}
	



	private void findadjacy(Element level1, View v) {
		
		
		if(v.getParent()!=null){
			ViewGroup adfather=(ViewGroup)v.getParent();
			int childcount=adfather.getChildCount();
			int numberofchild=0;
			for (int i=0;i<childcount;i++){
				
				View brother=adfather.getChildAt(i);
				
				if(brother instanceof TextView){
					
					String brothertext=((TextView) brother).getText().toString();
					Element IDele=new Element("ID"+String.valueOf(numberofchild++)+"text");
					IDele.setText(brothertext);
					level1.addContent(IDele);
				}
			}
		}
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
	
	
	
/*	
	
    public void Outputcurrent() throws JDOMException, IOException{
    	System.out.println("outputcurrent");
    	
    	ArrayList<View> currentviews = solo.getCurrentViews();
    	
    	
    	for (View v:currentviews){//because the currentviews are a list, so it need to be processed at the first layer
    	
    	  if(v.isShown()){
    		if(v.isClickable()){
    			//v.isl
    			if(v instanceof ViewGroup){//v is a viewgroup
    				Element newviewgroup=new Element("viewID"+String.valueOf(++viewgroupid));//a new viewgroup
    				Element viewclass=new Element("viewclass");//add the view style
    				viewclass.setText(v.getClass().getName());
    				newviewgroup.addContent(viewclass);
    				viewgroup.addContent(newviewgroup);
    				Element subclass=new Element("subclass");
    				newviewgroup.addContent(subclass);
    				Groupviewdeeper(v,subclass,0,v.getClass().getName(),"short");
    				
    			}else{//v is not a viewgroup
    			   				
    				if(v instanceof ImageButton){
    					Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
        				Attribute attrid=new Attribute("id",String.valueOf(nogroupid));
        				newnogroup.setAttribute(attrid);  				
        				
        				Element viewclass=new Element("viewclass");
        				viewclass.setText(v.getClass().getName());
        				newnogroup.addContent(viewclass);
        				
        			//	Element viewtext=new Element("viewtext");
    				//	newnogroup.addContent(viewtext);
    				//	viewtext.setText(((TextView)v).getText().toString());
    					
    					Element imagebutton=new Element("imagebutton");//image button does not have viewtext
    					newnogroup.addContent(imagebutton);
    					imagebutton.setText(String.valueOf(imagebuttoncount++));				
    					////short
    					Element clicktype=new Element("clicktype");
    					newnogroup.addContent(clicktype);
    					clicktype.setText("short");			
    					runableID=new Element("runableID");
    					runableID.setAttribute(new Attribute("id",String.valueOf(++number)));
    					
    					 runableID.addContent(viewclass.clone());
    		             runableID.addContent(imagebutton.clone());
    		             runableID.addContent(clicktype.clone());
    					 easyoperate.addContent(runableID);
    					 currentnumber=number;
        			   	nogroup.addContent(newnogroup);
    					
    				}else{//other textview case
    		//			if(((TextView)v).getText().toString().equals("1")){
    		//				System.out.println("bingo");
    		//			}
    					
    					
    				    Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
    				    Attribute attrid=new Attribute("id",String.valueOf(nogroupid));
    			     	newnogroup.setAttribute(attrid);  				
    				
    			    	Element viewclass=new Element("viewclass");
    			    	viewclass.setText(v.getClass().getName());
    			    	newnogroup.addContent(viewclass);
    				    Element viewtext=new Element("viewtext");
				    	newnogroup.addContent(viewtext);
				    	viewtext.setText(((TextView)v).getText().toString());
					
					    ////short
				    	Element clicktype=new Element("clicktype");
					    newnogroup.addContent(clicktype);
					    clicktype.setText("short");
					
			    		runableID=new Element("runableID");
				    	runableID.setAttribute(new Attribute("id",String.valueOf(++number)));
		            //viewclass.clone();
					
				    	 runableID.addContent(viewclass.clone());
		                 runableID.addContent(viewtext.clone());
		                 runableID.addContent(clicktype.clone());
		             
					     easyoperate.addContent(runableID);
					     currentnumber=number;
    		   	    	nogroup.addContent(newnogroup);
    				
    				}
    				
    				
    			
    			}
    		  	
    		}
    		
    		/////long clickable
    		if(v.isLongClickable()){
    			//v.isl
    			if(v instanceof ViewGroup){//v is a viewgroup
    				Element newviewgroup=new Element("viewID"+String.valueOf(++viewgroupid));//a new viewgroup
    				Element viewclass=new Element("viewclass");//add the view style
    				viewclass.setText(v.getClass().getName());
    				newviewgroup.addContent(viewclass);
    				viewgroup.addContent(newviewgroup);
    				Element subclass=new Element("subclass");
    				newviewgroup.addContent(subclass);
    				Groupviewdeeper(v,subclass,0,v.getClass().getName(),"long");
    				
    			}else{//v is not a viewgroup
    				Element newnogroup= new Element("ID"+String.valueOf(++nogroupid));
    				Attribute attrid=new Attribute("id",String.valueOf(nogroupid));
    				newnogroup.setAttribute(attrid);  				
    				
    				Element viewclass=new Element("viewclass");
    				viewclass.setText(v.getClass().getName());
    				newnogroup.addContent(viewclass);
    				Element viewtext=new Element("viewtext");
					newnogroup.addContent(viewtext);
					viewtext.setText(((TextView)v).getText().toString());
					
				    ////long
					Element clicktype=new Element("clicktype");
					newnogroup.addContent(clicktype);
					clicktype.setText("long");
					
					
					
					
					runableID=new Element("runableID");
					runableID.setAttribute(new Attribute("id",String.valueOf(++number)));
		            //viewclass.clone();
					
					 runableID.addContent(viewclass.clone());
		             runableID.addContent(viewtext.clone());
		             runableID.addContent(clicktype.clone());
					 easyoperate.addContent(runableID);
					 currentnumber=number;
    			//	if(override.contains(v.getId())){
    			//		Element alert=new Element("alert");
    			//		alert.setText("override happens");
    			//		newnogroup.addContent(alert);
    			//	}
    				nogroup.addContent(newnogroup);
    				
    				
    				
    				
    			}	
    		}
    		
    		
    	  }
    		
    		
    		
    	}
    	
    	
    	boolean onlyclosecase=false;
    	/////try to click ok and close at here
    	if(easyoperate.getChildren().size()==1){
    		Element onlyitem=easyoperate.getChildren().get(0);
    		String viewtext=onlyitem.getChild("viewtext").getText();
    		if(viewtext.equalsIgnoreCase("ok") || viewtext.equalsIgnoreCase("close")){
    			
    			ArrayList<View> viewswait = solo.getCurrentViews();
    			solo.clickOnButton(0);
    			
    			solo.waitForCondition(new Mycondition(viewswait,solo), 5000);//wait the viewswait's changing
    			//solo.sleep(3000);
    			
    			
    			Outputresult uioutput=new Outputresult(solo,stepid,false);
    	    	uioutput.Outputcurrent();
    			onlyclosecase=true;
    			
    		}
    		
    		
    	}
    	
    	
    	
    	
    	
    	
    	
    	if(onlyclosecase==false){//this if is ok and close case
    	   XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		   xmlOutput.setFormat(Format.getPrettyFormat());
		   xmlOutput.output(doc, new FileWriter("/sdcard/result.xml"));
    	}
    	
    	
    
  }  	
*/
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
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

