package com.example.projectmanagementapp.AwsAPI

import android.os.AsyncTask
import android.util.Log
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.Team
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.Clog

class AwsApisAsyncWrapper {
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
    class postOrUpdateTaskAsync: AsyncTask<Pair< Pair<Task,Boolean>, Pair<String,String> >, Int, Void>() {
        //Pair< Task, updateTask>, Pair<userID, hash>

        // Do the long-running work in here
        override fun doInBackground(vararg arg: Pair< Pair<Task, Boolean>, Pair<String, String>>): Void? {
            Clog.log("REST", "postTask "+arg[0].toString())
            AwsApi.postOrUpdateTask(arg[0].first.first, arg[0].first.second,arg[0].second.first,arg[0].second.second)
            return null
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: Void?) {
            //showNotification("Downloaded $result bytes")
            Clog.log("REST_postTask", "Got task info from server")
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
}

