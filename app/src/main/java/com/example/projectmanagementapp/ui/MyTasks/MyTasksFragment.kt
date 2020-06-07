package com.example.projectmanagementapp.ui.MyTasks

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagementapp.AwsAPI.AwsApisAsyncWrapper
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.Clog
import com.example.projectmanagementapp.extensions.OnItemClickListener

class MyTasksFragment : Fragment() {

    private lateinit var myTasksViewModel: MyTasksViewModel
    protected lateinit var rootView: View
    var navController: NavController? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: com.example.projectmanagementapp.extensions.ListAdapter
    lateinit var id: String
    lateinit var hash: String

    companion object {
        var TAG = MyTasksFragment::class.java.simpleName
        const val ARG_POSITION: String = "positioin"

        fun newInstance(): MyTasksFragment {
            var fragment = MyTasksFragment();
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()
        //id = loadPreference(this.context,"Id") as String
        //hash = loadPreference(this.context,"PasswordHash") as String
        id = "1" // todo user delete it
        hash = "dasijioasdjijdsaijdsa" // todo user delete it
    }

    private fun onCreateComponent() {
        adapter = com.example.projectmanagementapp.extensions.ListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_mytasks, container, false);
        initView()
        return rootView
    }

    private fun initView() {
        setUpAdapter()
        initializeRecyclerView()
        //TODO add loading screen (eg. spining circle) - to long time to load all tasks
        getTaskList("All")
        setAddTaskButton()
        setFilterListener()
    }

    private fun setFilterListener() {
        val filterButton: Button = rootView.findViewById(R.id.applyButton)
        filterButton.setOnClickListener {
            filtrTasks()
        }
    }

    private fun filtrTasks() {
        val priority: Spinner = rootView.findViewById(R.id.taskPriority)
        Clog.log("Priority set to: $priority")
        getTaskList(priority = priority.selectedItem.toString())
    }

    private fun setAddTaskButton() {
        val fab: View = rootView.findViewById(R.id.addTaskFab)
        fab.setOnClickListener { view ->
            navController?.navigate(
                R.id.action_nav_mytasks_to_nav_addtask
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun setUpAdapter() {
        adapter.setOnItemClickListener(onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                var task = adapter.getItem(position)
                val bundle = bundleOf(
                    "taskID" to task?.ID
                )
                navController?.navigate(
                    R.id.action_nav_mytasks_to_nav_task,
                    bundle
                )
            }

        })
    }

    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }


    private fun getTaskList(priority: String?) {
        Clog.log("Enter getTaskList() with userID:$id and priority: $priority")
        adapter.clear()
        //user analise
        val user: User = AwsApisAsyncWrapper.getUserAsync().execute(id, hash).get()
        if (user != null)
            Clog.log(user.toString())
        else
            Clog.log("retrieve empty user")

        var list: ArrayList<Task> = ArrayList<Task>()

        for (taskId in user.taskIDs) {
            Clog.log("$priority analise task id: $taskId")
            val task = AwsApisAsyncWrapper.getTaskIDasync().execute(taskId, user.ID, hash).get()
            if ((task.priority == priority || priority == "All")
                && task.taskName != null
            ) {
                Clog.log("task added to list of myTasks " + task.toString())
                list.add(task)
                Log.v("MyTaskFragment", "list of tasks created")

            }
        }

            Clog.log("list of tasks: $list")
            adapter.addItems(list)
        }


}
