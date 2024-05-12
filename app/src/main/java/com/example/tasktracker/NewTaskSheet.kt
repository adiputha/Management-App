package com.example.tasktracker

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tasktracker.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

            private lateinit var binding: FragmentNewTaskSheetBinding
            private lateinit var taskViewModel: TaskViewModel
            private var dueTime: LocalTime? = null
            private var dueDate: LocalDate? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        if(taskItem != null){
            binding.taskTitle.text = getString(R.string.EditTask)
            binding.name.text = Editable.Factory.getInstance().newEditable(taskItem!!.name)
            binding.desc.text = Editable.Factory.getInstance().newEditable(taskItem!!.desc)
            if (taskItem!!.dueTime() != null && taskItem!!.dueDate() != null){
                dueTime = taskItem!!.dueTime()!!
                dueDate = taskItem!!.dueDate()!!
                updateTimeBtnText()
                updateDateBtnText()
            }

        }else
        {
            binding.taskTitle.text = getString(R.string.NewTask)
        }
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveButton()
        }
        binding.timePick.setOnClickListener {
            openTimePicker()
        }

        binding.datePick.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker(){
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),{_, year, month, dayOfMonth ->
            dueDate = LocalDate.of(year, month + 1, dayOfMonth)
            val selectedDate = "${month +1}/$dayOfMonth/$year"
            binding.datePick.text = selectedDate
        },year,month,day)
        datePickerDialog.show()
    }

    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour,selectedMinute)
            updateTimeBtnText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due Time")
        dialog.show()
    }



    private fun updateTimeBtnText(){
        binding.timePick.text = String.format("%02d:%02d", dueTime!!.hour,dueTime!!.minute)
    }

    private fun updateDateBtnText(){
        binding.datePick.text = String.format("%02d/%02d/%d", dueDate!!.monthValue,dueDate!!.dayOfMonth,dueDate!!.year)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)

        return binding.root
    }

    private fun saveButton(){
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if(dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
        val dueDate = if (dueDate == null) null else TaskItem.dateFormatter.format(dueDate)
        val completedTimeString = null

        if (taskItem == null){
            val newTask = TaskItem(name,desc,dueTimeString,dueDate,completedTimeString)
            taskViewModel.addTaskItem(newTask)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG).show()
        }else{
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueTimeString = dueTimeString
            taskItem!!.dueDate = dueDate
            taskViewModel.updateTaskItem(taskItem!!)
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_LONG).show()
        }
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()

    }


}