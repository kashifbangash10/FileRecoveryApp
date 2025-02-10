package com.example.filerecoveryapp.data.FileRepository
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.filerecoveryapp.ui.Adaptor.folderAdaptor.FolderItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getMediaFolders(): List<FolderItem> {
        val folderMap = mutableMapOf<String, MutableList<String>>()

        // Common projection for both images & videos
        val projection = arrayOf(
            MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns._ID
        )

        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"

        // 🔹 Fetch Images
        fetchMedia(
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection = projection,
            sortOrder = sortOrder,
            folderMap = folderMap
        )

        // 🔹 Fetch Videos
        fetchMedia(
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection = projection,
            sortOrder = sortOrder,
            folderMap = folderMap
        )

        // 🔹 Convert Map to List of FolderItem
        return folderMap.map { (name, paths) ->
            FolderItem(name, paths, paths.size)
        }
    }

    private fun fetchMedia(
        uri: Uri,
        projection: Array<String>,
        sortOrder: String,
        folderMap: MutableMap<String, MutableList<String>>
    ) {
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, sortOrder)

        cursor?.use {
            val folderColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
            val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)

            while (it.moveToNext()) {
                val folderName = it.getString(folderColumn) ?: "Unknown"
                val fileId = it.getLong(idColumn)
                val fileUri = Uri.withAppendedPath(uri, fileId.toString()).toString()

                folderMap.getOrPut(folderName) { mutableListOf() }.add(fileUri)
            }
        }
    }
}
