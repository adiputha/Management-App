package com.example.tasktracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TaskItem::class], version = 2, exportSchema = false)
abstract class TaskItemDatabase : RoomDatabase() {

    abstract fun taskItemDao(): TaskItemDao

    companion object {
        @Volatile
        private var INSTANCE: TaskItemDatabase? = null

        // Define the migration from version 1 to version 2
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // SQL query to add the 'dueDate' column to the 'taskItemTable'
                database.execSQL("ALTER TABLE taskItemTable ADD COLUMN dueDate TEXT")
            }
        }

        fun getDatabase(context: Context): TaskItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskItemDatabase::class.java,
                    "taskItemDatabase"
                )
                    .addMigrations(MIGRATION_1_2) // Add the migration to the database builder
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
