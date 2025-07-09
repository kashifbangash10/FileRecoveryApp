# File Recovery App - Complete Setup and Preview Guide

## Project Overview
This is a modern Android file recovery application built with:
- **Kotlin** with **Jetpack Compose** architecture
- **Hilt** for dependency injection
- **Room** database for local storage
- **Navigation Component** for screen navigation
- **Lottie** animations for smooth UI
- **Material Design 3** components

## Prerequisites Setup

### 1. Install Android Studio
- Download from: https://developer.android.com/studio
- Install with default settings
- Ensure Android SDK is installed (API level 24-34)

### 2. System Requirements
- **Minimum**: 8GB RAM, 4GB free disk space
- **Recommended**: 16GB RAM, SSD storage
- **OS**: Windows 10+, macOS 10.14+, or Linux

## Quick Start Guide

### Step 1: Open Project
```bash
# Clone or extract the project
# Open Android Studio
# File > Open > Select project folder
# Wait for Gradle sync (this may take 5-10 minutes first time)
```

### Step 2: Setup Virtual Device (Recommended)
1. **Tools > AVD Manager**
2. **Create Virtual Device**
3. **Choose Device**: Pixel 6 or Pixel 7
4. **System Image**: API 30 (Android 11) or higher
5. **Download** system image if needed
6. **Finish** and **Start** emulator

### Step 3: Run the App
1. **Click Run button** (green play icon)
2. **Select deployment target** (your emulator)
3. **Wait for build** (first build takes longer)
4. **App launches** automatically

## App Flow and Features to Test

### 1. Splash Screen (2 seconds)
- Beautiful animated logo
- "All Recovery" branding
- Auto-transitions to onboarding

### 2. Onboarding Screens (3 screens)
- **Screen 1**: "Automatically Search & Recovery all"
- **Screen 2**: "Recover easily and quickly" 
- **Screen 3**: "Recover all deleted photo and video"
- **Navigation**: Swipe or tap "Next"
- **Skip**: Tap "Skip" to jump to main app

### 3. Home Screen - Main Dashboard
**Storage Capacity Card:**
- Shows used/total storage (e.g., "Used 31GB / 119GB")
- Animated progress bar
- Color-coded indicators for Photos, Videos, Other

**Feature Cards:**
- **Recover Photos**: Scan and recover image files
- **Recover Videos**: Find deleted video files  
- **Recover Other Files**: Documents, APKs, etc.
- **Recovered Files**: View previously recovered items

### 4. Recovery Process
**Photo Recovery Flow:**
1. Tap "Recover Photos"
2. Shows scanning animation (Lottie)
3. "Tap to scan photos" instruction
4. Progress bar during scan
5. Results screen with folders
6. File selection interface

**Expected Behavior:**
- Requests storage permissions
- Scans device storage
- Groups files by folders
- Shows file counts and sizes
- Allows bulk selection

### 5. Permissions Testing
The app requires these permissions:
- `READ_MEDIA_IMAGES`
- `MANAGE_EXTERNAL_STORAGE` (Android 11+)
- `READ_EXTERNAL_STORAGE` (Android 10-)
- `WRITE_EXTERNAL_STORAGE`

## Testing on Physical Device

### Enable Developer Mode:
1. **Settings > About Phone**
2. **Tap "Build Number" 7 times**
3. **Settings > Developer Options**
4. **Enable "USB Debugging"**

### Connect and Run:
1. **Connect via USB**
2. **Allow USB debugging** when prompted
3. **Run from Android Studio**
4. **Select your device**

## Build Commands (Terminal)

```bash
# Navigate to project directory
cd /path/to/FileRecoveryApp

# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Run tests
./gradlew test
```

## Key Files to Understand

### Main Activities:
- `SplashActivity.kt` - App entry point
- `HomeActivity.kt` - Main container with navigation

### Core Fragments:
- `HomeFragment.kt` - Dashboard with storage info
- `RecoverPhotoFragment.kt` - Photo recovery interface
- `ScanResultFragment.kt` - Shows scan results
- `OnboardingFragment.kt` - Introduction screens

### Data Layer:
- `MediaRepository.kt` - Handles file scanning
- `MediaViewModel.kt` - UI state management
- `FileDatabase.kt` - Local storage

## Troubleshooting Common Issues

### Gradle Sync Problems:
```bash
# In Android Studio:
File > Sync Project with Gradle Files
# Or clean and rebuild:
Build > Clean Project
Build > Rebuild Project
```

### Emulator Issues:
- **Slow performance**: Increase RAM allocation in AVD settings
- **Won't start**: Check virtualization is enabled in BIOS
- **App crashes**: Check Logcat for error messages

### Permission Issues:
- Grant storage permissions when prompted
- For Android 11+: Enable "All files access" in settings
- Test on different Android versions (API 24-34)

### Build Errors:
```bash
# Clear Gradle cache
./gradlew clean
rm -rf ~/.gradle/caches/

# Update dependencies
# Check build.gradle.kts for version conflicts
```

## Expected App Behavior

### ✅ Working Features:
- Smooth splash screen animation
- Interactive onboarding with ViewPager2
- Storage capacity calculation and display
- File scanning with progress indicators
- Folder-based file organization
- Material Design 3 UI components
- Dark/Light theme support
- Responsive layouts for different screen sizes

### 🔄 Simulated Features:
- Actual file recovery (shows UI flow)
- Progress animations during scanning
- File thumbnail generation
- Storage permission handling

## Performance Notes

### First Launch:
- **Build time**: 3-5 minutes (downloads dependencies)
- **App startup**: 2-3 seconds (splash + initialization)
- **Scanning**: Simulated with progress animation

### Subsequent Launches:
- **Build time**: 30-60 seconds
- **App startup**: 1-2 seconds
- **Navigation**: Instant between screens

## UI/UX Highlights

### Design System:
- **Primary Color**: #587EF5 (Blue)
- **Typography**: Roboto font family
- **Spacing**: 8dp grid system
- **Animations**: Lottie for smooth interactions

### Responsive Design:
- Supports phones and tablets
- Adaptive layouts for different orientations
- Consistent spacing and typography

## Next Steps for Development

### To Make Fully Functional:
1. **Implement real file scanning** (currently simulated)
2. **Add actual file recovery logic**
3. **Integrate with device storage APIs**
4. **Add file preview capabilities**
5. **Implement backup/restore features**

### Testing Recommendations:
1. **Test on multiple devices** (different screen sizes)
2. **Test different Android versions** (API 24-34)
3. **Test with/without storage permissions**
4. **Test with large file collections**
5. **Test memory usage during scanning**

## Support and Resources

### Documentation:
- [Android Developer Guide](https://developer.android.com/guide)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Material Design Guidelines](https://material.io/design)

### Debugging:
- Use **Logcat** in Android Studio for runtime logs
- **Layout Inspector** for UI debugging
- **Memory Profiler** for performance analysis

---

**Ready to run!** Follow the steps above and you'll have the File Recovery app running smoothly. The app showcases modern Android development practices with a polished UI and smooth user experience.