package com.example.filerecoveryapp.data.FileRepository



import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OtherFileRepository @Inject constructor(@ApplicationContext private val context: Context) {


    fun getMediaFolders1(): List<FolderItem> {
        val folderMap = mutableMapOf<String, MutableList<String>>()

        // Fetch images
        fetchFilesFromMediaStore(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            folderMap
        )

        // Fetch videos
        fetchFilesFromMediaStore(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            folderMap
        )

        return folderMap.map { (name, paths) ->
            FolderItem(name, paths.toString(), paths.size)
        }
    }


    private fun fetchFilesFromMediaStore(contentUri: Uri, folderMap: MutableMap<String, MutableList<String>>) {
        val projection = arrayOf(
            MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns._ID
        )

        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"

        val cursor: Cursor? = context.contentResolver.query(
            contentUri, projection, null, null, sortOrder
        )

        cursor?.use {
            val folderColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
            val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)

            while (it.moveToNext()) {
                val folderName = it.getString(folderColumn) ?: "Unknown"
                val fileId = it.getLong(idColumn)
                val fileUri = Uri.withAppendedPath(contentUri, fileId.toString()).toString()

                folderMap.getOrPut(folderName) { mutableListOf() }.add(fileUri)
            }
        }
    }


    fun getOtherFiles(): List<FolderItem> {
        val folderMap = mutableMapOf<String, MutableList<String>>()

        val contentUri: Uri = MediaStore.Files.getContentUri("external")

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA
        )

        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE} IS NULL"
        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

        val cursor: Cursor? = context.contentResolver.query(
            contentUri, projection, selection, null, sortOrder
        )

        cursor?.use {
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)

            while (it.moveToNext()) {
                val fileName = it.getString(nameColumn) ?: "Unknown"
                val filePath = it.getString(pathColumn) ?: continue

                val folderName = filePath.substringBeforeLast("/") // Extract folder name

                folderMap.getOrPut(folderName) { mutableListOf() }.add(filePath)
            }
        }

        return folderMap.map { (name, paths) ->
            FolderItem(name, paths.toString(), paths.size)
        }
    }
}
