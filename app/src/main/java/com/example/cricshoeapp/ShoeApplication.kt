package com.example.cricshoeapp

import android.app.Application
import com.example.cricshoeapp.di.ShoeApplicationComponent

class ShoeApplication : Application() {
    private lateinit var appComponent: ShoeApplicationComponent
    override fun onCreate() {
        super.onCreate()
        // Initialise app component here
        appComponent = DaggerShoeApplicationComponent.factory().create(applicationContext)
    }
}