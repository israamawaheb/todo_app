package com.example.todo.ui.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.todo.R
import com.example.todo.databinding.ActivityEditBinding
import com.example.todo.todoapp.MyDataBase
import com.example.todo.todoapp.model.Task
import com.example.todo.ui.home.HomeActivity
import java.text.SimpleDateFormat
import java.util.Date

class EditActivity : AppCompatActivity() {
    lateinit var viewBinding :ActivityEditBinding
    lateinit var task: Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        task= intent.getSerializableExtra("Task") as Task
        showData(task)
        viewBinding.editeBtn.setOnClickListener {
            updateTodo()
        }
    }

    fun valid():Boolean{
        var isValid=true
        if(viewBinding.title.text.toString().isNullOrBlank()){
            viewBinding.titleContainer.error= "please enter title"
            isValid=false
        }
        else{
            viewBinding.titleContainer.error=null
        }
        if (viewBinding.description.text.toString().isNullOrBlank()){
            viewBinding.descContainer.error="please enter description"
            isValid=false
        }
        else{
            viewBinding.descContainer.error=null
        }
        if (viewBinding.date.text.toString().isNullOrBlank()){
            viewBinding.dateContainer.error="choose date"
            isValid=false
        }
        else{
            viewBinding.dateContainer.error=null
        }
        return true
    }

    private fun updateTodo() {
        if(valid()==false){

            return
        }
        task.title=viewBinding.titleContainer.editText.toString()
        task.description=viewBinding.descContainer.editText.toString()
        MyDataBase.getInstance(this).tasksDao().updateTask(task)
        showInsertDialog()
        startActivity(Intent(this,HomeActivity::class.java))
        finish()

    }

    private fun showInsertDialog() {
         val alertDialogBuilder =AlertDialog.Builder(this).setMessage("Update successfully")
             .setPositiveButton(R.string.ok){
                 dialog,which->dialog.dismiss()
             }

    }

    private fun showData(task: Task) {
        viewBinding.titleContainer.editText?.setText(task.title)
        viewBinding.descContainer.editText?.setText(task.description)
        val date=convertLongToTime(task.dateTime)
        viewBinding.date.text=date

    }

    private fun convertLongToTime(dateTime: Long?): CharSequence? {
               val date=Date(dateTime!!)
        val formate=SimpleDateFormat("yyyy/mm/dd")
        return formate.format(date)
    }
}