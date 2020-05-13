package com.example.projectmanagementapp.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
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

        private val imageView: ImageView = view.image_view
        private val textView: TextView = view.text_view

        init {
            view.setOnClickListener(this)
        }

        fun setUpView(task: Task?) {
            //task?.Name?.let { imageView.setImageResource() }
            textView.text = task?.taskName
        }

        override fun onClick(v: View?) {
            //itemClickListener?.onItemClick(adapterPosition, v)
        }
    }
}
interface OnItemClickListener : AdapterView.OnItemClickListener {
    abstract fun onItemClick(position: Int, view: View?)
}