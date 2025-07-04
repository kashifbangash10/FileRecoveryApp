package com.example.filerecoveryapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaFile(
    val id: Long,
    val name: String,
    val path: String,
    val uri: String,
    val size: Long,
    val dateAdded: Long,
    val mimeType: String,
    val type: MediaType
) : Parcelable

enum class MediaType {
    IMAGE, VIDEO, DOCUMENT, AUDIO, OTHER
}

@Parcelize
data class MediaFolder(
    val name: String,
    val files: List<MediaFile>,
    val fileCount: Int,
    val totalSize: Long,
    val type: MediaType
) : Parcelable