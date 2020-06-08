package com.example.projectmanagementapp.ui.GroupTasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GroupTasksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Your group tasks"
    }
    val text: LiveData<String> = _text
}