package com.example.cricshoeapp.viewmodeltest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cricshoeapp.MockData
import com.example.cricshoeapp.TestDispatcherRule
import com.example.cricshoeapp.db.ShoeRoomDatabase
import com.example.cricshoeapp.db.dao.ShoeDao
import com.example.cricshoeapp.repo.DetailsRepository
import com.example.cricshoeapp.viewmodel.DetailsViewModel
import com.example.cricshoeapp.viewmodel.OneInProgress
import com.example.cricshoeapp.viewmodel.OneSneakerResponse
import com.example.cricshoeapp.viewmodel.OneSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@RunWith(AndroidJUnit4::class)
class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel

    @Mock
    internal lateinit var repo: DetailsRepository

    private lateinit var dao: ShoeDao
    private lateinit var db: ShoeRoomDatabase

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(context, ShoeRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.shoeDao()
        repo = DetailsRepository(dao)
    }

    @Test
    fun `for success resource, data must be available`() = runTest{
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = DetailsViewModel(repo)
        val fakeFlowData = com.example.cricshoeapp.FakeSneakerUseCase(repo).fakeFlow // creating fake use case object
        fakeFlowData.emit(MockData.retMockSingleShoeFromDb())  // emitting success resource from fake use case
        var result: OneSneakerResponse? = null
        viewModel.sneakerData.take(1).collect{
            result = it
        }
        Assert.assertEquals(result, OneInProgress)
    }
}