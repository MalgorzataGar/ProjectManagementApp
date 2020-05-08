package com.example.projectmanagementapp.ui.AddTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.R

class AddTaskFragment : Fragment() {

    private lateinit var addTaskViewModel: AddTaskViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addTaskViewModel =
                ViewModelProviders.of(this).get(AddTaskViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_addtask, container, false)
        val textView: TextView = root.findViewById(R.id.text_addtask)
        addTaskViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
