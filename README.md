# Android-ScreenShot-Shortcut
Triggers ScreenShot when App is launched and immediately closes.

The App has a transparent UI so you don't see anything (not shady at all).

This app uses root access (again nothing shady going on at all) because it runs `input keyevent 120` in a shell and the app needs root access to run the input command. 
You can actually trigger the screen shot using adb the same way the app does by running `adb shell input keyevent 120`, 120 is the keycode for the screen shot key.

The first time you run the app, grant root access and you will get an annoying popup saying screenshot shortcut was granted root access present in your screenshot lol.
This only happens once as the app tells magisk to hide toast popups.

The reason I made this app is because on the Asus Rog Phone 2 with Omni 11 the airtriggers shortcuts don't work with the default screenshot settings but they allow you to launch an app so this is the workaround by selecting ScreenShot Shortcut from the Launch App menu lol.
