package com.example.projectmanagementapp.ui.EditGroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditGroupViewModel : ViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "Add new Task"
    }
    val text: LiveData<String> = _text
}



