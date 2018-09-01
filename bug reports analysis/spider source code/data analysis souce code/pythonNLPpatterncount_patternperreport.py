'''
Created on Jun 12, 2017

@author: yu
'''
from spacy.en import English
#import strand
import re
from xml.dom.minidom import Document
from xml.dom import minidom
import os
from itertools import product, imap
#from imghdr import what
'''
def computesimularity(word1,word2,model):
    vertorword1=model.wv[word1]
    vertorword2=model.wv[word2]
    above=0
    below1=0
    below2=0
    vectorlen=len(vertorword1)
    for index in range(0,vectorlen-1):        
        above+=vertorword1[index]*vertorword2[index]
        below1+=(vertorword1[index])**2
        below2+=(vertorword2[index])**2
    below=math.sqrt(below1)*math.sqrt(below2)
    simularity=above/below
    return simularity
#os.system("ls")
'''
'''
model = Word2Vec.load('wiki.en.word2vec.model')
print model.wv.similarity('puzzle','medium')
print computesimularity('hard', 'hard', model)
'''
#blob = TextBlob("I love your dog")
#print blob.tags


clickcount= {'CR1':0, 'CR2':0, 'CR3':0, 'CR4':0, 'CR5':0, 'CR6':0, 'CR7':0}
editcount={'TR1':0, 'TR2':0, 'TR3':0, 'TR4':0, 'TR5':0, 'TR6':0, 'TR7':0, 'TR8':0, 'TR9':0, 'TR10':0, 'TR11':0, 'TR12':0, 'TR13':0, 'TR14':0}

class strandform:


    def __init__(self):
        self.type=None
        self.step=None
        self.clickwhere=list()
        self.clicktype=None
        self.clicktimes=False
        self.typewhat=list()
        self.digittypewhat=list()
        self.typewhere=list()
        self.digittypewhere=list()
        self.typetimes=False
        self.createwhat=list()
        self.sentence=list()
        self.sentenceid=None
        
    def printform(self):
        if(self.type==0):
            print("click type")
        if(self.type==1):
            print("click click")
        if(self.type==2):
            print("click create")
        return 2
