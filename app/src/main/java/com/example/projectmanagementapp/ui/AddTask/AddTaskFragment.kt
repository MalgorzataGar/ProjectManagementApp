package com.example.projectmanagementapp.ui.AddTask

import android.app.DatePickerDialog
import android.content.Context
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
        id = loadPreference(this.context,"Id") as String
        hash = loadPreference(this.context,"PasswordHash") as String

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
            SubmitTask();
        }
        cancelButton.setOnClickListener {
            ClearPage();
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
        Log.v("AddTaskFragment","Submit Task was chosen")
        val taskID = "13" //TODO replace with id handling
        val priorityDropdown: Spinner = root.findViewById(R.id.taskPriority)
        val executor: Spinner = root.findViewById(R.id.taskExecutor)
        val group : Spinner = root.findViewById(R.id.taskGroup)
        val nameTextView: TextView = root.findViewById(R.id.taskName)
        val descriptionTextView: TextView = root.findViewById(R.id.taskDescription)

        val task =  Task(id, editDate.text.toString(), getExecutorId(executor.selectedItem.toString()),
            getGroupId(group.selectedItem.toString()), taskID, priorityDropdown.selectedItem.toString(),
            "new",descriptionTextView.text.toString(),nameTextView.text.toString())
        Log.v("AddTaskFragment", "Task object was created: $task")

        AwsApisAsyncWrapper(context as Context).postOrUpdateTaskAsync().execute(Pair(task,false)) //TODO if update task - create handling for true
        Toast.makeText(root.context,"Saved",Toast.LENGTH_SHORT).show()
        ClearPage()
        //TODO przy powrocie do ekranu tasków powinno wczytywać z pamięci, a nie ładować od nowa (tutaj trzeba będzie pobrać id taska z odpowiedzi serwera i zapisać w tasku)
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
