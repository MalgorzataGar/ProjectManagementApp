package com.example.projectmanagementapp.ui.AddTask

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.AwsAPI.AwsApi
import com.example.projectmanagementapp.AwsAPI.AwsApisAsyncWrapper
import com.example.projectmanagementapp.AwsAPI.AwsApisAsyncWrapper.postOrUpdateTaskAsync
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.extensions.Clog
import com.example.projectmanagementapp.extensions.loadPreference
import java.util.*


class AddTaskFragment : Fragment() {

    private lateinit var addTaskViewModel: AddTaskViewModel
    private lateinit var root: View
    private lateinit var editDate : Button
    private lateinit var id: String
    private lateinit var hash: String

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addTaskViewModel =
                ViewModelProviders.of(this).get(AddTaskViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_addtask, container, false)
        //id = loadPreference(this.context,"Id") as String
        //hash = loadPreference(this.context,"PasswordHash") as String
        id = "1" // TODO user delete it
        hash = "dasijioasdjijdsaijdsa" // TODO user delete it

        setGroups()
        setExecutors()
        setButtonListeners()
        setDatePicker()

        return root
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

    private fun setButtonListeners() {
        val submitButton: Button = root.findViewById(R.id.submitButton)
        val cancelButton: Button = root.findViewById(R.id.cancelButton)
        submitButton.setOnClickListener {
            Clog.log("submit button pressed")
            subimtTaskWrapper()
        }
        cancelButton.setOnClickListener {
            Clog.log("cancel button pressed")
            fragmentManager?.popBackStack() // TODO  replace - cause error when reload fragment
            //ClearPage();
        }
    }

    private fun setExecutors() {
        val executors: Spinner = root.findViewById(R.id.taskExecutor)
        //val list: MutableList<String> = GetUserNames()
        val list : MutableList<String> = GetDummyNames()
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        executors.setAdapter(dataAdapter)
    }

    private fun GetDummyNames(): MutableList<String> {
        val list  = ArrayList<String>()
        list.add("Maciej")
        list.add("Agata")
        list.add("Juliusz")
        return list
    }
    private fun GetDummyGroups(): MutableList<String> {
        val list  = ArrayList<String>()
        list.add("Informatycy")
        list.add("Elektronika")
        list.add("Marketing")
        return list
    }

    // private fun GetUserNames(): MutableList<String> {
        //val users =  AwsApi.GetUsers()
        // save parameter
   // }

    private fun setGroups() {
        val groups: Spinner = root.findViewById(R.id.taskGroup)
        //val list: MutableList<String> = GetGroupNames()
        val list : MutableList<String> = GetDummyGroups()
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_item, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        groups.setAdapter(dataAdapter)

    }
    // private fun GetGroupNames(): MutableList<String> {
    //val users =  AwsApi.GetGroups()
    // save list of group!
    //return list of names
    // }

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

    private fun SubmitTask() {
        Clog.log("Submit Task was chosen, userID: $id, hash: $hash")
        val taskID = "13" //TODO replace with id handling
        val priorityDropdown: Spinner = root.findViewById(R.id.taskPriority)
        val executor: Spinner = root.findViewById(R.id.taskExecutor)
        val group : Spinner = root.findViewById(R.id.taskGroup)
        val nameTextView: TextView = root.findViewById(R.id.taskName)
        val descriptionTextView: TextView = root.findViewById(R.id.taskDescription)

        val date = if (editDate.text.toString()!="Set deadline")  editDate.text.toString() else "" //todo check if cause db error
        val task = Task(id, date, getExecutorId(executor.selectedItem.toString()),
            getGroupId(group.selectedItem.toString()), taskID, priorityDropdown.selectedItem.toString(),
            "new",descriptionTextView.text.toString(),nameTextView.text.toString())
        Clog.log( "Task object was created: $task")

        postOrUpdateTaskAsync().execute(Pair(Pair(task,false), Pair(id,hash))) //TODO if update task - create handling for true
        Toast.makeText(root.context,"Saved",Toast.LENGTH_SHORT).show()
        ClearPage()
        //TODO przy powrocie do ekranu tasków powinno wczytywać z pamięci, a nie ładować od nowa (tutaj trzeba będzie pobrać id taska z odpowiedzi serwera i zapisać w tasku)
    }

    private fun subimtTaskWrapper(){ // ensure task name is not null
        val nameTextView: TextView = root.findViewById(R.id.taskName)
        if (nameTextView.text.toString()!="")
            SubmitTask()
        else
            Toast.makeText(root.context,"Task name cannot be empty",Toast.LENGTH_SHORT).show()
    }

    private fun getExecutorId(toString: String): MutableList<String> {
        //TODO get from view
        val list =  ArrayList<String>()
        list.add("1")
        return list
    }
    private fun getGroupId(toString: String): String {
        //TODO get from view
        return "1"
    }


}
