package com.example.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.database.dao.NoteDao
import com.example.noteapp.database.dao.ToDoDao
import com.example.noteapp.model.Note
import com.example.noteapp.model.ToDo

@Database(entities = [Note::class, ToDo::class], version = 6, exportSchema = false)
abstract class NoteDatabase :RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    abstract fun getToDoDao(): ToDoDao
    companion object{
        @Volatile
        private var instace: NoteDatabase ?= null
        fun getInstance(context: Context) : NoteDatabase{
            if(instace == null){
                instace = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "NoteDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instace!!
        }
    }
}