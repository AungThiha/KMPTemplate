# KMP Project

> ⚠️ **Project Setup Instructions Outdated**  
> Instructions provided in the file "How To Set Up and Run the KMP Project.pdf" is outdated. Fleet is no longer a go-to IDE for KMP project. New instructions will be provided before 14 Jun 2025.

## Get the Precompiled App
- [iOS - Setup Instructions](https://aungthiha.github.io/iOSAppAccessAutomation/pages/firebase-setup.html)
- Android - Compile from the source (for now)

## Roadmap
- Update setup instruction because Fleet is no longer a go-to IDE for KMP project
- Write integration tests to ensure new changes don't break the existing ones
- Write instrumentation tests to ensure things work as expected on real devices
- Use paging3 in PhotoListScreen to support pagination
- Figure out which snapshot test framework would be best suited for the project
- Set up snapshot tests
- Implement [Talaiot](https://github.com/cdsap/Talaiot) to analyze Gradle tasks
- Implement AES-GCM encryption for session storage to enhance security
- Unify the jvmToolchain version across all modules to make updating java versions easy
- Use spotless to sort the keys in version catalog to make searching keys easier

## CI/CD
- [iOS](https://aungthiha.github.io/iOSAppAccessAutomation/)
- Android (Pending)
