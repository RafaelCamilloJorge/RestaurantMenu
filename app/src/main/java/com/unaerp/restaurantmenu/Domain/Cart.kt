package com.unaerp.restaurantmenu.Domain

data class Cart(
    val items: MutableList<CartItem>, var totalValue: Double
) {
    companion object {
        fun initialCart(): Cart {
            return Cart(mutableListOf(), 0.0)
        }
    }

    fun addValue(value: Double) {
        totalValue += value
    }
}