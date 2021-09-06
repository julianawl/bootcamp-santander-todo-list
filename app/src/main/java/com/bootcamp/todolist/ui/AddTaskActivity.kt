package com.bootcamp.todolist.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.todolist.App
import com.bootcamp.todolist.R
import com.bootcamp.todolist.data.model.Task
import com.bootcamp.todolist.databinding.ActivityAddTaskBinding
import com.bootcamp.todolist.extensions.format
import com.bootcamp.todolist.extensions.text
import com.bootcamp.todolist.ui.viewmodel.MainViewModel
import com.bootcamp.todolist.ui.viewmodel.MainViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)){
            val taskId = intent.getIntExtra(TASK_ID, 0)
            mainViewModel.getTask(taskId)
            mainViewModel.taskResponse.observe(this, {
                binding.toolbar.title = getString(R.string.edit_task_label)
                binding.tilTitle.text = it.title
                binding.tilTime.text = it.time
                binding.tilDate.text = it.date
                binding.tilDescription.text = it.description
            })
        }

        initActions()
    }

    private fun initActions() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
//                binding.tilDate.editText?.setText(Date(it).format())
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilTime.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if(timePicker.minute in 0..9) {
                    "0${timePicker.minute}"
                } else {
                    timePicker.minute
                }
                val hour = if(timePicker.hour in 0..9) {
                    "0${timePicker.hour}"
                } else {
                    timePicker.hour
                }
                binding.tilTime.text = "${hour}:${minute}"
            }
            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")
        }

        binding.btnSave.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                time = binding.tilTime.text,
                date = binding.tilDate.text,
                description = binding.tilDescription.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            if (intent.hasExtra(TASK_ID)){
                mainViewModel.update(task)
            } else {
                mainViewModel.insert(task)
            }
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}