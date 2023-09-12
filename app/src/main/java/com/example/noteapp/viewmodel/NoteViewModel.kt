package com.example.noteapp.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.database.repository.NoteRepository
import com.example.noteapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: FragmentActivity?) : ViewModel()  {
    private val repository: NoteRepository = NoteRepository(application)
    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO)  {
        repository.deleteNote(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(note)
    }
    fun getAllNote() : LiveData<List<Note>> = repository.getAllNote().asLiveData()

    fun searchDatabase(searchQuery:String):LiveData<List<Note>> = repository.searchDatabase(searchQuery).asLiveData()
    class NoteViewModelFactory(private val application: FragmentActivity?) :ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}