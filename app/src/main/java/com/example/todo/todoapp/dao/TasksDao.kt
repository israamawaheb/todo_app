package com.example.todo.todoapp.dao

import androidx.room.*
import com.example.todo.todoapp.model.Task
import kotlinx.coroutines.selects.select
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Update



@Dao
interface TasksDao {
    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("select * from tasks")
    fun getAllTasks():List<Task>

   @Query("select * from tasks where dateTime=:dateTime")
    fun getTasksByDate(dateTime:Long):List<Task>
}