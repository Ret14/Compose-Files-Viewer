package com.example.filewatcher.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filewatcher.data.room.dao.FileDao
import com.example.filewatcher.data.room.entities.LocalFile

@Database(entities = [LocalFile::class], version = 1)
abstract class FilesDatabase : RoomDatabase() {
    abstract val files: FileDao
}