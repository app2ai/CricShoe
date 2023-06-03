package com.example.cricshoeapp.di

import android.content.Context
import com.example.cricshoeapp.MainActivity
import com.example.cricshoeapp.ui.CartFragment
import com.example.cricshoeapp.ui.ShoeListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// App component dependency provider
@Singleton
@Component(modules = [ViewModelModule::class, DatabaseModule::class])
interface ShoeApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ShoeListFragment)
    fun inject(fragment: CartFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context): ShoeApplicationComponent
    }
}