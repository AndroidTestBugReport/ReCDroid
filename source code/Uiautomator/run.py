'''
Created on Jun 12, 2017

@author: yu
'''
#from uiautomator import device as d
from uiautomator import Device
import time
import sys

from xml.dom.minidom import Document
from xml.dom import minidom
import commands
import os

def main():
    
    
    
    
    port='5554'
    
    d = Device('emulator-'+port)
    
    #d.dump("/home/yu/repeatbugreport/middleResults/result.xml")

    #this is for flash
    d(resourceId="com.example.terin.asu_flashcardapp:id/email").set_text("lu@gml.com")
    d(resourceId="com.example.terin.asu_flashcardapp:id/password").set_text("12331986")
    d(resourceId="com.example.terin.asu_flashcardapp:id/email_sign_in_button").click();
    
    time.sleep(4)




    '''
    #this is for yastore
    d(resourceId="android:id/text1").click();
    
    d(resourceId="com.github.yeriomin.yalpstore:id/email").set_text("lunarlightgg@gmail.com")
    d(resourceId="com.github.yeriomin.yalpstore:id/password").set_text("lunar1986")
    d(resourceId="com.github.yeriomin.yalpstore:id/button_ok").click();
    time.sleep(4)
    '''

    '''this is for mifos
    d(resourceId="com.mifos.mifosxdroid:id/et_username").set_text("mifos")    
    d(resourceId="com.mifos.mifosxdroid:id/et_password").set_text("password") 
    
    d(text="LOGIN").click();
    
    
    time.sleep(2)
    '''



    #d(text="ALLOW").click();

    #d.press.back()
    #d.press.home()
    d.orientation = "n"
    
    d.screen.on()
    
    #address='/home/yu/repeatbugreport'
    address='.'
    
    doc=minidom.parse(address+'/middleResults/run.xml')
    root=doc.documentElement
    
    for  step in root.childNodes:
        if step.nodeType == minidom.Node.ELEMENT_NODE:##minidom is a little bit ugly
            
            
            textid=""
            textX=""
            textY=""
            textStr=""
            
            actionTypes=step.getElementsByTagName("Actiontype")
            if actionTypes[0].firstChild.nodeValue=="Noaction":
                continue
            if actionTypes[0].firstChild.nodeValue=="back":
                d.press.back()
                continue
            
            if actionTypes[0].firstChild.nodeValue=="enter":
                d.press("enter")
                time.sleep(2)
                continue
            
            
            ##########get the source id
            ownText=step.getElementsByTagName("ownText")
            
            if ownText.length!=0: 
                textid=ownText[0].firstChild.nodeValue;
            
            #########get the x-position and y-position
            xPositions=step.getElementsByTagName("xposition")
            yPositions=step.getElementsByTagName("yposition")
                
                
            if xPositions.length!=0:
                textX=xPositions[0].firstChild.nodeValue
                textY=yPositions[0].firstChild.nodeValue
            
            
            #########get the click text
            clickText=step.getElementsByTagName("clickText") 
            
            if clickText.length!=0:
                if clickText[0].childNodes.length!=0:
                    textStr=clickText[0].firstChild.nodeValue
            
            
            #print(actionTypes[0].firstChild.nodeValue)
            if actionTypes[0].firstChild.nodeValue=="EditText":
                
                
                typewhat=step.getElementsByTagName("typeWhat")
                textTypeWhat=typewhat[0].firstChild.nodeValue;
                
                if textTypeWhat=="default":
                    continue
                
                
                #write text
                d(resourceId=textid).set_text(textTypeWhat)
                #d.press("enter")

                
                
                #print(textid)
                #print(textTypeWhat)
                
                #d(resourceId="com.newsblur:id/login_password").set_text("asd")
                #d.wait.idle()
                time.sleep(0.5)
                     
            else:
            #elif actionTypes[0].firstChild.nodeValue=="ClickList":
                #########get the x-position and y-position
                
                clicktype=step.getElementsByTagName("clicktype")
                textclicktype=clicktype[0].firstChild.nodeValue;
                
                
                try:
                    if textclicktype=="short":
                        if not textid=="":
                            d(resourceId=textid).click();
                            
                        if not textStr=="":
                            d(text=textStr).click();
                        else:
                            d.click(int(textX),int(textY))
                            
                    elif textclicktype=="long":
                        if not textid=="":
                            d(resourceId=textid).long_click();
                            
                        elif not textStr=="":
                            d(text=textStr).long_click();
                        else:
                            d.long_click(int(textX),int(textY))
                #d.wait.update()
                except Exception as err:
                    break;
                    
                
                
            time.sleep(2)
                
            
            
            #d.dump("/home/yu/repeatbugreport/middleResults/result.xml")
            
            
    d.dump(address+"/middleResults/result.xml")
    
    cmd="adb -s emulator-"+port+" shell dumpsys window windows | grep -E 'mFocusedApp'"
    packInfo=commands.getstatusoutput(cmd)
    
    fo = open(address+"/middleResults/packInfo", "w")
    fo.write(str(packInfo))
    fo.close()
    
    if root.getAttribute("Rotate")=="True":
                
            d.orientation = "l"
            time.sleep(0.5)
            d.orientation = "n"
            time.sleep(0.5)
    #address=".."
    
    
if __name__ == '__main__':
    main()         

    