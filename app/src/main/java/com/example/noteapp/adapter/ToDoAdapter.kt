package com.example.noteapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.TodoItemBinding
import com.example.noteapp.model.ToDo
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random


class ToDoAdapter(
    private val context: Context,
    private val editTodo: (ToDo) ->Unit,
    private val completedTodo: (ToDo) ->Unit
): RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    val DILL_CALLBACK: DiffUtil.ItemCallback<ToDo> = object : DiffUtil.ItemCallback<ToDo>(){
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem == newItem
        }

    }

    var mDiffer2: AsyncListDiffer<ToDo> = AsyncListDiffer<ToDo>(this, DILL_CALLBACK )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mDiffer2.currentList.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.onBindToDo(mDiffer2.currentList[position])
    }
    fun setToDo (toDoList: List<ToDo>){
        mDiffer2.submitList(toDoList)
    }

    inner class ToDoViewHolder(itemView: TodoItemBinding) :RecyclerView.ViewHolder(itemView.root){
        private val title: TextView = itemView.txtTodoTitle
        private val completedDate: TextView = itemView.txtTodoCompletedDate
        private val check : ImageButton = itemView.checkbox
        private val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        private val layoutItem: LinearLayout = itemView.linearLayout

        fun onBindToDo(toDo: ToDo){
            title.text = toDo.title
            check.setImageResource(R.drawable.baseline_radio_button_unchecked_24)
            if(toDo.completedDate != null)
                completedDate.text = toDo.completedDate
            else
                completedDate.text = " "
            layoutItem.setBackgroundColor(itemView.resources.getColor(getRandomColor(),null))
            layoutItem.setOnClickListener {
                editTodo(toDo)
            }
            check.setOnClickListener {
                check.setImageResource(R.drawable.baseline_check_circle_24)
                completedTodo(toDo)
            }

        }

    }
    fun getRandomColor(): Int{
        val colors = ArrayList<Int>()
       // colors.add(R.color.random1)
        colors.add(R.color.random2)
        colors.add(R.color.random3)
       // colors.add(R.color.random4)
        colors.add(R.color.random5)
        colors.add(R.color.random6)

        val random  = Random.nextInt(colors.size)
        return colors[random]
    }
}