package com.example.filewatcher.presentation.files_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filewatcher.R
import com.example.filewatcher.domain.FileModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FilesScreen(viewModel: FilesScreenViewModel = viewModel()) {
    val externalPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    )
    if (externalPermissionState.allPermissionsGranted) {
        val fileList = viewModel.files.value

        BackHandler() {
            if (viewModel.currentPath.value != viewModel.rootPath)
                viewModel.navigateBack()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            CurrentPath(path = viewModel.currentPath.value)
            LazyColumn {
                items(items = fileList) {
                    FileCard(file = it) {
                        if (it.isDir) viewModel.navigateTo(it.absPath)
                    }
                }
            }
        }
    } else {
        Button(onClick = {
            externalPermissionState.launchMultiplePermissionRequest()
        }
        ) {
            
        }
    }
}

@Composable
private fun FileCard(file: FileModel, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (file.isDir)
            Icon(
                painter = painterResource(id = R.drawable.dir_icon),
                contentDescription = stringResource(R.string.dir_icon_description)
            )
        else
            Icon(
                painter = painterResource(id = R.drawable.file_icon),
                contentDescription = stringResource(R.string.file_icon_description)
            )
        Column {
            Text(text = file.fileName)
            Text(text = "${file.fileSize} KB")
            Text(text = file.creationDate.toString())
        }
    }
}

@Composable
private fun CurrentPath(path: String) {
    Text(text = path, modifier = Modifier.fillMaxWidth())
}
