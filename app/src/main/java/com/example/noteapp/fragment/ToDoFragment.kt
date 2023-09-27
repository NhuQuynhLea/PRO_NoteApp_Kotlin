package com.example.noteapp.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.adapter.ToDoAdapter
import com.example.noteapp.databinding.FragmentToDoBinding
import com.example.noteapp.model.ToDo
import com.example.noteapp.viewmodel.ToDoViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Timer
import kotlin.concurrent.timerTask

class ToDoFragment : Fragment() {
    private lateinit var binding: FragmentToDoBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: ToDoAdapter
    private var isLinearLayout:Boolean = false
    private val toDoViewModel: ToDoViewModel by lazy {
        ViewModelProvider (this, ToDoViewModel.ToDoViewModelFactory(this.activity)
        )[ToDoViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initControls() {
        adapter = ToDoAdapter(this.requireContext(), editToDo,completedToDo)
        recyclerView = binding.recyclerview
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerview.layoutManager = linearLayoutManager
        gridLayoutManager = GridLayoutManager(context,2)
        binding.recyclerview.adapter = adapter

        toDoViewModel.getAllToDo().observe(viewLifecycleOwner, Observer {
//            val sdf = SimpleDateFormat("HH:mm")
//            val dueTime = sdf.format(Date())
//            for(toDo in it){
//                if(toDo.completedDate == dueTime){
//                    toDoViewModel.deleteToDo(toDo)
//
//                }
//            }
            adapter.setToDo(it)
        })
        //Toast.makeText(context,isLinearLayout.toString(), Toast.LENGTH_SHORT).show()

        parentFragmentManager.setFragmentResultListener("toDoSort",viewLifecycleOwner) { key, bundle ->
            val isAscending = bundle.getBoolean("bundleKey")

            if(isAscending){
                toDoViewModel.getAllSortedCreatedTimeASC().observe(viewLifecycleOwner, Observer {
                    adapter.setToDo(it)
                })

            }
            else{
                toDoViewModel.getAllSortedCreatedTimeDESC().observe(viewLifecycleOwner, Observer {
                    adapter.setToDo(it)
                })

            }

        }

    }


    private val editToDo: (ToDo) ->Unit =  {
        BottomDialogFragment(it).show(parentFragmentManager,"Edit Task")
    }

    private val completedToDo: (ToDo) ->Unit =  {
        Timer().schedule(timerTask {
            toDoViewModel.deleteToDo(it)
        },2000)

    }

}