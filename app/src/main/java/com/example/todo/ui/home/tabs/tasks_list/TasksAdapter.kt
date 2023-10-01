package com.example.todo.ui.home.tabs.tasks_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.databinding.ItemTaskListBinding
import com.example.todo.todoapp.model.Task

class TasksAdapter(var tasks: MutableList<Task>?):RecyclerView.Adapter<TasksAdapter.ViewHolder>(){


    class ViewHolder(var itemBinding:ItemTaskListBinding):RecyclerView.ViewHolder(itemBinding.root){

        fun bind(task: Task){
            itemBinding.title.text=task.title
            itemBinding.description.text=task.description

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding=ItemTaskListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          holder.itemBinding.title.text=tasks?.get(position)?.title
          holder.itemBinding.description.text=tasks?.get(position)?.description

          if (tasks!![position].isDone){
              holder.itemBinding.btnIsdone.setBackgroundColor(Color.GREEN)
              holder.itemBinding.title.setTextColor(Color.GREEN)
              holder.itemBinding.btnIsdone.setBackgroundResource(R.drawable.ic_done)
          }

        if (onnItemClicked!=null){
            holder.itemBinding.root.setOnLongClickListener {
                onnItemClicked?.onItemClicked(tasks!![position])
                true
            }
        }
        if (onItemDeleteListener!=null){
            holder.itemBinding.deleteView.setOnClickListener {
                holder.itemBinding.swipeLayout.close(true)
                onItemDeleteListener?.onItemclick(position,tasks!![position])
            }
        }
    }

    override fun getItemCount(): Int =tasks?.size?:0

    fun bindTasks(tasks: MutableList<Task>) {
          this.tasks=tasks
        notifyDataSetChanged()
    }

    fun taskDeleted(task: Task) {
        val position =tasks?.indexOf(task)
        tasks?.remove(task)
        notifyItemRemoved(position!!)
    }

    var onnItemClicked: onItemClicked?=null
    interface onItemClicked{
        fun onItemClicked(task: Task)

    }

    var onItemDeleteListener:onItemClickedListener?=null
    fun interface onItemClickedListener{
        fun onItemclick(position: Int,task: Task)
    }


}