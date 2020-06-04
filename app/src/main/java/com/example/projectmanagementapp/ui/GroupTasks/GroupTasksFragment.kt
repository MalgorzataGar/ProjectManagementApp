package com.example.projectmanagementapp.ui.GroupTasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.example.projectmanagementapp.AwsAPI.AwsApisAsyncWrapper
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.Team
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.Clog
import com.example.projectmanagementapp.extensions.OnItemClickListener
import com.example.projectmanagementapp.extensions.loadPreference
import com.example.projectmanagementapp.ui.TaskView.TaskFragment

class GroupTasksFragment : Fragment() {

    private lateinit var groupTasksViewModel: GroupTasksFragment
    private lateinit var groupList :ArrayList<Team>
    protected lateinit var rootView: View
    var navController: NavController? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: com.example.projectmanagementapp.extensions.ListAdapter
    lateinit var id: String
    lateinit var hash: String

    companion object {
        var TAG = GroupTasksFragment::class.java.simpleName
        const val ARG_POSITION: String = "positioin"

        fun newInstance(): GroupTasksFragment {
            var fragment = GroupTasksFragment();
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Clog.log("Enter into Group task fragment")
        super.onCreate(savedInstanceState)
        onCreateComponent()
    }

    private fun onCreateComponent() {
        adapter = com.example.projectmanagementapp.extensions.ListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_grouptasks, container, false);
        initView()
        return rootView
    }

    private fun initView(){
        setUpAdapter()
        //id = loadPreference(this.context,"Id") as String
        //hash = loadPreference(this.context,"PasswordHash") as String
        id = "1" // TODO user delete it
        hash = "dasijioasdjijdsaijdsa" // TODO user delete it
        groupList = arrayListOf<Team>()

        initGroups()
        initializeRecyclerView()
        Clog.log("initialize Groups and recycleview")

        if (!groupList.isEmpty()) {
            getTaskList(groupName = "")
            setUpGroups()
        }
        setFilterListener()
    }

    private fun initGroups() {
        val user : User = AwsApisAsyncWrapper.getUserAsync().execute(id,hash).get()
        Clog.log("user groups: "+user.groupIDs.joinToString(" "))
        for( groupid in user.groupIDs)
        {
            val team = AwsApisAsyncWrapper.getTeamAsync().execute(groupid,user.id,hash).get()
            if (team.groupName != null)
                groupList.add(AwsApisAsyncWrapper.getTeamAsync().execute(groupid,user.id,hash).get())
            // TODO if group is null (group deleted) - remove groupID from user data
        }
        Clog.log("groups: "+groupList.joinToString (" "))
    }


    private fun setFilterListener() {
    val filterButton: Button = rootView.findViewById(R.id.applyButton)
    filterButton.setOnClickListener {
        Clog.log("Apply filter for groups was activated")
        filtrTasks()
    }
}
    private fun filtrTasks() {
        val groupSpinner : Spinner = rootView.findViewById(R.id.spinnerGroup)
        val selected = groupSpinner.selectedItem.toString()
        if(selected != "All")
        {
            getTaskList(groupName = selected)
        }
        else
        {
            getTaskList(groupName = "")
        }

    }

    private fun setUpGroups() {
        val group: Spinner = rootView.findViewById(R.id.spinnerGroup)
        val list: MutableList<String> = GetUserGroupsNames()
        //val list : MutableList<String> = GetDummyGroupsNames()
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            rootView.context,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        group.setAdapter(dataAdapter)
    }

    private fun GetDummyGroupsNames(): MutableList<String> {
        val list  = java.util.ArrayList<String>()
        list.add("All")
        list.add("Informatycy")
        list.add("Elektronika")
        list.add("Marketing")
        return list
    }
    private fun GetUserGroupsNames(): MutableList<String> {
        val list  = java.util.ArrayList<String>()
        //list.add("All") // its have no sense in this case
        for(group in groupList)
        {
            list.add(group.groupName)
        }
        return list
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
                    R.id.action_nav_grouptasks_to_nav_task,
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
        task1.ID = "1"
        var task2 = Task()
        task2.taskName = "Taks2"
        task2.ID = "2"
        var task3 = Task()
        task3.taskName = "Taks3"
        task3.ID = "3"
        list.add(task1)
        list.add(task2)
        list.add(task3)
        adapter.addItems(list)
    }
    private fun getTaskList(groupName : String?)
    {
        Log.v("GroupTaskFragment","Enter getTaskList()")
        adapter.clear()

        var groupId: Team;
        if (groupName == ""){
            if (groupList.isEmpty())
                return;
            else
                groupId = groupList[0]
        }
        else {
            groupId = groupList.find { it.groupName == groupName } ?: return // retrun if null
        }
        Clog.log("in use groupID $groupId")

        //user analise
        val user : User = AwsApisAsyncWrapper.getUserAsync().execute(id,hash).get()
        Clog.log("user to get group tasks $user")

        var tasksList: ArrayList<Task> = ArrayList<Task>()

        val groupTaskIDsList = groupId.taskIDs
        for (taskId in groupTaskIDsList){
            val task:Task? = AwsApisAsyncWrapper.getTaskIDasync().execute(taskId, id, hash).get()

            if (task!=null)
                if (task.taskName!="") {
                    tasksList.add(task)
                    Clog.log("task added to list: $task")
                    continue
                }
            Clog.log("task was not added to list - taskID: $taskId")
            //TODO - delete id from groupTasks if task return null
        }
        // bellow code to show all user tasks in group, above show all tasks in group
        /*
        var taskIdList: ArrayList<String> = ArrayList()
        taskIdList.addAll(user.taskIDs)
        /*for(groupid in user.groupIDs)
        {
            taskIdList.addAll(AwsApi.getTeam(groupid).taskIDs)
        }*/
        if(!groupName.isNullOrEmpty())
        {
            for (taskId in taskIdList) //TODO do analizy
            {
                val task = AwsApisAsyncWrapper.getTaskIDasync().execute(taskId).get()
                /*if (task.groupID == groupId && task.taskName != null) {
                    tasksList.add(task)
                }*/
                tasksList.add(task)
            }
        }
        else
        {
            for (taskId in taskIdList)
            {
                val task = AwsApisAsyncWrapper.getTaskIDasync().execute(taskId).get()
                if(task.taskName != null)
                {
                    tasksList.add(task)
                }

            }
        }*/
        adapter.addItems(tasksList)
        Clog.log("task list added to adapter")
    }
}
