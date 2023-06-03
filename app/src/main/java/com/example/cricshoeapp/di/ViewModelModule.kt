package com.example.cricshoeapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cricshoeapp.viewmodel.ShoeListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [DatabaseModule::class])
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ShoeListViewModel::class)
    fun bindsShoeViewModel(viewModel: ShoeListViewModel) : ViewModel
}
