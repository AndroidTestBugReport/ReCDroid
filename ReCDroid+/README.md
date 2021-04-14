## ReCDroid+ is a journal extension for ReCDroid by adding S2R extraction and Crash identification.

This folder provides the source code and data set of S2R and Crash extraction.

Source code: https://drive.google.com/file/d/1VRH04LAkdBPbwJ39R5E5fc023pIbpZQe/view?usp=sharing

Labeled bug report data set: https://drive.google.com/file/d/1wcPrjCFVlHzou_bPwjoaa1n5Hwvy9iJx/view?usp=sharing

## Bug report labeled format:

- rules:
  #step is tagged end of the S2R sentence
  #oracle is tagged end of the crash sentence

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


