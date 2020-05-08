package com.example.projectmanagementapp.ui.EditGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.R

class AddGroupFragment : Fragment(){
    private lateinit var editGroupViewModel: EditGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editGroupViewModel =
            ViewModelProviders.of(this).get(EditGroupViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_addtask, container, false)
        val textView: TextView = root.findViewById(R.id.text_addtask)
        editGroupViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}

