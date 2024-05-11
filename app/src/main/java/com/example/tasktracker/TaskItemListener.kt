package com.example.tasktracker

interface TaskItemListener {
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)

    fun deleteTaskItem(taskItem: TaskItem)
}