package com.example.projectmanagementapp.ui.AddTask

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.projectmanagementapp.AwsAPI.AwsApisAsyncWrapper
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.Team
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.Clog
import com.example.projectmanagementapp.extensions.loadPreference


class AddTaskFragment : Fragment() {

    private lateinit var addTaskViewModel: AddTaskViewModel
    var navController: NavController? = null
    private lateinit var root: View
    private lateinit var editDate : Button
    private lateinit var id: String
    private lateinit var hash: String
    private lateinit var groupList :ArrayList<Team>
    private lateinit var usersList :ArrayList<User>
    private lateinit var user : User
    private lateinit var activeGroup : Team
    private var spinnerChoiceRestart = true

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addTaskViewModel =
                ViewModelProviders.of(this).get(AddTaskViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_addtask, container, false)
        id = loadPreference(this.context,"Id") as String
        hash = loadPreference(this.context,"PasswordHash") as String
        initData()
        setGroups()
        //setExecutors() // this line is executed by spinner onItemSelected
        setGroupSpinnerListener()
        setButtonListeners()
        setDatePicker()

        return root
    }

    private fun initData() {
        Clog.log("Entered into initData")
        user = AwsApisAsyncWrapper.getUserAsync().execute(id, hash).get()
        groupList = ArrayList<Team>()
        usersList = ArrayList<User>()
        for(groupid in user.groupIDs)
        {
            val team = AwsApisAsyncWrapper.getTeamAsync().execute(groupid,user.id,hash).get()
            if (team?.groupName != null) {
                groupList.add(team)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setDatePicker() {
        editDate = root.findViewById(R.id.taskDeadline)
        editDate.text = "Set deadline"
        editDate.setOnClickListener {
            clickDataPicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDataPicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd =
            this.context?.let {
                DatePickerDialog(it, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                   editDate.text = dayOfMonth.toString() + "-" + monthOfYear.toString() + "-" + year.toString()
                }, year, month, day)
            }
        if (dpd != null) {
            dpd.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setButtonListeners() {
        val submitButton: Button = root.findViewById(R.id.submitButton)
        val cancelButton: Button = root.findViewById(R.id.cancelButton)
        submitButton.setOnClickListener {
            Clog.log("submit button pressed")
            subimtTaskWrapper()
        }
        cancelButton.setOnClickListener {
            Clog.log("cancel button pressed")
            navController?.navigate(
                R.id.action_nav_addtask_to_nav_mytasks
            )
        }
    }

    private fun setExecutors() {
        val executors: Spinner = root.findViewById(R.id.taskExecutor)
        val list: MutableList<String> = GetUserNames()
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        executors.setAdapter(dataAdapter)
    }

     private fun GetUserNames(): MutableList<String> {
         Clog.log("I am adding new users")
         val list : MutableList<String> = ArrayList()
         usersList = ArrayList()

         var memberIds = ArrayList<String>()
         val groupSpinner : Spinner = root.findViewById(R.id.taskGroup)
         var groupId : String
         if(groupSpinner.selectedItem == null)
             return list

         groupId = getGroupId(groupSpinner.selectedItem.toString())
         val team = AwsApisAsyncWrapper.getTeamAsync().execute(groupId,user.id,hash).get()
         if (team!= null && team.groupName != null) {
             for(memberId in team.usersIDs) {
                 var member =
                     AwsApisAsyncWrapper.getExternalUserAsync().execute(memberId, id, hash).get()
                 if (member != null && member.name != null) {
                     usersList.add(member)
                     list.add(member.name)
                 }

             }
         }
         Clog.log("New users list created: "+list.joinToString { " "})
         return list
    }

    private fun setGroups() {
        val groups: Spinner = root.findViewById(R.id.taskGroup)
        val list: MutableList<String> = GetGroupNames()
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        groups.setAdapter(dataAdapter)

        spinnerChoiceRestart = true
    }
     private fun GetGroupNames(): MutableList<String> {
         val list : MutableList<String> = ArrayList()
         for(group in groupList)
         {
             if(group.groupName != null)
                 list.add(group.groupName)
         }
         return list
     }

    private fun ClearPage() {
        val nameTextView: TextView = root.findViewById(R.id.taskName)
        val descriptionTextView: TextView = root.findViewById(R.id.taskDescription)
        val priorityDropdown: Spinner = root.findViewById(R.id.taskPriority)
        val executor: Spinner = root.findViewById(R.id.taskExecutor)
        val group : Spinner = root.findViewById(R.id.taskGroup)
        nameTextView.text = ""
        descriptionTextView.text = ""
        editDate.text = "Set deadline"
        priorityDropdown.setSelection(0);
        executor.setSelection(0);
        group.setSelection(0)

    }


    private fun submitTask() {
        Clog.log("Submit Task was chosen, userID: $id, hash: $hash")
        val priorityDropdown: Spinner = root.findViewById(R.id.taskPriority)
        val executorSpinner: Spinner = root.findViewById(R.id.taskExecutor)
        var executorId : MutableList<String>?
        if(executorSpinner.selectedItem != null)
        {
             executorId = getExecutorId(executorSpinner.selectedItem.toString())
        }
        else executorId = getExecutorId("")
        val groupSpinner : Spinner = root.findViewById(R.id.taskGroup)
        var groupId : String
        if(groupSpinner.selectedItem != null)
        {
            groupId = getGroupId(groupSpinner.selectedItem.toString())
        }
        else groupId = getGroupId("")
        val nameTextView: TextView = root.findViewById(R.id.taskName)
        val descriptionTextView: TextView = root.findViewById(R.id.taskDescription)
        val date = if (editDate.text.toString()!="Set deadline")  editDate.text.toString() else "" //todo check if cause db error
        val task = Task(id, date, executorId,groupId, null, priorityDropdown.selectedItem.toString(),
            "new",descriptionTextView.text.toString(),nameTextView.text.toString())
        Clog.log( "Task object was created: $task")
        updateDataBase(task)
        Toast.makeText(root.context,"Saved",Toast.LENGTH_SHORT).show()
        ClearPage()
        //TODO przy powrocie do ekranu tasków powinno wczytywać z pamięci, a nie ładować od nowa (tutaj trzeba będzie pobrać id taska z odpowiedzi serwera i zapisać w tasku)
    }

    private fun updateDataBase(task: Task) {
        var taskid =  AwsApisAsyncWrapper.postOrUpdateTaskAsync().execute(Pair(Pair(task,false), Pair(id,hash))).get()
        taskid = taskid.replace("\"","")
        val group = groupList.find{x -> x.ID == task.groupID}
        val executor = usersList.find{x -> x.ID == task.executorsIDs.first()}
        if (group != null) {
            group.taskIDs.add(taskid)
            AwsApisAsyncWrapper.postOrUpdateGroupAsync().execute(Pair(Pair(group,true), Pair(id,hash))).get()
        }
        if (executor != null) {
            executor.taskIDs.add(taskid)
            AwsApisAsyncWrapper.UpdateUserAsync().execute(Pair(executor,Pair(id,hash))).get()
        }
        else if(task.executorsIDs.first() == id)
        {
            var user  = AwsApisAsyncWrapper.getUserAsync().execute(id, hash).get()
            user.taskIDs.add(taskid)
            AwsApisAsyncWrapper.UpdateUserAsync().execute(Pair(user,Pair(id,hash))).get()
        }

    }

    private fun subimtTaskWrapper(){ // ensure task name is not null
        val nameTextView: TextView = root.findViewById(R.id.taskName)
        if (nameTextView.text.toString()=="")
            Toast.makeText(root.context,"Task name cannot be empty",Toast.LENGTH_SHORT).show()
        else if(!checkExecutor())
            Toast.makeText(root.context,"Selected executor is not a member of selected group",Toast.LENGTH_SHORT).show()
        else submitTask()
    }

    private fun checkExecutor(): Boolean {
        val executorSpinner: Spinner = root.findViewById(R.id.taskExecutor)
        val groupSpinner : Spinner = root.findViewById(R.id.taskGroup)
        if(executorSpinner.selectedItem == null)
        {
            return true
        }
        val executorId = getExecutorId(executorSpinner.selectedItem.toString())?.first()
        val group = groupList.find{x -> x.groupName == groupSpinner.selectedItem.toString()}
        if(group?.usersIDs?.contains(executorId)!!)
            return true
        return false
    }


    private fun getExecutorId(executorName: String): MutableList<String>? {
        val list : MutableList<String> = ArrayList()
        var executorId = usersList.find{ x-> x.name == executorName}?.ID
        if(executorId!= null)
            list.add(executorId)
        else list.add(id)
        return list
    }
    private fun getGroupId(groupName: String): String {
        var id = groupList.find{ x-> x.groupName == groupName}?.ID
        if(id != null)
            return id
        return ""
    }

    private fun setGroupSpinnerListener(){
        val groups: Spinner = root.findViewById(R.id.taskGroup)
        groups.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                Clog.log("CUSTOM_SPINNER", "item in group spinner selected")
                setExecutors()
            }

        })
    }


}
