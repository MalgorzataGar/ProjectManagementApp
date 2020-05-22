package com.example.projectmanagementapp.ui.TaskView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.AwsAPI.AwsApi
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.loadPreference

class TaskFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var root :View
    private lateinit var id: String
    private lateinit var hash: String
    private lateinit var task : Task

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
        val taskId = arguments?.getString("taskID")
        id = loadPreference(this.context,"Id") as String
        hash = loadPreference(this.context,"PasswordHash") as String
        initView(taskId)
        initButtons()
        return root
    }

    private fun initButtons() {
        val stateButton: Button = root.findViewById(R.id.changeStateButton)
        val deleteButton: Button = root.findViewById(R.id.deleteButton)
        stateButton.setOnClickListener {
            ChangeTaskState()
        }
        deleteButton.setOnClickListener {
            DeleteTask()
        }
    }

    private fun DeleteTask() {
        //aws api delete task
    }

    private fun ChangeTaskState() {
        val text = task.state
        /*when (text) {
            "todo" -> //aws api update task state
            "inprogress" ->// aws api update task state
            "done" -> //aws api close task
        }*/
        setStateButtonText()
    }

    private fun initView(id: String?) {
        task  = GetTaskByID(id)
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
        setStateButtonText()
    }

    private fun setStateButtonText() {
        val text = task.state
        val stateButton: Button = root.findViewById(R.id.changeStateButton)
        when (text) {
            "todo" -> stateButton.text = getString(R.string.stateButtonStartProgress)
            "inprogress" -> stateButton.text = getString(R.string.stateButtonFinish)
            "done" -> stateButton.text = getString(R.string.stateButtonClose)

        }

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
       // return AwsApi.getTask(id)
        return Task("2","12.05.2020", listOf("1"),
            "groupID",id,"important","new","test task","Name")
    }


}
