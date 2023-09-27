package com.example.noteapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.R
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "todo_table")
data class ToDo(
    @ColumnInfo(name  = "title_col") var title: String,
    @ColumnInfo(name = "createdTime_col") var createdTime: String,
    @ColumnInfo(name  = "dueTime_col")var dueTime: String?,
    @ColumnInfo(name  = "completed_col")var completedDate: String?,
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0




}