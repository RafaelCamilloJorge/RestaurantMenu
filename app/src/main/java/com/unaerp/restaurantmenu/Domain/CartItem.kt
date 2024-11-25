package com.unaerp.restaurantmenu.Domain

data class CartItem(
    val id: String,
    val name: String,
    val description: String,
    val unitPrice: Double,
    val image: String,
    val type: String,
    val quantity: Long
) {
    fun totalPrice(): Double {
        return this.quantity * this.unitPrice
    }
}