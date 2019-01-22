# Evaluation Result

In the evaluation result, we tested 51 bug reports using ReCDroid.

There are 1 folder and 4 files.

## APKs

There are apks of 51 bug reports under test.


## Comparison with Monkey, Stoat, and Sapienz  

We compared  ReCDroid with three existing methods, including [Monkey](https://developer.android.com/studio/test/monkey), [Stoat](https://github.com/tingsu/Stoat), and [Sapienz](https://github.com/Rhapsod/sapienz).  The results can be found in `monkey-stoat-sap.xlsx`. 

The execution parameters of the four tools are as follows:

- Sapinez

timeout 2h python main.py $app_src_dir $avd_name $avd_serial &> $RESULTDIR$p/tool.log

- Mokey

timeout 2h adb -s $DEV_SERIAL shell monkey -p $package -v --throttle 200 --ignore-crashes --ignore-timeouts --ignore-security-exceptions --bugreport 1000000 > $OUTPUTDIR/monkey.log

- Stoat

ruby run_stoat_testing.rb --apps_dir /home/XX/test_apps/ --apps_list /home/XX/test_apps/apps_list.txt --avd_name testAVD_1 --avd_port 5554 --stoat_port 2000 --project_type apk --force_restart 

- ReCDroid

./percerun_LibreNews.sh


## Error Messages

The error messages are used to determine if certain crashes are triggered. The error messages are described in `ID.docx`


## Evaluation DataSet

You can find the links to the used bug reports in `evaluationDataSet.xlsx`. This file also described the reason when ReCDroid fails to reproduce some of the crashes. 


## User Study

Details on user study can be found in `userStudyGithub.xlsx`. 



