package com.example.cricshoeapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
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
    fun getAllShoes(): Flow<List<Sneaker>?>

    @Query("Select * from tblShoe where id = 0")
    fun getShoes(): Sneaker

    @Query("DELETE FROM tblShoe")
    suspend fun deleteAll()
}