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
import android.widget.*
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.data.model.Team
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.loadPreference

class AddGroupFragment : Fragment(){
    private lateinit var addGroupViewModel: AddGroupViewModel
    private lateinit var root: View
    private lateinit var id: String
    private lateinit var hash: String
    private lateinit var users : ArrayList<User>
    private lateinit var usersNames : Array<CharSequence>
    private  var selectedMembers: ArrayList<CharSequence> = ArrayList<CharSequence>()
    private lateinit var checkedMembers : BooleanArray
    private lateinit var tasks : ArrayList<Task>
    private lateinit var tasksNames : Array<CharSequence>
    private  var selectedTasks: ArrayList<CharSequence> = ArrayList<CharSequence>()
    private lateinit var checkedTasks : BooleanArray


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addGroupViewModel =
            ViewModelProviders.of(this).get(AddGroupViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_addgroup, container, false)

        addGroupViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        id = loadPreference(this.context,"Id") as String
        hash = loadPreference(this.context,"PasswordHash") as String
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
            SubmitUpdate();
        }
        cancelButton.setOnClickListener {
            ClearPage();
        }
        membersButton.setOnClickListener {
            EditMembersList();
        }
        tasksButton.setOnClickListener {
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
        //ToDo use members!!!
        var newTeam = Team(id,nameText.text.toString(),groupIdText.text.toString(),tasksIds)
        //add team
        Toast.makeText(root.context,"Saved",Toast.LENGTH_SHORT).show()
        ClearPage()
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
            var user = users.find{x-> x.name == memberName}
            if (user != null)
                usersIds.add(user.ID)
        }
        return usersIds
    }

    private fun setUsers() {
        //val users =  AwsApi.GetUsers()
        // save parameter
        // }
        usersNames = GetDummyNames()
        checkedMembers = BooleanArray(usersNames.size)
        initCheckedList(usersNames.size, checkedMembers)

    }
    private fun GetDummyNames():  Array<CharSequence> {
        val listItems: MutableList<String> = ArrayList()
        listItems.add("Maciej")
        listItems.add("Agata")
        listItems.add("Juliusz")
        val charSequenceItems =
            listItems.toTypedArray<CharSequence>()
        return charSequenceItems
    }
    private fun setTasks() {
        //val users =  AwsApi.GetUsers()
        // save parameter
        // }
        tasksNames = GetDummyTasks()
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
    private fun GetDummyTasks():  Array<CharSequence> {
        val listItems: MutableList<String> = ArrayList()
        listItems.add("Add engine")
        listItems.add("Create prototype")
        listItems.add("Refactor code")
        val charSequenceItems =
            listItems.toTypedArray<CharSequence>()
        return charSequenceItems
    }
}




