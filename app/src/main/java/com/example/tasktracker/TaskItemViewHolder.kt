package com.example.tasktracker

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.widget.Toast
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


    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

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

        binding.deleteBtn.setOnClickListener {
            Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show()
            clickListener.deleteTaskItem(taskItem)
        }

        if (taskItem.dueTime() != null)
            binding.duTime.text = timeFormat.format(taskItem.dueTime())
    }
}