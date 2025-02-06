package com.example.filerecoveryapp.data.FileRepository

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.ScanResultAdaptor.VideoFolder
import android.database.Cursor
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VideoRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getVideoFolders(): List<VideoFolder> {
        val folderMap = mutableMapOf<String, MutableList<String>>()

        // Define the projection (fields) to fetch from the MediaStore
        val projection = arrayOf(
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media._ID
        )

        // URI for querying video media
        val videoUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        // Query for videos from the external storage
        val cursor: Cursor? = context.contentResolver.query(
            videoUri,
            projection,
            null,  // No selection criteria (fetch all videos)
            null,  // No selection args
            sortOrder
        )

        // If the cursor is null or empty, return an empty list
        if (cursor == null || cursor.count == 0) {
            Log.d("VideoRepository", "No videos found!")
            return emptyList()
        }

        // Process the cursor to group the videos by folder name
        cursor.use {
            val folderColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)

            while (it.moveToNext()) {
                val folderName = it.getString(folderColumn) ?: "Unknown"
                val videoId = it.getLong(idColumn)
                val videoUri = Uri.withAppendedPath(videoUri, videoId.toString()).toString() // Add video URI

                // Add the video to the appropriate folder list in the map
                folderMap.getOrPut(folderName) { mutableListOf() }.add(videoUri)
            }
        }

        // Return the list of VideoFolder objects (mapped from folderMap)
        return folderMap.map { (name, paths) ->
            VideoFolder(name, paths, paths.size)  // Mapping FolderItem to VideoFolder
        }
    }
}
