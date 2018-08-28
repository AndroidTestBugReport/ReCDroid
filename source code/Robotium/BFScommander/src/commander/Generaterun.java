package commander;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

//import android.app.Activity;

public class Generaterun {

	static int count=0;
	static ArrayList<Integer> currentpathlist=new ArrayList<Integer>();
	static HashMap<String,String> quatitytrans;
	static HashMap<String,ArrayList<String>> similarityMap=new HashMap<String,ArrayList<String>>();//key is the from bugreport, value is from the result.xml
	static ArrayList<Element> currentPathList=new ArrayList<Element> ();//current path list in the record.xml
	static ArrayList<Element> pathToRun=new ArrayList<Element> ();//path to run
	//static ArrayList<Pathitem> currentpath= new ArrayList<Pathirem>();
	static HashSet<String> unexecuteClassName=new HashSet<String>();
	static ArrayList<String> typeWhatList104=new ArrayList<String>();
	static ArrayList<String> digitTypeWhatList104=new ArrayList<String>();
	static boolean backOrNot;
	static HashSet<String> typedSet=new HashSet<String>();
	
	
	
	static int runstep=0;
	
	public Generaterun() {
		System.out.println("success1");
		// TODO Auto-generated constructor stub
	}	
	
	
	static public void firstrun(String rotateOrNot, String address){

///////////generate record.xml/////////////
		Document docrecord = new Document();
		OutputStream writer = null;  
		try {  
		    writer = new FileOutputStream(address+"/middleResults/record.xml");  
		} catch (FileNotFoundException e1) {  
		    e1.printStackTrace();  
		}  
		
		
///////////generate root class///////////////
		Element Rootrecord=new Element("GraphEvent");
		docrecord.addContent(Rootrecord);//add the root
		
		Rootrecord.setAttribute(new Attribute("eventCount", "0"));
		
		////////////add three itmes
		Element OmitMatch=new Element("OmitMatch");
		Rootrecord.addContent(OmitMatch);
		
		////////////add three itmes
		Element typed=new Element("Typed");
		Rootrecord.addContent(typed);
		
		
		Element classitem=new Element("Classitem");
		Element path=new Element("Path");
		path.setAttribute(new Attribute("RunGo","1"));
		path.setAttribute(new Attribute("RunGodelay","false"));
		path.setAttribute(new Attribute("currentclassid","0"));
		path.setAttribute(new Attribute("NlpMh","false"));
		
		/////////////add the path root in classitem
		Element classEle=new Element("ID");
		classEle.setAttribute(new Attribute("id","0"));
		
		
		Element classnameEle=new Element("Classname");
		classnameEle.setText("this is the path root");
		
		Element similarEle=new Element("similarActivities");
		classEle.addContent(similarEle);
		
		
		
		classitem.addContent(classEle);
		classEle.addContent(classnameEle);
		
		Element menu=new Element("Menu");
		
		Rootrecord.addContent(classitem);
		Rootrecord.addContent(path);
		Rootrecord.addContent(menu);
				
		
		Element matchInformation=new Element("Match");
		Rootrecord.addContent(matchInformation);
		
		Element rule4=new Element("Rule4");
		Rootrecord.addContent(rule4);
		
//////////output record.xml to storage//////////////////////////////
		XMLOutputter outrecord = new XMLOutputter();
		Format formatrecord = Format.getPrettyFormat();  
		outrecord.setFormat(formatrecord);
		
		try {  
		    outrecord.output(docrecord, writer);  
		} catch (IOException e) {  
		    e.printStackTrace();  
		}
		
		try {
			writer.close();//close the writer
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//////////generate the first run.xml///////////////////////////////
		Document docrun = new Document();
		OutputStream writerrun = null;
		try {  
		    writerrun = new FileOutputStream(address+"/middleResults/run.xml");  
		} catch (FileNotFoundException e1) {  
		    e1.printStackTrace();  
		} 
		
		Element Root=new Element("Run");
		
	    if(rotateOrNot.equals("true")){
	    	Root.setAttribute(new Attribute("Rotate","True"));
	    }else if(rotateOrNot.equals("trueback")){
	    	Root.setAttribute(new Attribute("Rotate","TrueBack"));
	    }else{
	    	Root.setAttribute(new Attribute("Rotate","False"));
	    }
		
		addfirst(Root,"yes");
		XMLOutputter outrun = new XMLOutputter();
		Format formatrun = Format.getPrettyFormat();  
		outrun.setFormat(formatrun);
		docrun.addContent(Root);
		try {  
		    outrun.output(docrun, writerrun);  
		} catch (IOException e) {  
		    e.printStackTrace();  
		}
		
		try {
			writerrun.close();//close the writer
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	public static void addfirst(Element root, String yesorno){
		
        Element step=new Element("step");	
		root.addContent(step);

		Attribute stepid=new Attribute("id","1");
		step.setAttribute(stepid);
		////////////////////record the path/////////////////////////
		Element graphclassfrom=new Element("graphclassfrom");
		graphclassfrom.setText("firstrun");
		Element graphclassitem=new Element("graphclassitem");
		graphclassitem.setText("firstun");
		
		Element outputcurrent=new Element("outputcurrent");
		outputcurrent.setText(yesorno);
		
		step.addContent(graphclassfrom);
		step.addContent(graphclassitem);
		step.addContent(outputcurrent);
		
		//Element action=new Element("action");
		//step.addContent(action);

		//////////something about action/////////////////////////////////////////
		Element classname=new Element("classname");
		classname.setText("firstrun");
		step.addContent(classname);		//Attribute actionid=new Attribute("actionid","1");
		Element Actiontype=new Element("Actiontype");
		Actiontype.setText("Noaction");
		step.addContent(Actiontype);
	}
	
	/*
	//////menu run /// this version does have the menu dfs funtion, it may be one day work to add it
	public static void menurun() throws JDOMException, IOException {
		///operate on the record
				SAXBuilder builder = new SAXBuilder();
				File recordFile= new File("/home/yu/repeatbugreport/middleResults/record.xml");
				Document recorddoc= (Document) builder.build(recordFile);
				Element rootrecord=recorddoc.getRootElement();

				
				/////read the result.xml/////
				SAXBuilder sb = new SAXBuilder();//generate the builder
				String path="/home/yu/repeatbugreport/middleResults/result.xml";
				Document docresult=sb.build(new FileInputStream(path));//read the file
				Element rootresult=docresult.getRootElement();
		
				
				int menulistsize=rootrecord.getChild("Menu").getChildren().size();
				
				Element newmenuitem=new Element("newmenuitem");
				newmenuitem.setAttribute(new Attribute("id",String.valueOf(menulistsize)));
				rootrecord.getChild("Menu").addContent(newmenuitem);
				
				/////////////something form the result
				int lastresultid=rootresult.getChildren().size();
				Element finalresult=rootresult.getChildren().get(lastresultid-1);
				
				
				int newid=rootrecord.getChild("Classitem").getChildren().size()+1;	//this is the new class id
				//add it into the record
				Element newelement=new Element("ID");
				rootrecord.getChild("Classitem").addContent(newelement);				//add it into the classitem
	 			

				
				newelement.setAttribute("id",String.valueOf(newid));	
				newelement.addContent(finalresult.getChild("classname").clone());
	 			newelement.addContent(finalresult.getChild("Viewgroup").clone());
	 			newelement.addContent(finalresult.getChild("Nogroup").clone());
	 			newelement.addContent(finalresult.getChild("Easyoperate").clone());
				
	 			//read the class id and class name from run
	 			SAXBuilder runbuilder = new SAXBuilder();
				File runFile= new File("/home/yu/repeatbugreport/middleResults/run.xml");
				Document RunGoc= (Document) runbuilder.build(runFile);
				Element rootrun=RunGoc.getRootElement();
				
				
				int lastindex=rootrun.getChildren().size()-1;//lastindex of run
	 			Element lastrun=rootrun.getChildren().get(lastindex);
	 			
	 			String motherid=null;
	 			
	 			if(lastrun.getChild("action").getChild("classid")!=null){
	 			motherid=lastrun.getChild("action").getChild("classid").getText();
	 			}else{
	 				System.out.println("error,the run is not a menu run");
	 			}
	 			
	 			String motherclassname=lastrun.getChild("action").getChild("classname").getText();
	 			
	 			/////
	 			Element motheridelement=new Element("motherid");
	 			motheridelement.setText(motherid);
	 			Element motherclass=new Element("motherclass");
	 			motherclass.setText(motherclassname);
	 			Element menuid=new Element("menuid");
	 			menuid.setText(String.valueOf(newid));
	 			
	 			
	 			
	 			newmenuitem.addContent(motheridelement);
	 			newmenuitem.addContent(motherclass);
	 			newmenuitem.addContent(menuid);
	 			
	 			XMLOutputter xmlOutput = new XMLOutputter();
	 			xmlOutput.setFormat(Format.getPrettyFormat());
	 			xmlOutput.output(recorddoc, new FileWriter("/home/yu/repeatbugreport/middleResults/record.xml")); 	 			
	 			/////////////generate the common run same to the common
	 			

	 		    //////generate the run.xml////////////
	 			
	 			    Document docrun = new Document();
	 			    OutputStream writerrun = null;
	 			    try {  
	 			       writerrun = new FileOutputStream("/home/yu/repeatbugreport/middleResults/run.xml");  
	 		     	} catch (FileNotFoundException e1) {  
	 			       e1.printStackTrace();  
	 			    } 
	 			
	 			    Element Root=new Element("Run");
	 			
	 			    ///////////////////set the noaction, it is used to output the first screen
	 			    addfirst(Root,"no");
	 			    runstep++;
	 			    ////////////////////////////////
	 			    Element allpath=rootrecord.getChild("Path");	 			    
	 			    generaterun(allpath,rootrecord, Root);
	 			
	 			
	 			
	 			    XMLOutputter outrun = new XMLOutputter();
	 			    Format formatrun = Format.getPrettyFormat();  
	 			    outrun.setFormat(formatrun);
	 			    docrun.addContent(Root);
	 			    try {  
	 			       outrun.output(docrun, writerrun);  
	 		    	} catch (IOException e) {  
	 		    	    e.printStackTrace();  
	 		    	}
	 			
	 		    	try {
	 			    	writerrun.close();//close the writer
	 			    } catch (IOException e) {
	 			    	// TODO Auto-generated catch block
	 		    		e.printStackTrace();
	 		    	}
	 			
		// TODO Auto-generated method stub
		
	}
	*/
	/*
	/////////////////////////java copy file/////////////////////////////
	public static void copyFile(String oldPath, String newPath) {
	       try {
	           int bytesum = 0;
	           int byteread = 0;
	           File oldfile = new File(oldPath);
	           if (oldfile.exists()) { //文件存在时
	               InputStream inStream = new FileInputStream(oldPath); //读入原文件
	               FileOutputStream fs = new FileOutputStream(newPath);
	               byte[] buffer = new byte[1444];
	               int length;
	               while ( (byteread = inStream.read(buffer)) != -1) {
	                   bytesum += byteread; //字节数 文件大小
	                   System.out.println(bytesum);
	                   fs.write(buffer, 0, byteread);
	               }
	               inStream.close();
	           }
	       }
	       catch (Exception e) {
	           System.out.println("复制单个文件操作出错");
	           e.printStackTrace();

	       }

	   } 
	*/
	
	
	////////////////////////java del file///////////////////////////////
	   public static void delFile(String filePathAndName) {
	       try {
	           String filePath = filePathAndName;
	           filePath = filePath.toString();
	           java.io.File myDelFile = new java.io.File(filePath);
	           myDelFile.delete();

	       }
	       catch (Exception e) {
	           System.out.println("删除文件操作出错");
	           e.printStackTrace();

	       }

	   } 
	
	
	
	//8/25/2017
	static public void afterrun(ArrayList<Step> nlpsteplist, HashMap<Integer, ArrayList<Step>> nlpwholemap, ArrayList<Sents> sentslist, String rotateOrNot, ArrayList<String> typeWhatList, boolean backOrNotinput, ArrayList<String> digitTypeWhatList, String address) throws JDOMException, IOException{		
		
		backOrNot=backOrNotinput;
		typeWhatList104=typeWhatList;
		digitTypeWhatList104=digitTypeWhatList;
		///add executeClassName
		unexecuteClassName.add("android.widget.EditText");
		unexecuteClassName.add("android.widget.CheckBox");
		unexecuteClassName.add("android.widget.CheckedTextView");
		
		
		///add a dict
		quatitytrans=new HashMap<String,String>();
		quatitytrans.put("litre","l");
		quatitytrans.put("kilometer","km");
		quatitytrans.put("meter","m");
		quatitytrans.put("mile","m");
		quatitytrans.put("second","s");
		quatitytrans.put("minute","m");
		quatitytrans.put("centimeter","cm");
		quatitytrans.put("gram","g");
		quatitytrans.put("kilogram","kg");
		quatitytrans.put("watt","w");
		quatitytrans.put("kilowatt","kw");
		

		///////////read the report.xml////////////////////////////
		///operate on the record
				SAXBuilder recordbuilder = new SAXBuilder();
				File recordFile= new File(address+"/middleResults/record.xml");
				Document recorddoc= (Document) recordbuilder.build(recordFile);
				Element rootrecord=recorddoc.getRootElement();
		
		
		
		///////////read the result.xml after nlp python process ///////////////////
		SAXBuilder sb = new SAXBuilder();//generate the builder
		String path=address+"/middleResults/output/result.xml";		
		Document docresult;
		
		while(true){
			File file=new File(address+"/middleResults/output/result.xml");
			if(file.exists()){
				try{
                   docresult=sb.build(new FileInputStream(path));//read the file
				}catch(Exception e){
					continue;
				}
				
				
				break;
			}
		}
		
	    /////read the result.xml/////
			//SAXBuilder sb = new SAXBuilder();//generate the builder
			//String path="/home/yu/repeatbugreport/middleResults/output/result.xml";
			//Document docresult=sb.build(new FileInputStream(path));//read the file
			Element rootresult=docresult.getRootElement();
		
		////delete the result.xml after read it//////
			//we do not use this delete
			//delFile("/home/yu/repeatbugreport/output/result.xml");
			
		/////find the rotate event/////////
			ArrayList<Integer> rotatestep =new ArrayList<Integer>();//rotatestep can store all of the roate step id
			for(int i=0; i<nlpsteplist.size(); i++){
				String eventtype=nlpsteplist.get(i).getType();
				if(eventtype.equals("rotate")){
					rotatestep.add(i);
				}
			}
			
			//Element allpath=rootrecord.getChild("Path");
			
			ArrayList<Matched> extendone=new ArrayList<Matched>();//extend one steps case
			ArrayList<Matched> extendtwo=new ArrayList<Matched>();//extend two steps case
			
			Element typeEle=rootrecord.getChild("Typed");
			for(Element typeItem :typeEle.getChildren()){
				String text=typeItem.getText();
				typedSet.add(text);
			}
			
			
			
			checkresultnlpmatch(rootresult,nlpsteplist,nlpwholemap,sentslist,extendone,extendtwo,address);

			String addresult=addtoReportfile(rootresult, rootrecord, extendone, extendtwo);//currently, I do not how to use addresult. It has been defined in the method.
			
			
			
			checkPathtoRun(rootrecord, extendone, extendtwo, rotateOrNot,address);////The pathToRun should be filled, ArrayList<Element> pathToRun is defined on the globeable variable.
			
	//		generateRun();
			
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(recorddoc, new FileWriter(address+"/middleResults/record.xml"));  
			
		    System.out.println("asd");
			
            			
			
	}
	
	
	
	
	private static void generateRun() {
		 //////generate the run.xml////////////
		/*
	    Document docrun = new Document();
	    OutputStream writerrun = null;
	    try {  
	       writerrun = new FileOutputStream("./run.xml");  
     	} catch (FileNotFoundException e1) {  
	       e1.printStackTrace();  
	    } 
	
	    Element Root=new Element("Run");
	
	    ///////////////////set the noaction, it is used to output the first screen
	    addfirst(Root);// just output the first.
	    runstep++;
	    ////////////////////////////////
	
	    generaterun(allpath,rootrecord, Root);
	
	
	
	    XMLOutputter outrun = new XMLOutputter();
	    Format formatrun = Format.getPrettyFormat();  
	    outrun.setFormat(formatrun);
	    docrun.addContent(Root);
	    try {  
	       outrun.output(docrun, writerrun);  
    	} catch (IOException e) {  
    	    e.printStackTrace();  
    	}
	
    	try {
	    	writerrun.close();//close the writer
	    } catch (IOException e) {
	    	// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
		
		*/
		// TODO Auto-generated method stub
		
	}


	private static void checkPathtoRun(Element rootrecord, ArrayList<Matched> extendone, ArrayList<Matched> extendtwo, String rotateOrNot, String address) {
		//runPathwithDelay(rootrecord.getChild("Path"));
		
		Element runNode=runPathwithDelay(rootrecord.getChild("Path"));//in this method, pathtorun is changed
		
		if(runNode==null){//it may be the delay case, in this case they are some node rundo!=0 ,but delay =true. So no path to run.
		    removeAlldelay(rootrecord.getChild("Path"));
		    runNode=runPathwithDelay(rootrecord.getChild("Path"));
		    if(runNode==null){//it means no where to match any more
		    	allBoundaryAddOne(rootrecord.getChild("Path"));//add every rungo on the edge of the path as 1.
		    	//// this time it should be runNode!=null
			    runNode=runPathwithDelay(rootrecord.getChild("Path"));//in this case the pathToRun should be filled
		        if(runNode==null){
		        	System.out.println("it is unpossible to get a null runNode, you need to check your code in this function");
		        }
		    }
		}
		
		////////////////////////generate run.xml//////////////
		
		
	    Document docrun = new Document();
	    OutputStream writerrun = null;
	    try {  
	       writerrun = new FileOutputStream(address+"/middleResults/run.xml");  
     	} catch (FileNotFoundException e1) {  
	       e1.printStackTrace();  
	    } 
	
	    Element runRoot=new Element("Run");
	
	    ///////////////////set the noaction, it is used to output the first screen
	    addfirst(runRoot,"no");
	    //runstep++;
	    ////////////////////////////////
	    generateRun(rootrecord,runRoot, extendone, extendtwo);
	    
	    if(rotateOrNot.equals("true")){
	    	runRoot.setAttribute(new Attribute("Rotate","True"));
	    }else if(rotateOrNot.equals("trueback")){
	    	runRoot.setAttribute(new Attribute("Rotate","TrueBack"));
	    }else{
	    	runRoot.setAttribute(new Attribute("Rotate","False"));
	    }
	    
	    //System.out.println(pathToRun);
	    //System.out.println(pathToRun.get(0).getAttribute("RunGo").getValue());//the pathtorun do not have root of the path
	    //generaterun(allpath,rootrecord, Root);
	    
	    
	    //int pathSize=pathToRun.size();//because the root is the firstopen, do not need to add 1 as the new run.
	    int pathSize=runRoot.getChildren().size();
	    String existedCount=rootrecord.getAttributeValue("eventCount");
	    int newCount=Integer.valueOf(existedCount)+pathSize;
	    rootrecord.getAttribute("eventCount").setValue(String.valueOf(newCount));
	    
	    //eventCount
	    /*
	    if(rootrecord.getAttribute("eventsInsert")==null){
	    	
	    }
	*/
	
	    XMLOutputter outrun = new XMLOutputter();
	    Format formatrun = Format.getPrettyFormat();  
	    outrun.setFormat(formatrun);
	    docrun.addContent(runRoot);
	    try {  
	       outrun.output(docrun, writerrun);  
    	} catch (IOException e) {  
    	    e.printStackTrace();  
    	}
	
    	try {
	    	writerrun.close();//close the writer
	    } catch (IOException e) {
	    	// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
				
		// TODO Auto-generated method stub
		
	}


	private static Element generateRun(Element rootRecord,
			Element runRoot, ArrayList<Matched> extendone, ArrayList<Matched> extendtwo) {
		
		for(Element pathItem: pathToRun){
			
			if(pathItem.getName().equals("Path")){//added at 11.30.2017
				continue;
			}
			
			int currentClassId=Integer.parseInt(pathItem.getAttributeValue("currentclassid"));
			
			Element classItem=rootRecord.getChild("Classitem").getChildren().get(currentClassId);// this is in the Classitem
			Element Easyoperate=classItem.getChild("Easyoperate");
			List<Element> Easyoperatelist=Easyoperate.getChildren();
			
			if(pathItem.getAttribute("unExe")!=null){// this item has some unExecuteable actions

				String unExeID=pathItem.getAttributeValue("unExe");//this unExeID is not the itemId
				Element unExe=rootRecord.getChild("Match").getChildren().get(Integer.parseInt(unExeID));
								
				for(Element unExeItem: unExe.getChildren()){
					
					    ////every unExe is a step
						Element step=new Element("step");
						runRoot.addContent(step);
						step.setAttribute(new Attribute("id",String.valueOf(++runstep)));
					
	                    /////////////////
						///set outputcurrent
						  Element outputcurrent=new Element("outputcurrent");
						  outputcurrent.setText("no");
						  step.addContent(outputcurrent);
						  
						///set currentclass
						  Element currentClass=new Element("currentClass");
						  currentClass.setText(String.valueOf(currentClassId));
						  step.addContent(currentClass);
						///set sub id
						  Element unExeIDele=new Element("unExeID");
						  unExeIDele.setText(unExeID);
						  step.addContent(unExeIDele);
						  
						  ///set pageSubID

						  String subIDstr=unExeItem.getChild("pageSubId").getText();
						  Element subIDele=new Element("subID");
						  subIDele.setText(subIDstr);
						  step.addContent(subIDele);
						  
						////set action and something left
						  
						  ////////////
						  Element Easyoperateitem=null;//=Easyoperatelist.get(Integer.valueOf(subIDstr)-1);//2018.2.28
						  for(Element eleItem :Easyoperatelist){
							  if(eleItem.getAttributeValue("id").equals(subIDstr)){
								  Easyoperateitem=eleItem;
							  }
						  }
						  
						  
						  if(Easyoperateitem.getChild("motherviewclass")!=null){//the motherviewclass means the class is a viewgroup
							  //currently, this is no found unexecuteable viewgroup
							  if(Easyoperateitem.getChild("motherviewclass").getText().equals("android.widget.Spinner")){/// in this version, if we meet the android.widget.Spinner, we do nothing as default.
								  if(Easyoperateitem.getChild("childviewclass").getText().equals("android.widget.CheckedTextView")){
									  /*
									  String ownid=Easyoperateitem.getChild("androidid").getChild("ownid").getText();
									  Element viewID=new Element("viewID");
									  viewID.setText(ownid);
									  step.addContent(viewID);
									  */
									  
									  
									  //Element viewID=new Element("viewID");
									  step.addContent(Easyoperateitem.getChild("androidid").clone());
									  //step.addContent(viewID);
									  
									  Element actiontype=new Element("Actiontype");
									  actiontype.setText("CheckedTextView");
									  step.addContent(actiontype);
									  
									  Element actionwhat=new Element("actionwhat");
									  actionwhat.setText(unExeItem.getChild("actionWhat").getText());
									  step.addContent(actionwhat);
								  }
								  
								  Element actiontype=new Element("Nothing");
							  }/*else if(Easyoperateitem.getChild("motherviewclass").equals("android.widget.LinearLayout")){
								  //String childViewClass=Easyoperateitem.getChild("childviewclass").getText();
							  }*/
						  }else{//it is not a viewgroup
							  if(Easyoperateitem.getChild("viewclass").getText().equals("android.widget.EditText")){
								  /*
								  String ownid=Easyoperateitem.getChild("androidid").getChild("ownid").getText();
								  Element viewID=new Element("viewID");
								  viewID.setText(ownid);
								  step.addContent(viewID);
								  */
								  step.addContent(Easyoperateitem.getChild("androidid").clone());
								  
								  Element actiontype=new Element("Actiontype");
								  actiontype.setText("EditText");
								  step.addContent(actiontype);
								  
								  Element typeWhatEle=new Element("typeWhat");
								  /////at here we need to get input what//this information is in the match of the record
								  //unExeItem.getChild("typeWhat").getText();
								  typeWhatEle.setText(unExeItem.getChild("typeWhat").getText());
								  step.addContent(typeWhatEle);
								  
							  }if(Easyoperateitem.getChild("viewclass").getText().equals("android.widget.CheckBox")){
								  /*
								  String ownid=Easyoperateitem.getChild("androidid").getChild("ownid").getText();
								  Element viewID=new Element("viewID");
								  viewID.setText(ownid);
								  step.addContent(viewID);
								  */
								  step.addContent(Easyoperateitem.getChild("androidid").clone());
								  
								  Element actiontype=new Element("Actiontype");
								  actiontype.setText("CheckBox");
								  step.addContent(actiontype);
								  
								  Element typeWhatEle=new Element("actionWhat");
								  /////at here we need to get input what//this information is in the match of the record
								  //unExeItem.getChild("typeWhat").getText();
								  typeWhatEle.setText(unExeItem.getChild("actionWhat").getText());
								  step.addContent(typeWhatEle);
								  
							  }							  
						  }
				}
			}
			
			
			// this is for the Executeable actions part
			////every unExe is a step
				Element step=new Element("step");
				runRoot.addContent(step);
				step.setAttribute(new Attribute("id",String.valueOf(++runstep)));
			
			    
			
                /////////////////
				///set outputcurrent
				  Element outputcurrent=new Element("outputcurrent");
				  outputcurrent.setText("yes");
				  step.addContent(outputcurrent);
				  
				///set currentclass
				  Element currentClass=new Element("currentClass");
				  currentClass.setText(String.valueOf(currentClassId));
				  step.addContent(currentClass);
				///set sub id
				  Element subIdele=new Element("subId");
				  String sub=pathItem.getAttributeValue("subid");
				  subIdele.setText(sub);
				  step.addContent(subIdele);
				
				  
				  //Element Easyoperateitem=Easyoperatelist.get(Integer.valueOf(sub)-1);
				  ////////////
				  Element Easyoperateitem=null;//=Easyoperatelist.get(Integer.valueOf(subIDstr)-1);//2018.2.28
				  for(Element eleItem :Easyoperatelist){
					  if(eleItem.getAttributeValue("id").equals(sub)){
						  Easyoperateitem=eleItem;
					  }
				  }
				  
				  if(Easyoperateitem.getChild("motherviewclass")!=null){//the motherviewclass means the class is a viewgroup
					  /*if(Easyoperateitem.getChild("motherviewclass").getText().equals("android.widget.LinearLayout") && Easyoperateitem.getChild("childviewclass").equals("android.widget.TextView")){						  

						  step.addContent(Easyoperateitem.getChild("androidid").clone());
						  
						  
						  Element actiontype=new Element("Actiontype");
						  actiontype.setText("TextViewWithGroup");
						  step.addContent(actiontype);

						  Element clicktype=new Element("clicktype");
						  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
						  step.addContent(clicktype);
						  
					  }else*/ if(Easyoperateitem.getChild("motherviewclass").getText().endsWith("ListView")){ //equals("android.widget.ListView") || Easyoperateitem.getChild("motherviewclass").getText().equals("com.android.internal.app.AlertController$RecycleListView")){
						  
						  Element actiontype=new Element("Actiontype");
						  actiontype.setText("ClickList");
						  step.addContent(actiontype);
						  
						  Element parameter=new Element("Parameter");
						  parameter.setText(Easyoperateitem.getChild("listindex").getText());
						  step.addContent(parameter);
						  
						  Element clicktype=new Element("clicktype");
						  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
						  step.addContent(clicktype);
						  
					  }else if(Easyoperateitem.getChild("motherviewclass").getText().equals("android.widget.Spinner")){
						  /////may do something in the future, if needed. Now it is in the unExe class
					  }else{// if(Easyoperateitem.getChild("motherviewclass").getText().equals("android.widget.LinearLayout") || Easyoperateitem.getChild("motherviewclass").getText().endsWith("TabView") || Easyoperateitem.getChild("motherviewclass").getText().endsWith("Layout") || Easyoperateitem.getChild("motherviewclass").getText().endsWith("CardView")){
						  
						  if(Easyoperateitem.getChild("viewtext")!=null){
							  Element text=new Element("clickText");
							  text.setText(Easyoperateitem.getChild("viewtext").getText());
							  step.addContent(text);
						  }
						  
						  if(Easyoperateitem.getChild("xposition")!=null){
							  
							  if(Integer.valueOf(Easyoperateitem.getChild("xposition").getText())>0 || Integer.valueOf(Easyoperateitem.getChild("yposition").getText())>0){
								  Element xpoisition=new Element("xposition");
								  Element ypoisition=new Element("yposition");
								  
								  xpoisition.setText(Easyoperateitem.getChild("xposition").getText());
								  ypoisition.setText(Easyoperateitem.getChild("yposition").getText());
								  step.addContent(xpoisition);
								  step.addContent(ypoisition);
							  }
						  }
						  
						  
						  
						  step.addContent(Easyoperateitem.getChild("androidid").clone());
						  
						  Element actiontype=new Element("Actiontype");
						  actiontype.setText("JustClick");
						  step.addContent(actiontype);

						  Element clicktype=new Element("clicktype");
						  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
						  step.addContent(clicktype);
						  
						  
					  }					  
				  }else{//this is for the Executeable actions part and a no group part.
					  /*
					  String ownid=Easyoperateitem.getChild("androidid").getChild("ownid").getText();
					  Element viewID=new Element("viewID");
					  viewID.setText(ownid);
					  step.addContent(viewID);
					  */
					  
					  step.addContent(Easyoperateitem.getChild("androidid").clone());
					  
					  Element actiontype=new Element("Actiontype");
					  actiontype.setText(Easyoperateitem.getChild("viewclass").getText());
					  //this actiontype may be like: com.android.internal.view.menu.ActionMenuItemView
					  //mat be: imageview textview or some else                                                                      
					  step.addContent(actiontype);	
					  
					  Element clicktype=new Element("clicktype");
					  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
					  step.addContent(clicktype);
					  
				  }
				  
				  if(pathItem.getAttribute("back")!=null){  /////back part
						Element stepBack=new Element("step");
						runRoot.addContent(stepBack);
						stepBack.setAttribute(new Attribute("id",String.valueOf(++runstep)));
						Element actiontype=new Element("Actiontype");
						actiontype.setText("back");
						stepBack.addContent(actiontype);
						
						///set outputcurrent
						  Element outputcurrentBack=new Element("outputcurrent");
						  outputcurrentBack.setText("yes");
						  stepBack.addContent(outputcurrentBack);
				  }
				  
			}
		
		return null;
	}
		// TODO Auto-generated method stub
		/*
		for(Element pathItem: pathToRun){
			int currentClassId=Integer.parseInt(pathItem.getAttributeValue("currentclassid"));
			
			
			if(pathItem.getAttribute("unExe")!=null){// this item has some unExecuteable actions
				Element step=new Element("step");
				runRoot.addContent(step);
				step.setAttribute(new Attribute("id",String.valueOf(++runstep)));
				
				String unExeID=pathItem.getAttributeValue("unExe");
				Element unExe=rootRecord.getChild("Match").getChildren().get(Integer.parseInt(unExeID));
				for(Element unExeItem: unExe.getChildren()){
					
					/////////////////
					///set outputcurrent
					  Element outputcurrent=new Element("outputcurrent");
					  outputcurrent.setText("no");
					///set currentclass
					  Element currentClass=new Element("currentClass");
					  currentClass.setText(String.valueOf(currentClassId));
					///set sub id
					  Element subIdele=new Element("subId");
					  subIdele.setText(unExeID);
					////
					  Element classItem=rootRecord.getChild("Classitem").getChildren().get(currentClassId);// this is in the Classitem
					  Element Easyoperate=classItem.getChild("Easyoperate");
					  List<Element> Easyoperatelist=Easyoperate.getChildren();
					  
					  ////////////////
					  Element Easyoperateitem=Easyoperatelist.get(Integer.valueOf(unExeID)-1);
					  if(Easyoperateitem.getChild("motherviewclass")!=null){//the motherviewclass means the class is a viewgroup
						  
						  if(Easyoperateitem.getChild("motherviewclass").equals("android.widget.Spinner")){/// in this version, if we meet the android.widget.Spinner, we do nothing as default.
							  Element actiontype=new Element("Nothing");
						  }/*else if(Easyoperateitem.getChild("motherviewclass").equals("android.widget.LinearLayout")){
							  //String childViewClass=Easyoperateitem.getChild("childviewclass").getText();
						  }*/
		/*
					  }else{//it is not a viewgroup
						  if(Easyoperateitem.getChild("viewclass").equals("android.widget.EditText")){
							  String ownid=Easyoperateitem.getChild("androidid").getChild("ownid").getText();
							  Element actiontype=new Element("Actiontype");
							  actiontype.setText("EditText");
							  
							  Element inputWhat=new Element("inputWhat");
							  /////at here we need to get input what////////
							  
						  }
					  }
					  
					  
					  
					  
					  
					  
					  
						  
						  Element actiontype=new Element("Actiontype");
						  actiontype.setText("ClickList");
						  
						  
						  
						  Element clicktype=new Element("clicktype");
						  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
						  
						  
						 // clicktype.setText("short");//here need to add long
						  
						  Element parameter=new Element("Parameter");
						  parameter.setText(Easyoperateitem.getChild("listindex").getText());		
						  
						  action.addContent(actiontype);
						  action.addContent(clicktype);
						  action.addContent(parameter);
						  
						  
						  
					  }else{
						  Element clicktype=new Element("clicktype");
						  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
						  
						  
						  Element actiontype=new Element("Actiontype");
						  Element parameter=new Element("Parameter");
						  
						  if(Easyoperateitem.getChild("imagebutton")!=null){
							  actiontype.setText("ClickImagebutton");
							  parameter.setText(Easyoperateitem.getChild("imagebutton").getText());	  
						  }else{
							  actiontype.setText("ClickText");
							  parameter.setText(Easyoperateitem.getChild("viewtext").getText());	  
						  }
						  
						   
						  
						  action.addContent(actiontype);
						  action.addContent(clicktype);
						  action.addContent(parameter);
						  
					  }
					  
					  
					  
					  
					  
				}
				
				
			}
			
			  
			///set outputcurrent
			  Element outputcurrent=new Element("outputcurrent");
			  outputcurrent.setText("yes");
			  ///set currentclass
			  Element graphclassfrom=new Element("graphclassfrom");
			  String before= pathItem.getParentElement().getAttributeValue("nextclassname");
			  graphclassfrom.setText(before);
			  ///set sub id
			  Element graphclassitem=new Element("graphclassitem");
			  String sub=pathItem.getAttributeValue("subid");
			  graphclassitem.setText(sub);
			  ///set action
			  Element action=new Element("action");
			  
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		//////////////////////old versions
		
		Element classitem=rootrecord.getChild("Classitem");
		List<Element> classitemlist=classitem.getChildren();
		


		///////////////////////////////////////////////
		List<Element> checklist=element.getChildren();	//the element is rootrecord.getChild("Path");	
		
		for(int i=0;i<checklist.size();i++){
			Element checklistitem=(Element)checklist.get(i);
			if(checklistitem.getAttributeValue("color").equals("black")){//color is black
				continue;
			}else{
				  
					  ////add run
					  Element step=new Element("step");
					  Root.addContent(step);
					  step.setAttribute(new Attribute("id",String.valueOf(++runstep)));
					  
					  
					  if(runstep==1){
						  System.out.println(runstep);
					  }
					  
					  
					  ///set outputcurrent
					  Element outputcurrent=new Element("outputcurrent");
					  outputcurrent.setText("yes");
					  ///set currentclass
					  Element graphclassfrom=new Element("graphclassfrom");
					  String before= checklistitem.getParentElement().getAttributeValue("nextclassname");
					  graphclassfrom.setText(before);
					  ///set sub id
					  Element graphclassitem=new Element("graphclassitem");
					  String sub=checklistitem.getAttributeValue("subid");
					  graphclassitem.setText(sub);
					  ///set action
					  Element action=new Element("action");
					  
					  String thisclassid=checklistitem.getParentElement().getAttributeValue("nextclassname");
					  //String subid=checklistitem.getAttributeValue("subid");
					  
					  
					  Element currentclass=classitemlist.get(Integer.valueOf(thisclassid)-1);
					  Element Easyoperate=currentclass.getChild("Easyoperate");
					  
					  List<Element> Easyoperatelist=Easyoperate.getChildren();
					  Element Easyoperateitem=Easyoperatelist.get(Integer.valueOf(sub)-1);
					  if(Easyoperateitem.getChild("motherviewclass")!=null){//the motherviewclass means the class is a viewgroup
						  Element actiontype=new Element("Actiontype");
						  actiontype.setText("ClickList");
						  
						  Element clicktype=new Element("clicktype");
						  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
						  
						  
						 // clicktype.setText("short");//here need to add long
						  
						  Element parameter=new Element("Parameter");
						  parameter.setText(Easyoperateitem.getChild("listindex").getText());		
						  
						  action.addContent(actiontype);
						  action.addContent(clicktype);
						  action.addContent(parameter);
						  
						  
						  
					  }else{
						  Element clicktype=new Element("clicktype");
						  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
						  
						  
						  Element actiontype=new Element("Actiontype");
						  Element parameter=new Element("Parameter");
						  
						  if(Easyoperateitem.getChild("imagebutton")!=null){
							  actiontype.setText("ClickImagebutton");
							  parameter.setText(Easyoperateitem.getChild("imagebutton").getText());	  
						  }else{
							  actiontype.setText("ClickText");
							  parameter.setText(Easyoperateitem.getChild("viewtext").getText());	  
						  }
						  
						   
						  
						  action.addContent(actiontype);
						  action.addContent(clicktype);
						  action.addContent(parameter);
						  
					  }
					  
			          step.addContent(outputcurrent);
			          step.addContent(graphclassfrom);
			          step.addContent(graphclassitem);
			          step.addContent(action);
					  
			      if(checklistitem.getChildren().size()!=0){
			    	  Element itemfound=generaterun(checklistitem, rootrecord, Root);
			    	  
			    	  
					  //Element itemfound=Checkcurrentpath(checklistitem);
					  if(itemfound!=null){//if itemfound!=null, means there is a white in below paths.
						   return itemfound;	
					  }
				  }else{
					  return checklistitem;  
				  }	
			}
		}
		
		
		return null;
*/



	private static void allBoundaryAddOne(Element element) {
		// TODO Auto-generated method stub
		
		if(element.getChildren().size()==0){
			//System.out.println("RunGo:"+element.getAttribute("RunGo").getValue());
			
			if(!element.getAttribute("RunGo").getValue().equals("-1")){
				element.getAttribute("RunGo").setValue("1");
			}
			
		}else{
			for(Element child:element.getChildren()){
				allBoundaryAddOne(child);
			}	
		}
	}


	private static void removeAlldelay(Element element) {
		// TODO Auto-generated method stub
		for(Element child:element.getChildren()){
			if(child.getAttribute("RunGodelay").getValue().equals("true")){
				child.getAttribute("RunGodelay").setValue("false");
			}
		}	
	}


	private static String addtoReportfile(Element rootresult,
			Element rootrecord, ArrayList<Matched> extendone, ArrayList<Matched> extendtwo) {
		// TODO Auto-generated method stub
		

		
		
		
		
		Element currentnode=findCurrentNodewithDelay(rootrecord.getChild("Path"));//in this method, currentPathList is changed
		//////first to find the currentPathList and current node
		if(currentnode==null){//maybe all nodes are at delay
			currentnode=findCurrentNode(rootrecord.getChild("Path"));//in this method, currentPathList is changed
			if(currentnode==null){
				System.out.println("error:the current node is null, it is unpossible at the beginning");
			}
		}
		
		/*
		if(currentnode==null){
			System.out.println("error:the current node is null, it is unpossible at the beginning");
		}
		*/
		//////check the result.xml and add it to the current node
		///rules: 1. if the record.xml has the same information on result.xml, the class ID should merge. And if the element of the reocrd.xml has similar information with last result, it sould be recorded.
		//        2. if the record.xml path has two same 3-step branch, one more path branch should be closed.
		//        3. if the before 6 events have same class to the final one(continue), the current event should be closed.
		//        4. if the path has the same class to its parent, the parent should be closed. It is because some of the buttons are unavailble. if the result have no any subpath. The parent should be closed.
		//        5. if a path has the 3 same loops of activies name, remove the last two and close the path.
        //        6. if a path has 10 events, it should be closed in the last event.
		
		
		int rootsize=rootresult.getChildren().size();
		Element finalresult=rootresult.getChildren().get(rootsize-1);
		
		/////////check
		Element classitem=rootrecord.getChild("Classitem");
		List<Element> checkClasslist=classitem.getChildren();//get all classitem

		ArrayList<Integer> currentClassIdList=new ArrayList<Integer>();//put the class id on the current path into this list
		for(Element currentPathElement: currentPathList){
			int currentClassId=Integer.valueOf(currentPathElement.getAttributeValue("currentclassid"));
		        currentClassIdList.add(currentClassId);
		    }
		
		//we need to check wheather the final result in the record
		int classID=0;//0 means the root of class, if this value is changed to other int, it means a match of record
						
		//for rule 1
		ArrayList<String> similarActivity=new ArrayList<String>();
		for(int m=0;m<checkClasslist.size();m++){//for every item in the class of record
			if(m!=0){
				//ArrayList<Integer> countForMatch=new ArrayList<Integer>();
				//countForMatch.add(0, 0);//Integer matchtextual=0;
				//countForMatch.add(1, 0);//unmatchtextual=0;
				int[] countForMatch={0,0};
				
				//int unmatchtextual=0;
				Element classitemElement=(Element)checkClasslist.get(m);//pick up one
				boolean checkequal=checkchildrenequal(classitemElement.getChild("Easyoperate"),finalresult.getChild("Easyoperate"),countForMatch);
				int matchtextual=countForMatch[0];
				int unmatchtextual=countForMatch[1];
				
				boolean classNameEq=classitemElement.getChild("Classname").getText().equals(finalresult.getChild("Classname").getText());
				
				
				if(checkequal && unmatchtextual==0){//if checkeqaul == true, it is matched it should be unmatchtextual!=0 and the same time.
					classID=Integer.valueOf(classitemElement.getAttributeValue("id"));//another class equal
					similarActivity.add(classitemElement.getAttributeValue("id"));//add itself to the similaractivity
				}else if(matchtextual!=0 && unmatchtextual/matchtextual<0.1 && classNameEq){//the unmatched precentage is lowwer than 0.1
					similarActivity.add(classitemElement.getAttributeValue("id"));
				}
			}
		}
		
		//for rule 3
		////first to collect all existing class name
		//boolean  rule3Trigger=false;
		
		HashMap<String,String> existClassMap=new HashMap<String,String>();//key is id, value is classname
		HashMap<String,Element> existActivityMap=new HashMap<String,Element>();//key is id, value is similar class id/for rule 5 and similarity updated
		
		for (Element classitemElement: checkClasslist){
			existClassMap.put(classitemElement.getAttribute("id").getValue(), classitemElement.getChild("Classname").getText());
			existActivityMap.put(classitemElement.getAttribute("id").getValue(), classitemElement);
		}
		
		String finalclassName=finalresult.getChild("Classname").getText();
		int countPathMatch=0;//if countPathMatch, we do not need to add it.
		int alleventPath=0;
		for(Element currentPathEle: currentPathList){
			String currentPathEleclassName=existClassMap.get(currentPathEle.getAttribute("currentclassid").getValue());
			if(currentPathEleclassName.equals(finalclassName)){
				//matchId=currentPathEle.getAttribute("currentclassid").getValue();//this is help the rule 5;				
				countPathMatch++;
				if(countPathMatch>=20){
					currentnode.getAttribute("RunGo").setValue("-1");//-1 means we need to close it
					return "rule3";
				}
			}else{
				countPathMatch=-100;//never trigger rule3 later.
			}			
			
			alleventPath++;
			if(alleventPath>=10){
				currentnode.getAttribute("RunGo").setValue("-1");//-1 means we need to close it
				return "rule6";
			}
		}
		//for rule 5//rule 5 is only for the match of the class name.

		ArrayList<DetectLoop> possibleList=new ArrayList<DetectLoop>();//store all possible loop
        int currentSize=currentPathList.size();
        //
        ArrayList<String> allArivalClassNameList=new ArrayList<String>();
        allArivalClassNameList.add(finalresult.getChild("Classname").getText());//using the class name to detect the loop.
        ArrayList<ArrayList<String>> allArivalSimilarList=new ArrayList<ArrayList<String>>();
		allArivalSimilarList.add(similarActivity);//using similarity to verify the similar activity.
        
        @SuppressWarnings("unchecked")
		DetectLoop detectLoopfirst=new DetectLoop((ArrayList<String>) allArivalClassNameList.clone(),0,(ArrayList<ArrayList<String>>) allArivalSimilarList.clone());
        possibleList.add(detectLoopfirst);
		        
		        
		        
        for(int i=currentSize-1; i>=0 ; i--){//from end to begin    //notice the currentPathList does not contain the classid="0", i should can be equal to 0 to get the first element of the list. The first one is not the root of the path.
        	
        	//String aa=currentPathList.get(0).getAttribute("currentclassid").getValue();
        	String eleId=currentPathList.get(i).getAttribute("currentclassid").getValue();
        	String eleClassName=existClassMap.get(eleId);
        	
        	
        	ArrayList<DetectLoop> removeList= new ArrayList<DetectLoop>();
        	for(DetectLoop eleLoop: possibleList){
        		int loopSize=eleLoop.getPossibleLoop().size();
        		int currentPoint=eleLoop.getCurrentPoint();
        		//update the currentPoint
        		if(currentPoint+1>loopSize-1){//yichu
        			currentPoint=0;
        		}else{
        			currentPoint++;
        		}
        		eleLoop.setCurrentPoint(currentPoint);
        		
        		//check match
        		if(!eleLoop.getPossibleLoop().get(currentPoint).equals(eleClassName)){//match 1, the classname should be same
        			removeList.add(eleLoop);
        		}else if(!eleLoop.getAllArivalSimilarList().get(currentPoint).contains(eleId)){
        			removeList.add(eleLoop);
        		}else{
        			if(currentPoint==loopSize-1){//return to the loop end
        				if(eleLoop.getLooptimes()+1!=3){//less than 3 loop
        					eleLoop.setLooptimes(eleLoop.getLooptimes()+1);//looptimes +1 if match
        				}else{//it is 3 loop
        					boolean subIdloop=checksubId(currentPathList,loopSize,i);//4.10.2018
        					if(!subIdloop){
        						removeList.add(eleLoop);
        					    continue;
        					}//add end
        					
        					
        					System.out.println("rule 5 bingo");
        					///rule 5 enable
        					currentPathList.get(i+2*loopSize-1).getAttribute("RunGo").setValue("-1");//-1 means we need to close it
        					currentPathList.get(i+2*loopSize-1).removeChildren("subpath");//remove all subpath
        					
        					Element surgeryOnElement=currentPathList.get(i+2*loopSize-1);
        					
        					//close at 3/11 to take a try
	        					Element newOmitMatch=new Element("Ele"+surgeryOnElement.getAttributeValue("currentclassid")+"Ele"+surgeryOnElement.getAttributeValue("subid"));
	        					rootrecord.getChild("OmitMatch").addContent(newOmitMatch);
	        					
        					return "rule5";
        				}
        			}
        		}
        	}
        	
        	//add a new possible loop as all arrival class name
        	allArivalClassNameList.add(eleClassName);
        	
        	//String[] aa=existSimilarClassMap.get(i).split(";");
        	//ArrayList<String> aa= new ArrayList<Sting>(existSimilarClassMap.get(i).split(";"));
        	
        	String[] usetoSplit=existActivityMap.get(eleId).getChild("similarActivities").getText().split(";");
        	ArrayList<String> StrArrtoArraylist=new ArrayList<String>();
        	for(String strele: usetoSplit){
        		StrArrtoArraylist.add(strele);
        	}
        	allArivalSimilarList.add(StrArrtoArraylist);
        	
        	@SuppressWarnings("unchecked")
			DetectLoop detectLoop=new DetectLoop((ArrayList<String>) allArivalClassNameList.clone(),allArivalClassNameList.size()-1,(ArrayList<ArrayList<String>>) allArivalSimilarList.clone());
        	
        	//ArrayList<String> aa=(ArrayList<String>) allArivalClassNameList.clone();
        	possibleList.removeAll(removeList);//remove all the list needed to be removed.
        	possibleList.add(detectLoop);
        }

		//for rule 4
        //////added by 1.15.2018
        String currentclassid=currentnode.getAttributeValue("currentclassid");
        String currentsubid=currentnode.getAttributeValue("subid");
        /*
        for(Element rule4Item:rootrecord.getChild("Rule4").getChildren()){
        	if(rule4Item.getName().equals("Ele"+currentclassid+"d"+currentsubid)){
				currentnode.getAttribute("RunGo").setValue("-1");
				return "rule4";
        	}
        }
        */
		//////add by 2017.12.5 
        if(rootresult.getAttribute("back").getValue().equals("false")){
        //if(!backOrNot){
			if(rootresult.getChildren().size()>=2){
				Element sencondLastResult=rootresult.getChildren().get(rootsize-2);
				if(checkchildrenALLequal(finalresult.getChild("Easyoperate"),sencondLastResult.getChild("Easyoperate")) || finalresult.getChild("Easyoperate").getChildren().size()==0){
					///it this kind of view should be recorded. added by 1.15.2018
					//Element rule4Ele=new Element("Ele"+currentclassid+"Ele"+currentsubid);
					//rootrecord.getChild("Rule4").addContent(rule4Ele);//try 4.20.2018
					//end added by 1.15.2018
					
					currentnode.getAttribute("RunGo").setValue("-1");
					return "rule4";
				}
			}
        }
		/////////////////////////////////add to the record
		addtoRecord(rootrecord,classitem,finalresult,currentnode,classID,similarActivity,existActivityMap);// in the function, are are classID==0(no current class match it case) and classID!=0 case
		
		//for rule 2
		/*bug code on rule 2, if time is enough, we need to fix it.
		
		*/// the whole rule define is in the addtoReportfile, we need to solve rule 2 at here.
        //	        2. if the record.xml path has two same 3-step branch, one more path branch should be closed.
		HashMap<String,Element> stepRecordMap=new HashMap<String,Element>();//record all node's child and gradchild info to hash, and node as Element
		ArrayList<RemoveStepdata> passList=new ArrayList<RemoveStepdata>();//to help to record the node's child and gradchild
		ArrayList<Element> resultList=new ArrayList<Element>();
		boolean resultforcheck=checkStepBranch(rootrecord.getChild("Path"),stepRecordMap,passList,resultList,10);// 4 means the generation level to check
		if(resultforcheck){
			removeStepbrach(rootrecord.getChild("Path"),resultList);
		}
	
		//////////////////////////////////////////
		rootrecord.getChild("OmitMatch");
		rootrecord.getChild("Rule4");
		
		//if there is a nlp match, we need to do something
		HashSet<String> existingRule4andOmitMatch=new HashSet<String>();//the below "if" can clear all the rungo, we will block it, if the rule4 and omitmatch is triggered.
	    /////influenced subId//////
	    HashSet<String> influSubIdSet=new HashSet<String>();
		if(classID!=0 || !similarActivity.isEmpty()){//rule 4 case and OmitMatch case. The class should be existed or similiaried.
			//similarActivity.add(String.valueOf(classID));
			//detect the existing rule4
		    for(Element rule4Item:rootrecord.getChild("Rule4").getChildren()){
		    	existingRule4andOmitMatch.add(rule4Item.getName());
		    }
		    for(Element OmitMatchItem:rootrecord.getChild("OmitMatch").getChildren()){
		    	existingRule4andOmitMatch.add(OmitMatchItem.getName());
		    }
		    
		    for(String matchedItem: existingRule4andOmitMatch){
		    	String[] splitResult=matchedItem.split("Ele");
		    	String existedClassId=splitResult[1];//the [0] is zero
		    	if(similarActivity.contains(existedClassId)){
		    		String influSubId=splitResult[2];
		    		influSubIdSet.add(influSubId);
		    	}
		    }
		    ///////////
		    ArrayList<Matched> removeListMatchOne=new ArrayList<Matched>();
			for(Matched matchone: extendone){
				if(influSubIdSet.contains(String.valueOf(matchone.getResultId()))){
					removeListMatchOne.add(matchone);
				}
				
				  //for(Element childElement: currentnode.getChildren()){//add 4.7.2018
				        Element childElement=currentnode.getChildren().get(0);
					  	boolean matched=checkMatchedinBeforePath(currentnode,String.valueOf(matchone.getResultId()),childElement.getAttribute("currentclassid").getValue(),existActivityMap);//added 3.31.2018
				        if(matched){
				        	removeListMatchOne.add(matchone);
				        }
				  //}
				
				
				
				
			}
			extendone.removeAll(removeListMatchOne);
		    ///
		    ArrayList<Matched> removeListMatchTwo=new ArrayList<Matched>();
			for(Matched matchtwo: extendtwo){
				if(influSubIdSet.contains(String.valueOf(matchtwo.getResultId()))){
					removeListMatchTwo.add(matchtwo);
				}
				
				  //for(Element childElement: currentnode.getChildren()){//add 4.7.2018
		                Element childElement=currentnode.getChildren().get(0);
					  	boolean matched=checkMatchedinBeforePath(currentnode,String.valueOf(matchtwo.getResultId()),childElement.getAttribute("currentclassid").getValue(),existActivityMap);//added 3.31.2018
				        if(matched){
				        	removeListMatchTwo.add(matchtwo);
				        }
				 // }
				
				
			}
			extendtwo.removeAll(removeListMatchTwo);
		    /////////
		}
		
		
		if(!currentnode.getChildren().isEmpty()){// the rule 2 may do something
		
			boolean matchedBoolean=false;//close by 3.4.2018, for do not give proity for the edittext.
			/*
			for(Matched itemMathed: extendone){
				if(!itemMathed.getViewClass().equals("android.widget.EditText")){
					matchedBoolean=true;
					break;
				}
			}
			for(Matched itemMathed: extendtwo){
				if(!itemMathed.getViewClass().equals("android.widget.EditText")){
					matchedBoolean=true;
					break;
				}
			}
			*/
			
			if(!extendone.isEmpty() || !extendtwo.isEmpty()){//we need to set all path rungo=0 and delay=false, and only restore the match one
			//if(matchedBoolean){//we need to set all path rungo=0 and delay=false, and only restore the match one
				clearallpath(rootrecord.getChild("Path"));//++++++++++++++this clear all of the rungo except the already matched.
			}// if not good, we all remove this process later
			
			addRungoforMatch(currentnode,extendone,extendtwo,influSubIdSet,existActivityMap);//for rungo
			
			addMatchInformCurrentNode(finalresult,currentnode,rootrecord,extendone,extendtwo);//for edit view
		}
		
		return "normal";
		
		
		
			/*
			if(currentClassIdList.contains(m)){
				
				
				//pick up the index m in the record class
				
				
			}
*/
		
		/*
		//for rule 5//rule 5 is only for the match of the class name.

		ArrayList<DetectLoop> possibleList=new ArrayList<DetectLoop>();
        int currentSize=currentPathList.size();
        //
        ArrayList<String> allArivalClassNameList=new ArrayList<String>();
        allArivalClassNameList.add(finalresult.getChild("Classname").getText());//using the class name to detect the loop
        @SuppressWarnings("unchecked")
		DetectLoop detectLoopfirst=new DetectLoop((ArrayList<String>) allArivalClassNameList.clone(),0);
        possibleList.add(detectLoopfirst);
        
        
        
        for(int i=currentSize-1; i>0 ; i--){//from end to begin
        	String eleId=currentPathList.get(i).getAttribute("currentclassid").getValue();
        	String eleClassName=existClassMap.get(eleId);
        	
        	ArrayList<DetectLoop> removeList= new ArrayList<DetectLoop>();
        	for(DetectLoop eleLoop: possibleList){
        		int loopSize=eleLoop.getPossibleLoop().size();
        		int currentPoint=eleLoop.getCurrentPoint();
        		//update the currentPoint
        		if(currentPoint+1>loopSize-1){//yichu
        			currentPoint=0;
        		}else{
        			currentPoint++;
        		}
        		eleLoop.setCurrentPoint(currentPoint);
        		
        		//check match
        		if(!eleLoop.getPossibleLoop().get(currentPoint).equals(eleClassName)){//match 1, the classname should be same
        			removeList.add(eleLoop);
        		}else{
        			if(currentPoint==loopSize-1){//return to the loop end
        				if(eleLoop.getLooptimes()+1!=3){//less than 3 loop
        					eleLoop.setLooptimes(eleLoop.getLooptimes()+1);//looptimes +1 if match
        				}else{//it is 3 loop
        					System.out.println("rule 5 bingo");
        					///rule 5 enable
        					currentPathList.get(i+loopSize-1).getAttribute("RunGo").setValue("-1");//-1 means we need to close it
        					currentPathList.get(i+loopSize-1).removeChildren("subpath");//remove all subpath
        					return "rule5";
        				}
        			}
        		}
        	}
        	
        	//add a new possible loop as all arrival class name
        	allArivalClassNameList.add(eleClassName);
        	@SuppressWarnings("unchecked")
			DetectLoop detectLoop=new DetectLoop((ArrayList<String>) allArivalClassNameList.clone(),allArivalClassNameList.size()-1);
        	
        	//ArrayList<String> aa=(ArrayList<String>) allArivalClassNameList.clone();
        	possibleList.removeAll(removeList);//remove all the list needed to be removed.
        	possibleList.add(detectLoop);
        }
            */
		}
			

	

	private static boolean checksubId(ArrayList<Element> currentPathList2,
			int loopSize, int j) {
		// TODO Auto-generated method stub
		/*
		for(int i=currentPathList2.size()-1; i>=0 ; i--){
			currentPathList2
			
			
			
		}
		*/
		
		ArrayList<String> subidList=new ArrayList<String>(); 
		for(int i=j; i<j+loopSize; i++){
			subidList.add(currentPathList2.get(i).getAttributeValue("subid"));
		}
		
		for(int i=j; i<currentPathList2.size(); i++){
			int yushu=(i-j)%loopSize;
			
			if(!currentPathList2.get(i).getAttributeValue("subid").equals(subidList.get(yushu))){
				return false;
			}
		}
		
		return true;
	}

	private static void addMatchInformCurrentNode(Element finalresult, Element currentnode, Element rootrecord, ArrayList<Matched> extendone, ArrayList<Matched> extendtwo) {
		HashMap<String,String> specificSymbolTrans=new HashMap<String,String>(); 
		///add another specificSymbol dict
		specificSymbolTrans.put("apostrophe","'");
		specificSymbolTrans.put("comma",",");
		specificSymbolTrans.put("colon",":");
		specificSymbolTrans.put("semicolon",";");
		specificSymbolTrans.put("hyphen","-");
		specificSymbolTrans.put("parentheses","()");
		specificSymbolTrans.put("quote","\"");
		specificSymbolTrans.put("space","realspace");
		
		
		
		//firstly, we pick up all unexecutable branch in the finalresult.
		//All nlp match of unexecutable branch should be repeated by nlp branch
		//rootrecord.getChild(cname);
		
		///first to check preious match// this part can be done if time is enough
		
		///then give a number to current unExeInfo
		//child.getAttribute("NlpMh").getValue().equals("false")
		Boolean multiType=false;//rule 105
		for(int i=0; i<2; i++){
		
			Element unExeInfoEle=new Element("unExeInfo");//if there is a unExecuable element in the finalresult, this element will have a child
	
			
			//////////////////set unexeinfo on all children of current node///////////
			Element finalEasy=finalresult.getChild("Easyoperate");
			for(Element finalEle: finalEasy.getChildren()){//this is for every final result item
				   String viewclassName=null;
				   if(finalEle.getChild("viewclass")!=null){
					   viewclassName=finalEle.getChild("viewclass").getText();
				   }else{
					   viewclassName=finalEle.getChild("childviewclass").getText();
				   }
				
				   //if(unexecuteClassName.contains(viewclassName)){// if the viewclassName is in unexecute(typeview, checkbox), we need to write //code 1012341
				   if(viewclassName.equals("android.widget.EditText")){
				       Element subInfoId=new Element("subInfoId");
					   unExeInfoEle.addContent(subInfoId);
					   
					   Element viewClassEle=new Element("viewClassName");
					   subInfoId.addContent(viewClassEle);
					   viewClassEle.setText(viewclassName);
					   
					   Element matchRoNot=new Element("matchRoNot");
					   subInfoId.addContent(matchRoNot);
					   
					   Element matchExtend=new Element("matchExtend");
					   subInfoId.addContent(matchExtend);
					   
					   Element pageSubId=new Element("pageSubId");// this is the view id.
					   subInfoId.addContent(pageSubId);
					   pageSubId.setText(finalEle.getAttributeValue("id"));
					   
					   // in the below function, information is added on record
					   //first we do the extendtwo
					   ArrayList<Boolean> canShu= new ArrayList<Boolean>();
					   canShu.add(multiType);
					   boolean twoMatched=addUnexecuteMatchToRecord(extendtwo,finalEle,viewclassName,subInfoId,specificSymbolTrans,canShu);
					   boolean oneMatched=false;
					   if(!twoMatched){
						   //second we do the extendone, if the resultId is done by extendtwo, we omit it.
						   oneMatched=addUnexecuteMatchToRecord(extendone,finalEle,viewclassName,subInfoId,specificSymbolTrans,canShu);
					   }
					   
					   multiType=canShu.get(0);
					   if(twoMatched){
						   matchExtend.setText("1");//orignal is 2
					   }else if(oneMatched){
						   matchExtend.setText("1");
					   }
					   
					   if(!twoMatched && !oneMatched){//it means there is no match of the nlp part.
	                	   //we need to check the viewtext is null or not.
	                	   //if the viewtext has something, I suggest to use the default value.
						   /*
				     		  Element nlpMatched=new Element("nlpMatched");
				     		  subInfoId.addContent(nlpMatched);
				     		  nlpMatched.setText("no");
						   */
	 					   ////////////there is no any matched, we will random give a word/////
						   
						   if(viewclassName.equals("android.widget.EditText")){// this view is a EditText or not
								   String editType=finalEle.getChildText("edittype");//"digit" or "string"
								   if(editType.equals("digit")){
									   if(!digitTypeWhatList104.isEmpty()){
										   Element typeWhat=new Element("typeWhat");
			         					   String theType=digitTypeWhatList104.get(0);
				      					   typeWhat.setText(theType);//rule 104
				      					   subInfoId.addContent(typeWhat);
									   }else{	   
					   					   Element typeWhat=new Element("typeWhat");
					 					   typeWhat.setText("11");// this "11" is the random digit I choose
					 					   subInfoId.addContent(typeWhat);
									   }
								   }else{
							     			String viewtext=finalEle.getChildText("viewtext");//default text
							     			//if(viewtext.isEmpty() || !typeWhatList104.isEmpty()){//|| !typeWhatList104.isEmpty() 2.22.2018
							     			//if(viewtext.isEmpty()){
							     			String fousable=finalEle.getChildText("focusable");
							     			if(fousable.equals("true")){
							     			
							     			  if(!typeWhatList104.isEmpty()){
							     				  
					         					   Element typeWhat=new Element("typeWhat");
					         					   
					         					   @SuppressWarnings("unchecked")
												   ArrayList<String> type104clone=(ArrayList<String>) typeWhatList104.clone();
					         					   type104clone.retainAll(specificSymbolTrans.keySet());
					         					   String theType=typeWhatList104.get(0);
					         					   if(!type104clone.isEmpty()){
					         						  theType=type104clone.get(0);
					         					   }
					         					   
					         					   
					         					   if(specificSymbolTrans.containsKey(theType)){//rule105 added 2.20/2018
					         						   if(multiType==false){
						      						      theType=specificSymbolTrans.get(theType);
						      						      multiType=true;
					         						   }else{
					         							  theType=specificSymbolTrans.get(theType)+"abcd";
					         							  multiType=false;
					         						   }
					         					   }
					         					   				         					   
						      					   typeWhat.setText(theType);//rule 104
						      					   subInfoId.addContent(typeWhat);
							     			  }else{	
					         					   Element typeWhat=new Element("typeWhat");
						      					   typeWhat.setText("abcdefg");
						      					   subInfoId.addContent(typeWhat);
							     			  }
							     			}else if(fousable.equals("false")){
					         					   Element typeWhat=new Element("typeWhat");
						      					   typeWhat.setText("default");
						      					   subInfoId.addContent(typeWhat);
							     			}
							     			//}else{
				         					//   Element typeWhat=new Element("typeWhat");
					      					//   typeWhat.setText("default");
					      					//   subInfoId.addContent(typeWhat);
							     			//}
							     			 
								   }
						   }else{
	   					       Element actionWhat=new Element("actionWhat");
	      					   actionWhat.setText("default");
	      					   subInfoId.addContent(actionWhat);
			     		 }
						   
						   
				     	   matchRoNot.setText("no");
					   }else{
						   matchRoNot.setText("yes");
					   }
						   
					   
					   /*  
	                   if(!matchNlp){
	                	   //we need to check the viewtext is null or not.
	                	   //if the viewtext has something, I suggest to use the default value.
	                   }
	                   */
	                   
				   }
			}
			
			boolean matched=false;
			int IDnumber=0;
			for(Element exitedElement: rootrecord.getChild("Match").getChildren()){
				matched=checkchildrenALLequal(exitedElement,unExeInfoEle);
				if(matched){//check wheather there is a existing matched.
					IDnumber=Integer.parseInt(exitedElement.getAttribute("ID").getValue());
					break;
				}
			}
			
			if(!matched){//no matched case
				
				IDnumber=rootrecord.getChild("Match").getChildren().size();
				
				unExeInfoEle.setAttribute("ID", String.valueOf(IDnumber));
				
				rootrecord.getChild("Match").addContent(unExeInfoEle);
				
				
				/////////////added 4.12
				
						for(Element item:unExeInfoEle.getChildren()){
							Element typewhat=item.getChild("typeWhat");//added 4.12
							if(typewhat!=null){
								String what=typewhat.getText();
								if (!typedSet.contains(what)){
									Element newtype=new Element("ID");
									newtype.setText(what);
									rootrecord.getChild("Typed").addContent(newtype);
								}
							}
						}
				////////////add end
			}
			
			if(i==0){
				for(Element currentChild :currentnode.getChildren()){
					currentChild.setAttribute("unExe",String.valueOf(IDnumber));
				}
			}else{//rule 105
				ArrayList<Element> needAddedElement=new ArrayList<Element>();
				for(Element currentChild :currentnode.getChildren()){
					
					Element newcurrentChild=currentChild.clone();
					newcurrentChild.setAttribute("unExe",String.valueOf(IDnumber));
					
					
					needAddedElement.add(newcurrentChild);
				}
				for(Element needAdd:needAddedElement){
					currentnode.addContent(needAdd);
				}
			}
			
			if(multiType==false){
				break;//rule 105
			}
		}
		//unexecuteClassName.contains(extendEle.getViewClass())
		
		
		
		
		
		// TODO Auto-generated method stub
		
	}


	private static boolean addUnexecuteMatchToRecord(ArrayList<Matched> extendtwo, Element finalEle, String viewclassName, Element subInfoId, HashMap<String, String> specificSymbolTrans, ArrayList<Boolean> canShu) {
		// TODO Auto-generated method stub
        //ArrayList<String> matchedIndexList=new ArrayList<String>();//matchedResultIndexList  //the index is represented by string
		
		boolean Matched=false;
		for(Matched matchItem:extendtwo){                	   
     	   String resultId=String.valueOf(matchItem.getResultId());
			     	   if(resultId.equals(finalEle.getAttributeValue("id"))){// the extendtwo has nlp match as resultId recorded in it is equal to the resultID we picked up from finalresult
			     		   ///////////////
			     		  Matched=true;
			     		  /*
			     		  Element nlpMatched=new Element("nlpMatched");
			     		  subInfoId.addContent(nlpMatched);
			     		  nlpMatched.setText("yes");
			     		  */
			     		  
			     		   if(viewclassName.equals("android.widget.EditText")){// this view is a EditText or not
			     			   String editType=finalEle.getChildText("edittype");//"digit" or "string"
			     			   String viewtext=finalEle.getChildText("viewtext");//default text
			     			   //if(finalEle.getChild("androidid").getChildText("ownid").equals("2131361814")){
			     				//   System.out.println("bingo");
			     			   //}
			     			   
                                   ///////////step part for digit///
			     			   
		         				   Step nlpStep=matchItem.getStep();
		
		         				   if(editType.equals("digit")){
		         					   
		         					   
		         					  if(matchItem.isSteporSent()){//step
			         					   if(!nlpStep.getDigittypewhat().isEmpty()){
			         					   
				         					   ArrayList<String> digitList=nlpStep.getDigittypewhat().get(0);//this get(0) may be modified later
				         					   String digitFill=digitList.get(0);// this get(0) may be modified later. It is because there are lots of possible digit. We need to transfrom it for many branch later.
				         					   
				         					   Element typeWhat=new Element("typeWhat");
				         					   typeWhat.setText(digitFill);
				         					   subInfoId.addContent(typeWhat);
				         					   return true;
			         					   }else{
			         						   for(ArrayList<String> whatList: nlpStep.getTypewhat()){//in the typewhat, it may also have a digit
			         							  
			         							  String typeFinal=null;
			         							  for(String what: whatList){//2018.2.21
			         								 typeFinal=typeFinal+what;
			         							  }
			         							  
			         							  Element typeWhat=new Element("typeWhat");
	    			         					  typeWhat.setText(typeFinal);
	    			         					  subInfoId.addContent(typeWhat);
			         							   /*
			         							   for(String what: whatList){
			         								   if(what.matches("-?\\d+(\\.\\d+)?")){
			    			         					   Element typeWhat=new Element("typeWhat");
			    			         					   typeWhat.setText(what);
			    			         					   subInfoId.addContent(typeWhat);
			    			         					   return true;
			         								   }
			         										   
			         							   }*/
			         						   }
			         					   }
		         				     }
		         					   ///////////////sent part for digit
		         					   Sents nlpSent=matchItem.getSent();
		         					   ArrayList<String> wordSentList=nlpSent.getSentswords();
	             					   int currentIndex=matchItem.getWordIndex();
	             					   /// we need to find the nearest digit.// but too complex, I just simple the algorithm but not good.
	             					   // that is to compare the current index of the word and whole size. Then we can decide to interate the list from which end
	             					   if(currentIndex>wordSentList.size()/2){
	                 					   for(int i=0; i<wordSentList.size(); i++){
	                 						  if(wordSentList.get(i).matches("-?\\d+(\\.\\d+)?")){// it is a digit
	                        					   Element typeWhat=new Element("typeWhat");
		                     					   typeWhat.setText(wordSentList.get(i));
		                     					   subInfoId.addContent(typeWhat);
		                     					   return true;
	                 						  }
	                 					   }
	             					   }else{
	             						   for (int i = wordSentList.size() - 1; i >= 0; i--) {
	                  						  if(wordSentList.get(i).matches("-?\\d+(\\.\\d+)?")){// it is a digit
	                           					   Element typeWhat=new Element("typeWhat");
	                         					   typeWhat.setText(wordSentList.get(i));
	                         					   subInfoId.addContent(typeWhat);
	                         					   return true;
	                  						  }
	             						   }
	             					   }
	             					   
	             					   if(subInfoId.getChild("typeWhat")==null){
	             						  Element typeWhat=new Element("typeWhat");
	             						  
	             						   
	             						   if(!digitTypeWhatList104.isEmpty()){
	             							   typeWhat.setText(digitTypeWhatList104.get(0));
	             						   }else{
	             							   typeWhat.setText(String.valueOf(11));
	             						   }
                    					   subInfoId.addContent(typeWhat);
	             					   }
	             					   
                 					   return true; 
                 					   
                 					   
                 					   
		         				   }else{//digit end// string begin	   
		         					   
		         					   ////begin with the step part
		         					  if(matchItem.isSteporSent()){
			         					   if(!nlpStep.getTypewhat().isEmpty()){
			         						   
		         						   
				         					   ArrayList<String> stringList=nlpStep.getTypewhat().get(0);//here is modified by 4.1.2018
				         					   String stringFill=stringList.get(0);

				         					  for (ArrayList<String> strList: nlpStep.getTypewhat()){
				         						  for(String strItem: strList){
				         							  if(specificSymbolTrans.containsKey(strItem)){
				         								 stringFill=strItem;
				         							  }  
				         						  }
				         					  }
				         					   
				         					   
				         					   //rule 105
				         					   if(specificSymbolTrans.containsKey(stringFill)){//rule105 added 2.20/2018
				         						   if(canShu.get(0)==false){
				         							  stringFill=specificSymbolTrans.get(stringFill);
					      						      canShu.set(0, true);
				         						   }else{
				         							  stringFill=specificSymbolTrans.get(stringFill)+"abcd";
				         							  canShu.set(0, false);
				         						   }
				         					   }
				         					   
				         					   Element typeWhat=new Element("typeWhat");
				         					   typeWhat.setText(stringFill);
				         					   
				         					   subInfoId.addContent(typeWhat);
			         					       return true;
			         					   }
		         					  }
		         					   
		         					  
		         					   /////if step has nothing to match, do random
	            					   
						     			//viewtext=finalEle.getChildText("viewtext");//default text
						     			if(viewtext.isEmpty()){
						     				
						     			   if(!typeWhatList104.isEmpty()){//|| !typeWhatList104.isEmpty() 3.1.2018
						     				  Element typeWhat=new Element("typeWhat");
						     				  
				         					   @SuppressWarnings("unchecked")
											   ArrayList<String> type104clone=(ArrayList<String>) typeWhatList104.clone();
				         					   type104clone.retainAll(specificSymbolTrans.keySet());
				         					   String theType=typeWhatList104.get(0);
				         					   if(!type104clone.isEmpty()){
				         						  theType=type104clone.get(0);
				         					   }
						     				  
					      					   typeWhat.setText(theType);
					      					   subInfoId.addContent(typeWhat);
						     				   
						     			   }else{
				         					   Element typeWhat=new Element("typeWhat");
					      					   typeWhat.setText("abcdefg");
					      					   subInfoId.addContent(typeWhat);
						     			   }

			
						     			}else{
			         					   Element typeWhat=new Element("typeWhat");
				      					   typeWhat.setText("default");
				      					   subInfoId.addContent(typeWhat);
						     			}
						     			/*
						     		   Element typeWhat=new Element("typeWhat");
	            					   typeWhat.setText("abcdefg");// this is the random part
		         					   subInfoId.addContent(typeWhat);
		         					   */
		         					   return true;
		         					   
		         					   
		         					   
		         				   }
			     			   
			     			   
			     			   /*
			     			   if(matchItem.isSteporSent()){//step
			         				   Step nlpStep=matchItem.getStep();
			
			         				   if(editType.equals("digit")){
			         					   
			         					   
			         					   
			         					   ArrayList<String> digitList=nlpStep.getDigittypewhat().get(0);//this get(0) may be modified later
			         					   String digitFill=digitList.get(0);// this get(0) may be modified later. It is because there are lots of possible digit. We need to transfrom it for many branch later.
			         					   
			         					   Element typeWhat=new Element("typeWhat");
			         					   typeWhat.setText(digitFill);
			         					   subInfoId.addContent(typeWhat);
			         					   
			         				   }else{//"string"
			         					   
			         					   
			         					   
			         					   ArrayList<String> stringList=nlpStep.getTypewhat().get(0);
			         					   String stringFill=stringList.get(0);
			         					   
			         					   Element typeWhat=new Element("typeWhat");
			         					   typeWhat.setText(stringFill);
			         					   subInfoId.addContent(typeWhat);
			         				   }
			     				   
			     			   }else{//sent
			     				   Sents nlpSent=matchItem.getSent();
			     				   ArrayList<String> wordSentList=nlpSent.getSentswords();
			     				   
			         				   if(editType.equals("digit")){//the fill is a "digit"
			             					   int currentIndex=matchItem.getWordIndex();
			             					   /// we need to find the nearest digit.// but too complex, I just simple the algorithm but not good.
			             					   // that is to compare the current index of the word and whole size. Then we can decide to interate the list from which end
			             					   if(currentIndex>wordSentList.size()/2){
			                 					   for(int i=0; i<wordSentList.size(); i++){
			                 						  if(wordSentList.get(i).matches("-?\\d+(\\.\\d+)?")){// it is a digit
			                        					   Element typeWhat=new Element("typeWhat");
			                     					   typeWhat.setText(wordSentList.get(i));
			                     					   subInfoId.addContent(typeWhat);
			                 						  }
			                 					   }
			             					   }else{
			             						   for (int i = wordSentList.size() - 1; i >= 0; i--) {
			                  						  if(wordSentList.get(i).matches("-?\\d+(\\.\\d+)?")){// it is a digit
			                           					   Element typeWhat=new Element("typeWhat");
			                         					   typeWhat.setText(wordSentList.get(i));
			                         					   subInfoId.addContent(typeWhat);
			                  						  }
			             						   }
			             					   }
			         					   
			         				   }else{// the fill is a "string"
			            					   Element typeWhat=new Element("typeWhat");
			         					   typeWhat.setText("abcdefg");
			         					   subInfoId.addContent(typeWhat);
			         				   }
			     				      
			     			   }
			     			      */
			     		   }else{
			     			   ////someother not editview.
			     			   // we do not have such now.
			     		   }
			     		     
			     	   }
			     	   
        }
		
		
		return Matched;// this is unreached, I just leave it at here.
	}


	private static void addRungoforMatch(Element currentnode,
			ArrayList<Matched> extendone, ArrayList<Matched> extendtwo, HashSet<String> influSubIdSet, HashMap<String, Element> existActivityMap) {
		// TODO Auto-generated method stub
		//currentnode.getChildren();
		
		/*
		   if(pathlistitem.getChild("viewclass")!=null){
			   className=pathlistitem.getChild("viewclass").getText();
		   }else{
			   className=pathlistitem.getChild("childviewclass").getText();
		   }
		   
		   if(unexecuteClassName.contains(className)){//the edittext and checkbox can not executed.
			   continue;
		   }		
		*/
		
		HashMap<String, Element> sortHaspRule10=new HashMap<String, Element>();//for the rule 10
		HashMap<Element, Element> sortHaspRule10back=new HashMap<Element, Element>();//for the rule 10
		HashSet<Element> repeatEle=new HashSet<Element>();//for back
		
		
		boolean checkbackresult=checkBeforeBack(currentnode);
		
		if(!extendone.isEmpty()){
			  for(Matched extendEle: extendone){
				  //if(unexecuteClassName.contains(extendEle.getViewClass())){//we only consider the texteditview in this version, identify code 1012341.
				  if(extendEle.getViewClass().equals("android.widget.EditText")){
					  for(Element child: currentnode.getChildren()){
						  if(influSubIdSet.contains(child.getAttributeValue("subid"))){//this is for the rule4 and omitmatch case
							  continue;
						  }
						  
						  child.getAttribute("RunGo").setValue("1");//close by 3.4.2018, for do not give proity for the edittext.
						  child.getAttribute("NlpMh").setValue("true");//close by 3.4.2018
					  }
				  }else{
					  //Element effectedElement=currentnode.getChildren();//.get(extendEle.getResultId());
					  
					  //boolean longorNot=extendEle.isLongornot();
					  
					  for(Element childElement: currentnode.getChildren()){
						  if(childElement.getAttributeValue("subid").equals(String.valueOf(extendEle.getResultId()))){
							  /*
							  boolean matched=checkMatchedinBeforePath(currentnode,String.valueOf(extendEle.getResultId()),childElement.getAttribute("currentclassid").getValue(),existActivityMap);//added 3.31.2018
							  
							  if(matched){
								  continue;
							  }
							  */
							  childElement.getAttribute("RunGo").setValue("1");
							  childElement.getAttribute("NlpMh").setValue("true");  
						      //rule 10
							  String sentenceId=String.valueOf(extendEle.getSentId());
							  String wordIndex=String.valueOf(extendEle.getWordIndex());
							  sortHaspRule10.put(sentenceId+";"+wordIndex,childElement);  
							  
							  if(!repeatEle.contains(childElement) && backOrNot){//for back
								  
								  if(!checkbackresult){
									  repeatEle.add(childElement);
									  Element childclone=childElement.clone();
									  //Attribute backAttr=new Attribute("back");
									  childclone.setAttribute(new Attribute("back","true"));
									  sortHaspRule10back.put(childElement, childclone);
								  }
							  }
						  }
					  }
					  
					  
					 /// effectedElement.getAttribute("RunGo").setValue("1");
				  }  
			  } 
		}		
		
		if(!extendtwo.isEmpty()){
		  for(Matched extendEle: extendtwo){
			  //if(unexecuteClassName.contains(extendEle.getViewClass())){
			  if(extendEle.getViewClass().equals("android.widget.EditText")){
				  for(Element child: currentnode.getChildren()){
					  if(influSubIdSet.contains(child.getAttributeValue("subid"))){//this is for the rule4 and omitmatch case
						  continue;
					  }
					  child.getAttribute("RunGo").setValue("1");//orignal is 2
					  child.getAttribute("NlpMh").setValue("true");
				  }
			  }else{
				  
				  //boolean longorNot=extendEle.isLongornot();
				  
				  for(Element childElement: currentnode.getChildren()){
					  if(childElement.getAttributeValue("subid").equals(String.valueOf(extendEle.getResultId()))){
						  /*
						  boolean matched=checkMatchedinBeforePath(currentnode,String.valueOf(extendEle.getResultId()),childElement.getAttribute("currentclassid").getValue(),existActivityMap);//added 3.31.2018
						  if(matched){
							  continue;
						  }
						  */
						  childElement.getAttribute("RunGo").setValue("1");
						  childElement.getAttribute("NlpMh").setValue("true");
					      //rule 10
						  String sentenceId=String.valueOf(extendEle.getSentId());
						  String wordIndex=String.valueOf(extendEle.getWordIndex());
						  sortHaspRule10.put(sentenceId+";"+wordIndex,childElement);
						  
						  if(!repeatEle.contains(childElement) && backOrNot){//for back
							  if(!checkbackresult){
								  repeatEle.add(childElement);
								  Element childclone=childElement.clone();
								  //Attribute backAttr=new Attribute("back");
								  childclone.setAttribute(new Attribute("back","true"));
								  sortHaspRule10back.put(childElement, childclone);
							  }
						  }
					  }//orignal is 2
					  
				  }
			  }  
		  } 
		}
		
		///rule 10
		ArrayList<String> sortList=new ArrayList<String>(sortHaspRule10.keySet());
		Collections.sort(sortList, new SortRules());
		
		ArrayList<Element> elementList=new  ArrayList<Element>();//sorted element list
		for(String headStr: sortList){
			Element ele=sortHaspRule10.get(headStr);
			if(elementList.contains(ele)){
				continue;
			}else{
				elementList.add(ele);
			}
		}
		

		for(Element ele: elementList){
			currentnode.removeContent(ele);
		}
		
		Collections.reverse(elementList);
		
		for(Element ele: elementList){
			currentnode.addContent(0,ele);
			if(backOrNot && !checkbackresult){
				
				currentnode.addContent(0,sortHaspRule10back.get(ele));//for the back
				
			}
		}
		
		
		//System.out.println("asd");
		
		
		/*
		if(!extendtwo.isEmpty()){
			for(Element child: currentnode.getChildren()){
				child.getAttribute("RunGo").setValue("2");
			}
		}else if(!extendone.isEmpty()){
			for(Element child: currentnode.getChildren()){
				child.getAttribute("RunGo").setValue("1");
			}
		}
		*/
		
		/*
		////add all element matched the extendone RunGO=1
		for(Matched matched: extendone){
			int resultId=matched.getResultId();
			Element matchedElement=currentnode.getChildren().get(resultId-1);
			matchedElement.getAttribute("RunGo").setValue("1");
		}
		
	////add all element matched the extendtwo RunGO=2
			for(Matched matched: extendtwo){
				int resultId=matched.getResultId();
				Element matchedElement=currentnode.getChildren().get(resultId-1);
				matchedElement.getAttribute("RunGo").setValue("2");
			}
		*/
	}



	//path.setAttribute(new Attribute("NlpMh","false"));

	private static boolean checkMatchedinBeforePath(Element currentnode,
			String thissubId, String thisclassId, HashMap<String, Element> existActivityMap) {
		// TODO Auto-generated method stub
		
		//if(!currentnode.getParentElement().getName().equals("Path") && !currentnode.getName().equals("Path")){
		if(!currentnode.getName().equals("Path")){
			String currentclassid=currentnode.getAttribute("currentclassid").getValue();
			String subid=currentnode.getAttribute("subid").getValue();
			
			
			if(thissubId.equals(subid)){
				if(thisclassId.equals(currentclassid) && currentnode.getAttribute("NlpMh").getValue().equals("true")){
					return true;
				}
					
				if(existActivityMap.containsKey(currentclassid)){
					//existActivityMap.get(currentclassid).getChild("similarActivities").getText().split(";");
					
					
					
		        	String[] usetoSplit=existActivityMap.get(currentclassid).getChild("similarActivities").getText().split(";");
		        	for(String strele: usetoSplit){
		        		if(strele.equals(thisclassId)){
		        			return true;
		        		}
		        	}
				}
			}
		
		//if(!currentnode.getParentElement().getName().equals("Path")){
		    boolean result=checkMatchedinBeforePath(currentnode.getParentElement(), thissubId, thisclassId, existActivityMap);
		    if(result){
		    	return true;
		    }
		}
		
		
		return false;
	}


	private static boolean checkBeforeBack(Element currentnode) {
		// TODO Auto-generated method stub
		if(currentnode.getAttribute("back")!=null){
			return true;
		}else{
			if(!currentnode.getName().equals("Path")){
				boolean result=checkBeforeBack(currentnode.getParentElement());
				if(result){
					return true;
				}
			}
		}		
		return false;
	}


	private static void clearallpath(Element element) {
		// TODO Auto-generated method stub
		for (Element child: element.getChildren()){
			if(child.getAttribute("NlpMh").getValue().equals("false")){
				if(!child.getAttribute("RunGo").getValue().equals("-1")){
					child.getAttribute("RunGo").setValue("0");
					child.getAttribute("RunGodelay").setValue("false");
				}
			}
			clearallpath(child);
		}
	}


	private static boolean removeStepbrach(Element element,
			ArrayList<Element> resultList) {
		// TODO Auto-generated method stub
		for(Element subElement: element.getChildren()){
			if(resultList.contains(subElement)){
				
				resultList.remove(subElement);//this is for keep the first match.
				///ok, bingo and remove
				Element needtoRemove=resultList.get(0);//this is for remove the second match.
				needtoRemove.removeChildren("subpath");//remove all subpath
				needtoRemove.getAttribute("RunGo").setValue("-1");
				
			}else{
				boolean done=removeStepbrach(subElement,resultList);
				if(done){
					return true;
				}
			}
		}
		
		return false;
		
	}


	private static boolean checkStepBranch(Element element, HashMap<String,Element> stepRecordMap, ArrayList<RemoveStepdata> passList, ArrayList<Element> resultList, int level) {
		// TODO Auto-generated method stub
		
		//System.out.println(stepRecordMap.keySet());
		
		
		for(RemoveStepdata data: passList){
			int dataPass=data.getGenerationpass();
			data.setGenerationpass(dataPass+1);//every datapass+1 means child to gradchild
			
			if(data.getGenerationpass()<=level){
				int generationInt=data.getGenerationpass();
				int currentclassid=Integer.valueOf(element.getAttribute("currentclassid").getValue());// this is currentelement's class id used to match and record
				
				
				try{
				data.getDescendant().get(generationInt).add(currentclassid);//generationInt:child,grad
				}catch(Exception e){
					System.out.println("bingo");
				}
				
				//at there, generationInt is the level,for example =1 is the child case, =2 is gradchild case and so on
			}
		}
		
		
		///////////////this node generate information to its descendant.
		if(!element.getChildren().isEmpty()){
			RemoveStepdata passData=new RemoveStepdata(level);
			passList.add(passData);//when it is sent the dataPass=0
		}
/*
		System.out.println("begin");
		System.out.println("RunGo:"+element.getAttributeValue("RunGo"));
		System.out.println("subid:"+element.getAttributeValue("subid"));
		for(RemoveStepdata data1 :passList){
			System.out.println("Pass:"+data1.getGenerationpass());
			System.out.println(data1.getDescendant());

			System.out.println("\n\n");
		}
*/
		
		if(!element.getChildren().isEmpty()){
			for(Element subElement:element.getChildren()){
	           // System.out.println(element.getAttribute("RunGo").getValue());
	          //  System.out.println(subElement.getAttribute("RunGo").getValue());
				
				
				boolean hasMatached=checkStepBranch(subElement,stepRecordMap, passList, resultList, level);
				
				if(hasMatached){
					return true;//it means there is a match, we need to go back out all of recursive method
				}
				
				for(RemoveStepdata data :passList){
					int dataPass=data.getGenerationpass();
					data.setGenerationpass(dataPass-1);	
				
					if(data.getGenerationpass()==0){
						////this part should after the loop. it also means after information selection from children
						//RemoveStepdata remeberforRemove=null;

						if(data.getDescendant().get(1).size()==element.getChildren().size()){// it means all branch has been returned on this node// 10/12 thinks this if condition can be removed
							//////////////////////////////////////////////////////
							if(data.getDescendant().get(level).isEmpty()){
								
								passList.remove(data);
								//break;// it means the last generation level do not have any element//break means every time only node can be pass=0
							}else{
								String stringforHash="node:"+String.valueOf(element.getAttribute("currentclassid"));
								for(int i=1; i<=level ;i++){//extract information from each level,from the first level as child
									stringforHash=stringforHash+"level"+String.valueOf(i)+":";
									for(int classid:data.getDescendant().get(i)){//get classid at each level
										stringforHash=stringforHash+String.valueOf(classid)+",";
									}
								}
							
								//bingo part
								if(stepRecordMap.keySet().contains(stringforHash)){
								     System.out.println("3 generation bingo");//3 generation are same
								     resultList.add(element);
								     resultList.add(stepRecordMap.get(stringforHash));//this result set can only have two items.
								     return true;//return false to return all recursive
								}
								/////////////////if no match, store itself
								stepRecordMap.put(stringforHash,element);
								//remeberforRemove=data;//we need to remove this data at last to save memory
								passList.remove(data);//remove it in the list

							}
							
							
						}

						break;//break means every time only node can be pass=0
					}
					
					
					
				}
				
				
				
				

				///deal with passList again, after done removeStepBranch
			}
		}
	
	
		/*
		System.out.println("begin");
		for(RemoveStepdata data :passList){
			System.out.println(data.getGenerationpass());
			System.out.println(data.getDescendant());
		}
		*/

		
		return false;
	}
/*

	private static boolean checkStepBranch(Element element, HashMap<String,Element> stepRecordMap, ArrayList<RemoveStepdata> passList, ArrayList<Element> resultList, int level) {
		// TODO Auto-generated method stub
		for(RemoveStepdata data: passList){
			int dataPass=data.getGenerationpass();
			data.setGenerationpass(dataPass+1);//every datapass+1 means child to gradchild
			
			
			
		    if(data.getGenerationpass()==1){//child case
		    	int currentclassid=Integer.valueOf(element.getAttribute("currentclassid").getValue());
		    	data.getChildren().add(currentclassid);
		    	
		    	
		    }else if(data.getGenerationpass()==2){//gradChild case
		    	int currentclassid=Integer.valueOf(element.getAttribute("currentclassid").getValue());
		    	data.getGradChildren().add(currentclassid);
		    }
		}
		

		//////////////////////////////////
		RemoveStepdata passData=new RemoveStepdata();
		passList.add(passData);//when it is sent the dataPass=0
		for(Element subElement:element.getChildren()){

			boolean hasMatached=checkStepBranch(subElement,stepRecordMap, passList, resultList, level);
			if(!hasMatached){
				return false;//it means there is a match, we need to go back out all of recursive method
			}
			///deal with passList again, after done removeStepBranch
			
		}
		
		////this part should after the loop
		RemoveStepdata remeberforRemove=null;
		for(RemoveStepdata data :passList){
			int dataPass=data.getGenerationpass();
			data.setGenerationpass(dataPass-1);
			if(data.getGenerationpass()==0){//it means this data is sent by this node of the path
				//we need to pick information out
				ArrayList<Integer> children=data.getChildren();
				ArrayList<Integer> gradChildren=data.getGradChildren();
				if(gradChildren.size()==0){
					break;//it means this node has no gradchildren (2- generation), we can omit all other data in list 
				}
				
				//create a string
				String stringforHash="node:"+String.valueOf(element.getAttribute("currentclassid"))+" children:";
				for(int child: children){
					stringforHash=stringforHash+String.valueOf(child)+",";
				}
				
				for(int gradChild: gradChildren){
					stringforHash=stringforHash+String.valueOf(gradChild)+",";
				}
				
				
				//bingo part
				if(stepRecordMap.keySet().contains(stringforHash)){
				     System.out.println("art bingo");//3 generation are same
				     resultList.add(element);
				     resultList.add(stepRecordMap.get(stringforHash));//this result set can only have two items.
				     return false;//return false to return all recursive
				}
				
				
				/////////////////if no match, store itself
				stepRecordMap.put(stringforHash,element);
				remeberforRemove=data;//we need to remove this data at last to save memory
			}
			
			
		}
		passList.remove(remeberforRemove);//remove it in the list
		
		
		
		
		
		
		
		return true;
	}
	*/
	
	
	
	private static void addtoRecord(Element rootrecord, Element classitem, Element finalresult, Element currentnode, int classID, ArrayList<String> similarActivity, HashMap<String, Element> existActivityMap) {
		// TODO Auto-generated method stub
		   List<Element> checkrecordlist=rootrecord.getChild("Classitem").getChildren();
		   
		   int  newid;
		   
		   if(classID==0){//copy the infromation into record, not in the path
			   Element newelement=new Element("ID");
			   newid=checkrecordlist.size();// the initial is 0, so do not add 1			   
			   //newelement.setAttribute("id",String.valueOf(checkrecordlist.size()+1));	
			   newelement.setAttribute("id",String.valueOf(newid));	
			   classitem.addContent(newelement);
			   
			   ///add the final result to the record
			   newelement.addContent(finalresult.getChild("Classname").clone());
			  //newelement.addContent(finalresult.getChild("Viewgroup").clone());
			  // newelement.addContent(finalresult.getChild("Nogroup").clone());
			   newelement.addContent(finalresult.getChild("Easyoperate").clone());
			   currentnode.setAttribute(new Attribute("nextclassname",String.valueOf(newid)));//at here add the newid
		   
			   //similarity activities part
			   Element similarEle=new Element("similarActivities");
			   newelement.addContent(similarEle);
			   
			   if(!similarActivity.isEmpty()){
				   //itself have a simiarity
				   String similarIDstr=String.valueOf(newid);
				   for(String similarId: similarActivity){
					   similarIDstr=similarIDstr+";"+similarId;//divided by the ;
				   }
				   similarEle.setText(similarIDstr);
				   
				   //update the related simiarity activity
				   for(String similarId: similarActivity){
					   Element existSimilarityEle=existActivityMap.get(similarId).getChild("similarActivities");
					   existSimilarityEle.setText(existSimilarityEle.getText()+";"+String.valueOf(newid));
				   }
				   
			   }else{
				   //put itself as a simiarity
				   String similarIDstr=String.valueOf(newid);
				   similarEle.setText(similarIDstr);
			   }
			   
		   }else{
			   currentnode.setAttribute(new Attribute("nextclassname",String.valueOf(classID)));
			   newid=classID;
		   }
		   		   
		   //pick up the current RunGo
		   int currentRunGo=Integer.parseInt(currentnode.getAttribute("RunGo").getValue());
		   
		   ///compute the nextRunGo and RunGodelay//at here RunGo delay is used to current RunGo=2 case, it can make sure the next is a BFS
		   //delay will be runned after all no delay item has been runned
		   //we do not need to consider the pathlist.size==0 case. It is because the no child will be added as subpath.
		   String nextRunGodelay="false";
		   
		   String nextRunGo=String.valueOf(currentRunGo-1);
		   
		   if(currentRunGo>1){
			   nextRunGodelay="true";
			   }
		   
		   
		   List<Element> pathlist=finalresult.getChild("Easyoperate").getChildren();//find children operation of finalresult
		   
		   
		 //modify the order of the pathlist, if the child has "OK","Done", it should be in the before part of the list
		   HashSet<String> sensiveWords=new HashSet<String>();
		   sensiveWords.add("done");
		   sensiveWords.add("ok");
		   //sensiveWords.add("cancel");
		   
		   
		   ArrayList<Element> movetoBefore=new ArrayList<Element>();
		   for(Element listitem: pathlist){
		   //this part is modified in the 1.14.2018  
			   boolean containWords=checklistitem(listitem,sensiveWords);//true means triggered
			   if(containWords){
				   movetoBefore.add(listitem);
			   }
		   }
		   pathlist.removeAll(movetoBefore);
		   pathlist.addAll(0,movetoBefore);
		   ///end the "OK" and "Done"
		   
		   
		   
		   
		   //detect the existing rule4
		   HashSet<String> existingRule4=new HashSet<String>();
	        for(Element rule4Item:rootrecord.getChild("Rule4").getChildren()){
	        	existingRule4.add(rule4Item.getName());
	        }
		   
		   
		   //orignal part
		   for(int i=0;i<pathlist.size();i++){
			   Element pathlistitem=(Element)pathlist.get(i);
			   String pathlistitemid=pathlistitem.getAttributeValue("id");
			   
			   String className=null;
			   
			   
			   if(pathlistitem.getChild("viewclass")!=null){
				   className=pathlistitem.getChild("viewclass").getText();
			   }else{
				   className=pathlistitem.getChild("childviewclass").getText();
			   }
			   
			   if(unexecuteClassName.contains(className)){//the edittext and checkbox can not executed.
				   continue;
			   }			   
			   
		       Element subpathitem=new Element("subpath");
		       subpathitem.setAttribute(new Attribute("subid",String.valueOf(pathlistitemid)));
		       
		     //detect the existing rule4.
		       if(!existingRule4.contains("Ele"+newid+"Ele"+String.valueOf(pathlistitemid))){//detect the existing rule4.
		           subpathitem.setAttribute(new Attribute("RunGo",String.valueOf(nextRunGo)));
		           subpathitem.setAttribute(new Attribute("NlpMh","false"));
		       }else{
		    	   subpathitem.setAttribute(new Attribute("RunGo",String.valueOf("-1")));
		    	   subpathitem.setAttribute(new Attribute("NlpMh","false"));
		       }
		       
		       subpathitem.setAttribute(new Attribute("RunGodelay",String.valueOf(nextRunGodelay)));
		       subpathitem.setAttribute(new Attribute("currentclassid",String.valueOf(newid)));
		       
		      currentnode.addContent(subpathitem);			       
		   }
		   //set the currentRunGo
		   currentnode.getAttribute("RunGo").setValue("0");
		   currentnode.getAttribute("RunGodelay").setValue("false");
	}


	private static boolean checklistitem(Element listitem, HashSet<String> sensingWords) {
		// TODO Auto-generated method stub
		List<Element> checklist=listitem.getChildren();
		for(Element checkItem: checklist){
			if(sensingWords.contains(checkItem.getText().toLowerCase())){
				return true;
			}
			boolean checkresult=checklistitem(checkItem,sensingWords);
			if(checkresult==true){
				return true;
			}
		}
		return false;
	}


	private static Element findCurrentNodewithDelay(Element element) {
		// TODO Auto-generated method stub
		
		List<Element> checklist=element.getChildren();		
		for(int i=0;i<checklist.size();i++){
			Element checklistitem=(Element)checklist.get(i);
			
			if(checklistitem.getAttributeValue("RunGo").equals("-1")){
				continue;
			}
			
			//this sentence is different with it in  findCurrentNode:  && checklistitem.getAttributeValue("RunGodelay").equals("false")
			if(!checklistitem.getAttributeValue("RunGo").equals("0") && checklistitem.getAttributeValue("RunGodelay").equals("false")){
				currentPathList.add(0,checklistitem);//inverse order add
				return checklistitem;
			}else{
				Element result=findCurrentNodewithDelay(checklistitem);
				if(result!=null){
					currentPathList.add(0,checklistitem);
					return result;
				}
			}
		}
		
		if(element.getName().equals("Path")){//add the pathroot
			currentPathList.add(0,element);
			if(element.getChildren().size()==0){//if the pathroot has no child, return itself
				return element;
			}
		}
		
		
		return null;
	}
	
	private static Element runPathwithDelay(Element element) {
		
		List<Element> checklist=element.getChildren();		
		for(int i=0;i<checklist.size();i++){
			Element checklistitem=(Element)checklist.get(i);
			
			if(checklistitem.getAttributeValue("RunGo").equals("-1")){
				continue;
			}
			//this sentence is different with it in  findCurrentNode:  && checklistitem.getAttributeValue("RunGodelay").equals("false")
			if(!checklistitem.getAttributeValue("RunGo").equals("0") && checklistitem.getAttributeValue("RunGodelay").equals("false")){
				pathToRun.add(0,checklistitem);//inverse order add,and the pathtorun is what need to added.
				return element;
			}else{
				Element result=runPathwithDelay(checklistitem);
				if(result!=null){
					pathToRun.add(0,checklistitem);
					return result;
				}
			}
		}
		
		if(element.getName().equals("Path")){//add the pathroot
			pathToRun.add(0,element);
			if(element.getChildren().size()==0){//if the pathroot has no child, return itself
				return element;
			}
		}
		
		
		return null;
	}
	
	
	
	
	private static Element findCurrentNode(Element element) {
		// TODO Auto-generated method stub
		//first case
		/*
		if(element.getName().equals("Pathroot") && element.getChildren().size()==0){
			currentPathList.add(0,element);
			return element;
		}
		*/
		/*
		if(element.getName().equals("Path")){
			currentPathList.add(element);
		}
		*/
		
		List<Element> checklist=element.getChildren();		
		for(int i=0;i<checklist.size();i++){
			Element checklistitem=(Element)checklist.get(i);
			if(checklistitem.getAttributeValue("RunGo").equals("-1")){
				continue;
			}
			if(!checklistitem.getAttributeValue("RunGo").equals("0")){
				currentPathList.add(0,checklistitem);//inverse order add
				return element;
			}else{
				Element result=findCurrentNode(checklistitem);
				if(result!=null){
					currentPathList.add(0,checklistitem);
					return result;
				}
			}
		}
		
		if(element.getName().equals("Path")){//add the pathroot
			currentPathList.add(0,element);
			if(element.getChildren().size()==0){//if the pathroot has no child, return itself
				return element;
			}
		}
		
		
		return null;
	}


	private static void checkresultnlpmatch(Element rootresult, ArrayList<Step> nlpsteplist, HashMap<Integer, ArrayList<Step>> nlpwholemap, ArrayList<Sents> sentslist, ArrayList<Matched> extendone, ArrayList<Matched> extendtwo, String address) throws FileNotFoundException, JDOMException, IOException {
		
		 /////read the similarity.xml/////
		while(true){
			File file=new File(address+"/middleResults/output/similarity.xml");
			if(file.exists()){
				break;
			}
		}
		

		SAXBuilder buildsimilarity = new SAXBuilder();//generate the builder
		String path=address+"/middleResults/output/similarity.xml";
		Document docsimilarity=buildsimilarity.build(new FileInputStream(path));//read the file
		Element rootsimilarity=docsimilarity.getRootElement();// get the root of similarity
		
		ArrayList<Similarity> Simlist=new ArrayList<Similarity>();
		
		for(Element IDsimilarityelement:rootsimilarity.getChildren()){
			String SimID=IDsimilarityelement.getChild("SentenceID").getText();
			String SentenceWord=IDsimilarityelement.getChild("SentenceWord").getText();
			String ResultWord=IDsimilarityelement.getChild("ResultWord").getText();
			
			if(!SentenceWord.equals(ResultWord)){
				if(!similarityMap.containsKey(SentenceWord)){
					ArrayList<String> newsim=new ArrayList<String>();
					newsim.add(ResultWord);
					similarityMap.put(SentenceWord,newsim);
				}else{
					similarityMap.get(SentenceWord).add(ResultWord);
				}
				
				/*
				Similarity sim=new Similarity();
				sim.setSimID(SimID);
				sim.setSentenceWord(SentenceWord);
				sim.setResultWord(ResultWord);
				Simlist.add(sim);
				*/
			}
		}
		
////////deal with the result.xml, the idea is matching text on each item of the result.xml//////////
		
		int rootsize=rootresult.getChildren().size();
		Element finalstep=rootresult.getChildren().get(rootsize-1);
		
		Element easyoperate=finalstep.getChild("Easyoperate");
		
		////rule 101. In the 1.26.2018 I will add a new rule 101. The same key word matching in a page, I just count the extendone and extendtwo as the most matching. As the "edit puzzle" and "delete puzzle" in a same page, I only count the "edit puzzle" as the most matched.
		///rule 102. Omit the punctuation matching.
		HashMap<String,HashMap<Integer,ArrayList<Matched>>> extendoneMap=new HashMap<String,HashMap<Integer,ArrayList<Matched>>>();
		//HashMap<"note",HashMap<1,ArrayList<Matched>>> //HashMap<"note",HashMap<2,ArrayList<Matched>>>, 1 and 2 is the time
		HashMap<String,HashMap<Integer,ArrayList<Matched>>> extendtwoMap=new HashMap<String,HashMap<Integer,ArrayList<Matched>>>();
		
		
		for(Element easyItem:easyoperate.getChildren()){//easyItem is the easyoperate part of the final result
			String easyID=easyItem.getAttributeValue("id");//<runableID id="1">
			
		   String className=null;
		   if(easyItem.getChild("viewclass")!=null){
			   className=easyItem.getChild("viewclass").getText();
		   }else{
			   className=easyItem.getChild("childviewclass").getText();
		   }
			   
		   boolean editTextOrnot=false;
		   if(className.equals("android.widget.EditText")){
			   editTextOrnot=true;
		   }
			
			//String resultClassName=easyItem.getChildText();
			//boolean matchornot =Checkeasyoperatematch(easyItem,Simlist,nlpsteplist,sentslist,extendone,extendtwo,easyID,className);// it is a recursion
		   //boolean matchornot =Checkeasyoperatematch101(easyItem,Simlist,nlpsteplist,sentslist,extendoneMap,extendtwoMap,easyID,className);// it is a recursion
		    //System.out.println(easyID);
		    //HashSet<String> nlpTextinResult=new HashSet<String>();
		    
		    //HashMap<String, Integer> nlpTextinResultmap=new HashMap<String, Integer>();
		    HashSet<ArrayList<String>> nlpTextinResultSet=new HashSet<ArrayList<String>>();//every list is a text end item in the result. and the word in list is the component of the item.
		    
		    
		    CheckeasyNlpText(easyItem,nlpTextinResultSet);
		    
		    String clickType="xyz";//defalut is nothing
		    if(easyItem.getChild("clicktype")!=null){
		    	clickType=easyItem.getChild("clicktype").getText();
		    }
		    //if(nlpTextinResultSet.){
		    	
		    
		    
		    
		    // all of this are in one easyitem.
		    HashMap<String, String> quatityRecord=new HashMap<String, String>();//<result, sent>
		    HashMap<String, String> similarityRecord=new HashMap<String, String>();//<result, sent>
		    
		    for(Sents sent: sentslist){//this sentlist is all bug report sents
		    	ArrayList<String> sentWordList=sent.getSentswords();
		    	
		    	ArrayList<String> addsentWordList=new ArrayList<String>();
		    	
		    	for(String sentWord: sentWordList){
		    		if(quatitytrans.containsKey(sentWord)){
		    			addsentWordList.add(quatitytrans.get(sentWord));//here can add a hashmap to record the add.
		    			quatityRecord.put(quatitytrans.get(sentWord), sentWord);
		    		}
		    		
		    		if(similarityMap.containsKey(sentWord)){
		    			for(String similarWord: similarityMap.get(sentWord)){
		    				if(!similarWord.equals(sentWord)){
		    					addsentWordList.add(similarWord);
		    					similarityRecord.put(similarWord, sentWord);
		    				}
		    			}
		    		}
		    	}
		    	
		    	sentWordList.addAll(addsentWordList);
		    	

		    	
		    	ArrayList<String> Result103=new ArrayList<String>();
		    	//String aa="bb";
		    	for(ArrayList<String> ResultList: nlpTextinResultSet){
		    		//SetitemList.size();
		    		@SuppressWarnings("unchecked")
					ArrayList<String> ResultListclone= (ArrayList<String>) ResultList.clone();
		    		ResultListclone.retainAll(sentWordList);
		    		if(!editTextOrnot){//if not a edittext //close by 2.19
		    			if(ResultListclone.size()>=2){
		    				
		    				if(addsentWordList.isEmpty() && !checkContinue(sentWordList, ResultListclone)){
		    				    continue;
		    					
		    				}else{
		    					Result103.addAll(ResultListclone);
		    				}
		    				
		    			}else if(ResultListclone.size()==1 && ResultList.size()==1){
		    				Result103.addAll(ResultListclone);
		    			}else{
		    				continue;
		    			}
		    			
		    			
		    			/*
			    		if(ResultList.size()>=2*ResultListclone.size() || ResultListclone.size()>=2){//not 103 match
			    			continue;
			    		}else{//103 match
			    			Result103.addAll(ResultListclone);
			    		}
			    		*/
		    		}else{//if a edottext //close by 2.19
		    			if(ResultListclone.size()>=2){
		    				
		    				if(addsentWordList.isEmpty() && !checkContinue(sentWordList, ResultListclone)){
		    				    continue;
		    					
		    				}else{
		    					Result103.addAll(ResultListclone);
		    				}
		    				
		    			}else if(ResultListclone.size()==1 && ResultList.size()==1){
		    				Result103.addAll(ResultListclone);
		    			}else{
		    				continue;
		    			}
		    			
		    			
		    			/*
				    		if(ResultList.size()>=2*ResultListclone.size() || ResultListclone.size()>=2){//not 103 match
		    			        continue;
				    		}else{//103 match
				    			Result103.addAll(ResultListclone);
				    		}
				        */
		    		} //close by 2.19
		    	}
		    	/*
		    	if(Result103.contains("l")){
		    	    System.out.println("aa");
		    	}
		    	*/
		    	
		    	int sentid=sent.getSentid();
		    	for(String resultWord: Result103){
		    		
		    		boolean longornot= checkLongorNot(resultWord,sent.getSentswords());
                    if(!longornot && clickType.equals("long")){
		    			continue;
		    		}
		    		
		    		
		    		
		    		if(!quatityRecord.containsKey(resultWord)){
			    		int indexWord=sent.getSentswords().indexOf(resultWord);
	    				boolean matchnlp=checkstepmatch101(nlpsteplist, resultWord, sentid, easyID, extendtwo,className,indexWord,sent,longornot);//this className is viewclassName
	                    if(!matchnlp){
	                    	Matched match= new Matched();
							match.setSteporSent(false);
							match.setSent(sent);
							match.setResultId(Integer.valueOf(easyID));
							match.setMatchedword(resultWord);
						    match.setSentId(sentid);//add in 2017.10.12
						    match.setViewClass(className);//add in 2017.10.13
						    match.setWordIndex(indexWord);
						    match.setLongornot(longornot);
						    extendtwo.add(match);
	                    }
		    		}
                    
                    
		    	}
		    	
                /////////////////quatitytrans and similarity map
		    	for(String resultWord: Result103){
		    		
		    		boolean longornot= checkLongorNot(resultWord,sent.getSentswords());
		    		if(!longornot && clickType.equals("long")){
		    			continue;
		    		}
		    		
		    		
		    		if(quatityRecord.containsKey(resultWord)){
		    			int indexWord=sentWordList.indexOf(quatityRecord.get(resultWord));
    					//boolean matchnlp=checkstepmatch(nlpsteplist,resultWord,sentid, easyID, extendtwo,className,indexWord,sent);
		    			boolean matchnlp=checkstepmatch(nlpsteplist,quatityRecord.get(resultWord),sentid, easyID, extendtwo,className,indexWord,sent,longornot);
    					if(!matchnlp){
	    					Matched match= new Matched();
							match.setSteporSent(false);
							match.setSent(sent);
							match.setResultId(Integer.valueOf(easyID));
							match.setMatchedword(resultWord);
						    match.setSentId(sentid);//add in 2017.10.12
						    match.setViewClass(className);//add in 2017.10.13
						    match.setWordIndex(indexWord);
						    match.setLongornot(longornot);
						    extendtwo.add(match);
						    //MatchList.add(match);
						    //addIntoExtendMap(extendtwoMap, resultWord,MatchList, wordMatchCount);
    					}
		    		}
		    		
		    		if(similarityRecord.containsKey(resultWord)){
		    			int indexWord=sentWordList.indexOf(similarityRecord.get(resultWord));
    					boolean matchnlp=checkstepmatch(nlpsteplist,resultWord,sentid, easyID, extendone,className,indexWord,sent,longornot);
    					if(!matchnlp){
	    					Matched match= new Matched();
							match.setSteporSent(false);
							match.setSent(sent);
							match.setResultId(Integer.valueOf(easyID));
							match.setMatchedword(resultWord);
						    match.setSentId(sentid);//add in 2017.10.12
						    match.setViewClass(className);//add in 2017.10.13
						    match.setWordIndex(indexWord);
						    match.setLongornot(longornot);
						    extendone.add(match);
						    //MatchList.add(match);
						    //addIntoExtendMap(extendtwoMap, resultWord,MatchList, wordMatchCount);
    					}
		    		}
		    		/*
		    		if(!extendtwo.isEmpty()){
		    			System.out.println("bingo");
		    		}
		    		*/
		    		
		    	}
		    }
		}	    	
	}
		    	
		    	
		    	
		    	/*
		    	
		    	
				int sentid=sent.getSentid();	
				@SuppressWarnings("unchecked")
				HashSet<String> Resultclone=(HashSet<String>) nlpTextinResult.clone();//nlpTextinResult is from the result of the tester
    			Resultclone.retainAll(sentWordList);
    			
    			//rule 103
    			if(!editTextOrnot){//if not a edittext
	    			if(nlpTextinResult.size()>=2*Resultclone.size()){
	    				continue;
	    			}
    			}
    			
    			
    			int wordMatchCount=Resultclone.size();
    			
    			for(String resultWord: Resultclone){
    				
    				int indexWord=sent.getSentswords().indexOf(resultWord);
    				ArrayList<Matched> MatchList=new ArrayList<Matched>();
    				boolean matchnlp=checkstepmatch101(nlpsteplist, resultWord, sentid, easyID, MatchList,className,indexWord,sent);//this className is viewclassName
    				if(matchnlp){
    					addIntoExtendMap(extendtwoMap, resultWord,MatchList, wordMatchCount);
    					
    					
    				}else{
						Matched match= new Matched();
						match.setSteporSent(false);
						match.setSent(sent);
						match.setResultId(Integer.valueOf(easyID));
						match.setMatchedword(resultWord);
					    match.setSentId(sentid);//add in 2017.10.12
					    match.setViewClass(className);//add in 2017.10.13
					    match.setWordIndex(indexWord);
					    MatchList.add(match);
					    addIntoExtendMap(extendtwoMap, resultWord,MatchList, wordMatchCount);    					
    				}
    			}
    			
    			/////////////////quatitytrans and similarity map
    			for(String sentWord: sentWordList){
    				if(quatitytrans.keySet().contains(sentWord)){
    					if(nlpTextinResult.contains(quatitytrans.get(sentWord))){
    						String resultWord=quatitytrans.get(sentWord);
    						int indexWord=sent.getSentswords().indexOf(resultWord);
    						ArrayList<Matched> MatchList=new ArrayList<Matched>();
    						boolean matchnlp=checkstepmatch(nlpsteplist,resultWord,sentid, easyID, MatchList,className,indexWord,sent);
    						if(matchnlp){
    	    					addIntoExtendMap(extendtwoMap, resultWord,MatchList, wordMatchCount);
    	    				}else{
    	    					Matched match= new Matched();
								match.setSteporSent(false);
								match.setSent(sent);
								match.setResultId(Integer.valueOf(easyID));
								match.setMatchedword(resultWord);
							    match.setSentId(sentid);//add in 2017.10.12
							    match.setViewClass(className);//add in 2017.10.13
							    match.setWordIndex(indexWord);
							    MatchList.add(match);
							    addIntoExtendMap(extendtwoMap, resultWord,MatchList, wordMatchCount);
    	    				}
    					}
    				}
    				
    				if(similarityMap.containsKey(sentWord)){
    					for (String simword: similarityMap.get(sentWord)){//simword is the resultword
    						if(nlpTextinResult.contains(simword)){
    							int indexWord=sent.getSentswords().indexOf(sentWord);
        						ArrayList<Matched> MatchList=new ArrayList<Matched>();
    							boolean matchnlp=checkstepmatch(nlpsteplist,simword,sentid, easyID, MatchList,className,indexWord,sent);
                                if(matchnlp){
        	    					addIntoExtendMap(extendoneMap, simword,MatchList, wordMatchCount);
                                }else{
    								Matched match= new Matched();
    								match.setSteporSent(false);
    								match.setSent(sent);
    								match.setResultId(Integer.valueOf(easyID));
    								match.setMatchedword(simword);
    							    match.setSentId(sentid);//add in 2017.10.12
    							    match.setViewClass(className);//add in 2017.10.13
    							    match.setWordIndex(indexWord);
    							    MatchList.add(match);
    							    addIntoExtendMap(extendoneMap, simword,MatchList, wordMatchCount);
                                }
    						}
    					}	
    			    }
    			}
		    
		    }
		    ///////rule 101 remove the low times match result in map.
		    //
		    for(String wordString: extendoneMap.keySet()){
		    	HashMap<Integer,ArrayList<Matched>> countMatchmap= extendoneMap.get(wordString);
		    	int maxwordMatchCount=Collections.max(countMatchmap.keySet());
		    	extendone.addAll(countMatchmap.get(maxwordMatchCount));
		    }
		    
		    for(String wordString: extendtwoMap.keySet()){
		    	HashMap<Integer,ArrayList<Matched>> countMatchmap= extendtwoMap.get(wordString);
		    	int maxwordMatchCount=Collections.max(countMatchmap.keySet());
		    	extendtwo.addAll(countMatchmap.get(maxwordMatchCount));
		    }
		    
		    */
		    
		    
		    //System.out.println("bingo");
		
		
		//deal with a and an. a and an means a list.
		
		
		
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
	
	/*
private static void addIntoExtendMap(
			HashMap<String, HashMap<Integer, ArrayList<Matched>>> extendtwoMap,
			String currentWord, ArrayList<Matched> nlpMatchList,
			int wordMatchCount) {
		// TODO Auto-generated method stub
			if(!extendtwoMap.containsKey(currentWord)){
				HashMap<Integer,ArrayList<Matched>>  newHashMap=new HashMap<Integer,ArrayList<Matched>>();
				extendtwoMap.put(currentWord, newHashMap);
			}
			
			if(extendtwoMap.get(currentWord).containsKey(wordMatchCount)){
				extendtwoMap.get(currentWord).get(wordMatchCount).addAll(nlpMatchList);
			}else{
				//HashMap<Integer,ArrayList<Matched>> newHashMap=new HashMap<Integer,ArrayList<Matched>>();
				extendtwoMap.get(currentWord).put(wordMatchCount, nlpMatchList);
			}
	}
*/

	private static boolean checkContinue(ArrayList<String> sentWordList,
			ArrayList<String> resultListclone) {
		// TODO Auto-generated method stub
		
        
		String resultStr="";
		for(String resultitem: resultListclone){
			resultStr=resultStr+resultitem;
		}
		
		///
		String sentWordStr="";
		for(String sentItem:sentWordList){
			sentWordStr=sentWordStr+sentItem;
		}
		
		if(sentWordStr.contains(resultStr)){
			return true;
		}
		
		return false;
	}


	private static boolean checkLongorNot(String resultWord,
			ArrayList<String> sentswords) {
		// TODO Auto-generated method stub
		
		
		HashSet<String> clickwords=new HashSet<String>();;
		clickwords.add("click");
		clickwords.add("press");
		clickwords.add("tap");
		
		
		if(!sentswords.contains("long")){
			return false;
		}
		
		if(sentswords.contains(resultWord)){
			
			
			int index=sentswords.indexOf(resultWord);
			
			for(int i=index; i>0; i--){
				if(clickwords.contains(sentswords.get(i))){
					if(i-1>=0){
						if(sentswords.get(i-1).equals("long")){
							return true;
						}
						
					}

					if(i-2>=0){
						if(sentswords.get(i-2).equals("long")){
							return true;
						}
					}
					return false;
				}
			}
		}
		
		
		
		
		
		
		
		return false;
	}


	/*

	private static boolean Checkeasyoperatematch101(Element easyitem,
			ArrayList<Similarity> simlist, ArrayList<Step> nlpsteplist,
			ArrayList<Sents> sentslist, HashMap<String,HashMap<Integer,ArrayList<Matched>>> extendoneMap,
			HashMap<String,HashMap<Integer,ArrayList<Matched>>> extendtwoMap, String easyID, String className) {//this className is viewclassName
		
		//the key idea of this part is every node in the bug report to match the nlp processing result and the sentence.
		//In the 1.26.2018 I will add a new rule 101. The same key word matching in a page, I just count the extendone and extendtwo as the most matching. As the "edit puzzle" and "delete puzzle" in a same page, I only count the "edit puzzle" as the most matched.
		
		HashSet 
		for(Element ele:easyitem.getChildren()){
			if(ele.getName().equals("level2") | ele.getName().equals("level3")){
				return false;
			}
			
			if(ele){
				
			}	
		}
			
			
			//////////////
			if(ele.getName().endsWith("nlp")){
				for(Element eleId: ele.getChildren()){
				
					
					String currentstring=eleId.getText();//current string is in the result.xml
					
					for(Sents sent: sentslist){//this sentlist is all bug report sents
						//boolean beforedigit=false;
						
						for(String word: sent.getSentswords()){//bugreport sent's word
			
							int indexWord=sent.getSentswords().indexOf(word);
							
							int sentid=sent.getSentid();
							if(word.equals(currentstring)){//word is from the bugreport, nlp is from result.xml
								
								
								
								boolean matchnlp=checkstepmatch(nlpsteplist,word,sentid, easyID, extendtwo,className,indexWord,sent);//this className is viewclassName
								if(!matchnlp){//it means no nlp part match.
									Matched match= new Matched();
									match.setSteporSent(false);
									match.setSent(sent);
									match.setResultId(Integer.valueOf(easyID));
									match.setMatchedword(currentstring);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);
								    
								    //sent.getSentswords().get(indexWord);
								    if(){
								    	
								    }
								    
									extendtwo.add(match);
								}
								
								
							}else if(quatitytrans.containsKey(word)){
								if(quatitytrans.get(word).equals(currentstring)){
									boolean matchnlp=checkstepmatch(nlpsteplist,word,sentid, easyID, extendtwo,className,indexWord,sent);
									if(!matchnlp){
										Matched match= new Matched();
										match.setSteporSent(false);
										match.setSent(sent);
										match.setResultId(Integer.valueOf(easyID));
										match.setMatchedword(currentstring);
									    match.setSentId(sentid);//add in 2017.10.12
									    match.setViewClass(className);//add in 2017.10.13
									    match.setWordIndex(indexWord);
										extendtwo.add(match);
									}
									
								}
							}
                            if(similarityMap.containsKey(word)){
								for (String simword: similarityMap.get(word)){
									if(currentstring.equals(simword)){
										boolean matchnlp=checkstepmatch(nlpsteplist,currentstring,sentid, easyID, extendone,className,indexWord,sent);
										if(!matchnlp){
											Matched match= new Matched();
											match.setSteporSent(false);
											match.setSent(sent);
											match.setResultId(Integer.valueOf(easyID));
											match.setMatchedword(currentstring);
										    match.setSentId(sentid);//add in 2017.10.12
										    match.setViewClass(className);//add in 2017.10.13
										    match.setWordIndex(indexWord);
											extendone.add(match);
										}
									}
								}
								
							}						
						}			
				}		
			    return false;
			}
			}else{
				
				//recursive part
				Checkeasyoperatematch101(ele,simlist,nlpsteplist,sentslist,extendoneMap,extendtwoMap,easyID,className);
				//Checkeasyoperatematch(ele,simlist,nlpsteplist,sentslist,extendone,extendtwo,easyID,className);
			}	
		}
		return false;
		
		
		
		// TODO Auto-generated method stub
		
	}
*/
    private static boolean CheckeasyNlpText(Element easyitem, HashSet<ArrayList<String>> nlpTextinResultList){
    	for(Element ele:easyitem.getChildren()){
			if(ele.getName().equals("level2") | ele.getName().equals("level3")){
				return false;
			}
			
			if(ele.getName().endsWith("nlp")){
				ArrayList<String> SetitemList=new ArrayList<String>();
				nlpTextinResultList.add(SetitemList);
				for(Element eleId: ele.getChildren()){
					String currentstring=eleId.getText();//current string is in the result.xml
					
					if(!typedSet.contains(currentstring) && !currentstring.equals("")){//add 4.12
						SetitemList.add(currentstring);
					}
					
					//SetitemList.add(currentstring);
					//nlpTextinResultList.put(currentstring, );
					
					//nlpTextinResultmap.add(currentstring);
				}
				//return false;
			}else{
				CheckeasyNlpText(ele,nlpTextinResultList);
			}
    	}
		return false;
    }
	/*
	private static boolean Checkeasyoperatematch(Element easyitem,
			ArrayList<Similarity> simlist, ArrayList<Step> nlpsteplist,
			ArrayList<Sents> sentslist, ArrayList<Matched> extendone,
			ArrayList<Matched> extendtwo, String easyID, String className) {//this className is viewclassName
		
		//the key idea of this part is every node in the bug report to match the nlp processing result and the sentence.
		//In the 1.26.2018 I will add a new rule 101. The same key word matching in a page, I just count the extendone and extendtwo as the most matching. As the "edit puzzle" and "delete puzzle" in a same page, I only count the "edit puzzle" as the most matched.
		for(Element ele:easyitem.getChildren()){
			if(ele.getName().equals("level2") | ele.getName().equals("level3")){
				return false;
			}
			
			if(ele.getName().endsWith("nlp")){
				for(Element eleId: ele.getChildren()){
				
					
					String currentstring=eleId.getText();//current string is in the result.xml
					
					for(Sents sent: sentslist){//this sentlist is all bug report sents
						//boolean beforedigit=false;
						for(String word: sent.getSentswords()){//bugreport sent's word
		
							int indexWord=sent.getSentswords().indexOf(word);
							
							int sentid=sent.getSentid();
							if(word.equals(currentstring)){//word is from the bugreport, nlp is from result.xml
								
								
								
								boolean matchnlp=checkstepmatch(nlpsteplist,word,sentid, easyID, extendtwo,className,indexWord,sent);//this className is viewclassName
								if(!matchnlp){//it means no nlp part match.
									Matched match= new Matched();
									match.setSteporSent(false);
									match.setSent(sent);
									match.setResultId(Integer.valueOf(easyID));
									match.setMatchedword(currentstring);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);
									extendtwo.add(match);
								}
								
								
							}else if(quatitytrans.containsKey(word)){
								if(quatitytrans.get(word).equals(currentstring)){
									boolean matchnlp=checkstepmatch(nlpsteplist,word,sentid, easyID, extendtwo,className,indexWord,sent);
									if(!matchnlp){
										Matched match= new Matched();
										match.setSteporSent(false);
										match.setSent(sent);
										match.setResultId(Integer.valueOf(easyID));
										match.setMatchedword(currentstring);
									    match.setSentId(sentid);//add in 2017.10.12
									    match.setViewClass(className);//add in 2017.10.13
									    match.setWordIndex(indexWord);
										extendtwo.add(match);
									}
									
								}
							}
                            if(similarityMap.containsKey(word)){
								for (String simword: similarityMap.get(word)){
									if(currentstring.equals(simword)){
										boolean matchnlp=checkstepmatch(nlpsteplist,currentstring,sentid, easyID, extendone,className,indexWord,sent);
										if(!matchnlp){
											Matched match= new Matched();
											match.setSteporSent(false);
											match.setSent(sent);
											match.setResultId(Integer.valueOf(easyID));
											match.setMatchedword(currentstring);
										    match.setSentId(sentid);//add in 2017.10.12
										    match.setViewClass(className);//add in 2017.10.13
										    match.setWordIndex(indexWord);
											extendone.add(match);
										}
									}
								}
								
							}						
						}			
				}		
			    return false;
			}
			}else{
				
				//recursive part
				Checkeasyoperatematch(ele,simlist,nlpsteplist,sentslist,extendone,extendtwo,easyID,className);
			}	
		}
		return false;
		
		
		
		// TODO Auto-generated method stub
		
	}
*/
	private static boolean checkstepmatch101(ArrayList<Step> nlpsteplist,
			String currentstring, int sentid, String easyID, ArrayList<Matched> extendvalue, String className, int indexWord, Sents sent, boolean longornot) {//this className is viewclassName

		boolean matchorNot=false;		
		
		for(Step nlpstep: nlpsteplist){
			if(nlpstep.getSentenceid()==sentid){//the sentence id should match
			  //if(true){		
					switch(nlpstep.getType()){
					case "click":
						for (ArrayList<String> substringList: nlpstep.getClickwhere()){
							for(String nlpword: substringList){
								if(nlpword.equals(currentstring)){
									Matched match=new Matched();
									match.setMatchedword(currentstring);
								    match.setStep(nlpstep);
								    match.setResultId(Integer.valueOf(easyID));
								    match.setSteporSent(true);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setStepId(nlpsteplist.indexOf(nlpstep));//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);//add in 2017.10.17
								    match.setSent(sent);//add in 2017.10.17
								    match.setLongornot(longornot);
								    
								    extendvalue.add(match);
								    matchorNot=true;
								}
							}
						}
						//currentstring=
						
						
						break;
					
					case "input":
						for (ArrayList<String> substringList: nlpstep.getTypewhere()){
							for(String nlpword: substringList){
								if(nlpword.equals(currentstring)){
									Matched match=new Matched();
									match.setMatchedword(currentstring);
								    match.setStep(nlpstep);
								    match.setResultId(Integer.valueOf(easyID));
								    match.setSteporSent(true);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setStepId(nlpsteplist.indexOf(nlpstep));//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);
								    match.setSent(sent);//add in 2017.10.17
								    extendvalue.add(match);
								    matchorNot=true;
								}
							}
						}
						
						for (ArrayList<String> substringList: nlpstep.getDigittypewhere()){
							for(String nlpword: substringList){
								if(nlpword.equals(currentstring)){
									Matched match=new Matched();
									match.setMatchedword(currentstring);
								    match.setStep(nlpstep);
								    match.setResultId(Integer.valueOf(easyID));
								    match.setSteporSent(true);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setStepId(nlpsteplist.indexOf(nlpstep));//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);
								    match.setSent(sent);//add in 2017.10.17
								    extendvalue.add(match);
								    matchorNot=true;
								}
							}
						}
						
						break;
					
					case "rotate":
						break;
					
					case "create":
						for (ArrayList<String> substringList: nlpstep.getCreatewhat()){
							for(String nlpword: substringList){
								if(nlpword.equals(currentstring)){
									Matched match=new Matched();
									match.setMatchedword(currentstring);
								    match.setStep(nlpstep);
								    match.setResultId(Integer.valueOf(easyID));
								    match.setSteporSent(true);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setStepId(nlpsteplist.indexOf(nlpstep));//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);
								    match.setSent(sent);//add in 2017.10.17
								    extendvalue.add(match);
								    matchorNot=true;
								}
							}
						}
						break;
					
					case "cancel":	
						break;
				}
		
			}
		
		
		}
			
		return matchorNot;
		
		// TODO Auto-generated method stub
		
	}
	
	
	
	private static boolean checkstepmatch(ArrayList<Step> nlpsteplist,
			String currentstring, int sentid, String easyID, ArrayList<Matched> extendvalue, String className, int indexWord, Sents sent, boolean longornot) {//this className is viewclassName

		boolean matchorNot=false;		
		
		for(Step nlpstep: nlpsteplist){
			if(nlpstep.getSentenceid()==sentid){//the sentence id should match
			  //if(true){		
					switch(nlpstep.getType()){
					case "click":
						for (ArrayList<String> substringList: nlpstep.getClickwhere()){
							for(String nlpword: substringList){
								if(nlpword.equals(currentstring)){
									Matched match=new Matched();
									match.setMatchedword(currentstring);
								    match.setStep(nlpstep);
								    match.setResultId(Integer.valueOf(easyID));
								    match.setSteporSent(true);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setStepId(nlpsteplist.indexOf(nlpstep));//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);//add in 2017.10.17
								    match.setSent(sent);//add in 2017.10.17
								    match.setLongornot(longornot);
								    extendvalue.add(match);
								    matchorNot=true;
								}
							}
						}
						//currentstring=
						
						
						break;
					
					case "input":
						for (ArrayList<String> substringList: nlpstep.getTypewhere()){
							for(String nlpword: substringList){
								if(nlpword.equals(currentstring)){
									Matched match=new Matched();
									match.setMatchedword(currentstring);
								    match.setStep(nlpstep);
								    match.setResultId(Integer.valueOf(easyID));
								    match.setSteporSent(true);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setStepId(nlpsteplist.indexOf(nlpstep));//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);
								    match.setSent(sent);//add in 2017.10.17
								    extendvalue.add(match);
								    matchorNot=true;
								}
							}
						}
						
						for (ArrayList<String> substringList: nlpstep.getDigittypewhere()){
							for(String nlpword: substringList){
								if(nlpword.equals(currentstring)){
									Matched match=new Matched();
									match.setMatchedword(currentstring);
								    match.setStep(nlpstep);
								    match.setResultId(Integer.valueOf(easyID));
								    match.setSteporSent(true);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setStepId(nlpsteplist.indexOf(nlpstep));//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);
								    match.setSent(sent);//add in 2017.10.17
								    extendvalue.add(match);
								    matchorNot=true;
								}
							}
						}
						
						break;
					
					case "rotate":
						break;
					
					case "create":
						for (ArrayList<String> substringList: nlpstep.getCreatewhat()){
							for(String nlpword: substringList){
								if(nlpword.equals(currentstring)){
									Matched match=new Matched();
									match.setMatchedword(currentstring);
								    match.setStep(nlpstep);
								    match.setResultId(Integer.valueOf(easyID));
								    match.setSteporSent(true);
								    match.setSentId(sentid);//add in 2017.10.12
								    match.setStepId(nlpsteplist.indexOf(nlpstep));//add in 2017.10.12
								    match.setViewClass(className);//add in 2017.10.13
								    match.setWordIndex(indexWord);
								    match.setSent(sent);//add in 2017.10.17
								    extendvalue.add(match);
								    matchorNot=true;
								}
							}
						}
						break;
					
					case "cancel":	
						break;
				}
		
			}
		
		
		}
			
		return matchorNot;
		
		// TODO Auto-generated method stub
		
	}

/*
	//////common run
	public static void run() throws JDOMException, IOException {//run after the first time
		//XMLOutputter xmlCheck=new XMLOutputter();
		
		
		///operate on the record
		SAXBuilder builder = new SAXBuilder();
		File recordFile= new File("/home/yu/repeatbugreport/middleResults/record.xml");
		Document recorddoc= (Document) builder.build(recordFile);
		Element rootrecord=recorddoc.getRootElement();

		
		/////read the result.xml/////
		SAXBuilder sb = new SAXBuilder();//generate the builder
		String path="/home/yu/repeatbugreport/middleResults/result.xml";
		Document docresult=sb.build(new FileInputStream(path));//read the file
		Element rootresult=docresult.getRootElement();
		
		Element allpath=rootrecord.getChild("Path");
		
		
		////////add a menu part
	     //////////////menu case//////////
		///the checkandadd is done without the menu
		// we can add something in the existing run.xml rather than generate a new one
		// the new run.xml will with the tag menu case
		// if the commander receive a menu tag case, it will 
		
		
		     List<Element> classlist=rootrecord.getChild("Classitem").getChildren();
		     List<Element> resultlistformenu=rootresult.getChildren();
		     String finalclassname=resultlistformenu.get(resultlistformenu.size()-1).getChild("classname").getText(); //The last class name 		
		
		     boolean thereismatch=false;
		
		     for(Element classitem : classlist){
		    	  if(classitem.getChild("classname").getText().equals(finalclassname)){
			         thereismatch=true;
			   	 break;	
			   }			
		     }
		
		////add a menu part end
		
		
		Element finalresult = null;
		
		if(rootresult.getChild("Fail")!=null){////failpath ase
			int failstep=Integer.valueOf(rootresult.getChild("Fail").getChild("stepid").getText())-1;//from 0, so it need to be reduced by 1
			
			//do some other things
			System.out.println("fail");
			///read the run.xml
			SAXBuilder sbrun = new SAXBuilder();//generate the builder
			String pathrun="/home/yu/repeatbugreport/middleResults/run.xml";
			Document doca=sbrun.build(new FileInputStream(pathrun));//read the file
			Element rootrun=doca.getRootElement();//get the root
			List<Element> runlist=rootrun.getChildren();
			Element currentelement=allpath;			
			boolean thisisfailtep=false;
	
			if (rootrecord.getChild("Failedpath")==null){
				rootrecord.addContent(new Element("Failedpath"));
			}		
			
			Element failedpath=rootrecord.getChild("Failedpath");
			int failedsize=failedpath.getChildren().size();
			String newfailid="failid"+String.valueOf(failedsize+1);
			failedpath.addContent(new Element(newfailid));//add the new failcase
			
            failedpath.getChild(newfailid).addContent(rootrun.clone());//add the run information
            failedpath.getChild(newfailid).addContent(rootresult.getChild("Fail").clone());//add the fail information
			
			
			for(int i=0;i<runlist.size();i++){
				String graphclassfrom=runlist.get(i).getChild("graphclassfrom").getText();
				String graphclassitem=runlist.get(i).getChild("graphclassitem").getText();
				
				
				
				if(i==failstep){
					thisisfailtep=true;
				}			
				
				if(graphclassfrom.equals("firstrun")){//the firstrun or no action run step	
					
					// it is unpossible to happen fail at there
					//continue;
				}else{
					
					List<Element> subcurrentlist=currentelement.getChildren();				
					for(int j=0;j<subcurrentlist.size();j++){
						Element subcurrentitem=subcurrentlist.get(j);
						if(subcurrentitem.getAttribute("subid").getValue().equals(graphclassitem)){
							
							//currentelement=subcurrentitem;
							if(thisisfailtep==true){		
								currentelement.removeChildren("subpath");//remove the content											
							//	currentelement
								currentelement.removeAttribute("nextclassname");
								//currentelement.removeAttribute("nextclassname");
								rootresult.getChild("Fail").removeChild("stepid");
								checkandadd(allpath, rootrecord, rootresult.getChild("Fail"));
								finalresult=rootresult.getChild("Fail");//used by menu
													
								
								//add the new current id to current's father
								// remove the current
								// add new current
							}else{
								currentelement=subcurrentitem;
							}							
							break;
						}						
					}									
				}						
			}		
		}else{//normal case
			List resultlist=rootresult.getChildren();
			finalresult=(Element)resultlist.get(resultlist.size()-1);//get the finnal result
			checkandadd(allpath, rootrecord, finalresult);//added the finalresult
		}
			
		
//////////////////////////common case/////////////////////////
////////wrtie the record.xml
XMLOutputter xmlOutput = new XMLOutputter();
xmlOutput.setFormat(Format.getPrettyFormat());
xmlOutput.output(recorddoc, new FileWriter("/home/yu/repeatbugreport/middleResults/record.xml"));  
	     


	     ////menu case
	   if(thereismatch==false){//should run a menu,should be ==, != is for test, run menu at first time of class
		    int thisclassid=classlist.size();   
		 ///operate on the run
			SAXBuilder builderunmenu = new SAXBuilder();
			File runmenufile= new File("/home/yu/repeatbugreport/middleResults/run.xml");
			Document runmenudoc= (Document) builder.build(runmenufile);
			Element rootrunmenu=runmenudoc.getRootElement();
			rootrunmenu.setName("runmenu");
		    
			int stepid=rootrunmenu.getChildren().size()+1;
			
			Element step=new Element("step");
			step.setAttribute(new Attribute("id",String.valueOf(stepid)));
			rootrunmenu.addContent(step);
			
			Element outputcurrent=new Element("outputcurrent");
			outputcurrent.setText("yes");
			step.addContent(outputcurrent);
		//	Element graphclassfrom=new Element("graphclassfrom");
		//	graphclassfrom.setText("menu");
			Element action=new Element("action");
			step.addContent(action);
			
			Element actiontype=new Element("Actiontype");
			actiontype.setText("menu");
			action.addContent(actiontype);
			
			Element classname=new Element("classname");
			classname.setText(finalclassname);
			action.addContent(classname);
			
			Element classid=new Element("classid");
			classid.setText(String.valueOf(thisclassid));
			action.addContent(classid);
			
			
			//action.addContent(new Element("Actiontype")).setText("menu");
			//action.addContent(new Element("classname")).setText(finalclassname);
			//action.addContent(new Element("classid")).setText(String.valueOf(stepid));
			
			//<outputcurrent>yes</outputcurrent><graphclassfrom>3</graphclassfrom><graphclassitem>12</graphclassitem>
			
			
			XMLOutputter xmlOutputmenu = new XMLOutputter();
			xmlOutputmenu.setFormat(Format.getPrettyFormat());
			xmlOutputmenu.output(runmenudoc, new FileWriter("/home/yu/repeatbugreport/middleResults/run.xml"));
			
	    }else{
		
		
		
		
	    //////generate the run.xml////////////
		
		    Document docrun = new Document();
		    OutputStream writerrun = null;
		    try {  
		       writerrun = new FileOutputStream("/home/yu/repeatbugreport/middleResults/run.xml");  
	     	} catch (FileNotFoundException e1) {  
		       e1.printStackTrace();  
		    } 
		
		    Element Root=new Element("Run");
		
		    ///////////////////set the noaction, it is used to output the first screen
		    addfirst(Root,"no");
		    runstep++;
		    ////////////////////////////////
		
		    generaterun(allpath,rootrecord, Root);
		
		
		
		    XMLOutputter outrun = new XMLOutputter();
		    Format formatrun = Format.getPrettyFormat();  
		    outrun.setFormat(formatrun);
		    docrun.addContent(Root);
		    try {  
		       outrun.output(docrun, writerrun);  
	    	} catch (IOException e) {  
	    	    e.printStackTrace();  
	    	}
		
	    	try {
		    	writerrun.close();//close the writer
		    } catch (IOException e) {
		    	// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
		
		// TODO Auto-generated method stub
	}
	*/
	/*
    public static void checkandadd(Element allpath, Element rootrecord, Element finalresult) throws FileNotFoundException, JDOMException, IOException{
    	
		
		Element focuselement=Checkcurrentpath(allpath);//fill currentpathlist
	
		
		/////////check
		Element classitem=rootrecord.getChild("Classitem");
		List checkrecordlist=classitem.getChildren();
		int matchid=0;
		boolean tomatch=false;//check all
		boolean tocheck=false;//check in the list
		//////
		for(int m=0;m<checkrecordlist.size();m++){
			Element needtocheck=(Element)checkrecordlist.get(m);
			if(currentpathlist.contains(m+1)){//in the currentpathlist
				boolean checkequal=checkchildrenequal(needtocheck,finalresult);			
				if(checkequal==true)//true means needtocheck and finalresult are equal with each other
                {
					matchid=m+1;
					System.out.println("bingo");
				    tocheck=true;
				    break;
				}			
			}else{// not in the currentpathlist but in the record
                boolean checkequal=checkchildrenequal(needtocheck,finalresult);
				
				if(checkequal==true)//true means needtocheck and finalresult are equal with each other
                {
					
					matchid=m+1;
					System.out.println("bingo");
				    tomatch=true;
				    //break;
				}
			}
		}
		
		if(tocheck==false){//fix 2017/5/21 yuzhao, deal with a activity change its content again and again. It will generate the explore of dfs			
			//this fix only work on the all outputcurrent ==yes on every step
			List<Element> listresult=finalresult.getParentElement().getChildren();
			for(int q=0; q< listresult.size()-1; q++){//this -1 can except the finalresult
				Element resultitem=listresult.get(q);
				boolean checkequal=checkchildrenequal(resultitem,finalresult);
				if(checkequal==true){
					if(matchid==0){
					
				    	SAXBuilder sbrun = new SAXBuilder();//generate the builder
				  	    String pathrun="/home/yu/repeatbugreport/middleResults/run.xml";
					    Document doca=sbrun.build(new FileInputStream(pathrun));//read the file
					    Element rootrun=doca.getRootElement();//get the root
					    List<Element> runlist=rootrun.getChildren();
					    Element runstepitem=runlist.get(q+1);//the next step has this step class
					    String graphclassfrom =runstepitem.getChild("graphclassfrom").getText();					
					    matchid=Integer.valueOf(graphclassfrom);	
					    
					}
					tocheck=true;
					System.out.println("bingo 5/21");
					break;
				}
				
			}
			
			
			
			
			
			//finalresult
			
		}
		
		
		
		/////////add
		if(tocheck==false){//no exited classview match the finalresult in path
            if(tomatch==true){// there is a classview match in the record but not in path
         	   int newid=matchid;
         	   focuselement.setAttribute(new Attribute("nextclassname",String.valueOf(newid)));//at here add the newid
         	   List pathlist=finalresult.getChild("Easyoperate").getChildren();//add all item's all Easyoperate item
 			   for(int y=0;y<pathlist.size();y++){
 				   Element pathlistitem=(Element)pathlist.get(y);
 				   String pathlistitemid=pathlistitem.getAttributeValue("id");
 			       Element subpathitem=new Element("subpath");
 			       subpathitem.setAttribute(new Attribute("subid",String.valueOf(pathlistitemid)));
 			       subpathitem.setAttribute(new Attribute("color","white"));
 			       focuselement.addContent(subpathitem);			       
 			   }
         	   
         	   
         	   
         	   
            }else{//no classview matched in the record
         	 //add it to the record
 			   Element newelement=new Element("ID");
 			   int newid=checkrecordlist.size()+1;			   
 			   //newelement.setAttribute("id",String.valueOf(checkrecordlist.size()+1));	
 			   newelement.setAttribute("id",String.valueOf(newid));	
 			   classitem.addContent(newelement);
 			   
 			   ///add the final result to the record
 			   newelement.addContent(finalresult.getChild("classname").clone());
 			   newelement.addContent(finalresult.getChild("Viewgroup").clone());
 			   newelement.addContent(finalresult.getChild("Nogroup").clone());
 			   newelement.addContent(finalresult.getChild("Easyoperate").clone());
 			   
 			   ///add the path
 			   ///at the last time focuselement may be null
 			 //  if(focuselement==null){
 			  //     System.out.println("finish case");
 			 //  }else{
 			   focuselement.setAttribute(new Attribute("nextclassname",String.valueOf(newid)));//at here add the newid
 			 //  }
 			   
 			   
 			   List pathlist=finalresult.getChild("Easyoperate").getChildren();
 			   for(int y=0;y<pathlist.size();y++){
 				   Element pathlistitem=(Element)pathlist.get(y);
 				   String pathlistitemid=pathlistitem.getAttributeValue("id");
 			       Element subpathitem=new Element("subpath");
 			       subpathitem.setAttribute(new Attribute("subid",String.valueOf(pathlistitemid)));
 			       subpathitem.setAttribute(new Attribute("color","white"));
 			       focuselement.addContent(subpathitem);			       
 			   }
 			   
 			   //an activity has no clickable child case
 			   if(pathlist.size()==0){
 				  focuselement.getAttribute("color").setValue("black");
 			   }
 			   
 			   
             }
			   				   			   
			}else{//exited classview match the finalresult in the path
				///print the black
				//Element allpath=rootrecord.getChild("Path");
				if(focuselement!=null){
				focuselement.getAttribute("color").setValue("black");
				}
				//find the programming end at the run part rather than this part.
				focuselement.setAttribute(new Attribute("nextclassname",String.valueOf(matchid)));
				
				String finalcolor=printtheblack(allpath,focuselement);
				if(finalcolor.equals("black")){
					allpath.getAttribute("color").setValue("black");
				}
				
				
				//if(allpath.getAttributeValue("color").equals("black")){
				//	System.out.println("all the programming is done");
			//	}
				
			}
    	
    	
    }
	*/
	private static Element generaterun(Element element, Element rootrecord, Element Root) {
		// TODO Auto-generated method stub
		Document docrun = new Document();
		OutputStream writerrun = null;
		try {  
		    writerrun = new FileOutputStream("/home/yu/repeatbugreport/middleResults/run.xml");  
		} catch (FileNotFoundException e1) {  
		    e1.printStackTrace();  
		} 
		
		
		
		
		Element classitem=rootrecord.getChild("Classitem");
		List<Element> classitemlist=classitem.getChildren();
		


		///////////////////////////////////////////////
		List<Element> checklist=element.getChildren();	//the element is rootrecord.getChild("Path");	
		
		for(int i=0;i<checklist.size();i++){
			Element checklistitem=(Element)checklist.get(i);
			if(checklistitem.getAttributeValue("color").equals("black")){//color is black
				continue;
			}else{
				  
					  ////add run
					  Element step=new Element("step");
					  Root.addContent(step);
					  step.setAttribute(new Attribute("id",String.valueOf(++runstep)));
					  
					  
					  if(runstep==1){
						  System.out.println(runstep);
					  }
					  
					  
					  ///set outputcurrent
					  Element outputcurrent=new Element("outputcurrent");
					  outputcurrent.setText("yes");
					  ///set currentclass
					  Element graphclassfrom=new Element("graphclassfrom");
					  String before= checklistitem.getParentElement().getAttributeValue("nextclassname");
					  graphclassfrom.setText(before);
					  ///set sub id
					  Element graphclassitem=new Element("graphclassitem");
					  String sub=checklistitem.getAttributeValue("subid");
					  graphclassitem.setText(sub);
					  ///set action
					  Element action=new Element("action");
					  
					  String thisclassid=checklistitem.getParentElement().getAttributeValue("nextclassname");
					  //String subid=checklistitem.getAttributeValue("subid");
					  
					  
					  Element currentclass=classitemlist.get(Integer.valueOf(thisclassid)-1);
					  Element Easyoperate=currentclass.getChild("Easyoperate");
					  
					  List<Element> Easyoperatelist=Easyoperate.getChildren();
					  Element Easyoperateitem=Easyoperatelist.get(Integer.valueOf(sub)-1);
					  if(Easyoperateitem.getChild("motherviewclass")!=null){//the motherviewclass means the class is a viewgroup
						  Element actiontype=new Element("Actiontype");
						  actiontype.setText("ClickList");
						  
						  Element clicktype=new Element("clicktype");
						  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
						  
						  
						 // clicktype.setText("short");//here need to add long
						  
						  Element parameter=new Element("Parameter");
						  parameter.setText(Easyoperateitem.getChild("listindex").getText());		
						  
						  action.addContent(actiontype);
						  action.addContent(clicktype);
						  action.addContent(parameter);
						  
						  
						  
					  }else{
						  Element clicktype=new Element("clicktype");
						  clicktype.setText(Easyoperateitem.getChild("clicktype").getText());
						  
						  
						  Element actiontype=new Element("Actiontype");
						  Element parameter=new Element("Parameter");
						  
						  if(Easyoperateitem.getChild("imagebutton")!=null){
							  actiontype.setText("ClickImagebutton");
							  parameter.setText(Easyoperateitem.getChild("imagebutton").getText());	  
						  }else{
							  actiontype.setText("ClickText");
							  parameter.setText(Easyoperateitem.getChild("viewtext").getText());	  
						  }
						  
						   
						  
						  action.addContent(actiontype);
						  action.addContent(clicktype);
						  action.addContent(parameter);
						  
					  }
					  
			          step.addContent(outputcurrent);
			          step.addContent(graphclassfrom);
			          step.addContent(graphclassitem);
			          step.addContent(action);
					  
			      if(checklistitem.getChildren().size()!=0){
			    	  Element itemfound=generaterun(checklistitem, rootrecord, Root);
			    	  
			    	  
					  //Element itemfound=Checkcurrentpath(checklistitem);
					  if(itemfound!=null){//if itemfound!=null, means there is a white in below paths.
						   return itemfound;	
					  }
				  }else{
					  return checklistitem;  
				  }	
			}
		}
		
		
		return null;

		
	}

	private static String printtheblack(Element element, Element focuselement) {
	//	String color;
		List checklist=element.getChildren();		
		for(int i=0;i<checklist.size();i++){
			Element checklistitem=(Element)checklist.get(i);
			if(checklistitem.getAttributeValue("color").equals("black")){//color is black
				continue;
			}else{//color is white
				if(checklistitem.getChildren().size()!=0){//has children
					String color=printtheblack(checklistitem,focuselement);
					if(color.equals("black")){
						checklistitem.getAttribute("color").setValue("black");
					}else{//return is white
						return "white";
					}	
				}else{//has no children
					return "white";
				}
			}
			
		}
		
		
		
		// TODO Auto-generated method stub
		return "black";
	}

	private static Element Checkcurrentpath(Element element) {
		// TODO Auto-generated method stub
		//first case
		if(element.getName().equals("Path") && element.getChildren().size()==0){
			return element;
		}
		
		if(element.getName().equals("Path")){
			currentpathlist.add(Integer.valueOf(element.getAttributeValue("nextclassname")));
		}
		
		//after case
		List checklist=element.getChildren();		
		for(int i=0;i<checklist.size();i++){
			Element checklistitem=(Element)checklist.get(i);
			if(checklistitem.getAttributeValue("color").equals("black")){//color is black
				continue;
			}else{//color is white
				if(checklistitem.getChildren().size()!=0){//has children
					//if(checklistitem.getAttributeValue("nextclassname")!=null){
						    currentpathlist.add(Integer.valueOf(checklistitem.getAttributeValue("nextclassname")));
						//}
					
					
					Element itemfound=Checkcurrentpath(checklistitem);
					//if(itemfound==)
					//if(itemfound.getAttributeValue("nextclassname")!=null){
				//	    currentpathlist.add(Integer.valueOf(itemfound.getAttributeValue("nextclassname")));
				//	}
					if(itemfound!=null){//if itemfound!=null, means there is a white in below paths.
					   return itemfound;	
					}
					
					//return itemfound;
					
				}else{//has no children
					return checklistitem;
				}		
			}
						
		}
		  if(element.getName().equals("Path")){
		   System.out.println("error"+"all black children but white parent");
		   System.out.println("false error"+"all runs done");
		   //return element;
		  }else{
		   System.out.println("error"+"all black children but white parent");
		  }
		   
	   return null;
	}

	
	
	
	
	private static boolean checkchildrenequal(Element element1,
			Element element2,int[] countForMatch) {
		List checklist1=element1.getChildren();
		List checklist2=element2.getChildren();
		
		if(checklist1.size()!=checklist2.size()){
			return false;
		}
		
		
		for(int i=0;i<checklist1.size();i++){
			Element checklistitem1=(Element)checklist1.get(i);
			Element checklistitem2=(Element)checklist2.get(i);
			
			//check the structure
			if(!checklistitem1.getName().equals(checklistitem2.getName())){
				return false;
			}else{
				countForMatch[0]=countForMatch[0]+1;
			}
			
			//check the textal
			if(!checklistitem1.getText().equals(checklistitem2.getText())){
				if(!checklistitem1.getText().startsWith("\n")){//and not void text
					countForMatch[1]=countForMatch[1]+1;
				    //unmatchedText++;
					//return false;
			    }
			}
			
			/*
			if(!checklistitem1.getText().equals(checklistitem2.getText())){//if not equal
				if(!checklistitem1.getText().startsWith("\n")){//and not void text
				    return false;
			    }
			}
			*/
			//attribution should be added in the future ifr needed
			
			boolean checkresult=checkchildrenequal(checklistitem1,checklistitem2,countForMatch);
			
			if(checkresult==false){
				return false;
			}
		}
		return true;
	}
	
	
	private static boolean checkchildrenALLequal(Element element1,
			Element element2) {
		List checklist1=element1.getChildren();
		List checklist2=element2.getChildren();
		
		if(checklist1.size()!=checklist2.size()){
			return false;
		}
		
		
		for(int i=0;i<checklist1.size();i++){
			Element checklistitem1=(Element)checklist1.get(i);
			Element checklistitem2=(Element)checklist2.get(i);
			
			if(!checklistitem1.getName().equals(checklistitem2.getName())){
				return false;
				
			}
			if(!checklistitem1.getText().equals(checklistitem2.getText())){//if not equal
				if(!checklistitem1.getText().startsWith("\n")){//and not void text
				    return false;
			    }
			}
			//attribution should be added in the future ifr needed
			
			boolean checkresult=checkchildrenALLequal(checklistitem1,checklistitem2);
			
			if(checkresult==false){
				return false;
			}		
		}
		return true;
	}
	
	/*
	private static boolean checkchildrenequal(Element element1,
			Element element2) {
		List checklist1=element1.getChildren();
		List checklist2=element2.getChildren();
		
		if(checklist1.size()!=checklist2.size()){
			return false;
		}
		
		for(int i=0;i<checklist1.size();i++){
			Element checklistitem1=(Element)checklist1.get(i);
			Element checklistitem2=(Element)checklist2.get(i);
		//	System.out.println(count++);
			
		//	if(count==2){
		//		System.out.println("break");
		//	}
			
			
			if(!checklistitem1.getName().equals(checklistitem2.getName())){
				return false;
				
			}
			if(!checklistitem1.getText().equals(checklistitem2.getText())){
				if(!checklistitem1.getText().startsWith("\n")){
				    return false;
			    }
			}
			//attribution should be added in the future ifr needed
			
			boolean checkresult=checkchildrenequal(checklistitem1,checklistitem2);
			
			if(checkresult==false){
				return false;
			}		
		}
		return true;
	}
*/
	


	
	
	
	

}



class SortRules implements Comparator<String>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(String a, String b)
    {
        String[] aAr=a.split(";");
        String[] bAr=b.split(";");
    	if(Integer.valueOf(aAr[0])>Integer.valueOf(bAr[0])){
    		return 1;
    	}else if(Integer.valueOf(aAr[0])<Integer.valueOf(bAr[0])){
    		return -1;
    	}
    	
    	if(Integer.valueOf(aAr[0])==Integer.valueOf(bAr[0])){
    		if(Integer.valueOf(aAr[1])>Integer.valueOf(bAr[1])){
    			return 1;
    		}else if(Integer.valueOf(aAr[0])<Integer.valueOf(bAr[0])){
    			return -1;
    		}
    	}
    	
    	
    	return 0;
    }
}

	//String test=xmlOutput.outputString(rootresult.getChildren());
	//System.out.println(test);
	
	/*
	
	
	
	List list=currentpath.getChildren();
	for(int i=0;i<list.size();i++){
		Element currentpathitem=(Element) list.get(i);
		String classid=currentpathitem.getText();
	    String subviewid=currentpathitem.getAttributeValue("subviewid");
	
	    ////here should add the fail case
	    
	    ////take care the last item		    
	}
	
	
	/// read the result.xml/////
	SAXBuilder sb = new SAXBuilder();//generate the builder
	String path="/home/yu/repeatbugreport/result.xml";
	Document docresult=sb.build(new FileInputStream(path));//read the file
	Element rootresult=docresult.getRootElement();
	if(rootresult.getChild("fail")!=null){
		//do some other things
		
		
	}else{
		List liststep=rootresult.getChildren();
		Element laststep=(Element)liststep.get(list.size()-1);
		//modify the received first			
		//laststep.getChild("Viewgroup");
		Element Easyoperate=new Element("Easyoperate");
		laststep.addContent(Easyoperate);
		//////
		int count=1;
		List viewgrouplist=laststep.getChild("Viewgroup").getChildren();//Viewgroup's children
		String currentID;
		for(int i=0;i<liststep.size();i++){
			int countinlist=0;//in one viewgroup
			
			Element viewgroupitem=(Element)viewgrouplist.get(i); //may have more than one list view
			List subviewitem=viewgroupitem.getChildren();
			
			Element itemsactivity;
			////////////
			for(int j;j<subviewitem.size();j++){
				Element textviewitem=(Element) subviewitem.get(j);//one textview
				if(Integer.valueOf(textviewitem.getAttributeValue("id"))==countinlist){
					itemsactivity.addContent("action");//I assume here there is only one listview, if not there are twol list(1)
					
					
					
					
				}else{
					itemsactivity=new Element("Itemsactivity");
					Easyoperate.addContent(itemsactivity);
					//set id
					Attribute itemsactivityid=new Attribute("id",String.valueOf(++count));
					//++count;
				}
				
				
			}
			
			
			
			
			
			
			
			
			
			
			currentID=(Element)liststep.get(i);
			
			
			Element Iditem=new Element("ID"+Integer.toString(count++));
			
			
			
			
			
		}
		
		
		
		
		/// check the laststep is existed in the current record or not.
	    checklaststep(laststep,rootrecord.getChild("classitem"));
		
		
		
		
		
		Element classitem=rootrecord.getChild("Classitem");			
		laststep.addContent(classitem);//add the last currentscreen into the record.
		
		
	//	list.size()-1;
		
		*/
		
		
	
	
	
	
	
	//recordadd(recorddoc,rootrecord);
	
	
	
	
	
	
	//for()
	
	
	
	
	
	
// 	XMLOutputter xmlOutput = new XMLOutputter();
//	xmlOutput.setFormat(Format.getPrettyFormat());
//	xmlOutput.output(recorddoc, new FileWriter("/home/yu/repeatbugreport/record.xml"));
	
	
	
	/*
	
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
	step.addContent(viewgroup);
	step.addContent(nogroup);
	
	XMLOutputter xmlOutput = new XMLOutputter();

	// display nice nice
	xmlOutput.setFormat(Format.getPrettyFormat());
	xmlOutput.output(doc, new FileWriter("/sdcard/result.xml"));
	
	*/
	
	
