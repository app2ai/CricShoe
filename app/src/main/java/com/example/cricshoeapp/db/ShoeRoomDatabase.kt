package com.example.cricshoeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cricshoeapp.db.dao.ShoeDao
import com.example.cricshoeapp.model.Sneaker

// Offline database
@Database(entities = [Sneaker::class], version = 1, exportSchema = false)
abstract class ShoeRoomDatabase : RoomDatabase() {

    abstract fun shoeDao(): ShoeDao

    companion object {
        @Volatile
        private var INSTANCE: ShoeRoomDatabase? = null

        fun getDbInstance(
            context: Context
        ): ShoeRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ShoeRoomDatabase::class.java,
                    "shoe_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}