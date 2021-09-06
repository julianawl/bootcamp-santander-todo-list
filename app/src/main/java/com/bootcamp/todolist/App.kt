package com.bootcamp.todolist

import android.app.Application
import com.bootcamp.todolist.data.AppDatabase
import com.bootcamp.todolist.data.TaskRepository

class App: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}