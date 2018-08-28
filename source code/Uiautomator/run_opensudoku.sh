    python ./nlpBugReport/pythonNLP.py
    rm -r ./middleResults/record.xml
    rm -r ./middleResults/result.xml
    rm -r ./middleResults/testResult
    rm -r ./middleResults/copy/*

    
    start=$(date +%s.%N)

    for loop in {1..5000}
    do

        #adb shell am force-stop com.android.mms
        #adb shell am force-stop com.android.email
        adb shell input keyevent 4
        echo "The value is: $loop"

        echo "first uninstall"
        adb shell pm uninstall cz.romario.opensudoku
        

        echo "then reinstall"
        adb install ./OpenSudoku.apk
 

        adb shell rm -r sdcard/*     #this ScreenCam running needs the sdcard, if the sdcard is removed the screenCam will be crash.


        cp ./middleResults/record.xml ./middleResults/copy/record$loop.xml #just want to record the trace of record.xml to help debug.
        cp ./middleResults/output/result.xml ./middleResults/copy/result$loop.xml #just want to record the trace of record.xml to help debug.

        echo "commander"

        if java -jar ./commander.jar | grep -q 'Exception'; then
           echo "errorcommander"
           break;
        fi         

        #cp ./middleResults/record.xml ./middleResults/copy/record$loop.xml #just want to record the trace of record.xml to help debug.
        cp ./middleResults/run.xml ./middleResults/copy/run$loop.xml #just want to record the trace of run.xml to help debug.
        #cp ./middleResults/output/result.xml ./middleResults/copy/result$loop.xml #just want to record the trace of record.xml to help debug.

	adb shell mkdir /data/data/cz.romario.opensudoku/files #need to modify

	adb push ./middleResults/run.xml /data/data/cz.romario.opensudoku/files/run.xml #need to modify

        #cp  middleResults/output/result.xml middleResults/copy/result.xml
        cp  middleResults/output/result.xml middleResults/output/resultcopy.xml
        cp  middleResults/output/similarity.xml middleResults/output/similaritycopy.xml
        rm -r middleResults/output/result.xml

	echo "android tester"


        rm -r ./middleResults/testResult
        adb shell am instrument -w cz.romario.opensudoku.test/android.test.InstrumentationTestRunner >> middleResults/testResult

        if grep -q "Unable to" middleResults/testResult; then
           echo "Crash"
           break;  
        fi

        if grep -q "NullPointerException" middleResults/testResult; then
           echo "Crash"
           break;  
        fi

        if grep -q "IllegalArgumentException" middleResults/testResult; then
           echo "Crash"
           break;
        fi

        if grep -q "INSTRUMENTATION_CODE: 0" middleResults/testResult; then
           echo "Crash"
           break;
        fi

        if grep -q "Input dispatching timed out" middleResults/testResult; then
           echo "APP Hang"
           break;
        fi


#        if adb shell am instrument -w cz.romario.opensudoku.test/android.test.InstrumentationTestRunner | grep -q 'Input dispatching timed out'; then
#           echo "APP Hang"
#           break;
#        fi


	adb pull /data/data/cz.romario.opensudoku/files/result.xml ./middleResults/result.xml #need to modify


        adb shell input keyevent 4


    done
    dur=$(echo "$(date +%s.%N) - $start" | bc)
    printf "Execution time: %.6f seconds" $dur
