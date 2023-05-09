package com.example.filewatcher.domain

data class FileModel(
    val fileName: String,
    val fileSize: Long,
    val creationDate: Long,
    val absPath: String,
    val isDir: Boolean
)
