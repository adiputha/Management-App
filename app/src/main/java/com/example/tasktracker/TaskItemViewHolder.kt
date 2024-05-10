package com.example.tasktracker

import android.content.Context
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktracker.databinding.FragmentNewTaskSheetBinding
import com.example.tasktracker.databinding.TaskItemCardBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder (
    private val context: Context,
    private val binding: TaskItemCardBinding,
    private val clickListener: TaskItemListener

): RecyclerView.ViewHolder(binding.root){

    @RequiresApi(Build.VERSION_CODES.O)
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    @RequiresApi(Build.VERSION_CODES.O)
    fun bindTaskItem(taskItem: TaskItem){
        binding.name.text = taskItem.name

        if (taskItem.isCompleted()){
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.duTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.completeBtn.setImageResource(taskItem.imageResource())
        binding.completeBtn.setColorFilter(taskItem.imageColor(context))

        binding.completeBtn.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }

        binding.taskCardContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        if (taskItem.dueTime != null)
            binding.duTime.text = timeFormat.format(taskItem.dueTime)
    }
}