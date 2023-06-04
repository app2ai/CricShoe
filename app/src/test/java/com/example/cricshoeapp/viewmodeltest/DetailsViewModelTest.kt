package com.example.cricshoeapp.viewmodeltest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cricshoeapp.repo.DetailsRepository
import com.example.cricshoeapp.viewmodel.DetailsViewModel
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock

@RunWith(AndroidJUnit4::class)
class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel

    @Mock
    internal lateinit var repo: DetailsRepository

    @Before
    fun setup() {
        viewModel = DetailsViewModel(repo)
    }
}