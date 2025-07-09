# Quick Run Commands

## Option 1: Android Studio (Recommended)
1. **Open Android Studio**
2. **File > Open** → Select project folder
3. **Wait for Gradle sync** (5-10 minutes first time)
4. **Tools > AVD Manager** → Create virtual device (Pixel 6, API 30+)
5. **Click Run button** (green play icon)
6. **Select emulator** and wait for app to launch

## Option 2: Command Line
```bash
# Navigate to project directory
cd FileRecoveryApp

# Make gradlew executable (Linux/Mac)
chmod +x gradlew

# Clean and build
./gradlew clean build

# Install on connected device/emulator
./gradlew installDebug

# Or build APK file
./gradlew assembleDebug
# APK will be in: app/build/outputs/apk/debug/
```

## Option 3: Direct APK Install
```bash
# If you have ADB installed
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Expected App Flow:
1. **Splash Screen** (2s) → **Onboarding** (3 screens) → **Home Dashboard**
2. **Test Features**: Tap "Recover Photos" → See scanning animation → View results
3. **Permissions**: App will request storage access permissions

## Troubleshooting:
- **Gradle issues**: File > Sync Project with Gradle Files
- **Emulator slow**: Increase RAM in AVD settings  
- **Build errors**: ./gradlew clean then rebuild
- **Permissions**: Grant storage access when prompted

**The app is ready to run and showcases a modern file recovery interface with smooth animations and Material Design!**