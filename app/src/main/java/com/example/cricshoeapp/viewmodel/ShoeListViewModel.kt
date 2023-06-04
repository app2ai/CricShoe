package com.example.cricshoeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricshoeapp.model.Sneaker
import com.example.cricshoeapp.repo.SneakerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoeListViewModel @Inject constructor(
    private val repository: SneakerRepository
) : ViewModel() {

    private var _sneakerData = MutableStateFlow<SneakerResponse>(InProgress)
    val sneakerData: StateFlow<SneakerResponse> = _sneakerData

    fun addItemToCart(id: Int) {
        viewModelScope.launch {
            repository.addSneakerToCart(id)
        }
    }

    fun filterSneakers(filterText: String) {
        viewModelScope.launch {
            repository.getFilteredSneakers(filterText)
                .catch {
                    _sneakerData.emit(Failed)
                }
                .collect {
                    if (it.isEmpty()) {
                        _sneakerData.emit(NotAvailable(emptyList()))
                    } else {
                        _sneakerData.emit(Success(it))
                    }
                }
        }
    }
}

// Response sealed status
sealed class SneakerResponse
data class Success(val shoes: List<Sneaker>) : SneakerResponse()
object Failed : SneakerResponse()
object InProgress : SneakerResponse()
data class NotAvailable(val emptyList: List<Sneaker>) : SneakerResponse()