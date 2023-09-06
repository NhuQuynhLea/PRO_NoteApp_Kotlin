package com.example.noteapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.NoteItemBinding
import com.example.noteapp.model.Note


class NoteAdapter (
    private val context: Context,
    private val onClick: (Note) -> Unit,
    private val onDelete: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
  //  private var noteList: List<Note> = listOf()
   val DILL_CALLBACK: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
     val mDiffer: AsyncListDiffer<Note> = AsyncListDiffer<Note>(this, DILL_CALLBACK )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       // holder.onBind( noteList[position])
        holder.onBind(mDiffer.currentList[position])
    }
    fun setNotes (noteList: List<Note>){
//        this.noteList = noteList
//        notifyDataSetChanged()
        mDiffer.submitList(noteList)
    }

     inner class NoteViewHolder(itemView: NoteItemBinding) : RecyclerView.ViewHolder(itemView.root){
        private val title:TextView = itemView.txtNoteTitle
        private val layoutItem: LinearLayout = itemView.itemLayout
        private val deleteItem: ImageView = itemView.btnDelete
        fun onBind(note : Note){
            title.text = note.title
            deleteItem.setOnClickListener{ onDelete(note) }
            layoutItem.setOnClickListener{ onClick(note) }

        }
    }

}