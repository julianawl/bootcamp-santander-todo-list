package com.bootcamp.todolist.data

import androidx.room.*
import com.bootcamp.todolist.data.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    suspend fun getAll(): List<Task>

    @Query("SELECT * FROM Task WHERE id=:taskId")
    suspend fun getTask(taskId: Int): Task

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)
}