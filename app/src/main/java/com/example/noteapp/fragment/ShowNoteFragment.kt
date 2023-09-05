package com.example.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentAddNoteBinding
import com.example.noteapp.databinding.FragmentShowNoteBinding
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewModel

class ShowNoteFragment : Fragment() {

    private lateinit var binding: FragmentShowNoteBinding
    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider (this, NoteViewModel.NoteViewModelFactory(this.activity)
        )[NoteViewModel::class.java]

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowNoteBinding.inflate(inflater, container,false)
        val arg : ShowNoteFragmentArgs by navArgs()
        binding.txtShowTitle.text = arg.note.title
        binding.txtShowDescription.text = arg.note.description
        binding.btnEdt.setOnClickListener {
            val direction = ShowNoteFragmentDirections.actionShowNoteFragmentToEditNoteFragment(arg.note)
            findNavController().navigate(direction)
        }
        return binding.root
    }

}