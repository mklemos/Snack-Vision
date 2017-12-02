This is the SnackVision Sprint 2 main readme.

To begin, download Android Studio. This project is configured to run with android 24, 
so apply the correct dependencies when downloading.

After your Android Studio has been properly configured, open the project: "SnackVision"
located inside the "main" folder.

Run your first gradle build once the project is loaded. If there are missing dependencies,
Android Studio will prompt you to download dependencies after you build the gradle.

Once you are ready, you will need to configure an emulator to run the app, or use your android
device connected via USB. If you choose to run an emulator, click the play button to run android
you will see two options. The pop-up will scan for connected android devices. After the scan completes,
you will see COnnected Devices and Available Virtual devices. Click create new virtual device. Choose
a device that has the playstore option and click next. I recommend Nexus 5x. Grab Nougat 24 under
the x86 tab. Click next. Name your virtual device and and hit finish.

Select your virtual device and click OK. The emulator should take some time to start up.
*NOTE: When the emulator loads, the app may not have finished its gradle build. Android Studio
will show the gradle process. Your app is loading.

Once the app launches you are free to move around via usual google maps controls.

The marker functionality is no longer present in this version, but the map still works. Pressing the
hamburger button in the top left will open and close a navigation drawer.
