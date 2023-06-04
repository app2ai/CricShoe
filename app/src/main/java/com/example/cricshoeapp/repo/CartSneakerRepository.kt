package com.example.cricshoeapp.repo

import com.example.cricshoeapp.db.dao.ShoeDao
import com.example.cricshoeapp.model.Sneaker
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CartSneakerRepository @Inject constructor(
    private val dao: ShoeDao
) {
    fun getCartShoes(): Flow<List<Sneaker>> {
        return dao.getOnlyCartShoes()
    }

    suspend fun removeSneakerFromCart(id: Int) {
        dao.updateCartStatus(false, id)
    }
}
