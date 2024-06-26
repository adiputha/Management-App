package com.example.tasktracker

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskItemRepo (private val taskItemDao: TaskItemDao) {

    val allTaskItems: Flow<List<TaskItem>> = taskItemDao.getAllTaskItems()

    @WorkerThread
    suspend fun insertTaskItem(taskItem: TaskItem){
            taskItemDao.insertTaskItem(taskItem)
    }

    @WorkerThread
    suspend fun updateTaskItem(taskItem: TaskItem){
        taskItemDao.updateTaskItem(taskItem)
    }

    @WorkerThread
    suspend fun deleteTaskItem(id: Int){
        taskItemDao.deleteTaskItem(id)
    }
}