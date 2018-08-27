# ReCDroid

The ReCDRoid is an android project to reproduce android bugs from bug report.

## 1. Usage:
### (1) Find all crashes
`Input`: An android bug report and related apk.

`Exploration`: Using NLP to process bug report and DFS to seach crashs.

`Output`: All test cases which can trigger crash.

### (2) Find the specific crash
`Input`: An android bug report, related apk, related error message.

`Exploration`: Using NLP to process bug report and DFS to seach the specifc crash.

`Output`: A test case which can trigger crash with the input error message.

ReCDRoid can run test cases of output.

## 2. Example:
A bug report in https://github.com/milesmcc/LibreNews-Android/issues/22:
   Install v1.4 from FDroid.
   Launch app.
   Disable automatically refresh
   Change server address to an invalid one, e.g., xxyyzz.
   Click refresh.



### (1)Example video:

`Exploration video`: <font color=red>example1-exploration.mkv</font>

`Reproduce video`:  example2-runTestCase.mkv


### (2)Output test cases:
Output steps in run.xml: 

```
<step id="1"><currentClass>1</currentClass><subId>1</subId><androidid><ownid>2131689606</ownid><ownText>app.librenews.io.librenews:id/startLibreNews</ownText></androidid><Actiontype>android.support.v7.widget.AppCompatButton</Actiontype><clicktype>short</clicktype></step>
 
<step id="2"><currentClass>2</currentClass><subId>5</subId><Actiontype>ClickList</Actiontype><Parameter>3</Parameter><clicktype>short</clicktype></step>

<step id="3"><currentClass>3</currentClass><subId>4</subId><Actiontype>ClickList</Actiontype><Parameter>2</Parameter><clicktype>short</clicktype></step>

<step id="4"><currentClass>4</currentClass><unExeID>1</unExeID><subID>1</subID><androidid><ownid>16908291</ownid><ownText>android:id/edit</ownText></androidid><Actiontype>EditText</Actiontype><typeWhat>xxyyzz</typeWhat></step>

<step id="5"><currentClass>4</currentClass><subId>3</subId><androidid><ownid>16908313</ownid><ownText>android:id/button1</ownText></androidid><Actiontype>android.support.v7.widget.AppCompatButton</Actiontype><clicktype>short</clicktype></step>

<step id="6"><currentClass>3</currentClass><subId>1</subId><androidid><ownid>2131689596</ownid><ownText>app.librenews.io.librenews:id/refresh_button</ownText></androidid><Actiontype>android.support.v7.widget.AppCompatButton</Actiontype><clicktype>short</clicktype></step>
```

Related information in record.xml: 
```
<ID id="4">
      <Classname>MainFlashActivity</Classname>
      <Easyoperate>
        <runableID id="1">
          <viewclass>android.widget.EditText</viewclass>
          <androidid>
            <ownid>16908291</ownid>
            <ownText>android:id/edit</ownText>
          </androidid>
          <edittype>string</edittype>
          <adjacyText>
            <level1>
              <ID0text />
              <ID1text>https://librenews.io/api</ID1text>
              <ID1textnlp>
                <ID0>https://librenews.io/api</ID0>
              </ID1textnlp>
            </level1>
            <level2 />
            <level3 />
          </adjacyText>
          <viewtext>https://librenews.io/api</viewtext>
          <focusable>true</focusable>
          <viewtextnlp>
            <ID0>https://librenews.io/api</ID0>
          </viewtextnlp>
        </runableID>
        <runableID id="2">
          <viewclass>android.support.v7.widget.AppCompatButton</viewclass>
          <androidid>
            <ownid>16908314</ownid>
            <ownText>android:id/button2</ownText>
          </androidid>
          <viewtext>Cancel</viewtext>
          <clicktype>short</clicktype>
          <viewtextnlp>
            <ID0>cancel</ID0>
          </viewtextnlp>
        </runableID>
```


There are 6 steps for ReCDroid to trigger a bug. CurrentClass here is a page in android app. The detailed information of a papge can be found in the record.xml file with (CurrentClass in run.xml)=(ID in record.xml). I am apologize for the name disunion. It will be fixed in the future. Every papge has some UI components, so the subID is the compontent ID in this page. The detailed information of UI compoents can also be found in the record.xml with (subID in run.xml)=(runableID in record.xml). Actiontype is the view type of the compontent. And there are some detailed parameters to active this action. 




