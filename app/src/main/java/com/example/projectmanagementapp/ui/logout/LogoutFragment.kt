package com.example.projectmanagementapp.ui.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanagementapp.R

import com.example.projectmanagementapp.extensions.removeAllPreference
import com.example.projectmanagementapp.ui.login.LoginActivity

class LogoutFragment : Fragment(){
    private lateinit var logoutViewModel: LogoutViewModel

    companion object {
        var TAG = LogoutFragment::class.java.simpleName
        const val ARG_POSITION: String = "positioin"

        fun newInstance(): LogoutFragment {
            var fragment = LogoutFragment();
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logoutViewModel =
            ViewModelProviders.of(this).get(LogoutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_logout, container, false)
        logout()
        return root
    }

    private fun logout() {
        removeAllPreference(this.context)
        val i = Intent(this.context, LoginActivity::class.java)
        startActivity(i)
    }
}
