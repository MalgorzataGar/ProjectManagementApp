package com.example.projectmanagementapp.ui.AddTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddTaskViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Add new Task"
    }
    val text: LiveData<String> = _text
}