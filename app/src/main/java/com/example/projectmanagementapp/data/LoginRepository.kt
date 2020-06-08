package com.example.projectmanagementapp.data

import com.example.projectmanagementapp.AwsAPI.AwsApi
import com.example.projectmanagementapp.AwsAPI.AwsApisAsyncWrapper
import com.example.projectmanagementapp.data.model.LoggedInUser
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

    fun login(login: String, password: String): LoggedInUser? {
        // handle login
        val hash = getHash(password)
        try {
            val result = AwsApisAsyncWrapper.loginAsync().execute(Pair(login, hash)).get()
            if (result == null)
            {
                throw Exception("Invalid operation")
            }
            else
            {
                val user  = AwsApisAsyncWrapper.getLoggedUserAsync().execute(Pair(result, hash)).get()
                val loggedInUser = LoggedInUser(result, user.name,hash)
                setLoggedInUser(loggedInUser)
                return loggedInUser
            }
        }
        catch (e : Exception)
        {
            //TODO handle exception
        }
        return null
    }

    private fun getHash(password: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(password.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {
            sb.append(String.format("%02X", b))
        }
        val hashedString = sb.toString()
        return hashedString
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}
