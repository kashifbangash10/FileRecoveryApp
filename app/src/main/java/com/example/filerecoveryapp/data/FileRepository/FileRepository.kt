package com.example.filerecoveryapp.data.FileRepository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getMediaFolders(): List<FolderItem> {
        val folderMap = mutableMapOf<String, MutableList<String>>()

        // Define the projections (fields) to fetch
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID
        )

        val contentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        // Query images and videos from the external storage
        val cursor: Cursor? = context.contentResolver.query(
            contentUri,
            projection,
            null,
            null,
            sortOrder
        )

        // If the cursor is null or empty, return an empty list
        if (cursor == null || cursor.count == 0) {
            Log.d("FileRepository", "No media found!")
            return emptyList()
        }

        // Process the cursor to group the images by folder name
        cursor.use {
            val folderColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (it.moveToNext()) {
                val folderName = it.getString(folderColumn) ?: "Unknown"
                val imageId = it.getLong(idColumn)
                val imageUri = Uri.withAppendedPath(contentUri, imageId.toString()).toString()

                // Add the image to the appropriate folder list in the map
                folderMap.getOrPut(folderName) { mutableListOf() }.add(imageUri)
            }
        }

        // Optionally, repeat the process for videos
        val videoUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val videoCursor: Cursor? = context.contentResolver.query(
            videoUri,
            projection,
            null,
            null,
            sortOrder
        )

        videoCursor?.use {
            val folderColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)

            while (it.moveToNext()) {
                val folderName = it.getString(folderColumn) ?: "Unknown"
                val videoId = it.getLong(idColumn)
                val videoUri = Uri.withAppendedPath(videoUri, videoId.toString()).toString()

                // Add the video to the appropriate folder list in the map
                folderMap.getOrPut(folderName) { mutableListOf() }.add(videoUri)
            }
        }

        // Return the list of FolderItem objects with the folder name, list of URIs, and count of items
        return folderMap.map { (name, paths) ->
            FolderItem(name, paths.toString(), paths.size)
        }
    }
}
