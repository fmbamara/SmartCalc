    SmartCalc - Android 11 (SDK 30) Java project

    This archive contains the SmartCalc app (calculator + converter + history).


    How to build locally:

- Open in Android Studio
- Sync Gradle
- Run on device or emulator

CI (GitHub Actions): .github/workflows/android-build.yml included.

Signing release builds in CI:

You can store your keystore as a GitHub secret (BASE64) and the workflow will decode it.
Set these repo secrets:
- KEYSTORE_BASE64 (base64-encoded keystore file)
- KEYSTORE_PASSWORD
- KEY_ALIAS
- KEY_PASSWORD

The included workflow writes the keystore file to ./keystore.jks and sets project properties so Gradle can sign.
See .github/workflows/android-build.yml for details.
