# KMP Project

> ⚠️ **Project Setup Instructions Outdated**    
> 
> The instructions in *How To Set Up and Run the KMP Project.pdf* are outdated. Fleet IDE is no longer supported. Updated setup steps will be available before 14 June 2025.
> 
> **Side note:** Normally, setup instructions would be included in the README. However, because this project was originally submitted as a university assignment (where professors were unfamiliar with Markdown), the instructions were written in a PDF instead. The updated setup guide will be included directly in the README.    

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

## Testing
Sample unit tests can be found in [DefaultNavigationDispatcherTest.kt](composeApp/src/commonTest/kotlin/aung/thiha/photo/album/navigation/DefaultNavigationDispatcherTest.kt).

More test coverage and additional testing strategies (such as integration tests, instrumentation tests and snapshot tests) are on the roadmap.

## Roadmap
- Update setup instruction because Fleet IDE is no longer supported 
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