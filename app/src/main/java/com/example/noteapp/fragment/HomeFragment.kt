package com.example.noteapp.fragment



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider (this,NoteViewModel.NoteViewModelFactory(this.activity)
        )[NoteViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        initControls()
        return binding.root
    }

    private fun initControls() {
        val adapter : NoteAdapter = NoteAdapter(this@HomeFragment.requireContext(), onItemClick,onItemDelete)
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = adapter
        noteViewModel.getAllNote().observe(viewLifecycleOwner, Observer {
            adapter.setNotes(it)
        })

        binding.fab.setOnClickListener{
             findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }

    }
    private val onItemClick: (Note) ->Unit = {
        val direction = HomeFragmentDirections.actionHomeFragmentToShowNoteFragment(it)
        findNavController().navigate(direction)
    }
    private val onItemDelete: (Note) -> Unit = {
        noteViewModel.deleteNote(it)
    }

}