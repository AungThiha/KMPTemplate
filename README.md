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
3. Open the project in Android Studio, assuming you already have the Android development environment set up.
4. If you get a `NoToolchainAvailableException`, install [JDK 11](https://formulae.brew.sh/cask/zulu@11) using Homebrew. If you don’t get this error, you can skip this step.
5. Swift packages should be automatically resolved by Android Studio. If not, please update your Android Studio.
6. There’s a dropdown menu beside the Run button in Android Studio. That’s where you choose the target platform.
7. Enjoy!

## Testing
Sample unit tests can be found in [DefaultNavigationDispatcherTest.kt](composeApp/src/commonTest/kotlin/aung/thiha/photo/album/navigation/DefaultNavigationDispatcherTest.kt).

More test coverage and additional testing strategies (such as integration tests, instrumentation tests and snapshot tests) are on the roadmap.

## Roadmap
- Set up spacer sizes in terms of XXS, XS, S, M, L, XL and so on instead of hardcoded DPs to easily unify sizes
- Write integration tests to ensure new changes don't break the existing ones
- Write instrumentation tests to ensure things work as expected on real devices
- Use paging3 in PhotoListScreen to support pagination
- Figure out which snapshot test framework would be best suited for the project
- Set up snapshot tests
- Research the benefits of `com.android.kotlin.multiplatform.library` over `com.android.library`
- Implement [Talaiot](https://github.com/cdsap/Talaiot) to analyze Gradle tasks
- Implement AES-GCM encryption for session storage to enhance security
- Implement remote Gradle cache
- Unify the jvmToolchain version across all modules to make updating java versions easy
- Use [Spotless](https://github.com/diffplug/spotless) to sort the keys in version catalog to make searching keys easier

## CI/CD
- [iOS](https://github.com/AungThiha/iOSAppAccessAutomation)
- Android (Pending)

## Contributing
PRs and feedback welcome!

## License
Apache 2.0