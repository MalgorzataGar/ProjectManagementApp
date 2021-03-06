package com.example.projectmanagementapp.ui.MyTasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyTasksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Your tasks"
    }
    val text: LiveData<String> = _text
}