package com.example.projectmanagementapp.ui.EditGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.ui.AddGroup.AddGroupViewModel

class AddGroupFragment : Fragment(){
    private lateinit var addGroupViewModel: AddGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addGroupViewModel =
            ViewModelProviders.of(this).get(AddGroupViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_addtask, container, false)

        addGroupViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}

