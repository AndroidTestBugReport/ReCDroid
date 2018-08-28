package cz.romario.opensudoku.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.robotium.solo.Solo;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import cz.romario.opensudoku.gui.FolderListActivity;
import android.view.ViewGroup;

 
@SuppressWarnings("rawtypes")
public class test extends ActivityInstrumentationTestCase2{

	private Solo solo;
	public Activity activity;
    static final int Main=0;
    static final int Menu=1;
    public Activity thisactivity;
	
    //private static String mainActiviy="com.dozingcatsoftware.asciicam.AsciiCamActivity";
    private static String mainActiviy="com.newsblur.activity.InitActivity";
    //private static String mainActiviy="com.mkulesh.micromath.plus.MainActivityPlus";
    //private static String mainActiviy="net.gsantner.markor.activity.MainActivity";
    //private static String mainActiviy="caldwell.ben.bites.Bites";
    //private static String mainActiviy="org.liberty.android.fantastischmemo.ui.AnyMemo";
    //private static String mainActiviy="com.rigid.birthdroid.BirthdroidActivity";
    //private static String mainActiviy="org.awesomeapp.messenger.RouterActivity";
    //private static String mainActiviy="com.moez.QKSMS.ui.welcome.WelcomeActivity";
    //private static String mainActiviy="org.y20k.transistor.MainActivity";
    //private static String mainActiviy="org.addhen.smssync.presentation.view.ui.activity.MainActivity";
    //private static String mainActiviy="com.orpheusdroid.screenrecorder.MainActivity";
    //private static String mainActiviy="app.librenews.io.librenews.views.MainFlashActivity";
    //private static String mainActiviy="com.mikepenz.fastadapter.app.SampleActivity";
    //private static String mainActiviy ="com.olam.MainSearch";
    //private static String mainActiviy ="bander.notepad.NoteList";
    //private static String mainActiviy ="net.robotmedia.acv.ui.ComicViewerActivity";
    //private static String mainActiviy = "cz.romario.opensudoku.gui.FolderListActivity";
    //private static String mainActiviy="me.kuehle.carreport.gui.ReportActivity";
    //private static String mainActiviy="de.pixart.messenger.ui.StartUI";
    //private static String mainActiviy="com.secupwn.aimsicd.AIMSICD";
    //private static String mainActiviy="com.jtxdriggers.android.ventriloid.Main";
    //private static String mainActiviy="com.github.yeriomin.yalpstore.UpdatableAppsActivity";
    //private static String mainActiviy="de.luhmer.owncloudnewsreader.NewsReaderListActivity";
    //private static String mainActiviy="org.openhab.habdroid.ui.OpenHABMainActivity";
    //private static String mainActiviy="pt.lighthouselabs.obd.reader.activity.MainActivity";
    //private static String mainActiviy="com.fsck.k9.activity.Accounts";
    //private static String mainActiviy="com.vestrel00.daggerbutterknifemvp.ui.main.MainActivity";
    //private static String mainActiviy="com.simplecity.amp_library.ui.activities.MainActivity";
    //private static String mainActiviy="org.odk.collect.android.activities.SplashScreenActivity";
    //private static String mainActiviy="vocabletrainer.heinecke.aron.vocabletrainer.activity.MainActivity";
    //private static String mainActiviy="org.wordpress.android.ui.WPLaunchActivity";
    
    
    //private static String packageName="com.dozingcatsoftware.asciicam";
    private static String packageName="com.newsblur";
    //private static String packageName="com.mkulesh.micromath.plus";
    //private static String packageName="net.gsantner.markor";
    //private static String packageName="caldwell.ben.bites";
    //private static String packageName="org.liberty.android.fantastischmemo";
    //private static String packageName="com.rigid.birthdroid";
    //private static String packageName="im.zom.messenger";
    //private static String packageName="com.moez.QKSMS";
    //private static String packageName="org.y20k.transistor";
    //private static String packageName="org.addhen.smssync";
    //private static String packageName="com.orpheusdroid.screenrecorder";
    //private static String packageName="app.librenews.io.librenews";
    //private static String packageName="com.mikepenz.fastadapter.app";
    //private static String packageName ="com.olam";
    //private static String packageName ="bander.notepad";
    //private static String packageName="net.androidcomics.acv";
    //private static String packageName = "cz.romario.opensudoku";
    //private static String packageName = "me.kuehle.carreport";
    //private static String packageName = "de.pixart.openmessenger";//for the share
    //private static String packageName = "de.pixart.messenger";
    //private static String packageName="com.SecUpwN.AIMSICD";
    //private static String packageName="com.jtxdriggers.android.ventriloid";
    //private static String packageName="com.github.yeriomin.yalpstore";
    //private static String packageName="de.luhmer.owncloudnewsreader";
    //private static String packageName="org.openhab.habdroid";
    //private static String packageName="pt.lighthouselabs.obd.reader";
    //private static String packageName="com.fsck.k9";
    //private static String packageName="com.vestrel00.daggerbutterknifemvp";
    //private static String packageName="another.music.player";
    //private static String packageName="org.odk.collect.android";
    //private static String packageName="vocabletrainer.heinecke.aron.vocabletrainer";
    //private static String packageName="org.wordpress.android";
    
