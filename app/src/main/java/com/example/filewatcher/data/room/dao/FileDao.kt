package com.example.filewatcher.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filewatcher.data.room.entities.LocalFile

@Dao
interface FileDao {
    @Query(
        """
            SELECT * 
            FROM file A INNER JOIN file B
            ON A.absPath = B.absPath
            WHERE A.hash <> B.hash
        """
    )
    suspend fun readChangedFiles(): List<LocalFile>

    @Query("DELETE from file WHERE file.isLastLaunchAdded = 0")
    suspend fun deleteDeprecatedFiles()

    @Query("UPDATE file SET isLastLaunchAdded=0")
    suspend fun markAllDeprecated()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addManyFiles(files: List<LocalFile>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFile(file: LocalFile)
}