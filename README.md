# KMP Project

To run the project, refer to the instructions provided in the file "How To Set Up and Run the KMP Project.pdf", located in the root directory of this project.

## Get the Precompiled App
- [iOS - Setup Instructions](https://aungthiha.github.io/iOSAppAccessAutomation/pages/firebase-setup.html)
- Android - Compile from the source (for now)


## Roadmap
- Scope the ViewModels to NavGraph destinations so that they can become garbage collectable when a respective destination exits
- Move presentation layers into their own respective modules so they can be compiled in parallel with other modules and have clear boundaries with other modules
- Update setup instruction because Fleet is no longer a go-to IDE for KMP project
- Write integration tests to ensure new changes don't break the existing ones
- Write instrumentation tests to ensure things work as expected on real devices
- Figure which snapshot test framework would be best suited for the project
- Set up snapshot tests
- Implement AES-GCM encryption for session storage for security
- Unify the jvmToolchain version across all modules to make sure updating java versions easy
- Use spotless to sort the keys in version catalog to make searching keys easier

## CI/CD
- [iOS](https://aungthiha.github.io/iOSAppAccessAutomation/)
- Android (Pending)
