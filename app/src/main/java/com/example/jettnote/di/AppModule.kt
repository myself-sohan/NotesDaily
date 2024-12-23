package com.example.jettnote.di

import android.content.Context
import androidx.room.Room
import com.example.jettnote.data.NoteDataBase
import com.example.jettnote.data.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase: NoteDataBase): NoteDatabaseDao = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDataBase
    =
        Room.databaseBuilder(
            context,
            NoteDataBase::class.java,
            "notes_db"
        ).fallbackToDestructiveMigration()
            .build()
}


