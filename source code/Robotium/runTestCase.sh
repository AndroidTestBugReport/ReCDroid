echo "first uninstall"
adb -s emulator-5554 shell pm uninstall app.librenews.io.librenews
        

echo "then reinstall"
adb -s emulator-5554 install ./LibreNews-v1.4_debug.apk

adb -s emulator-5554 shell mkdir /data/data/app.librenews.io.librenews/files #need to modify

adb -s emulator-5554 push ./middleResults/run.xml /data/data/app.librenews.io.librenews/files/run.xml #need to modify

adb -s emulator-5554 shell am instrument -w cz.romario.opensudoku.test/android.test.InstrumentationTestRunner
