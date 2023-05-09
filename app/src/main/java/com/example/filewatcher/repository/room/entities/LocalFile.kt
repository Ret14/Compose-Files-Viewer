package com.example.filewatcher.repository.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filewatcher.domain.FileModel

@Entity(tableName = "file")
data class LocalFile(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val absPath: String,
    val hashCode: Long,
    val fileName: String,
    val isDir: Boolean,
    val fileSize: Long,
    val creationDate: Long,
    val isLastLaunchAdded: Boolean
) {
    fun LocalFile.toFileModel() = FileModel(
        fileName, fileSize, creationDate, absPath, isDir
    )
}
