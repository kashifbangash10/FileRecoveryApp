# File Recovery App - Preview Guide

## Prerequisites
1. **Download Android Studio**: Get it from https://developer.android.com/studio
2. **Install Android SDK**: Android Studio will guide you through this
3. **Enable Developer Options** on your phone (optional, for physical device testing)

## Steps to Preview the App

### Option 1: Using Android Emulator (Virtual Device)
1. Open Android Studio
2. Click "Open an existing project" and select your project folder
3. Wait for Gradle sync to complete
4. Click "AVD Manager" (Android Virtual Device Manager)
5. Create a new virtual device:
   - Choose a device (e.g., Pixel 6)
   - Select API level 30+ (Android 11+)
   - Download the system image if needed
6. Start the emulator
7. Click the "Run" button (green play icon) in Android Studio
8. Select your emulator as the deployment target

### Option 2: Using Physical Android Device
1. Enable "Developer Options" on your Android device:
   - Go to Settings > About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings > Developer Options
   - Enable "USB Debugging"
2. Connect your device via USB
3. Allow USB debugging when prompted
4. In Android Studio, click "Run"
5. Select your physical device

### Option 3: Preview UI Only (Layout Inspector)
1. In Android Studio, open any layout file (e.g., `fragment_home.xml`)
2. Click the "Design" tab to see visual preview
3. Use "Interactive Preview" to see how UI responds

## Build Commands (Terminal)
```bash
# Clean and build the project
./gradlew clean build

# Install debug APK on connected device
./gradlew installDebug

# Generate APK file
./gradlew assembleDebug
```

## Troubleshooting
- **Gradle sync issues**: File > Sync Project with Gradle Files
- **Missing dependencies**: Check internet connection and sync again
- **Emulator slow**: Allocate more RAM in AVD settings
- **Permission issues**: Grant storage permissions when app starts

## App Features to Test
1. **Splash Screen**: App startup animation
2. **Onboarding**: Swipe through introduction screens
3. **Home Screen**: Storage capacity display and feature cards
4. **Photo Recovery**: Tap "Recover Photos" and test scanning
5. **Video Recovery**: Test video file detection
6. **Permissions**: Test storage permission requests
7. **File Display**: Check if recovered files show properly

## Expected Behavior
- App requests storage permissions on first launch
- Scanning shows progress with Lottie animations
- Files are displayed in grid format with thumbnails
- Navigation works between all screens