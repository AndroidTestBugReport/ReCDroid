# Evaluation Result

In the evaluation result, we test 51 bug reports by ReCDroid.

There are 1 folder and 4 files in this folder.

## 1.apk

There are apks of 51 bug reports under test.

Collecting these apks costs lots of mannual work.

## 2.compare with monkey-stoat-sap.xlsx

We compare our ReCDroid to some existing methods as monkey, stoat amd sapinez.
[Stoat](t](https://github.com/tingsu/Stoat)  [S  [Sapienz](z](https://github.com/Rhapsod/sapienz)  [M  [Monkey](y](https://developer.android.com/studio/test/monkey)

The run parameters of four tools are as follow:

- sapinez

timeout 2h python main.py $app_src_dir $avd_name $avd_serial &> $RESULTDIR$p/tool.log

- mokey

timeout 2h adb -s $DEV_SERIAL shell monkey -p $package -v --throttle 200 --ignore-crashes --ignore-timeouts --ignore-security-exceptions --bugreport 1000000 > $OUTPUTDIR/monkey.log

- stoat

ruby run_stoat_testing.rb --apps_dir /home/XX/test_apps/ --apps_list /home/XX/test_apps/apps_list.txt --avd_name testAVD_1 --avd_port 5554 --stoat_port 2000 --project_type apk --force_restart 

- ReCDroid

./percerun_LibreNews.sh


## 3.error message with bug ID.docx

Some bug reports do not have error message online.

We manually trigger the bug based on the description of bug reports and record at there.

ReCDroid and other tools should trigger a bug with the same error message.

## 4.evaluationDataSet.xlsx

We record the bug reports link in this file.

We also describe the fail reason when ReCDroid fails to reproduce some of them in this file.

## 5.userStudyGithub.xlsx

We invite 12 users to manually reproduce these bug reports and record the time(s).

Every user has 1800(s) as the time limit. If a user can not reproduce a bug report when the time is up, we record the time as "Nan".

These 12 users include 5 common users, 4 developers, 3 experts of android.