    private static Class<?> launchActivityClass;
    
    static {
    	try {
    	launchActivityClass = Class.forName(mainActiviy);
    	} catch (ClassNotFoundException e) {
    	throw new RuntimeException(e);
    	}
    	}
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public test() {
        super(packageName, launchActivityClass);
    }
    
	
	//public test(){
	//	super("cz.romario.opensudoku", FolderListActivity.class);
		// TODO Auto-generated constructor stub
	//}
	
    @Override
	public void setUp() throws Exception  {
		super.setUp();
		this.activity = this.getActivity();
		solo=new Solo(getInstrumentation(), getActivity());
		//System.setOut(System.out);
	}
    
    /*@Override
	public void setUp() throws Exception  {
		super.setUp();
		solo=new Solo(getInstrumentation(), getActivity());
		//System.setOut(System.out);
		
	}
	*/
/*
    public int Outputcurrent(int tag) throws JDOMException, IOException{
    	System.out.println("outputcurrent");
        UiCurrentoutput uioutput=new UiCurrentoutput(solo,tag);   	
    	uioutput.Outputcurrent();
    	return 1;
    }
  */  
    
    public Element Outputresult(int stepid, Element Rootnode) throws InterruptedException{
    	//Outputresult uioutput=new Outputresult(solo,nodstepid);
    	Outputresult uioutput=new Outputresult(solo);
    	Element step=uioutput.Outputcurrent(stepid, Rootnode);    	
    	return step;
    }
    
    
    public void Outfail(int stepid){
    //	Outputresult uioutput=new Outputresult(solo,Rootnode);
    //	uioutput.Outputcurrent(stepid);
    }
    
    
    /*
    public int Outresult(int stepid) throws JDOMException, IOException{
    	Outputresult uioutput=new Outputresult(solo,stepid,false);
    	uioutput.Outputcurrent();
    	return 1;
    }
    
    public int Outfail(int stepid) throws JDOMException, IOException{
    	Outputresult uioutput=new Outputresult(solo,stepid,true);
    	uioutput.Outputcurrent();
    	return 1;
    } 
    */
    //@Rule ExpectedException exception1 = ExpectedException.none();
    
    
	public void test2() throws IOException, InterruptedException, JDOMException{
    	//exception1.expect(SecurityException.class);
    	

		
		
		
		//solo.sendKey(KeyEvent.KEYCODE_MENU);
		
		//[144,156][608,194]
		
		//solo.clickOnImage(0);
		
		//solo.clickOnScreen(386, 193);
		//solo.clickOnText("Method");
		
		//solo.clickInList(1);
		
		//solo.sleep(1000000);
		//solo.clickOnText("SKIP");
		/*
		
		
		View v1=solo.getView(2131820703);
		solo.clickOnView(v1);
		solo.clickOnView(v1);
		solo.sleep(1000);
		solo.clickOnView(v1);
		*/
		
		//solo.clickOnText("settings1");
		
		//solo.clickOnText("GO TO LIBRENEWS");
		
		
		
		
		
		/*
		View asd=solo.getView(16908890);
		solo.clickOnView(asd);
		*/
		
		//solo.clickOnText("Add refueling");
		
		
		
		
		/*
		solo.clickOnView(solo.getView(2131361833));
		solo.sleep(5000);
		
		
		View asd=solo.getView(2131361810);
		System.out.println("bingo");
		solo.sleep(5000);
		*/
		
		
		
		
		/*
		solo.sendKey(KeyEvent.KEYCODE_MENU);
		solo.sleep(3000);
		*/
		
		
		
		/*
		solo.clickOnView(solo.getView(2131361833));
		
		solo.sleep(3000);
		
		*/
		/*
		solo.clickOnText("Add refueling");
		
		solo.sleep(3000);
		/*
		
		solo.clickOnText("Fuel consumption");
		
		solo.sleep(3000);
		
		
		solo.clickOnText("Fuel price");
		
		solo.sleep(3000);
		*/
		///follow commands on run
		//Activity firstActivity=solo.getCurrentActivity();
		
		//solo.clickInList(0);
		//solo.clickOnText("Easy");
		
		//solo.sleep(3000);

		thisactivity=this.getActivity();
		
		
		Activity currentActivity=solo.getCurrentActivity();
		//currentActivity.getPackageName();
		
		//int currentOrientation = currentActivity.getResources().getConfiguration().orientation;
		//currentActivity.class.getSimpleName();
		
		if(!currentActivity.getLocalClassName().startsWith("com.android")){//detect the jump to other apps
			
			try{
				
		        ExecutorService executor = Executors.newSingleThreadExecutor();  
		        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {  

		        	
		        	
		            @Override  
		            public String call() throws Exception {  
		            	solo.sleep(5000);//for the newsblur, it has an open screen 7.31.2018
		            	
		            	//View v12=solo.getView(16908313);
		            	//solo.clickOnView(v12);
		            	/*
		            	View v11=solo.getView(2131558466);
		            	
		            	View v2=((ViewGroup) v11).getChildAt(2);
		            	
		            	solo.clickOnView(v2);
		            	
		            	solo.clickOnText("Settings");
		            	
		            	solo.scrollDown();
		            	
		            	solo.scrollDown();
		            	
		            	solo.scrollDown();
		            	
		            	solo.sleep(1000);
		            	//solo.clickOnView(v11);
		            	*/
		            	/*
		        		View v12=solo.getView(16908308);
		        		solo.clickOnView(v12);
		        		
		        		View v1=solo.getView(2131296269);
		        		solo.enterText((android.widget.EditText) v1, "lunarlightgg@gmail.com");
		        		View v2=solo.getView(2131296270);
		        		solo.enterText((android.widget.EditText) v2, "lunar1986");

		        		View v3=solo.getView(2131296272);
		        		solo.clickOnView(v3);
		        		
		        		solo.sleep(5000);
		            	*/
		            	
		            	
		            	/*
		            	solo.goBack();
		            	solo.sleep(2000);
		            	*/
		            	
		            	//solo.clickOnActionBarHomeButton();
		            	
		            	//wordpress
		            	
		            	/*wordpress
		            	View v1=solo.getView(2131756098);
		        		solo.enterText((android.widget.EditText) v1, "521373737@qq.com");
		        		View v12=solo.getView(2131756004);
		        		solo.clickOnView(v12);
		        		solo.sleep(2000);
		        		
		        		View v2=solo.getView(2131755626);
		        		solo.clickOnView(v2);
		        		
		        		solo.sleep(1000);
		            	
		        		View v3=solo.getView(2131756099);
		        		solo.enterText((android.widget.EditText) v3, "zjz2010625");

		            	View v4=solo.getView(2131756004);
		            	solo.clickOnView(v4);
		        		
		            	solo.sleep(2000);
		        		*/
		        		Format format = Format.getPrettyFormat(); 
		        		XMLOutputter genout = new XMLOutputter(); 
		        		genout.setFormat(format);
		        		/////write on the internal storage
		        		
		        		FileOutputStream genresult=thisactivity.openFileOutput("result.xml", 0);
		        		Element Root=new Element("Result");
		        		Document docresult = new Document();
		        		docresult.addContent(Root);
		        		genout.output(docresult, genresult);  
		        		genresult.close();
		        		
		        		/////read the run.xml
		        		FileInputStream readrun = getActivity().openFileInput("run.xml");
		        		SAXBuilder runbuilder = new SAXBuilder();
		        		Document readrundoc=(Document) runbuilder.build(readrun);
		        		Element Rootrun=readrundoc.getRootElement();
		        		
		        		
		        		
		        		////modify the result.xml
		                ///first I need to read
		        		FileInputStream readresult = getActivity().openFileInput("result.xml");
		        		SAXBuilder builder = new SAXBuilder();
		        		Document readdoc = (Document) builder.build(readresult);
		        		Element Rootnode=readdoc.getRootElement();
		            	
		            	
		            	
		        		String clicktype;
		        		String direction;
		        		ArrayList<View> viewswait = solo.getCurrentViews();
		        		
		        		String rotateOrNot="false";
		        		//boolean rotateOrNot=false;
		        		String rotateStr=Rootrun.getAttributeValue("Rotate");
		        		if(rotateStr.equals("True")){
		        			rotateOrNot="true";
		        		}else if(rotateStr.equals("TrueBack")){
		        			rotateOrNot="trueback";
		        		}
		        		
		        		
		        		List<Element> list=Rootrun.getChildren();
		        		
		        		
		        		
		        		Activity activity=solo.getCurrentActivity();
		        		final String FirstActivityClassname=activity.getClass().getSimpleName();//get First Activity class name   
		        		
		        		Attribute backatLastStep=new Attribute("back","false");
		        		
		        		Rootnode.setAttribute(backatLastStep);
		        		
		        	    boolean middleRotate=false;
		        		
		        		for(int i=0; i<list.size();i++){
		        			
		        			backatLastStep.setValue("false");
		        			
		        			Element item=(Element) list.get(i);
		        			/*
		        			if(item.getName().equals("Rotate")){
		        				rotateOrNot=true;
		        				continue;//rotate case
		        			}
		        			*/
		        			int stepid= Integer.valueOf(item.getAttributeValue("id"));
		        			
		        			String actionTypeStr=item.getChild("Actiontype").getText();
		        			
		        			String[] split=actionTypeStr.split("\\.");//pick up the last string, com.android.internal.view.menu.ActionMenuItemView, only pick up ActionMenuItemView
		        			
		        			if(split.length!=0){
		        				actionTypeStr=split[split.length-1];//at here it is the last string
		        			}
		        			
		                    if(item.getChild("unExeID")!=null){//unexecutable case
		        			    if(actionTypeStr.equals("EditText")){
		        			    	
		        			    	
		                            Element androididEle=item.getChild("androidid");
		        					
		                            
		                            //long millisStart = System.currentTimeMillis();
		        					//View v;
		        					View v=getView(androididEle);//from ancestor or own id
		        			    	
		        					////////////get typewhat////
		        					String typeWhatStr=item.getChild("typeWhat").getText();
		        					if(typeWhatStr.contains("realspace")){//this is because the space can not be extracted in the xml.
		        						typeWhatStr=typeWhatStr.replaceAll("realspace"," ");
		        					}
		        					
		        					
		        					if(typeWhatStr.equals("default")){
		        						//do nothing						
		        					}else{
		        						
		        						solo.clearEditText((android.widget.EditText) v);
		        						solo.enterText((android.widget.EditText) v, typeWhatStr);
		        					//	 long millisEnd=System.currentTimeMillis();
		        					//	 if(millisEnd-millisStart<1000){
		        					//	  		Thread.sleep(1000 - (millisEnd-millisStart));
		        					//	 }
		        						//System.out.println("edittextTime:"+String.valueOf(System.currentTimeMillis()-millisStart));

		        						//solo.sleep(1000);
		        					}					
		        					
		        			    }if(actionTypeStr.equals("CheckBox")){
		        			    	//currently checkbox only have default
		        			    }if(actionTypeStr.equals("CheckedTextView")){
		        			    	//currenlt CheckedTextView only have default
		        			    }
		        				
		        				
		                    }else{//executable case
		                    	if(actionTypeStr.equals("Noaction")){//because switch does not support || or && so we use if elseif at here
		        					//do nothing
		        				}else if(actionTypeStr.equals("TextView") || actionTypeStr.equals("ImageView") || actionTypeStr.equals("ActionMenuItemView") || actionTypeStr.endsWith("MenuButton") || actionTypeStr.endsWith("Button") || actionTypeStr.endsWith("ImageView") || actionTypeStr.endsWith("TextView") || actionTypeStr.contains("bs") || actionTypeStr.contains("an")){
		        					
		        					Element androididEle=item.getChild("androidid");
		        					//long millisStart = System.currentTimeMillis();
		        					//View v;
		        					View v=getView(androididEle);//from ancestor or own id
		        					
		        					
		        					////////////////short click or long click
		        					clicktype=item.getChild("clicktype").getText();//this item is an step element
		        					
		        					if(clicktype.equals("short")){
		        						    solo.clickOnView(v);
		        				         }else if(clicktype.equals("Long")){
		        				    	//  String text=item.getChild("Parameter").getText();
		        				    	    solo.clickLongOnView(v);
		        				    }
		        					//long millisEnd=System.currentTimeMillis();
		        					//if(millisEnd-millisStart<1000){
		        					//  		Thread.sleep(1000 - (millisEnd-millisStart));
		        					//}
		        					
		        					
		        					solo.waitForCondition(new Mycondition(viewswait,solo), 2000);//wait the viewswait's changing
		        				    viewswait = solo.getCurrentViews();//update the viewswait
		        						
		        				}else if(actionTypeStr.equals("JustClick")){//It can increase robust of the code
		        					clicktype=item.getChild("clicktype").getText();//this item is an step element
		        					
		        					long millisStart = System.currentTimeMillis();
		        					if(item.getChild("clickText")!=null){
		        					     try{
		        					    	 
		        					    	 if(item.getChild("clickText").getText().length()==0){
		        					    		 
		        					    		 throw new java.lang.Error("this is very bad");
		        					    	 }else{
		        					    	 
			        					    	 if(clicktype.equals("short")){
			        						        solo.clickOnText(item.getChild("clickText").getText());
			        					    	 }else if(clicktype.equals("Long")){
			        					    		 solo.clickLongOnText(item.getChild("clickText").getText());
			        					    	 }
		        					    	 }
		        						        
		        						        
		        						     }catch(Error e){
		        						    	 if(false){//item.getChild("xposition")!=null && !middleRotate){
		        						    		 String x=item.getChild("xposition").getText();
		        						    		 String y=item.getChild("yposition").getText();
		        						    		 
		        							    	 if(clicktype.equals("short")){
		        									        solo.clickOnScreen(Float.parseFloat(x), Float.parseFloat(y));
		        								    	 }else if(clicktype.equals("Long")){
		        								    		 solo.clickLongOnScreen(Float.parseFloat(x), Float.parseFloat(y));
		        								    	 }
		        						    		 
		        						    	 }else{
		        										Element androididEle=item.getChild("androidid");
		        										
		        										
		        										
		        										//View v;
		        										View v=getView(androididEle);//from ancestor or own id
		        										////////////////short click or long click
		        										
		        										
		        										if(clicktype.equals("short")){
		        											    solo.clickOnView(v);
		        									         }else if(clicktype.equals("Long")){
		        									    	//  String text=item.getChild("Parameter").getText();
		        									    	    solo.clickLongOnView(v);
		        									    }
		        						    	 }
		        						     }
		        						
		        					}
		        			//		long millisEnd=System.currentTimeMillis();
		        			//		if(millisEnd-millisStart<1000){
		        			//		  		Thread.sleep(1000 - (millisEnd-millisStart));
		        			//		}
		        					
		        					solo.waitForCondition(new Mycondition(viewswait,solo), 2000);//wait the viewswait's changing
		        					viewswait = solo.getCurrentViews();//update the viewswait
		        					
		        					
		        					
		        					
		        				}else if(actionTypeStr.equals("ClickList")){
		        					  clicktype=item.getChild("clicktype").getText();
		        					  int listid=Integer.valueOf(item.getChild("Parameter").getText());
		        					  /*
		        					  ArrayList<ListView> Listoflist=solo.getCurrentViews(ListView.class);
		        					  ListView listviewitem=Listoflist.get(0);
		        					//  listid=1000000;
		        					  if(listviewitem.getChildCount()<listid){
		        						//  int stepid= Integer.valueOf(item.getAttributeValue("id"));
		        				    	  Outfail(stepid);
		        				    	  fail("fail, no match");
		        					  }
		        					  */
		        /*					  
		        					  if(i==3){
		        						  try{  
		        						     solo.clickInList(2);
		        						  }catch(Error e){
		        							  System.out.println("AA");
		        						  }
		        					  }
		        	*/				  
		        					  //long millisStart = System.currentTimeMillis();
		        					  if(clicktype.equals("short")){
		        						     try{
		        						        solo.clickInList(listid);
		        						     }catch(Error e){
		        						    	 //do nothing
		        						     }
		        					     }else{
		        						     try{
		        					    	     solo.clickLongInList(listid);
		        						     }catch(Error e){
		        						    	 //do nothing
		        						     }
		        					  }
		        					  //long millisEnd=System.currentTimeMillis();
		        					  //if(millisEnd-millisStart<10000){
		        					  //		Thread.sleep(10000 - (millisEnd-millisStart));
		        					  //}
		        					//System.out.println("millis:"+String.valueOf(millisStart));
		        				    //System.out.println("current time:"+String.valueOf(System.currentTimeMillis()));
		        			//		  System.out.println("clickListTime:"+String.valueOf(System.currentTimeMillis()-millisStart));
		        			//		  long millisEnd=System.currentTimeMillis();
		        			//		  if(millisEnd-millisStart<1000){
		        			//				Thread.sleep(1000 - (millisEnd-millisStart));
		        			//		  }
		        					  
		        					  solo.waitForCondition(new Mycondition(viewswait,solo), 2000);
		        					  viewswait = solo.getCurrentViews();
		        				}else if(actionTypeStr.equals("back")){
		        						
		        					solo.goBack();
		        					solo.sleep(1000);
		        					backatLastStep.setValue("true");
		        				}
		                    }//end else	
		                    
		                    //solo.sleep(5000);
		            		//////if there is an outputcurrent in the run, we need to write it the result
		        			if(item.getChild("outputcurrent").getText().equals("yes") || i==list.size()-1 || i==list.size()-2){
		        				Element step=Outputresult(stepid,Rootnode);
		        				//if(step.getChild("Easyoperate").getChildren().size()==0){
		        					//solo.sleep(5000);
		        				//	Rootnode.removeContent(step);
		        				//	Outputresult(stepid,Rootnode);
		        				//}
		        			}
		                    /*
		        			if(i==list.size()-2){
		        				if(rotateOrNot.equals("true")){
		        					try{
		        						Activity currentActivity=solo.getCurrentActivity();
		        						//currentActivity.getPackageName();
		        						int currentOrientation = currentActivity.getResources().getConfiguration().orientation;
		        						 // 1 for Configuration.ORIENTATION_PORTRAIT
		        						 // 2 for Configuration.ORIENTATION_LANDSCAPE
		                              // 0 for Configuration.ORIENTATION_SQUARE
		        						 if(currentOrientation==1){
		        							 solo.setActivityOrientation(0);
		        						 }else{
		        							 solo.setActivityOrientation(1);
		        						 }
		        						 
		        						 middleRotate=true;
		        					}catch(Exception e){
		        						fail("Crash");
		        					}
		        				}
		        				solo.sleep(1000);
		        				
		        			}
		        			*/
		        			//if(!actionTypeStr.equals("Noaction")){
		        				
		        			//}
		        			
		        		}//end for
		        		solo.sleep(3000);
		        		//solo.sleep(3000);//add by the due day
		        		
		        		//solo.clickOnView(solo.getView(R.id.exitButton));
		        		
		        		//ActivityMonitor currentMonitor=solo.getActivityMonitor();
		        		//solo.waitForEmptyActivityStack(50000);
		        		//getViews()
		        		//solo.clickLongOnScreen(0, 0);
		        		if(rotateOrNot.equals("true")){
		        			try{						
		        				
		        				Activity currentActivity=solo.getCurrentActivity();
		        			    int currentOrientation = currentActivity.getResources().getConfiguration().orientation;
		        			 // 1 for Configuration.ORIENTATION_PORTRAIT
		        			 // 2 for Configuration.ORIENTATION_LANDSCAPE
		                 // 0 for Configuration.ORIENTATION_SQUARE
		        			 if(currentOrientation==1){
		        				 solo.setActivityOrientation(0);
		        			 }else{
		        				 solo.setActivityOrientation(1);
		        			 }
		        				solo.setActivityOrientation(0);
		        			}catch(Exception e){
		        				fail("Crash");
		        			}
		        		}
		        		/*
		        		else if(rotateOrNot.equals("trueback")){
		        			solo.setActivityOrientation(0);
		        			solo.sleep(1000);
		        			solo.goBack();
		        		}//close at the 3.24.2018
		        		*/
		        		Activity currentActivity=solo.getCurrentActivity();
		            	
		        		if(!currentActivity.getLocalClassName().startsWith("com.android")){//detect the jump to other apps
		                // TODO Auto-generated method stub  
			            	solo.goBackToActivity(FirstActivityClassname);//detect the dead lock
		        		}
		        		
		        		
		        		
		        		XMLOutputter modifyout = new XMLOutputter();  
		        		modifyout.setFormat(format);
		        		FileOutputStream fout=thisactivity.openFileOutput("result.xml", 0);
		        		modifyout.output(readdoc, fout);
		        		fout.close();
		        		
		        		
		                return "aa";
		                
		            }  
		        });  
				
		        executor.execute(future);  
		        try {  
		            String result = future.get(120000, TimeUnit.MILLISECONDS);  
		            System.out.println(result);  
		        } catch (Exception e) {  
		            // TODO Auto-generated catch block  
		        }
				
				//solo.goBackToActivity(FirstActivityClassname);//detect the dead lock
				
				}catch(Exception e){
					
				   //Rootnode.removeChildren();
				   fail("Hang");
				}
		}
		//com.android.internal
		
		
	 	//solo.goBack();
		
		
		   //solo.waitForEmptyActivityStack(10000);
		
