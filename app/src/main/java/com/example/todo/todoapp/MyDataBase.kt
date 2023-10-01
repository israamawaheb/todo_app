package com.example.todo.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.todoapp.dao.TasksDao
import com.example.todo.todoapp.model.Task


@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class MyDataBase:RoomDatabase(){

   abstract fun tasksDao(): TasksDao

companion object{

   private var instance: MyDataBase?=null

   fun getInstance(context: Context): MyDataBase {

      if (instance ==null){
         //initialize instance
         instance = Room.databaseBuilder(context.applicationContext, MyDataBase::class.java,"tasksDB")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
      }
      return instance!!
   }
}
}