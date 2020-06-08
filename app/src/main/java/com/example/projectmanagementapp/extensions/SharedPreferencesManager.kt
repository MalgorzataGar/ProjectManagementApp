package com.example.projectmanagementapp.extensions

import android.content.Context
import androidx.preference.PreferenceManager

    fun savePreference(
        context: Context?,
        name: String?,
        value: String?
    ) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        if (name != null && value != null) {
            editor.putString(name, value)
        }
        editor.apply()
    }

    fun loadPreference(context: Context?, name: String?): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(name, null)
    }
    fun removeAllPreference(context: Context?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.clear()
        editor.apply();
    }