		//Outputresult(stepid,Rootnode);	
		
		
		/*
		
		
		
		
		for(int i=0;i<list.size();i++){
			
			
			Element item=(Element) list.get(i);
			int stepid= Integer.valueOf(item.getAttributeValue("id"));
			
			
			Element action=item.getChild("action");
			switch(action.getChild("Actiontype").getText()){
			case "Noaction":
				break;
			case "ClickText":
			      clicktype=action.getChild("clicktype").getText();
			      String text=action.getChild("Parameter").getText();
			      //add fail case
			      if(!solo.searchText(text,true)){//can not find the text
			    	  //int stepid= Integer.valueOf(item.getAttributeValue("id"));
			    	  Outfail(stepid);
			    	  fail("fail, no match");
			      }
			      
			      
			      if(clicktype.equals("short")){
				            solo.clickOnText(text,0);
				         }else if(clicktype.equals("Long")){
				    	//  String text=item.getChild("Parameter").getText();
				    	    solo.clickLongOnText(text,0);
				    }
			      
			      
			      solo.waitForCondition(new Mycondition(viewswait,solo), 5000);//wait the viewswait's changing
			      viewswait = solo.getCurrentViews();//update the viewswait
			      break;
			      
			case "ClickImagebutton"://robotium do not support clickonlongimagebutton    //use the index(not source id) to click the image button
			      clicktype=action.getChild("clicktype").getText();
			      String imageidex=action.getChild("Parameter").getText();
			      
			      ArrayList<ImageButton> listofimagebutton=solo.getCurrentViews(ImageButton.class);//get the imagebutton view list
			      
			  
			      
			      
			      if(listofimagebutton.size()<Integer.valueOf(imageidex)){
			    	 // int stepid= Integer.valueOf(item.getAttributeValue("id"));
			    	  Outfail(stepid);
			    	  fail("fail, no match");
			      }
			          if(clicktype.equals("short")){
				    	 // String text=item.getChild("Parameter").getText();
				    	    solo.clickOnImageButton( Integer.valueOf(imageidex));
				      }else{
				    	    int imagebuttonid=solo.getImageButton(Integer.valueOf(imageidex)).getId();
				    	    solo.clickLongOnView(solo.getView(imagebuttonid));
				      }
			      
			      solo.waitForCondition(new Mycondition(viewswait,solo), 5000);//wait the viewswait's changing
			      viewswait = solo.getCurrentViews();//update the viewswait
			      break;      
			      
			case "ClickList":
				  clicktype=action.getChild("clicktype").getText();
				  int listid=Integer.valueOf(action.getChild("Parameter").getText());
				  
				  ArrayList<ListView> Listoflist=solo.getCurrentViews(ListView.class);
				  ListView listviewitem=Listoflist.get(0);
				//  listid=1000000;
				  if(listviewitem.getChildCount()<listid){
					//  int stepid= Integer.valueOf(item.getAttributeValue("id"));
			    	  Outfail(stepid);
			    	  fail("fail, no match");
				  }
				  
				  
				  if(clicktype.equals("short")){
					     solo.clickInList(listid);
				     }else{
					     solo.clickLongInList(listid);						  }
				
				  solo.waitForCondition(new Mycondition(viewswait,solo), 5000);
				  viewswait = solo.getCurrentViews();
				  break;
				  
			case "Orientation":
				 direction=action.getChild("Parameter").getText();
				 try{    
				     if(direction.equals("Landscape")){
					     solo.setActivityOrientation(0);
				     }else if(direction.equals("Portrait")){
					     solo.setActivityOrientation(1);
				     }
				 }catch(Exception e){
					 //int stepid= Integer.valueOf(item.getAttributeValue("id"));
			    	  Outfail(stepid);
					 fail("exception");
				 }
				 
				 
				 solo.sleep(5000);//wait for orientation
				 break;
				 
			case "menu":
				solo.sendKey(KeyEvent.KEYCODE_MENU);
				solo.waitForCondition(new Mycondition(viewswait,solo), 5000);
				viewswait = solo.getCurrentViews();
				
				break;
			}
		
		//////if there is an outputcurrent in the run, we need to write it the result
			if(item.getChild("outputcurrent").getText().equals("yes")){
				Outputresult(stepid,Rootnode);
				
				
				
				Outputresult(stepid,Rootnode);
						////make a new instance of Outputresult
						///Outputresult uioutput=new Outputresult(solo,Rootnode);
						
						///Outputresult(stepid);					
			}

		}
		*/
		
		
		
