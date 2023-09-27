package com.example.noteapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.model.Note
import com.example.noteapp.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToDo(todo: ToDo)

    @Query("SELECT * FROM todo_table")
    fun getAllToDoLiveData(): LiveData<List<ToDo>>
    @Update
    fun updateToDo(todo: ToDo)

    @Delete
    fun deleteToDo(todo: ToDo)
    @Query("SELECT * FROM todo_table ORDER BY  createdTime_col DESC")
    fun getAllSortedByCreatedTimeDESC() : Flow<List<ToDo>>
    @Query("SELECT * FROM todo_table ORDER BY  createdTime_col ASC")
    fun getAllSortedByCreatedTimeASC() : Flow<List<ToDo>>
}