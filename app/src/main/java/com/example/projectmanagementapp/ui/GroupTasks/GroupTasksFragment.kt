package com.example.projectmanagementapp.ui.GroupTasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.R

class GroupTasksFragment : Fragment() {

    private lateinit var groupTasksViewModel: GroupTasksViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        groupTasksViewModel =
                ViewModelProviders.of(this).get(GroupTasksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_grouptasks, container, false)
        val textView: TextView = root.findViewById(R.id.text_grouptasks)
        groupTasksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
