package com.unaerp.restaurantmenu.Domain

data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val image: String
)