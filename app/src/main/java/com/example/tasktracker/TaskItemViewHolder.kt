package com.example.tasktracker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktracker.databinding.FragmentNewTaskSheetBinding
import com.example.tasktracker.databinding.TaskItemCardBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder (
    private val context: Context,
    private val binding: TaskItemCardBinding


): RecyclerView.ViewHolder(binding.root){

    @RequiresApi(Build.VERSION_CODES.O)
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    @RequiresApi(Build.VERSION_CODES.O)
    fun bindTaskItem(taskItem: TaskItem){
        binding.name.text = taskItem.name

        if (taskItem.dueTime != null)
            binding.duTime.text = timeFormat.format(taskItem.dueTime)
    }
}