package com.example.projectmanagementapp.AwsAPI

import android.os.AsyncTask
import android.util.Log
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.User

class AwsApisAsyncWrapper {
    class getTaskIDasync : AsyncTask<String, Int, Task>() {

        // Do the long-running work in here
        override fun doInBackground(vararg id1: String): Task? {
            Log.v("deb", id1[0])
            return AwsApi.getTask(id1[0])
        }

        // This is called each time you call publishProgress()
        override fun onProgressUpdate(vararg progress: Int?) {
            //setProgressPercent(progress.firstOrNull() ?: 0)
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: Task?) {
            //showNotification("Downloaded $result bytes")
            Log.v("REST_TASK", "Got task info from server")
        }

    }

    class getUserAsync : AsyncTask<String, Int, User>() {

        // Do the long-running work in here
        override fun doInBackground(vararg userID: String): User? {
            Log.v("deb", userID[0])
            return AwsApi.getUser(userID[0])
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: User?) {
            //showNotification("Downloaded $result bytes")
            Log.v("REST_TASK", "Got task info from server")
        }

    }
}