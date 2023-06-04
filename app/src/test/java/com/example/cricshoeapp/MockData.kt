package com.example.cricshoeapp

import com.example.cricshoeapp.model.Sneaker

object MockData {
    fun mockListOfShoeInDb() : List<Sneaker> {
        return listOf(
            Sneaker(0, "AB", "ad", "male", "", "", "AB", "", 2010, 22000, "", "",false),
            Sneaker(1, "XY", "adcc", "female", "", "", "BHDH", "", 2016, 21000, "", "",false)
        )
    }

    fun mockSingleShoeFromDb() {
        Sneaker(0, "AB", "ad", "male", "", "", "AB", "", 2010, 22000, "", "",false)
    }

    fun retMockSingleShoeFromDb() = Sneaker(1, "AB", "ad", "male", "", "", "AB", "", 2010, 22000, "", "",false)
}