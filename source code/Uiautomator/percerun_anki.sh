    #this app needs to disable the keyboard
    # we need to install the apk first by adb -s emulator-5554 install ./73-k9-build.apk
    #then click the allow
    gnome-terminal -e "./trans.sh" 

    #sleep 0.1m
    #adb -s emulator-5554 uninstall cz.romario.opensudoku.test
    #adb -s emulator-5554 install BFS_androidtester.apk 


    rm -r ./percentresult/*
    mkdir ./percentresult/0 

    for percent in 0
    do
    echo $percent
    


    for testtimes in 1
    do

    #java -jar percentage.jar $percent
    #rm ./nlpBugReport/bugreport


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

	adb -s emulator-5554 shell rm -r sdcard/*     #this ScreenCam running needs the sdcard, if the sdcard is removed the screenCam will be crash.
        #adb shell am force-stop com.android.mms
        #adb shell am force-stop com.android.email
        adb -s emulator-5554 shell input keyevent 4
        echo "The value is: $loop"
         
        #echo "first uninstall"
        #adb -s emulator-5554 shell pm uninstall com.example.terin.asu_flashcardapp
        

        #echo "then reinstall"
       	#adb -s emulator-5554 install ./99.FlashCards.apk


        cp ./middleResults/record.xml ./middleResults/copy/record$loop.xml #just want to record the trace of record.xml to help debug.
        cp ./middleResults/output/result.xml ./middleResults/copy/result$loop.xml #just want to record the trace of record.xml to help debug.



	adb -s emulator-5554 shell am start -S -n com.example.terin.asu_flashcardapp/com.example.terin.asu_flashcardapp.LoginActivity

	sleep 2s




	echo "commander"

        if java -jar commander.jar | grep -q 'Exception'; then
           echo "errorcommander"
           break;
        fi

        rm -r ./middleResults/output/result.xml
	echo "android tester"
	python run.py >> middleResults/testResult
        

        rm -r ./middleResults/logcat
        adb -s emulator-5554 logcat -d > ./middleResults/logcat

        if grep -q "FATAL" middleResults/logcat || grep -q "fatal" middleResults/logcat; then
		echo "Crash"


		if grep -q "android.database.sqlite.SQLiteOpenHelper.getWritableDatabase(SQLiteOpenHelper.java:163)" middleResults/logcat; then #need to be modify
		        echo "matchCrash"
		        
		   	break;  
		fi

	fi
	adb -s emulator-5554 logcat -c

        dur=$(echo "$(date +%s.%N) - $start" | bc)
        
        int=${dur%.*}

        if [[ $int -ge 7200 ]]; then
           echo "timeout"
           break;
	fi 


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
