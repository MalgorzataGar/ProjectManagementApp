package com.example.projectmanagementapp.ui.AddGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.R
import android.app.AlertDialog
import android.util.Log
import android.widget.*
import com.example.projectmanagementapp.AwsAPI.AwsApisAsyncWrapper
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.Team
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.Clog
import com.example.projectmanagementapp.extensions.loadPreference

class AddGroupFragment : Fragment(){
    private lateinit var addGroupViewModel: AddGroupViewModel
    private lateinit var root: View
    private lateinit var id: String
    private lateinit var hash: String
    private lateinit var users : Map<String,String>
    private lateinit var usersNames : Array<CharSequence>
    private  var selectedMembers: ArrayList<CharSequence> = ArrayList<CharSequence>()
    private lateinit var checkedMembers : BooleanArray
    private  var tasks : ArrayList<Task> = ArrayList<Task>()
    private lateinit var tasksNames : Array<CharSequence>
    private  var selectedTasks: ArrayList<CharSequence> = ArrayList<CharSequence>()
    private lateinit var checkedTasks : BooleanArray


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Clog.log("enter add Group Fragment")
        addGroupViewModel =
            ViewModelProviders.of(this).get(AddGroupViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_addgroup, container, false)

        addGroupViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        id = loadPreference(this.context,"Id") as String
        hash = loadPreference(this.context,"PasswordHash") as String
        //id = "1" // TODO user delete it
        //hash = "dasijioasdjijdsaijdsa" // TODO user delete it
        initializeData()
        setButtonListeners()
        return root
    }

    private fun initializeData() {
        setUsers()
        setTasks()
    }

    private fun setButtonListeners() {
        val submitButton: Button = root.findViewById(R.id.submitButton)
        val cancelButton: Button = root.findViewById(R.id.cancelButton)
        val membersButton: Button = root.findViewById(R.id.membersButton)
        val tasksButton : Button = root.findViewById(R.id.taskButton)
        submitButton.setOnClickListener {
            Clog.log("submint button was pressed")
            SubmitUpdate();
        }
        cancelButton.setOnClickListener {
            Clog.log("cancel button was pressed")
            ClearPage();
        }
        membersButton.setOnClickListener {
            Clog.log("chose member in add group")
            EditMembersList();
        }
        tasksButton.setOnClickListener {
            Clog.log("chose task in add group")
            EditTasksList();
        }
    }
    private fun EditMembersList(){
        lateinit var dialogAlert:AlertDialog
        var builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        builder.setTitle("Select members");
        builder.setMultiChoiceItems(
            usersNames,
            checkedMembers,
            {dialog,position,isChecked->
                checkedMembers[position] = isChecked
                if (isChecked) {
                    selectedMembers.add(usersNames[position])
                } else {
                    selectedMembers.remove(usersNames[position])
                }
            }
        )
        dialogAlert = builder.create()
        dialogAlert.show()
    }
    private fun EditTasksList(){
        lateinit var dialogAlert:AlertDialog
        var builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        builder.setTitle("Select members");
        builder.setMultiChoiceItems(
            tasksNames,
            checkedTasks,
            {dialog,position,isChecked->
                checkedTasks[position] = isChecked
                if (isChecked) {
                    selectedTasks.add(tasksNames[position])
                } else {
                    selectedTasks.remove(tasksNames[position])
                }
            }
        )
        dialogAlert = builder.create()
        dialogAlert.show()
    }
    private fun ClearPage() {
        var nameText : EditText = root.findViewById(R.id.nameEdit)
        var groupIdText : EditText = root.findViewById(R.id.groupIDEdit)
        nameText.setText("")
        groupIdText.setText("")
        selectedMembers.clear()
        selectedTasks.clear()
        initCheckedList(tasksNames.size,checkedTasks)
        initCheckedList(usersNames.size,checkedMembers)
    }

    private fun SubmitUpdate() {
        var nameText : EditText = root.findViewById(R.id.nameEdit)
        var groupIdText : EditText = root.findViewById(R.id.groupIDEdit)
        var tasksIds = getSelectedTasksIds()
        var usersIds = getSelectedUsersIds()
        var newTeam = Team(id,nameText.text.toString(),groupIdText.text.toString(),tasksIds,usersIds)
        updateUsers(usersIds,newTeam.ID)
        updateTasks(tasksIds,newTeam.ID)
        AwsApisAsyncWrapper.postOrUpdateGroupAsync().execute(Pair(Pair(newTeam,false),Pair(id,hash))).get()
       // updateUsers(usersIds,newTeam.ID)
        //updateTasks(tasksIds,newTeam.ID)
        Toast.makeText(root.context,"Saved",Toast.LENGTH_SHORT).show()
        ClearPage()
    }

    private fun updateTasks(tasksIds: ArrayList<String>, groupId: String?) {
        for(taskId in tasksIds)
        {
            var task = AwsApisAsyncWrapper.getTaskIDasync().execute(taskId,id, hash).get()
            task.groupID = groupId
            AwsApisAsyncWrapper.postOrUpdateTaskAsync().execute(Pair(Pair(task,true),Pair(id,hash))).get()
        }
    }

    private fun updateUsers(usersIds: ArrayList<String>,teamId : String) {
        for(userId in usersIds)
        {
            var user = AwsApisAsyncWrapper.getExternalUserAsync().execute(userId,id, hash).get()
            user.groupIDs.add(teamId)
            AwsApisAsyncWrapper.UpdateUserAsync().execute(Pair(user,Pair(id,hash))).get()
        }
        if(!usersIds.contains(id))
        {
            var user = AwsApisAsyncWrapper.getUserAsync().execute(id, hash).get()
            user.groupIDs.add(teamId)
            AwsApisAsyncWrapper.UpdateUserAsync().execute(Pair(user,Pair(id,hash))).get()
        }
    }

    private fun getSelectedTasksIds(): ArrayList<String> {
        var tasksIds = ArrayList<String>()
        for(taskName in selectedTasks.iterator())
        {
            var task = tasks.find{x-> x.taskName == taskName}
            if (task != null)
                tasksIds.add(task.ID)
        }
        return tasksIds
    }
    private fun getSelectedUsersIds(): ArrayList<String> {
        var usersIds = ArrayList<String>()
        for(memberName in selectedMembers.iterator())
        {
            for(user in users)
            {
                if(user.value == memberName)
                    usersIds.add(user.key)
            }
        }
        return usersIds
    }

    private fun setUsers() {
        Clog.log("Enter setUsers() with userID:$id ")
        var usersNamesList = ArrayList<String>()
        users = AwsApisAsyncWrapper.getAllUsersAsync().execute(id,hash).get()
        for(user in users)
        {
            usersNamesList.add(user.value)
        }
        usersNames = usersNamesList.toTypedArray<CharSequence>()
        checkedMembers = BooleanArray(usersNames.size)
        initCheckedList(usersNames.size,checkedMembers)
    }

    private fun setTasks() {
        Clog.log("Enter setTasks() with userID:$id ")
        //user analise
        val user: User = AwsApisAsyncWrapper.getUserAsync().execute(id, hash).get()
        val taskNameList: MutableList<String> = ArrayList()
        for (taskId in user.taskIDs) {
            val task = AwsApisAsyncWrapper.getTaskIDasync().execute(taskId, user.ID, hash).get()
            if (task.taskName != null )
            {
                Clog.log("task added to list of tasks " + task.toString())
                tasks.add(task)
                taskNameList.add(task.taskName)
            }
        }
        tasksNames = taskNameList.toTypedArray<CharSequence>()
        checkedTasks = BooleanArray(tasksNames.size)
        initCheckedList(tasksNames.size,checkedTasks)
    }
    private fun initCheckedList(size: Int, list :BooleanArray )
    {
        for(i in 0 until size)
        {
            list[i] = false
        }
    }

}




