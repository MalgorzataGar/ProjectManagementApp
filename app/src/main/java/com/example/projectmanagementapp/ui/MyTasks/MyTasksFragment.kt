package com.example.projectmanagementapp.ui.MyTasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.core.os.bundleOf

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagementapp.AwsAPI.AwsApi
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.OnItemClickListener
import com.example.projectmanagementapp.extensions.loadPreference
import com.example.projectmanagementapp.ui.TaskView.TaskFragment
import com.google.android.material.snackbar.Snackbar

class MyTasksFragment : Fragment() {

    private lateinit var myTasksViewModel: MyTasksViewModel
    protected lateinit var rootView: View
    var navController: NavController? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: com.example.projectmanagementapp.extensions.ListAdapter
    lateinit var id :String
    lateinit var hash :String

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
        id = loadPreference(this.context,"Id") as String
        hash = loadPreference(this.context,"PasswordHash") as String
    }

    private fun onCreateComponent() {
        adapter = com.example.projectmanagementapp.extensions.ListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_mytasks, container, false);
        initView()
        return rootView
    }

    private fun initView(){
        setUpAdapter()
        initializeRecyclerView()
        setUpDummyData()
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
        val priority : Spinner = rootView.findViewById(R.id.taskPriority)
        if(priority.selectedItem.toString() != "All")
        {
            //gettask list with priority
        }
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

    private fun setUpDummyData(){
        adapter.clear()
        var list: ArrayList<Task> = ArrayList<Task>()
        var task1 = Task()
        task1.taskName = "Taks1"
        task1.priority = "normal"
        task1.state = "todo"
        task1.ID = "1"
        var task2 = Task()
        task2.taskName = "Taks2"
        task2.ID = "2"
        task2.priority = "minor"
        task2.state = "inprogress"
        var task3 = Task()
        task3.taskName = "Taks3"
        task3.ID = "3"
        task3.priority = "major"
        task3.state = "done"
        list.add(task1)
        list.add(task2)
        list.add(task3)
        adapter.addItems(list)
    }
    private fun getTaskList(priority : String?)
    {
        adapter.clear()
        val user : User = AwsApi.getUser("1","")
        var list: ArrayList<Task> = ArrayList<Task>()
        if(priority !=null)
        {
            for (taskId in user.taskIDs)
            {
                //val task = AwsApi.getTask(taskId)
                //if task priority == priority
                // list.add(AwsApi.getTask(taskId))
            }
        }
        else
        {
            for (taskId in user.taskIDs)
            {
                //val task = AwsApi.getTask(taskId)
                // list.add(AwsApi.getTask(taskId))
            }
        }

        adapter.addItems(list)
    }
}
