package com.example.cricshoeapp.utils

interface ShoeItemListener {
    fun clickToAddInCart(sId: Int)
    fun clickToGoDetailPage(sId: Int)
}

interface ShoeCartItemListener {
    fun clickToRemoveItem(sId: Int)
}