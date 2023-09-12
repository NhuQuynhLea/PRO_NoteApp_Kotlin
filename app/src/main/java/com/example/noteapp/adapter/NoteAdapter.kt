package com.example.noteapp.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.NoteItemBinding
import com.example.noteapp.model.Note
import kotlin.random.Random


class NoteAdapter (
    private val context: Context,
    private val onClick: (Note) -> Unit,
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
     var mDiffer: AsyncListDiffer<Note> = AsyncListDiffer<Note>(this, DILL_CALLBACK )
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
        fun onBind(note : Note){
            title.text = note.title
            layoutItem.setOnClickListener{ onClick(note) }
            Log.e("Color", getRandomColor().toString() )
            layoutItem.setBackgroundColor(itemView.resources.getColor(getRandomColor(),null))
        }
    }
    fun getRandomColor(): Int{
        val colors = ArrayList<Int>()
        colors.add(R.color.random1)
        colors.add(R.color.random2)
        colors.add(R.color.random3)
        colors.add(R.color.random4)
        colors.add(R.color.random5)
        colors.add(R.color.random6)

        val random  = Random.nextInt(colors.size)
        return colors[random]
    }
//    private val customFilter = object : Filter() {
//        override fun performFiltering(constraint: CharSequence?): FilterResults {
//            val filteredList = mutableListOf<Note>()
//            if (constraint == null || constraint.isEmpty()) {
//                filteredList.addAll(list)
//            } else {
//                val filterPattern = constraint.toString().toLowerCase().trim()
//
//                for (item in list) {
//// here i am searching at custom obj by managerName
//                    if (item.managerName.toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item)
//                    }
//                }
//            }
//            val results = FilterResults()
//            results.values = filteredList
//            return results
//        }
//
//        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
//            submitList(filterResults?.values as MutableList<CJO>?)
//        }
//    }
}