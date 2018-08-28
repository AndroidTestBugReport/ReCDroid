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

`Exploration video`: <font color=#0099ff>example1-exploration.mkv</font>

`Reproduce video`:  example2-runTestCase.mkv


### (2)Output test cases:
Output steps in run.xml: 

```
<step id="1"><currentClass>1</currentClass><subId>1</subId>...</step>
 
<step id="2"><currentClass>2</currentClass><subId>5</subId>...</step>

<step id="3"><currentClass>3</currentClass><subId>4</subId>...</step>

<step id="4"><currentClass>4</currentClass><subID>1</subID>...</step>

<step id="5"><currentClass>4</currentClass><subId>3</subId>...</step>

<step id="6"><currentClass>3</currentClass><subId>1</subId>...</step>
```

There are 6 steps for ReCDroid to trigger a bug. CurrentClass here is a page in android app. The detailed information of a papge can be found in the record.xml file with (CurrentClass in run.xml)=(ID in record.xml). I am apologize for the name disunion. It will be updated in the next version. Every papge has some UI components, so the subID is the compontent ID in this page. The detailed information of UI compoents can also be found in the record.xml with (subID in run.xml)=(runableID in record.xml). After subID, there are some detailed parameters as "..." to help tester knowing how to active this step.


<font color=#00ffff size=72>color=#00ffff</font>

