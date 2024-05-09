package com.example.tasktracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.UUID

class TaskViewModel: ViewModel() {

    var taskItems = MutableLiveData<MutableList<TaskItem>>()

            init{
                taskItems.value = mutableListOf()
            }

    fun addTaskItem(newTask: TaskItem){
        val list = taskItems.value
        list!!.add(newTask)
        taskItems.postValue(list)
    }

    fun updateTaskItem(id: UUID, name:String, desc:String, duTime: LocalTime?){
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.desc = desc
        task.dueTime = duTime
        taskItems.postValue(list)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(taskItem: TaskItem){
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id }!!
        if (task.completedTime == null)
            task.completedTime = LocalDate.now()
        taskItems.postValue(list)
    }

}