# KMP Project

A template to jumpstart KMP/CMP projects with modularization, clean architecture, MVVM and UDF.

This project uses:
- Ktor for networking
- Kotlin Coroutines for asynchronous tasks
- Jetpack Navigation for screen transitions
- Jetpack DataStore for local persistence
- Firebase for app distributions

## UI Design
This is not a full-fledged photo album. It’s just a simple photo viewer to help you jumpstart setting up your own project.

![UI Design](ui_design.png)

## Get the Precompiled App
- [iOS](https://aungthiha.github.io/iOSAppAccessAutomation/pages/firebase-setup.html) - Automatically compiled and distributed after email registration (usually within 20 minutes)
- Android - Please, manually compile from the source (for now)

## Set up the Project
1. Follow [the official guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/quickstart.html#set-up-the-environment) to set up the KMP development environment.
2. Clone the repo.
3. Setup Firebase for Android by following [the official guide](https://firebase.google.com/docs/android/setup). Place `google-services.json` in `composeApp` directory.
4. Setup Firebase for iOS by following [the official guide](https://firebase.google.com/docs/ios/setup). The initialization code is already added. Just place `GoogleService-Info.plist` into `iosApp/iosApp/` directory. 
5. Open the project in Android Studio. 
6. If you get a `NoToolchainAvailableException`, install JDK 24. If you don’t get this error, you can skip this step. 
7. Swift packages should be automatically resolved by Android Studio. If not, please update your Android Studio. 
8. There’s a dropdown menu beside the Run button in Android Studio. That’s where you choose the target platform. 
9. Enjoy!

## Testing
Refer to [the dedicated page](TESTING.md) for testing.

## Roadmap
- Set up spacer sizes in terms of XXS, XS, S, M, L, XL and so on instead of hardcoded DPs to easily unify sizes
- Write an Android Studio plugin help developers easily generate fakes(test doubles) to use with integration tests
- Write instrumentation tests to ensure things work as expected on real devices
- Use paging3 in PhotoListScreen to support pagination
- Figure out which snapshot test framework would be best suited for the project
- Set up snapshot tests
- Implement [Talaiot](https://github.com/cdsap/Talaiot) to analyze Gradle tasks
- Implement AES-GCM encryption for session storage to enhance security
- Implement remote Gradle cache for the iOS CI/CD pipeline
- Use [Spotless](https://github.com/diffplug/spotless) to sort the keys in version catalog to make searching keys easier

## CI/CD
- [iOS](https://github.com/AungThiha/iOSAppAccessAutomation)
- Android (Pending)

## Contributing
PRs and feedback welcome!

## License
Apache 2.0
