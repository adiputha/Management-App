package com.example.tasktracker

import android.app.Application

class TodoApp: Application() {
    private val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repository by lazy { TaskItemRepo(database.taskItemDao()) }
}