package com.example.cricshoeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricshoeapp.model.Sneaker
import com.example.cricshoeapp.repo.CartSneakerRepository
import com.example.cricshoeapp.utils.Constants.FIXED_TAX_CHARGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val repository: CartSneakerRepository
): ViewModel() {

    private var _cartSneakerData = MutableStateFlow<CartSneakerResponse>(CartInProgress)
    val cartSneakerData: StateFlow<CartSneakerResponse> = _cartSneakerData

    private var _subtotalLD = MutableStateFlow<Int>(0)
    val subtotalLD: StateFlow<Int> = _subtotalLD

    private var _totalLD = MutableStateFlow<Int>(0)
    val totalLD: StateFlow<Int> = _totalLD

    fun fetchCartShoeFromDb() {
        viewModelScope.launch {
            repository.getCartShoes()
                .catch {
                    _cartSneakerData.emit(CartFailed)
                }
                .collect{
                    if (it.isEmpty()) {
                        _cartSneakerData.emit(CartEmpty)
                    } else {
                        _cartSneakerData.emit(CartSuccess(it))
                    }
                    calculateCost(it)
                }
        }
    }

    private fun calculateCost(sneakers: List<Sneaker>) {
        var total = 0
        sneakers.forEach {
            total += it.retail_price_cents/100
        }
        viewModelScope.launch {
            _subtotalLD.emit(total)
            _totalLD.emit(total+FIXED_TAX_CHARGE)
        }
    }

    fun removeItemToCart(id: Int) {
        viewModelScope.launch {
            repository.removeSneakerFromCart(id)
        }
    }
}

// Response sealed status
sealed class CartSneakerResponse
data class CartSuccess(val shoes: List<Sneaker>) : CartSneakerResponse()
object CartFailed : CartSneakerResponse()
object CartInProgress : CartSneakerResponse()
object CartEmpty : CartSneakerResponse()