#grammar = nltk.parse_cfg()
def main():
    #################xml part##################
    ########write##################
    '''
    file_handle = open("filename.xml","wb")
    doc =Document()
    root=doc.createElement("some_tag")
    root.setAttribute("id",'myIdvalue')
    
    tempChild=doc.createElement("aa")
    root.appendChild(tempChild)
    
    nodeText=doc.createTextNode("asdwqe")
    tempChild.appendChild(nodeText)
    
    doc.appendChild(root)
    doc.writexml(file_handle)
    file_handle.close()
    
    #######read#############
    doc=minidom.parse("filename.xml")
    name = doc.getElementsByTagName("aa")[0]
    print(name.firstChild.data)
    '''
    
    
    tableclick = {'click':0, 'choose':0, 'select':0, 'launch':0, 'pick':0, 'tap':0, 'open':0, 'press':0, 'go':0, 'select':0}
    tableinput = {'input':0, 'enter':0, 'type':0, 'insert':0, 'fill':0, 'change':0, 'write':0, 'set':0, 'put':0, 'add':0}
    tableback = {'ok': 0, 'cancel':0, 'done':0, 'back': 0, 'zoom': 0, 'swipe':0, 'rotat':0}
    tablespecial={'apostrophe':0 ,'comma' :0, 'colon' :0, 'semicolon' :0, 'hyphen' :0, 'parentheses' :0, 'quote' :0, 'space' :0}
    tablespecial2={'"\'':0 ,'",' :0, '":' :0, '";' :0, '"-' :0, '"(' :0, '"\\"' :0, '" "' :0}
    
    
    
    global clickcount
    global editcount
    
    
    specialbugreportCount=0
    backword=0
    
    github=True
    allused=False
    
    issueId=0
    nlp = English()
    
    inputcount=0
    
    if(allused):
        for x in os.listdir('./allused'):
            
            setclick=set()
            setinput=set()
            setback=set()
            setspecial=set()
            setspecial2=set()
            
            
            if(x.startswith("39.")):
            
                lineList=[]
                
                fp=os.path.join('./allused',x)
            
                if os.path.isfile(fp):
                    with open(fp, 'r') as fc:
                        for line in fc.readlines():
                            line=line.lower()
                            for actionWord in tableclick.keys():
                                if(actionWord in line):
                                    setclick.add(actionWord)
                                    
                                    
                                    #tableclick[actionWord]=tableclick[actionWord]+1
                                    
                            for actionWord in tableinput.keys():
                                if(actionWord in line):
                                    setinput.add(actionWord)
                                    
                                    
                                    #tableinput[actionWord]=tableinput[actionWord]+1
                                    
                            for actionWord in tableback.keys():
                                if(actionWord in line):
                                    setback.add(actionWord)
                                    
                                    
                                    #tableback[actionWord]=tableback[actionWord]+1       
                                    
                            for actionWord in tablespecial.keys():
                                if(actionWord in line):
                                    setspecial.add(actionWord)
                                    #tablespecial[actionWord]=tablespecial[actionWord]+1  
                                    
                            for actionWord in tablespecial2.keys():
                                if(actionWord in line):
                                    setspecial2.add(actionWord)
                                    
                                    #tablespecial2[actionWord]=tablespecial2[actionWord]+1        
                                    
                                    
                            #["click","choose","select","lanuch","pick","tap","open","press","go","select"]  
                            try:
                                lineList.append(unicode(line))
                                #pickupcrital(nlp,lineList, "./", issueId)  
                                
                                
                            except UnicodeDecodeError:
                                print "got exception"
                            
                            print(line)    
                pickupcrital(nlp,lineList, "./", issueId)  
            
            for actionWord in setclick:
                tableclick[actionWord]=tableclick[actionWord]+1
                
            for actionWord in setinput:
                tableinput[actionWord]=tableinput[actionWord]+1
                
            for actionWord in setback:
                tableback[actionWord]=tableback[actionWord]+1
                
            for actionWord in setspecial:
                tablespecial[actionWord]=tablespecial[actionWord]+1 
                
            for actionWord in setspecial2:
                tablespecial2[actionWord]=tablespecial2[actionWord]+1  
                
                
            
            
            
    
    
    list=["select","input","enter","type","insert","fill","change","write","set","put","add"]
    
    
    for x in os.listdir('./result/allstepscrash'):
        
        
        setclick=set()
        setinput=set()
        setback=set()
        setspecial=set()
        setspecial2=set()
        
        if(allused):
            break
        
        
        lineList=[]
        #print("aa")
        fp=os.path.join('./result/allstepscrash',x)
        
        if os.path.isfile(fp):
            with open(fp, 'r') as fc:

                for line in fc.readlines():
                        
                        
                        if(github):
                        
                            # this is for github
                            if('<li>' in line or '<p>' in line):
                                #if('<p>' in line):
                                    line=line.lower()
                                    line=line.replace('<li>','')
                                    line=line.replace('<ol>','')
                                    line=line.replace('<h2>','')
                                    line=line.replace('</h2>','')
                                    line=line.replace('</li>','')
                                    line=line.replace('</ol>','')
                                    line=line.replace('<ul>','')
                                    line=line.replace('<p>','')
                                    line=line.replace('</p>','')
                                    line=line.replace('<br>','')
                                    
                                    line=line.lower()
                                    for actionWord in tableclick.keys():
                                        if(actionWord in line):
                                            setclick.add(actionWord)
                                            
                                            
                                            #tableclick[actionWord]=tableclick[actionWord]+1
                                            
                                    for actionWord in tableinput.keys():
                                        if(actionWord in line):
                                            setinput.add(actionWord)
                                            
                                            
                                            #tableinput[actionWord]=tableinput[actionWord]+1
                                            
                                    for actionWord in tableback.keys():
                                        if(actionWord in line):
                                            setback.add(actionWord)
                                            
                                            
                                            #tableback[actionWord]=tableback[actionWord]+1       
                                            
                                    for actionWord in tablespecial.keys():
                                        if(actionWord in line):
                                            setspecial.add(actionWord)
                                            #tablespecial[actionWord]=tablespecial[actionWord]+1  
                                            
                                    for actionWord in tablespecial2.keys():
                                        if(actionWord in line):
                                            setspecial2.add(actionWord)      
                                            
                                            
                                    #["click","choose","select","lanuch","pick","tap","open","press","go","select"]  
                                    try:
                                        lineList.append(unicode(line))
                                        #pickupcrital(nlp,lineList, "./", issueId)  
                                        
                                        
                                    except UnicodeDecodeError:
                                        print "got exception"
                                    
                                    print(line)    
                                
                        else:        
                            
                                #this is from gogole code
                                line=line.lower()
                                line=line.replace('</p>','')
                                line=line.replace('<p>','')
                                line=line.replace('</b>',"")
                                line=line.replace('<b>',"")
                                
                                line=line.lower()
                                for actionWord in tableclick.keys():
                                    if(actionWord in line):
                                        setclick.add(actionWord)
                                        
                                        
                                        #tableclick[actionWord]=tableclick[actionWord]+1
                                        
                                for actionWord in tableinput.keys():
                                    if(actionWord in line):
                                        setinput.add(actionWord)
                                        
                                        
                                        #tableinput[actionWord]=tableinput[actionWord]+1
                                        
                                for actionWord in tableback.keys():
                                    if(actionWord in line):
                                        setback.add(actionWord)
                                        
                                        
                                        #tableback[actionWord]=tableback[actionWord]+1       
                                        
                                for actionWord in tablespecial.keys():
                                    if(actionWord in line):
                                        setspecial.add(actionWord)
                                        #tablespecial[actionWord]=tablespecial[actionWord]+1  
                                        
                                for actionWord in tablespecial2.keys():
                                    if(actionWord in line):
                                        setspecial2.add(actionWord)        
                                
                                            
                                    #["click","choose","select","lanuch","pick","tap","open","press","go","select"]  
                                try:
                                    lineList.append(unicode(line))
                                except UnicodeDecodeError:
                                    print "got exception"
                                    
                                print(line)
                        
                        
                        if any(word in line.lower() for word in list):
                            inputcount+=1
                            break
        
        
        if len(setspecial)!=0:
            specialbugreportCount+=1
            
            
        if len(setspecial2)!=0:    
            specialbugreportCount+=1
        
        
        
        if len(setback)!=0:
            backword+=1
        
        
        for actionWord in setclick:
            tableclick[actionWord]=tableclick[actionWord]+1
                
        for actionWord in setinput:
            tableinput[actionWord]=tableinput[actionWord]+1
            
        for actionWord in setback:
            tableback[actionWord]=tableback[actionWord]+1
            
        for actionWord in setspecial:
            tablespecial[actionWord]=tablespecial[actionWord]+1 
            
        for actionWord in setspecial2:
            tablespecial2[actionWord]=tablespecial2[actionWord]+1  
                            
                        
        clickpatternset=set()
        editpatternset=set()
        
        
                                
        if(allused):
            pickupcrital(nlp,lineList, "./allused", issueId, clickpatternset, editpatternset)  
        
        
        else:
            pickupcrital(nlp,lineList, "./", issueId, clickpatternset, editpatternset)  

        
        
        
        for patternword in clickpatternset:
            clickcount[patternword]=clickcount[patternword]+1
            
        for patternword in editpatternset:
            editcount[patternword]=editcount[patternword]+1
            
        
        


        #pickupcrital(nlp,lineList, "./", issueId)                       
        #tag=False
        #if os.path.isfile(fp):
    
    
    
    
    
    
    #########################spacy
    SUBJECTS = ["nsubj","nsubjpass"] ## add or delete more as you wish
    OBJECTS = ["dobj", "pobj", "dobj"] ## add or delete more as you wish
    PREDICATE=["VERB"]
    
    #address="/home/yu/repeatbugreport"
    #address="."
    
    
    nlp = English()
    #fileObject=open(address+'/nlpBugReport/bugreport')
    
    
    #lineList=[]
    
    #for line in fileObject:
    #    lineList.append(unicode(line))
        
    
    
    #allText=fileObject.read()
    #uniallText=unicode(allText)
    
    
    
    #pickupcrital(nlp,lineList, address)

    
    #pickupcrital(nlp,u" During multiple input of identical values e.g. 10 km (by day) and 10 litres some calculations fail. E.g. first calculates correcty 100 l/km but then next two results are \"infinity l / 100 km. Plotting will death lock program then and you have to restart device.")
    #pickupcrital(nlp,u"Cancel it.")
    #pickupcrital(nlp, u"A game is clicked. Select a level. Long-click a game. The context menu pops up. Select \"Edit note\". A dialog pops up. Cancel it. Long-click the same game. The context menu pops up again.")

    
    
    #pickupcrital(nlp,u"e.g. input 10km (by day) and 10L some calculations fail. E.g. first calculates correcty 100 l/km but then next two results are infinity l / 100 km. Plotting will death lock program then and you have to restart device.")
    #pickupcrital(nlp,u"If you enter a weight with 3 digits the labeling on the weight axis will omit the first digit. E.g.: 104 kg will be displayed as 04 kg. I click the button aaa.")
    #pickupcrital(nlp,u"When I create an entry for a purchase, the autocomplete list shows up, per usual. ")
    #pickupcrital(nlp,u"pick an option in the drop-down box on the left panel. Then rotate the screen to horizontal. Enter a number in the name field.")
    
    #Select a game. Input a girl on it. Rotate your phone, switch to another orientation.")
    
    #pickupcrital(nlp,u"I input \"cute apple\" with a \" car\" for many times")
    #pickupcrital(nlp,u"I enter a value 10 km to the km field")
    
    #pickupcrital(nlp,u"the input is 10 on km"
    #pickupcrital(nlp,u"I take \"do it\" as input on person")

    apples=nlp(u"refuel")
    orange=nlp(u"good")
    
    print(apples.similarity(orange))
    
    
    
    for actionWord in tableclick.keys():
        print(actionWord+" "+str(tableclick[actionWord]))
        #print(tableclick[actionWord])
    
    for actionWord in tableinput.keys():
        print(actionWord+" "+str(tableinput[actionWord]))
        
        
    for actionWord in tableback.keys():
        print(actionWord+" "+str(tableback[actionWord]))
        
    for actionWord in tablespecial.keys():
        print(actionWord+" "+str(tablespecial[actionWord]))
        
    for actionWord in tablespecial2.keys():
        print(actionWord+" "+str(tablespecial2[actionWord]))
        #print(tableinput[actionWord])
    
    totaltype=0
    for type in clickcount.keys():
        print(type+" "+str(clickcount[type]))
        if clickcount[type]!=0:
            totaltype+=1
        
    for type in editcount.keys():
        print(type+" "+str(editcount[type]))
        if editcount[type]!=0:
            totaltype+=1
        #print(tableinput[actionWord])
        
        #print("totaltype"+" "+str(totaltype))
        
        
    print("inputcount "+str(inputcount))
    
    
    print("specialbugreportCount"+str(specialbugreportCount))
    
    print("backword"+str(backword))
    

def pd(a,b):
    return list(imap('_'.join, product(a, [b])))

