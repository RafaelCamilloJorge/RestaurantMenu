package com.unaerp.restaurantmenu.core.repositories.menu

import com.google.firebase.firestore.FirebaseFirestore
import com.unaerp.restaurantmenu.Domain.MenuCategory
import com.unaerp.restaurantmenu.Domain.MenuItem
import com.unaerp.restaurantmenu.core.results.OnResult

interface MenuRepository {
    val db: FirebaseFirestore

    suspend fun getMenu(): OnResult<List<MenuCategory>>

    suspend fun addItemInShoppingCar(item: MenuItem, idUser: String): OnResult<Unit>

    suspend fun removeItemInShoppingCar(idItem: String, idUser: String): OnResult<Unit>

    suspend fun editQuantityOfItemInShoppingCar(
        idItem: String, newQuantity: Int, idUser: String
    ): OnResult<Unit>

}