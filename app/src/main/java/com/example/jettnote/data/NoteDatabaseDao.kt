package com.example.jettnote.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jettnote.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao
{
    @Query(value = "Select * from notes_tbl")
    fun getNotes(): Flow<List<Note>>

    @Query(value = "Select * from notes_tbl where id =:id")
    suspend fun getNoteById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query(value = "Delete from notes_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note: Note)
}