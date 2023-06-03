package com.example.cricshoeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricshoeapp.db.dao.ShoeDao
import com.example.cricshoeapp.model.Sneakers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val dao: ShoeDao
) : ViewModel() {

    private var _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    fun setupTheDatabase(sneakers: Sneakers) = runBlocking{
        val j = viewModelScope.async(Dispatchers.Default) {
            // Add dummy data to DB
            try {
                for (i in 0 until 10)
                    dao.addShoe(sneakers.sneakers)
                return@async true
            } catch (ex: Exception) {
                return@async false
            }
        }
        _status.postValue(j.await())
    }
}