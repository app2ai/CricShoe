package com.example.cricshoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.cricshoeapp.databinding.ActivityMainBinding
import com.example.cricshoeapp.db.cache.DbSharedPref
import com.example.cricshoeapp.model.Sneakers
import com.example.cricshoeapp.utils.Constants.DB_STATUS_KEY
import com.example.cricshoeapp.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.create(
            MainViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as ShoeApplication).appComponent.inject(this)
        setContentView(R.layout.activity_main)

        if (!DbSharedPref(this).getDBStatus(DB_STATUS_KEY, false)) {
            viewModel.setupTheDatabase(provideJsonDataToList())
        }
        observeData()
    }

    private fun observeData() {
        viewModel.status.observe(this) {status ->
            DbSharedPref(this).setDBStatus(DB_STATUS_KEY, status)
        }
    }

    private fun provideJsonDataToList(): Sneakers {
        val type: Type = object : TypeToken<Sneakers>() {}.type
        return Gson().fromJson<Sneakers>(
            BufferedReader(
                InputStreamReader(
                    application.applicationContext.assets.open("data.json")
                )
            ), type
        )
    }
}