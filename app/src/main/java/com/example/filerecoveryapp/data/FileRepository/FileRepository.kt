package com.example.filerecoveryapp.data.FileRepository

import androidx.lifecycle.LiveData
import com.example.filerecoveryapp.data.Dao.FileDao
import com.example.filerecoveryapp.data.model.FileEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepository @Inject constructor(private val fileDao: FileDao) {

    suspend fun insertFile(file: FileEntity) = fileDao.insertFile(file)

    fun getFilesByType(type: String): LiveData<List<FileEntity>> = fileDao.getFilesByType(type)
}
