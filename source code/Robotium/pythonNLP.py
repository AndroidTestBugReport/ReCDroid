'''
Created on Jun 12, 2017

@author: yu
'''
from spacy.en import English
#import strand
import re
from xml.dom.minidom import Document
from xml.dom import minidom

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
    #########################spacy
    SUBJECTS = ["nsubj","nsubjpass"] ## add or delete more as you wish
    OBJECTS = ["dobj", "pobj", "dobj"] ## add or delete more as you wish
    PREDICATE=["VERB"]
    
    #address="/home/yu/repeatbugreport"
    address="."
    
    
    nlp = English()
    fileObject=open(address+'/nlpBugReport/bugreport')
    
    
    lineList=[]
    
    for line in fileObject:
        lineList.append(unicode(line))
        
    
    
    #allText=fileObject.read()
    #uniallText=unicode(allText)
    
    
    
    pickupcrital(nlp,lineList, address)

    
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

def pickupcrital(nlp,lineList, address):
    ############all cases
    file_allcase = open(address+"/middleResults/allcases.xml","wb")
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
                            if (word.pos_ in ["NUM"] and word.is_digit==False):
                                #print("test1")
                                #print(word.text)
                                recorder.digittypewhat.append(re.findall ('[\d ]+', word.lemma_))#digit
                                recorder.digittypewhere.append(re.findall ('[^\d ]+', word.lemma_))#string
                                
                                #print re.findall ('[\d ]+', word.text);
                                #print re.findall ('[^\d ]+', word.text);
                        
                            if (word.pos_ in ["NUM"] and word.is_digit==True):
                                recorder.digittypewhat.insert(0, word.lemma_)
                                #recorder.digittypewhere.append(word.head.text)
                                #for child in word.children:        
                                recorder.digittypewhere.insert(0, doc.__getitem__(word.i+1))#get next item
                                
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
                        if stringnounphrasemap.has_key(word.head.i):
                            newinfo.clickwhere.append(stringnounphrasemap.get(word.head.i))
                        if normalnounphrasemap.has_key(word.head.i):
                            newinfo.clickwhere.append(normalnounphrasemap.get(word.head.i))
                        newinfo.clickwhere.append(word.head.lemma_)
                        
                    elif word.dep_ in ["amod"]:
                        if stringnounphrasemap.has_key(word.head.i):
                            newinfo.clickwhere.append(stringnounphrasemap.get(word.head.i))
                        if normalnounphrasemap.has_key(word.head.i):
                            newinfo.clickwhere.append(normalnounphrasemap.get(word.head.i))
                        newinfo.clickwhere.append(word.head.lemma_)
                        
                    else:
                        for child in word.subtree:
                            if(child.dep_ in ["nsubjpass","dobj","pobj","oprd","intj"]):#injt gantanci  #oprd is a ind of object
                                if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                    newinfo.clickwhere.append(stringnounphrasemap.get(child.i))
                                            
        
                                if normalnounphrasemap.has_key(child.i):
                                    newinfo.clickwhere.append(normalnounphrasemap.get(child.i))
                                
                                newinfo.clickwhere.append(child.lemma_)
                            
                            
                    for subtr in word.subtree:
                        if(subtr.lemma_ in [u"multiple",u"twice"] or subtr.text in [u"times"]):
                            newinfo.clicktimes=True                
                        
                        
                        if(subtr.lemma_ in ["long"]):
                            newinfo.clicktype="long"
                            
    
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
        
                                        
                                        if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                            newinfo.typewhat.append(stringnounphrasemap.get(child.i))
                                        
                                        
                                        
                                        if normalnounphrasemap.has_key(child.i):
                                            newinfo.typewhat.append(normalnounphrasemap.get(child.i))
                                        
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
                                                    newinfo.typewhere.append(stringnounphrasemap.get(onobjchild.i))
                                                    
                                                if normalnounphrasemap.has_key(onobjchild.i):
                                                    newinfo.typewhere.append(normalnounphrasemap.get(onobjchild.i))
                                                
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
                                        if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                            newinfo.typewhere.append(stringnounphrasemap.get(child.i))
                                        
                                        if normalnounphrasemap.has_key(child.i):
                                            newinfo.typewhere.append(normalnounphrasemap.get(child.i))
                                        
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
                                                    newinfo.typewhat.append(stringnounphrasemap.get(onobjchild.i))
                                                    
                                                if normalnounphrasemap.has_key(onobjchild.i):
                                                    newinfo.typewhat.append(normalnounphrasemap.get(onobjchild.i))
                                                
                                                
                                                #28/7/2017 added
                                                sublist=list()
                                                for sub in onobjchild.subtree:
                                                    if sub.pos_ in [u"NUM"]:
                                                        sublist.append(sub.lemma_)
                                                if(len(sublist)>0):
                                                    newinfo.typewhat.append(sublist)
                                                #end
                                                
                                                newinfo.typewhat.append(onobjchild.lemma_)
                            
                            
                             
                    #######################the input is not a NOUN#############                   
                    for child in word.children:
                        #########################passive
                        if(child.dep_ in [u"nsubjpass"]):#passive case
                            #for prepchild in word.children:# notice that the word.children
                            for prepchild in doc:
                                if(prepchild.dep_ in [u"prep"] and prepchild.lemma_ in [u"on", u"in",u"to"]):
    
                                    if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                        if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                            newinfo.typewhere.append(stringnounphrasemap.get(child.i));
                                        else:    
                                            newinfo.typewhat.append(stringnounphrasemap.get(child.i))
                                    
                                    if normalnounphrasemap.has_key(child.i):
                                        if word.lemma_ in ["change"]:
                                            newinfo.typewhere.append(normalnounphrasemap.get(child.i))
                                        else:
                                            newinfo.typewhat.append(normalnounphrasemap.get(child.i))
                                    
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
                                                    newinfo.typewhat.append(stringnounphrasemap.get(onobjchild.i))
                                                else:
                                                    newinfo.typewhere.append(stringnounphrasemap.get(onobjchild.i))
    
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                if word.lemma_ in ["change"]:
                                                    newinfo.typewhat.append(normalnounphrasemap.get(onobjchild.i))
                                                else:
                                                    newinfo.typewhere.append(normalnounphrasemap.get(onobjchild.i))
                                            
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
                                    if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                        newinfo.typewhere.append(stringnounphrasemap.get(child.i))
                                    
                                    if normalnounphrasemap.has_key(child.i):
                                        newinfo.typewhere.append(normalnounphrasemap.get(child.i))
                                    
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
                                                newinfo.typewhat.append(stringnounphrasemap.get(onobjchild.i))
                                                
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                newinfo.typewhat.append(normalnounphrasemap.get(onobjchild.i))
                                            
                                            #28/7/2017 added
                                            sublist=list()
                                            for sub in onobjchild.subtree:
                                                if sub.pos_ in [u"NUM"]:
                                                    sublist.append(sub.lemma_)
                                                if(len(sublist)>0):
                                                    newinfo.typewhat.append(sublist)
                                            #end
                                            
                                            newinfo.typewhat.append(onobjchild.lemma_)
                        #################################active
                        if(child.dep_ in [u"pobj",u"dobj",u"appos"]):
                            for prepchild in doc:
                                if(prepchild.dep_ in [u"prep"] and prepchild.lemma_ in [u"on", u"in",u"to",u"as"]):
    
                                    if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                        if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                            newinfo.typewhere.append(stringnounphrasemap.get(child.i))
                                        else:
                                            newinfo.typewhat.append(stringnounphrasemap.get(child.i))
    
                                            
                                    if normalnounphrasemap.has_key(child.i):
                                        if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                            newinfo.typewhere.append(normalnounphrasemap.get(child.i))
                                        else:
                                            newinfo.typewhat.append(normalnounphrasemap.get(child.i))
                                    
                                    #28/7/2017 added
                                    sublist=list()
                                    for sub in child.subtree:
                                        if sub.pos_ in [u"NUM"]:
                                            sublist.append(sub.lemma_)
                                    if(len(sublist)>0):
                                        newinfo.typewhat.append(sublist)
                                    #end
                                    
                                    if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                        newinfo.typewhere.append(child.lemma_)
                                    else:
                                        newinfo.typewhat.append(child.lemma_)
                                    
                                    for onobjchild in prepchild.children:
                                        if(onobjchild.dep_ in [u"pobj"]):
                                            if stringnounphrasemap.has_key(onobjchild.i):
                                                if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                                    newinfo.typewhat.append(stringnounphrasemap.get(onobjchild.i))
                                                else:
                                                    newinfo.typewhere.append(stringnounphrasemap.get(onobjchild.i))
    
                                                    
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                                    newinfo.typewhat.append(normalnounphrasemap.get(onobjchild.i))
                                                else:
                                                    newinfo.typewhere.append(normalnounphrasemap.get(onobjchild.i))
    
                                            #28/7/2017 added
                                            sublist=list()
                                            for sub in onobjchild.subtree:
                                                if sub.pos_ in [u"NUM"]:
                                                    sublist.append(sub.lemma_)
                                            if(len(sublist)>0):
                                                newinfo.typewhere.append(sublist)
                                            #end
                                            
                                            
                                            if word.lemma_ in ["change"]:#change has a inverse grammar than fill
                                                newinfo.typewhat.append(onobjchild.lemma_)
                                            else:
                                                newinfo.typewhere.append(onobjchild.lemma_)
                                            #newinfo.typewhere.append(onobjchild.lemma_)
                                            
                                elif(prepchild.dep_ in [u"prep"] and prepchild.lemma_ in [u"with"]):   
                                    if stringnounphrasemap.has_key(child.i):# check weather the hashmap contain or not 
                                        newinfo.typewhere.append(stringnounphrasemap.get(child.i))
                                    
                                    if normalnounphrasemap.has_key(child.i):
                                        newinfo.typewhere.append(normalnounphrasemap.get(child.i))
                                    
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
                                                newinfo.typewhat.insert(0,stringnounphrasemap.get(onobjchild.i));
                                                #newinfo.typewhat.append(stringnounphrasemap.get(onobjchild.i))
                                                
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                #newinfo.typewhat.append(normalnounphrasemap.get(onobjchild.i))
                                                newinfo.typewhat.insert(0,normalnounphrasemap.get(onobjchild.i));
                                            
                                            #28/7/2017 added
                                            sublist=list()
                                            for sub in onobjchild.subtree:
                                                if sub.pos_ in [u"NUM"]:
                                                    sublist.append(sub.lemma_)
                                                if(len(sublist)>0):
                                                    newinfo.typewhat.append(sublist)
                                            #end
                                            newinfo.typewhat.append(onobjchild.lemma_)
                                else:##2018.2.19
                                    for onobjchild in prepchild.children:
                                        if(onobjchild.dep_ in [u"dobj",u"pobj"]):
                                            if stringnounphrasemap.has_key(onobjchild.i):
                                                if word.lemma_ not in ["change"]:#change has a inverse grammar than fill
                                                    newinfo.typewhat.append(stringnounphrasemap.get(onobjchild.i))
                                                
                                            if normalnounphrasemap.has_key(onobjchild.i):
                                                if word.lemma_ not in ["change"]:#change has a inverse grammar than fill
                                                    newinfo.typewhat.append(normalnounphrasemap.get(onobjchild.i))          
            
                    
        
         
                    
                    if not newinfo==None:
                        if newinfo.type=="input":
                            for oneword in doc:
                                ################digit case########################
                                if (oneword.pos_ in ["NUM"] and oneword.is_digit==False):
                                        #print("test1")
                                        #print(word.text)
                                        newinfo.digittypewhat.append(re.findall ('[\d ]+', oneword.lemma_))#digit
                                        newinfo.digittypewhere.append(re.findall ('[^\d ]+', oneword.lemma_))#string
                                        
                                        #print re.findall ('[\d ]+', word.text);
                                        #print re.findall ('[^\d ]+', word.text);
                                
                                if (oneword.pos_ in ["NUM"] and oneword.is_digit==True):
                                        newinfo.digittypewhat.append(oneword.lemma_)
                                        #recorder.digittypewhere.append(word.head.text)
                                        if(len(doc)>oneword.i+1):
                                            newinfo.digittypewhere.append(doc.__getitem__(oneword.i+1).lemma_)#get next item
                                        #for child in word.children:        
                                        #    newinfo.digittypewhere.append(child.text)
                                #####################################33
                        steplist.append(newinfo)
                    
                    
                    examplemet=False
                    for perword in doc:
                        if perword.text in ["e.g.","E.g.","E.G.","example","say"]:
                            examplemet=True
                        
                        if examplemet & (perword.pos_ in ["NUM"]):
                            newinfo.digittypewhat.insert(0,perword.text)
                            break;
                        
                        if examplemet & (perword.pos_ in ["NOUN"]):
                            newinfo.typewhat.insert(0,perword.text)
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
                
                
                
                
                
                   
    ###################33xml###############33
    file_name=open(address+"/middleResults/nlp.xml","wb")
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

    