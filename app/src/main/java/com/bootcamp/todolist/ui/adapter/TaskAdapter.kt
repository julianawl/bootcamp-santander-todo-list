package com.bootcamp.todolist.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.todolist.R
import com.bootcamp.todolist.databinding.ItemTaskBinding
import com.bootcamp.todolist.data.model.Task

class TaskListAdapter: ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()) {

    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
        ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Task){
            binding.taskTitle.text = item.title
            binding.taskDate.text = item.date
            binding.taskTime.text = item.time
            binding.ivMore.setOnClickListener {
                showPopUp(item)
            }
        }

        private fun showPopUp(item: Task) {
            val more = binding.ivMore
            val menu = PopupMenu(more.context, more)
            menu.menuInflater.inflate(R.menu.popup_menu, menu.menu)
            menu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            menu.show()
        }
    }
}

class DiffCallback: DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
}