'''
Created on Jun 12, 2017

@author: yu
'''
from spacy.en import English
#import strand
import re
from xml.dom.minidom import Document
from xml.dom import minidom
import time
import os
from gensim.models import Word2Vec
import codecs
import shutil
import copy
import os
#grammar = nltk.parse_cfg()
def addnlptotext(doc,nlp,node):
    for subnode in node.childNodes:        
        if(subnode.nodeType == minidom.Node.ELEMENT_NODE):
            #time.sleep(1)          
            if subnode.hasChildNodes():
            
                if subnode.nodeName.endswith("text"):
                    
                    
                    
                    text=subnode.firstChild.nodeValue.lower()
                    sent=nlp(unicode(text))
                    textnlpelement=doc.createElement(subnode.nodeName+"nlp")
                    node.appendChild(textnlpelement)
                    
                    i=0
                    for word in sent:
                        IDelement=doc.createElement("ID"+str(i))
                        textnlpelement.appendChild(IDelement)
                        wordelement=doc.createTextNode(word.lemma_)
                        IDelement.appendChild(wordelement)
                        i=i+1
                else:
                    addnlptotext(doc,nlp,subnode)
                    
                    
                 #   textnlpelement=doc.createElement("ID"+str(i))
                    
                    
                    
                    
                 #   alltextnlp.appendChild(alltextnlpelement)
                 #   textelement=doc.createTextNode(word.lemma_)
                 #   alltextnlpelement.appendChild(textelement)
                 #   i=i+1


def main():
    
    #address="/home/yu/repeatbugreport"
    address=".."
    
    nlp = English()
    #model= Word2Vec.load('wiki.en.word2vec.model')#or yumodel
    model= Word2Vec.load('yumodel')
    

    #doc = nlp(u"VIDEOSq")

    #for token in doc:
    #    print(token, token.lemma, token.lemma_)
    #print pluralize('child')
