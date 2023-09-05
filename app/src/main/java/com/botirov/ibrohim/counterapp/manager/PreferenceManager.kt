package com.botirov.ibrohim.counterapp.manager

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    fun saveCountForZikr(zikrName: String, count: Int) { // takes zikrname, count,
        sharedPreferences.edit().putInt(zikrName, count).apply() //retrieves the editor from the shared preferences and
        // uses it to save the count for the corresponding zikr name using putInt. Finally, it applies the changes using apply.
    }

    fun getCountForZikr(zikrName: String): Int { // It retrieves the count for the corresponding zikr name from the shared preferences using getInt.
        return sharedPreferences.getInt(zikrName, 0) // It returns the retrieved count.
    }
}