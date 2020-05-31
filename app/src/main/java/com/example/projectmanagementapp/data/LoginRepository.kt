package com.example.projectmanagementapp.data

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.projectmanagementapp.AwsAPI.AwsApi
import com.example.projectmanagementapp.data.model.LoggedInUser
import java.lang.Exception
import java.security.MessageDigest

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository() {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
       user = null
    }

    fun logout() {
        user = null
    }

    fun login(username: String, password: String): LoggedInUser? {
        // handle login
        val hash = getHash(password)
        try {
            val id = AwsApi.login("test@test.test", "dasijioasdjijdsaijdsa")
            if (id == null)
            {
                throw Exception("Invalid operation")
            }
            else
            {
                val user  = AwsApi.getUser(id,hash)
                val loggedInUser = LoggedInUser(user.id, user.name,hash)
                setLoggedInUser(loggedInUser)
                return loggedInUser
            }
        }
        catch (e : Exception)
        {
            //TODO handle exception
        }
        return null
        //  val user =  LoggedInUser("3","JaneDoe", "dasijioasdjijdsaijdsa")
        //setLoggedInUser(user)
        //return user
    }

    private fun getHash(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA")
            .digest(password.toByteArray())
        return bytes.toString()
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}
