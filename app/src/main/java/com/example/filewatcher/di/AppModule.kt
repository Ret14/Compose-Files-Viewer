package com.example.filewatcher.di

import android.content.Context
import androidx.room.Room
import com.example.filewatcher.data.repository.FileRepository
import com.example.filewatcher.data.repository.FileRepositoryImpl
import com.example.filewatcher.data.room.FilesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFilesDatabase(@ApplicationContext context: Context): FilesDatabase {
        return Room
            .databaseBuilder(context, FilesDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFileRepository(database: FilesDatabase): FileRepository {
        return FileRepositoryImpl(database)
    }
}
