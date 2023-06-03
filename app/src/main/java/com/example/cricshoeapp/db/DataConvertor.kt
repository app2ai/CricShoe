package com.example.cricshoeapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataConverter {

    @TypeConverter
    fun fromGenderList(gender: List<String>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int>?>() {}.type
        return gson.toJson(gender, type)
    }

    @TypeConverter
    fun toGenderList(gender: String): List<String> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(gender, type)
    }
}