package com.example.projectmanagementapp.ui.TaskView

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.AwsAPI.AwsApi
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.User

class TaskFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var root :View

    companion object {
        var TAG = TaskFragment::class.java.simpleName
        const val ARG_POSITION: String = "positioin"

        fun newInstance(): TaskFragment {
            var fragment = TaskFragment();
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        taskViewModel =
            ViewModelProviders.of(this).get(TaskViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_task, container, false);
        val id = arguments?.getString("taskID")
        initView(id)
        return root
    }

    private fun initView(id: String?) {
        val task : Task = GetTaskByID(id)
        val executorTextView: TextView = root.findViewById(R.id.taskExecutor)
        val nameTextView: TextView = root.findViewById(R.id.taskName)
        val creatorTextView: TextView = root.findViewById(R.id.taskCreator)
        val deadlineTextView: TextView = root.findViewById(R.id.taskDeadline)
        val descriptionTextView: TextView = root.findViewById(R.id.taskDescription)
        val priorityTextView: TextView = root.findViewById(R.id.taskPriority)
        val stateTextView: TextView = root.findViewById(R.id.taskState)
        taskViewModel.text.observe(viewLifecycleOwner, Observer {
            executorTextView.text = GetExecutorById(task.executorsIDs.first())?.getName()
            nameTextView.text = task.taskName
            creatorTextView.text = GetCreatorById(task.creatorID)?.getName()
            deadlineTextView.text = task.deadline
            priorityTextView.text = task.priority
            stateTextView.text = task.state
            descriptionTextView.text = task.taskDescription
        })
    }

    private fun GetExecutorById(executorID: String?): User? {
        //return AwsApi.getUser(executorID)
        return null
    }

    private fun GetCreatorById(creatorID: String?): User? {
        //return AwsApi.getUser(creatorID)
        return null
    }


    private fun GetTaskByID(id: String?): Task {
        return getTaskIDasync().execute("1").get() //TODO change 1 to id
        //return Task("2","12.05.2020", listOf("1"),
        //    "groupID",id,"important","new","test task","Name")
    }


    // async
    private class getTaskIDasync : AsyncTask<String, Int, Task>() {

        // Do the long-running work in here
        override fun doInBackground(vararg id1: String): Task? {
            Log.v("deb",id1[0])
            return AwsApi.getTask(id1[0])
        }

        // This is called each time you call publishProgress()
        override fun onProgressUpdate(vararg progress: Int?) {
            //setProgressPercent(progress.firstOrNull() ?: 0)
        }

        // This is called when doInBackground() is finished
        override fun onPostExecute(result: Task?) {
            //showNotification("Downloaded $result bytes")
            Log.v("REST_TASK","Got task info from server")
        }

    }



}
