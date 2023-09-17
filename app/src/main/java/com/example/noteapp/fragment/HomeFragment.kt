package com.example.noteapp.fragment




import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.model.Note
import com.example.noteapp.utils.SwipeToDeleteCallBack
import com.example.noteapp.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar
    private var isAscending: Boolean = true
    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider (this,NoteViewModel.NoteViewModelFactory(this.activity)
        )[NoteViewModel::class.java]

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        //toolBar
        toolBar = binding.toolbarHome
        (requireActivity() as AppCompatActivity?)!!.setSupportActionBar(toolBar)
        //

        initControls()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        val toolBar = binding.toolbarHome
        val appBarConfig = AppBarConfiguration(navController.graph)
        toolBar.setupWithNavController(navController,appBarConfig)
        super.onViewCreated(view, savedInstanceState)

        val swipeToDeleteCallBack = object : SwipeToDeleteCallBack(){
            @SuppressLint("ResourceAsColor")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = adapter.mDiffer.currentList[position]
                noteViewModel.deleteNote(note)

                val snackBar = Snackbar.make(view, "Item Deleted", Snackbar.LENGTH_SHORT)
                snackBar.setAction("UNDO"){
                    noteViewModel.insertNote(note)
                }.show()

            }
        }
        ItemTouchHelper(swipeToDeleteCallBack).apply {
            attachToRecyclerView(binding.recyclerview)
        }

    }

    private fun initControls() {
         adapter = NoteAdapter(this@HomeFragment.requireContext(), onItemClick)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.search){
            val direction = HomeFragmentDirections.actionHomeFragmentToSearchViewFragment()
            findNavController().navigate(direction)
        }
        else if(item.itemId == R.id.sort){
            isAscending = !isAscending
            if(isAscending){
                noteViewModel.getAllSortedCreatedTimeASC().observe(viewLifecycleOwner, Observer {
                    adapter.setNotes(it)
                })
                Toast.makeText(context,"Asc", Toast.LENGTH_SHORT).show()
            }else{
                noteViewModel.getAllSortedCreatedTimeDESC().observe(viewLifecycleOwner, Observer {
                    adapter.setNotes(it)
                })
                Toast.makeText(context,"Desc", Toast.LENGTH_SHORT).show()
            }

        }
        return true
    }

}