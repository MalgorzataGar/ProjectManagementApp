package com.example.projectmanagementapp.AwsAPI

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.Team
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.loadPreference
import com.example.projectmanagementapp.extensions.Clog

class AwsApisAsyncWrapper (){
     class getTaskIDasync : AsyncTask<String, Int, Task>() {

        // Do the long-running work in here
        override fun doInBackground(vararg taskId_userID_hash: String): Task? {
            Clog.log("REST", "get task info: ${taskId_userID_hash.joinToString(" ")}")
            return AwsApi.getTask(taskId_userID_hash[0],taskId_userID_hash[1],taskId_userID_hash[2]);
        }

        // This is called each time you call publishProgress()
        override fun onProgressUpdate(vararg progress: Int?) {
            //setProgressPercent(progress.firstOrNull() ?: 0)
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: Task?) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST", "Got task info from server")
        }

    }

    class getUserAsync : AsyncTask<String, Int, User>() {

        // Do the long-running work in here
        override fun doInBackground(vararg idHash: String): User? {
            Clog.log("REST", "idHash: "+idHash[0]+" hash: "+idHash[1])
            return AwsApi.getUser(idHash[0],idHash[1])
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: User?) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST", "Got user info from server")
        }

    }
    class getExternalUserAsync : AsyncTask<String, Int, User>() {

        // Do the long-running work in here
        override fun doInBackground(vararg ididHash: String): User? {
            Clog.log("REST", "external user's id: "+ididHash[0]+"idHash: "+ididHash[1]+" hash: "+ididHash[2])
            return AwsApi.getExternalUser(ididHash[0],ididHash[1],ididHash[2])
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: User?) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST", "Got user info from server")
        }

    }
    class getAllUsersAsync : AsyncTask<String, Int, Map<String,String>>() {

        // Do the long-running work in here
        override fun doInBackground(vararg idHash: String): Map<String,String> {
            Clog.log("REST", "idHash: "+idHash[0]+" hash: "+idHash[1])
            return AwsApi.getAllUsers(idHash[0],idHash[1])
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: Map<String,String>) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST", "Got user info from server")
        }

    }
    class postOrUpdateTaskAsync: AsyncTask<Pair< Pair<Task,Boolean>, Pair<String,String> >, Int, String>() {
        //Pair< Task, updateTask>, Pair<userID, hash>

        // Do the long-running work in here
        override fun doInBackground(vararg arg: Pair< Pair<Task, Boolean>, Pair<String, String>>): String {
            Clog.log("REST", "postTask "+arg[0].toString())
           return  AwsApi.postOrUpdateTask(arg[0].first.first, arg[0].first.second,arg[0].second.first,arg[0].second.second)
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: String) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST_postTask", "Sent task info from server")
        }

    }

    class getTeamAsync : AsyncTask<String, Int, Team>() {

        // Do the long-running work in here
        override fun doInBackground(vararg teamID_userID_Hash: String): Team? {
            Clog.log("REST", "try to get team info: teamID "+ teamID_userID_Hash[0]+" user: "+teamID_userID_Hash[1]+" hash: "+teamID_userID_Hash[2])
            return AwsApi.getTeam(teamID_userID_Hash[0],teamID_userID_Hash[1],teamID_userID_Hash[2])
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: Team?) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST", "Got team info from server")
        }

    }

    class postOrUpdateGroupAsync: AsyncTask<Pair< Pair<Team,Boolean>, Pair<String,String> >, Int, String>() {
        //Pair< Team, updateTeam>, Pair<userID, hash>

        // Do the long-running work in here
        override fun doInBackground(vararg arg: Pair< Pair<Team, Boolean>, Pair<String, String>>): String {
            Clog.log("REST", "postGroup "+arg[0].toString())
            return AwsApi.postOrUpdateGroup(arg[0].first.first, arg[0].first.second,arg[0].second.first,arg[0].second.second)

        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: String) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST_postGroup", "Sent group info to server")
        }

    }
    class loginAsync: AsyncTask<Pair<String,String>, Int, String>() {

        // Do the long-running work in here
        override fun doInBackground(vararg arg: Pair<String, String>): String? {
            Log.v("REST_login",arg[0].toString())
            Log.v("REST_login", arg[0].first.toString())
            return AwsApi.login(arg[0].first,arg[0].second)
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: String?) {
            //showNotification("Downloaded $result bytes")
            Log.v("REST_login", "Logged in")
        }

    }
    class getLoggedUserAsync : AsyncTask<Pair<String,String>, Int, User>() {

        // Do the long-running work in here
        override fun doInBackground(vararg arg: Pair<String, String>): User? {
            Log.v("REST_getUser", arg[0].first)
            return AwsApi.getUser(arg[0].first,arg[0].second)
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: User?) {
            //showNotification("Downloaded $result bytes")
            Log.v("REST_getUser", "Got user info from server")
        }

    }
    class deleteTaskAsync : AsyncTask<String, Int, Void>() {

        // Do the long-running work in here
        override fun doInBackground(vararg taskId_userID_hash: String) :Void? {
            Clog.log("REST", "delete task: ${taskId_userID_hash.joinToString(" ")}")
            AwsApi.deleteTask(
                taskId_userID_hash[0],
                taskId_userID_hash[1],
                taskId_userID_hash[2]
            );
            return null
        }

        // This is called each time you call publishProgress()
        override fun onProgressUpdate(vararg progress: Int?) {
            //setProgressPercent(progress.firstOrNull() ?: 0)
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: Void? ) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST", "Got task info from server")
        }
    }
    class UpdateUserAsync: AsyncTask<Pair<User,Pair<String,String>>, Int, Void>() {

        // Do the long-running work in here
        override fun doInBackground(vararg arg:Pair<User,Pair<String,String>>): Void? {
            Clog.log("REST", "updateUser "+arg[0].toString())
            AwsApi.updateUser(arg[0].first,arg[0].second.first,arg[0].second.second)
            return null
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: Void?) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST_postGroup", "Sent group info to server")
        }

    }
}

