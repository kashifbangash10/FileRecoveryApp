package com.example.filerecoveryapp.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.data.FileRepository.FileRepository

import com.example.filerecoveryapp.data.FileRepository.VideoRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var fileRepository: FileRepository
    @Inject
    lateinit var videoRepository: VideoRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Checking permissions when the activity is created
        checkPermissions()
    }

    // Method to check necessary permissions based on Android version
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11+ check MANAGE_EXTERNAL_STORAGE permission
            if (!Environment.isExternalStorageManager()) {
                requestManageStoragePermission()
                return
            }
        } else {
            // For Android 10 and below check READ_EXTERNAL_STORAGE permission
            if (ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestReadStoragePermission()
                return
            }
        }

        // If all permissions are granted, load media folders
        loadMediaFolders()
    }

    // Request READ_EXTERNAL_STORAGE permission for Android 10 and below
    private fun requestReadStoragePermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    // Launcher to handle the READ_EXTERNAL_STORAGE permission request result
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.d("HomeActivity", "READ_EXTERNAL_STORAGE granted")
                loadMediaFolders()
            } else {
                Log.d("HomeActivity", "READ_EXTERNAL_STORAGE denied")
                Toast.makeText(this, "Permission denied. Please enable it in settings.", Toast.LENGTH_LONG).show()
                openAppSettings()
            }
        }

    // Request MANAGE_EXTERNAL_STORAGE permission for Android 11 and above
    private fun requestManageStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                // Intent to open the "Manage all files access" settings
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = Uri.parse("package:${packageName}")
                }
                // Start activity to request the permission
                requestManageStorageLauncher.launch(intent)
            } catch (e: Exception) {
                Log.e("HomeActivity", "Error opening Manage External Storage permission settings", e)
            }
        }
    }

    // Launcher to handle the result of the "Manage all files access" permission request
    @RequiresApi(Build.VERSION_CODES.R)
    private val requestManageStorageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (Environment.isExternalStorageManager()) {
                // If permission granted, load media
                Log.d("HomeActivity", "MANAGE_EXTERNAL_STORAGE permission granted")
                loadMediaFolders()
            } else {
                // If permission denied, show message
                Log.d("HomeActivity", "MANAGE_EXTERNAL_STORAGE permission denied")
                Toast.makeText(this, "Permission denied. Please enable it in settings.", Toast.LENGTH_LONG).show()
                openAppSettings()
            }
        }

    // Method to open the app settings page for the user to manually enable permissions
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:${packageName}")
        }
        startActivity(intent)
    }

    // Dummy method to simulate loading media folders (Replace this with actual logic)
    private fun loadMediaFolders() {
        // Logic to load media folders (images, videos, etc.) after permission is granted
        Toast.makeText(this, "Loading media folders...", Toast.LENGTH_SHORT).show()
        Log.d("HomeActivity", "Media folders loaded successfully")
    }
}
