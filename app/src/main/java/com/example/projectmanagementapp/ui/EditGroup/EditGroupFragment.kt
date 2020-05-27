package com.example.projectmanagementapp.ui.EditGroup

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Team
import com.example.projectmanagementapp.data.model.User
import com.example.projectmanagementapp.extensions.loadPreference

class EditGroupFragment : Fragment(){
    private lateinit var editGroupViewModel: EditGroupViewModel
    private lateinit var root: View
    private lateinit var id: String
    private lateinit var hash: String
    private lateinit var groups : ArrayList<Team>
    private lateinit var users : ArrayList<User>
    private lateinit var usersNames : Array<CharSequence>
    private  var selectedMembers: ArrayList<CharSequence> = ArrayList<CharSequence>()
    private var canChooseMembers : Boolean = false
    private lateinit var checkedMembers : BooleanArray

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editGroupViewModel =
            ViewModelProviders.of(this).get(EditGroupViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_editgroup, container, false)
        id = loadPreference(this.context,"Id") as String
        hash = loadPreference(this.context,"PasswordHash") as String
        editGroupViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        setGroups()
        setGroupsSpinner()
        setMembers()
        setButtonListeners()
        return root
    }

    private fun setGroups() {
        //groups = AwsApi.getTeam()
        //get user groups
        //filter ones in which user is admin
    }

    private fun setGroupsSpinner() {
        val groups: Spinner = root.findViewById(R.id.groupToEdit)
        //val list: MutableList<String> = GetGroupNames()
        val list : MutableList<String> = GetDummyGroups()
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        groups.setAdapter(dataAdapter)

    }
    private fun setButtonListeners() {
        val chooseGroupButton : Button = root.findViewById(R.id.chooseGroupButton)
        val submitButton: Button = root.findViewById(R.id.submitButton)
        val cancelButton: Button = root.findViewById(R.id.cancelButton)
        val membersButton: Button = root.findViewById(R.id.membersButton)
        chooseGroupButton.setOnClickListener {
            ChooseGroup()
        }
        submitButton.setOnClickListener {
            SubmitUpdate();
        }
        cancelButton.setOnClickListener {
            ClearPage();
        }
        membersButton.setOnClickListener {
            EditMembersList();
        }
    }

    private fun ChooseGroup() {
        val groupName: Spinner = root.findViewById(R.id.groupToEdit)
        setGroupData( groupName.selectedItem.toString())
    }

    private fun setGroupData(name: String) {
       // val group = groups.find{it.groupName == name}
        val dummyTaskList = ArrayList<String>()
        dummyTaskList.add("2")
        val group : Team = Team(id,name,"5", dummyTaskList)
        val nameText: EditText = root.findViewById(R.id.nameEdit)
        val adminId: EditText = root.findViewById(R.id.adminIDEdit)
        nameText.setText(group?.groupName)
        adminId.setText(group?.adminID)
         checkedMembers = BooleanArray(usersNames.size)
        for(i in 0 until usersNames.size)
        {
            //TODO should be initialized base on members
            checkedMembers[i] = false
        }
        canChooseMembers = true

    }
    private fun EditMembersList(){
        if(canChooseMembers)
        {
            multiCheckBoxesDialog()
        }
    }
    private fun multiCheckBoxesDialog() {
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


    private fun ClearPage() {

        val group : Spinner = root.findViewById(R.id.taskGroup)
        group.setSelection(0)

    }

    private fun SubmitUpdate() {
        ClearPage()
    }
    private fun setMembers() {
        //val users =  AwsApi.GetUsers()
        // save parameter
        // }
        usersNames = GetDummyNames()
    }
    private fun GetDummyNames():  Array<CharSequence> {
        val listItems: MutableList<String> = ArrayList()
        listItems.add("Maciej")
        listItems.add("Agata")
        listItems.add("Juliusz")
        listItems.add("Juliusz")
        listItems.add("Juliusz")
        listItems.add("Juliusz")
        listItems.add("Juliusz")
        listItems.add("Juliusz")
        listItems.add("Juliusz")
        listItems.add("Maciej")
        listItems.add("Agata")
        listItems.add("Juliusz")
        listItems.add("Maciej")
        listItems.add("Agata")
        listItems.add("Juliusz")
        listItems.add("Maciej")
        listItems.add("Agata")
        listItems.add("Juliusz")
        listItems.add("Maciej")
        listItems.add("Agata")
        listItems.add("Juliusz")
        listItems.add("Maciej")
        listItems.add("Agata")
        listItems.add("Juliusz")
        val charSequenceItems =
            listItems.toTypedArray<CharSequence>()
        return charSequenceItems
    }
    private fun GetDummyGroups(): MutableList<String> {
        val list  = ArrayList<String>()
        list.add("Informatycy")
        list.add("Elektronika")
        list.add("Marketing")
        return list
    }

}