		///second I need to write
		/*
		XMLOutputter modifyout = new XMLOutputter();  
		modifyout.setFormat(format);
		FileOutputStream fout=this.getActivity().openFileOutput("result.xml", 0);
		modifyout.output(readdoc, fout);
		fout.close();
		*/
		//solo.clickOnActionBarHomeButton();
		
    }


	private View getView(Element androididEle) {
		// TODO Auto-generated method stub
		View v;
		if(androididEle.getChild("ownid")==null){//this is that the target v does not have viewid, we need to find it from its ancestors
			int beginIndex=androididEle.getChildren().size()-1;
			String fatherIdstr=androididEle.getChildren().get(beginIndex).getChild("fatherid").getText();
			v=solo.getView(Integer.parseInt(fatherIdstr));
			
			
			for(int j=androididEle.getChildren().size()-1; j>=0 ;j--){
				//androididEle.getChildren().get(j).getChild("fatherid");
				int ancestorIndex=Integer.parseInt(androididEle.getChildren().get(j).getChild("index").getText());
				v=((ViewGroup) v).getChildAt(ancestorIndex);//every time to get the v by the index
			}
			
			
		}else{
		    String ownid=androididEle.getChild("ownid").getText();
		    int ownidInt=Integer.parseInt(ownid);
		    v=solo.getView(ownidInt);
		    
		    //v=solo.getView(ownidInt);
		    //v=solo.getView(Integer.parseInt(ownid));
		    /*
		    try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    solo.clickOnView((View)(v.getParent()));
		    */
		}
		return v;
	}
  }	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		        /*
				
				////11.8.2017
				//solo.clickOnView(solo.getView(android.R.id.button1));
				
				//solo.clickOnText("https://github.com/netmackan/ATimeTracker");
				//solo.clickOnText(Pattern.quote("https://github.com/netmackan/ATimeTracker"));
				//solo.clickOnImage(0);
				
				solo.sleep(3000);
				
				//solo.clickon
				
				Outresult(1);
				
				solo.clickOnView(solo.getView(2131361833));
				//solo.clickOnView(solo.getView("menu_add_refueling"));
				
				View v=solo.getView("edtMileage");
				
			    System.out.println("Hint   "+((EditText)v).getHint().toString());
			    
				solo.enterText((android.widget.EditText) v, "100");	
				
				solo.sleep(3000);
				
				View v1=solo.getView("edtVolume");
				
				solo.enterText((android.widget.EditText) v1, "10");
				
				solo.sleep(3000);
				
				View v3=solo.getView("edtPrice");
				
				solo.enterText((android.widget.EditText) v3, "10");
				
				solo.sleep(3000);
				
				View v2=solo.getView("menu_save");
				
				solo.clickOnView(v2);
				
			
				//solo.clickOnScreen(744, 75);
				
				solo.sleep(3000);
				//solo.clickOnText(Pattern.quote("Add refueling"));
				//solo.clickOnText(Pattern.quote("Add refueling"));
				
				///solo.sleep(3000);
				
				
				//solo.sendKey(KeyEvent.KEYCODE_MENU);
				//solo.sleep(3000);
				//solo.clickOnText("Share");
				
				
				//solo.clickOnButton(2);
				//solo.sleep(3000);
				//solo.sendKey(KeyEvent.KEYCODE_MENU);
				//solo.sleep(3000);
				//solo.clickOnActionBarItem(0x0);
				//solo.sleep(6000);
				
				*/
				
				
				
				
				/*
				//boolean contentsfirstscreen=false;
				for(int i=0;i<list.size();i++){
					Element item=(Element) list.get(i);
					Element action=item.getChild("action");
					switch(action.getChild("Actiontype").getText()){
					case "Noaction":
						break;
					case "ClickText":
					      clicktype=action.getChild("clicktype").getText();
					      String text=action.getChild("Parameter").getText();
					      //add fail case
					      if(!solo.searchText(text,true)){//can not find the text
					    	  int stepid= Integer.valueOf(item.getAttributeValue("id"));
					    	  Outfail(stepid);
					    	  fail("fail, no match");
					      }
					      
					      
					      if(clicktype.equals("short")){
						            solo.clickOnText(text,0);
						         }else if(clicktype.equals("Long")){
						    	//  String text=item.getChild("Parameter").getText();
						    	    solo.clickLongOnText(text,0);
						    }
					      
					      
					      solo.waitForCondition(new Mycondition(viewswait,solo), 5000);//wait the viewswait's changing
					      viewswait = solo.getCurrentViews();//update the viewswait
					      break;
					      
					case "ClickImagebutton"://robotium do not support clickonlongimagebutton    //use the index(not source id) to click the image button
					      clicktype=action.getChild("clicktype").getText();
					      String imageidex=action.getChild("Parameter").getText();
					      
					      ArrayList<ImageButton> listofimagebutton=solo.getCurrentViews(ImageButton.class);//get the imagebutton view list
					      
					  
					      
					      
					      if(listofimagebutton.size()<Integer.valueOf(imageidex)){
					    	  int stepid= Integer.valueOf(item.getAttributeValue("id"));
					    	  Outfail(stepid);
					    	  fail("fail, no match");
					      }
					          if(clicktype.equals("short")){
						    	 // String text=item.getChild("Parameter").getText();
						    	    solo.clickOnImageButton( Integer.valueOf(imageidex));
						      }else{
						    	    int imagebuttonid=solo.getImageButton(Integer.valueOf(imageidex)).getId();
						    	    solo.clickLongOnView(solo.getView(imagebuttonid));
						      }
					      
					      solo.waitForCondition(new Mycondition(viewswait,solo), 5000);//wait the viewswait's changing
					      viewswait = solo.getCurrentViews();//update the viewswait
					      break;      
					      
					case "ClickList":
						  clicktype=action.getChild("clicktype").getText();
						  int listid=Integer.valueOf(action.getChild("Parameter").getText());
						  
						  ArrayList<ListView> Listoflist=solo.getCurrentViews(ListView.class);
						  ListView listviewitem=Listoflist.get(0);
						//  listid=1000000;
						  if(listviewitem.getChildCount()<listid){
							  int stepid= Integer.valueOf(item.getAttributeValue("id"));
					    	  Outfail(stepid);
					    	  fail("fail, no match");
						  }
						  
						  
						  if(clicktype.equals("short")){
							     solo.clickInList(listid);
						     }else{
							     solo.clickLongInList(listid);						  }
						
						  solo.waitForCondition(new Mycondition(viewswait,solo), 5000);
						  viewswait = solo.getCurrentViews();
						  break;
						  
					case "Orientation":
						 direction=action.getChild("Parameter").getText();
						 try{    
						     if(direction.equals("Landscape")){
							     solo.setActivityOrientation(0);
						     }else if(direction.equals("Portrait")){
							     solo.setActivityOrientation(1);
						     }
						 }catch(Exception e){
							 int stepid= Integer.valueOf(item.getAttributeValue("id"));
					    	  Outfail(stepid);
							 fail("exception");
						 }
						 
						 
						 solo.sleep(5000);//wait for orientation
						 break;
						 
					case "menu":
						solo.sendKey(KeyEvent.KEYCODE_MENU);
						solo.waitForCondition(new Mycondition(viewswait,solo), 5000);
						viewswait = solo.getCurrentViews();
						
						break;
					}
					
					//////if there is an outputcurrent in the run, we need to write it the result
					if(item.getChild("outputcurrent").getText().equals("yes")){	
						  //String strstepid=
						  int stepid= Integer.valueOf(item.getAttributeValue("id"));
						  Outresult(stepid);	
					}	
				}		
				*/

