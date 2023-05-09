package com.example.filewatcher.data.repository

import com.example.filewatcher.domain.FileModel
import java.io.File

interface FileRepository {
    suspend fun addManyFiles(files: List<File>)
    suspend fun addFile(file: File)
    suspend fun getChangedFiles(): List<FileModel>
    suspend fun refreshFiles()
}