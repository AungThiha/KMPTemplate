# KMP Project

This branch is not a starter template. This is for Android engineers who want to start learning KMP. Please, follow [the learning guide](https://www.notion.so/KMP-Learning-Guide-206fc55144a1805abeb4e2463a571194) documented on Notion to get started

## Set up the Project
1. Follow [the official guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/quickstart.html#set-up-the-environment) to set up the KMP development environment.
2. Clone the repo.
3. Setup Firebase for Android by following [the official guide](https://firebase.google.com/docs/android/setup). Place `google-services.json` in `composeApp` directory.
4. Setup Firebase for iOS by following [the official guide](https://firebase.google.com/docs/ios/setup). The initialization code is already added. Just place `GoogleService-Info.plist` into `iosApp/iosApp/` directory.
   Open the project in Android Studio.
5. If you get a `NoToolchainAvailableException`, install [JDK 11](https://formulae.brew.sh/cask/zulu@11) using Homebrew. If you don’t get this error, you can skip this step.
6. Swift packages should be automatically resolved by Android Studio. If not, please update your Android Studio.
7. There’s a dropdown menu beside the Run button in Android Studio. That’s where you choose the target platform.
8. Enjoy!

## License
Apache 2.0