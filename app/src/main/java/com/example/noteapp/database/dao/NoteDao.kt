package com.example.noteapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.noteapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
      fun insertNote(note: Note)
    @Update
      fun updateNote(note: Note)
    @Delete
      fun deleteNote(note: Note)

    @Query("SELECT * FROM note_table")
    fun getAllNote(): Flow<List<Note>>

    @Query("SELECT * FROM note_table WHERE title_col LIKE :searchQuery")
    fun searchDatabase(searchQuery:String): Flow<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY  createdTime_col DESC")
    fun getAllSortedByCreatedTimeDESC() : Flow<List<Note>>
    @Query("SELECT * FROM note_table ORDER BY  createdTime_col ASC")
    fun getAllSortedByCreatedTimeASC() : Flow<List<Note>>
 }