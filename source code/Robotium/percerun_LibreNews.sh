    gnome-terminal -e "./trans.sh" 

    #sleep 0.1m
    adb -s emulator-5554 uninstall cz.romario.opensudoku.test
    adb -s emulator-5554 install BFS_androidtester.apk 


    rm -r ./percentresult/*
    mkdir ./percentresult/0 

    for percent in 0
    do
    echo $percent
    


    for testtimes in 1
    do

    #java -jar percentage.jar $percent
    #rm ./nlpBugReport/bugreport
    #cp ./nlpBugReport/percentbugreport ./nlpBugReport/bugreport

    start=$(date +%s.%N)
    python ./nlpBugReport/pythonNLP.py
    rm -r ./middleResults/record.xml
    rm -r ./middleResults/result.xml
    rm -r ./middleResults/testResult
    rm -r ./middleResults/copy/*

    
    adb -s emulator-5554 logcat -c
    cp ./nlpBugReport/bugreport ./percentresult/$percent/bugreport$testtimes

    for loop in {1..100000}
    do

        adb -s emulator-5554 shell am force-stop com.android.mms
        adb -s emulator-5554 shell am force-stop com.android.email
        adb -s emulator-5554 shell input keyevent 4
        echo "The value is: $loop"

        echo "first uninstall"
        adb -s emulator-5554 shell pm uninstall app.librenews.io.librenews
        

        echo "then reinstall"
        adb -s emulator-5554 install ./LibreNews-v1.4_debug.apk
 

        adb -s emulator-5554 shell rm -r sdcard/*     #this ScreenCam running needs the sdcard, if the sdcard is removed the screenCam will be crash.


        cp ./middleResults/record.xml ./middleResults/copy/record$loop.xml #just want to record the trace of record.xml to help debug.
        cp ./middleResults/output/result.xml ./middleResults/copy/result$loop.xml #just want to record the trace of record.xml to help debug.

        echo "commander"

        if java -jar commander.jar | grep -q 'Exception'; then
           echo "errorcommander"
           break;
        fi         

        #cp ./middleResults/record.xml ./middleResults/copy/record$loop.xml #just want to record the trace of record.xml to help debug.
        cp ./middleResults/run.xml ./middleResults/copy/run$loop.xml #just want to record the trace of run.xml to help debug.
        #cp ./middleResults/output/result.xml ./middleResults/copy/result$loop.xml #just want to record the trace of record.xml to help debug.

	adb -s emulator-5554 shell mkdir /data/data/app.librenews.io.librenews/files #need to modify

	adb -s emulator-5554 push ./middleResults/run.xml /data/data/app.librenews.io.librenews/files/run.xml #need to modify

        #cp  middleResults/output/result.xml middleResults/copy/result.xml
        cp  middleResults/output/result.xml middleResults/output/resultcopy.xml
        cp  middleResults/output/similarity.xml middleResults/output/similaritycopy.xml
        rm -r middleResults/output/result.xml

	echo "android tester"


        rm -r ./middleResults/testResult
        adb -s emulator-5554 shell am instrument -w cz.romario.opensudoku.test/android.test.InstrumentationTestRunner >> middleResults/testResult

    #    if grep -q "Unable to" middleResults/testResult; then
                      

    #       echo "Crash"
    #       break;  
    #    fi

        if grep -q "Unable to" middleResults/testResult || grep -q "NullPointerException" middleResults/testResult || grep -q "IllegalArgumentException" middleResults/testResult || grep -q "INSTRUMENTATION_CODE: 0" middleResults/testResult || grep -q "Input dispatching timed out" middleResults/testResult; then

           echo "Crash"
           cp ./middleResults/run.xml ./middleResults/copy/run_crash$loop.xml #record the crash.
           sleep 0.1m
           
           rm -r ./middleResults/logcat
           adb -s emulator-5554 logcat -d > ./middleResults/logcat
           if grep -q "FlashManager.convertFlashesToOutputString(FlashManager.java:184)" middleResults/logcat; then #need to be modify
                echo "matchCrash"
                
           	break;  
           fi




        fi

        dur=$(echo "$(date +%s.%N) - $start" | bc)
        
        int=${dur%.*}

        if [[ $int -ge 7200 ]]; then
           echo "timeout"
           break;
	fi  
  
	adb -s emulator-5554 pull /data/data/app.librenews.io.librenews/files/result.xml ./middleResults/result.xml #need to modify


        adb -s emulator-5554 shell input keyevent 4


    done

    dur=$(echo "$(date +%s.%N) - $start" | bc)
    printf "Execution time: %.6f seconds" $dur
    echo $dur >> ./percentresult/$percent/timerecord$testtimes
    sleep 1m
    adb -s emulator-5554 logcat -d >> ./percentresult/$percent/logcat$testtimes
    cp ./middleResults/run.xml ./percentresult/$percent/run_crash$testtimes.xml #record the crash.
    done
    done
