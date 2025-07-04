package com.example.filerecoveryapp.data.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.os.StatFs
import android.provider.MediaStore
import com.example.filerecoveryapp.domain.model.MediaFile
import com.example.filerecoveryapp.domain.model.MediaFolder
import com.example.filerecoveryapp.domain.model.MediaType
import com.example.filerecoveryapp.domain.repository.MediaRepository
import com.example.filerecoveryapp.domain.repository.StorageInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaRepository {

    override suspend fun scanMediaFiles(type: MediaType): Flow<List<MediaFile>> = flow {
        val files = mutableListOf<MediaFile>()
        
        when (type) {
            MediaType.IMAGE -> {
                files.addAll(scanImages())
            }
            MediaType.VIDEO -> {
                files.addAll(scanVideos())
            }
            MediaType.DOCUMENT -> {
                files.addAll(scanDocuments())
            }
            MediaType.AUDIO -> {
                files.addAll(scanAudio())
            }
            MediaType.OTHER -> {
                files.addAll(scanOtherFiles())
            }
        }
        
        emit(files)
    }.flowOn(Dispatchers.IO)

    override suspend fun getMediaFolders(type: MediaType): List<MediaFolder> = withContext(Dispatchers.IO) {
        val files = when (type) {
            MediaType.IMAGE -> scanImages()
            MediaType.VIDEO -> scanVideos()
            MediaType.DOCUMENT -> scanDocuments()
            MediaType.AUDIO -> scanAudio()
            MediaType.OTHER -> scanOtherFiles()
        }
        
        groupFilesByFolder(files, type)
    }

    override suspend fun getAllMediaFolders(): List<MediaFolder> = withContext(Dispatchers.IO) {
        val allFolders = mutableListOf<MediaFolder>()
        
        MediaType.values().forEach { type ->
            allFolders.addAll(getMediaFolders(type))
        }
        
        allFolders
    }

    override suspend fun getStorageInfo(): StorageInfo = withContext(Dispatchers.IO) {
        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        val totalSpace = stat.blockSizeLong * stat.blockCountLong
        val freeSpace = stat.blockSizeLong * stat.availableBlocksLong
        val usedSpace = totalSpace - freeSpace

        val photoCount = getMediaCount(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val videoCount = getMediaCount(MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        val documentCount = getDocumentCount()

        StorageInfo(
            totalSpace = totalSpace,
            usedSpace = usedSpace,
            freeSpace = freeSpace,
            photoCount = photoCount,
            videoCount = videoCount,
            documentCount = documentCount
        )
    }

    private suspend fun scanImages(): List<MediaFile> = withContext(Dispatchers.IO) {
        val files = mutableListOf<MediaFile>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.MIME_TYPE
        )

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val mimeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn) ?: "Unknown"
                val path = it.getString(pathColumn) ?: continue
                val size = it.getLong(sizeColumn)
                val dateAdded = it.getLong(dateColumn)
                val mimeType = it.getString(mimeColumn) ?: "image/*"
                val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString()).toString()

                files.add(
                    MediaFile(
                        id = id,
                        name = name,
                        path = path,
                        uri = uri,
                        size = size,
                        dateAdded = dateAdded,
                        mimeType = mimeType,
                        type = MediaType.IMAGE
                    )
                )
            }
        }
        files
    }

    private suspend fun scanVideos(): List<MediaFile> = withContext(Dispatchers.IO) {
        val files = mutableListOf<MediaFile>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.MIME_TYPE
        )

        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.DATE_ADDED} DESC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
            val mimeColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn) ?: "Unknown"
                val path = it.getString(pathColumn) ?: continue
                val size = it.getLong(sizeColumn)
                val dateAdded = it.getLong(dateColumn)
                val mimeType = it.getString(mimeColumn) ?: "video/*"
                val uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id.toString()).toString()

                files.add(
                    MediaFile(
                        id = id,
                        name = name,
                        path = path,
                        uri = uri,
                        size = size,
                        dateAdded = dateAdded,
                        mimeType = mimeType,
                        type = MediaType.VIDEO
                    )
                )
            }
        }
        files
    }

    private suspend fun scanDocuments(): List<MediaFile> = withContext(Dispatchers.IO) {
        val files = mutableListOf<MediaFile>()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MIME_TYPE
        )

        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ? OR " +
                "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ? OR " +
                "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ?"
        
        val selectionArgs = arrayOf("application/pdf", "application/msword", "text/%")

        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            selectionArgs,
            "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)
            val mimeColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn) ?: "Unknown"
                val path = it.getString(pathColumn) ?: continue
                val size = it.getLong(sizeColumn)
                val dateAdded = it.getLong(dateColumn)
                val mimeType = it.getString(mimeColumn) ?: "application/octet-stream"
                val uri = Uri.withAppendedPath(MediaStore.Files.getContentUri("external"), id.toString()).toString()

                files.add(
                    MediaFile(
                        id = id,
                        name = name,
                        path = path,
                        uri = uri,
                        size = size,
                        dateAdded = dateAdded,
                        mimeType = mimeType,
                        type = MediaType.DOCUMENT
                    )
                )
            }
        }
        files
    }

    private suspend fun scanAudio(): List<MediaFile> = withContext(Dispatchers.IO) {
        val files = mutableListOf<MediaFile>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.MIME_TYPE
        )

        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Audio.Media.DATE_ADDED} DESC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
            val mimeColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn) ?: "Unknown"
                val path = it.getString(pathColumn) ?: continue
                val size = it.getLong(sizeColumn)
                val dateAdded = it.getLong(dateColumn)
                val mimeType = it.getString(mimeColumn) ?: "audio/*"
                val uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id.toString()).toString()

                files.add(
                    MediaFile(
                        id = id,
                        name = name,
                        path = path,
                        uri = uri,
                        size = size,
                        dateAdded = dateAdded,
                        mimeType = mimeType,
                        type = MediaType.AUDIO
                    )
                )
            }
        }
        files
    }

    private suspend fun scanOtherFiles(): List<MediaFile> = withContext(Dispatchers.IO) {
        val files = mutableListOf<MediaFile>()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MIME_TYPE
        )

        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ?"
        val selectionArgs = arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_NONE.toString())

        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            selectionArgs,
            "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val pathColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)
            val mimeColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn) ?: "Unknown"
                val path = it.getString(pathColumn) ?: continue
                val size = it.getLong(sizeColumn)
                val dateAdded = it.getLong(dateColumn)
                val mimeType = it.getString(mimeColumn) ?: "application/octet-stream"
                val uri = Uri.withAppendedPath(MediaStore.Files.getContentUri("external"), id.toString()).toString()

                files.add(
                    MediaFile(
                        id = id,
                        name = name,
                        path = path,
                        uri = uri,
                        size = size,
                        dateAdded = dateAdded,
                        mimeType = mimeType,
                        type = MediaType.OTHER
                    )
                )
            }
        }
        files
    }

    private fun groupFilesByFolder(files: List<MediaFile>, type: MediaType): List<MediaFolder> {
        val folderMap = files.groupBy { file ->
            val path = file.path
            val lastSlashIndex = path.lastIndexOf('/')
            if (lastSlashIndex != -1) {
                path.substring(0, lastSlashIndex).substringAfterLast('/')
            } else {
                "Unknown"
            }
        }

        return folderMap.map { (folderName, folderFiles) ->
            MediaFolder(
                name = folderName,
                files = folderFiles,
                fileCount = folderFiles.size,
                totalSize = folderFiles.sumOf { it.size },
                type = type
            )
        }.sortedByDescending { it.fileCount }
    }

    private fun getMediaCount(uri: Uri): Int {
        var count = 0
        val cursor = context.contentResolver.query(
            uri,
            arrayOf(MediaStore.MediaColumns._ID),
            null,
            null,
            null
        )
        cursor?.use {
            count = it.count
        }
        return count
    }

    private fun getDocumentCount(): Int {
        var count = 0
        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ? OR " +
                "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ? OR " +
                "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ?"
        val selectionArgs = arrayOf("application/pdf", "application/msword", "text/%")

        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            arrayOf(MediaStore.Files.FileColumns._ID),
            selection,
            selectionArgs,
            null
        )
        cursor?.use {
            count = it.count
        }
        return count
    }
}