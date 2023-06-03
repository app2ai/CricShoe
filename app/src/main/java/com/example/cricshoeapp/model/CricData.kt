package com.example.cricshoeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Sneakers(
    var sneakers: List<Sneaker>
)

@Entity(tableName = "tblShoe")
data class Sneaker(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var brand_name: String,
    var details: String,
    var gender: String,
    var grid_picture_url: String,
    var main_picture_url: String,
    var name: String,
    var original_picture_url: String,
    var release_year: Int,
    var retail_price_cents: Int,
    var size_range: String,
    var story_html: String?,
    var isAddedToCart: Boolean = false
)