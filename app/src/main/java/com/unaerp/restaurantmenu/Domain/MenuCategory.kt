package com.unaerp.restaurantmenu.Domain

data class MenuCategory(
    val id: Int,
    val title: String,
    val items: List<MenuItem>
)