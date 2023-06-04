package com.example.cricshoeapp.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cricshoeapp.MockData
import com.example.cricshoeapp.db.dao.ShoeDao
import com.example.cricshoeapp.model.Sneaker
import junit.framework.TestCase
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoeRoomDatabaseTest {

    private lateinit var db: ShoeRoomDatabase
    private lateinit var dao: ShoeDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ShoeRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.shoeDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun readWriteDataIntoDb() = runBlocking {
        dao.addShoe(MockData.mockListOfShoeInDb())
        val data = dao.getShoes(1)
        var sn: Sneaker? = null

        data?.take(1)?.collect{
            sn = it
        }
        Assert.assertEquals(sn?.id, 1)
    }
}