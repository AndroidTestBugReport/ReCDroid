# Evaluation Result

In the evaluation result, we test 51 bug reports by ReCDroid.

There are 1 folder and 4 files in this folder.

## 1.apk

There are apks of 51 bug reports under test.

Collecting these apks costs lots of mannual work.

## 2.compare with monkey-stoat-sap.xlsx

We compare our ReCDroid to some existing methods as monkey, stoat amd sapinez.

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

Some bug reports are not have error message online.

We mannually trigger the bug based on the description of bug reports and record at there.

ReCDroid and other tools should trigger a bug with the same error message.

## 4.evaluationDataSet.xlsx

We record the bug reports link in this file.

We also describe the fail reason when ReCDroid fail to reproduce some of them in this file.

## 5.userStudyGithub.xlsx

We invite 12 users to mannully reproduce these bug reports and record the time(s).

Every users have 1800(s) as time limit. If a user can not reproduce the bug report when time is up, we record as Nan.

These 12 users include 5 common users, 4 developers, 3 experts of android.



