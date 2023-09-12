package com.example.noteapp.database.repository

import androidx.fragment.app.FragmentActivity
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.database.dao.NoteDao
import com.example.noteapp.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(app: FragmentActivity?) {
    private val noteDao: NoteDao
    init{
        val noteDatabase : NoteDatabase = NoteDatabase.getInstance(app!!)
        noteDao = noteDatabase.getNoteDao()
    }
    val allNotes: Flow<List<Note>> = noteDao.getAllNote()
    @Suppress("RedundantSuspendModifier")
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    @Suppress("RedundantSuspendModifier")
    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    fun getAllNote():Flow<List<Note>> = noteDao.getAllNote()
    fun searchDatabase(searchQuery:String) :Flow<List<Note>> = noteDao.searchDatabase(searchQuery)
}