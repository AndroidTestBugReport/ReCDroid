# ReCDroid

[![MIT License](https://img.shields.io/github/license/xiaocong/uiautomator.svg)](http://opensource.org/licenses/MIT)


The ReCDRoid is an android project to reproduce android bugs from bug report.

Quick demo with bug report https://github.com/milesmcc/LibreNews-Android/issues/22: 
- `Reproducing process` https://youtu.be/i0fQ58aUPa8 
- `Reproducing result` https://youtu.be/WVtp-Ld4WF0 
           
## 1. Artifact description:
This is the artifact for the paper Tests from Traces:  ReCDroid: Automatically Reproducing Android
Application Crashes from Bug Reports published at ICSE 2019. The aim is to:

1. Show how the ReCDroid introduced in the paper works and can be used for reproducing android bugs from bug report.
2. Reproduce the experiment used to evaluate it.

Concretely, we rerun the the code snippets shown in Table II from the Section V.

### Pre-requirements

- Prepare an desktop coputer with recommended configuration: 32GB memory, 8 kenerls cpu, more than 50 GB free disk.
- Download [24 GB virtualbox image](https://drive.google.com/file/d/1kKdHsa9TJEyU4pYyOQPyMzZqagWBcVSX/view?usp=sharing). The root password of the image is `rec_xxx`.
- Use Virtualbox(we use 5.1.38) to import the virtualbox image.

### Run the artifact

1. Open the virtualbox image.
2. Start an android emulator. Wait(mins) until it is totally lanuched.
```sh
   emulator -avd avd -wipe-data
```
3. Enter the Artifact folder, in which we have 25 bug report folders with App name on it.
```sh
   cd ~/Artifact-Evaluation/1.worked-in-this-VM/Android-4.4.2
   ls
```
   result:
```sh
10.olam1_'_s            17.transistor_s     24.obdreader_s  6.acv_s
11.olam_space_s         18.zom              26.odk          7.anymemo_deafult_s
12.fastadaper_s         19.Pix-Art-share_s  27.k-9_s        8.anymemo_menu_s
13.librenews_refresh_s  1.newsblur_s        2.markor_s      9.nodepad_s
14.librenews_back_s     20.Pix-Art_s        3.birthdroid_s
15.librenews_s          22.ventriloid_s     4.carreport_s
16.smssync_s            23.news_s           5.opensudoku_s
```
4. Enter one bug report folder like 1.newsblur_s.
```sh
   cd 1.newsblur_s
```

5. Run the reproducing process which is named with "percerun_`AppName`.sh"
```sh
   ./percerun_newsblur.sh
```
   In this step, ReCDRoid will explore the app and try to reproduce bug based on the description of bug report. 
   Please wait until it shows "matchcrash" and "execution time". It may run more than 1 hour for some difficult bugs.

6. Run the reproducing result to show how to trigger the bug with "runCrash_`AppName`.sh ".
```sh
   ./runCrash_newsblur.sh
```
7. Close all of the terminals and emluator before you want to test another bug report.

### Toubleshooting

Actually, running Andtoid emulator in a virtualbox ubuntun system is not a good way to run artifact for these [reasons](https://stackoverflow.com/questions/14971621/android-emulator-not-starting-in-a-virtualbox-ubuntu-instance). So the emulator in this virtualbox will be much slow than in the physical machine. Some emualtors of resource consuming android sdk version have lanuch error as Android 7.0.0 or can not be totaly lanuched as Android 5.0.1 and 5.1.1. So as the result, only 25 bug reports can be run in this vritual box whereas our ReCDroid claims it can reproduce 33 bug reports in the paper. If you want to use ReCDroid to reproduce all of the 33 bug reports, you should prepare Ubuntu 16.04 physical machine to build environment with this [instruction](https://drive.google.com/file/d/1W2HUs_6YJ3gD6qAeEUieYApiLTdCjb3Y/view?usp=sharing). I believe the android environment may need hours(much longer than half hour) to build on a physical machine.


## 2. Usage:
### (1) Find specific crash. ReCDRoid stops when it has triggered a specific crash which matches the provided error message.
`Input`: An android bug report, related apk, related error message.

`Exploration`: Using NLP to process bug report and DFS to search the specific crash.

`Output`: A test case which can trigger crash with the input error message.


### (2) Find all crashes. ReCDRoid tends to continually find other crashes after it triggers one.
`Input`: An android bug report and related apk.

`Exploration`: Using NLP to process bug report and BFS to search crashs.

`Output`: All test cases which can trigger crash.

ReCDRoid can run test cases of output.

## 3. Example:
A bug report in https://github.com/milesmcc/LibreNews-Android/issues/22 as fellow:

- Install v1.4 from FDroid.
- Launch app.
- Disable automatically refresh
- Change server address to an invalid one, e.g., xxyyzz.
- Click refresh.



### (1)Example video:

`Exploration video`: example1-exploration.mkv

Youtube link: https://youtu.be/i0fQ58aUPa8

`Reproduce video`: example2-runTestCase.mkv

Youtube link: https://youtu.be/WVtp-Ld4WF0

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

There are 6 steps for ReCDroid to trigger a bug. CurrentClass here is a page in android app. The detailed information of a papge can be found in the record.xml file with (CurrentClass in run.xml)=(ID in record.xml). I apologize for the name disunion. It will be updated in the next version. Every page has some UI components, so the subID is the compontent ID in this page. The detailed information of UI components can also be found in the record.xml with (subID in run.xml)=(runableID in record.xml). After subID, there are some detailed parameters as "..." to help tester knowing how to active this step.

### (3)Quick start example project
You need 5 steps to run this project on your computer.

(1) Install android environment.

(2) Install gensim python environment.

(3) open an android avd with port 5554 and android 4.4.2

(4) download ReCDroid Robotium example project from Google file link(there is a 200MB word2vec library in it): https://drive.google.com/open?id=19iXoI_8QtQMKBheS8fHhABt7YCS3rayg

(5) run ReCDroid with percerun_LibreNews.sh in the folder.

### (4)Uiautomator version
All of above is Robotium version which can support Android sdk lower than Android 6.0.
If you want to use ReCDroid version equal or above Android 6.0, please download ReCDroid Uiautomator example project to run. Google file link: https://drive.google.com/open?id=1lMMvSGx3Of7C7RcqE98iAJotWFXOEt3T
Related bug report link: https://github.com/ASU-CodeDevils/FlashCards/issues/13
