package com.example.cricshoeapp.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.cricshoeapp.MockData
import com.example.cricshoeapp.TestDispatcherRule
import com.example.cricshoeapp.db.dao.ShoeDao
import com.example.cricshoeapp.model.Sneaker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoeDaoTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var db: ShoeRoomDatabase
    private lateinit var dao: ShoeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(context, ShoeRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.shoeDao()
    }

    @After
    fun cleanup() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun added_Sneakers_should_return() = runTest {
        dao.addShoe(MockData.mockListOfShoeInDb())
        dao.getShoes(1)?.test {
            val sn: Sneaker = awaitItem()
            Assert.assertEquals(sn.id, 1)
            cancel()
        }
    }
}