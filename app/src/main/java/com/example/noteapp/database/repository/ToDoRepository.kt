package com.example.noteapp.database.repository

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.database.dao.ToDoDao
import com.example.noteapp.model.Note
import com.example.noteapp.model.ToDo
import kotlinx.coroutines.flow.Flow

class ToDoRepository(app : FragmentActivity?) {
    private val toDoDao: ToDoDao

    init {
        val noteDatabase : NoteDatabase = NoteDatabase.getInstance(app!!)
        toDoDao = noteDatabase.getToDoDao()
    }
    @get:JvmName("getAllToDos")
    val allToDo: LiveData<List<ToDo>> = toDoDao.getAllToDoLiveData()
    @Suppress("RedundantSuspendModifier")
    suspend fun insertToDo(toDo: ToDo) = toDoDao.insertToDo(toDo)

    fun getAllToDo():LiveData<List<ToDo>> = toDoDao.getAllToDoLiveData()

    suspend fun deleteToDo(toDo: ToDo) = toDoDao.deleteToDo(toDo)
    @Suppress("RedundantSuspendModifier")
    suspend fun updateToDo(toDo: ToDo) = toDoDao.updateToDo(toDo)


    fun getAllSortedCreatedTimeASC(): Flow<List<ToDo>> = toDoDao.getAllSortedByCreatedTimeASC()

    fun getAllSortedCreatedTimeDESC(): Flow<List<ToDo>> = toDoDao.getAllSortedByCreatedTimeDESC()
}