package com.example.filerecoveryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recovered_files")
data class FileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val filePath: String,
    val fileType: String,
    val fileSize: Long
)