package com.example.cricshoeapp.db.cache

import android.content.Context
import android.content.SharedPreferences
import com.example.cricshoeapp.utils.Constants.DB_CACHE

class DbSharedPref(ctx: Context){
    private val dbSharedPreferences: SharedPreferences = ctx.getSharedPreferences(DB_CACHE, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = dbSharedPreferences.edit()

    fun setDBStatus(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getDBStatus(key: String, defaultValue: Boolean) : Boolean {
        return dbSharedPreferences.getBoolean(key, defaultValue)
    }
}