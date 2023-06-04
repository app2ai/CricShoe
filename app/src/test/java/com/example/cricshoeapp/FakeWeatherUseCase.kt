package com.example.cricshoeapp

import com.example.cricshoeapp.model.Sneaker
import com.example.cricshoeapp.repo.DetailsRepository
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeSneakerUseCase(repo: DetailsRepository) {

    var fakeFlow = MutableSharedFlow<Sneaker>()
    suspend fun emit(value: Sneaker) = fakeFlow.emit(value)

}