package com.unaerp.restaurantmenu.Domain

import com.google.firebase.firestore.QueryDocumentSnapshot

class MenuCategory(
    val id: Long,
    val title: String,
    val items: List<MenuItem>
) {
    companion object {
        fun fromCollection(collection: QueryDocumentSnapshot): MenuCategory {
            val itemsList = collection.get("items") as? List<Map<String, Any>> ?: emptyList()

            val listMenuItems: MutableList<MenuItem> = mutableListOf()
            for (menuItem in itemsList) {
                listMenuItems.add(MenuItem.fromCollection(menuItem))
            }

            return MenuCategory(
                collection.getLong("id") ?: 0,
                collection.getString("title") ?: "",
                listMenuItems
            )
        }
    }
}