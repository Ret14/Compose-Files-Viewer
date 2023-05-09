package com.example.filewatcher.repository.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.filewatcher.repository.room.entities.LocalFile

@Dao
interface FileDao {
    @Query(
        """
            SELECT * 
            FROM file A INNER JOIN file B
            ON A.absPath = B.absPath
            WHERE A.hashCode <> B.hashCode
        """
    )
    suspend fun readChangedFiles(): List<LocalFile>

    @Query("DELETE from file WHERE file.isLastLaunchAdded = 0")
    suspend fun deleteDeprecatedFiles()

    @Query("UPDATE file SET isLastLaunchAdded=0")
    suspend fun markAllDeprecated()
}