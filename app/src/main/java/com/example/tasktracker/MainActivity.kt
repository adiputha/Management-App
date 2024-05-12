package com.example.tasktracker

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasktracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskItemListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var emptyTaskView: TextView
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModel.TaskItemModelFactory((application as TodoApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        emptyTaskView = findViewById(R.id.tvEmptyText)

        binding.newTaskButton.setOnClickListener{
                NewTaskSheet(null).show(supportFragmentManager,"newTask")
        }

        setRecycleView()
    }

    private fun setRecycleView(){
        val mainActivity = this
        taskViewModel.allTaskItems.observe(this){taskItems ->
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(taskItems, mainActivity)
            }
                if (taskItems.isNullOrEmpty()){
                    emptyTaskView.visibility = View.VISIBLE
                }else{
                    emptyTaskView.visibility = View.GONE
                }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        val newTaskSheet = NewTaskSheet(taskItem)
        newTaskSheet.show(supportFragmentManager, "newTask")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    override fun deleteTaskItem(taskItem: TaskItem) {
        taskViewModel.deleteTaskItem(taskItem.id)
    }


}