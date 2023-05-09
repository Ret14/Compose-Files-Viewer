package com.example.filewatcher.presentation.files_screen

import android.os.Environment
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.filewatcher.domain.FileModel
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.text.SimpleDateFormat
import java.util.*

class FilesScreenViewModel : ViewModel() {

    val rootPath = Environment.getExternalStorageDirectory().absolutePath
    val currentPath = mutableStateOf(rootPath)

    private val _files: MutableState<List<FileModel>> =
        mutableStateOf(emptyList())
    val files: State<List<FileModel>> = _files


    init {
        navigateTo(rootPath)
    }
    fun navigateBack() {
        val backPath = currentPath.value.dropLastWhile { it != '/' }.dropLast(1)
        navigateTo(backPath)
    }
    fun navigateTo(path: String) {
        currentPath.value = path
        val fileList = File(currentPath.value).listFiles()?.toMutableList() ?: emptyList<File>()
        fileList.sortedBy { it.name }
        _files.value = fileList.map {
            FileModel(
                it.name,
                it.length() / 1024,
                getCreationDate(it).time,
                it.absolutePath,
                it.isDirectory
            )
        }
    }
    private fun getCreationDate(file: File): Date {
        val date = Files.readAttributes(file.toPath(), BasicFileAttributes::class.java).creationTime()
        return Date(date.toMillis())
    }

    private fun formatDate(date: Date): String {
        val pattern = "dd-MM-yyyy HH:mm:ss"
        return SimpleDateFormat(pattern, Locale.UK).format(date)
    }
}