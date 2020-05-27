package com.example.projectmanagementapp.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import kotlinx.android.synthetic.main.task_item.view.*

class ListAdapter: BaseRecyclerViewAdapter<Task>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.task_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var myHolder = holder as? MyViewHolder
        myHolder?.setUpView(task = getItem(position))
    }

    inner class MyViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val stateImageView: ImageView = view.image_state
        private val priorityImageView: ImageView = view.image_priority
        private val textView: TextView = view.text_view

        init {
            view.setOnClickListener(this)
        }

        fun setUpView(task: Task?) {
            textView.text = task?.taskName
            if (task != null) {
                priorityImageView.setImageResource(GetPriorityIcon(task.priority))
                stateImageView.setImageResource(GetStateIcon(task.state))
            }
        }

        private fun GetStateIcon(state: String?): Int {
            if(state == "new")  return R.drawable.todo
            else if(state == "inprogress") return R.drawable.inprogress
            else if(state == "done") return R.drawable.done
            else return 0
        }
        private fun GetPriorityIcon(priority: String?): Int {
            if(priority == "minor") return R.drawable.minor
            else if(priority == "normal") return R.drawable.normal
            else if(priority == "major") return R.drawable.major
            else return 0
        }

        override fun onClick(v: View?) {
            itemClickListener?.onItemClick(adapterPosition, v)
        }
    }
}



