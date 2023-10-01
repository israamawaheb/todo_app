package com.example.todo.ui.home.tabs.tasks_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentSettingsBinding
import com.example.todo.databinding.FragmentTasksListBinding
import com.example.todo.todoapp.MyDataBase
import com.example.todo.todoapp.model.Task
import com.example.todo.ui.edit.EditActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.Calendar

class TasksListFragment :BaseFragment(){

    lateinit var viewBinding : FragmentTasksListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentTasksListBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        loadTasks()

        tasksAdapter.onnItemClicked= object : TasksAdapter.onItemClicked {
            override fun onItemClicked(task: Task) {
                 showMessage("what do you want","Update",{
                    _,dialog->updateTodoTask(task) },"Make Done",{_,dialog -> makeDone(task)})
            }
            
        }
    }

    private fun updateTodoTask(task: Task) {
         var intent = Intent(requireContext(),EditActivity::class.java)
          intent.putExtra("Task",task)  
        startActivity(intent)

    }
    private fun makeDone(task: Task){
        task.isDone=true
        MyDataBase.getInstance(requireContext()).tasksDao().updateTask(task)

    }


    fun loadTasks() {
         context?.let {
             val tasks=  MyDataBase.getInstance(requireContext()).tasksDao().getTasksByDate(selectedDate.timeInMillis)
             tasksAdapter.bindTasks(tasks.toMutableList())
         }

    }


    private val tasksAdapter=TasksAdapter(null)
    var selectedDate :Calendar=Calendar.getInstance()
    init {
        selectedDate.set(Calendar.HOUR,0)
        selectedDate.set(Calendar.MINUTE,0)
        selectedDate.set(Calendar.SECOND,0)
        selectedDate.set(Calendar.MILLISECOND,0)
    }
    private fun initViews() {
        viewBinding.recycleView.adapter=tasksAdapter
        tasksAdapter.onItemDeleteListener= TasksAdapter.onItemClickedListener { position, task ->
            deleteTaskFromDatabase(task)
            tasksAdapter.taskDeleted(task)
        }
        viewBinding.calendarView.setSelectedDate(CalendarDay.today())

        viewBinding.calendarView.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->

           if (selected){
               selectedDate.set(Calendar.YEAR,date.year)
               selectedDate.set(Calendar.MONTH,date.month-1)
               selectedDate.set(Calendar.DAY_OF_MONTH,date.day)
           }
        })
        loadTasks()
    }

    private fun deleteTaskFromDatabase(task: Task) {
          MyDataBase.getInstance(requireContext()).tasksDao().deleteTask(task)
    }


}