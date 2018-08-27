# ReCDroid

The ReCDRoid is an android project to reproduce android bugs from bug report.

## 1. Usage:
They are two was to use it.
### (1) Find all crashes
`Input`: An android bug report and related apk.

`Exploration`: Using NLP to process bug report and DFS to seach crashs.

`Output`: All test cases which can trigger crash.

### (2) Find the specific crash
`Input`: An android bug report, related apk, related error message.

`Exploration`: Using NLP to process bug report and DFS to seach the specifc crash.

`Output`: A test case which can trigger crash with the input error message.

All of outputs can be run in our tool directly.

## 2. Example:
A bug report in https://github.com/milesmcc/LibreNews-Android/issues/22:
   Install v1.4 from FDroid.
   Launch app.
   Disable automatically refresh
   Change server address to an invalid one, e.g., xxyyzz.
   Click refresh.



### (1)Example video:

`Exploration video`: example1-exploration.mkv

`Reproduce video`:  example2-runTestCase.mkv


### (2)Output test cases:






