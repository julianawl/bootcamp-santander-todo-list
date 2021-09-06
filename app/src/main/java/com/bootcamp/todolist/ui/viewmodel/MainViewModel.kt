package com.bootcamp.todolist.ui.viewmodel

import androidx.lifecycle.*
import com.bootcamp.todolist.data.TaskRepository
import com.bootcamp.todolist.data.model.Task
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TaskRepository): ViewModel() {

    private val _listResponse = MutableLiveData<List<Task>>()
    val listResponse = _listResponse as LiveData<List<Task>>

    private val _taskResponse = MutableLiveData<Task>()
    val taskResponse = _taskResponse as LiveData<Task>

    fun insert(task: Task){
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun delete(task: Task){
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun update(task: Task){
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun getAll() {
        viewModelScope.launch {
            _listResponse.postValue(repository.getAll())
        }
    }

    fun getTask(taskId: Int){
        viewModelScope.launch {
            _taskResponse.postValue(repository.getTask(taskId))
        }
    }
}

class MainViewModelFactory(private val repository: TaskRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}