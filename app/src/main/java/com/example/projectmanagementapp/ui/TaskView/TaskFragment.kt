package com.example.projectmanagementapp.ui.TaskView

import android.os.AsyncTask
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.projectmanagementapp.AwsAPI.AwsApi
import com.example.projectmanagementapp.AwsAPI.AwsApisAsyncWrapper
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.Team
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.loadPreference

class TaskFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    var navController: NavController? = null
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
        //id = "1" // todo user delete it
        //hash = "dasijioasdjijdsaijdsa" // todo user delete it
        initView(taskId)
        initButtons()
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
    private fun initButtons() {
        val stateButton: Button = root.findViewById(R.id.changeStateButton)
        val deleteButton: Button = root.findViewById(R.id.deleteButton)
        if(task.executorsIDs.first() != id)
        {
            stateButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
        }
        else
        {
            stateButton.setOnClickListener {
                ChangeTaskState()
            }
            deleteButton.setOnClickListener {
                DeleteTask()
            }
        }

    }

    private fun DeleteTask() {
        AwsApisAsyncWrapper.deleteTaskAsync().execute(task.ID, id, hash).get()
        val group = GetGroupByID(task.groupID)
        val executor = GetExecutorById(task.executorsIDs.first())
        if (group.taskIDs != null && group.taskIDs.contains(task.ID)) {
            group.taskIDs.remove(task.ID)
            AwsApisAsyncWrapper.postOrUpdateGroupAsync().execute(Pair(Pair(group,true), Pair(id,hash))).get()
        }
        if (executor?.taskIDs != null && executor.taskIDs.contains(task.ID)) {
            executor.taskIDs.remove(task.ID)
            AwsApisAsyncWrapper.UpdateUserAsync().execute(Pair(executor,Pair(id,hash))).get()
        }
        navController?.navigateUp()
    }

    private fun ChangeTaskState() {
        val text = task.state
        when (text) {
            "new" -> task.state = "inprogress"
            "inprogress" ->task.state = "done"
        }
        AwsApisAsyncWrapper.postOrUpdateTaskAsync().execute(Pair(Pair(task,true),Pair(id,hash))).get()
        task = GetTaskByID(task.ID)
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
        val groupTextView: TextView = root.findViewById(R.id.taskGroup)
        taskViewModel.text.observe(viewLifecycleOwner, Observer {
            executorTextView.text = GetExecutorById(task.executorsIDs.first())?.getName()
            nameTextView.text = task.taskName
            creatorTextView.text = GetCreatorById(task.creatorID)?.getName()
            deadlineTextView.text = task.deadline
            priorityTextView.text = task.priority
            stateTextView.text = task.state
            descriptionTextView.text = task.taskDescription
            groupTextView.text = GetGroupByID(task.groupID)?.groupName
        })
        setStateButtonText()
    }

    private fun setStateButtonText() {
        val text = task.state
        val stateButton: Button = root.findViewById(R.id.changeStateButton)
        when (text) {
            "new" -> stateButton.text = getString(R.string.stateButtonStartProgress)
            "inprogress" -> stateButton.text = getString(R.string.stateButtonFinish)
            "done" -> stateButton.visibility = View.GONE
        }
    }

    private fun GetExecutorById(executorID: String?): User? {
        val user: User = AwsApisAsyncWrapper.getExternalUserAsync().execute(executorID,id, hash).get()
        return user
    }

    private fun GetCreatorById(creatorID: String?): User? {
        val user: User = AwsApisAsyncWrapper.getExternalUserAsync().execute(creatorID,id, hash).get()
        return user
    }


    private fun GetTaskByID(taskId: String?): Task {
        val task = AwsApisAsyncWrapper.getTaskIDasync().execute(taskId, id, hash).get()
        return task
    }
    private fun GetGroupByID(groupId: String?): Team {
        val team = AwsApisAsyncWrapper.getTeamAsync().execute(groupId, id, hash).get()
        return team
    }

}
