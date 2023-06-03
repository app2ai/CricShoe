package com.example.cricshoeapp

import android.app.Application
import com.example.cricshoeapp.di.DaggerShoeApplicationComponent
import com.example.cricshoeapp.di.ShoeApplicationComponent

class ShoeApplication : Application() {
    lateinit var appComponent: ShoeApplicationComponent
    override fun onCreate() {
        super.onCreate()
        // Check if DB is filled up with data
        appComponent = DaggerShoeApplicationComponent
            .factory()
            .create(applicationContext)
    }
}