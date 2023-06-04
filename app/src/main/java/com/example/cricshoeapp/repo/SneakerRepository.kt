package com.example.cricshoeapp.repo

import com.example.cricshoeapp.db.dao.ShoeDao
import com.example.cricshoeapp.model.Sneaker
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SneakerRepository @Inject constructor(
    private val dao: ShoeDao
) {
    fun getAllShoes(): Flow<List<Sneaker>> {
        return dao.getAllShoes()
    }

    suspend fun addSneakerToCart(id: Int) {
        dao.updateCartStatus(true, id)
    }
}
