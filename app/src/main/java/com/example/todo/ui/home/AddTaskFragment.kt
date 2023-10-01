package com.example.todo.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.todoapp.MyDataBase
import com.example.todo.todoapp.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskFragment:BottomSheetDialogFragment() {
    lateinit var viewBinding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.addTaskButton.setOnClickListener {
            creatTask()
        }

        viewBinding.dateContainer.setOnClickListener {
            showDatePickerDialog()
        }
    }
    val calendar= Calendar.getInstance()
    private fun showDatePickerDialog() {

          context?.let {

              val dialog=DatePickerDialog(it)
              dialog.setOnDateSetListener { datePicker, year, month, day ->
                  viewBinding.date.setText("$day- ${month+1}-$year")

                  calendar.set(year,month,day)
                  calendar.set(Calendar.HOUR_OF_DAY,0)
                  calendar.set(Calendar.MINUTE,0)
                  calendar.set(Calendar.MILLISECOND,0)

              }
              dialog.show()


          }

    }


    private fun creatTask() {
        if (!valid()) {
            return
        }
        val task =Task(title = viewBinding.title.text.toString()
                      ,description = viewBinding.description.text.toString()
                      ,dateTime = calendar.timeInMillis)
        MyDataBase.getInstance(requireContext())
            .tasksDao().insertTask(task)
        onnTaskAddedListener?.onTaskAdded()
        dismiss()
    }

    var onnTaskAddedListener:onTaskAddedListener?=null
    fun interface onTaskAddedListener {
        fun onTaskAdded()
    }

    private fun valid(): Boolean {
        var isValid=true
      if (viewBinding.title.text.toString().isNullOrBlank()){
          viewBinding.titleContainer.error="Please enter title"
          isValid=false

      }else{
          viewBinding.titleContainer.error=null
      }
        if (viewBinding.description.text.toString().isNullOrBlank()){
            viewBinding.descContainer.error="Please enter description"
            isValid=false

        }else {
            viewBinding.descContainer.error=null
        }
        if (viewBinding.date.text.toString().isNullOrBlank()){
            viewBinding.dateContainer.error="Please choose date"
            isValid=false

        }else {
            viewBinding.dateContainer.error=null
        }
        return true


    }


}