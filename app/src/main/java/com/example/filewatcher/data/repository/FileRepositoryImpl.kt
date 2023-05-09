package com.example.filewatcher.data.repository

import com.example.filewatcher.data.room.FilesDatabase
import com.example.filewatcher.data.room.entities.LocalFile
import com.example.filewatcher.data.room.entities.toFileModel
import com.example.filewatcher.domain.FileModel
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

class FileRepositoryImpl(
    database: FilesDatabase
): FileRepository {

    private val filesDao = database.files

    override suspend fun addManyFiles(files: List<File>) {
        filesDao.addManyFiles(files.map { it.toLocalFile() })
    }

    override suspend fun addFile(file: File) {
        filesDao.addFile(file.toLocalFile())
    }

    override suspend fun getChangedFiles(): List<FileModel> {
        return filesDao.readChangedFiles().map { it.toFileModel() }
    }

    override suspend fun refreshFiles() {
        filesDao.deleteDeprecatedFiles()
        filesDao.markAllDeprecated()
    }

    private fun File.toLocalFile() = LocalFile(
        absPath = absolutePath,
        hashCode = hashCode(),
        fileName = name,
        isDir = isDirectory,
        fileSize = length(),
        creationDate = getCreationDate(this).time,
    )
    private fun getCreationDate(file: File): Date {
        val date = Files.readAttributes(file.toPath(), BasicFileAttributes::class.java).creationTime()
        return Date(date.toMillis())
    }
}