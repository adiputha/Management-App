package com.example.tasktracker

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskItem(
    var name: String,
    var desc: String,
    var dueTime: LocalTime?,
    var completedTime: LocalDate?,
    var id: UUID = UUID.randomUUID()
)
{
    fun isCompleted() = completedTime != null
    fun imageResource(): Int = if (isCompleted()) R.drawable.checked_24 else R.drawable.unchecked_24


}
