package com.example.projectmanagementapp


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.projectmanagementapp.extensions.loadPreference
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        getSampleRESTRequest() // REST TEST

        //val fab: FloatingActionButton = findViewById(R.id.fab)
        //fab.setOnClickListener { view ->
          //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show()
        //}
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val myIntent = intent
        val txtProfileName =
            navView.getHeaderView(0).findViewById<View>(R.id.header_user) as TextView
        val userName = loadPreference(this.applicationContext,"Name")
        txtProfileName.setText(userName)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_mytasks, R.id.nav_addtask, R.id.nav_grouptasks,
                R.id.nav_addgroup,R.id.nav_editgroup), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun getSampleRESTRequest(){
        Log.v("REST","Uruchomienie wezwania API")
    }
    // TODO przechowywać hasha hasła i singleton id użytkownika
}
