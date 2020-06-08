package com.example.projectmanagementapp.ui.AddGroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddGroupViewModel : ViewModel(){
    private val _text = MutableLiveData<String>().apply {
    }
    val text: LiveData<String> = _text
}



