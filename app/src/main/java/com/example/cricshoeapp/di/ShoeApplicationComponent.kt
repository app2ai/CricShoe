package com.example.cricshoeapp.di

import android.content.Context
import com.example.cricshoeapp.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// App component dependency provider
@Singleton
@Component(modules = [ViewModelModule::class, DatabaseModule::class])
interface ShoeApplicationComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ShoeApplicationComponent
    }
}