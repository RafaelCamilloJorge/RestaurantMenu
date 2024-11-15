package com.unaerp.restaurantmenu.Domain

data class CartItem(
    val id: Int,
    val name: String,
    val price: Double,
    var quantity: Int = 1
)