package com.example.cricshoeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricshoeapp.model.Sneaker
import com.example.cricshoeapp.repo.DetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
) : ViewModel(){
    private var _sneakerData = MutableStateFlow<OneSneakerResponse>(OneInProgress)
    val sneakerData: StateFlow<OneSneakerResponse> = _sneakerData

    fun fetchShoeWithId(id: Int) {
        viewModelScope.launch {
            repository.getShoeById(id)
                ?.catch {
                    _sneakerData.emit(OneFailed)
                }
                ?.collect{
                    _sneakerData.emit(OneSuccess(it))
                }
        }
    }

    fun addItemToCart(id: Int) {
        viewModelScope.launch {
            repository.updateCartStatus(id)
        }
    }
}

// Response sealed status
sealed class OneSneakerResponse
data class OneSuccess(val shoe: Sneaker) : OneSneakerResponse()
object OneFailed : OneSneakerResponse()
object OneInProgress : OneSneakerResponse()