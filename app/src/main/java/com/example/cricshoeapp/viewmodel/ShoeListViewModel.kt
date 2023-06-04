package com.example.cricshoeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricshoeapp.model.Sneaker
import com.example.cricshoeapp.repo.SneakerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoeListViewModel @Inject constructor(
    private val repository: SneakerRepository
) : ViewModel() {

    private var _sneakerData = MutableStateFlow<SneakerResponse>(InProgress)
    val sneakerData: StateFlow<SneakerResponse> = _sneakerData

    fun fetchAllShoeFromDb() {
        viewModelScope.launch {
            repository.getAllShoes()
                .catch {
                    _sneakerData.emit(Failed)
                }
                .collect{
                    _sneakerData.emit(Success(it))
                }
        }
    }

    fun addItemToCart(id: Int) {
        viewModelScope.launch {
            repository.addSneakerToCart(id)
        }
    }
}

// Response sealed status
sealed class SneakerResponse
data class Success(val shoes: List<Sneaker>) : SneakerResponse()
object Failed : SneakerResponse()
object InProgress : SneakerResponse()