package com.example.filerecoveryapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.filerecoveryapp.R
import com.example.filerecoveryapp.utils.PermissionHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted, continue with app functionality
        } else {
            showPermissionDeniedMessage()
        }
    }

    private val requestManageStorageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (PermissionHelper.hasStoragePermission(this)) {
            // Permission granted, continue with app functionality
        } else {
            showPermissionDeniedMessage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        if (!PermissionHelper.hasStoragePermission(this)) {
            // Request storage permission using the helper
            // Note: We need to adapt PermissionHelper to work with Activity
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                val intent = Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = android.net.Uri.parse("package:$packageName")
                }
                requestManageStorageLauncher.launch(intent)
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun showPermissionDeniedMessage() {
        Toast.makeText(
            this,
            "Storage permission is required for file recovery functionality",
            Toast.LENGTH_LONG
        ).show()
        
        // Optionally, show a dialog explaining why the permission is needed
        // and provide a button to open app settings
    }

    override fun onResume() {
        super.onResume()
        // Check permissions again when returning from settings
        if (!PermissionHelper.hasStoragePermission(this)) {
            // Show permission explanation or request again
        }
    }
}