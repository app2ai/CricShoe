package com.example.cricshoeapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cricshoeapp.model.Sneaker
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addShoe(sneakers: List<Sneaker>)

    @Query("Select * from tblShoe")
    fun getAllShoes(): Flow<List<Sneaker>>

    @Query("Select * from tblShoe where isAddedToCart = 1")
    fun getOnlyCartShoes(): Flow<List<Sneaker>>

    @Query("Select * from tblShoe where id = :shoeId")
    fun getShoes(shoeId: Int): Flow<Sneaker>?

    @Query("DELETE FROM tblShoe")
    suspend fun deleteAll()

    @Query("Update tblShoe set isAddedToCart = :status where id = :sId")
    suspend fun updateCartStatus(status: Boolean, sId: Int)

    @Query("Select * from tblShoe where name like :query")
    fun getFilteredShoes(query: String): Flow<List<Sneaker>>
}