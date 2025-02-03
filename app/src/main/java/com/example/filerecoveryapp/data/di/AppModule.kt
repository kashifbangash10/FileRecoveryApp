package com.example.filerecoveryapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.filerecoveryapp.data.Dao.FileDao
import com.example.filerecoveryapp.data.FileDb.FileDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
    @InstallIn(SingletonComponent::class)
    object AppModule {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): FileDatabase {
            return Room.databaseBuilder(context, FileDatabase::class.java, "file_db").build()
        }

        @Provides
        fun provideDao(database: FileDatabase): FileDao = database.fileDao()
    }
