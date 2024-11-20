package com.unaerp.restaurantmenu.Domain

class ResponseShoppingCartItem(
    var id: String, var quantity: Int
) {
    companion object {
        fun fromMap(map: Map<String, Any>): ResponseShoppingCartItem {
            return ResponseShoppingCartItem(
                (map["id"] ?: "") as String, (map["quantity"] ?: 0) as Int
            )
        }
    }

    fun toMap(): Map<String, Any> {
        var map: MutableMap<String, Any> = mutableMapOf()

        map.put("id", this.id)
        map.put("quantity", this.quantity)
        return map
    }
}