package com.example.projectmanagementapp.ui.MyTasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanagementapp.R
import com.example.projectmanagementapp.data.model.Task
import com.example.projectmanagementapp.extensions.OnItemClickListener

class MyTasksFragment : Fragment() {

    private lateinit var myTasksViewModel: MyTasksViewModel
    protected lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: com.example.projectmanagementapp.extensions.ListAdapter

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
    }

    private fun setUpAdapter() {
        adapter.setOnItemClickListener(onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                var task = adapter.getItem(position)
//                startActivity(context?.let {ctx ->
//                    task?.let {
//                            user -> DetailsActivity.newIntent(ctx, user)
//                    }
//                })
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun setUpDummyData(){
        var list: ArrayList<Task> = ArrayList<Task>()
        var task1 = Task()
        task1.taskName = "Taks1"
        var task2 = Task()
        task2.taskName = "Taks2"
        var task3 = Task()
        task3.taskName = "Taks3"
        list.add(task1)
        list.add(task2)
        list.add(task3)
        adapter.addItems(list)
    }
}
