package com.unaerp.restaurantmenu.Domain

import com.google.firebase.firestore.QueryDocumentSnapshot

class ResponseMenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val type: String,
) {
    companion object {
        fun fromCollection(item: QueryDocumentSnapshot): ResponseMenuItem {
            return ResponseMenuItem(
                item.id,
                item.getString("name") ?: "",
                item.getString("description") ?: "",
                item.getDouble("price") ?: 0.0,
                item.getString("image") ?: "",
                item.getString("type") ?: ""
            )
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "name" to name,
            "description" to description,
            "price" to price,
            "image" to image,
            "type" to type,
        )
    }
}