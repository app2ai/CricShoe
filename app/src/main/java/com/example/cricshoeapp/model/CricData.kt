package com.example.cricshoeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblShoe")
data class Sneaker(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val brand_name: String,
    val color: String,
    val details: String,
    val gender: List<String>,
    val grid_picture_url: String,
    val has_picture: Boolean,
    val has_stock: Boolean,
    val main_picture_url: String,
    val midsole: String,
    val name: String,
    val nickname: String,
    val original_picture_url: String,
    val release_year: Int,
    val retail_price_cents: Int,
    val shoe_condition: String,
    val size_range: List<Int>,
    val sku: String,
    val slug: String,
    val status: String,
    val story_html: String,
    var isAddedToCart: Boolean = false
)