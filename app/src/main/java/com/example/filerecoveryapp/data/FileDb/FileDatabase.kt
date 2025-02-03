package com.example.filerecoveryapp.data.FileDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filerecoveryapp.data.Dao.FileDao
import com.example.filerecoveryapp.data.model.FileEntity

@Database(entities = [FileEntity::class], version = 1, exportSchema = false)

    abstract class FileDatabase : RoomDatabase() {
        abstract fun fileDao(): FileDao
    }
