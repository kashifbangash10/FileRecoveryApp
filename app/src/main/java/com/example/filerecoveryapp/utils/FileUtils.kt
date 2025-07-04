package com.example.filerecoveryapp.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

object FileUtils {

    fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(bytes.toDouble()) / log10(1024.0)).toInt()
        
        return DecimalFormat("#,##0.#").format(
            bytes / 1024.0.pow(digitGroups.toDouble())
        ) + " " + units[digitGroups]
    }

    fun getFileExtension(fileName: String): String {
        return fileName.substringAfterLast('.', "")
    }

    fun isImageFile(mimeType: String): Boolean {
        return mimeType.startsWith("image/")
    }

    fun isVideoFile(mimeType: String): Boolean {
        return mimeType.startsWith("video/")
    }

    fun isAudioFile(mimeType: String): Boolean {
        return mimeType.startsWith("audio/")
    }

    fun isDocumentFile(mimeType: String): Boolean {
        return mimeType.startsWith("application/") || mimeType.startsWith("text/")
    }

    fun getFileFromUri(context: Context, uri: Uri): File? {
        val cursor = context.contentResolver.query(
            uri,
            arrayOf(MediaStore.MediaColumns.DATA),
            null,
            null,
            null
        )
        
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                val filePath = it.getString(columnIndex)
                return File(filePath)
            }
        }
        
        return null
    }

    fun createRecoveryFolder(context: Context): File {
        val recoveryDir = File(context.getExternalFilesDir(null), "RecoveredFiles")
        if (!recoveryDir.exists()) {
            recoveryDir.mkdirs()
        }
        return recoveryDir
    }
}