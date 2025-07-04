package com.example.filerecoveryapp.domain.repository

import com.example.filerecoveryapp.domain.model.MediaFile
import com.example.filerecoveryapp.domain.model.MediaFolder
import com.example.filerecoveryapp.domain.model.MediaType
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun scanMediaFiles(type: MediaType): Flow<List<MediaFile>>
    suspend fun getMediaFolders(type: MediaType): List<MediaFolder>
    suspend fun getAllMediaFolders(): List<MediaFolder>
    suspend fun getStorageInfo(): StorageInfo
}

data class StorageInfo(
    val totalSpace: Long,
    val usedSpace: Long,
    val freeSpace: Long,
    val photoCount: Int,
    val videoCount: Int,
    val documentCount: Int
)