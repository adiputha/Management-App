package com.example.tasktracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskItemDao {

    @Query("SELECT * FROM taskItemTable ORDER BY id ASC")
    fun getAllTaskItems(): Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskItem(taskItem: TaskItem)

    @Update
    suspend fun updateTaskItem(taskItem: TaskItem)

    @Query("DELETE FROM taskItemTable where id = :id")
    suspend fun deleteTaskItem(id: Int)
}