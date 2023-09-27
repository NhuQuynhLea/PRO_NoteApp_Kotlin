package com.example.noteapp.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.database.repository.ToDoRepository
import com.example.noteapp.model.Note
import com.example.noteapp.model.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class ToDoViewModel(application: FragmentActivity?) : ViewModel() {
    private val repository : ToDoRepository = ToDoRepository(application)
    @get:JvmName("getAllToDos")
    val allToDo : LiveData<List<ToDo>> = repository.getAllToDo()

    fun insertToDo(toDo: ToDo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertToDo(toDo)
    }
    fun getAllToDo() : LiveData<List<ToDo>> = repository.getAllToDo()
    fun updateToDo(toDo: ToDo) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateToDo(toDo)
    }
    fun deleteToDo(toDo: ToDo) = viewModelScope.launch(Dispatchers.IO)  {
        repository.deleteToDo(toDo)
    }
    fun getAllSortedCreatedTimeASC() :LiveData<List<ToDo>> = repository.getAllSortedCreatedTimeASC().asLiveData()
    fun getAllSortedCreatedTimeDESC() :LiveData<List<ToDo>> = repository.getAllSortedCreatedTimeDESC().asLiveData()
    class ToDoViewModelFactory(private val application: FragmentActivity?) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ToDoViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}