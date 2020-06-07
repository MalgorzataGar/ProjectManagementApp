package com.example.projectmanagementapp.data

import com.example.projectmanagementapp.data.model.LoggedInUser
import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception

class LoginRepositoryTest {

    val USER_LOGIN = "testUser@test.test"
    val PASSWORD = "Test123"
    val loginrepo = LoginRepository()
    val user = LoggedInUser(USER_LOGIN,"testName",PASSWORD)

    @Test
    fun login() {
        val loggedUser = loginrepo.login(USER_LOGIN,PASSWORD)
        if (loggedUser != null) {
            assertEquals(true,loggedUser.equals(user))
        }
        else{
            throw Exception("Logging in failed")
        }
    }
}