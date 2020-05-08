package com.example.projectmanagementapp.ui.MyTasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.R

class MyTasksFragment : Fragment() {

    private lateinit var myTasksViewModel: MyTasksViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        myTasksViewModel =
                ViewModelProviders.of(this).get(MyTasksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mytasks, container, false)
        val textView: TextView = root.findViewById(R.id.text_mytasks)
        myTasksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
