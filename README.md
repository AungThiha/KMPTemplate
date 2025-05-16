# KMP Project

To run the project, refer to the instructions provided in the file "How To Set Up and Run the KMP Project.pdf", located in the root directory of this project.

## Apache License 2.0
License content can be found in `LICENSE-2.0.txt` in the root folder.

## Roadmap
- Scope the ViewModels to NavGraph destinations so that they can become garbage collectable when a destination exits
- Move presentation layers into their own respective modules so they can be compiled in parallel with other modules and have clear boundaries with other modules
- Implement AES-GCM encryption for session storage for security
- Unify the jvmToolchain version across all modules to make sure updating java versions easy
- Use spotless to sort the keys in version catalog to make searching keys easier
- Write integration tests and instrumentation tests to ensure new changes don't break the existing ones

## CI/CD
- [iOS](https://aungthiha.github.io/iOSAppAccessAutomation/)
- Android (Pending)