def pickupcrital(nlp,lineList, address, issueId, clickpatternset, editpatternset):
    #global editcount
    #global clickcount

    ############all cases
    file_allcase = open("./allused/allcases.xml","wb")
    doc_allcase =Document()
    root_allcase=doc_allcase.createElement("Allcases")
    
    
    
    ##########################
    

    recorder=None #record last sentence
    steplist=list()
    step=0
    sentid=0    
    
    for sentence in lineList:        
        for sent in nlp(sentence).sents:
            
            ########all cases
            newsent=doc_allcase.createElement("Sent"+str(sentid))
            root_allcase.appendChild(newsent)
            
            sentenceid=doc_allcase.createElement("Sentid")
            nodetext=doc_allcase.createTextNode(str(sentid))
            sentenceid.appendChild(nodetext)
            newsent.appendChild(sentenceid)
            
            sentid+=1
            
            sentele=doc_allcase.createElement("sentence")
            newsent.appendChild(sentele)
            
            nounlist=doc_allcase.createElement("nounlist")
            newsent.appendChild(nounlist)
            
            
            #########
            
            stringnounphrasemap={}
            normalnounphrasemap={}
            
            
            
            doc=nlp(unicode(sent))
            recordstring=list()
            recordindexlist=list()
            stringboolean=False
            for word in doc:
                if(word.text=="\"" and stringboolean==False):
                    stringboolean=True
                    continue
                if(word.text=="\"" and stringboolean==True):
                    stringboolean=False
                    for index in recordindexlist:
                        stringnounphrasemap[index]=recordstring
                    recordstring=list()#initial
                    recordindexlist=list()
                    continue
                
                if(stringboolean==True):
                    recordstring.append(word.text)
                    recordindexlist.append(word.i)
                    
            for np in doc.noun_chunks:
                recordstring=list()
                recordindexlist=list()
                i=0
                for word in np:
                    if(word.pos_==u"PUNCT"):
                        continue
                    i+=1
                if(i>1):
                    for word in np:
                        if(not word.pos_==u"PUNCT"):                    
                            recordstring.append(word.lemma_)
                            recordindexlist.append(word.i)
                    for index in recordindexlist:
                        normalnounphrasemap[index]=recordstring
                
            #test codes
            #for k,v in normalnounphrasemap.items():
            #    print(k,v)
                
            newinfo = None
            
            nounk=0
            kk=0        
            for word in doc:
                #############another example case########2018.2.21#####
                '''
                if word.text in ["e.g.","E.g.","E.G.","example"]:
                    newinfo = strandform()
                    newinfo.type="input"
                    newinfo.step=step
                    for everyword in doc:
                        newinfo.sentence.append(everyword.lemma_)
                    newinfo.sentenceid=sentid-1
                    step+=1
                
                    examplemet=False;
                    for perword in doc:
                        if perword.text in ["e.g.","E.g.","E.G.","example"]:
                            examplemet=True
                        if examplemet & (perword.pos_ in ["NOUN"]):
                            newinfo.typewhat.append(perword.text)
                    steplist.append(newinfo)        
                '''           
                            
                
                ###########above all case##############################
                newitem=doc_allcase.createElement("item"+str(kk))
                noteText=doc_allcase.createTextNode(word.lemma_)
                newitem.appendChild(noteText)
                sentele.appendChild(newitem)
                
                if word.pos_ in ["NOUN"]:
                    nounitem=doc_allcase.createElement("item"+str(nounk))
                    nounk+=1
                    noteText=doc_allcase.createTextNode(word.lemma_)
                    nounitem.appendChild(noteText)
                    nounlist.appendChild(nounitem)
                    
                    if word.head.pos_ in ["VERB"]:
                        nounitem.setAttribute("verb",word.head.lemma_)
                    
                
                ############example case#########################
                if not recorder == None:
                    if recorder.type=="input":
                
                        examplecase=False
                        for eachword in doc:
                            if(eachword.lemma_ in ["e.g.","example","say"]):
                                examplecase=True
                                break
                                
                        if examplecase==True:
                            editpatternset.add("TR7")
                            
                             
                            #editcount["TR7"]=editcount["TR7"]+1
                            
                                          
                            if (word.pos_ in ["NUM"] and word.is_digit==False):
                                editpatternset.add("TR11")
                                
                                
                                
                                #editcount["TR11"]=editcount["TR11"]+1
                                #print("test1")
                                #print(word.text)
                                recorder.digittypewhat.append(pd(re.findall ('[\d ]+', word.lemma_),"TR11"))#digit
                                recorder.digittypewhere.append(pd(re.findall ('[^\d ]+', word.lemma_),"TR11"))#string
                                
                                #print re.findall ('[\d ]+', word.text);
                                #print re.findall ('[^\d ]+', word.text);
                        
                            if (word.pos_ in ["NUM"] and word.is_digit==True):
                                editpatternset.add("TR12")
                                
                                #editcount["TR12"]=editcount["TR12"]+1
                                recorder.digittypewhat.insert(0, word.lemma_+"TR12")
                                #recorder.digittypewhere.append(word.head.text)
                                #for child in word.children:        
                                recorder.digittypewhere.insert(0, doc.__getitem__(word.i+1).lemma_+"TR12")#get next item
                                
                                #print("test2")
                                #print(word.text)
                                #print word.head.text
                
            #########################click case####################
                if(word.lemma_ in ["click","choose","select","lanuch","pick","tap","open","press","go","select"]):
                    newinfo = strandform()
                    newinfo.type="click"
                    newinfo.step=step
                    
                    for eachword in doc:
                        newinfo.sentence.append(eachword.lemma_)
                    
                    newinfo.sentenceid=sentid-1
                    
                    #newinfo.sentence=sent
                    
                    step+=1
                    
                    ####8/8/2017 fix the if part
                    if word.dep_ in ["compound"]:
                        clickpatternset.add("CR7")
                        
                        
                        #clickcount["CR7"]=clickcount["CR7"]+1
                        if stringnounphrasemap.has_key(word.head.i):
                            newinfo.clickwhere.append(pd(stringnounphrasemap.get(word.head.i), " CR7"))
                        if normalnounphrasemap.has_key(word.head.i):
                            clickpatternset.add("CR4")
                            
                            #clickcount["CR4"]=clickcount["CR4"]+1
                            newinfo.clickwhere.append(pd(normalnounphrasemap.get(word.head.i), " CR7"))
                        newinfo.clickwhere.append(word.head.lemma_+"CR7")
                        
                    elif word.dep_ in ["amod"]:
                        clickpatternset.add("CR7")
                        
                        #clickcount["CR7"]=clickcount["CR7"]+1
                        if stringnounphrasemap.has_key(word.head.i):
                            newinfo.clickwhere.append(pd(stringnounphrasemap.get(word.head.i), " CR7"))
                        if normalnounphrasemap.has_key(word.head.i):
                            clickpatternset.add("CR4")
                            
                            #clickcount["CR4"]=clickcount["CR4"]+1
                            newinfo.clickwhere.append(pd(normalnounphrasemap.get(word.head.i), " CR7"))
                        newinfo.clickwhere.append(word.head.lemma_+"CR7")
                        
                        
                    else:
                        for child in word.subtree:
                            if(child.dep_ in ["nsubjpass","dobj","pobj","oprd","intj"]):#injt gantanci  #oprd is a ind of object
                                
                                if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                    #clickcount["CR4"]=clickcount["CR4"]+1
                                    clickpatternset.add("CR4")
                                    
                                    newinfo.clickwhere.append(pd(stringnounphrasemap.get(child.i),"CR4"))
                                
                                if(child.dep_ in ["dobj"]):
                                    clickpatternset.add("CR1")
                                    #clickcount["CR1"]=clickcount["CR1"]+1
                                    newinfo.clickwhere.append(child.lemma_+" CR1")
                                    
                                    #if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                    #    newinfo.clickwhere.append(list(imap('_'.join, product(normalnounphrasemap.get(child.i), " CR1"))))
                                    
                                if(child.dep_ in ["nsubjpass"]):
                                    
                                    #clickcount["CR2"]=clickcount["CR2"]+1
                                    clickpatternset.add("CR2")
                                    newinfo.clickwhere.append(child.lemma_+" CR2")
                                    
                                if(child.dep_ in ["pobj"]):
                                    
                                    clickpatternset.add("CR3")
                                    
                                    
                                    #clickcount["CR3"]=clickcount["CR3"]+1
                                    
                                    newinfo.clickwhere.append(child.lemma_+" CR3")
                                                     
                                
                                #if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                #    newinfo.clickwhere.append(stringnounphrasemap.get(child.i))
                                            
        
                                if normalnounphrasemap.has_key(child.i):
                                    newinfo.clickwhere.append(pd(normalnounphrasemap.get(child.i), " CR4"))
                                    
                                    clickpatternset.add("CR4")
                                    #clickcount["CR4"]=clickcount["CR4"]+1
                                
                                #newinfo.clickwhere.append(child.lemma_)
                            
                            
                    for subtr in word.subtree:
                        if(subtr.lemma_ in [u"multiple",u"twice"] or subtr.text in [u"times"]):
                            newinfo.clicktimes=True                
                            clickpatternset.add("CR5")
                            #clickcount["CR5"]=clickcount["CR5"]+1
                        
                        if(subtr.lemma_ in ["long"]):
                            newinfo.clicktype="long"
                            clickpatternset.add("CR6")
                            
                            #clickcount["CR6"]=clickcount["CR6"]+1
                            
    
                    steplist.append(newinfo)
            ######################################################
                if(word.lemma_ in ["cancel"]):
                    newinfo = strandform()
                    newinfo.type="cancel"
                    newinfo.step=step
                    for eachword in doc:
                        newinfo.sentence.append(eachword.lemma_)
                        
                    newinfo.sentenceid=sentid-1
                    
                    step+=1
                    steplist.append(newinfo)
            
            
            
            #######################create case#####################
                if(word.lemma_ in ["create"]):
                    newinfo = strandform()
                    newinfo.type="create"
                    newinfo.step=step
                    for eachword in doc:
                        newinfo.sentence.append(eachword.lemma_)
                    
                    newinfo.sentenceid=sentid-1
                    
                    step+=1
                    
                    ####8/8/2017 fix the if part
                    if word.dep_ in ["compound"]:
                        if stringnounphrasemap.has_key(word.head.i):
                            newinfo.createwhat.append(stringnounphrasemap.get(word.head.i))
                        if normalnounphrasemap.has_key(word.head.i):
                            newinfo.createwhat.append(normalnounphrasemap.get(word.head.i))
                        newinfo.createwhat.append(word.head.lemma_)
                        
                    elif word.dep_ in ["amod"]:
                        if stringnounphrasemap.has_key(word.head.i):
                            newinfo.createwhat.append(stringnounphrasemap.get(word.head.i))
                        if normalnounphrasemap.has_key(word.head.i):
                            newinfo.createwhat.append(normalnounphrasemap.get(word.head.i))
                        newinfo.createwhat.append(word.head.lemma_)
                    
                    else:
                        
                        for child in word.children:
                            if(child.dep_ in ["nsubjpass","dobj"]):
                                if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                    newinfo.createwhat.append(stringnounphrasemap.get(child.i))
                                            
        
                                if normalnounphrasemap.has_key(child.i):
                                    newinfo.createwhat.append(normalnounphrasemap.get(child.i))
                                
                                newinfo.createwhat.append(child.lemma_)
                                #subtreelist=list()
                                for sub in child.children:
                                    if(sub.dep_ in ["prep"]):
                                        for subsub in sub.children:
                                            if(subsub.dep_ in ["pobj"]):
                                                newinfo.createwhat.append(subsub.lemma_)
    
                    steplist.append(newinfo)
            #####################rotate###########################
                if(word.lemma_ in ["rotate", "orientation"]):
                    newinfo = strandform()
                    newinfo.type="rotate"
                    newinfo.step=step
                    for eachword in doc:
                        newinfo.sentence.append(eachword.lemma_)
                        
                    newinfo.sentenceid=sentid-1    
                        
                    step+=1
                    steplist.append(newinfo)
                      
                
                
            ###########################left out case#############################
                if(word.lemma_ in ["leave", "left"]):
                    if(doc.__getitem__(kk+1).lemma_ in ["out"]):
                        
                        newinfo = strandform()
                        newinfo.type="input"
                        newinfo.step=step
                        newinfo.sentenceid=sentid-1
                        for everyword in doc:
                            newinfo.sentence.append(everyword.lemma_)
                        
                        step+=1
                        
                        newinfo.typewhat.append("")
                        #newinfo.typewhere.append(stringnounphrasemap.get(child.i))

                        for wordchild in word.subtree:
                            #if (wordchild.dep_ in ["dobj"]):
                            newinfo.typewhere.append(wordchild.lemma_)
                                
                                
                        newinfo.digittypewhat.append("")
                        #newinfo.typewhere.append(stringnounphrasemap.get(child.i))

                        for wordchild in word.subtree:
                            #if (wordchild.dep_ in ["dobj"]):
                            newinfo.digittypewhere.append(wordchild.lemma_)
                                #newinfo.typewhere.append(stringnounphrasemap.get(wordchild))

                #    for (child.dep_ in [ ]):
            
            
                        steplist.append(newinfo)
                
            ###########################input case###################3
                if(word.lemma_ in ["input","enter","type","insert","fill","change","write","set","put","add"]):   
                    
                    
                    #editcount["TR13"]=editcount["TR13"]+1
                    
                    
                    #print("word is an input")
                    newinfo = strandform()
                    newinfo.type="input"
                    newinfo.step=step
                    
                    for everyword in doc:
                        newinfo.sentence.append(everyword.lemma_)
                        #newinfo.sentence.append(stringnounphrasemap.get(everyword.lemma_))
                    
                    newinfo.sentenceid=sentid-1
                    
                    step+=1
                    for subtr in word.subtree:
                        if(subtr.lemma_ in [u"multiple",u"twice"] or subtr.text in [u"times"]):
                                        newinfo.typetimes=True                
                    #######################"input is NOUN"
                    if(word.pos_ in [u"NOUN"]):
                        rootofdoc=None
                        for oneword in doc:#find the root
                            if(oneword.dep_== u'ROOT'):
                                rootofdoc=oneword
                        
                        for child in doc:                        
                            if (child.dep_ in [u"dobj",u"obj",u"attr",u"appos",u"nmod"]):
                        
                                for prepchild in doc:
                                    if(prepchild.dep_ in [u"prep"] and prepchild.lemma_ in [u"on", u"in",u"to"]):
                                        editpatternset.add("TR1")
                                        
                                        #editcount["TR1"]=editcount["TR1"]+1
                                        
                                        if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                            newinfo.typewhat.append(pd(stringnounphrasemap.get(child.i), " TR1"))
                                        
                                        
                                        
                                        if normalnounphrasemap.has_key(child.i):
                                            editpatternset.add("TR13")
                                            
                                            #editcount["TR13"]=editcount["TR13"]+1
                                            newinfo.typewhat.append(pd(normalnounphrasemap.get(child.i), " TR1"))
                                        
                                        #28/7/2017 added
                                        sublist=list()
                                        for sub in child.subtree:
                                            if sub.pos_ in [u"NUM"]:
                                                
                                                editpatternset.add("TR1")
                                                
                                                #sublist.append(sub.lemma_+"TR1")
                                        if(len(sublist)>0):
                                            newinfo.typewhat.append(sublist)
                                        #end
                                        
                                        newinfo.typewhat.append(child.lemma_+"TR1")
                                        
                                        for onobjchild in prepchild.children:
                                            if(onobjchild.dep_ in [u"pobj"]):
                                                
                                                
                                                #editcount["TR1p"]=editcount["TR1p"]-1
                                                #editcount["TR1"]=editcount["TR1"]+1

                                                
                                                
                                                if stringnounphrasemap.has_key(onobjchild.i):
                                                    newinfo.typewhere.append(pd(stringnounphrasemap.get(onobjchild.i),"TR1"))
                                                    
                                                if normalnounphrasemap.has_key(onobjchild.i):
                                                    editpatternset.add("TR13")
                                                    
                                                    #editcount["TR13"]=editcount["TR13"]+1
                                                    newinfo.typewhere.append(pd(normalnounphrasemap.get(onobjchild.i),"TR1"))
                                                
                                                #28/7/2017 added
                                                sublist=list()
                                                for sub in onobjchild.subtree:
                                                    if sub.pos_ in [u"NUM"]:
                                                        sublist.append(sub.lemma_)
                                                if(len(sublist)>0):
                                                    newinfo.typewhere.append(sublist)
                                                #end
                                                
                                                newinfo.typewhere.append(onobjchild.lemma_)
                                                
                                    elif(prepchild.dep_ in [u"prep"] and prepchild.lemma_ in [u"with"]):   
                                        
                                        editpatternset.add("TR2")
                                        #editcount["TR2"]=editcount["TR2"]+1
                                        
                                        if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                            newinfo.typewhere.append(pd(stringnounphrasemap.get(child.i), " TR2"))
                                        
                                        if normalnounphrasemap.has_key(child.i):
                                            
                                            
                                            editpatternset.add("TR13")
                                            #editcount["TR13"]=editcount["TR13"]+1
                                            #newinfo.typewhere.append(pd(stringnounphrasemap.get(child.i), " TR2"))should be added as all use
                                        
                                        #28/7/2017 added
                                        sublist=list()
                                        for sub in child.subtree:
                                            if sub.pos_ in [u"NUM"]:
                                                
                                                editpatternset.add("TR2")
                                                
                                                #sublist.append(sub.lemma_+"TR2")
                                        if(len(sublist)>0):
                                            newinfo.typewhere.append(sublist)
                                        #end
                                        
                                        newinfo.typewhere.append(child.lemma_+"TR2")
                                        
                                        for onobjchild in prepchild.children:
                                            if(onobjchild.dep_ in [u"pobj"]):
                                                
                                                #
                                                # editcount["TR2p"]=editcount["TR2p"]-1
                                                # editcount["TR2"]=editcount["TR2"]+1
                                                
                                                if stringnounphrasemap.has_key(onobjchild.i):
                                                    newinfo.typewhat.append(pd(stringnounphrasemap.get(onobjchild.i),"TR2"))
                                                    
                                                if normalnounphrasemap.has_key(onobjchild.i):
                                                    
                                                    
                                                    editpatternset.add("TR13")
                                                    #editcount["TR13"]=editcount["TR13"]+1
                                                    newinfo.typewhat.append(pd(normalnounphrasemap.get(onobjchild.i),"TR2"))
                                                
                                                
                                                #28/7/2017 added
                                                sublist=list()
                                                for sub in onobjchild.subtree:
                                                    if sub.pos_ in [u"NUM"]:
                                                        sublist.append(sub.lemma_+"TR2")
                                                if(len(sublist)>0):
                                                    newinfo.typewhat.append(sublist)
                                                #end
                                                
                                                newinfo.typewhat.append(onobjchild.lemma_+"TR2")
                            
                            
                             
                    #######################the input is not a NOUN#############                   
                    for child in word.children:
                        #########################passive
                        if(child.dep_ in [u"nsubjpass"]):#passive case
                            #for prepchild in word.children:# notice that the word.children
                            for prepchild in doc:
                                if(prepchild.dep_ in [u"prep"] and prepchild.lemma_ in [u"on", u"in",u"to"]):
    
                                    if word.lemma_ in ["change"]:
                                        
                                        editpatternset.add("TR3")
                                        
                                        #editcount["TR3"]=editcount["TR3"]+1
                                    else:
                                        
                                        
                                        editpatternset.add("TR1")
                                        #editcount["TR1"]=editcount["TR1"]+1
                                        
    
                                    if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                        if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                            newinfo.typewhere.append(pd(stringnounphrasemap.get(child.i),"TR3"));
                                            #
                                            #editcount["TR3"]=editcount["TR3"]+1
                                            
                                        else:    
                                            #
                                            #editcount["TR1"]=editcount["TR1"]+1
                                            newinfo.typewhat.append(pd(stringnounphrasemap.get(child.i),"TR1"))
                                    
                                    if normalnounphrasemap.has_key(child.i):
                                        
                                        editpatternset.add("TR13")
                                        
                                        #editcount["TR13"]=editcount["TR13"]+1
                                        if word.lemma_ in ["change"]:
                                            newinfo.typewhere.append(pd(normalnounphrasemap.get(child.i),"TR3"))
                                            #
                                            #editcount["TR3"]=editcount["TR3"]+1
                                        else:
                                            newinfo.typewhat.append(pd(normalnounphrasemap.get(child.i),"TR1"))
                                            #
                                            #editcount["TR1"]=editcount["TR1"]+1
                                    
                                    #28/7/2017 added
                                    sublist=list()
                                    for sub in child.subtree:
                                        if sub.pos_ in [u"NUM"]:
                                            sublist.append(sub.lemma_)
                                    if(len(sublist)>0):
                                        newinfo.typewhat.append(sublist)
                                    #end
                                    
                                    
                                    newinfo.typewhat.append(child.lemma_)
                                    
                                    for onobjchild in prepchild.children:
                                        if(onobjchild.dep_ in [u"pobj"]):
                                            if stringnounphrasemap.has_key(onobjchild.i):
                                                if word.lemma_ in ["change"]:
                                                    newinfo.typewhat.append(pd(stringnounphrasemap.get(onobjchild.i),"TR3"))
                                                else:
                                                    newinfo.typewhere.append(pd(stringnounphrasemap.get(onobjchild.i),"TR1"))
    
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                #editcount["TR13"]=editcount["TR13"]+1
                                                
                                                editpatternset.add("TR13")
                                                
                                                if word.lemma_ in ["change"]:
                                                    newinfo.typewhat.append(pd(normalnounphrasemap.get(onobjchild.i),"TR3"))
                                                else:
                                                    newinfo.typewhere.append(pd(normalnounphrasemap.get(onobjchild.i),"TR1"))
                                            
                                            #28/7/2017 added
                                            sublist=list()
                                            for sub in onobjchild.subtree:
                                                if sub.pos_ in [u"NUM"]:
                                                    sublist.append(sub.lemma_)
                                                if(len(sublist)>0):
                                                    newinfo.typewhere.append(sublist)
                                            #end
                                            
                                            
                                            newinfo.typewhere.append(onobjchild.lemma_)
                                            
                                elif(prepchild.dep_ in [u"prep"] and prepchild.lemma_ in [u"with"]):
                                    
                                    #editcount["TR2"]=editcount["TR2"]+1
                                    
                                    editpatternset.add("TR2")
                                    
                                    if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                        newinfo.typewhere.append(pd(stringnounphrasemap.get(child.i),"TR2"))
                                    
                                    if normalnounphrasemap.has_key(child.i):
                                        #editcount["TR13"]=editcount["TR13"]+1
                                        
                                        editpatternset.add("TR13")
                                        newinfo.typewhere.append(pd(normalnounphrasemap.get(child.i),"TR2"))
                                    
                                    #28/7/2017 added
                                    sublist=list()
                                    for sub in child.subtree:
                                        if sub.pos_ in [u"NUM"]:
                                            sublist.append(sub.lemma_)
                                        if(len(sublist)>0):
                                            newinfo.typewhere.append(sublist)
                                    #end
                                    
                                    newinfo.typewhere.append(child.lemma_)
                                    
                                    for onobjchild in prepchild.children:
                                        if(onobjchild.dep_ in [u"pobj"]):
                                            if stringnounphrasemap.has_key(onobjchild.i):
                                                newinfo.typewhat.append(pd(stringnounphrasemap.get(onobjchild.i),"TR2"))
                                                
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                #editcount["TR13"]=editcount["TR13"]+1
                                                editpatternset.add("TR13")
                                                
                                                newinfo.typewhat.append(pd(normalnounphrasemap.get(onobjchild.i),"TR2"))
                                            
                                            #28/7/2017 added
                                            sublist=list()
                                            for sub in onobjchild.subtree:
                                                if sub.pos_ in [u"NUM"]:
                                                    sublist.append(sub.lemma_+"TR2")
                                                if(len(sublist)>0):
                                                    newinfo.typewhat.append(sublist)
                                            #end
                                            
                                            newinfo.typewhat.append(onobjchild.lemma_+"TR2")
                        #################################active
                        if(child.dep_ in [u"pobj",u"dobj",u"appos"]):
                            for prepchild in doc:
                                if(prepchild.dep_ in [u"prep"] and prepchild.lemma_ in [u"on", u"in",u"to",u"as"]):
                                    if word.lemma_ in ["change"]:#
                                        editpatternset.add("TR3")
                                        
                                        #editcount["TR3"]=editcount["TR3"]+1
                                    else:
                                        
                                        
                                        editpatternset.add("TR1")
                                        #editcount["TR1"]=editcount["TR1"]+1
                                    
                                    
    
                                    if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                        if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                            newinfo.typewhere.append(pd(stringnounphrasemap.get(child.i),"TR3"))
                                            #
                                            #editcount["TR3"]=editcount["TR3"]+1
                                        else:
                                            newinfo.typewhat.append(pd(stringnounphrasemap.get(child.i),"TR1"))
                                            #
                                            #editcount["TR1"]=editcount["TR1"]+1
    
                                            
                                    if normalnounphrasemap.has_key(child.i):
                                        
                                        editpatternset.add("TR13")
                                        #editcount["TR13"]=editcount["TR13"]+1
                                        if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                            newinfo.typewhere.append(pd(normalnounphrasemap.get(child.i),"TR3"))
                                            #
                                            #editcount["TR3"]=editcount["TR3"]+1
                                        else:
                                            newinfo.typewhat.append(pd(normalnounphrasemap.get(child.i),"TR1"))
                                            #
                                            #editcount["TR1"]=editcount["TR1"]+1
                                    
                                    #28/7/2017 added
                                    sublist=list()
                                    for sub in child.subtree:
                                        if sub.pos_ in [u"NUM"]:
                                            sublist.append(sub.lemma_)
                                    if(len(sublist)>0):
                                        newinfo.typewhat.append(sublist)
                                    #end
                                    
                                    if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                        newinfo.typewhere.append(child.lemma_+"TR3")
                                        #global editcount
                                        #editcount["TR3"]=editcount["TR3"]+1
                                    else:
                                        newinfo.typewhat.append(child.lemma_+"TR1")
                                        #global editcount
                                        #editcount["TR1"]=editcount["TR1"]+1
                                    
                                    for onobjchild in prepchild.children:
                                        if(onobjchild.dep_ in [u"pobj"]):
                                            #if word.lemma_ in ["change"]:#
                                            #editcount["TR3"]=editcount["TR3"]+1
                                            #else:
                                            #editcount["TR11"]=editcount["TR11"]+1
                                            
                                            
                                            
                                            if stringnounphrasemap.has_key(onobjchild.i):
                                                if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                                    newinfo.typewhat.append(pd(stringnounphrasemap.get(onobjchild.i),"TR3"))
                                                    #global editcount
                                                    #editcount["TR3"]=editcount["TR3"]+1
                                                    
                                                else:
                                                    newinfo.typewhere.append(pd(stringnounphrasemap.get(onobjchild.i),"TR1"))
                                                    #global editcount
                                                    #editcount["TR1"]=editcount["TR1"]+1
    
                                                    
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                editpatternset.add("TR13")
                                                
                                                
                                                
                                                #editcount["TR13"]=editcount["TR13"]+1
                                                if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                                    newinfo.typewhat.append(pd(normalnounphrasemap.get(onobjchild.i),"TR3"))
                                                    #global editcount
                                                    #editcount["TR3"]=editcount["TR3"]+1
                                                    
                                                else:
                                                    newinfo.typewhere.append(pd(normalnounphrasemap.get(onobjchild.i),"TR1"))
                                                    #global editcount
                                                    #editcount["TR1"]=editcount["TR1"]+1
    
                                            #28/7/2017 added
                                            sublist=list()
                                            for sub in onobjchild.subtree:
                                                if sub.pos_ in [u"NUM"]:
                                                    sublist.append(sub.lemma_)
                                            if(len(sublist)>0):
                                                newinfo.typewhere.append(sublist)
                                            #end
                                            
                                            
                                            if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                                newinfo.typewhat.append(onobjchild.lemma_+"TR3")
                                                #global editcount
                                                #editcount["TR3"]=editcount["TR3"]+1
                                                
                                                
                                            else:
                                                newinfo.typewhere.append(onobjchild.lemma_+"TR1")
                                                #global editcount
                                                #editcount["TR1"]=editcount["TR1"]+1
                                            #newinfo.typewhere.append(onobjchild.lemma_)
                                            
                                elif(prepchild.dep_ in [u"prep"] and prepchild.lemma_ in [u"with"]): 
                                    
                                    editpatternset.add("TR2")
                                    
                                    #editcount["TR2"]=editcount["TR2"]+1  
                                    if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                        newinfo.typewhere.append(pd(stringnounphrasemap.get(child.i),"TR2"))
                                        #
                                        #editcount["TR2"]=editcount["TR2"]+1
                                    
                                    if normalnounphrasemap.has_key(child.i):
                                        editpatternset.add("TR13")
                                        
                                        
                                        #editcount["TR13"]=editcount["TR13"]+1
                                        newinfo.typewhere.append(pd(normalnounphrasemap.get(child.i),"TR2"))
                                        #global editcount
                                        #editcount["TR2"]=editcount["TR2"]+1
                                    
                                    #28/7/2017 added
                                    sublist=list()
                                    for sub in child.subtree:
                                        if sub.pos_ in [u"NUM"]:
                                            sublist.append(sub.lemma_+"TR2")
                                        if(len(sublist)>0):
                                            newinfo.typewhere.append(sublist)
                                    #end
                                    
                                    
                                    newinfo.typewhere.append(child.lemma_)
                                    
                                    for onobjchild in prepchild.children:
                                        if(onobjchild.dep_ in [u"pobj"]):
                                            if stringnounphrasemap.has_key(onobjchild.i):
                                                newinfo.typewhat.insert(0,pd(stringnounphrasemap.get(onobjchild.i),"TR2"));
                                                #newinfo.typewhat.append(stringnounphrasemap.get(onobjchild.i))
                                                
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                
                                                editpatternset.add("TR13")
                                                #editcount["TR13"]=editcount["TR13"]+1
                                                #newinfo.typewhat.append(normalnounphrasemap.get(onobjchild.i))
                                                newinfo.typewhat.insert(0,pd(normalnounphrasemap.get(onobjchild.i),"TR2"));
                                            
                                            #28/7/2017 added
                                            sublist=list()
                                            for sub in onobjchild.subtree:
                                                if sub.pos_ in [u"NUM"]:
                                                    sublist.append(sub.lemma_+"TR2")
                                                if(len(sublist)>0):
                                                    newinfo.typewhat.append(sublist)
                                            #end
                                            newinfo.typewhat.append(onobjchild.lemma_+"TR2")
                                else:##2018.2.19
                                    for onobjchild in prepchild.children:
                                        if(onobjchild.dep_ in [u"dobj",u"pobj"]):
                                            
                                            #if word.lemma_ in ["change"]:#
                                            #editcount["TR3"]=editcount["TR3"]+1
                                            #else:
                                            #editcount["TR11"]=editcount["TR11"]+1
                                            
                                            
                                            if stringnounphrasemap.has_key(onobjchild.i):
                                                if word.lemma_ not in ["change"]:#change has a inverse grammar than fill
                                                    newinfo.typewhat.append(pd(stringnounphrasemap.get(onobjchild.i),"TR3"))
                                                
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                
                                                editpatternset.add("TR13")
                                                
                                                #editcount["TR13"]=editcount["TR13"]+1
                                                if word.lemma_ not in ["change"]:#change has a inverse grammar than fill
                                                    #editcount["TR14"]=editcount["TR14"]+1
                                                    
                                                    editpatternset.add("TR14")
                                                    
                                                    newinfo.typewhat.append(pd(normalnounphrasemap.get(onobjchild.i),"TR14"))          
            
                    
        
         
                    
                    if not newinfo==None:
                        if newinfo.type=="input":
                            for oneword in doc:
                                ################digit case########################
                                if (oneword.pos_ in ["NUM"] and oneword.is_digit==False):
                                    
                                        editpatternset.add("TR4")
                                    
                                        #editcount["TR4"]=editcount["TR4"]+1
                                        
                                        #print("test1")
                                        #print(word.text)
                                        newinfo.digittypewhat.append(pd(re.findall ('[\d ]+', oneword.lemma_),"TR4"))#digit
                                        newinfo.digittypewhere.append(pd(re.findall ('[^\d ]+', oneword.lemma_),"TR4"))#string
                                        
                                        #print re.findall ('[\d ]+', word.text);
                                        #print re.findall ('[^\d ]+', word.text);
                                
                                if (oneword.pos_ in ["NUM"] and oneword.is_digit==True):
                                    
                                        editpatternset.add("TR5")
                                    
                                        #editcount["TR5"]=editcount["TR5"]+1
                                        newinfo.digittypewhat.append(oneword.lemma_+"TR5")
                                        #recorder.digittypewhere.append(word.head.text)
                                        if(len(doc)>oneword.i+1):
                                            newinfo.digittypewhere.append(doc.__getitem__(oneword.i+1).lemma_+"TR5")#get next item
                                        #for child in word.children:        
                                        #    newinfo.digittypewhere.append(child.text)
                                #####################################33
                        steplist.append(newinfo)
                    
                    
                    examplemet=False
                    for perword in doc:
                        if perword.text in ["e.g.","E.g.","E.G.","example","say"]:
                            
                            
                            editpatternset.add("TR6")
                            #editcount["TR6"]=editcount["TR6"]+1
                            examplemet=True
                        
                        if examplemet & (perword.pos_ in ["NUM"]):
                            
                            
                            editpatternset.add("TR9")
                            #editcount["TR9"]=editcount["TR9"]+1
                            newinfo.digittypewhat.insert(0,perword.text+"TR9")
                            break;
                        
                        if examplemet & (perword.pos_ in ["NOUN"]):
                            
                            
                            editpatternset.add("TR10")
                            
                            #editcount["TR10"]=editcount["TR10"]+1
                            newinfo.typewhat.insert(0,perword.text+"TR10")
                            break;
                                
                        
                    tag=False
                    for oneword in doc:
                        if oneword.text in ["e.g.","E.g.","E.G.","example","say"]:
                            tag=True
                            break
                    if tag==False:
                        recorder=newinfo
                kk+=1
                
                
                
                            ###########################left out case#############################
                if(word.lemma_ in ["leave"]):#, "left"
                    #editcount["TR8"]=editcount["TR8"]+1
                    
                    editpatternset.add("TR8")
                    
                    if(doc.__getitem__(kk+1).lemma_ in ["out"]):
                        #editcount["TR8"]=editcount["TR8"]+1  
                        
                        newinfo = strandform()
                        newinfo.type="input"
                        newinfo.step=step
                        newinfo.sentenceid=sentid-1
                        for everyword in doc:
                            newinfo.sentence.append(everyword.lemma_+"TR8")
                        
                        step+=1
                        
                        newinfo.typewhat.append("")
                        #newinfo.typewhere.append(stringnounphrasemap.get(child.i))

                        for wordchild in word.subtree:
                            #if (wordchild.dep_ in ["dobj"]):
                            newinfo.typewhere.append(wordchild.lemma_+"TR8")
                                
                                
                        newinfo.digittypewhat.append("")
                        #newinfo.typewhere.append(stringnounphrasemap.get(child.i))

                        for wordchild in word.subtree:
                            #if (wordchild.dep_ in ["dobj"]):
                            newinfo.digittypewhere.append(wordchild.lemma_+"TR8")
                                #newinfo.typewhere.append(stringnounphrasemap.get(wordchild))

                #    for (child.dep_ in [ ]):
            
            
                        steplist.append(newinfo)
                
                
                
                
                
                   
    ###################33xml###############33
    file_name=open("./allused/nlp.xml","wb")
    doc=Document()
    root=doc.createElement("Steps")
    
    for step in steplist:
        child=doc.createElement("Step"+str(step.step))
        root.appendChild(child)
        
        
        sentenceele=doc.createElement("sentence")
        k=0
        for sten in step.sentence:
                newitem=doc.createElement("item"+str(k))
                k+=1
                nodeText=doc.createTextNode(sten)
                newitem.appendChild(nodeText)
                sentenceele.appendChild(newitem)
        child.appendChild(sentenceele)
        
        idinfo=doc.createElement("sentenceid")
        nodeText=doc.createTextNode(str(step.sentenceid))
        idinfo.appendChild(nodeText)
        child.appendChild(idinfo)
        
        #######################
        
        ################333input 
        if(step.type=="input"):
            type=doc.createElement("type")
            nodeText=doc.createTextNode("input")
            type.appendChild(nodeText)
            
            typetimes=doc.createElement("inputtimes")
            nodeText=doc.createTextNode(str(step.typetimes))
            typetimes.appendChild(nodeText)
            
            typewhere=doc.createElement("typewhere")
            i=0
            for where in step.typewhere:
                ID=doc.createElement("ID"+str(i))
                i=i+1
                typewhere.appendChild(ID)
                
                if not isinstance(where,list):
                    oneitem=doc.createElement("item0")
                    nodeText=doc.createTextNode(unicode(where))
                    oneitem.appendChild(nodeText)
                    ID.appendChild(oneitem)
                else:
                    k=0
                    for itemwhere in where:
                        newitem=doc.createElement("item"+str(k))
                        k+=1
                        nodeText=doc.createTextNode(unicode(itemwhere))
                        newitem.appendChild(nodeText)
                        ID.appendChild(newitem)


            typewhat=doc.createElement("typewhat")
            i=0
            for where in step.typewhat:
                ID=doc.createElement("ID"+str(i))
                i=i+1
                typewhat.appendChild(ID)
                
                if not isinstance(where,list):
                    oneitem=doc.createElement("item0")
                    nodeText=doc.createTextNode(unicode(where))
                    oneitem.appendChild(nodeText)
                    ID.appendChild(oneitem)
                else:                   
                    k=0
                    for itemwhere in where:
                        newitem=doc.createElement("item"+str(k))
                        k+=1
                        nodeText=doc.createTextNode(unicode(itemwhere))
                        newitem.appendChild(nodeText)
                        ID.appendChild(newitem)
                        
                        
            digittypewhere=doc.createElement("digittypewhere")
            i=0
            for where in step.digittypewhere:
                ID=doc.createElement("ID"+str(i))
                i=i+1
                digittypewhere.appendChild(ID)
                
                if not isinstance(where,list):
                    oneitem=doc.createElement("item0")
                    nodeText=doc.createTextNode(unicode(where))
                    oneitem.appendChild(nodeText)
                    ID.appendChild(oneitem)
                else:                   
                    k=0
                    for itemwhere in where:
                        newitem=doc.createElement("item"+str(k))
                        k+=1
                        nodeText=doc.createTextNode(unicode(itemwhere))
                        newitem.appendChild(nodeText)
                        ID.appendChild(newitem)



            digittypewhat=doc.createElement("digittypewhat")
            i=0
            for where in step.digittypewhat:
                ID=doc.createElement("ID"+str(i))
                i=i+1
                digittypewhat.appendChild(ID)
                
                if not isinstance(where,list):
                    oneitem=doc.createElement("item0")
                    nodeText=doc.createTextNode(unicode(where))
                    oneitem.appendChild(nodeText)
                    ID.appendChild(oneitem)
                else:                   
                    k=0
                    for itemwhere in where:
                        newitem=doc.createElement("item"+str(k))
                        k+=1
                        nodeText=doc.createTextNode(unicode(itemwhere))
                        newitem.appendChild(nodeText)
                        ID.appendChild(newitem)

            
            
            
            nodeText=doc.createTextNode(str(step.typetimes))
            typetimes.appendChild(nodeText)
            


            child.appendChild(type)
            child.appendChild(typetimes)
            child.appendChild(typewhere)
            child.appendChild(typewhat)
            child.appendChild(digittypewhere)
            child.appendChild(digittypewhat)

        ###############33click
        if(step.type=="click"):
            type=doc.createElement("type")
            nodeText=doc.createTextNode("click")
            type.appendChild(nodeText)
            
            clicktimes=doc.createElement("clicktimes")
            nodeText=doc.createTextNode(str(step.clicktimes))
            clicktimes.appendChild(nodeText)

            clicktype=doc.createElement("clicktype")
            nodeText=doc.createTextNode(str(step.clicktype))
            clicktype.appendChild(nodeText)
        
        
            clickwhere=doc.createElement("clickwhere")
            i=0
            for where in step.clickwhere:
                ID=doc.createElement("ID"+str(i))
                i=i+1
                clickwhere.appendChild(ID)
                
                if not isinstance(where,list):
                    oneitem=doc.createElement("item0")
                    nodeText=doc.createTextNode(unicode(where))
                    oneitem.appendChild(nodeText)
                    ID.appendChild(oneitem)
                else:                   
                    k=0
                    for itemwhere in where:
                        newitem=doc.createElement("item"+str(k))
                        k+=1
                        nodeText=doc.createTextNode(unicode(itemwhere))
                        newitem.appendChild(nodeText)
                        ID.appendChild(newitem)
            

            child.appendChild(type)
            child.appendChild(clicktimes)
            child.appendChild(clicktype)
            child.appendChild(clickwhere)
        ################cancel
        if(step.type=="cancel"):
            type=doc.createElement("type")
            nodeText=doc.createTextNode("cancel")
            type.appendChild(nodeText)
        
            child.appendChild(type)
            
            
        ################rotate
        if(step.type=="rotate"):
            type=doc.createElement("type")
            nodeText=doc.createTextNode("rotate")
            type.appendChild(nodeText)
        
            child.appendChild(type)
            
        ################crate
        if(step.type=="create"):
            type=doc.createElement("type")
            nodeText=doc.createTextNode("create")
            type.appendChild(nodeText)
            
            createwhat=doc.createElement("createwhat")
            i=0
            for where in step.createwhat:
                ID=doc.createElement("ID"+str(i))
                i=i+1
                createwhat.appendChild(ID)
                
                if not isinstance(where,list):
                    oneitem=doc.createElement("item0")
                    nodeText=doc.createTextNode(unicode(where))
                    oneitem.appendChild(nodeText)
                    ID.appendChild(oneitem)
                else:                   
                    k=0
                    for itemwhere in where:
                        newitem=doc.createElement("item"+str(k))
                        k+=1
                        nodeText=doc.createTextNode(unicode(itemwhere))
                        newitem.appendChild(nodeText)
                        ID.appendChild(newitem)
            
            child.appendChild(type)
            child.appendChild(createwhat)
        
    
    doc.appendChild(root)
    doc.writexml(file_name)
    file_name.close()
    
    ################allcase
    doc_allcase.appendChild(root_allcase)
    doc_allcase.writexml(file_allcase)
    file_allcase.close()
    
    
    
        
    '''
            ########write##################
    file_handle = open("filename.xml","wb")
    doc =Document()
    root=doc.createElement("some_tag")
    root.setAttribute("id",'myIdvalue')
    
    tempChild=doc.createElement("aa")
    root.appendChild(tempChild)
    
    nodeText=doc.createTextNode("asdwqe")
    tempChild.appendChild(nodeText)
    
    doc.appendChild(root)
    doc.writexml(file_handle)
    file_handle.close()
    '''

    
    for step in steplist:
        print ""
        print ""
        print ""
        print "newinf,type"
        print step.type
        print "newinf,step"
        print step.step
        if(step.type=="input"):
            print "newinf,type more times"
            print step.typetimes                       
            print "newinfo,where"
            print step.typewhere
            print "newinfo,what"
            print step.typewhat
            print "digit,where"
            print step.digittypewhere
            print "digit,what"
            print step.digittypewhat
        if(step.type=="click"):
            print "click more times"
            print step.clicktimes
            print "click where"
            print step.clickwhere
            print "click one or not"
            print step.clicktype
        if(step.type=="create"):
            print "create whatt"
            print step.createwhat
        if(step.type=="rotate"):
            print "rotate type"
        
    
    for sentence in lineList:  
        for sent in nlp(sentence).sents:
            print sent
            doc=nlp(unicode(sent))
            
            for word in doc:
                print(word.head)
                
                if(word.lemma_ in ["input","enter","type"]):
                    
                    
                    print("word is an input")
                    
                    
                if(word.lemma_ in ["tap","select","click"]):
                    print("word is a click")
                if(word.lemma_ in ["create"]):
                    print("word is a create")
                if(word.lemma_ in ["fail"]):
                    print("word is a fail")    
                
                #for subword in word.conjuncts:
                #    print "conjuncts"
                #    print subword.text
                #print word.n_lefts
                #print word.i
                print(word.text, word.lemma, word.lemma_, word.tag, word.tag_, word.pos, word.pos_, word.dep_, word.dep,word.like_num,word.ent_type_,word.is_digit)
            print("one sentence over")
    
        '''
        for np in doc.noun_chunks:
            print np
            for asd in np:
                print asd.i
        '''
    '''
    
    plants={}
    plants["aa"]="asd"
    print plants["aa"]
    print plants.get("aa")
    print plants.keys()
    print plants.values()
    for k,v in plants.items():
        print(k,v)
    print plants.has_key("aa")  
    '''
    ##############################33
    
    
    
    

    
    
    
    
    
    
    
if __name__ == '__main__':
    main()         

    