package com.example.noteapp.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentBottomDialogBinding
import com.example.noteapp.model.ToDo
import com.example.noteapp.viewmodel.ToDoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class BottomDialogFragment(private val toDo: ToDo?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomDialogBinding
    private lateinit var title: EditText
    private var dueTime: String? = null
    private var completedDate : String? = null

    private val toDoViewModel: ToDoViewModel by lazy {
        ViewModelProvider (this, ToDoViewModel.ToDoViewModelFactory(this.activity)
        )[ToDoViewModel::class.java]
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = binding.edtTaskName
        if(toDo == null){
            binding.txtTodoTitle.text = "New Task"
        }else{
            binding.txtTodoTitle.text = "Edit Task"
            title.setText(toDo.title)
            completedDate = toDo.completedDate
        }
        binding.btnSaveTask.setOnClickListener {
            saveToDo()

        }
        binding.btnSeclectTime.setOnClickListener {
            showTimePickerDialog()
        }
    }


    private fun showTimePickerDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.time_picker)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val timePicker : TimePicker = dialog.findViewById(R.id.timePicker)
        val btnSave : TextView = dialog.findViewById(R.id.btn_save_time)
        val btnCancel: TextView = dialog.findViewById(R.id.btn_cancel_time)
        val textView: TextView = dialog.findViewById(R.id.textView)
        onClickTime(timePicker,textView)
        btnSave.setOnClickListener {
            completedDate = textView.text.toString()
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomDialogBinding.inflate(inflater,container,false)
        return binding.root
    }



    private fun saveToDo(){
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val createdTime = sdf.format(Date())
        dueTime = null
        if(toDo == null){

            if(title.text.toString().isNotEmpty()){
                val newToDo = ToDo(title.text.toString(),createdTime,dueTime,completedDate)
                toDoViewModel.insertToDo(newToDo)
                dismiss()
            }
            else{
                 Toast.makeText(context,"Please enter the task",Toast.LENGTH_SHORT).show()
            }

        }
        else{
            val editToDo = ToDo(title.text.toString(),createdTime,dueTime,completedDate)
            editToDo.id = toDo.id
            toDoViewModel.updateToDo(editToDo)
            dismiss()
        }

    }

    private fun onClickTime(timePicker: TimePicker,textView: TextView) {
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }

                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }

                else -> am_pm = "AM"
            }
            if (textView != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute

                val msg = "$hour : $min $am_pm"
                textView.text = msg

            }

        }


    }
}

