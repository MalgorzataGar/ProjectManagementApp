package com.example.projectmanagementapp.extensions

import android.app.Application


public class UserProvider : Application() {
    private var UserId: String? = null
    private var UserName: String? = null

    fun getUserId(): String? {
        return UserId
    }

    fun setUserId(id: String?) {
        this.UserId = id
    }
    fun getUserName(): String? {
        return UserName
    }

    fun setUserName(name: String?) {
        this.UserName = name
    }


}