package com.example.noteapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentAddNoteBinding
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewModel

class AddNoteFragment : Fragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var toolBar: Toolbar
    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider (this, NoteViewModel.NoteViewModelFactory(this.activity)
        )[NoteViewModel::class.java]

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_note,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        toolBar.setupWithNavController(navController,appBarConfig)
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater,container,false)
        //toolBar
        toolBar = binding.toolbarAddNote
        (requireActivity() as AppCompatActivity?)!!.setSupportActionBar(toolBar)
        //

        return binding.root
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.save){
            val note = Note(binding.edtTitle.text.toString(),binding.edtDescription.text.toString())
            noteViewModel.insertNote(note)
            findNavController().navigate(R.id.action_addNoteFragment_to_homeFragment)
        }
        return true
    }

}