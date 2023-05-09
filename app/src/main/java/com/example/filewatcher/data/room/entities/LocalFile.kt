package com.example.filewatcher.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filewatcher.domain.FileModel
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

@Entity(tableName = "file")
data class LocalFile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val absPath: String,
    val hash: Int,
    val fileName: String,
    val isDir: Boolean,
    val fileSize: Long,
    val creationDate: Long,
    val isLastLaunchAdded: Boolean = true
)

fun LocalFile.toFileModel() = FileModel(
    fileName, fileSize, creationDate, absPath, isDir
)
