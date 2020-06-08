package com.example.projectmanagementapp.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
        val userId: String,
        val displayName: String,
        val passwordHash: String


){
        fun equals(otherLoggedInUser: LoggedInUser): Boolean {
                if(!this.userId.equals(otherLoggedInUser.userId)){
                        return false
                }
                if(!this.displayName.equals(otherLoggedInUser.displayName)){
                        return false
                }
                if(!this.passwordHash.equals(otherLoggedInUser.passwordHash)){
                        return false
                }
                return true


        }
}
