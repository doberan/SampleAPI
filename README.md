# Android apps API sample

## What this Project?

This project is an API execution sample using AIDL. This is an Android app implementation.

## How to use

1. ADB can be executed on the command line.
2. Add JAVA_PATH to environment variable to use GradleWrapper.

```
$ git clone https://github.com/doberan/SampleAPI.git
$ cd ./SampleAPI
$ ./gradlew :client:assembleDebug
$ ./gradlew :server:assembleDebug
$ adb install <Working directory>/SampleAPI/client/build/apk/debug/client-debug.apk
$ adb install <Working directory>/SampleAPI/server/build/apk/debug/server-debug.apk
```