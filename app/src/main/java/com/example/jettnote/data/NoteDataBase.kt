package com.example.jettnote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jettnote.model.Note
import com.example.jettnote.utils.DateConverter
import com.example.jettnote.utils.UUIDConverter

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NoteDataBase: RoomDatabase()
{
    abstract fun noteDao(): NoteDatabaseDao
}