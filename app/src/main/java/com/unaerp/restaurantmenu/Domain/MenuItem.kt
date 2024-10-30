package com.unaerp.restaurantmenu.Domain

class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val image: String
) {
    companion object {
        fun fromCollection(item: Map<String, Any>): MenuItem {
            return MenuItem(
                (item["id"] ?: 0) as Int,
                (item["name"] ?: "").toString(),
                (item["description"] ?: "") as String,
                (item["price"] ?: 0.0) as Double,
                (item["image"] ?: "") as String
            )
        }
    }
}