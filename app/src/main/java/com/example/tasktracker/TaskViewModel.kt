package com.example.tasktracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.UUID

class TaskViewModel(private val repository: TaskItemRepo): ViewModel() {

    var allTaskItems: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()


    fun addTaskItem(newTask: TaskItem){
        viewModelScope.launch {
            repository.insertTaskItem(newTask)
        }
    }

    fun updateTaskItem(taskItem: TaskItem) {
        viewModelScope.launch {
            repository.updateTaskItem(taskItem)
        }
    }

    fun deleteTaskItem(id: Int){
        viewModelScope.launch {
            repository.deleteTaskItem(id)
        }
    }

    fun setCompleted(taskItem: TaskItem) {
        viewModelScope.launch {
            if (!taskItem.isCompleted())
                taskItem.completedTimeString = TaskItem.dateFormatter.format(LocalDate.now())
            repository.updateTaskItem(taskItem)
        }
    }

    class TaskItemModelFactory(private val repository: TaskItemRepo): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T
        {
            if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

            throw IllegalArgumentException("Unknown class for model")
        }
    }




    /*  fun updateTaskItem(id: UUID, name:String, desc:String, duTime: LocalTime?){
          val list = taskItems.value
          val task = list!!.find { it.id == id }!!
          task.name = name
          task.desc = desc
          task.dueTime = duTime
          taskItems.postValue(list)
      }



      fun setCompleted(taskItem: TaskItem){
          val list = taskItems.value
          val task = list!!.find { it.id == taskItem.id }!!
          if (task.completedTime == null)
              task.completedTime = LocalDate.now()
          taskItems.postValue(list)
      }*/


}