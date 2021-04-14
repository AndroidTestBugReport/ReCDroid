## ReCDroid+ is a journal extension for ReCDroid by adding step to reproduce (S2R) sentence extraction and Crash identification.

ReCDroid+ can input HTML bug report and output bug report in text format with sentence segmentation.

By analysising the bug report in text format, ReCDroid+ identifies the crash sentence to verify whether the bug report is in its usage scope.

S2R sentences are extracted by a deep neural network model from the bug report. 

The extracted S2R can be used to reproduce the crash by adding the original recdroid.


- HTML2TXT Source code: https://drive.google.com/file/d/1G2AdwwX08fAOmEsE-DR0K6l7BfJRyee7/view?usp=sharing

- S2R/Crash extraction Source code: https://drive.google.com/file/d/1VRH04LAkdBPbwJ39R5E5fc023pIbpZQe/view?usp=sharing

- Labeled bug report data set: https://drive.google.com/file/d/1wcPrjCFVlHzou_bPwjoaa1n5Hwvy9iJx/view?usp=sharing

2 people labeled the same data set. The third people revise the labeling and provide the finial decision.

## Bug report labeled format:

- rules:

  #step is tagged at end of the S2R sentence

  #oracle is tagged at end of the crash sentence

- Example:

```
#####title#12345#
Crash when long-pressing a folder #step #oracle
#####title#12345#
Crash on Nexus 4, ACV 1.4.1.4: #oracle
numDot start the app #step
numDot click menu #step
numDot choose "open" #step
numDot go to directories like /mnt #step
numDot long-press a folder, like "secure" #step
numDot crash #oracle
The reason is that, when you don't have permission, File.list() would return null.
But this is not checked.
The problem happens in src/net/robotmedia/acv/ui/SDBrowserActivity.java:111, where you called file.list() and later used the result.
The return code may be null.
In this case, it's due to permission, so maybe it's not that interesting.
However, it may also return null due to other reasons.
Anyway, showing an error message is better than crashing. #oracle
####comment#12345#
```