#    aa=model.wv.similarity('movie', 'video')
    
    print("load finish")
    
    
    err2times=0
    while(1):
        
        
       
        
        '''
        if (os.path.exists('/home/yu/repeatbugreport/similarity.xml')):
            #################read the similarity.xml and pick the root############
            doc=minidom.parse('/home/yu/repeatbugreport/similarity.xml')
            root=doc.documentElement
        
        
        
            apples=nlp(u"good")
            orange=nlp(u"better")
    
            print(apples.similarity(orange))
            

            model= Word2Vec.load('yumodel')
            model.wv.similarity('refueling', 'refuel')

            
            #################write
            file_write = open("/home/yu/repeatbugreport/output/similarity.xml","wb")
            doc_write =Document()
        
            doc_write.appendChild(root)            
            doc_write.writexml(file_write)
            file_write.close()
        '''
        
        
        if (os.path.exists(address+'/middleResults/result.xml')):
            time.sleep(0.5)
            ##################read the result.xml and pick the root###############           
            try:
                doc=minidom.parse(address+'/middleResults/result.xml')
                root=doc.documentElement

            except Exception as err:
                print("null file error : err")
                continue
            

            #######compute similarity#################################
            ###################
            ###########3
            ######3
            sentmap={}#key is the sentence id, value is a list of sentence string.
            
            file_sim = open(address+"/middleResults/output/similarity.xml","wb")
            doc_sim=Document()
            root_sim=doc_sim.createElement("Similarity")
            doc_sim.appendChild(root_sim)
            
            '''

            doc_sim.writexml(file_sim)
            file_sim.close()
            '''
            simid=0
            sentid=None
            
            ###########################first to transfer the allcases into sentmap##################
            docallsent=minidom.parse(address+'/middleResults/allcases.xml')
            rootallsent=docallsent.documentElement
            
            for sent in rootallsent.childNodes:
                if (sent.nodeType == minidom.Node.ELEMENT_NODE):##minidom is a little bit ugly
                    #sentid=str(-1)
                    for item in sent.childNodes:
                        if (item.nodeType == minidom.Node.ELEMENT_NODE):##minidom is a little bit ugly
                            
                            if(item.nodeName=="Sentid"):
                                sentid=item.firstChild.nodeValue
                                sentwordlist=list()
                                sentmap.update({sentid:sentwordlist})
                            
                                
                            if(item.nodeName=="sentence"):
                                for word in item.childNodes:
                                    if (word.nodeType == minidom.Node.ELEMENT_NODE):##minidom is a little bit ugly
                                        if word.hasChildNodes():
                                            sentmap.get(sentid).append(word.firstChild.nodeValue)
                                            
                            
                    
            ################################deal with 
            #for stepnode in root.childNodes:### this root is from result.xml
            try:    ##all true case, to deal with tabs
                stepnode=(root.childNodes)[-2]    
                
                
                if (stepnode.nodeType == minidom.Node.ELEMENT_NODE):##minidom is a little bit ugly
                
                    for functionode in stepnode.childNodes:
                        ###########alltext cases
                        if (functionode.nodeName =="AllText"):
                            for textnode in functionode.childNodes:
                                if (textnode.nodeType == minidom.Node.ELEMENT_NODE):##minidom is a little bit ugly
                                    try:
                                        text=textnode.firstChild.nodeValue
                                        
                                        sent=nlp(unicode(text))
                                    except Exception as err:
                                        continue
                                        
                                    for word in sent:
                                        resultwordtext=word.lemma_
                                        
                                        keylist=sentmap.keys()## sentmap is from all sents
                                        for k in keylist:
                                            allsenttextlist=sentmap.get(k)
                                            
                                            for allsenttext in allsenttextlist:
                                            
                                                ### here should be added a exception
                                                if (resultwordtext in model.wv.vocab) and (allsenttext in model.wv.vocab):
                
                                                    similarity=model.wv.similarity(resultwordtext, allsenttext)
                                                    '''
                                                    print(resultwordtext)
                                                    print(allsenttext)
                                                    print(similarity)
                                                    print("\n")
                                                    '''
                                                    
                                                    if similarity>0.8:
                                                        onesimilarity=doc_sim.createElement("ID"+str(simid))
                                                        root_sim.appendChild(onesimilarity)
                                                        
                                                        sentid=doc_sim.createElement("SentenceID")
                                                        onesimilarity.appendChild(sentid)
                                                        textnode=doc_sim.createTextNode(k)
                                                        sentid.appendChild(textnode)
                                                        
                                                        sentword=doc_sim.createElement("SentenceWord")
                                                        onesimilarity.appendChild(sentword)
                                                        textnode=doc_sim.createTextNode(allsenttext)
                                                        sentword.appendChild(textnode)
                                                        
                                                        resultword=doc_sim.createElement("ResultWord")
                                                        onesimilarity.appendChild(resultword)
                                                        textnode=doc_sim.createTextNode(resultwordtext)
                                                        resultword.appendChild(textnode)
                                                        
                                                        
                                                                                                       
                
                                                        simid+=1
            
            except Exception as err:
                if err2times<10:
                    err2times+=1
                    print("null file error : err2")
                    continue
                else:#creat a blank result and similarity
                    err2times=0
                    
                    docResultBlank = Document()
                    rootblank = docResultBlank.createElement('Result')
                    docResultBlank.appendChild(rootblank);
                    
                    rootblank.setAttribute("back", "false")
                    step1=docResultBlank.createElement("Step1");
                    step2=docResultBlank.createElement("Step2");
                    rootblank.appendChild(step1)
                    rootblank.appendChild(step2)
                    
                    Classname=docResultBlank.createElement('Classname')
                    text=docResultBlank.createTextNode("error may be crashed")
                    Classname.appendChild(text)
                    
                    ViewGroup=docResultBlank.createElement('ViewGroup')
                    NoGroup=docResultBlank.createElement('NoGroup')
                    Easyoperate=docResultBlank.createElement('Easyoperate')
                    AllText=docResultBlank.createElement('AllText')
                    
                    step1.appendChild(copy.deepcopy(Classname))
                    step1.appendChild(copy.deepcopy(ViewGroup))
                    step1.appendChild(copy.deepcopy(NoGroup))
                    step1.appendChild(copy.deepcopy(Easyoperate))
                    step1.appendChild(copy.deepcopy(AllText))
                    
                    step2.appendChild(copy.deepcopy(Classname))
                    step2.appendChild(copy.deepcopy(ViewGroup))
                    step2.appendChild(copy.deepcopy(NoGroup))
                    step2.appendChild(copy.deepcopy(Easyoperate))
                    step2.appendChild(copy.deepcopy(AllText))
                    #rootblank=
                    #rootblank
                     
                    with codecs.open(address+"/middleResults/output/result.xml","wb","utf-8") as out:
                        docResultBlank.writexml(out)
            
                    out.close()        
                    
                    os.remove(address+'/middleResults/result.xml')
                    
                    doc_sim.writexml(file_sim)
                    file_sim.close()
                    #shutil.copy(address+'/middleResults/output/resultcopy.xml',address+'/middleResults/output/result.xml');
                    #shutil.copy(address+'/middleResults/output/similaritycopy.xml',address+'/middleResults/output/similarity.xml');

                    continue
            doc_sim.writexml(file_sim)
            file_sim.close()
            ##################similarity end##########
            ###############
            ########
            ###                            
                                        
                                        
                                         
                                
                                

            
            ##################write the result.xml in output######################
            #file_write = open("/home/yu/repeatbugreport/middleResults/output/result.xml","wb")
            for stepnode in root.childNodes:
                addnlptotext(doc,nlp,stepnode)
                                         
            doc_write =Document()
            doc_write.appendChild(root)  
            with codecs.open(address+"/middleResults/output/result.xml","wb","utf-8") as out:
                doc_write.writexml(out)
            
            
            out.close()
            '''
            file_write = open("/home/yu/repeatbugreport/middleResults/output/result.xml","wb")
            doc_write =Document()
            
            
            ###################process on all element end with task
            for stepnode in root.childNodes:
                addnlptotext(doc,nlp,stepnode)
            
            
            ##################write the result.xml in output######################
            doc_write.appendChild(root)            
            doc_write.writexml(file_write)
            file_write.close()
            '''



            ####################remove the orignal file#################################
            os.remove(address+'/middleResults/result.xml')
            
            
            
        time.sleep(1)
    
if __name__ == '__main__':
    main()         

    