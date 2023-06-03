package com.example.cricshoeapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cricshoeapp.model.Sneaker
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoe(sneaker: Sneaker): Long

    @Query("Select * from tblShoe")
    suspend fun getAllShoes(): Flow<List<Sneaker>?>
}