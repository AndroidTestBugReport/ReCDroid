# Evaluation Result

In the evaluation result, we test 51 bug reports by ReCDroid.

There are 1 folder and 4 files in this folder.

## apk

There are apks of 51 bug reports under test.

Collecting these apks costs lots of mannual work.

## compare with monkey-stoat-sap.xlsx

We compare our ReCDroid to some existing methods as monkey, stoat amd sapinez.

The run parameters of four tools as follow:

- sapinez

timeout 2h python main.py $app_src_dir $avd_name $avd_serial &> $RESULTDIR$p/tool.log

- mokey

timeout 2h adb -s $DEV_SERIAL shell monkey -p $package -v --throttle 200 --ignore-crashes --ignore-timeouts --ignore-security-exceptions --bugreport 1000000 > $OUTPUTDIR/monkey.log

- stoat

ruby run_stoat_testing.rb --apps_dir /home/XX/test_apps/ --apps_list /home/XX/test_apps/apps_list.txt --avd_name testAVD_1 --avd_port 5554 --stoat_port 2000 --project_type apk --force_restart 

- ReCDroid

./percerun_LibreNews.sh


