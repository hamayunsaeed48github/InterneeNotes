package com.example.internnotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM NoteEntity ORDER BY title ASC")
    fun getOrderedByTitle():Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity ORDER BY dateAdded")
    fun getOrderedByDateAdded():Flow<List<NoteEntity>>

}