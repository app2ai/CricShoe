package com.example.cricshoeapp.di

import android.content.Context
import com.example.cricshoeapp.db.ShoeRoomDatabase
import com.example.cricshoeapp.db.dao.ShoeDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideShoeDbDao(context: Context): ShoeDao {
        return ShoeRoomDatabase.getDbInstance(context).shoeDao()
    }
}