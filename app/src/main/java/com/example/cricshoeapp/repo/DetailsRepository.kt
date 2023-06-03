package com.example.cricshoeapp.repo

import com.example.cricshoeapp.db.dao.ShoeDao
import com.example.cricshoeapp.model.Sneaker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val dao: ShoeDao
) {
    fun getShoeById(id: Int): Flow<Sneaker>? {
        return dao.getShoes(id)
    }

    fun updateCartStatus(id: Int) {
        dao.updateCartStatus(true, id)
    }
}