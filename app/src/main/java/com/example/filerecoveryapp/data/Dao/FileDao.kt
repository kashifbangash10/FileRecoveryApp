package com.example.filerecoveryapp.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filerecoveryapp.data.model.FileEntity

@Dao
interface FileDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertFile(file: FileEntity)

        @Query("SELECT * FROM recovered_files WHERE fileType = :type")
        fun getFilesByType(type: String): LiveData<List<FileEntity>>
    }
